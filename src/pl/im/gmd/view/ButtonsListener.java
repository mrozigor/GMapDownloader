/**
 * 
 */
package pl.im.gmd.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import pl.im.gmd.model.Coordinates;
import pl.im.gmd.model.Downloader;
import pl.im.gmd.model.MissingSettingException;
import pl.im.gmd.model.Settings;
import pl.im.gmd.model.Tile;
import pl.im.gmd.model.WrongCoordinatesException;
import pl.im.gmd.model.WrongProxyServerFileStructureException;

/**
 * @author Igor
 * 
 */
public class ButtonsListener extends Thread implements ActionListener {

	private MainWindow mainWindow;
	private Settings settings;
	private Downloader downloader;

	public ButtonsListener(MainWindow window) {
		mainWindow = window;
		this.settings = mainWindow.getSettings();
	}

	@Override
	public void actionPerformed(ActionEvent arg) {
		if (arg.getSource() == mainWindow.getStartDownloadButton()) {
			try {
				getCoordinatesFromTextAreasAndSave();
				mainWindow.clearMessageArea();
				downloader = new Downloader(mainWindow);
				if (checkIfLastDownloadWasNotCompletedSuccessfully()) {
					if (checkIfUserWantsResumeDownloading() == JOptionPane.YES_OPTION) {
						swapSettings();
					}
				}
				mainWindow.getSettings().checkAllOptionsAreSelected();
				if (downloader.displayInformationWindow() == JOptionPane.YES_OPTION) {
					mainWindow.setButtonsInSwapedConfiguration();
					downloader.startDownload();
				}
			} catch (FileNotFoundException error) {
				JOptionPane.showMessageDialog(null, error.getMessage(),
						"File not found", JOptionPane.ERROR_MESSAGE);
			} catch (WrongProxyServerFileStructureException error) {
				JOptionPane.showMessageDialog(null, error.getMessage(),
						"Wrong file structure", JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException error) {
				JOptionPane.showMessageDialog(null,
						"Please enter correct number.", "Wrong number format",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (WrongCoordinatesException error) {
				JOptionPane.showMessageDialog(null, error.getMessage(),
						"Wrong coordinates", JOptionPane.INFORMATION_MESSAGE);
			} catch (MissingSettingException error) {
				JOptionPane.showMessageDialog(null, error.getMessage(),
						"Missing settings", JOptionPane.ERROR_MESSAGE);
			} catch (IOException error) {
				JOptionPane.showMessageDialog(null, error.getMessage(),
						"General IOException", JOptionPane.ERROR_MESSAGE);
			}
		} else if (arg.getSource() == mainWindow.getCancelDownloadButton()) {
			if (downloader != null
					&& mainWindow.getCancelDownloadButton().isVisible()) {
				downloader.cancelDownload();
				mainWindow.setButtonsInInitialConfiguration();
			}
		} else if (arg.getSource() == mainWindow.getSaveDirectoryButton()) {
			JFileChooser directoryChooser = new JFileChooser();
			directoryChooser.setDialogTitle("Choose folder");
			directoryChooser
					.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = directoryChooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String directory = directoryChooser.getSelectedFile()
						.toString();
				settings.setSaveDirectory(directory);
			}
		} else if (arg.getSource() == mainWindow.getProxySettingsMenu()) {
			new ProxyWindow(settings).setVisible(true);
		} else if (arg.getSource() == mainWindow.getHelpMenu()) {
			// TODO Implement help window
		} else if (arg.getSource() == mainWindow.getAboutMenu()) {
			JOptionPane.showMessageDialog(null,
					"GMap Downloader\nAuthor: Igor Mroz\n\nA.D. 2014", "About",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void swapSettings() {
		try {
			File file = new File(settings.getSaveDirectory() + File.separator
					+ "MissedElements");
			ObjectInputStream stream = new ObjectInputStream(
					new FileInputStream(file));
			Settings tempSettings = (Settings) stream.readObject();
			List<Tile> tilesToDownload = (ArrayList<Tile>) stream.readObject();
			settings = tempSettings;
			mainWindow.setSettings(tempSettings);
			downloader.setTilesToDownload(tilesToDownload);
			stream.close();
			file.delete();
		} catch (ClassNotFoundException error) {
			JOptionPane.showMessageDialog(null, "ClassNotFoundException.",
					"Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException error) {
			JOptionPane.showMessageDialog(null, "IOException.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean checkIfLastDownloadWasNotCompletedSuccessfully() {
		File file = new File(settings.getSaveDirectory() + File.separator
				+ "MissedElements");
		if (file.isFile()) {
			return true;
		}
		return false;
	}

	private int checkIfUserWantsResumeDownloading() {
		String[] buttons = { "Yes", "No" };
		int answer = JOptionPane.showOptionDialog(null,
				"Do you want to resume downloading?", "Information",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				buttons, buttons[0]);
		return answer;
	}

	void getCoordinatesFromTextAreasAndSave() throws WrongCoordinatesException {
		Coordinates coordinates = new Coordinates();
		coordinates.setBorderN(mainWindow.getNBorderText());
		coordinates.setBorderS(mainWindow.getSBorderText());
		coordinates.setBorderW(mainWindow.getWBorderText());
		coordinates.setBorderE(mainWindow.getEBorderText());
		mainWindow.getSettings().setCoordinates(coordinates);
	}
}
