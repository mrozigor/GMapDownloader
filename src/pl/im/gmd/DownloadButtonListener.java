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
public class DownloadButtonListener implements ActionListener {
	
	private MainWindow mainWindow;
	private Downloader downloader;
	
	public DownloadButtonListener(MainWindow window) {
		mainWindow = window;
	}

	@Override
	public void actionPerformed(ActionEvent arg) {
		try {
			getCoordinatesFromTextAreasAndSave();
			mainWindow.getSettings().checkAllOptionsAreSelected();
			if(mainWindow.getSettings().displayInformationWindow() == JOptionPane.YES_OPTION) {
				downloader = new Downloader(mainWindow, mainWindow.getSettings());
				downloader.displayTilesInformationWindow();
				downloader.startDownload();
			}
		} catch(NumberFormatException error) {
			JOptionPane.showMessageDialog(null, "Please enter correct number.", "Wrong number format", JOptionPane.INFORMATION_MESSAGE);
		} catch (WrongCoordinatesException error) {
			JOptionPane.showMessageDialog(null, error.getMessage(), "Wrong coordinates", JOptionPane.INFORMATION_MESSAGE);
		} catch(Exception error) {
			JOptionPane.showMessageDialog(null, error.getMessage(), "Missing settings", JOptionPane.ERROR_MESSAGE);
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
