/**
 * 
 */
package pl.im.gmd.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

/**
 * @author Igor
 * 
 */
public class ProxyWindowButtonsListener implements ActionListener {

	private ProxyWindow proxyMenuWindow;

	public ProxyWindowButtonsListener(ProxyWindow proxyMenuWindow) {
		this.proxyMenuWindow = proxyMenuWindow;
	}

	@Override
	public void actionPerformed(ActionEvent arg) {
		if (arg.getSource() == proxyMenuWindow.getSelectPathButton()) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Choose proxy list file");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = fileChooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String directory = fileChooser.getSelectedFile().toString();
				proxyMenuWindow.getPathTextArea().setText(directory);
				proxyMenuWindow.getCurrentPathLabel().setText(
						"Current path: " + directory);
			}
		} else if (arg.getSource() == proxyMenuWindow.getResetButton()) {
			proxyMenuWindow.getSettings().setProxyServerListFilePath("none");
			proxyMenuWindow.getPathTextArea().setText("none");
			proxyMenuWindow.getCurrentPathLabel().setText("Current path: none");
		} else if (arg.getSource() == proxyMenuWindow.getApplyButton()) {
			// TODO Validate inserted path - is there valid file, if not exception and don't save to settings
			String path = proxyMenuWindow.getPathTextArea().getText();
			proxyMenuWindow.getSettings().setProxyServerListFilePath(path);
			proxyMenuWindow.dispose();
		}
	}
}
