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
public class SectionTableEntry
{
	private String name;
	private int virtualSize;
	private int virtualAddress;
	private int sizeOfRawData;
	private int ptrToRawData;
	private int ptrToRelocations;
	private int ptrToLineNumbers;
	private short relocationsCount;
	private short lineNumbersCount;
	private int characteristics;

	/**
	 * @return the virtualAddress
	 */
	public int getVirtualAddress() {
		return virtualAddress;
	}

	/**
	 * @return the characteristics
	 */
	public int getCharacteristics() {
		return characteristics;
	}

	/**
	 * @return the lineNumbersCount
	 */
	public short getLineNumbersCount() {
		return lineNumbersCount;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the ptrToLineNumbers
	 */
	public int getPtrToLineNumbers() {
		return ptrToLineNumbers;
	}

	/**
	 * @return the ptrToRawData
	 */
	public int getPtrToRawData() {
		return ptrToRawData;
	}

	/**
	 * @return the ptrToRelocations
	 */
	public int getPtrToRelocations() {
		return ptrToRelocations;
	}

	/**
	 * @return the relocationsCount
	 */
	public short getRelocationsCount() {
		return relocationsCount;
	}

	/**
	 * @return the virtualSize
	 */
	public int getVirtualSize() {
		return virtualSize;
	}

	/**
	 * @return the sizeOfRawData
	 */
	public int getSizeOfRawData() {
		return sizeOfRawData;
	}

	/**
	 * @param virtualAddress the virtualAddress to set
	 */
	public void setVirtualAddress(int address) {
		this.virtualAddress = address;
	}

	/**
	 * @param characteristics the characteristics to set
	 */
	public void setCharacteristics(int characteristics) {
		this.characteristics = characteristics;
	}

	/**
	 * @param lineNumbersCount the lineNumbersCount to set
	 */
	public void setLineNumbersCount(short lineNumbersCount) {
		this.lineNumbersCount = lineNumbersCount;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param ptrToLineNumbers the ptrToLineNumbers to set
	 */
	public void setPtrToLineNumbers(int ptrToLineNumbers) {
		this.ptrToLineNumbers = ptrToLineNumbers;
	}

	/**
	 * @param ptrToRawData the ptrToRawData to set
	 */
	public void setPtrToRawData(int ptrToRawData) {
		this.ptrToRawData = ptrToRawData;
	}

	/**
	 * @param ptrToRelocations the ptrToRelocations to set
	 */
	public void setPtrToRelocations(int ptrToRelocations) {
		this.ptrToRelocations = ptrToRelocations;
	}

	/**
	 * @param relocationsCount the relocationsCount to set
	 */
	public void setRelocationsCount(short relocationsCount) {
		this.relocationsCount = relocationsCount;
	}

	/**
	 * @param virtualSize the virtualSize to set
	 */
	public void setVirtualSize(int size) {
		this.virtualSize = size;
	}

	/**
	 * @param sizeOfRawData the sizeOfRawData to set
	 */
	public void setSizeOfRawData(int sizeOfRawData) {
		this.sizeOfRawData = sizeOfRawData;
	}
	
}
