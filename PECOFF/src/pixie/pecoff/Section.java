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
public interface Section
{
	public static final String TLS = ".tls";
	public static final String RESOURCE = ".rsrc";

	public String getName();
}
