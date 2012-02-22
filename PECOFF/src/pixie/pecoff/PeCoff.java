/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pixie.pecoff;

import java.util.ArrayList;
import java.util.List;

/**
 * $Id$
 *
 * @author adminjamesd
 */
public class PeCoff
{
	public static final short PE32 = 0x010b;
	public static final short PE32PLUS = 0x020b;

	private byte[] stub;
	private CoffHeader coffHdr;
	private PeCoffOptionalHeader optHdr;
	private List<SectionTableEntry> sectionTable =
		new ArrayList<SectionTableEntry>();

	public byte[] getStub()
	{
		return stub;
	}

	public CoffHeader getCoffHeader()
	{
		return coffHdr;
	}

	public PeCoffOptionalHeader getPeCoffOptionalHeader()
	{
		return optHdr;
	}

	public void setCoffHeader(CoffHeader coffHeader)
	{
		this.coffHdr = coffHeader;
	}

	public void setPeCoffOptionalHeader(PeCoffOptionalHeader optHdr)
	{
		this.optHdr = optHdr;
	}

	public void setStub(byte[] stub)
	{
		this.stub = new byte[stub.length];
		System.arraycopy(stub, 0, this.stub, 0, stub.length);
	}

	/**
	 * @return the sectionTable
	 */
	public List<SectionTableEntry> getSectionTable()
	{
		return sectionTable;
	}

	/**
	 * @param sectionTable the sectionTable to set
	 */
	public void setSectionTable(List<SectionTableEntry> sectionTable)
	{
		this.sectionTable = sectionTable;
	}

}
