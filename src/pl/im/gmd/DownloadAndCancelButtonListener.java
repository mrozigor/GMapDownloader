/**
 * 
 */
package pl.im.gmd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * @author Igor
 * 
 */
public class DownloadAndCancelButtonListener extends Thread implements ActionListener {

	private MainWindow mainWindow;
	private Downloader downloader;

	public DownloadAndCancelButtonListener(MainWindow window) {
		mainWindow = window;
	}

	@Override
	public void actionPerformed(ActionEvent arg) {
		if (arg.getSource() == mainWindow.startDownloadButton) {
			try {
				getCoordinatesFromTextAreasAndSave();
				mainWindow.getSettings().checkAllOptionsAreSelected();
				if (mainWindow.getSettings().displayInformationWindow() == JOptionPane.YES_OPTION) {
					swapButtons();
					downloader = new Downloader(mainWindow,
							mainWindow.getSettings());
					downloader.checkIfLastDownloadWasNotCompletedSuccessfully();
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
		} else if (arg.getSource() == mainWindow.cancelDownloadButton) {
			if (downloader != null && mainWindow.cancelDownloadButton.isVisible()) {
				downloader.cancelDownload();
			}
			mainWindow.cancelDownloadButton.setVisible(false);
			mainWindow.startDownloadButton.setVisible(true);
			mainWindow.saveDirectoryButton.setVisible(true);
		}
	}

	private void swapButtons() {
		mainWindow.startDownloadButton.setVisible(false);
		mainWindow.saveDirectoryButton.setVisible(false);
		mainWindow.cancelDownloadButton.setVisible(true);
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
