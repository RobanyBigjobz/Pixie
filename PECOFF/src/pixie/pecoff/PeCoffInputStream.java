/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pixie.pecoff;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * $Id$
 *
 * @author adminjamesd
 */
public class PeCoffInputStream extends FilterInputStream
{
	private static final Logger logger =
		Logger.getLogger(PeCoffInputStream.class.getName());
	private int pos;
	private int markedPos;
	private int peHeaderPos;
	private int coffHeaderPos;
	private int optHeaderPos;
	private int sectionTablePos;
	private int sectionBase;

	static
	{
		logger.setLevel(Level.FINE);
	}

	public PeCoffInputStream(File file) throws IOException
	{
		this(new BufferedInputStream(new FileInputStream(file)));
	}

	public PeCoffInputStream(InputStream in)
	{
		super(in);
	}

	private void checkPESignature() throws IOException
	{
		byte[] b = new byte[4];
		readFully(b, 0, 4);
		if ((b[0] != 'P') || (b[1] != 'E') || (b[2] != 0) || (b[3] != 0))
		{
			throw new PeCoffCodingException("Invalid PE signature");
		}
		logger.log(Level.FINE, "PE signature OK");
	}

	private SectionTableEntry getEntry(List<SectionTableEntry> sectionTable,
		String entryKey)
	{
		for (SectionTableEntry currEntry : sectionTable)
		{
			if (currEntry.getName().equals(entryKey))
			{
				return currEntry;
			}
		}
		return null;
	}

	public PeCoff getPeCoffImage() throws IOException
	{
		PeCoff image = new PeCoff();

		int len = available();
		logger.log(Level.FINE, "Image: {0} bytes", len);
		if (len < (0x40))
		{
			throw new PeCoffCodingException("Too few bytes");
		}
		mark(0x40);
		skip(0x3c);
		peHeaderPos = readInt32();
		logger.log(Level.FINE, "PE Header Offset: {0}", peHeaderPos);
		logger.log(Level.FINE, "PE Header Offset: {0}",
			String.format("0x08x", peHeaderPos));
		reset();
		mark(available());
		byte[] stub = new byte[peHeaderPos];
		readFully(stub, 0, peHeaderPos);
		checkPESignature();
		image.setStub(stub);

		CoffHeader coffHdr = readCoffHeader();
		image.setCoffHeader(coffHdr);
		dumpCoffHeader(coffHdr);
		PeCoffOptionalHeader optHdr = null;
		if (coffHdr.getOptHdrSize() > 0)
		{
			optHdr = readPeCoffOptionalHeader();
		}
		image.setPeCoffOptionalHeader(optHdr);
		dumpPeCoffOptionalHeader(optHdr);

		List<SectionTableEntry> sectionTable =
			readSectionTable(coffHdr.getSectionCount());
		image.setSectionTable(sectionTable);
		dumpSectionTable(sectionTable);
		reset();

		SectionTableEntry entry = getEntry(sectionTable, Section.TLS);
		TlsSection tls = readTlsSection(entry, optHdr.isPlus());
		dumpTlsSection(tls);
		reset();

		entry = getEntry(sectionTable, Section.RESOURCE);
		ResourceSection resource = readResourceSection(entry);
		dumpResourceSection(resource);
		reset();

		return image;
	}

	@Override
	public void mark(int readlimit)
	{
		in.mark(readlimit);
		markedPos = pos;
	}

	@Override
	public int read() throws IOException
	{
		int ch = in.read();
		if (ch != -1)
		{
			++pos;
		}
		return ch;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException
	{
		int result = in.read(b, off, len);
		if (result != -1)
		{
			pos += result;
		}
		return result;
	}

	private byte readByte() throws IOException
	{
		byte[] b = new byte[1];
		readFully(b, 0, 1);
		return b[0];
	}

	private byte readByte(InputStream stream) throws IOException
	{
		byte[] b = new byte[1];
		readFully(stream, b, 0, 1);
		return b[0];
	}

	private CoffHeader readCoffHeader() throws IOException
	{
		coffHeaderPos = pos;

		CoffHeader coffHdr = new CoffHeader();
		coffHdr.setMachine(readInt16());
		coffHdr.setSectionCount(readInt16());
		coffHdr.setTimeStamp(readInt32());
		coffHdr.setPtrToSymTable(readInt32());
		coffHdr.setSymbolCount(readInt32());
		coffHdr.setOptionalHeaderSize(readInt16());
		coffHdr.setCharacteristics(readInt16());

		return coffHdr;
	}

	public final void readFully(byte b[], int off, int len) throws IOException
	{
		if (len < 0)
		{
			throw new IndexOutOfBoundsException();
		}
		int n = 0;
		while (n < len)
		{
			int count = read(b, off + n, len - n);
			if (count < 0)
			{
				throw new EOFException();
			}
			n += count;
		}
	}

	public final void readFully(InputStream stream, byte b[], int off, int len) throws IOException
	{
		if (len < 0)
		{
			throw new IndexOutOfBoundsException();
		}
		int n = 0;
		while (n < len)
		{
			int count = stream.read(b, off + n, len - n);
			if (count < 0)
			{
				throw new EOFException();
			}
			n += count;
		}
	}

	private short readInt16() throws IOException
	{
		byte[] b = new byte[2];
		readFully(b, 0, 2);
		return (short) ((b[0] & 0x000000ff) + ((b[1] << 8) & 0x0000ff00));
	}

	private short readInt16(InputStream stream) throws IOException
	{
		byte[] b = new byte[2];
		readFully(stream, b, 0, 2);
		return (short) ((b[0] & 0x000000ff) + ((b[1] << 8) & 0x0000ff00));
	}

	private int readInt32() throws IOException
	{
		byte[] b = new byte[4];
		readFully(b, 0, 4);
		return (b[0] & 0x000000ff) + ((b[1] << 8) & 0x0000ff00) +
			((b[2] << 16) & 0x00ff0000) + ((b[3] << 24) & 0xff000000);
	}

	private int readInt32(InputStream stream) throws IOException
	{
		byte[] b = new byte[4];
		readFully(stream, b, 0, 4);
		return (b[0] & 0x000000ff) + ((b[1] << 8) & 0x0000ff00) +
			((b[2] << 16) & 0x00ff0000) + ((b[3] << 24) & 0xff000000);
	}

	private long readInt64() throws IOException
	{
		byte[] b = new byte[8];
		readFully(b, 0, 8);
		return (b[0] & 0x00000000000000ffL) +
			((b[1] << 8) & 0x000000000000ff00L) +
			((b[2] << 16) & 0x0000000000ff0000L) +
			((b[3] << 24) & 0x00000000ff000000L) +
			((b[4] << 32) & 0x000000ff00000000L) +
			((b[5] << 40) & 0x0000ff0000000000L) +
			((b[6] << 48) & 0x00ff000000000000L) +
			((b[7] << 56) & 0xff00000000000000L);
	}

	private long readInt64(InputStream stream) throws IOException
	{
		byte[] b = new byte[8];
		readFully(stream, b, 0, 8);
		return (b[0] & 0x00000000000000ffL) +
			((b[1] << 8) & 0x000000000000ff00L) +
			((b[2] << 16) & 0x0000000000ff0000L) +
			((b[3] << 24) & 0x00000000ff000000L) +
			((b[4] << 32) & 0x000000ff00000000L) +
			((b[5] << 40) & 0x0000ff0000000000L) +
			((b[6] << 48) & 0x00ff000000000000L) +
			((b[7] << 56) & 0xff00000000000000L);
	}

	private PeCoffOptionalHeader readPeCoffOptionalHeader() throws IOException
	{
		optHeaderPos = pos;

		PeCoffOptionalHeader optHdr = new PeCoffOptionalHeader();
		short magic = readInt16();
		if ((magic != PeCoff.PE32) &&
			 (magic != PeCoff.PE32PLUS))
		{
			throw new PeCoffCodingException(
				"Bad magic: "+String.format("0x%04x", magic));
		}
		optHdr.setMagic(magic);
		optHdr.setMajorLinkerVersion(readByte());
		optHdr.setMinorLinkerVersion(readByte());
		optHdr.setSizeOfCode(readInt32());
		optHdr.setSizeOfInitialisedData(readInt32());
		optHdr.setSizeOfUnintialisedData(readInt32());
		optHdr.setAddressOfEntryPoint(readInt32());
		optHdr.setBaseOfCode(readInt32());
		if (!optHdr.isPlus())
		{
			optHdr.setBaseOfData(readInt32());
		}

		// Windows specifics
		if (!optHdr.isPlus())
		{
			optHdr.setImageBase(readInt32());
		}
		else
		{
			optHdr.setImageBase(readInt64());
		}
		optHdr.setSectionAlignment(readInt32());
		optHdr.setFileAlignment(readInt32());
		optHdr.setMajorOSVersion(readInt16());
		optHdr.setMinorOSVersion(readInt16());
		optHdr.setMajorImageVersion(readInt16());
		optHdr.setMinorImageVersion(readInt16());
		optHdr.setMajorSubsystemVersion(readInt16());
		optHdr.setMinorSubsystemVersion(readInt16());
		optHdr.setWin32VersionValue(readInt32());
		optHdr.setSizeOfImage(readInt32());
		optHdr.setSizeOfHeaders(readInt32());
		optHdr.setCheckSum(readInt32());
		optHdr.setSubsystem(readInt16());
		optHdr.setDllCharacteristics(readInt16());
		if (!optHdr.isPlus())
		{
			optHdr.setSizeOfStackReserve(readInt32());
			optHdr.setSizeOfStackCommit(readInt32());
			optHdr.setSizeOfHeapReserve(readInt32());
			optHdr.setSizeOfHeapCommit(readInt32());
		}
		else
		{
			optHdr.setSizeOfStackReserve(readInt64());			
			optHdr.setSizeOfStackCommit(readInt64());			
			optHdr.setSizeOfHeapReserve(readInt64());			
			optHdr.setSizeOfHeapCommit(readInt64());			
		}
		optHdr.setLoaderFlags(readInt32());
		optHdr.setNumberOfRvaAndSizes(readInt32());
		optHdr.setExportTableAddress(readInt32());
		optHdr.setExportTableSize(readInt32());
		optHdr.setImportTableAddress(readInt32());
		optHdr.setImportTableSize(readInt32());
		optHdr.setResourceTableAddress(readInt32());
		optHdr.setResourceTableSize(readInt32());
		optHdr.setExceptionTableAddress(readInt32());
		optHdr.setExceptionTableSize(readInt32());
		optHdr.setCertTableAddress(readInt32());
		optHdr.setCertTableSize(readInt32());
		optHdr.setBaseRelocTableAddress(readInt32());
		optHdr.setBaseRelocTableSize(readInt32());
		optHdr.setDebugAddress(readInt32());
		optHdr.setDebugSize(readInt32());
		optHdr.setArchitecture(readInt64());
		optHdr.setGlobalPtrAddress(readInt32());
		optHdr.setGlobalPtrSize(readInt32());
		optHdr.setTlsTableAddress(readInt32());
		optHdr.setTlsTableSize(readInt32());
		optHdr.setLoadConfigTableAddress(readInt32());
		optHdr.setLoadConfigTableSize(readInt32());
		optHdr.setBoundImportTableAddress(readInt32());
		optHdr.setBoundImportTableSize(readInt32());
		optHdr.setIaTableAddress(readInt32());
		optHdr.setIaTableSize(readInt32());
		optHdr.setDelayImportDescAddress(readInt32());
		optHdr.setDelayImportDescSize(readInt32());
		optHdr.setClrRuntimeHeaderAddress(readInt32());
		optHdr.setClrRuntimeHeaderSize(readInt32());
		optHdr.setReserved(readInt64());

		return optHdr;
	}

	private ResourceDataEntry readResourceDataEntry(InputStream stream,
		int dataEntryRVA) throws IOException
	{
		stream.skip(dataEntryRVA);

		ResourceDataEntry rde = new ResourceDataEntry();
		rde.setDataRVA(readInt32(stream));
		rde.setSize(readInt32(stream));
		rde.setCodePage(readInt32(stream));
		rde.setReserved(readInt32(stream));

		return rde;
	}

	private ResourceDirectoryTable readResourceDirectoryTable(InputStream stream) throws IOException
	{
		return readResourceDirectoryTable(stream, 0);
	}

	private ResourceDirectoryTable readResourceDirectoryTable(InputStream stream,
		int subDirectoryRVA) throws IOException
	{
		stream.skip(subDirectoryRVA);

		ResourceDirectoryTable rdt = new ResourceDirectoryTable();
		rdt.setCharacteristics(readInt32(stream));
		rdt.setTimeStamp(readInt32(stream));
		rdt.setMajorVersion(readInt16(stream));
		rdt.setMinorVersion(readInt16(stream));
		rdt.setNameEntryCount(readInt16(stream));
		rdt.setIdEntryCount(readInt16(stream));

		List<ResourceDirectoryEntry> nameEntries = readResourceDirEntries(
			stream, rdt.getNameEntryCount(), false);
		rdt.setNameEntries(nameEntries);

		List<ResourceDirectoryEntry> idEntries = readResourceDirEntries(
			stream, rdt.getIdEntryCount(), true);
		rdt.setIdEntries(idEntries);

		return rdt;
	}

	private List<ResourceDirectoryEntry> readResourceDirEntries(
		InputStream stream, int nEntries, boolean isId) throws IOException
	{
		List<ResourceDirectoryEntry> entries =
			new ArrayList<ResourceDirectoryEntry>();
		for (int i=0; i<nEntries; i++)
		{
			ResourceDirectoryEntry rde = new ResourceDirectoryEntry();
			if (isId)
			{
				rde.setIntegerID(readInt32(stream));
			}
			else
			{
				rde.setNameRVA(readInt32(stream));
			}
			int field2 = readInt32(stream);
			int highBit32 = 0x80000000;
			if ((field2 & highBit32) == highBit32)
			{
				rde.setSubDirectoryRVA(field2);
			}
			else
			{
				rde.setDataEntryRVA(field2);
			}
			entries.add(rde);
		}

		return entries;
	}

	private ResourceSection readResourceSection(SectionTableEntry entry) throws IOException
	{
		if (entry == null)
		{
			return null;
		}

		// Read whole section into a byte array for use as a new InputStream
		ResourceSection section = new ResourceSection();
		int offset = entry.getPtrToRawData();
		logger.log(Level.FINE, "File offset for .rsrc section: {0}",
			String.format("0x%08x", offset));
		skip(offset);
		int size = entry.getSizeOfRawData();
		byte[] rawData = new byte[size];
		readFully(rawData, 0, size);
		ByteArrayInputStream rsrcIn = new ByteArrayInputStream(rawData);
		rsrcIn.mark(size);

		// Read the dir tables from the new stream
		List<ResourceDirectoryTable> dirTables =
			new ArrayList<ResourceDirectoryTable>();
		ResourceDirectoryTable rdt = readResourceDirectoryTable(rsrcIn);
		dirTables.add(rdt);
		int nTablesProcessed = 0;
		while (nTablesProcessed < dirTables.size())
		{
			processDirTable(rsrcIn, dirTables.get(nTablesProcessed), dirTables);
			nTablesProcessed++;
		}
		section.setDirectoryTables(dirTables);

		return section;
	}

	private int processDirTable(ByteArrayInputStream stream,
		ResourceDirectoryTable dirTable, List<ResourceDirectoryTable> dirTableList)
		throws IOException
	{
		List<ResourceDirectoryEntry> entries;
		int nNew = 0;

		entries = dirTable.getIdEntries();
		int nEntries = entries.size();
		for (int i=0; i<nEntries; i++)
		{
			ResourceDirectoryEntry dirEntry = entries.get(i);
			stream.reset();
			if (dirEntry.isLeaf())
			{
				ResourceDataEntry dataEntry = readResourceDataEntry(stream,
					dirEntry.getDataEntryRVA());
				dirEntry.setDataEntry(dataEntry);
			}
			else
			{
				ResourceDirectoryTable rdt = readResourceDirectoryTable(stream,
					dirEntry.getSubDirectoryRVA());
				dirTableList.add(rdt);
				nNew++;
			}
		}

		return nNew;
	}

	private String readSectionName() throws IOException
	{
		StringBuilder sb = new StringBuilder();
		byte[] nameBytes = new byte[8];
		readFully(nameBytes, 0, 8);
		for (int i=0; i<8; i++)
		{
			if (nameBytes[i] == 0)
			{
				break;
			}
			sb.append((char) nameBytes[i]);
		}
		return sb.toString();
	}

	private List<SectionTableEntry> readSectionTable(int entryCount) throws IOException
	{
		sectionTablePos = pos;
		sectionBase = pos;

		List<SectionTableEntry> table = new ArrayList<SectionTableEntry>();
		for (int i=0; i<entryCount; i++)
		{
			table.add(readSectionTableEntry());
		}

		return table;
	}

	private SectionTableEntry readSectionTableEntry() throws IOException
	{
		SectionTableEntry entry = new SectionTableEntry();
		entry.setName(readSectionName());
		entry.setVirtualSize(readInt32());
		entry.setVirtualAddress(readInt32());
		entry.setSizeOfRawData(readInt32());
		entry.setPtrToRawData(readInt32());
		entry.setPtrToRelocations(readInt32());
		entry.setPtrToLineNumbers(readInt32());
		entry.setRelocationsCount(readInt16());
		entry.setLineNumbersCount(readInt16());
		entry.setCharacteristics(readInt32());
		return entry;
	}

	private TlsSection readTlsSection(SectionTableEntry entry, boolean isPlus) throws IOException
	{
		if (entry == null)
		{
			return null;
		}
		int offset = entry.getPtrToRawData();
		logger.log(Level.FINE, "File offset for .tls section: {0}",
			String.format("0x%08x", offset));
		skip(offset);
		TlsSection tls = new TlsSection();
		if (isPlus)
		{
			tls.setRawDataStartVA(readInt64());
			tls.setRawDataEndVA(readInt64());
			tls.setAddressOfIndex(readInt64());
			tls.setAddressOfCallbacks(readInt64());
		}
		else
		{
			tls.setRawDataStartVA(readInt32());
			tls.setRawDataEndVA(readInt32());
			tls.setAddressOfIndex(readInt32());
			tls.setAddressOfCallbacks(readInt32());
		}
		tls.setSizeOfZeroFill(readInt32());
		tls.setCharacteristics(readInt32());
		return tls;
	}

	@Override
	public void reset() throws IOException
	{
		in.reset();
		pos = markedPos;
	}

	@Override
	public long skip(long n) throws IOException
	{
		long result = in.skip(n);
		if (result > 0)
		{
			pos += result;
		}
		return result;
	}

	private void dumpCoffHeader(CoffHeader coffHdr)
	{
		logger.log(Level.FINE, "COFF Header @ {0}",
			String.format("0x%08x", coffHeaderPos));
		logger.log(Level.FINE, " * Machine: {0}",
			String.format("0x%04x", coffHdr.getMachine()));
		logger.log(Level.FINE, " * Sections: {0}", coffHdr.getSectionCount());
		logger.log(Level.FINE, " * Timestamp: {0}",
			String.format("0x%08x", coffHdr.getTimeStamp()));
		logger.log(Level.FINE, " * Ptr to Sym Table: {0}",
			String.format("0x%08x", coffHdr.getPtrToSymTable()));
		logger.log(Level.FINE, " * Symbols: {0}", coffHdr.getSymbolCount());
		logger.log(Level.FINE, " * Opt Header Size: {0}",
			String.format("0x%04x", coffHdr.getOptHdrSize()));
		logger.log(Level.FINE, " * Flags: {0}",
			String.format("0x%04x", coffHdr.getCharacteristics()));
	}

	private void dumpPeCoffOptionalHeader(PeCoffOptionalHeader optHdr)
	{
		logger.log(Level.FINE, "PE/COFF Optional Header @ {0}",
			String.format("0x%08x", optHeaderPos));
		logger.log(Level.FINE, " * Magic: {0}",
			String.format("0x%04x", optHdr.getMagic()));
		logger.log(Level.FINE, " * Major Liner Version: {0}",
			optHdr.getMajorLinkerVersion());
		logger.log(Level.FINE, " * Minor Linker Version: {0}",
			optHdr.getMinorLinkerVersion());
		logger.log(Level.FINE, " * Size of Code: {0}", optHdr.getSizeOfCode());
		logger.log(Level.FINE, " * Size of Initialised Data: {0}",
			String.format("0x%08x", optHdr.getSizeOfInitialisedData()));
		logger.log(Level.FINE, " * Size of Uninitialised Data: {0}",
			String.format("0x%08x", optHdr.getSizeOfUnintialisedData()));
		logger.log(Level.FINE, " * Address of Entry Point: {0}",
			String.format("0x%08x", optHdr.getAddressOfEntryPoint()));
		logger.log(Level.FINE, " * Base of Code: {0}",
			String.format("0x%08x", optHdr.getBaseOfCode()));
		if (!optHdr.isPlus())
		{
			logger.log(Level.FINE, " * Base of Data: {0}",
				String.format("0x%08x", optHdr.getBaseOfData()));
		}
		// Windows specifics
		if (!optHdr.isPlus())
		{
			logger.log(Level.FINE, " * Image Base: {0}",
				String.format("0x%08x", optHdr.getImageBase()));
		}
		else
		{
			logger.log(Level.FINE, " * Image Base: {0}",
				String.format("0x%016x", optHdr.getImageBase()));
		}
		logger.log(Level.FINE, " * Section Alignment: {0}",
			String.format("0x%08x", optHdr.getSectionAlignment()));
		logger.log(Level.FINE, " * File Alignment: {0}",
			String.format("0x%08x", optHdr.getFileAlignment()));
		logger.log(Level.FINE, " * Major OS Version: {0}",
			optHdr.getMajorOSVersion());
		logger.log(Level.FINE, " * Minor OS Version: {0}",
			optHdr.getMinorOSVersion());
		logger.log(Level.FINE, " * Major Image Version: {0}",
			optHdr.getMajorOSVersion());
		logger.log(Level.FINE, " * Minor Image Version: {0}",
			optHdr.getMinorOSVersion());
		logger.log(Level.FINE, " * Major Subsystem Version: {0}",
			optHdr.getMajorSubsystemVersion());
		logger.log(Level.FINE, " * Minor Subsystem Version: {0}",
			optHdr.getMinorSubsystemVersion());
		logger.log(Level.FINE, " * Win32 Version Value: {0}",
			String.format("0x%08x", optHdr.getWin32VersionValue()));
		logger.log(Level.FINE, " * Size of Image: {0}",
			String.format("0x%08x", optHdr.getSizeOfImage()));
		logger.log(Level.FINE, " * Size of Headers: {0}",
			String.format("0x%08x", optHdr.getSizeOfHeaders()));
		logger.log(Level.FINE, " * Checksum: {0}",
			String.format("0x%08x", optHdr.getCheckSum()));
		logger.log(Level.FINE, " * Subsystem: {0}",
			String.format("0x%04x", optHdr.getSubsystem()));
		logger.log(Level.FINE, " * DLL Characteristics: {0}",
			String.format("0x%04x", optHdr.getDllCharacteristics()));
		if (!optHdr.isPlus())
		{
			logger.log(Level.FINE, " * Size of Stack Reserve: {0}",
				String.format("0x%08x", optHdr.getSizeOfStackReserve()));
			logger.log(Level.FINE, " * Size of Stack Commit: {0}",
				String.format("0x%08x", optHdr.getSizeOfStackCommit()));
			logger.log(Level.FINE, " * Size of Heap Reserve: {0}",
				String.format("0x%08x", optHdr.getSizeOfHeapReserve()));
			logger.log(Level.FINE, " * Size of Heap Commit: {0}",
				String.format("0x%08x", optHdr.getSizeOfHeapCommit()));
		}
		else
		{
			logger.log(Level.FINE, " * Size of Stack Reserve: {0}",
				String.format("0x%016x", optHdr.getSizeOfStackReserve()));
			logger.log(Level.FINE, " * Size of Stack Commit: {0}",
				String.format("0x%016x", optHdr.getSizeOfStackCommit()));
			logger.log(Level.FINE, " * Size of Heap Reserve: {0}",
				String.format("0x%016x", optHdr.getSizeOfHeapReserve()));
			logger.log(Level.FINE, " * Size of Heap Commit: {0}",
				String.format("0x%016x", optHdr.getSizeOfHeapCommit()));
		}
		logger.log(Level.FINE, " * Loader Flags: {0}",
			String.format("0x%08x", optHdr.getLoaderFlags()));
		logger.log(Level.FINE, " * Number of RVA and Sizes: {0}",
			optHdr.getNumberOfRvaAndSizes());
		logger.log(Level.FINE, " * Export Table Address: {0}",
			String.format("0x%08x", optHdr.getExportTableAddress()));
		logger.log(Level.FINE, " * Export Table Size: {0}",
			String.format("0x%08x", optHdr.getExportTableSize()));
		logger.log(Level.FINE, " * Import Table Address: {0}",
			String.format("0x%08x", optHdr.getImportTableAddress()));
		logger.log(Level.FINE, " * Import Table Size: {0}",
			String.format("0x%08x", optHdr.getImportTableSize()));
		logger.log(Level.FINE, " * Resource Table Address: {0}",
			String.format("0x%08x", optHdr.getResourceTableAddress()));
		logger.log(Level.FINE, " * Resource Table Size: {0}",
			String.format("0x%08x", optHdr.getResourceTableSize()));
		logger.log(Level.FINE, " * Exception Table Address: {0}",
			String.format("0x%08x", optHdr.getExceptionTableAddress()));
		logger.log(Level.FINE, " * Exception Table Size: {0}",
			String.format("0x%08x", optHdr.getExceptionTableSize()));
		logger.log(Level.FINE, " * Certificate Table Address: {0}",
			String.format("0x%08x", optHdr.getCertTableAddress()));
		logger.log(Level.FINE, " * Certificate Table Size: {0}",
			String.format("0x%08x", optHdr.getCertTableSize()));
		logger.log(Level.FINE, " * Base Relocation Table Address: {0}",
			String.format("0x%08x", optHdr.getBaseRelocTableAddress()));
		logger.log(Level.FINE, " * Base Relocation Table Size: {0}",
			String.format("0x%08x", optHdr.getBaseRelocTableSize()));
		logger.log(Level.FINE, " * Debug Address: {0}",
			String.format("0x%08x", optHdr.getDebugAddress()));
		logger.log(Level.FINE, " * Debug Size: {0}",
			String.format("0x%08x", optHdr.getDebugSize()));
		logger.log(Level.FINE, " * Architecture: {0}",
			String.format("0x%016x", optHdr.getArchitecture()));
		logger.log(Level.FINE, " * Global Pointer Address: {0}",
			String.format("0x%08x", optHdr.getGlobalPtrAddress()));
		logger.log(Level.FINE, " * Global Pointer Size: {0}",
			String.format("0x%08x", optHdr.getGlobalPtrSize()));
		logger.log(Level.FINE, " * TLS Table Address: {0}",
			String.format("0x%08x", optHdr.getTlsTableAddress()));
		logger.log(Level.FINE, " * TLS Table Size: {0}",
			String.format("0x%08x", optHdr.getTlsTableSize()));
		logger.log(Level.FINE, " * Load Config Table Address: {0}",
			String.format("0x%08x", optHdr.getLoadConfigTableAddress()));
		logger.log(Level.FINE, " * Load Config Table Size: {0}",
			String.format("0x%08x", optHdr.getLoadConfigTableSize()));
		logger.log(Level.FINE, " * Bound Import Table Address: {0}",
			String.format("0x%08x", optHdr.getBoundImportTableAddress()));
		logger.log(Level.FINE, " * Bound Import Table Size: {0}",
			String.format("0x%08x", optHdr.getBoundImportTableSize()));
		logger.log(Level.FINE, " * IA Table Address: {0}",
			String.format("0x%08x", optHdr.getIaTableAddress()));
		logger.log(Level.FINE, " * IA Table Size: {0}",
			String.format("0x%08x", optHdr.getIaTableSize()));
		logger.log(Level.FINE, " * Delay Import Descriptor Address: {0}",
			String.format("0x%08x", optHdr.getDelayImportDescAddress()));
		logger.log(Level.FINE, " * Delay Import Descriptor Size: {0}",
			String.format("0x%08x", optHdr.getDelayImportDescSize()));
		logger.log(Level.FINE, " * CLR Runtime Header Address: {0}",
			String.format("0x%08x", optHdr.getClrRuntimeHeaderAddress()));
		logger.log(Level.FINE, " * CLR Runtime Header Size: {0}",
			String.format("0x%08x", optHdr.getClrRuntimeHeaderSize()));
		logger.log(Level.FINE, " * Reserved: {0}",
			String.format("0x%016x", optHdr.getReserved()));
	}

	private void dumpResourceSection(ResourceSection section)
	{
		logger.log(Level.FINE, "Section: {0}", section.getName());
		List<ResourceDirectoryTable> dirTables = section.getDirectoryTables();
		int nTables = dirTables.size();
		for (int i=0; i<nTables; i++)
		{
			logger.log(Level.FINE, " * Directory Table: {0}", i);
			dumpResourceDirTable(dirTables.get(i));
		}
	}

	private void dumpResourceDirTable(ResourceDirectoryTable rdt)
	{

		logger.log(Level.FINE, "    * Characteristics: {0}",
			String.format("0x%08x", rdt.getCharacteristics()));
		logger.log(Level.FINE, "    * Timestamp: {0}",
			String.format("0x%08x", rdt.getTimeStamp()));
		logger.log(Level.FINE, "    * Major Version: {0}",
			String.format("0x%04x", rdt.getMajorVersion()));
		logger.log(Level.FINE, "    * Minor Version: {0}",
			String.format("0x%04x", rdt.getMinorVersion()));
		logger.log(Level.FINE, "    * Name Entry Count: {0}",
			rdt.getNameEntryCount());
		logger.log(Level.FINE, "    * ID Entry Count: {0}",
			rdt.getIdEntryCount());

		List<ResourceDirectoryEntry> nameEntries = rdt.getNameEntries();
		int nameEntryCount = nameEntries.size();
		for (int j=0; j<nameEntryCount; j++)
		{
			ResourceDirectoryEntry rde = nameEntries.get(j);
			logger.log(Level.FINE, "    * Name Directory Entry {0}", j);
			logger.log(Level.FINE, "       * Name RVA: {0}",
				String.format("0x%08x", rde.getNameRVA()));
			if (rde.isLeaf())
			{
				logger.log(Level.FINE, "       * Data Entry RVA: {0}",
					String.format("0x%08x", rde.getDataEntryRVA()));
			}
			else
			{
				logger.log(Level.FINE, "       * Sub-Directory RVA: {0}",
					String.format("0x%08x", rde.getSubDirectoryRVA()));
			}
		}
		List<ResourceDirectoryEntry> idEntries = rdt.getIdEntries();
		int idEntryCount = idEntries.size();
		for (int j=0; j<idEntryCount; j++)
		{
			ResourceDirectoryEntry rde = idEntries.get(j);
			logger.log(Level.FINE, "    * ID Directory Entry {0}", j);
			logger.log(Level.FINE, "       * Integer ID: {0}",
				String.format("0x%08x", rde.getIntegerID()));
			if (rde.isLeaf())
			{
				logger.log(Level.FINE, "       * Data Entry RVA: {0}",
					String.format("0x%08x", rde.getDataEntryRVA()));
				dumpDataEntry(rde.getDataEntry());
			}
			else
			{
				logger.log(Level.FINE, "       * Sub-Directory RVA: {0}",
					String.format("0x%08x", rde.getSubDirectoryRVA()));
			}
		}
	}

	private void dumpDataEntry(ResourceDataEntry rde)
	{
		logger.log(Level.FINE, "          * Data RVA: {0}",
			String.format("0x%08x", rde.getDataRVA()));
		logger.log(Level.FINE, "          * Data Size: {0}",
			String.format("0x%08x", rde.getSize()));
		logger.log(Level.FINE, "          * Code Page: {0}",
			String.format("0x%08x", rde.getCodePage()));
	}

	private void dumpSectionTable(List<SectionTableEntry> sectionTable)
	{
		logger.log(Level.FINE, "Section Table @ {0}",
			String.format("0x%08x", sectionTablePos));
		for (SectionTableEntry entry : sectionTable)
		{
			logger.log(Level.FINE, "Section Table Entry: {0}", entry.getName());
			logger.log(Level.FINE, " * Size: {0}",
				String.format("0x%08x", entry.getVirtualSize()));
			logger.log(Level.FINE, " * Address: {0}",
				String.format("0x%08x", entry.getVirtualAddress()));
			logger.log(Level.FINE, " * Size of Raw Data: {0}",
				String.format("0x%08x", entry.getSizeOfRawData()));
			logger.log(Level.FINE, " * Ptr to Raw Data: {0}",
				String.format("0x%08x", entry.getPtrToRawData()));
			logger.log(Level.FINE, " * Ptr to Relocations: {0}",
				String.format("0x%08x", entry.getPtrToRelocations()));
			logger.log(Level.FINE, " * Ptr to Line Numbers: {0}",
				String.format("0x%08x", entry.getPtrToLineNumbers()));
			logger.log(Level.FINE, " * Relocations Count: {0}",
				entry.getRelocationsCount());
			logger.log(Level.FINE, " * Line Numbers Count: {0}",
				entry.getLineNumbersCount());
			logger.log(Level.FINE, " * Characteristics: {0}",
				String.format("0x%08x", entry.getCharacteristics()));
		}
	}

	private void dumpTlsSection(TlsSection tls)
	{
		logger.log(Level.FINE, "Section: {0}", tls.getName());
		logger.log(Level.FINE, " * Raw Data Start VA: {0}",
			String.format("0x%016x", tls.getRawDataStartVA()));
		logger.log(Level.FINE, " * Raw Data End VA: {0}",
			String.format("0x%016x", tls.getRawDataEndVA()));
		logger.log(Level.FINE, " * Address of Index: {0}",
			String.format("0x%016x", tls.getAddressOfIndex()));
		logger.log(Level.FINE, " * Address of Callbacks: {0}",
			String.format("0x%016x", tls.getAddressOfCallbacks()));
		logger.log(Level.FINE, " * Size of Zero Fill: {0}",
			String.format("0x%08x", tls.getSizeOfZeroFill()));
		logger.log(Level.FINE, " * Characteristics: {0}",
			String.format("0x%08x", tls.getCharacteristics()));
	}

}
