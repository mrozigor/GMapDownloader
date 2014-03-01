/**
 * 
 */
package pl.im.gmd;

/**
 * @author Igor
 * 
 */
public class Settings {

	private MainWindow mainWindow = null;
	private String downloadType = null;
	private int zoom = 1;
	private String saveDirectory = null;
	private Coordinates coordinates = null;

	public Settings(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	/**
	 * @param type
	 *            Type of download. Two available are "map" and "satellite".
	 */
	public void setDownloadType(String type) {
		downloadType = type;
	}

	public void setZoomLevel(int zoom) {
		this.zoom = zoom;
	}

	public void setSaveDirectory(String directory) {
		saveDirectory = directory;
		System.out.println(directory);
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
}
