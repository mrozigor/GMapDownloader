/**
 * 
 */
package pl.im.gmd.model;



/**
 * @author Igor
 * 
 */
public class Settings {
	
	private MapTypes downloadType = null;
	private int zoom = 1;
	private String saveDirectory = null;
	private Coordinates coordinates = null;
	private String proxyServerListFilePath = "none";

	/**
	 * @param type
	 *            Type of download. Two available are "map" and "satellite".
	 */
	public void setDownloadType(MapTypes type) {
		downloadType = type;
	}

	public void setZoomLevel(int zoom) {
		this.zoom = zoom;
	}

	public void setSaveDirectory(String directory) {
		saveDirectory = directory;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	
	public void setProxyServerListFilePath(String serverListFilePath) {
		proxyServerListFilePath = serverListFilePath;
	}

	public void checkAllOptionsAreSelected() throws MissingSettingException {
		if (coordinates == null) {
			throw new MissingSettingException("Please enter coordinates.");
		} else if (downloadType == null) {
			throw new MissingSettingException("Please select download type.");
		} else if (saveDirectory == null) {
			throw new MissingSettingException("Please select download directory.");
		}
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}
	
	public int getZoom() {
		return zoom;
	}
	
	public String getSaveDirectory() {
		return saveDirectory;
	}
	
	public MapTypes getDownloadType() {
		return downloadType;
	}
	
	public String getProxyServerListFilePath() {
		return proxyServerListFilePath;
	}
}
