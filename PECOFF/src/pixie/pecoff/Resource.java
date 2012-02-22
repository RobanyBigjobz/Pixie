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
public class Resource
{
	public static final int ICON = 0x00000003;
	public static final int GROUP_ICON = 0x0000000e;

	private int type;
	private int name;
	private int language;
	private byte[] rawData;
	private int dataRVA;
	private int dataSize;

	/**
	 * @return the dataRVA
	 */
	public int getDataRVA() {
		return dataRVA;
	}

	/**
	 * @return the dataSize
	 */
	public int getDataSize() {
		return dataSize;
	}

	/**
	 * @return the language
	 */
	public int getLanguage() {
		return language;
	}

	/**
	 * @return the name
	 */
	public int getName() {
		return name;
	}

	/**
	 * @return the rawData
	 */
	public byte[] getRawData() {
		return rawData;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param dataRVA the dataRVA to set
	 */
	public void setDataRVA(int dataRVA) {
		this.dataRVA = dataRVA;
	}

	/**
	 * @param dataSize the dataSize to set
	 */
	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(int language) {
		this.language = language;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(int name) {
		this.name = name;
	}

	/**
	 * @param rawData the rawData to set
	 */
	public void setRawData(byte[] rawData) {
		this.rawData = rawData;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

}
