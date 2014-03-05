/**
 * 
 */
package pl.im.gmd;

import java.io.Serializable;

/**
 * @author Igor
 *
 */
public class Tile implements Serializable {

	private int valueX;
	private int valueY;
	
	public Tile(int valueX, int valueY) {
		this.valueX = valueX;
		this.valueY = valueY;
	}

	public int getValueX() {
		return valueX;
	}

	public int getValueY() {
		return valueY;
	}
}
