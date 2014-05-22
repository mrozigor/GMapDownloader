/**
 * 
 */
package pl.im.gmd.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import pl.im.gmd.view.MainWindow;

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
	private List<Tile> tilesToDownload = null;
	private ExecutorService downloadExecutor = null;
	private int numberOfTilesDownloadedSoFar = 0;
	private final int MAXIMUM_DOWNLOAD_AT_TIME = 4;
	private ProxyServerManager proxyServerManager = null;
	

	public Downloader(MainWindow mainWindow) throws WrongProxyServerFileStructureException, FileNotFoundException, IOException {
		this.mainWindow = mainWindow;
		this.settings = mainWindow.getSettings();
		this.proxyServerManager = new ProxyServerManager(settings.getProxyServerListFilePath());
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
		downloadExecutor = Executors
				.newFixedThreadPool(MAXIMUM_DOWNLOAD_AT_TIME);
		for (Tile tile : tilesToDownload) {
			downloadExecutor.execute(tile);
		}
		downloadExecutor.shutdown();
		while (!downloadExecutor.isTerminated()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null,
						"InterruptedException during downloading.",
						"InterruptedException", JOptionPane.ERROR_MESSAGE);
			}
		}
		tilesToDownload = null;
		mainWindow.setButtonsInInitialConfiguration();
		mainWindow.writeMessage("Download completed!");
	}

	private void createTilesToDownloadList() {
		if (tilesToDownload != null) {
			return;
		}
		tilesToDownload = new ArrayList<Tile>();
		for (int x = minimumX; x <= maximumX; ++x) {
			for (int y = minimumY; y <= maximumY; ++y) {
				tilesToDownload.add(new Tile(x, y, mainWindow, this));
			}
		}
		numberOfTiles = tilesToDownload.size();
	}

	private int calculateNumberOfTiles() {
		if (tilesToDownload != null) {
			return calculateTilesToDownloadIfResumingDownload();
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

	private int calculateTilesToDownloadIfResumingDownload() {
		int counter = tilesToDownload.size();
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

	public synchronized String getNumberOfDownloadedTilesSoFar() {
		++numberOfTilesDownloadedSoFar;
		String temp = "(" + numberOfTilesDownloadedSoFar + "/" + numberOfTiles
				+ ")";
		return temp;
	}
	
	public ProxyServerManager getProxyServerManager() {
		return proxyServerManager;
		
	}
	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public void setTilesToDownload(List<Tile> tiles) {
		tilesToDownload = tiles;
	}

	public void cancelDownload() {
		downloadExecutor.shutdownNow();
		try {
			while (!downloadExecutor.awaitTermination(5, TimeUnit.SECONDS));
			mainWindow.writeMessage("Download canceled.");
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null,
					"Interrupted Exception during canceling download.",
					"InterruptedException", JOptionPane.ERROR_MESSAGE);
		}
		try {
			removeDownloadedTilesFromList();
			writeConfigurationToFile();
			tilesToDownload = null;
		} catch (IOException error) {
			JOptionPane.showMessageDialog(null, "IOException.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void removeDownloadedTilesFromList() {
		Iterator<Tile> iterator = tilesToDownload.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().isDownloaded() == true) {
				iterator.remove();
			}
		}
	}

	private void writeConfigurationToFile() throws IOException {
		File file = new File(settings.getSaveDirectory() + File.separator
				+ "MissedElements");
		ObjectOutputStream stream = new ObjectOutputStream(
				new FileOutputStream(file));
		stream.writeObject(settings);
		stream.writeObject(tilesToDownload);
		stream.close();
	}

	public int displayInformationWindow() {
		Coordinates coordinates = settings.getCoordinates();
		String message = "Your settings:\n" + "- N/S Borders - "
				+ coordinates.getBorderN() + " : " + coordinates.getBorderS()
				+ "\n" + "- W/E Borders - " + coordinates.getBorderW() + " : "
				+ coordinates.getBorderE() + "\n" + "- download type - "
				+ settings.getDownloadType() + "\n" + "- zoom - "
				+ settings.getZoom() + "\n" + "- download path - "
				+ settings.getSaveDirectory() + "\n" + "- tiles number - "
				+ calculateNumberOfTiles() + "\n\n" + "Do you want to proceed?";
		String[] buttons = { "Yes", "No" };
		int answer = JOptionPane.showOptionDialog(null, message,
				"Confirm your settings", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
		return answer;
	}
}
