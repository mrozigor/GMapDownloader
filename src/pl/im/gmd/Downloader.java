/**
 * 
 */
package pl.im.gmd;

import javax.swing.JOptionPane;

/**
 * @author Igor
 *
 */
public class Downloader {

	private MainWindow mainWindow;
	private Settings settings;
	
	public Downloader(MainWindow mainWindow, Settings settings) {
		this.mainWindow = mainWindow;
		this.settings = settings;
	}

	public void startDownload() {
		//TODO Downloading
	}

	private int calculateNumberOfTiles() {
		Coordinates coordinates = settings.getCoordinates();
		int zoom = settings.getZoom();
		//Tiles X grow eastern, while tiles Y grows southern
		int minimumX = calculateTileX(coordinates.getBorderW(), zoom);
		int maximumX = calculateTileX(coordinates.getBorderE(), zoom);
		int maximumY = calculateTileY(coordinates.getBorderS(), zoom);
		int minimumY = calculateTileY(coordinates.getBorderN(), zoom);
		return (((maximumX - minimumX) + 1) * ((maximumY - minimumY) + 1));
	}
	
	private int calculateTileX(double longitude, int zoom) {
		int result = (int)(((longitude * Math.pow(2, zoom)) / 360) + Math.pow(2, (zoom - 1)));
		return result;
	}
	
	private int calculateTileY(double latitude, int zoom) {
		int result = (int)(Math.pow(2, (zoom - 1)) - ((Math.log(Math.tan((Math.PI * (latitude + 90.0)) / 360.0)) * Math.pow(2, zoom)) / (2 * Math.PI)));
		return result;
	}
	
	public void displayTilesInformationWindow() {
		int numberOfTiles = calculateNumberOfTiles();
		String message = "Total " + numberOfTiles + " tiles to download.";
		JOptionPane.showMessageDialog(null, message, "Tiles Information Window", JOptionPane.INFORMATION_MESSAGE);
	}
}
