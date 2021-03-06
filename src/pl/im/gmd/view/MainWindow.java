package pl.im.gmd.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JTextField;

import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import pl.im.gmd.model.MapTypes;
import pl.im.gmd.model.Settings;
import pl.im.gmd.model.ZoomLevel;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 8438936978792718404L;
	private JPanel contentPane;
	private JTextField nBorderText;
	private JTextField sBorderText;
	private JTextField wBorderText;
	private JTextField eBorderText;
	private JLabel zoomLabel;
	private JComboBox<ZoomLevel> zoomComboBox;
	private JButton saveDirectoryButton;
	private JButton startDownloadButton;
	private JButton cancelDownloadButton;
	private JTextPane messageArea;
	private JScrollPane scrollPane;
	private JLabel mapTypeLabel;
	private JRadioButton mapRadioButton;
	private JRadioButton satelliteRadioButton;
	private Settings settings;
	private JRadioButton terrainMapRadioButton;
	private JMenuBar menuBar;
	private JMenu optionsMenu;
	private JMenu hlpMenu;
	private JMenuItem proxySettingsMenu;
	private JMenuItem helpMenu;
	private JMenuItem aboutMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				cancelDownloadButton.doClick();
			}
		});
		
		settings = new Settings();
		ButtonGroup group = new ButtonGroup();
		ButtonsListener buttonsListener = new ButtonsListener(this);
		
		setTitle("GMap Downloader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		optionsMenu = new JMenu("Options");
		menuBar.add(optionsMenu);
		
		proxySettingsMenu = new JMenuItem("Proxy Settings");
		optionsMenu.add(proxySettingsMenu);
		proxySettingsMenu.addActionListener(buttonsListener);
		
		hlpMenu = new JMenu("Help");
		menuBar.add(hlpMenu);
		
		helpMenu = new JMenuItem("Help");
		hlpMenu.add(helpMenu);
		helpMenu.addActionListener(buttonsListener);
		
		aboutMenu = new JMenuItem("About");
		hlpMenu.add(aboutMenu);
		aboutMenu.addActionListener(buttonsListener);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 65, 50, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 1.0,
				0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel nBorderLabel = new JLabel("Northern Border");
		nBorderLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_nBorderLabel = new GridBagConstraints();
		gbc_nBorderLabel.fill = GridBagConstraints.VERTICAL;
		gbc_nBorderLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nBorderLabel.anchor = GridBagConstraints.WEST;
		gbc_nBorderLabel.gridx = 0;
		gbc_nBorderLabel.gridy = 0;
		contentPane.add(nBorderLabel, gbc_nBorderLabel);

		nBorderText = new JTextField();
		nBorderText.setText("0");
		GridBagConstraints gbc_nBorderText = new GridBagConstraints();
		gbc_nBorderText.insets = new Insets(0, 0, 5, 5);
		gbc_nBorderText.fill = GridBagConstraints.HORIZONTAL;
		gbc_nBorderText.gridx = 1;
		gbc_nBorderText.gridy = 0;
		contentPane.add(nBorderText, gbc_nBorderText);
		nBorderText.setColumns(10);

		zoomLabel = new JLabel("Zoom");
		GridBagConstraints gbc_zoomLabel = new GridBagConstraints();
		gbc_zoomLabel.gridwidth = 3;
		gbc_zoomLabel.insets = new Insets(0, 0, 5, 0);
		gbc_zoomLabel.gridx = 3;
		gbc_zoomLabel.gridy = 0;
		contentPane.add(zoomLabel, gbc_zoomLabel);

		JLabel sBorderLabel = new JLabel("Southern Border");
		sBorderLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_sBorderLabel = new GridBagConstraints();
		gbc_sBorderLabel.fill = GridBagConstraints.VERTICAL;
		gbc_sBorderLabel.anchor = GridBagConstraints.WEST;
		gbc_sBorderLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sBorderLabel.gridx = 0;
		gbc_sBorderLabel.gridy = 1;
		contentPane.add(sBorderLabel, gbc_sBorderLabel);

		sBorderText = new JTextField();
		sBorderText.setText("0");
		GridBagConstraints gbc_sBorderText = new GridBagConstraints();
		gbc_sBorderText.insets = new Insets(0, 0, 5, 5);
		gbc_sBorderText.fill = GridBagConstraints.HORIZONTAL;
		gbc_sBorderText.gridx = 1;
		gbc_sBorderText.gridy = 1;
		contentPane.add(sBorderText, gbc_sBorderText);
		sBorderText.setColumns(10);

		zoomComboBox = new JComboBox<ZoomLevel>();
		zoomComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				JComboBox<ZoomLevel> comboBox = (JComboBox<ZoomLevel>) arg.getSource();
				settings.setZoomLevel(Integer.parseInt(comboBox
						.getSelectedItem().toString()));
			}
		});
		zoomComboBox.setModel(new DefaultComboBoxModel<ZoomLevel>(ZoomLevel.values()));
		GridBagConstraints gbc_zoomComboBox = new GridBagConstraints();
		gbc_zoomComboBox.gridwidth = 3;
		gbc_zoomComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_zoomComboBox.fill = GridBagConstraints.BOTH;
		gbc_zoomComboBox.gridx = 3;
		gbc_zoomComboBox.gridy = 1;
		contentPane.add(zoomComboBox, gbc_zoomComboBox);

		JLabel wBorderLabel = new JLabel("Western Border");
		wBorderLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_wBorderLabel = new GridBagConstraints();
		gbc_wBorderLabel.fill = GridBagConstraints.VERTICAL;
		gbc_wBorderLabel.anchor = GridBagConstraints.WEST;
		gbc_wBorderLabel.insets = new Insets(0, 0, 5, 5);
		gbc_wBorderLabel.gridx = 0;
		gbc_wBorderLabel.gridy = 2;
		contentPane.add(wBorderLabel, gbc_wBorderLabel);

		wBorderText = new JTextField();
		wBorderText.setText("0");
		GridBagConstraints gbc_wBorderText = new GridBagConstraints();
		gbc_wBorderText.insets = new Insets(0, 0, 5, 5);
		gbc_wBorderText.fill = GridBagConstraints.HORIZONTAL;
		gbc_wBorderText.gridx = 1;
		gbc_wBorderText.gridy = 2;
		contentPane.add(wBorderText, gbc_wBorderText);
		wBorderText.setColumns(10);

		mapTypeLabel = new JLabel("Map type");
		GridBagConstraints gbc_mapTypeLabel = new GridBagConstraints();
		gbc_mapTypeLabel.gridwidth = 3;
		gbc_mapTypeLabel.insets = new Insets(0, 0, 5, 0);
		gbc_mapTypeLabel.gridx = 3;
		gbc_mapTypeLabel.gridy = 2;
		contentPane.add(mapTypeLabel, gbc_mapTypeLabel);

		JLabel eBorderLabel = new JLabel("Eastern Border");
		eBorderLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_eBorderLabel = new GridBagConstraints();
		gbc_eBorderLabel.fill = GridBagConstraints.VERTICAL;
		gbc_eBorderLabel.anchor = GridBagConstraints.WEST;
		gbc_eBorderLabel.insets = new Insets(0, 0, 5, 5);
		gbc_eBorderLabel.gridx = 0;
		gbc_eBorderLabel.gridy = 3;
		contentPane.add(eBorderLabel, gbc_eBorderLabel);

		eBorderText = new JTextField();
		eBorderText.setText("0");
		GridBagConstraints gbc_eBorderText = new GridBagConstraints();
		gbc_eBorderText.insets = new Insets(0, 0, 5, 5);
		gbc_eBorderText.fill = GridBagConstraints.HORIZONTAL;
		gbc_eBorderText.gridx = 1;
		gbc_eBorderText.gridy = 3;
		contentPane.add(eBorderText, gbc_eBorderText);
		eBorderText.setColumns(10);
		
		mapRadioButton = new JRadioButton("Map");
		mapRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				settings.setDownloadType(MapTypes.MAP);
			}
		});
		GridBagConstraints gbc_mapRadioButton = new GridBagConstraints();
		gbc_mapRadioButton.insets = new Insets(0, 0, 5, 5);
		gbc_mapRadioButton.gridx = 3;
		gbc_mapRadioButton.gridy = 3;
		contentPane.add(mapRadioButton, gbc_mapRadioButton);
		group.add(mapRadioButton);

		startDownloadButton = new JButton("Start Download");
		startDownloadButton.addActionListener(buttonsListener);

		satelliteRadioButton = new JRadioButton("Satellite");
		satelliteRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				settings.setDownloadType(MapTypes.SATELLITE);
			}
		});
		GridBagConstraints gbc_satelliteRadioButton = new GridBagConstraints();
		gbc_satelliteRadioButton.insets = new Insets(0, 0, 5, 0);
		gbc_satelliteRadioButton.gridx = 5;
		gbc_satelliteRadioButton.gridy = 3;
		contentPane.add(satelliteRadioButton, gbc_satelliteRadioButton);
		group.add(satelliteRadioButton);
		
		terrainMapRadioButton = new JRadioButton("Terrain map");
		terrainMapRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				settings.setDownloadType(MapTypes.TERRAIN);
			}
		});
		GridBagConstraints gbc_terrainMapRadioButton = new GridBagConstraints();
		gbc_terrainMapRadioButton.insets = new Insets(0, 0, 5, 5);
		gbc_terrainMapRadioButton.gridx = 4;
		gbc_terrainMapRadioButton.gridy = 3;
		contentPane.add(terrainMapRadioButton, gbc_terrainMapRadioButton);
		group.add(terrainMapRadioButton);
		
		startDownloadButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_startDownloadButton = new GridBagConstraints();
		gbc_startDownloadButton.gridwidth = 2;
		gbc_startDownloadButton.insets = new Insets(0, 0, 5, 5);
		gbc_startDownloadButton.fill = GridBagConstraints.BOTH;
		gbc_startDownloadButton.gridx = 0;
		gbc_startDownloadButton.gridy = 4;
		contentPane.add(startDownloadButton, gbc_startDownloadButton);

		cancelDownloadButton = new JButton("Cancel Download");
		cancelDownloadButton.addActionListener(buttonsListener);
		cancelDownloadButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_cancelDownloadButton = new GridBagConstraints();
		gbc_cancelDownloadButton.gridwidth = 6;
		gbc_cancelDownloadButton.insets = new Insets(0, 0, 5, 0);
		gbc_cancelDownloadButton.fill = GridBagConstraints.BOTH;
		gbc_cancelDownloadButton.gridx = 0;
		gbc_cancelDownloadButton.gridy = 4;
		contentPane.add(cancelDownloadButton, gbc_cancelDownloadButton);
		cancelDownloadButton.setVisible(false);

		saveDirectoryButton = new JButton("Save Directory");
		saveDirectoryButton.addActionListener(buttonsListener);
		GridBagConstraints gbc_saveDirectoryButton = new GridBagConstraints();
		gbc_saveDirectoryButton.gridwidth = 3;
		gbc_saveDirectoryButton.fill = GridBagConstraints.BOTH;
		gbc_saveDirectoryButton.insets = new Insets(0, 0, 5, 0);
		gbc_saveDirectoryButton.gridx = 3;
		gbc_saveDirectoryButton.gridy = 4;
		contentPane.add(saveDirectoryButton, gbc_saveDirectoryButton);

		messageArea = new JTextPane();
		scrollPane = new JScrollPane(messageArea);
		messageArea.setText("");
		messageArea.setEditable(false);
		GridBagConstraints gbc_messageArea = new GridBagConstraints();
		gbc_messageArea.gridwidth = 6;
		gbc_messageArea.fill = GridBagConstraints.BOTH;
		gbc_messageArea.gridx = 0;
		gbc_messageArea.gridy = 5;
		contentPane.add(scrollPane, gbc_messageArea);
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public JButton getSaveDirectoryButton() {
		return saveDirectoryButton;
	}

	public JButton getStartDownloadButton() {
		return startDownloadButton;
	}

	public JButton getCancelDownloadButton() {
		return cancelDownloadButton;
	}

	public JMenuItem getProxySettingsMenu() {
		return proxySettingsMenu;
	}

	public JMenuItem getHelpMenu() {
		return helpMenu;
	}

	public JMenuItem getAboutMenu() {
		return aboutMenu;
	}

	public String getNBorderText() {
		return nBorderText.getText();
	}

	public String getSBorderText() {
		return sBorderText.getText();
	}

	public String getEBorderText() {
		return eBorderText.getText();
	}

	public String getWBorderText() {
		return wBorderText.getText();
	}

	public synchronized void writeMessage(String message) {
		messageArea.setText(messageArea.getText().concat(message + "\n"));
	}

	public void clearMessageArea() {
		messageArea.setText("");
	}

	public void setButtonsInInitialConfiguration() {
		startDownloadButton.setVisible(true);
		saveDirectoryButton.setVisible(true);
		cancelDownloadButton.setVisible(false);
	}

	public void setButtonsInSwapedConfiguration() {
		startDownloadButton.setVisible(false);
		saveDirectoryButton.setVisible(false);
		cancelDownloadButton.setVisible(true);
	}
}
