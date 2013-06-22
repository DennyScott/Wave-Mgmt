package views;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.BorderLayout;
import javax.swing.JTable;

import components.ColorPanel;
import components.FooterPanel;
import components.TopRoundPanel;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import structures.User;

import data.UserSingleton;

public class MainWindow extends JFrame {

	private JTable table;
	
	private final ColorPanel header = new ColorPanel();
	private final TopRoundPanel tools = new TopRoundPanel();
	private final FooterPanel footer = new FooterPanel();

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	public void begin() {
		this.setVisible(true);
		User user = UserSingleton.getInstance();
		System.out.println(user.getFirstName());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setTitle("Project Record Keeper");
		this.setBounds(100, 100, 608, 428);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		setLookAndFeel("Nimbus");

		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		this.getContentPane().setLayout(
				new BorderLayout(0, 5));
		
		
		createHeader();
		createFooter();
		this.setExtendedState(this
				.getExtendedState() | JFrame.MAXIMIZED_BOTH);

	}
	

	/**
	 * Create Header section of the MainWindow
	 */
	private void createHeader() {

		header.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel head = new JLabel("Project Record Keeper");
		head.setHorizontalAlignment(SwingConstants.CENTER);
		head.setForeground(new Color(47, 79, 79));
		head.setFont(new Font("Cambria", Font.BOLD, 18));

		JLabel him = new JLabel("Health Information Management");
		him.setHorizontalAlignment(SwingConstants.CENTER);
		him.setForeground(new Color(47, 79, 79));
		him.setFont(new Font("Cambria", Font.BOLD, 14));

		this.getContentPane().add(header, BorderLayout.NORTH);
		header.setLayout(new GridLayout(0, 1, 0, 0));
		header.add(head);
		header.add(him);
	}

	/**
	 * Set look and feel of the window based on the String passed.
	 * 
	 * @param look Determines the look and feel
	 */
	private void setLookAndFeel(String look) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (look.equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(look + " not found");
		}
	}

	/**
	 * Create Table to display current projects. The data passed is the data that will be shown in the table
	 * 
	 * @param data Data to be shown
	 */
	public void createTable(Object[][] data) {
		String[] columnNames = { "Project Name", "Unit", "Analyst(s)",
				"Start Date", "End Date" };

		
	
		
		tools.setBorder(new EmptyBorder(0, 10, 10, 10));
		GridBagLayout gbl_tools = new GridBagLayout();
		gbl_tools.columnWidths = new int[] { 51, 0 };
		gbl_tools.rowHeights = new int[] { 28, 28, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_tools.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_tools.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		
		tools.setLayout(gbl_tools);
		JPanel text = new JPanel();
		text.setBackground(new Color(200, 200, 200, 50));
		GridBagConstraints gbc_text = new GridBagConstraints();
		gbc_text.fill = GridBagConstraints.BOTH;
		gbc_text.insets = new Insets(5, 0, 5, 5);
		gbc_text.gridx = 0;
		gbc_text.gridy = 0;
		tools.add(text, gbc_text);
		
		GridBagLayout gbl_text = new GridBagLayout();
		gbl_text.columnWidths = new int[] { 200, 39, 0, 0, 0 };
		gbl_text.rowHeights = new int[] { 20, 0 };
		gbl_text.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_text.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		text.setLayout(gbl_text);

		JTextField search = new JTextField("Search");
		GridBagConstraints gbc_search = new GridBagConstraints();
		gbc_search.insets = new Insets(0, 10, 0, 5);
		gbc_search.fill = GridBagConstraints.HORIZONTAL;
		gbc_search.anchor = GridBagConstraints.NORTH;
		gbc_search.gridx = 0;
		gbc_search.gridy = 0;
		text.add(search, gbc_search);

		JButton btnSearch = new JButton("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 0, 0, 5);
		gbc_btnSearch.anchor = GridBagConstraints.WEST;
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 0;
		text.add(btnSearch, gbc_btnSearch);

		JButton btnAdvancedSearch = new JButton("Advanced Search");
		GridBagConstraints gbc_btnAdvancedSearch = new GridBagConstraints();
		gbc_btnAdvancedSearch.insets = new Insets(0, 0, 0, 5);
		gbc_btnAdvancedSearch.anchor = GridBagConstraints.WEST;
		gbc_btnAdvancedSearch.gridx = 2;
		gbc_btnAdvancedSearch.gridy = 0;
		text.add(btnAdvancedSearch, gbc_btnAdvancedSearch);

		JButton btnAddRow = new JButton("Add Project");
		GridBagConstraints gbc_btnAddRow = new GridBagConstraints();
		gbc_btnAddRow.gridx = 3;
		gbc_btnAddRow.gridy = 0;
		text.add(btnAddRow, gbc_btnAddRow);

		table = new JTable(data, columnNames);
		table.setRowSelectionAllowed(false);
		table.setDragEnabled(true);
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 10, 5, 20);
		gbc_scrollPane.gridheight = 9;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		tools.add(scrollPane, gbc_scrollPane);
		getContentPane().add(tools, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("Showing " + table.getRowCount() + " Results");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 10);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 10;
		tools.add(lblNewLabel, gbc_lblNewLabel);

	}

	/**
	 * Create Footer section of the Main Window
	 */
	private void createFooter() {
		
		footer.setBackground(Color.LIGHT_GRAY);
		footer.setBorder(new EmptyBorder(4, 10, 5, 0));

		getContentPane().add(footer, BorderLayout.SOUTH);
		footer.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel user = new JLabel("Currently User: Denny Scott");
		user.setHorizontalAlignment(SwingConstants.LEFT);
		footer.add(user);
	}

}
