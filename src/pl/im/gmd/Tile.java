/**
 * 
 */
package pl.im.gmd;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * @author Igor
 * 
 */
public class Tile extends Thread implements Serializable {

	private int valueX;
	private int valueY;
	private MainWindow mainWindow;
	private Downloader downloader;
	private boolean isDownloaded = false;

	public Tile(int valueX, int valueY, MainWindow mainWindow,
			Downloader downloader) {
		this.valueX = valueX;
		this.valueY = valueY;
		this.mainWindow = mainWindow;
		this.downloader = downloader;
	}

	public int getValueX() {
		return valueX;
	}

	public int getValueY() {
		return valueY;
	}

	@Override
	public String toString() {
		String temp = "X: " + valueX + " Y: " + valueY;
		return temp;
	}

	@Override
	public void run() {
		download();
	}

	private void download() {
		try {
			String url = generateUrl(mainWindow.getSettings());
			URL connection = new URL(url);
			BufferedInputStream input = new BufferedInputStream(
					connection.openStream());
			byte[] tab = new byte[20000];
			int temp;
			int i = 0;
			while ((temp = input.read()) != -1) {
				tab[i] = (byte) temp;
				++i;
			}
			input.close();
			String filePath = generateFilePath(mainWindow.getSettings());
			FileOutputStream file = new FileOutputStream(filePath);
			file.write(tab);
			file.close();
			Thread.sleep(randomSleep());
			isDownloaded = true;
			mainWindow.writeMessage("Tile " + this
					+ " was downloaded successfully "
					+ downloader.getNumberOfDownloadedTilesSoFar());
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(null, "MalformedURLException.",
					"MalformedURLException", JOptionPane.ERROR_MESSAGE);
		} catch (FileNotFoundException error) {
			mainWindow.writeMessage("Tile " + this
					+ " isn't available at this zoom.");
			InputStream input = getClass().getResourceAsStream(
					"tileNotAvailable.png");
			try {
				saveTileAsNotAvailable(input);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"IOException when saving not available tile..",
						"IOException", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e) {
			mainWindow.writeMessage("Tile " + this
					+ " wasn't downloaded successfully. Error 400.");
		} catch (InterruptedException e) {
			mainWindow
					.writeMessage("InterruptedException during downloading tile "
							+ this);
		}
	}

	private void saveTileAsNotAvailable(InputStream input) throws IOException {
		BufferedInputStream inpt = new BufferedInputStream(input);
		byte[] tab = new byte[20000];
		int temp;
		int i = 0;
		while ((temp = inpt.read()) != -1) {
			tab[i] = (byte) temp;
			++i;
		}
		inpt.close();
		input.close();
		String filePath = generateFilePath(mainWindow.getSettings());
		FileOutputStream file = new FileOutputStream(filePath);
		file.write(tab);
		file.close();
	}

	private long randomSleep() {
		Random generator = new Random();
		long time = generator.nextInt(5000);
		time += 1000;
		return time;
	}

	private String generateFilePath(Settings settings) {
		String temp = settings.getSaveDirectory() + File.separator + "tile_"
				+ valueX + "_" + valueY + "_z" + settings.getZoom() + "_"
				+ settings.getDownloadType() + ".png";
		return temp;
	}

	private String generateUrl(Settings settings) {
		String temp = null;
		if (settings.getDownloadType() == "map") {
			temp = "https://" + randomServer("map")
					+ ".google.com/vt/lyrs=h@249000000&hl=pl&src=app&x="
					+ valueX + "&y=" + valueY + "&z=" + settings.getZoom()
					+ "&s=" + generateGalileoSubstr();
		} else if (settings.getDownloadType() == "satellite") {
			temp = "http://" + randomServer("satellite")
					+ ".google.com/kh/v=145&src=app&x=" + valueX + "&y="
					+ valueY + "&z=" + settings.getZoom() + "&s="
					+ generateGalileoSubstr();
		}
		return temp;
	}

	private String generateGalileoSubstr() {
		String word = "Galileo";
		int len = (3 * valueX + valueY) % 8;
		String substr = word.substring(0, len);
		return substr;
	}

	private String randomServer(String downloadType) {
		String temp = null;
		Random generator = new Random();
		int serverNumber = generator.nextInt(4);
		if (downloadType == "map") {
			temp = "mts" + serverNumber;
		} else if (downloadType == "satellite") {
			temp = "khms" + serverNumber;
		}
		return temp;
	}

	public boolean isDownloaded() {
		return isDownloaded;
	}
}
