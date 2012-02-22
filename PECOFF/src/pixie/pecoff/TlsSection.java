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
public class TlsSection implements Section
{
	private long rawDataStartVA;
	private long rawDataEndVA;
	private long addressOfIndex;
	private long addressOfCallbacks;
	private int sizeOfZeroFill;
	private int characteristics;

	@Override
	public String getName()
	{
		return Section.TLS;
	}

	/**
	 * @return the addressOfCallbacks
	 */
	public long getAddressOfCallbacks() {
		return addressOfCallbacks;
	}

	/**
	 * @return the addressOfIndex
	 */
	public long getAddressOfIndex() {
		return addressOfIndex;
	}

	/**
	 * @return the characteristics
	 */
	public int getCharacteristics() {
		return characteristics;
	}

	/**
	 * @return the rawDataEndVA
	 */
	public long getRawDataEndVA() {
		return rawDataEndVA;
	}

	/**
	 * @return the rawDataStartVA
	 */
	public long getRawDataStartVA() {
		return rawDataStartVA;
	}

	/**
	 * @return the sizeOfZeroFill
	 */
	public int getSizeOfZeroFill() {
		return sizeOfZeroFill;
	}

	/**
	 * @param addressOfCallbacks the addressOfCallbacks to set
	 */
	public void setAddressOfCallbacks(long addressOfCallbacks) {
		this.addressOfCallbacks = addressOfCallbacks;
	}

	/**
	 * @param addressOfIndex the addressOfIndex to set
	 */
	public void setAddressOfIndex(long addressOfIndex) {
		this.addressOfIndex = addressOfIndex;
	}

	/**
	 * @param characteristics the characteristics to set
	 */
	public void setCharacteristics(int characteristics) {
		this.characteristics = characteristics;
	}

	/**
	 * @param rawDataEndVA the rawDataEndVA to set
	 */
	public void setRawDataEndVA(long rawDataEndVA) {
		this.rawDataEndVA = rawDataEndVA;
	}

	/**
	 * @param rawDataStartVA the rawDataStartVA to set
	 */
	public void setRawDataStartVA(long rawDataStartVA) {
		this.rawDataStartVA = rawDataStartVA;
	}

	/**
	 * @param sizeOfZeroFill the sizeOfZeroFill to set
	 */
	public void setSizeOfZeroFill(int sizeOfZeroFill) {
		this.sizeOfZeroFill = sizeOfZeroFill;
	}

}
