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
public class ResourceDirectoryEntry
{
	// Four int fields but on disk format two ints: nameRVA/integerID,
	// dataEntryRVA/subDirectoryRVA
	private static final int byteCount = 8;

	private int nameRVA;
	private int integerID;
	private int dataEntryRVA;
	private int subDirectoryRVA;
	private ResourceDataEntry rde;
	private ResourceDirectoryTable rdt;

	public static int getByteCount()
	{
		return byteCount;
	}

	/**
	 * @return the dataEntryRVA
	 */
	public ResourceDataEntry getDataEntry() {
		return rde;
	}

	/**
	 * @return the dataEntryRVA
	 */
	public int getDataEntryRVA() {
		return dataEntryRVA;
	}

	/**
	 * @return the integerID
	 */
	public int getIntegerID() {
		return integerID;
	}

	public boolean isIdEntry()
	{
		return integerID != 0;
	}

	public boolean isLeaf()
	{
		return dataEntryRVA != 0;
	}

	/**
	 * @return the nameRVA
	 */
	public int getNameRVA() {
		return nameRVA;
	}

	/**
	 * @return the subDirectoryRVA
	 */
	public int getSubDirectoryRVA() {
		return subDirectoryRVA;
	}

	/**
	 * @return the subDirectoryRVA
	 */
	public ResourceDirectoryTable getSubDirectoryTable() {
		return rdt;
	}

	/**
	 * @param dataEntryRVA the dataEntryRVA to set
	 */
	public void setDataEntryRVA(int dataEntryRVA)
	{
		// Set only lower 31 bits
		this.dataEntryRVA = (dataEntryRVA & 0x7fffffff);
		subDirectoryRVA = 0;
	}

	/**
	 * @param dataEntry the dataEntry to set
	 */
	public void setDataEntry(ResourceDataEntry rde)
	{
		this.rde = rde;
	}

	/**
	 * @param integerID the integerID to set
	 */
	public void setIntegerID(int integerID)
	{
		this.integerID = integerID;
		nameRVA = 0;
	}

	/**
	 * @param nameRVA the nameRVA to set
	 */
	public void setNameRVA(int nameRVA)
	{
		this.nameRVA = nameRVA;
		integerID = 0;
	}

	/**
	 * @param subDirectoryRVA the subDirectoryRVA to set
	 */
	public void setSubDirectoryRVA(int subDirectoryRVA)
	{
		// Set only lower 31 bits
		this.subDirectoryRVA = (subDirectoryRVA & 0x7fffffff);
		dataEntryRVA = 0;
	}

	/**
	 * @param subDirectoryTable the subDirectoryRVA to set
	 */
	public void setSubDirectoryTable(ResourceDirectoryTable rdt)
	{
		this.rdt = rdt;
	}
}
