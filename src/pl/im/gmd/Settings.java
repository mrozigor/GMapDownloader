/**
 * 
 */
package pl.im.gmd;

import javax.swing.JOptionPane;

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
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public void checkAllOptionsAreSelected() throws Exception {
		if (coordinates == null) {
			throw new Exception("Please enter coordinates.");
		} else if (downloadType == null) {
			throw new Exception("Please select download type.");
		} else if (saveDirectory == null) {
			throw new Exception("Please select download directory.");
		}
	}

	public int displayInformationWindow() {
		String message = "Your settings:\n" + "- N/S Borders - "
				+ coordinates.getBorderN() + " : " + coordinates.getBorderS()
				+ "\n" + "- W/E Borders - " + coordinates.getBorderW() + " : "
				+ coordinates.getBorderE() + "\n" + "- download type - "
				+ downloadType + "\n" + "- zoom - " + zoom + "\n"
				+ "- download path - " + saveDirectory + "\n\n"
				+ "Do you want to proceed?";
		String[] buttons = { "Yes", "No" };
		int answer = JOptionPane.showOptionDialog(null, message,
				"Confirm your settings", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
		return answer;
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
	
	public String getDownloadType() {
		return downloadType;
	}
}
