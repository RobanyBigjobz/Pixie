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
public class ResourceDirectoryTable
{
	private static final int byteCount = 16;

	private int characteristics;
	private int timeStamp;
	private short majorVersion;
	private short minorVersion;
	private short nameEntryCount;
	private short idEntryCount;
	private List<ResourceDirectoryEntry> nameEntries =
		new ArrayList<ResourceDirectoryEntry>();
	private List<ResourceDirectoryEntry> idEntries =
		new ArrayList<ResourceDirectoryEntry>();

	public int getByteCount()
	{
		return byteCount;
	}

	/**
	 * @return the characteristics
	 */
	public int getCharacteristics() {
		return characteristics;
	}

	/**
	 * @return the idEntryCount
	 */
	public short getIdEntryCount() {
		return idEntryCount;
	}

	/**
	 * @return the majorVersion
	 */
	public short getMajorVersion() {
		return majorVersion;
	}

	/**
	 * @return the minorVersion
	 */
	public short getMinorVersion() {
		return minorVersion;
	}

	/**
	 * @return the nameEntryCount
	 */
	public short getNameEntryCount() {
		return nameEntryCount;
	}

	/**
	 * @return the timeStamp
	 */
	public int getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param characteristics the characteristics to set
	 */
	public void setCharacteristics(int characteristics) {
		this.characteristics = characteristics;
	}

	/**
	 * @param idEntryCount the idEntryCount to set
	 */
	public void setIdEntryCount(short idEntryCount) {
		this.idEntryCount = idEntryCount;
	}

	/**
	 * @param majorVersion the majorVersion to set
	 */
	public void setMajorVersion(short majorVersion) {
		this.majorVersion = majorVersion;
	}

	/**
	 * @param minorVersion the minorVersion to set
	 */
	public void setMinorVersion(short minorVersion) {
		this.minorVersion = minorVersion;
	}

	/**
	 * @param nameEntryCount the nameEntryCount to set
	 */
	public void setNameEntryCount(short nameEntryCount) {
		this.nameEntryCount = nameEntryCount;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * @return the idEntries
	 */
	public List<ResourceDirectoryEntry> getIdEntries() {
		return idEntries;
	}

	/**
	 * @return the nameEntries
	 */
	public List<ResourceDirectoryEntry> getNameEntries() {
		return nameEntries;
	}

	/**
	 * @param idEntries the idEntries to set
	 */
	public void setIdEntries(List<ResourceDirectoryEntry> idEntries) {
		this.idEntries = idEntries;
	}

	/**
	 * @param nameEntries the nameEntries to set
	 */
	public void setNameEntries(List<ResourceDirectoryEntry> nameEntries) {
		this.nameEntries = nameEntries;
	}

}
