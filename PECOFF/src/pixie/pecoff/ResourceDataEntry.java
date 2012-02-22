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
public class ResourceDataEntry
{
	private int dataRVA;
	private int size;
	private int codePage;
	private int reserved;

	/**
	 * @return the codePage
	 */
	public int getCodePage() {
		return codePage;
	}

	/**
	 * @return the dataRVA
	 */
	public int getDataRVA() {
		return dataRVA;
	}

	/**
	 * @return the reserved
	 */
	public int getReserved() {
		return reserved;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param codePage the codePage to set
	 */
	public void setCodePage(int codePage) {
		this.codePage = codePage;
	}

	/**
	 * @param dataRVA the dataRVA to set
	 */
	public void setDataRVA(int dataRVA) {
		this.dataRVA = dataRVA;
	}

	/**
	 * @param reserved the reserved to set
	 */
	public void setReserved(int reserved) {
		this.reserved = reserved;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
}
