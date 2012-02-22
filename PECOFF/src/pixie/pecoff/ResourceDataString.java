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
public class ResourceDataString
{
	private short length;
	private String dataString;

	/**
	 * @return the dataString
	 */
	public String getDataString() {
		return dataString;
	}

	/**
	 * @return the length
	 */
	public short getLength() {
		return length;
	}

	/**
	 * @param dataString the dataString to set
	 */
	public void setDataString(String dataString) {
		this.dataString = dataString;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(short length) {
		this.length = length;
	}
}
