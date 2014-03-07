/**
 * 
 */
package pl.im.gmd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JOptionPane;

/**
 * @author Igor
 * 
 */
public class Downloader {

	private MainWindow mainWindow;
	private Settings settings;
	private int minimumX;
	private int maximumX;
	private int minimumY;
	private int maximumY;
	private int numberOfTiles;
	private boolean resumeFailedDownload = false;
	private Tile[] tilesToDownload = null;

	public Downloader(MainWindow mainWindow, Settings settings) {
		this.mainWindow = mainWindow;
		this.settings = settings;
	}

	public void startDownload() {
		createTilesToDownloadList();
		// TODO Downloading normal and resume incomplete request. File name
		// "MissedElements". After successfully downloading,
		// resumeFailedDownload
		// flag should be false. If download wasn't successfully,
		// "MissedElements" file should be created at application exit. Nulls
		// from tilesToDownload should be omitted.
	}

	private void createTilesToDownloadList() {
		if (resumeFailedDownload) {
			return;
		}
		Tile[] temp = new Tile[numberOfTiles];
		int index = 0;
		for (int x = minimumX; x <= maximumX; ++x) {
			for (int y = minimumY; y <= maximumY; ++y) {
				temp[index] = new Tile(x, y);
				++index;
			}
		}
		tilesToDownload = temp;
	}

	private int calculateNumberOfTiles() {
		if (resumeFailedDownload) {
			return (tilesToDownload.length);
		}
		Coordinates coordinates = settings.getCoordinates();
		int zoom = settings.getZoom();
		// Tiles X grow eastern, while tiles Y grows southern
		minimumX = calculateTileX(coordinates.getBorderW(), zoom);
		maximumX = calculateTileX(coordinates.getBorderE(), zoom);
		maximumY = calculateTileY(coordinates.getBorderS(), zoom);
		minimumY = calculateTileY(coordinates.getBorderN(), zoom);
		return (((maximumX - minimumX) + 1) * ((maximumY - minimumY) + 1));
	}

	private int calculateTileX(double longitude, int zoom) {
		int result = (int) (((longitude * Math.pow(2, zoom)) / 360) + Math.pow(
				2, (zoom - 1)));
		return result;
	}

	private int calculateTileY(double latitude, int zoom) {
		int result = (int) (Math.pow(2, (zoom - 1)) - ((Math.log(Math
				.tan((Math.PI * (latitude + 90.0)) / 360.0)) * Math
				.pow(2, zoom)) / (2 * Math.PI)));
		return result;
	}

	public void displayTilesInformationWindow() {
		numberOfTiles = calculateNumberOfTiles();
		String message = "Total " + numberOfTiles + " tiles to download.";
		JOptionPane.showMessageDialog(null, message,
				"Tiles Information Window", JOptionPane.INFORMATION_MESSAGE);
	}

	public void checkIfLastDownloadWasNotCompletedSuccessfully() {
		File file = new File(settings.getSaveDirectory() + File.separator
				+ "MissedElements");
		if (file.isFile()) {
			try {
				ObjectInputStream stream = new ObjectInputStream(
						new FileInputStream(file));
				tilesToDownload = (Tile[]) stream.readObject();
				stream.close();
				displayMessage();
				resumeFailedDownload = true;
				deleteFile(file);
			} catch (ClassNotFoundException error) {
				JOptionPane.showMessageDialog(null, "ClassNotFoundException.",
						"Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException error) {
				JOptionPane.showMessageDialog(null, "IOException.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void deleteFile(File file) {
		file.delete();
	}

	private void displayMessage() {
		String message = "You're going to download missing tiles.";
		JOptionPane.showMessageDialog(null, message, "Resume latest download",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void cancelDownload() {
		//TODO Implement cancel download functions.
	}
}
