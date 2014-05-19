package pl.im.gmd;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.EventQueue;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JTextField;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class ProxyMenu extends JFrame {

	private JPanel contentPane;
	private JTextField pathTextArea;
	private JLabel currentPathLabel;
	private JButton selectPathButton;
	private JButton resetButton;
	private JButton applyButton;
	private Settings settings;
	
	public ProxyMenu(Settings settings) {
		this();
		this.settings = settings;
		currentPathLabel.setText("Current path: " + settings.getProxyServerListFilePath());
		pathTextArea.setText(settings.getProxyServerListFilePath());
	}
	/**
	 * Create the frame.
	 */
	public ProxyMenu() {
		setTitle("Proxy Settings");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 40, 15, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 20, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		ProxyWindowButtonsListener listener = new ProxyWindowButtonsListener(this);
		
		JLabel selectPathLabel = new JLabel("Select path to file with proxy server list");
		GridBagConstraints gbc_selectPathLabel = new GridBagConstraints();
		gbc_selectPathLabel.gridwidth = 4;
		gbc_selectPathLabel.insets = new Insets(0, 0, 5, 0);
		gbc_selectPathLabel.gridx = 0;
		gbc_selectPathLabel.gridy = 0;
		contentPane.add(selectPathLabel, gbc_selectPathLabel);
		
		pathTextArea = new JTextField();
		GridBagConstraints gbc_pathTextArea = new GridBagConstraints();
		gbc_pathTextArea.gridwidth = 3;
		gbc_pathTextArea.insets = new Insets(0, 0, 5, 5);
		gbc_pathTextArea.fill = GridBagConstraints.HORIZONTAL;
		gbc_pathTextArea.gridx = 0;
		gbc_pathTextArea.gridy = 1;
		contentPane.add(pathTextArea, gbc_pathTextArea);
		pathTextArea.setColumns(10);
		pathTextArea.setText(null);
		
		selectPathButton = new JButton("...");
		GridBagConstraints gbc_selectPathButton = new GridBagConstraints();
		gbc_selectPathButton.insets = new Insets(0, 0, 5, 0);
		gbc_selectPathButton.gridx = 3;
		gbc_selectPathButton.gridy = 1;
		contentPane.add(selectPathButton, gbc_selectPathButton);
		selectPathButton.addActionListener(listener);
		
		currentPathLabel = new JLabel("Current path: none");
		currentPathLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_currentPathLabel = new GridBagConstraints();
		gbc_currentPathLabel.gridwidth = 4;
		gbc_currentPathLabel.insets = new Insets(0, 0, 5, 5);
		gbc_currentPathLabel.gridx = 0;
		gbc_currentPathLabel.gridy = 2;
		contentPane.add(currentPathLabel, gbc_currentPathLabel);
		
		resetButton = new JButton("Reset");
		GridBagConstraints gbc_resetButton = new GridBagConstraints();
		gbc_resetButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_resetButton.insets = new Insets(0, 0, 0, 5);
		gbc_resetButton.gridx = 1;
		gbc_resetButton.gridy = 4;
		contentPane.add(resetButton, gbc_resetButton);
		resetButton.addActionListener(listener);
		
		applyButton = new JButton("Apply");
		GridBagConstraints gbc_applyButton = new GridBagConstraints();
		gbc_applyButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_applyButton.gridwidth = 2;
		gbc_applyButton.gridx = 2;
		gbc_applyButton.gridy = 4;
		contentPane.add(applyButton, gbc_applyButton);
		applyButton.addActionListener(listener);
	}

	public JLabel getCurrentPathLabel() {
		return currentPathLabel;
	}

	public JButton getSelectPathButton() {
		return selectPathButton;
	}

	public JButton getResetButton() {
		return resetButton;
	}

	public JButton getApplyButton() {
		return applyButton;
	}
	
	public JTextField getPathTextArea() {
		return pathTextArea;
	}
	
	public Settings getSettings() {
		return settings;
	}
}
