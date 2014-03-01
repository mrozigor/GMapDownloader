package pl.im.gmd;

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
import javax.swing.JFileChooser;

import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JTextField nWCornerText;
	private JTextField nECornerText;
	private JTextField sWCornerText;
	private JTextField sECornerText;
	private JLabel zoomLabel;
	private JComboBox zoomComboBox;
	private JButton saveDirectoryButton;
	private JButton startDownloadButton;
	private JTextPane messageArea;
	private JLabel mapTypeLabel;
	private JRadioButton mapRadioButton;
	private JRadioButton satelliteRadioButton;
	private Settings settings;
	private Downloader downloader;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
		settings = new Settings(this);
		downloader = new Downloader(this);
		setTitle("GMap Downloader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{65, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel nWCornerLabel = new JLabel("NW Corner");
		nWCornerLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_nWCornerLabel = new GridBagConstraints();
		gbc_nWCornerLabel.fill = GridBagConstraints.VERTICAL;
		gbc_nWCornerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nWCornerLabel.anchor = GridBagConstraints.WEST;
		gbc_nWCornerLabel.gridx = 0;
		gbc_nWCornerLabel.gridy = 0;
		contentPane.add(nWCornerLabel, gbc_nWCornerLabel);
		
		nWCornerText = new JTextField();
		GridBagConstraints gbc_nWCornerText = new GridBagConstraints();
		gbc_nWCornerText.insets = new Insets(0, 0, 5, 5);
		gbc_nWCornerText.fill = GridBagConstraints.HORIZONTAL;
		gbc_nWCornerText.gridx = 1;
		gbc_nWCornerText.gridy = 0;
		contentPane.add(nWCornerText, gbc_nWCornerText);
		nWCornerText.setColumns(10);
		
		zoomLabel = new JLabel("Zoom");
		GridBagConstraints gbc_zoomLabel = new GridBagConstraints();
		gbc_zoomLabel.gridwidth = 2;
		gbc_zoomLabel.insets = new Insets(0, 0, 5, 0);
		gbc_zoomLabel.gridx = 3;
		gbc_zoomLabel.gridy = 0;
		contentPane.add(zoomLabel, gbc_zoomLabel);
		
		JLabel nECornerLabel = new JLabel("NE Corner");
		nECornerLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_nECornerLabel = new GridBagConstraints();
		gbc_nECornerLabel.fill = GridBagConstraints.VERTICAL;
		gbc_nECornerLabel.anchor = GridBagConstraints.WEST;
		gbc_nECornerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nECornerLabel.gridx = 0;
		gbc_nECornerLabel.gridy = 1;
		contentPane.add(nECornerLabel, gbc_nECornerLabel);
		
		nECornerText = new JTextField();
		GridBagConstraints gbc_nECornerText = new GridBagConstraints();
		gbc_nECornerText.insets = new Insets(0, 0, 5, 5);
		gbc_nECornerText.fill = GridBagConstraints.HORIZONTAL;
		gbc_nECornerText.gridx = 1;
		gbc_nECornerText.gridy = 1;
		contentPane.add(nECornerText, gbc_nECornerText);
		nECornerText.setColumns(10);
		
		zoomComboBox = new JComboBox();
		zoomComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				JComboBox comboBox = (JComboBox)arg.getSource();
				settings.setZoomLevel(Integer.parseInt(comboBox.getSelectedItem().toString()));
			}
		});
		zoomComboBox.setModel(new DefaultComboBoxModel(ZoomLevel.values()));
		GridBagConstraints gbc_zoomComboBox = new GridBagConstraints();
		gbc_zoomComboBox.gridwidth = 2;
		gbc_zoomComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_zoomComboBox.fill = GridBagConstraints.BOTH;
		gbc_zoomComboBox.gridx = 3;
		gbc_zoomComboBox.gridy = 1;
		contentPane.add(zoomComboBox, gbc_zoomComboBox);
		
		JLabel sWCornerLabel = new JLabel("SW Corner");
		sWCornerLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_sWCornerLabel = new GridBagConstraints();
		gbc_sWCornerLabel.fill = GridBagConstraints.VERTICAL;
		gbc_sWCornerLabel.anchor = GridBagConstraints.WEST;
		gbc_sWCornerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sWCornerLabel.gridx = 0;
		gbc_sWCornerLabel.gridy = 2;
		contentPane.add(sWCornerLabel, gbc_sWCornerLabel);
		
		sWCornerText = new JTextField();
		GridBagConstraints gbc_sWCornerText = new GridBagConstraints();
		gbc_sWCornerText.insets = new Insets(0, 0, 5, 5);
		gbc_sWCornerText.fill = GridBagConstraints.HORIZONTAL;
		gbc_sWCornerText.gridx = 1;
		gbc_sWCornerText.gridy = 2;
		contentPane.add(sWCornerText, gbc_sWCornerText);
		sWCornerText.setColumns(10);
		
		mapTypeLabel = new JLabel("Map type");
		GridBagConstraints gbc_mapTypeLabel = new GridBagConstraints();
		gbc_mapTypeLabel.gridwidth = 2;
		gbc_mapTypeLabel.insets = new Insets(0, 0, 5, 0);
		gbc_mapTypeLabel.gridx = 3;
		gbc_mapTypeLabel.gridy = 2;
		contentPane.add(mapTypeLabel, gbc_mapTypeLabel);
		
		JLabel sECornerLabel = new JLabel("SE Corner");
		sECornerLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_sECornerLabel = new GridBagConstraints();
		gbc_sECornerLabel.fill = GridBagConstraints.VERTICAL;
		gbc_sECornerLabel.anchor = GridBagConstraints.WEST;
		gbc_sECornerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sECornerLabel.gridx = 0;
		gbc_sECornerLabel.gridy = 3;
		contentPane.add(sECornerLabel, gbc_sECornerLabel);
		
		sECornerText = new JTextField();
		GridBagConstraints gbc_sECornerText = new GridBagConstraints();
		gbc_sECornerText.insets = new Insets(0, 0, 5, 5);
		gbc_sECornerText.fill = GridBagConstraints.HORIZONTAL;
		gbc_sECornerText.gridx = 1;
		gbc_sECornerText.gridy = 3;
		contentPane.add(sECornerText, gbc_sECornerText);
		sECornerText.setColumns(10);
		
		mapRadioButton = new JRadioButton("Map");
		mapRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				settings.setDownloadType("map");
			}
		});
		GridBagConstraints gbc_mapRadioButton = new GridBagConstraints();
		gbc_mapRadioButton.insets = new Insets(0, 0, 5, 5);
		gbc_mapRadioButton.gridx = 3;
		gbc_mapRadioButton.gridy = 3;
		contentPane.add(mapRadioButton, gbc_mapRadioButton);
		
		satelliteRadioButton = new JRadioButton("Satellite");
		satelliteRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				settings.setDownloadType("satellite");
			}
		});
		GridBagConstraints gbc_satelliteRadioButton = new GridBagConstraints();
		gbc_satelliteRadioButton.insets = new Insets(0, 0, 5, 0);
		gbc_satelliteRadioButton.gridx = 4;
		gbc_satelliteRadioButton.gridy = 3;
		contentPane.add(satelliteRadioButton, gbc_satelliteRadioButton);
		
		ButtonGroup group = new ButtonGroup();
		group.add(mapRadioButton);
		group.add(satelliteRadioButton);
		
		startDownloadButton = new JButton("Start Download");
		startDownloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				//TODO Start Button service.
			}
		});
		startDownloadButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_startDownloadButton = new GridBagConstraints();
		gbc_startDownloadButton.gridwidth = 2;
		gbc_startDownloadButton.insets = new Insets(0, 0, 5, 5);
		gbc_startDownloadButton.fill = GridBagConstraints.BOTH;
		gbc_startDownloadButton.gridx = 0;
		gbc_startDownloadButton.gridy = 4;
		contentPane.add(startDownloadButton, gbc_startDownloadButton);
		
		saveDirectoryButton = new JButton("Save Directory");
		saveDirectoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				JFileChooser directoryChooser = new JFileChooser();
				directoryChooser.setDialogTitle("Choose folder");
				directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = directoryChooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            String directory = directoryChooser.getSelectedFile().toString();
		            settings.setSaveDirectory(directory);
		        }
			}
		});
		GridBagConstraints gbc_saveDirectoryButton = new GridBagConstraints();
		gbc_saveDirectoryButton.gridwidth = 2;
		gbc_saveDirectoryButton.fill = GridBagConstraints.BOTH;
		gbc_saveDirectoryButton.insets = new Insets(0, 0, 5, 0);
		gbc_saveDirectoryButton.gridx = 3;
		gbc_saveDirectoryButton.gridy = 4;
		contentPane.add(saveDirectoryButton, gbc_saveDirectoryButton);
		
		messageArea = new JTextPane();
		messageArea.setEditable(false);
		GridBagConstraints gbc_messageArea = new GridBagConstraints();
		gbc_messageArea.gridwidth = 5;
		gbc_messageArea.fill = GridBagConstraints.BOTH;
		gbc_messageArea.gridx = 0;
		gbc_messageArea.gridy = 5;
		contentPane.add(messageArea, gbc_messageArea);
	}

}
