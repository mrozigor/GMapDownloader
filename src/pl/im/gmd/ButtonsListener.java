/**
 * 
 */
package pl.im.gmd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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

	// TODO Refactoring stoped in this method
	@Override
	public void actionPerformed(ActionEvent arg) {
		if (arg.getSource() == mainWindow.getStartDownloadButton()) {
			try {
				getCoordinatesFromTextAreasAndSave();
				downloader.checkIfLastDownloadWasNotCompletedSuccessfully();
				mainWindow.getSettings().checkAllOptionsAreSelected();
				if (mainWindow.getSettings().displayInformationWindow() == JOptionPane.YES_OPTION) {
					mainWindow.setButtonsInSwapedConfiguration();
					downloader = new Downloader(mainWindow,
							mainWindow.getSettings());
					downloader.displayTilesInformationWindow();
					mainWindow.clearMessageArea();
					downloader.startDownload();
				}
			} catch (NumberFormatException error) {
				JOptionPane.showMessageDialog(null,
						"Please enter correct number.", "Wrong number format",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (WrongCoordinatesException error) {
				JOptionPane.showMessageDialog(null, error.getMessage(),
						"Wrong coordinates", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception error) {
				JOptionPane.showMessageDialog(null, error.getMessage(),
						"Missing settings", JOptionPane.ERROR_MESSAGE);
			}
		} else if (arg.getSource() == mainWindow.getCancelDownloadButton()) {
			if (downloader != null && mainWindow.getCancelDownloadButton().isVisible()) {
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
		}
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
