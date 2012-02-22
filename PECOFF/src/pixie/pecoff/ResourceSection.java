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
public class ResourceSection implements Section
{
	private List<ResourceDirectoryTable> directoryTables =
		new ArrayList<ResourceDirectoryTable>();

	@Override
	public String getName()
	{
		return Section.RESOURCE;
	}

	/**
	 * @return the directoryTables
	 */
	public List<ResourceDirectoryTable> getDirectoryTables() {
		return directoryTables;
	}

	/**
	 * @param directoryTables the directoryTables to set
	 */
	public void setDirectoryTables(List<ResourceDirectoryTable> directoryTables) {
		this.directoryTables = directoryTables;
	}

}
