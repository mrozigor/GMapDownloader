/**
 * 
 */
package pl.im.gmd.model;

/**
 * @author Igor
 *
 */
public enum MapTypes {
	MAP("map"),
	TERRAIN("terrain"),
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
