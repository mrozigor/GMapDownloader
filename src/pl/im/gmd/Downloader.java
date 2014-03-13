/**
 * 
 */
package pl.im.gmd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

/**
 * @author Igor
 * 
 */
public class Downloader extends Thread {

	private MainWindow mainWindow;
	private Settings settings;
	private int minimumX;
	private int maximumX;
	private int minimumY;
	private int maximumY;
	private int numberOfTiles;
	private boolean resumeFailedDownload = false;
	private Tile[] tilesToDownload = null;
	private ExecutorService downloadExecutor = null;
	private int numberOfTilesDownloadedSoFar = 0;

	public Downloader(MainWindow mainWindow, Settings settings) {
		this.mainWindow = mainWindow;
		this.settings = settings;
	}

	public void startDownload() {
		start();
	}

	@Override
	public void run() {
		createTilesToDownloadList();
		download();
	}

	private void download() {
		mainWindow.writeMessage("Starting download.");
		final int MAXIMUM_DOWNLOAD_AT_TIME = 4;
		downloadExecutor = Executors
				.newFixedThreadPool(MAXIMUM_DOWNLOAD_AT_TIME);
		for (int temp = 0; temp < tilesToDownload.length; ++temp) {
			if (tilesToDownload[temp].isDownloaded() == false) {
				downloadExecutor.execute(tilesToDownload[temp]);
			}
		}
		downloadExecutor.shutdown();
		while (!downloadExecutor.isTerminated()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "InterruptedException during downloading.", "InterruptedException",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		resumeFailedDownload = false;
		tilesToDownload = null;
		mainWindow.cancelDownloadButton.setVisible(false);
		mainWindow.startDownloadButton.setVisible(true);
		mainWindow.saveDirectoryButton.setVisible(true);
	}

	private void createTilesToDownloadList() {
		if (resumeFailedDownload) {
			return;
		}
		Tile[] temp = new Tile[numberOfTiles];
		int index = 0;
		for (int x = minimumX; x <= maximumX; ++x) {
			for (int y = minimumY; y <= maximumY; ++y) {
				temp[index] = new Tile(x, y, mainWindow, this);
				++index;
			}
		}
		tilesToDownload = temp;
	}

	private int calculateNumberOfTiles() {
		if (resumeFailedDownload) {
			return calculateTilesToDownloadIfDownloadWasFailed();
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

	private int calculateTilesToDownloadIfDownloadWasFailed() {
		int counter = 0;
		for (int temp = 0; temp < tilesToDownload.length; ++temp) {
			if (tilesToDownload[temp] != null) {
				++counter;
			}
		}
		return counter;
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
		downloadExecutor.shutdownNow();
		try {
			Thread.sleep(10000);	//Waiting if active download threads are ending.
			mainWindow.writeMessage("Wait 10s to complete the process.");
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null, "Interrupted Exception during canceling download.", "InterruptedException",
					JOptionPane.ERROR_MESSAGE);
		}
		try {
			File file = new File(settings.getSaveDirectory() + File.separator
					+ "MissedElements");
			ObjectOutputStream stream = new ObjectOutputStream(
					new FileOutputStream(file));
			stream.writeObject(tilesToDownload);
			stream.close();
			resumeFailedDownload = false;
			tilesToDownload = null;
		} catch (IOException error) {
			JOptionPane.showMessageDialog(null, "IOException.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public synchronized String getNumberOfDownloadedTilesSoFar() {
		++numberOfTilesDownloadedSoFar;
		String temp = "(" + numberOfTilesDownloadedSoFar + "/" + numberOfTiles + ")";
		return temp;
	}
}
