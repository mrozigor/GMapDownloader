/**
 * 
 */
package pl.im.gmd;

/**
 * @author Igor
 *
 */
public enum MapTypes {
	MAP("map"),
	TERRAIN("terrain map"),
	SATELLITE("satellite");
	
	private String name;
	
	private MapTypes(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
