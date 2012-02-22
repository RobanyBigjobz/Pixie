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
public class CoffHeader implements Cloneable
{
	private short machine;
	private short sectionCount;
	private int timeStamp;
	private int ptrToSymTable;
	private int nSymbols;
	private short optHdrSize;
	private short characteristics;

	@Override
	public CoffHeader clone()
	{
		CoffHeader clone = new CoffHeader();
		clone.machine = machine;
		clone.sectionCount = sectionCount;
		clone.timeStamp = timeStamp;
		clone.ptrToSymTable = ptrToSymTable;
		clone.nSymbols = nSymbols;
		clone.optHdrSize = optHdrSize;
		clone.characteristics = characteristics;
		return clone;
	}

	/**
	 * @return the characteristics
	 */
	public short getCharacteristics()
	{
		return characteristics;
	}

	/**
	 * @return the machine
	 */
	public short getMachine()
	{
		return machine;
	}

	/**
	 * @return the optHdrSize
	 */
	public short getOptHdrSize()
	{
		return optHdrSize;
	}

	/**
	 * @return the ptrToSymTable
	 */
	public int getPtrToSymTable()
	{
		return ptrToSymTable;
	}

	/**
	 * @return the timeStamp
	 */
	public int getTimeStamp()
	{
		return timeStamp;
	}

	/**
	 * @return the sectionCount
	 */
	public short getSectionCount() 
	{
		return sectionCount;
	}

	/**
	 * @return the nSymbols
	 */
	public int getSymbolCount()
	{
		return nSymbols;
	}

	/**
	 * @param characteristics the characteristics to set
	 */
	public void setCharacteristics(short flags)
	{
		this.characteristics = flags;
	}

	/**
	 * @param machine the machine to set
	 */
	public void setMachine(short machine)
	{
		this.machine = machine;
	}

	/**
	 * @param optHdrSize the optHdrSize to set
	 */
	public void setOptionalHeaderSize(short optHdrSize)
	{
		this.optHdrSize = optHdrSize;
	}

	/**
	 * @param ptrToSymTable the ptrToSymTable to set
	 */
	public void setPtrToSymTable(int ptrToSymTable)
	{
		this.ptrToSymTable = ptrToSymTable;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(int timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	/**
	 * @param sectionCount the sectionCount to set
	 */
	public void setSectionCount(short nSections)
	{
		this.sectionCount = nSections;
	}

	/**
	 * @param nSymbols the nSymbols to set
	 */
	public void setSymbolCount(int nSymbols)
	{
		this.nSymbols = nSymbols;
	}

	
}
