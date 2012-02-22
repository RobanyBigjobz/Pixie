/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pixie.pecoff;

/**
 * $Id$
 *
 * @author adminjamesd
 */
public class PeCoffOptionalHeader
{
	private boolean isPlus = false;
	private short magic;
	private byte majorLinkerVersion;
	private byte minorLinkerVersion;
	private int sizeOfCode;
	private int sizeOfInitialisedData;
	private int sizeOfUnintialisedData;
	private int addressOfEntryPoint;
	private int baseOfCode;
	private int baseOfData;

	// Windows-specific
	private long imageBase;
	private int sectionAlignment;
	private int fileAlignment;
	private short majorOSVersion;
	private short minorOSVersion;
	private short majorImageVersion;
	private short minorImageVersion;
	private short majorSubsystemVersion;
	private short minorSubsystemVersion;
	private int win32VersionValue;
	private int sizeOfImage;
	private int sizeOfHeaders;
	private int checkSum;
	private short subsystem;
	private short dllCharacteristics;
	private long sizeOfStackReserve;
	private long sizeOfStackCommit;
	private long sizeOfHeapReserve;
	private long sizeOfHeapCommit;
	private int loaderFlags;
	private int numberOfRvaAndSizes;
	private int exportTableAddress;
	private int exportTableSize;
	private int importTableAddress;
	private int importTableSize;
	private int resourceTableAddress;
	private int resourceTableSize;
	private int exceptionTableAddress;
	private int exceptionTableSize;
	private int certTableAddress;
	private int certTableSize;
	private int baseRelocTableAddress;
	private int baseRelocTableSize;
	private int debugAddress;
	private int debugSize;
	private long architecture;
	private int globalPtrAddress;
	private int globalPtrSize;
	private int tlsTableAddress;
	private int tlsTableSize;
	private int loadConfigTableAddress;
	private int loadConfigTableSize;
	private int boundImportTableAddress;
	private int boundImportTableSize;
	private int iaTableAddress;
	private int iaTableSize;
	private int delayImportDescAddress;
	private int delayImportDescSize;
	private int clrRuntimeHeaderAddress;
	private int clrRuntimeHeaderSize;
	private long reserved;

	/**
	 * @return the addressOfEntryPoint
	 */
	public int getAddressOfEntryPoint()
	{
		return addressOfEntryPoint;
	}

	/**
	 * @return the baseOfCode
	 */
	public int getBaseOfCode()
	{
		return baseOfCode;
	}

	/**
	 * @return the baseOfData
	 */
	public int getBaseOfData()
	{
		return baseOfData;
	}

	/**
	 * @return the fileAlignment
	 */
	public int getFileAlignment()
	{
		return fileAlignment;
	}

	/**
	 * @return the imageBase
	 */
	public long getImageBase()
	{
		return imageBase;
	}

	/**
	 * @return the magic
	 */
	public short getMagic()
	{
		return magic;
	}

	/**
	 * @return the majorImageVersion
	 */
	public short getMajorImageVersion()
	{
		return majorImageVersion;
	}

	/**
	 * @return the majorLinkerVersion
	 */
	public byte getMajorLinkerVersion()
	{
		return majorLinkerVersion;
	}

	/**
	 * @return the majorOSVersion
	 */
	public short getMajorOSVersion()
	{
		return majorOSVersion;
	}

	/**
	 * @return the minorImageVersion
	 */
	public short getMinorImageVersion()
	{
		return minorImageVersion;
	}

	/**
	 * @return the minorLinkerVersion
	 */
	public byte getMinorLinkerVersion()
	{
		return minorLinkerVersion;
	}

	/**
	 * @return the minorOSVersion
	 */
	public short getMinorOSVersion()
	{
		return minorOSVersion;
	}

	/**
	 * @return the sectionAlignment
	 */
	public int getSectionAlignment()
	{
		return sectionAlignment;
	}

	/**
	 * @return the sizeOfCode
	 */
	public int getSizeOfCode()
	{
		return sizeOfCode;
	}

	/**
	 * @return the sizeOfInitialisedData
	 */
	public int getSizeOfInitialisedData()
	{
		return sizeOfInitialisedData;
	}

	/**
	 * @return the sizeOfUnintialisedData
	 */
	public int getSizeOfUnintialisedData()
	{
		return sizeOfUnintialisedData;
	}

	/**
	 * @return the PE32+ status
	 */
	public boolean isPlus()
	{
		return isPlus;
	}

	/**
	 * @param addressOfEntryPoint the addressOfEntryPoint to set
	 */
	public void setAddressOfEntryPoint(int addressOfEntryPoint)
	{
		this.addressOfEntryPoint = addressOfEntryPoint;
	}

	/**
	 * @param baseOfCode the baseOfCode to set
	 */
	public void setBaseOfCode(int baseOfCode)
	{
		this.baseOfCode = baseOfCode;
	}

	/**
	 * @param baseOfData the baseOfData to set
	 */
	public void setBaseOfData(int baseOfData)
	{
		this.baseOfData = baseOfData;
	}

	/**
	 * @param fileAlignment the fileAlignment to set
	 */
	public void setFileAlignment(int fileAlignment)
	{
		this.fileAlignment = fileAlignment;
	}

	/**
	 * @param imageBase the imageBase to set
	 */
	public void setImageBase(long imageBase)
	{
		this.imageBase = imageBase;
	}

	/**
	 * @param magic the magic to set
	 */
	public void setMagic(short magic)
	{
		if ((magic != PeCoff.PE32) &&
			 (magic != PeCoff.PE32PLUS))
		{
			throw new IllegalArgumentException("Bad magic ("+
				String.format("0x%04x")+") must be 0x010b or 0x020b");
		}
		this.magic = magic;
		isPlus = (magic == PeCoff.PE32PLUS);
	}

	/**
	 * @param majorImageVersion the majorImageVersion to set
	 */
	public void setMajorImageVersion(short majorImageVersion)
	{
		this.majorImageVersion = majorImageVersion;
	}

	/**
	 * @param majorLinkerVersion the majorLinkerVersion to set
	 */
	public void setMajorLinkerVersion(byte majorLinkerVersion)
	{
		this.majorLinkerVersion = majorLinkerVersion;
	}

	/**
	 * @param majorOSVersion the majorOSVersion to set
	 */
	public void setMajorOSVersion(short majorOSVersion)
	{
		this.majorOSVersion = majorOSVersion;
	}

	/**
	 * @param minorImageVersion the minorImageVersion to set
	 */
	public void setMinorImageVersion(short minorImageVersion)
	{
		this.minorImageVersion = minorImageVersion;
	}

	/**
	 * @param minorLinkerVersion the minorLinkerVersion to set
	 */
	public void setMinorLinkerVersion(byte minorLinkerVersion)
	{
		this.minorLinkerVersion = minorLinkerVersion;
	}

	/**
	 * @param minorOSVersion the minorOSVersion to set
	 */
	public void setMinorOSVersion(short minorOSVersion)
	{
		this.minorOSVersion = minorOSVersion;
	}

	/**
	 * @param sectionAlignment the sectionAlignment to set
	 */
	public void setSectionAlignment(int sectionAlignment) {
		this.sectionAlignment = sectionAlignment;
	}
	
	/**
	 * @param sizeOfCode the sizeOfCode to set
	 */
	public void setSizeOfCode(int sizeOfCode)
	{
		this.sizeOfCode = sizeOfCode;
	}

	/**
	 * @param sizeOfInitialisedData the sizeOfInitialisedData to set
	 */
	public void setSizeOfInitialisedData(int sizeOfInitialisedData)
	{
		this.sizeOfInitialisedData = sizeOfInitialisedData;
	}

	/**
	 * @param sizeOfUnintialisedData the sizeOfUnintialisedData to set
	 */
	public void setSizeOfUnintialisedData(int sizeOfUnintialisedData)
	{
		this.sizeOfUnintialisedData = sizeOfUnintialisedData;
	}

	/**
	 * @return the checkSum
	 */
	public int getCheckSum() {
		return checkSum;
	}

	/**
	 * @return the majorSubsystemVersion
	 */
	public short getMajorSubsystemVersion() {
		return majorSubsystemVersion;
	}

	/**
	 * @return the minorSubsystemVersion
	 */
	public short getMinorSubsystemVersion() {
		return minorSubsystemVersion;
	}

	/**
	 * @return the sizeOfHeaders
	 */
	public int getSizeOfHeaders() {
		return sizeOfHeaders;
	}

	/**
	 * @return the sizeOfImage
	 */
	public int getSizeOfImage() {
		return sizeOfImage;
	}

	/**
	 * @return the win32VersionValue
	 */
	public int getWin32VersionValue() {
		return win32VersionValue;
	}

	/**
	 * @param checkSum the checkSum to set
	 */
	public void setCheckSum(int checkSum) {
		this.checkSum = checkSum;
	}

	/**
	 * @param majorSubsystemVersion the majorSubsystemVersion to set
	 */
	public void setMajorSubsystemVersion(short majorSubsystemVersion) {
		this.majorSubsystemVersion = majorSubsystemVersion;
	}

	/**
	 * @param minorSubsystemVersion the minorSubsystemVersion to set
	 */
	public void setMinorSubsystemVersion(short minorSubsystemVersion) {
		this.minorSubsystemVersion = minorSubsystemVersion;
	}

	/**
	 * @param sizeOfHeaders the sizeOfHeaders to set
	 */
	public void setSizeOfHeaders(int sizeOfHeaders) {
		this.sizeOfHeaders = sizeOfHeaders;
	}

	/**
	 * @param sizeOfImage the sizeOfImage to set
	 */
	public void setSizeOfImage(int sizeOfImage) {
		this.sizeOfImage = sizeOfImage;
	}

	/**
	 * @param win32VersionValue the win32VersionValue to set
	 */
	public void setWin32VersionValue(int win32VersionValue) {
		this.win32VersionValue = win32VersionValue;
	}

	/**
	 * @return the dllCharacteristics
	 */
	public short getDllCharacteristics() {
		return dllCharacteristics;
	}

	/**
	 * @return the loaderFlags
	 */
	public int getLoaderFlags() {
		return loaderFlags;
	}

	/**
	 * @return the numberOfRvaAndSizes
	 */
	public int getNumberOfRvaAndSizes() {
		return numberOfRvaAndSizes;
	}

	/**
	 * @return the sizeOfHeapCommit
	 */
	public long getSizeOfHeapCommit() {
		return sizeOfHeapCommit;
	}

	/**
	 * @return the sizeOfHeapReserve
	 */
	public long getSizeOfHeapReserve() {
		return sizeOfHeapReserve;
	}

	/**
	 * @return the sizeOfStackCommit
	 */
	public long getSizeOfStackCommit() {
		return sizeOfStackCommit;
	}

	/**
	 * @return the sizeOfStackReserve
	 */
	public long getSizeOfStackReserve() {
		return sizeOfStackReserve;
	}

	/**
	 * @return the subsystem
	 */
	public short getSubsystem() {
		return subsystem;
	}

	/**
	 * @param dllCharacteristics the dllCharacteristics to set
	 */
	public void setDllCharacteristics(short dllCharacteristics) {
		this.dllCharacteristics = dllCharacteristics;
	}

	/**
	 * @param loaderFlags the loaderFlags to set
	 */
	public void setLoaderFlags(int loaderFlags) {
		this.loaderFlags = loaderFlags;
	}

	/**
	 * @param numberOfRvaAndSizes the numberOfRvaAndSizes to set
	 */
	public void setNumberOfRvaAndSizes(int numberOfRvaAndSizes) {
		this.numberOfRvaAndSizes = numberOfRvaAndSizes;
	}

	/**
	 * @param sizeOfHeapCommit the sizeOfHeapCommit to set
	 */
	public void setSizeOfHeapCommit(long sizeOfHeapCommit) {
		this.sizeOfHeapCommit = sizeOfHeapCommit;
	}

	/**
	 * @param sizeOfHeapReserve the sizeOfHeapReserve to set
	 */
	public void setSizeOfHeapReserve(long sizeOfHeapReserve) {
		this.sizeOfHeapReserve = sizeOfHeapReserve;
	}

	/**
	 * @param sizeOfStackCommit the sizeOfStackCommit to set
	 */
	public void setSizeOfStackCommit(long sizeOfStackCommit) {
		this.sizeOfStackCommit = sizeOfStackCommit;
	}

	/**
	 * @param sizeOfStackReserve the sizeOfStackReserve to set
	 */
	public void setSizeOfStackReserve(long sizeOfStackReserve) {
		this.sizeOfStackReserve = sizeOfStackReserve;
	}

	/**
	 * @param subsystem the subsystem to set
	 */
	public void setSubsystem(short subsystem) {
		this.subsystem = subsystem;
	}

	/**
	 * @return the architecture
	 */
	public long getArchitecture() {
		return architecture;
	}

	/**
	 * @return the baseRelocTableAddress
	 */
	public int getBaseRelocTableAddress() {
		return baseRelocTableAddress;
	}

	/**
	 * @return the baseRelocTableSize
	 */
	public int getBaseRelocTableSize() {
		return baseRelocTableSize;
	}

	/**
	 * @return the boundImportTableAddress
	 */
	public int getBoundImportTableAddress() {
		return boundImportTableAddress;
	}

	/**
	 * @return the boundImportTableSize
	 */
	public int getBoundImportTableSize() {
		return boundImportTableSize;
	}

	/**
	 * @return the certTableAddress
	 */
	public int getCertTableAddress() {
		return certTableAddress;
	}

	/**
	 * @return the certTableSize
	 */
	public int getCertTableSize() {
		return certTableSize;
	}

	/**
	 * @return the clrRuntimeHeaderAddress
	 */
	public int getClrRuntimeHeaderAddress() {
		return clrRuntimeHeaderAddress;
	}

	/**
	 * @return the clrRuntimeHeaderSize
	 */
	public int getClrRuntimeHeaderSize() {
		return clrRuntimeHeaderSize;
	}

	/**
	 * @return the debugAddress
	 */
	public int getDebugAddress() {
		return debugAddress;
	}

	/**
	 * @return the debugSize
	 */
	public int getDebugSize() {
		return debugSize;
	}

	/**
	 * @return the delayImportDescAddress
	 */
	public int getDelayImportDescAddress() {
		return delayImportDescAddress;
	}

	/**
	 * @return the delayImportDescSize
	 */
	public int getDelayImportDescSize() {
		return delayImportDescSize;
	}

	/**
	 * @return the exceptionTableAddress
	 */
	public int getExceptionTableAddress() {
		return exceptionTableAddress;
	}

	/**
	 * @return the exceptionTableSize
	 */
	public int getExceptionTableSize() {
		return exceptionTableSize;
	}

	/**
	 * @return the exportTableAddress
	 */
	public int getExportTableAddress() {
		return exportTableAddress;
	}

	/**
	 * @return the exportTableSize
	 */
	public int getExportTableSize() {
		return exportTableSize;
	}

	/**
	 * @return the globalPtrAddress
	 */
	public int getGlobalPtrAddress() {
		return globalPtrAddress;
	}

	/**
	 * @return the globalPtrSize
	 */
	public int getGlobalPtrSize() {
		return globalPtrSize;
	}

	/**
	 * @return the iaTableAddress
	 */
	public int getIaTableAddress() {
		return iaTableAddress;
	}

	/**
	 * @return the iaTableSize
	 */
	public int getIaTableSize() {
		return iaTableSize;
	}

	/**
	 * @return the importTableAddress
	 */
	public int getImportTableAddress() {
		return importTableAddress;
	}

	/**
	 * @return the importTableSize
	 */
	public int getImportTableSize() {
		return importTableSize;
	}

	/**
	 * @return the loadConfigTableAddress
	 */
	public int getLoadConfigTableAddress() {
		return loadConfigTableAddress;
	}

	/**
	 * @return the loadConfigTableSize
	 */
	public int getLoadConfigTableSize() {
		return loadConfigTableSize;
	}

	/**
	 * @return the reserved
	 */
	public long getReserved() {
		return reserved;
	}

	/**
	 * @return the resourceTableAddress
	 */
	public int getResourceTableAddress() {
		return resourceTableAddress;
	}

	/**
	 * @return the resourceTableSize
	 */
	public int getResourceTableSize() {
		return resourceTableSize;
	}

	/**
	 * @return the tlsTableAddress
	 */
	public int getTlsTableAddress() {
		return tlsTableAddress;
	}

	/**
	 * @return the tlsTableSize
	 */
	public int getTlsTableSize() {
		return tlsTableSize;
	}

	/**
	 * @param architecture the architecture to set
	 */
	public void setArchitecture(long architecture) {
		this.architecture = architecture;
	}

	/**
	 * @param baseRelocTableAddress the baseRelocTableAddress to set
	 */
	public void setBaseRelocTableAddress(int baseRelocTableAddress) {
		this.baseRelocTableAddress = baseRelocTableAddress;
	}

	/**
	 * @param baseRelocTableSize the baseRelocTableSize to set
	 */
	public void setBaseRelocTableSize(int baseRelocTableSize) {
		this.baseRelocTableSize = baseRelocTableSize;
	}

	/**
	 * @param boundImportTableAddress the boundImportTableAddress to set
	 */
	public void setBoundImportTableAddress(int boundImportTableAddress) {
		this.boundImportTableAddress = boundImportTableAddress;
	}

	/**
	 * @param boundImportTableSize the boundImportTableSize to set
	 */
	public void setBoundImportTableSize(int boundImportTableSize) {
		this.boundImportTableSize = boundImportTableSize;
	}

	/**
	 * @param certTableAddress the certTableAddress to set
	 */
	public void setCertTableAddress(int certTableAddress) {
		this.certTableAddress = certTableAddress;
	}

	/**
	 * @param certTableSize the certTableSize to set
	 */
	public void setCertTableSize(int certTableSize) {
		this.certTableSize = certTableSize;
	}

	/**
	 * @param clrRuntimeHeaderAddress the clrRuntimeHeaderAddress to set
	 */
	public void setClrRuntimeHeaderAddress(int clrRuntimeHeaderAddress) {
		this.clrRuntimeHeaderAddress = clrRuntimeHeaderAddress;
	}

	/**
	 * @param clrRuntimeHeaderSize the clrRuntimeHeaderSize to set
	 */
	public void setClrRuntimeHeaderSize(int clrRuntimeHeaderSize) {
		this.clrRuntimeHeaderSize = clrRuntimeHeaderSize;
	}

	/**
	 * @param debugAddress the debugAddress to set
	 */
	public void setDebugAddress(int debugAddress) {
		this.debugAddress = debugAddress;
	}

	/**
	 * @param debugSize the debugSize to set
	 */
	public void setDebugSize(int debugSize) {
		this.debugSize = debugSize;
	}

	/**
	 * @param delayImportDescAddress the delayImportDescAddress to set
	 */
	public void setDelayImportDescAddress(int delayImportDescAddress) {
		this.delayImportDescAddress = delayImportDescAddress;
	}

	/**
	 * @param delayImportDescSize the delayImportDescSize to set
	 */
	public void setDelayImportDescSize(int delayImportDescSize) {
		this.delayImportDescSize = delayImportDescSize;
	}

	/**
	 * @param exceptionTableAddress the exceptionTableAddress to set
	 */
	public void setExceptionTableAddress(int exceptionTableAddress) {
		this.exceptionTableAddress = exceptionTableAddress;
	}

	/**
	 * @param exceptionTableSize the exceptionTableSize to set
	 */
	public void setExceptionTableSize(int exceptionTableSize) {
		this.exceptionTableSize = exceptionTableSize;
	}

	/**
	 * @param exportTableAddress the exportTableAddress to set
	 */
	public void setExportTableAddress(int exportTableAddress) {
		this.exportTableAddress = exportTableAddress;
	}

	/**
	 * @param exportTableSize the exportTableSize to set
	 */
	public void setExportTableSize(int exportTableSize) {
		this.exportTableSize = exportTableSize;
	}

	/**
	 * @param globalPtrAddress the globalPtrAddress to set
	 */
	public void setGlobalPtrAddress(int globalPtrAddress) {
		this.globalPtrAddress = globalPtrAddress;
	}

	/**
	 * @param globalPtrSize the globalPtrSize to set
	 */
	public void setGlobalPtrSize(int globalPtrSize) {
		this.globalPtrSize = globalPtrSize;
	}

	/**
	 * @param iaTableAddress the iaTableAddress to set
	 */
	public void setIaTableAddress(int iaTableAddress) {
		this.iaTableAddress = iaTableAddress;
	}

	/**
	 * @param iaTableSize the iaTableSize to set
	 */
	public void setIaTableSize(int iaTableSize) {
		this.iaTableSize = iaTableSize;
	}

	/**
	 * @param importTableAddress the importTableAddress to set
	 */
	public void setImportTableAddress(int importTableAddress) {
		this.importTableAddress = importTableAddress;
	}

	/**
	 * @param importTableSize the importTableSize to set
	 */
	public void setImportTableSize(int importTableSize) {
		this.importTableSize = importTableSize;
	}

	/**
	 * @param loadConfigTableAddress the loadConfigTableAddress to set
	 */
	public void setLoadConfigTableAddress(int loadConfigTableAddress) {
		this.loadConfigTableAddress = loadConfigTableAddress;
	}

	/**
	 * @param loadConfigTableSize the loadConfigTableSize to set
	 */
	public void setLoadConfigTableSize(int loadConfigTableSize) {
		this.loadConfigTableSize = loadConfigTableSize;
	}

	/**
	 * @param reserved the reserved to set
	 */
	public void setReserved(long reserved) {
		this.reserved = reserved;
	}

	/**
	 * @param resourceTableAddress the resourceTableAddress to set
	 */
	public void setResourceTableAddress(int resourceTableAddress) {
		this.resourceTableAddress = resourceTableAddress;
	}

	/**
	 * @param resourceTableSize the resourceTableSize to set
	 */
	public void setResourceTableSize(int resourceTableSize) {
		this.resourceTableSize = resourceTableSize;
	}

	/**
	 * @param tlsTableAddress the tlsTableAddress to set
	 */
	public void setTlsTableAddress(int tlsTableAddress) {
		this.tlsTableAddress = tlsTableAddress;
	}

	/**
	 * @param tlsTableSize the tlsTableSize to set
	 */
	public void setTlsTableSize(int tlsTableSize) {
		this.tlsTableSize = tlsTableSize;
	}

}
