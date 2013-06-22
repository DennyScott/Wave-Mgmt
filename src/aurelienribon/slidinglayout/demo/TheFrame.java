package aurelienribon.slidinglayout.demo;

import components.SwingUtils;
import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.slidinglayout.SLConfig;
import aurelienribon.slidinglayout.SLKeyframe;
import aurelienribon.slidinglayout.SLPanel;
import aurelienribon.slidinglayout.SLSide;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.TreePath;

import models.CompileTaskTable;
import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilCalendarModel;

import org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton;
import org.jdesktop.swingx.AbstractPatternPanel;
import org.jdesktop.swingx.JXSearchPanel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.PatternFilter;
import org.jdesktop.swingx.renderer.DefaultTreeRenderer;
import org.jdesktop.swingx.renderer.IconValue;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableNode;

import structures.DRDD;
import structures.Project;
import structures.Role;
import structures.Subtask;
import structures.Task;
import structures.TaskIF;
import structures.Unit;
import structures.User;

import components.ColorPanel;
import components.IconPanel;
import components.GradientPanel;
import components.ImagePanel;
import components.MyDialog;
import components.TopRoundPanel;
import components.TreeNode;
import controllers.PagesController;
import controllers.UserController;
import data.CompletedSingleton;
import data.ProjectSingleton;
import data.UserSingleton;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class TheFrame extends JFrame {
	
	private  SLPanel panel = new SLPanel();
	private  ThePanel logIn = new ThePanel();
	private  ThePanel logIn2 = new ThePanel();
	private  ColorPanel header = new ColorPanel();
	private ColorPanel headerName = new ColorPanel();
	private ThePanel advancedSearch = new ThePanel();
	private  ThePanel currentProjects = new ThePanel();
	private  ThePanel tools = new ThePanel();
	private  ThePanel taskTools = new ThePanel();
	private  ThePanel analystTools = new ThePanel();
	private ThePanel advSearchTools = new ThePanel();
	private ThePanel attachTools = new ThePanel();
	private ThePanel DRDDTools = new ThePanel();
	private ThePanel commentTools = new ThePanel();
	private  ThePanel tableBox = new ThePanel();
	private ThePanel taskBox = new ThePanel();
	private  ThePanel analystBox = new ThePanel();
	private ThePanel attachmentBox = new ThePanel();
	private ThePanel commentBox = new ThePanel();
	private ThePanel DRDDBox = new ThePanel();
	private  ThePanel deadlines = new ThePanel();
	private  ThePanel newestTasks = new ThePanel();
	private  ThePanel searchBar= new ThePanel(new Color(0x888888));
	private ThePanel taskBar = new ThePanel(new Color(0x888888));
	private  ThePanel footer = new ThePanel();
	private ThePanel commentFooter = new ThePanel();
	private  SLConfig mainCfg, logCfg, headerCfg, analystCfg, toolsCfg, tableBoxCfg, deadlinesCfg, 
		advCfg, attachCfg, commentCfg, DRDDCfg;
	private Stack<Runnable> mobility = new Stack<Runnable>();
	
	
	private JXTable table, analystTable, attachmentTable, fileTable;
	private static Color FG_COLOR = new Color(0xFFFFFF);
	private PagesController controller;
	private UserController uc;
	JButton viewProject, addUser,removeProject, addAnalyst, removeTask, completeTask, 
			addSubtask, addUnit, previousProject, removeAtt, downloadAtt, taskDetails, taskComments,
			addOs, addLocation, addData, saveData, saveFolder, addRow, completeProject, attachments;
	JLabel headLabel;
	JTextPane comments;
	JComboBox<String> dataDChoice, osChoice;
	JTextArea workPerformed, actionRequested, dataDelivered;
	JTextField location, dataAdd, newOS;
	DRDD drdd;
	Object[][] tableData;
	JMenuItem logOut, db, currentRoot;
	
	
	JXTreeTable treeTable;
	TreeNode root;
	DefaultTreeTableModel treeModel;
	TableModel model, attachmentModel, fileModel;
	Object lock1 = new Object();
	MyDialog test;
	boolean onTask, onMain, onProject;
	File file;
	JXSearchPanel search, projectName, startDate, unit, analyst, manager, endDate;

	public TheFrame(PagesController controller, UserController uc) {
		this.controller = controller;
		this.uc = uc;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Project Management Tool");
		getContentPane().setBackground(new Color(0x1E2C45));
		getContentPane().add(panel, BorderLayout.CENTER);
		
		Image icon = Toolkit.getDefaultToolkit().getImage("data/bison.png");
		this.setIconImage(icon);
		

		mainCfg = new SLConfig(panel)
		.gap(10, 10)
		.row(1f).col(1f).col(6f).col(1f)
		.beginGrid(0, 0)
		.row(8f).row(20f).col(1f)
		.place(0, 0, tools)
		.place(1, 0, currentProjects)
		.endGrid()
		.beginGrid(0, 1)
		.row(2f).row(1f).row(20f).row(1f).col(1f)
		.place(0, 0, header)
		.place(1, 0, searchBar)
		.place(2, 0, tableBox)
		.place(3,0, footer)
		.endGrid()
		.beginGrid(0, 2)
		.row(10f).row(18f).col(1f)
		.place(0, 0, deadlines)
		.place(1, 0, newestTasks)
		.endGrid();
		
		commentCfg = new SLConfig(panel)
		.gap(10, 10)
		.row(1f).col(1f).col(6f).col(1f)
		.beginGrid(0, 0)
		.row(8f).row(20f).col(1f)
		.place(0, 0, commentTools)
		.place(1, 0, currentProjects)
		.endGrid()
		.beginGrid(0, 1)
		.row(2f).row(22f).row(1f).col(1f)
		.place(0, 0, headerName)
		.place(1, 0, commentBox)
		.place(2,0, commentFooter)
		.endGrid()
		.beginGrid(0, 2)
		.row(10f).row(18f).col(1f)
		.place(0, 0, deadlines)
		.place(1, 0, newestTasks)
		.endGrid();
		
		DRDDCfg = new SLConfig(panel)
		.gap(10, 10)
		.row(1f).col(1f).col(6f).col(1f)
		.beginGrid(0, 0)
		.row(8f).row(20f).col(1f)
		.place(0, 0, DRDDTools)
		.place(1, 0, currentProjects)
		.endGrid()
		.beginGrid(0, 1)
		.row(2f).row(22f).row(1f).col(1f)
		.place(0, 0, headerName)
		.place(1, 0, DRDDBox)
		.place(2,0, footer)
		.endGrid()
		.beginGrid(0, 2)
		.row(10f).row(18f).col(1f)
		.place(0, 0, deadlines)
		.place(1, 0, newestTasks)
		.endGrid();
		
		advCfg = new SLConfig(panel)
		.gap(10, 10)
		.row(1f).col(1f).col(6f).col(1f)
		.beginGrid(0, 0)
		.row(8f).row(20f).col(1f)
		.place(0, 0, advSearchTools)
		.place(1, 0, currentProjects)
		.endGrid()
		.beginGrid(0, 1)
		.row(4f).row(19f).row(1f).col(1f)
		.place(0, 0, advancedSearch)
		.place(1, 0, tableBox)
		.place(2,0, footer)
		.endGrid()
		.beginGrid(0, 2)
		.row(10f).row(18f).col(1f)
		.place(0, 0, deadlines)
		.place(1, 0, newestTasks)
		.endGrid();
		
		logCfg = new SLConfig(panel)
		.gap(10, 10)
		.row(1f).col(1f)
		.beginGrid(0, 0)
		.row(2f).row(1f).col(1f)
		.place(0, 0, logIn2)
		.place(1, 0, logIn)
		.endGrid();
		

		headerCfg = new SLConfig(panel)
		.gap(10, 10)
		.row(1f).col(1f).col(6f).col(1f)
		.beginGrid(0, 0)
		.row(8f).row(20f).col(1f)
		.place(0, 0, taskTools)
		.place(1, 0, currentProjects)
		.endGrid()
		.beginGrid(0, 1)
		.row(2f).row(1f).row(22f).row(1f).col(1f)
		.place(0, 0, headerName)
		.place(1, 0, taskBar)
		.place(2, 0, taskBox)
		.place(3,0, footer)
		.endGrid()
		.beginGrid(0, 2)
		.row(10f).row(18f).col(1f)
		.place(0, 0, deadlines)
		.place(1, 0, newestTasks)
		.endGrid();
		
		attachCfg = new SLConfig(panel)
		.gap(10, 10)
		.row(1f).col(1f).col(6f).col(1f)
		.beginGrid(0, 0)
		.row(8f).row(20f).col(1f)
		.place(0, 0, attachTools)
		.place(1, 0, currentProjects)
		.endGrid()
		.beginGrid(0, 1)
		.row(2f).row(22f).row(1f).col(1f)
		.place(0, 0, headerName)
		.place(1, 0, attachmentBox)
		.place(2,0, footer)
		.endGrid()
		.beginGrid(0, 2)
		.row(10f).row(18f).col(1f)
		.place(0, 0, deadlines)
		.place(1, 0, newestTasks)
		.endGrid();
		
		analystCfg = new SLConfig(panel)
		.gap(10, 10)
		.row(1f).col(1f).col(6f).col(1f)
		.beginGrid(0, 0)
		.row(8f).row(20f).col(1f)
				.place(0, 0, analystTools)
		.place(1, 0, currentProjects)
		.endGrid()
		.beginGrid(0, 1)
		.row(2f).row(23f).row(1f).col(1f)
		.place(0, 0, headerName)
		.place(1, 0, analystBox)
		.place(2,0, footer)
		.endGrid()
		.beginGrid(0, 2)
		.row(10f).row(18f).col(1f)
		.place(0, 0, deadlines)
		.place(1, 0, newestTasks)
		.endGrid();

		toolsCfg = new SLConfig(panel)
		.gap(10, 10)
		.row(1f).col(2f).col(1f)
		.place(0, 0, tools)
		.place(0, 1, deadlines);

		tableBoxCfg = new SLConfig(panel)
		.gap(10, 10)
		.row(1f).row(1f).col(1f).col(1f)
		.place(0, 0, header)
		.place(1, 0, currentProjects)
		.place(0, 1, tools)
		.place(1, 1, tableBox);

		deadlinesCfg = new SLConfig(panel)
		.gap(10, 10)
		.row(1f).col(1f)
		.place(0, 0, deadlines);

		panel.setTweenManager(SLAnimator.createTweenManager());
		panel.initialize(logCfg);

		initialize();
		
	}

	private void initialize(){
		this.setTitle("Project Record Keeper");
		this.setBounds(100, 100, 608, 428);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		setLookAndFeel("Nimbus");

		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnDB = new JMenu("Database");
		menuBar.add(mnDB);
		
		currentRoot = new JMenuItem("Current Root Folder");
		mnDB.add(currentRoot);
		currentRoot.setEnabled(false);
		
		currentRoot.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(getFrame(), controller.getRootFolder());
			}
		});
		
		db = new JMenuItem("Change Root Folder");
		mnDB.add(db);
		db.setEnabled(false);
		
		db.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				File file = selectFolder();
				controller.updateRootFolder(file);
				
				
			}
			
		});

		logOut = new JMenuItem("Log Out");
		mnFile.add(logOut);
		
		logOut.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(onMain){
					logOutAction.run();		
				}else{
					JOptionPane.showMessageDialog(getFrame(), "Please return to the main screen before logging out.");
				}
			}
			
		});
		
		
		
		logOut.setEnabled(false);
		onTask = false;

		createLogin();
		createHeader();
		createSearchBar();
		createTaskBar();
		createAdvanced();
		createProjectTable(true);
		createTaskTable();
		createTools();
		createCommentTools();
		createCommentBox();
		createAnalystTools();
		createTaskTools();
		createAttachTools();
		createAdvSearchTools();
		createDRDDTools();
		
		
		

		this.setExtendedState(this
				.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}
	
	public void loadUser(){
		db.setEnabled(true);
		currentRoot.setEnabled(true);
		
		if(!(UserSingleton.getInstance().getRole().getRoleID()==1)){
			addUser.setEnabled(false);
			addUnit.setEnabled(false);
			db.setEnabled(false);
			currentRoot.setEnabled(false);
			
		}
		
		if(UserSingleton.getInstance().getRole().getRoleID()<3){
			addRow.setEnabled(true);
			removeProject.setEnabled(false);
			completeProject.setEnabled(false);
		}
		
		logOut.setEnabled(true);
		
		createYourProjects();
		createDeadlines();
		createNewestTasks();
		createFooter();
		attachFilters();
		test.dispose();
		
		PatternFilter filter = new PatternFilter("",
				Pattern.CASE_INSENSITIVE, 1);
		table.setFilters(new FilterPipeline(filter));
		search.setPatternFilter(filter);

		search.setFieldName("");
		
	}
	
	public void userLogIn(){
		
		
		logInAction.run();
		
	}
	
	private void createLogin(){
		
		logIn2.setLayout(new GridBagLayout());
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.weighty = 0;
		cons.anchor = GridBagConstraints.CENTER;
		cons.gridx = 0;
		cons.gridy = 0;
		
		logIn.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
		
		
		JLabel him = new JLabel("HIM");
		him.setForeground(new Color(0x707D90));
		JLabel project = new JLabel("PROJECT MANAGEMENT");
		project.setForeground(new Color(0x707D90));
		
		him.setFont(new Font("Algerian", Font.BOLD, 84));
		project.setFont(new Font("Algerian", Font.BOLD, 62));
		
		logIn2.add(him,cons);
		cons.gridy = 1;
		logIn2.add(project,cons);
		
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("data/calendar.jpg"));
			JLabel picLabel = new JLabel(new ImageIcon( myPicture ));
			//add(picLabel );
			
			cons.gridy=2;
			logIn2.add(picLabel,cons);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		JButton logButton = new JButton("Sign In");
		JButton exitButton = new JButton("Exit");
		JPanel buttonFiller = new JPanel();
		buttonFiller.setBackground(null);
		logIn.setLayout(new GridBagLayout());
		
		cons = new GridBagConstraints();
		cons.weighty = 1;
		cons.anchor = GridBagConstraints.CENTER;
		cons.ipady = 30;
		cons.ipadx = 50;
		cons.gridx = 0;
		cons.gridy = 0;
		
		logIn.add(logButton, cons);
		cons.gridx = 2;
		logIn.add(exitButton, cons);
		
		cons.ipadx = 100;
		cons.gridx = 1;
		
		logIn.add(buttonFiller, cons);
		
		logButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				openLogInDialog();
				

			}
		});
		
		exitButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				System.exit(0);
				

			}
		});
	}

	/**
	 * Create Header section of the MainWindow
	 */
	private void createHeader() {

		header.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel head = new JLabel("Project Record Keeper");
		head.setHorizontalAlignment(SwingConstants.CENTER);
		head.setForeground(FG_COLOR);
		head.setFont(new Font("Sans", Font.BOLD, 26));
		head.setVerticalAlignment(SwingConstants.CENTER);

		JLabel him = new JLabel("Health Information Management");
		him.setHorizontalAlignment(SwingConstants.CENTER);
		him.setVerticalAlignment(SwingConstants.CENTER);
		him.setForeground(FG_COLOR);
		him.setFont(new Font("Sans", Font.BOLD, 20));

		//this.getContentPane().add(header, BorderLayout.NORTH);
		header.setLayout(new GridLayout(0, 1, 0, 0));
		header.add(head);
		header.add(him);
	}
	
	/**
	 * Create Header section of the Project Window
	 */
	private void createProjectHeader() {

		headerName.setBorder(new EmptyBorder(10, 10, 10, 10));

		if(headLabel == null){
		headLabel = new JLabel();
		headLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headLabel.setForeground(FG_COLOR);
		headLabel.setFont(new Font("Sans", Font.BOLD, 26));
		headLabel.setVerticalAlignment(SwingConstants.CENTER);

		//this.getContentPane().add(header, BorderLayout.NORTH);
		headerName.setLayout(new GridLayout());
		headerName.add(headLabel);
		}
		headLabel.setText(ProjectSingleton.getInstance().getProjectName());
	}

	private void createSearchBar(){
		searchBar.setLayout(new GridLayout());

		JPanel searchPane = new JPanel();
		searchPane.setLayout(new GridBagLayout());
		searchBar.add(searchPane);

		GridBagConstraints cons = new GridBagConstraints();
		cons.weighty = .8;
		cons.anchor = GridBagConstraints.WEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipadx = 0;
		
		String[] field = new String[5];
		field[0] = "Project Name";
		field[1] = "Unit";
		field[2] = "Analyst(s)";
		field[3] = "Start Date";
		field[4] = "End Date";
		
		final JComboBox<String> combo = new JComboBox<String>(field);
		
		cons.gridx = 1;
		searchPane.add(combo, cons);

		JLabel fillerLeft = new JLabel();
		cons.gridx = 2;
		cons.ipadx = 0;
		searchPane.add(fillerLeft, cons);
		
		
		search = new JXSearchPanel();
		searchPane.add(search, cons);
		search.setFieldName("");
		
		
		combo.addItemListener(new ItemListener(){


				@Override
				public void itemStateChanged(ItemEvent arg0) {
					// TODO Auto-generated method stub
					int choice = 1;
					for(int i = 0; i<table.getColumnCount(); i++){
						String name =(String)combo.getSelectedItem();
						if(name.equals(table.getColumnName(i))){
							choice = i;
						}
					}
					
					PatternFilter filter = new PatternFilter("",
							Pattern.CASE_INSENSITIVE, choice);
					table.setFilters(new FilterPipeline(filter));
					search.setPatternFilter(filter);

					search.setFieldName("");
					
				}
						
			
			
		});
		
		

		JButton advancedSearch = new JButton("Advanced Search");
		cons.gridx = 3;
		searchPane.add(advancedSearch, cons);
		
		advancedSearch.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				advAction.run();
				
			}
			
		});

		JPanel filler = new JPanel();
		cons.gridx = 5;
		cons.weightx = 1.0;
		searchPane.add(filler,cons);




	}
	
	public void createTaskBar(){
		taskBar.setLayout(new GridLayout());
		
		JPanel toolPane = new JPanel();
		toolPane.setBackground(new Color(214,217,223));
		taskBar.add(toolPane);
		toolPane.setLayout(new GridBagLayout());
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.WEST;
		cons.weightx = .1;
		cons.weighty = 1;
		cons.gridx = 0;
		cons.gridy = 0;




		cons.weighty = 0;
		cons.weightx = .1;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.WEST;
		cons.gridx = 0;
		cons.gridy = 1;
		cons.ipadx = 0;
		cons.ipady = 0;
		
		JButton projectDetails = new JButton("Project Details");

		
		cons.gridx = 0;
		toolPane.add(projectDetails,cons);
		
		projectDetails.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				projectDetails();
				
			}
			
		});
		
		JButton DRDD = new JButton("DRDD");

		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridx = 1;
		toolPane.add(DRDD,cons);
		
		DRDD.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				DRDDAction.run();
				
			}
			
		});
		
		
		
		
		
		attachments = new JButton("Attachments");
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridy =1;
		cons.gridx = 2;
		toolPane.add(attachments,cons);
		attachments.setEnabled(false);
		
		attachments.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				createAttachmentTable();
				attachAction.run();
				
			}
			
		});
		
		
		
		addAnalyst = new JButton("Add Analyst");
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridy = 1;
		cons.gridx = 3;
		toolPane.add(addAnalyst,cons);
		
		addAnalyst.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addAnalyst();
				
			}
			
		});
		
		
		final JButton completed = new JButton("Toggle Completed");
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridy = 1;
		cons.gridx = 4;
		toolPane.add(completed,cons);
		
		completed.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(CompletedSingleton.getInstance()){
					CompletedSingleton.setInstance(false);
				}else{
					CompletedSingleton.setInstance(true);
				}
				
				runCompile(ProjectSingleton.getInstance().getProjectID(), CompletedSingleton.getInstance());
				
			}
			
		});


	}
	
	public void projectDetails(){
		final MyDialog prompt = new MyDialog(this, "Project Details", new Dimension(600, 500));
		JPanel panel = prompt.getPanel();
		
		JPanel topPane = new JPanel();
		topPane.setBackground(new Color(0x57637B));
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 150;
		
		panel.add(topPane, cons);
		
		
		
		panel.setBorder(new EmptyBorder(10,10,10,10));
		
		topPane.setLayout(new GridBagLayout());
		
		JPanel label = new JPanel();
		label.setBackground(null);
		
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weighty = .1;
		cons.ipady = 0;
		cons.weightx = 0;
		
//		topPane.add(label, cons);
		JLabel check = new JLabel("Project Name:");
		Font f = new Font(check.getFont().getFontName(), Font.PLAIN, 12);
		check.setForeground(Color.white);
		check.setFont(f);
		
		cons.weightx = 0;
		cons.ipadx = 0;
		cons.weighty = 0;
		cons.gridx = 0;
		cons.gridy = 1;
		
		topPane.add(check,cons);
		
		final JTextField projectName = new JTextField();
		projectName.setText(ProjectSingleton.getInstance().getProjectName());
		cons.gridx = 1;
		
		cons.ipadx = 125;
		topPane.add(projectName, cons);
		
		
		
		JLabel unitLabel = new JLabel("Unit:");
		unitLabel.setForeground(Color.white);
		unitLabel.setFont(f);
		
		cons.ipadx = 0;
		cons.gridy = 2;
		cons.gridx = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		
		topPane.add(unitLabel,cons);
		
		ArrayList<Object> unitsList = controller.getUnits();
		final ArrayList<Unit> unitsFull = (ArrayList<Unit>)unitsList.get(0);
		String[] unitNames = (String[])unitsList.get(1);
		
		int index = controller.getUnitFromList(unitNames);

		//Create the combo box
		final JComboBox<String> units = new JComboBox<String>(unitNames);
		units.setSelectedIndex(index);
		
		cons.gridx = 1;
		
		topPane.add(units, cons);
		cons.fill = GridBagConstraints.NONE;
		
		final JLabel startDate = new JLabel("Start Date:");
		JLabel endDate = new JLabel("End Date:");
		startDate.setForeground(Color.white);
		endDate.setForeground(Color.white);
		startDate.setFont(f);
		endDate.setFont(f);
		
		final JCalendarButton startCalendar = new org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton();
		final JTextField startDateField = new JTextField();
		startDateField.setText(ProjectSingleton.getInstance().getStartDate().split("\\s+")[0]);
		startDateField.setEditable(false);
		
		
		startCalendar.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date){
					Date temp = (Date)evt.getNewValue();
					DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");

					startDateField.setText(df2.format(temp.getTime()));
				}
				
			}
			
		});
		
		cons.gridy = 3;
		cons.ipadx = 125;
		topPane.add(startDateField,cons);
		cons.ipadx = 0;
		cons.gridx = 2;
		topPane.add(startCalendar,cons);
		
		cons.gridx = 0;
		topPane.add(startDate, cons);
		
		cons.gridy = 4;
		topPane.add(endDate,cons);
		cons.ipadx=125;
		cons.gridx = 1;
		
		final JCalendarButton endCalendar = new org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton();
		final JTextField endDateField = new JTextField();
		endDateField.setEditable(false);
		endDateField.setText(ProjectSingleton.getInstance().getEndDate().split("\\s+")[0]);
		
		topPane.add(endDateField,cons);
		cons.ipadx = 0;
		cons.gridx = 2;
		topPane.add(endCalendar,cons);
		
		final JCalendarButton expectedCalendar = new org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton();
		final JTextField expectedDateField = new JTextField();
		expectedDateField.setEditable(false);
		expectedDateField.setText(ProjectSingleton.getInstance().getExpectedEndDate().split("\\s+")[0]);
		
		JLabel expectedDateLabel = new JLabel("Expected End Date");
		expectedDateLabel.setFont(f);
		expectedDateLabel.setForeground(Color.WHITE);
		
		cons.gridx = 0;
		cons.gridy = 5;
		
		topPane.add(expectedDateLabel,cons);
		cons.ipadx = 125;
		cons.gridx = 1;
		topPane.add(expectedDateField, cons);
		
		cons.ipadx = 0;
		cons.gridx = 2;
		topPane.add(expectedCalendar,cons);
		
		expectedCalendar.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date){
					Date temp = (Date)evt.getNewValue();
					DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");

					expectedDateField.setText(df2.format(temp.getTime()));
				}
				
			}
			
		});
		
		cons.gridy = 6;
		cons.gridx = 0;
		
		JLabel reqOrgLabel = new JLabel("Requesting Organization:");
		reqOrgLabel.setForeground(Color.white);
		topPane.add(reqOrgLabel, cons);
		
		final JTextField reqOrg = new JTextField();
		reqOrg.setText(ProjectSingleton.getInstance().getReqOrg());
		
		cons.ipadx = 125;
		cons.gridx = 1;
		topPane.add(reqOrg, cons);
		
		
		final JTextArea description = new JTextArea();
		JScrollPane textPane = new JScrollPane(description);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		JLabel descLabel = new JLabel("Description:");
		
		description.setText(ProjectSingleton.getInstance().getDescription());
		cons.ipadx = 0;
		cons.gridy = 7;
		cons.gridx = 0;
		
		descLabel.setForeground(Color.white);
		
		topPane.add(descLabel, cons);
		cons.gridx = 1;
		cons.ipadx = 125;
		cons.ipady = 70;
		
		topPane.add(textPane, cons);
		
		
		cons.ipadx = 0;
		cons.ipady = 0;
		endCalendar.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date){
					Date temp = (Date)evt.getNewValue();
					DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
					
					endDateField.setText(df2.format(temp.getTime()));
				}
				
			}
			
		});
		
		cons.gridx = 0;
		cons.ipadx = 0;
		cons.ipady = 0;
		cons.gridy = 8;
		JLabel managerLabel = new JLabel("Manager:");
		managerLabel.setForeground(Color.white);
		topPane.add(managerLabel, cons);
		
		final JTextField mgmt = new JTextField();
		mgmt.setEditable(false);
		mgmt.setText(ProjectSingleton.getInstance().getCreatedBy().getFirstName() + " " +
				ProjectSingleton.getInstance().getCreatedBy().getLastName());
		
		cons.ipadx = 125;
		cons.gridx = 1;
		topPane.add(mgmt, cons);
		
		JLabel leadLabel = new JLabel("Lead Analyst");
		leadLabel.setForeground(Color.WHITE);
		final ArrayList<Object> lead = uc.getUsers();
		String[] leadName = (String[])lead.get(1);
		final ArrayList<User> listLead = (ArrayList<User>)lead.get(0);
		
		
		final JComboBox<String> leadAnalyst = new JComboBox<String>(leadName);
		leadAnalyst.setSelectedIndex(controller.getLeadNum(listLead));
		
		cons.gridy = 9;
		cons.gridx = 0;
		topPane.add(leadLabel,cons);
		
		cons.ipadx = 0;
		cons.gridx = 1;
		topPane.add(leadAnalyst,cons);
		
		JButton submit = new JButton("Save");
		JButton cancel = new JButton("Cancel");
		
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				boolean success = controller.editProject(projectName.getText(),startDateField.getText(),endDateField.getText(),
									description.getText(),reqOrg.getText(),unitsFull.get(units.getSelectedIndex()).getUnitID(),
										expectedDateField.getText(), listLead.get(leadAnalyst.getSelectedIndex()));
				if(success){
					createProjectTable(false);
					prompt.dispose();
				}
				

			}
		});
		
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				prompt.dispose();
				
				

			}
		});

		cons.ipady = 0;
		cons.gridx = 0;
		cons.gridy = 2;
		cons.anchor = GridBagConstraints.EAST;
		panel.add(submit, cons);
		cons.anchor = GridBagConstraints.WEST;
		cons.gridx = 0;
		
		panel.add(cancel, cons);
		prompt.setVisible(true);
		

	}
	
	public void taskDetails(){
		final MyDialog prompt = new MyDialog(this, "Task Details",new Dimension(500, 400));
		JPanel panel = prompt.getPanel();
		
		JPanel topPane = new JPanel();
		topPane.setBackground(new Color(0x57637B));
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 150;
		
		panel.add(topPane, cons);
		
		
		int node = treeTable.getPathForRow(treeTable.getSelectedRow()).getPathCount();
		final boolean leaf = node==2?false:true;
		final int taskID = (int)treeTable.getValueAt(treeTable.getSelectedRow(), 4);
			TaskIF task;
		if(!leaf){
			 task = controller.getTaskByID(taskID);
		}else{
			 task = controller.getSubtaskById(taskID);
		}
		
		
		panel.setBorder(new EmptyBorder(10,10,10,10));
		
		topPane.setLayout(new GridBagLayout());
		
		JPanel label = new JPanel();
		label.setBackground(null);
		
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weighty = .1;
		cons.ipady = 0;
		cons.weightx = 0;
		
//		topPane.add(label, cons);
		JLabel check = new JLabel("Task Name:");
		Font f = new Font(check.getFont().getFontName(), Font.PLAIN, 12);
		check.setForeground(Color.white);
		check.setFont(f);
		
		cons.weightx = 0;
		cons.ipadx = 0;
		cons.weighty = 0;
		cons.gridx = 0;
		cons.gridy = 1;
		
		topPane.add(check,cons);
		
		final JTextField projectName = new JTextField();
		projectName.setText(task.getTaskName());
		cons.gridx = 1;
		
		cons.ipadx = 125;
		topPane.add(projectName, cons);
		
		
		
		JLabel unitLabel = new JLabel("Assigned To:");
		unitLabel.setForeground(Color.white);
		unitLabel.setFont(f);
		
		cons.ipadx = 0;
		cons.gridy = 2;
		cons.gridx = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		
		topPane.add(unitLabel,cons);
		
		ArrayList<Object> analystList = uc.getProjectAnalysts();
		final ArrayList<User> analystFull = (ArrayList<User>)analystList.get(0);
		String[] analystNames = (String[])analystList.get(1);
		

		int index = controller.getAnalystFromList(analystNames, task);
		

		//Create the combo box
		final JComboBox<String> units = new JComboBox<String>(analystNames);
		units.setSelectedIndex(index);
		
		cons.gridx = 1;
		
		topPane.add(units, cons);
		cons.fill = GridBagConstraints.NONE;
		
		final JLabel startDate = new JLabel("Start Date:");
		JLabel endDate = new JLabel("End Date:");
		startDate.setForeground(Color.white);
		endDate.setForeground(Color.white);
		startDate.setFont(f);
		endDate.setFont(f);
		
		final JCalendarButton startCalendar = new org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton();
		final JTextField startDateField = new JTextField();
		startDateField.setText(task.getStartDate().split("\\s+")[0]);
		startDateField.setEditable(false);
		
		
		startCalendar.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date){
					Date temp = (Date)evt.getNewValue();
					DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");

					startDateField.setText(df2.format(temp.getTime()));
				}
				
			}
			
		});
		
		cons.gridy = 3;
		cons.ipadx = 125;
		topPane.add(startDateField,cons);
		cons.ipadx = 0;
		cons.gridx = 2;
		topPane.add(startCalendar,cons);
		
		cons.gridx = 0;
		topPane.add(startDate, cons);
		
		cons.gridy = 4;
		topPane.add(endDate,cons);
		cons.ipadx=125;
		cons.gridx = 1;
		
		final JCalendarButton endCalendar = new org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton();
		final JTextField endDateField = new JTextField();
		endDateField.setEditable(false);
		endDateField.setText(task.getEndDate().split("\\s+")[0]);
		
		topPane.add(endDateField,cons);
		cons.ipadx = 0;
		cons.gridx = 2;
		topPane.add(endCalendar,cons);
		
		final JCalendarButton expectedCalendar = new org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton();
		final JTextField expectedDateField = new JTextField();
		expectedDateField.setEditable(false);
		
		JLabel expectedDateLabel = new JLabel("Expected End Date");
		expectedDateLabel.setFont(f);
		expectedDateLabel.setForeground(Color.WHITE);
		expectedDateField.setText(task.getExpectedEndDate().split("\\s+")[0]);
		
		cons.gridx = 0;
		cons.gridy = 5;
		
		topPane.add(expectedDateLabel,cons);
		cons.ipadx = 125;
		cons.gridx = 1;
		topPane.add(expectedDateField, cons);
		
		cons.ipadx = 0;
		cons.gridx = 2;
		topPane.add(expectedCalendar,cons);
		
		expectedCalendar.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date){
					Date temp = (Date)evt.getNewValue();
					DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");

					expectedDateField.setText(df2.format(temp.getTime()));
				}
				
			}
			
		});
		
		cons.gridy = 6;
		cons.gridx = 0;
		
		JLabel reqOrgLabel = new JLabel("Requested By:");
		reqOrgLabel.setForeground(Color.white);
		topPane.add(reqOrgLabel, cons);
		
		final JTextField reqOrg = new JTextField();
		reqOrg.setText(task.getRequestedBy().getFirstName() + " " + task.getRequestedBy().getLastName());
		
		cons.ipadx = 125;
		cons.gridx = 1;
		topPane.add(reqOrg, cons);
		
		
		final JTextArea description = new JTextArea();
		JScrollPane textPane = new JScrollPane(description);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		JLabel descLabel = new JLabel("Description:");
		
		description.setText(task.getDescription());
		cons.ipadx = 0;
		cons.gridy = 7;
		cons.gridx = 0;
		
		descLabel.setForeground(Color.white);
		
		topPane.add(descLabel, cons);
		cons.gridx = 1;
		cons.ipadx = 125;
		cons.ipady = 70;
		
		topPane.add(textPane, cons);
		
		
		cons.ipadx = 0;
		cons.ipady = 0;
		endCalendar.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date){
					Date temp = (Date)evt.getNewValue();
					DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
					
					endDateField.setText(df2.format(temp.getTime()));
				}
				
			}
			
		});
		
		cons.gridx = 0;
		cons.ipadx = 0;
		cons.ipady = 0;
		cons.gridy = 9;
		
		
		JButton submit = new JButton("Save");
		JButton cancel = new JButton("Cancel");
		
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				boolean success = controller.editTask(projectName.getText(),startDateField.getText(),endDateField.getText(),
						description.getText(),analystFull.get(units.getSelectedIndex()).getUserID(), leaf, taskID, expectedDateField.getText() );
				if(success){
					runCompile(ProjectSingleton.getInstance().getProjectID(),CompletedSingleton.getInstance());
					prompt.dispose();
				}
				

			}
		});
		
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				prompt.dispose();
				
				

			}
		});

		cons.ipady = 0;
		cons.gridx = 0;
		cons.gridy = 2;
		cons.anchor = GridBagConstraints.EAST;
		panel.add(submit, cons);
		cons.anchor = GridBagConstraints.WEST;
		cons.gridx = 0;
		
		panel.add(cancel, cons);
		prompt.setVisible(true);
		
	}

	/**
	 * Create Table to display current projects. The data passed is the data that will be shown in the table
	 * 
	 * @param data Data to be shown
	 */
	public void createProjectTable(boolean full) {
		String[] columnNames = {"Project ID", "Project Name", "Unit", "Analyst(s)",
				"Start Date", "End Date" };


		Object[][] tableData = controller.getProjects();

		//		tableBox.setBorder(new EmptyBorder(10, 10, 10, 10));

		model = new DefaultTableModel(tableData, columnNames){
			 @Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }

		};
		
		
		
		
		if(full){
		//Table Creation
		table = new JXTable(model);
		table.setRowSelectionAllowed(true);
		table.setDragEnabled(true);
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(false);
		table.setFillsViewportHeight(true);

			JScrollPane scrollPane = new JScrollPane(table);
			
			tableBox.setLayout(new GridLayout());
			tableBox.add(scrollPane);
			
			attachFilters();
		}
		PatternFilter filter = new PatternFilter("",
				Pattern.CASE_INSENSITIVE, 1);
		table.setFilters(new FilterPipeline(filter));
		search.setPatternFilter(filter);

		search.setFieldName("");
		table.setModel(model);
		

		
		
		//Listeners for Tool Buttons. If a table cell is selected, Buttons become enabled.
		ListSelectionModel listSelectionModel = table.getSelectionModel();
		listSelectionModel.addListSelectionListener(new ListSelectionListener() {
		        public void valueChanged(ListSelectionEvent e) { 
		            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
		            viewProject.setEnabled(!lsm.isSelectionEmpty());
		            if(UserSingleton.getInstance().getRole().getRoleID()<3){
		            	removeProject.setEnabled(!lsm.isSelectionEmpty());
		            	completeProject.setEnabled(!lsm.isSelectionEmpty());
		            }
		}});

			

	}
	
	protected void attachFilters(){
		
		//Initial Filter, points to Project Name
		int choice = 1; 
		PatternFilter filter = new PatternFilter("",
		Pattern.CASE_INSENSITIVE, choice);
		table.setFilters(new FilterPipeline(filter));
		search.setPatternFilter(filter);

		search.setFieldName("");
		
		PatternFilter[] filterHold = new PatternFilter[6];
		int projectNum = 0;
		int unitNum = 0;
		int analystNum = 0;
		int managerNum = 0;
		int startDateNum = 0;
		int endDateNum = 0;
		//Advanced Filter
		
		for(int i = 0; i<table.getColumnCount(); i++){
			String name = table.getColumnName(i);
			if((name.equals("Project Name"))){
				projectNum = i;
			}else if(name.equals("Unit")){
				unitNum = i;
			}else if(name.equals("Analyst(s)")){
				analystNum = i;
			}else if(name.equals("Manager")){
				managerNum = i;
			}else if(name.equals("Start Date")){
				startDateNum = i;
			}else if(name.equals("End Date")){
				endDateNum = i;
			}
		}
		
		
		// Project Name
		filterHold[0] = new PatternFilter("",
		Pattern.CASE_INSENSITIVE, projectNum);
		projectName.setPatternFilter(filterHold[0]);
		projectName.setFieldName("Project Name");
		
		//Advanced Filter, Unit
		filterHold[1] = new PatternFilter("",
		Pattern.CASE_INSENSITIVE, unitNum);
		unit.setPatternFilter(filterHold[1]);
		unit.setFieldName("Unit                 ");
		
		//Analyst
		filterHold[2] = new PatternFilter("",
		Pattern.CASE_INSENSITIVE, analystNum);
		analyst.setPatternFilter(filterHold[2]);
		analyst.setFieldName("Analyst(s)");
		
		//Manager
		filterHold[3] = new PatternFilter("",
		Pattern.CASE_INSENSITIVE, managerNum);
		manager.setPatternFilter(filterHold[3]);
		manager.setFieldName("Manager  ");
		
		//Start Date
		filterHold[4] = new PatternFilter("",
		Pattern.CASE_INSENSITIVE, startDateNum);
		startDate.setPatternFilter(filterHold[4]);
		startDate.setFieldName("Start Date");
		
		//End Date
		filterHold[5] = new PatternFilter("",
		Pattern.CASE_INSENSITIVE, endDateNum);
		endDate.setPatternFilter(filterHold[5]);
		endDate.setFieldName("End Date       ");

		
		table.setFilters(new FilterPipeline(filterHold));
	}
	
	/**
	 * Create Table to display current projects. The data passed is the data that will be shown in the table
	 * 
	 * @param data Data to be shown
	 */
	public void createTaskTable() {
		ArrayList<String> columnNames = new ArrayList<String>();
		
		columnNames.add("Task");
		columnNames.add("Assigned To");
		columnNames.add("Start Date");
		columnNames.add("End Date");
		columnNames.add("ID");
		columnNames.add("Completed");
	
		
		
		treeModel = new DefaultTreeTableModel();
		
		treeModel.setColumnIdentifiers(columnNames);
		
		
		
		boolean exists = treeTable==null?false:true;
		if(!exists){
		 treeTable = new JXTreeTable(treeModel){
			 private static final long serialVersionUID = 1L;

		        /*@Override
		        public Class getColumnClass(int column) {
		        return getValueAt(0, column).getClass();
		        }*/
		        @Override
		        public Class getColumnClass(int column) {
		            switch (column) {
		                case 0:
		                    return String.class;
		                case 1:
		                    return String.class;
		                case 2:
		                    return String.class;
		                case 3:
		                    return String.class;
		                case 4:
		                    return String.class;
		                case 5:
		                    return String.class;
		                default:
		                    return String.class;
		            }
		        }
		        
		       


		    };
		
		JScrollPane scrollPane = new JScrollPane(treeTable);
		taskBox.setLayout(new GridLayout());


		taskBox.add(scrollPane);
		}
		treeTable.setTreeTableModel(treeModel);
	}
	

	private void createTools(){

		//		tools.setBorder(new EmptyBorder(10,10,10,10));
		tools.setLayout(new GridLayout());

		JPanel toolPane = new JPanel();
		toolPane.setBackground(Color.white);
		tools.add(toolPane);
		toolPane.setLayout(new GridBagLayout());

		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 20;



		JLabel headerTitle= new JLabel("PROJECTS");
		headerTitle.setForeground(Color.white);
		headerTitle.setFont(new Font("Sans", Font.BOLD, 14));

		GradientPanel headerTool = new GradientPanel();
		headerTool.setLayout(new GridLayout());
		headerTool.add(headerTitle);
		toolPane.add(headerTool, cons);

		addRow = new JButton("New Project");
		cons.gridx = 0;
		cons.gridy = 1;
		cons.ipady = 0;
		toolPane.add(addRow, cons);
		addRow.setEnabled(false);
		
		addRow.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				
				createNewProject();

			}
		});
		
		
		removeProject = new JButton("Remove Project");
		cons.gridx = 0;
		cons.gridy = 2;
		toolPane.add(removeProject, cons);
		
		removeProject.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				promptRemoveProject();
				

			}
		});
		
		removeProject.setEnabled(false);
		
		completeProject = new JButton("Complete Project");
		cons.gridx = 0;
		cons.gridy = 3;
		toolPane.add(completeProject, cons);
		
		completeProject.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				promptCompleteProject();
				

			}
		});
		
		completeProject.setEnabled(false);

		addUser = new JButton("Add New User");
		cons.gridx = 0;
		cons.gridy = 4;
		toolPane.add(addUser, cons);
		
		addUser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				addNewUser();
				
			}
		});
		
		addUnit = new JButton("Add New Unit");
		cons.gridx = 0;
		cons.gridy = 5;
		toolPane.add(addUnit, cons);
		
		addUnit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				addNewUnit();
				
			}
		});
		
		

		JButton toExcel = new JButton("Export to Excel");
		cons.gridx = 0;
		cons.gridy = 6;
		toolPane.add(toExcel, cons);
		
		toExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				controller.openExcel(table);
				
			}
		});
		
		
		previousProject = new JButton("Previous Project");
		previousProject.setEnabled(false);
		
		viewProject = new JButton("View Project");
		cons.gridx = 0;
		cons.gridy = 7;
		toolPane.add(viewProject, cons);
		
		viewProject.setEnabled(false);
		
		viewProject.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				
				previousProject.setEnabled(true);
				int selectedRow = table.getSelectedRow();
				int projectID = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
				runCompile(projectID, CompletedSingleton.getInstance());
				runMove();
				

			}
		});
		
		
		cons.gridx = 0;
		cons.gridy = 8;
		toolPane.add(previousProject, cons);
		
		
		
		previousProject.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				
				headerAction.run();

			}
		});

		JPanel filler = new JPanel();
		filler.setBackground(Color.white);
		cons.gridx = 0;
		cons.gridy = 9;
		cons.weighty = 1.0;
		toolPane.add(filler, cons);


		

	}
	
	
	
	public void createAdvanced(){
		advancedSearch.setLayout(new GridLayout());

		JPanel toolPane = new JPanel();
		toolPane.setBackground(new Color(214,217,223));
		advancedSearch.add(toolPane);
		toolPane.setLayout(new GridBagLayout());

		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weighty = .1;
		cons.weightx = .1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady =20;



		JLabel headerTitle= new JLabel("ADVANCED SEARCH");
		headerTitle.setForeground(Color.white);
		headerTitle.setFont(new Font("Sans", Font.BOLD, 14));

		GradientPanel headerTool = new GradientPanel();
		headerTool.setLayout(new GridLayout());
		headerTool.add(headerTitle);
		toolPane.add(headerTool, cons);
		
		cons.weightx = 0;
		cons.weighty = 0;
		cons.ipady = 29;
		JLabel headerFake = new JLabel("");
		GradientPanel headerSide = new GradientPanel();
		headerSide.add(headerFake);
		cons.gridx = 1;
		
		toolPane.add(headerSide, cons);
		
		cons.fill = GridBagConstraints.NONE;

		projectName = new JXSearchPanel();
		cons.weightx = 0;
		cons.weighty = 0;
		cons.gridx = 0;
		cons.gridy = 1;
		cons.ipady = 0;
		toolPane.add(projectName, cons);
		
		projectName.setFieldName("Project Name");
		
		startDate = new JXSearchPanel();

		cons.gridx = 1;
		toolPane.add(startDate, cons);
		
		startDate.setFieldName("Start Date");
//		
//		JXSearchPanel endDate = new JXSearchPanel();
//
//		cons.gridx = 2;
//		toolPane.add(endDate, cons);
//		
//		endDate.setFieldName("End Date");
		
		
		unit = new JXSearchPanel();

		cons.gridy = 2;
		cons.gridx = 0;
		toolPane.add(unit, cons);
		
		unit.setFieldName("Unit");
		
		analyst = new JXSearchPanel();

		cons.gridx = 1;
		toolPane.add(analyst, cons);
		
		analyst.setFieldName("Analyst(s)");
		
		
		manager = new JXSearchPanel();

		cons.gridx = 1;
		cons.gridy = 3;
		toolPane.add(manager, cons);
		
		manager.setFieldName("Manager");
		
		
		endDate = new JXSearchPanel();

		cons.gridx = 0;
		toolPane.add(endDate, cons);
		
		endDate.setFieldName("End Date");
		
		
//		JPanel filler = new JPanel();
//		filler.setBackground(Color.white);
//		cons.gridx = 0;
//		cons.gridy = 2;
//		cons.weightx = 1.0;
//		toolPane.add(filler, cons);


		
	}
	
	public void createAttachmentTable(){
		
		String[] columnNames = {"Attachment Name", "Link", "Loaded By", "Attachment ID"};

		Object[][] tableData = controller.getAttachments(treeTable);

		//		tableBox.setBorder(new EmptyBorder(10, 10, 10, 10));

		attachmentModel = new DefaultTableModel(tableData, columnNames){
			 @Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }

		};
		
		
		
		
		if(attachmentTable==null){
		//Table Creation
		attachmentTable = new JXTable(attachmentModel);
		attachmentTable.setRowSelectionAllowed(false);
		attachmentTable.setDragEnabled(true);
		attachmentTable.setCellSelectionEnabled(true);
		attachmentTable.setColumnSelectionAllowed(true);
		attachmentTable.setFillsViewportHeight(true);

			JScrollPane scrollPane = new JScrollPane(attachmentTable);
			attachmentBox.setLayout(new GridLayout());
			attachmentBox.add(scrollPane);
			
		}
		attachmentTable.setModel(attachmentModel);
		

		
		
		//Listeners for Tool Buttons. If a table cell is selected, Buttons become enabled.
		ListSelectionModel listSelectionModel = attachmentTable.getSelectionModel();
		listSelectionModel.addListSelectionListener(new ListSelectionListener() {
		        public void valueChanged(ListSelectionEvent e) { 
		            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
		            removeAtt.setEnabled(!lsm.isSelectionEmpty());
		            downloadAtt.setEnabled(!lsm.isSelectionEmpty());
		}});

		
	}
	
	public void createDRDDBox(){
		
		
		if(fileTable==null){
			
		DRDDBox.setLayout(new GridLayout());
		
		GradientPanel pane = new GradientPanel();
		
		DRDDBox.add(pane);
		pane.setBorder(new EmptyBorder(10,10,10,10));
		pane.setLayout(new GridBagLayout());
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.NONE;
		cons.weightx = 1;
		cons.weighty = 0;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		
		//Add Action Requested
		JPanel actionPanel = new JPanel();
		actionPanel.setOpaque(false);
		actionPanel.setLayout(new FlowLayout());
		
		JLabel actionRequestedLabel = new JLabel("Action Requested");
		actionRequestedLabel.setForeground(Color.WHITE);
		
		
		actionPanel.add(actionRequestedLabel);
		
		JButton saveActionChanges = new JButton("Save Changes");
		saveActionChanges.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.updateActionRequested(workPerformed.getText());
				
			}
			
		});
		actionPanel.add(saveActionChanges);
		pane.add(actionPanel, cons);
		
		cons.fill = GridBagConstraints.HORIZONTAL;
		actionRequested = new JTextArea();
		actionRequested.setLineWrap(true);
		actionRequested.setWrapStyleWord(true);
		JScrollPane ar = new JScrollPane(actionRequested);
		
		
		
		cons.gridy = 1;
		cons.ipady = 100;
		pane.add(ar, cons);
		
		cons.ipady = 0;
		JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
		cons.gridy = 2;
		pane.add(separator2, cons);
		
		//Add Work Performed
		JPanel workPanel = new JPanel();
		workPanel.setOpaque(false);
		workPanel.setLayout(new FlowLayout());
		
		cons.fill = GridBagConstraints.NONE;
		JLabel workPerformedLabel = new JLabel("Work Performed");
		workPerformedLabel.setForeground(Color.WHITE);
		
		workPanel.add(workPerformedLabel);
		
		JButton saveWorkChanges = new JButton("Save Changes");
		saveWorkChanges.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.updateWorkPerformed(workPerformed.getText());
				
			}
			
		});
		workPanel.add(saveWorkChanges);
		
		cons.gridy = 3;
		pane.add(workPanel, cons);
		
		cons.fill = GridBagConstraints.HORIZONTAL;
		workPerformed = new JTextArea();
		workPerformed.setLineWrap(true);
		workPerformed.setWrapStyleWord(true);
		JScrollPane wp = new JScrollPane(workPerformed);
		
		
		
		cons.gridy = 4;
		cons.ipady = 100;
		pane.add(wp, cons);
		
		JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
		cons.gridy = 5;
		cons.ipady = 0;
		pane.add(separator1, cons);
		
		//Add Data Delieverd
		
		JLabel dataDeliveredLabel = new JLabel("Data Delivered");
		dataDeliveredLabel.setForeground(Color.WHITE);
		
		cons.fill = GridBagConstraints.NONE;
		cons.ipady = 0;
		cons.gridy = 6;
		
		pane.add(dataDeliveredLabel, cons);
		
		
		dataDChoice = new JComboBox<String>();
		
		JPanel dataPanel = new JPanel();
		dataPanel.setOpaque(false);
		dataPanel.setLayout(new GridBagLayout());
		cons.gridy = 0;
		dataPanel.add(dataDChoice,cons);
		
		addData = new JButton("Add");
		cons.gridx = 2;
		dataPanel.add(addData,cons);
		
		dataAdd = new JTextField();
		cons.gridx = 1;
		cons.ipadx = 125;
		dataPanel.add(dataAdd, cons);
		
		saveData = new JButton("Save Changes");
		cons.ipadx = 0;
		cons.gridx = 3;
		dataPanel.add(saveData, cons);
		
		cons.gridx = 0;
		cons.gridy = 7;
		pane.add(dataPanel,cons);
		
		dataDelivered = new JTextArea();
		dataDelivered.setLineWrap(true);
		dataDelivered.setWrapStyleWord(true);
		JScrollPane dd = new JScrollPane(dataDelivered);
		
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridy = 8;
		cons.ipady = 100;
		pane.add(dd, cons);
		
		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		cons.ipady = 0;
		cons.gridy = 9;
		pane.add(separator, cons);
		//Add File and Folder Locations
		
		JLabel fileLocations = new JLabel("File and Folder Locations");
		fileLocations.setForeground(Color.WHITE);
		
		cons.fill = GridBagConstraints.NONE;
		
		cons.gridy = 10;
		
		pane.add(fileLocations, cons);
		
		JPanel filePanel = new JPanel();
		filePanel.setOpaque(false);
		filePanel.setLayout(new GridBagLayout());
		
		cons.gridy = 0;
		
		osChoice = new JComboBox<String>();
		
		filePanel.add(osChoice, cons);
		
		addOs = new JButton("Add OS");
		cons.gridx = 2;
		
		filePanel.add(addOs,cons);
		
		newOS = new JTextField();
		cons.ipadx = 125;
		cons.gridx = 1;
		filePanel.add(newOS,cons);
		
		addLocation = new JButton("Add Location");
		
		cons.ipadx = 0;
		cons.gridx = 3;
		filePanel.add(addLocation,cons);
		
		
		cons.gridx = 0;
		cons.gridy = 11;
		pane.add(filePanel, cons);
		
		JPanel workingFolder = new JPanel();
		workingFolder.setOpaque(false);
		workingFolder.setLayout(new GridBagLayout());
		location = new JTextField();
		JLabel loc = new JLabel("Working Folder");
		loc.setForeground(Color.WHITE);
		
		cons.gridy = 0;
		
		cons.anchor = GridBagConstraints.WEST;
		workingFolder.add(loc,cons);
		
		cons.gridx = 1;
		cons.ipadx = 150;
		
		
		workingFolder.add(location, cons);
		
		saveFolder = new JButton("Save Working Folder");
		
		cons.ipadx = 0;
		cons.gridx = 2;
		
		workingFolder.add(saveFolder, cons);
		
		cons.gridx = 0;

		
		cons.gridy = 12;
		pane.add(workingFolder, cons);
		



		
		
		//Table Creation
		fileTable = new JXTable();
		fileTable.setRowSelectionAllowed(false);
		fileTable.setDragEnabled(true);
		fileTable.setCellSelectionEnabled(true);
		fileTable.setColumnSelectionAllowed(true);
		fileTable.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(fileTable);
		cons.gridy = 13;
		cons.ipadx = 0;
		cons.ipady = 150;
		cons.fill = GridBagConstraints.HORIZONTAL;
		
		pane.add(scrollPane, cons);
			
		}
		
		//Load Data into there respective fields
		 drdd = controller.getDRDD();
		
		if(drdd!=null){
		
		actionRequested.setText(drdd.getActionRequested());
		workPerformed.setText(drdd.getWorkPerformed());
		
		
		String[] test = drdd.getDataDeliveredNames();
		dataDChoice.setModel(new DefaultComboBoxModel<String>(test));
		
		dataDChoice.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				int choice = 1;
				for(int i = 0; i<drdd.getDataDelivered().size(); i++){
					String name =(String)dataDChoice.getSelectedItem();
					
					if(name.equals(drdd.getDataDelivered().get(i).getData())){
						choice = i;
						break;
					}
				}
				
				dataDelivered.setText(drdd.getDataDelivered().get(choice).getDescription());
			}
		});
		
		addData.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.addData(dataAdd.getText(), drdd.getId());
				createDRDDBox();
			}
			
		});
		
		saveData.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.addDataDescription(dataDelivered.getText(), drdd.getDataDelivered().get(dataDChoice.getSelectedIndex()).getId());
				createDRDDBox();
			}
			
		});
		
		dataDelivered.setText(drdd.getDataDelivered().get(0).getDescription());
		
		String[] osNames = drdd.getFileNames();
		osChoice.setModel(new DefaultComboBoxModel<String>(osNames));
		
		addOs.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.addOS(newOS.getText(), drdd.getId());
				createDRDDBox();
			}
		});
		
		osChoice.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				int choice = 1;
				for(int i = 0; i<drdd.getFileAndFolder().size(); i++){
					String name =(String)osChoice.getSelectedItem();
					
					if(name.equals(drdd.getFileAndFolder().get(i).getOs())){
						choice = i;
						break;
					}
				}
				
				location.setText(drdd.getFileAndFolder().get(choice).getWorkingLocation());
				tableData = drdd.getFileAndFolder().get(choice).getObject();
				String[] columnNames = {"Location", "Description", "ID"};
				
				fileModel = new DefaultTableModel(tableData, columnNames){
					 @Override
					    public boolean isCellEditable(int row, int column) {
					       //all cells false
					       return false;
					    }

				};
				
				fileTable.setModel(fileModel);
				
			}
		});
		
		saveFolder.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.updateWorkingFolder(location.getText(), drdd.getFileAndFolder().get(osChoice.getSelectedIndex()).getId());
				createDRDDBox();
			}
			
		});
		
		location.setText(drdd.getFileAndFolder().get(0).getWorkingLocation());
		//Load Components needed for table
		String[] columnNames = {"Location", "Description", "ID"};
		tableData = drdd.getFileAndFolder().get(0).getObject();

		fileModel = new DefaultTableModel(tableData, columnNames){
			 @Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }

		};
		addLocation.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addLocation(drdd.getFileAndFolder().get(osChoice.getSelectedIndex()).getId());
				
			}
			
		});
		fileTable.setModel(fileModel);
		}
		else{
			actionRequested.setText("");
			workPerformed.setText("");
			dataDelivered.setText("");
			DefaultComboBoxModel temp = new DefaultComboBoxModel();
			dataDChoice.setModel(temp);
			osChoice.setModel(temp);
			location.setText("");
			
			String[] columnNames = {"Location", "Description", "id"};
			Object[][] tableData = new Object[0][3];
			
			fileModel = new DefaultTableModel(tableData, columnNames){ 
				@Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }

				};
			fileTable.setModel(fileModel);
		}
		
		
		
		
	}
	
	public void addLocation(final int fileID){
		final MyDialog prompt = new MyDialog(this, "Add New Location?", new Dimension(300, 200));
		JPanel panel = prompt.getPanel();
		
		JPanel topPane = new JPanel();
		topPane.setBackground(new Color(0x57637B));
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 150;
		
		panel.add(topPane, cons);
		
		
		
		panel.setBorder(new EmptyBorder(10,10,10,10));
		
		topPane.setLayout(new GridBagLayout());
		
		JPanel label = new JPanel();
		label.setBackground(null);
		
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weighty = .1;
		cons.ipady = 0;
		cons.weightx = 0;
		
//		topPane.add(label, cons);
		JLabel locationLabel = new JLabel("Location:");
		Font f = new Font(locationLabel.getFont().getFontName(), Font.PLAIN, 12);
		locationLabel.setForeground(Color.white);
		locationLabel.setFont(f);
		
		cons.weightx = 0;
		cons.ipadx = 0;
		cons.weighty = 0;
		cons.gridx = 0;
		cons.gridy = 1;
		
		topPane.add(locationLabel,cons);
		
		cons.fill = GridBagConstraints.HORIZONTAL;
		final JTextField locationName = new JTextField();
		cons.gridx = 1;
		
		cons.ipadx = 300;
		topPane.add(locationName, cons);
		
		
		cons.ipadx = 0;
		
		cons.gridy = 2;
		cons.gridx = 0;
		
		
		
		
		
		cons.ipadx = 0;
		cons.ipady = 0;
		
		final JTextArea description = new JTextArea();
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		JScrollPane textPane = new JScrollPane(description);
		JLabel descLabel = new JLabel("Description:");
		cons.ipadx = 0;
		cons.gridy = 2;
		cons.gridx = 0;
		
		descLabel.setForeground(Color.white);
		cons.fill = GridBagConstraints.NONE;
		topPane.add(descLabel, cons);
		cons.gridx = 1;
		cons.ipadx = 300;
		cons.ipady = 100;
		cons.fill = GridBagConstraints.HORIZONTAL;
		topPane.add(textPane, cons);

		cons.fill = GridBagConstraints.NONE;
		cons.ipadx = 0;
		cons.ipady = 0;
		JButton submit = new JButton("Submit");
		JButton cancel = new JButton("Cancel");
		
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				controller.addLocation(locationName.getText(), description.getText(), fileID);
				createDRDDBox();
				prompt.dispose();
				
				

			}
		});
		
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				prompt.dispose();
				
				

			}
		});

		
		cons.gridx = 0;
		cons.gridy = 2;
		cons.anchor = GridBagConstraints.EAST;
		panel.add(submit, cons);
		cons.anchor = GridBagConstraints.WEST;
		cons.gridx = 0;
		
		panel.add(cancel, cons);
		prompt.setVisible(true);
	}
	
	
	public void createCommentBox(){
		commentBox.setLayout(new GridLayout());
		
		GradientPanel pane = new GradientPanel();
		
		commentBox.add(pane);
		pane.setBorder(new EmptyBorder(10,10,10,10));
		pane.setLayout(new GridLayout());
		
		comments = new JTextPane();
		comments.setEditable(false);
		comments.setContentType("text/html");
		
		JScrollPane scrollPane = new JScrollPane(comments);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setViewportView(comments);
		
		


		pane.add(scrollPane);
		
		commentFooter.setLayout(new GridLayout());
		
		
		final JTextArea input = new JTextArea();
		input.setWrapStyleWord(true);
		input.setLineWrap(true);
		JScrollPane inputScroll = new JScrollPane(input);
		inputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		
		GradientPanel bottomPane = new GradientPanel();
		bottomPane.setBorder(new EmptyBorder(0,10,0,10));
		commentFooter.add(bottomPane);
		
		bottomPane.setLayout(new GridBagLayout());
		bottomPane.add(inputScroll, cons);
		
		cons.anchor = GridBagConstraints.CENTER;
		JButton submitComment = new JButton("Submit");
		
		submitComment.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.addComment((int)treeTable.getValueAt(treeTable.getSelectedRow(), 4), input.getText(),
						(int)treeTable.getPathForRow(treeTable.getSelectedRow()).getPathCount(), comments);
				
			}
			
		});
		
		cons.weightx = 0;
		cons.gridx = 1;
		
		bottomPane.add(submitComment, cons);
		
	}
	
	/**
	 * Prompt user with a dialog box for the essential details of a Project.
	 */
	protected void createNewProject(){
		final MyDialog prompt = new MyDialog(this, "Add New Project?", new Dimension(600, 500));
		JPanel panel = prompt.getPanel();
		
		JPanel topPane = new JPanel();
		topPane.setBackground(new Color(0x57637B));
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 150;
		
		panel.add(topPane, cons);
		
		
		
		panel.setBorder(new EmptyBorder(10,10,10,10));
		
		topPane.setLayout(new GridBagLayout());
		
		JPanel label = new JPanel();
		label.setBackground(null);
		
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weighty = .1;
		cons.ipady = 0;
		cons.weightx = 0;
		
//		topPane.add(label, cons);
		JLabel check = new JLabel("Project Name:");
		Font f = new Font(check.getFont().getFontName(), Font.PLAIN, 12);
		check.setForeground(Color.white);
		check.setFont(f);
		
		cons.weightx = 0;
		cons.ipadx = 0;
		cons.weighty = 0;
		cons.gridx = 0;
		cons.gridy = 1;
		
		topPane.add(check,cons);
		
		final JTextField projectName = new JTextField();
		cons.gridx = 1;
		
		cons.ipadx = 125;
		topPane.add(projectName, cons);
		
		
		
		JLabel unitLabel = new JLabel("Unit:");
		unitLabel.setForeground(Color.white);
		unitLabel.setFont(f);
		
		cons.ipadx = 0;
		cons.gridy = 2;
		cons.gridx = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		
		topPane.add(unitLabel,cons);
		
		ArrayList<Object> unitsList = controller.getUnits();
		final ArrayList<Unit> unitsFull = (ArrayList<Unit>)unitsList.get(0);
		String[] unitNames = (String[])unitsList.get(1);

		//Create the combo box
		final JComboBox<String> units = new JComboBox<String>(unitNames);
		
		cons.gridx = 1;
		
		topPane.add(units, cons);
		cons.fill = GridBagConstraints.HORIZONTAL;
		
		final JLabel startDate = new JLabel("Start Date (mm/dd/yyyy):");
		JLabel endDate = new JLabel("End Date (mm/dd/yyyy):");
		startDate.setForeground(Color.white);
		endDate.setForeground(Color.white);
		startDate.setFont(f);
		endDate.setFont(f);
		
		final JCalendarButton startCalendar = new org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton();
		final JTextField startDateField = new JTextField();
		startDateField.setEditable(false);
		
		
		startCalendar.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date){
					Date temp = (Date)evt.getNewValue();
					DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");

					startDateField.setText(df2.format(temp.getTime()));
				}
				
			}
			
		});
		
		cons.gridy = 3;
		cons.ipadx = 125;
		topPane.add(startDateField,cons);
		cons.ipadx = 0;
		cons.gridx = 2;
		topPane.add(startCalendar,cons);
		
		cons.gridx = 0;
		topPane.add(startDate, cons);
		
		cons.gridy = 4;
		topPane.add(endDate,cons);
		cons.ipadx=125;
		cons.gridx = 1;
		
		final JCalendarButton endCalendar = new org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton();
		final JTextField endDateField = new JTextField();
		endDateField.setEditable(false);
		
		topPane.add(endDateField,cons);
		cons.ipadx = 0;
		cons.gridx = 2;
		topPane.add(endCalendar,cons);
		
		final JCalendarButton expectedCalendar = new org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton();
		final JTextField expectedDateField = new JTextField();
		expectedDateField.setEditable(false);
		
		JLabel expectedDateLabel = new JLabel("Expected End Date");
		expectedDateLabel.setFont(f);
		expectedDateLabel.setForeground(Color.WHITE);
		
		cons.gridx = 0;
		cons.gridy = 5;
		
		topPane.add(expectedDateLabel,cons);
		cons.ipadx = 125;
		cons.gridx = 1;
		topPane.add(expectedDateField, cons);
		
		cons.ipadx = 0;
		cons.gridx = 2;
		topPane.add(expectedCalendar,cons);
		
		expectedCalendar.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date){
					Date temp = (Date)evt.getNewValue();
					DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");

					expectedDateField.setText(df2.format(temp.getTime()));
				}
				
			}
			
		});
		
		cons.gridy = 6;
		cons.gridx = 0;
		
		JLabel reqOrgLabel = new JLabel("Requesting Organization:");
		reqOrgLabel.setForeground(Color.white);
		topPane.add(reqOrgLabel, cons);
		
		final JTextField reqOrg = new JTextField();
		
		cons.ipadx = 125;
		cons.gridx = 1;
		topPane.add(reqOrg, cons);
		
		
		final JTextArea description = new JTextArea();
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		JScrollPane textPane = new JScrollPane(description);
		JLabel descLabel = new JLabel("Description:");
		cons.ipadx = 0;
		cons.gridy = 7;
		cons.gridx = 0;
		
		descLabel.setForeground(Color.white);
		
		topPane.add(descLabel, cons);
		cons.gridx = 1;
		cons.ipadx = 125;
		cons.ipady = 70;
		
		topPane.add(textPane, cons);
		
		
		cons.ipadx = 0;
		cons.ipady = 0;
		endCalendar.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date){
					Date temp = (Date)evt.getNewValue();
					DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
					
					endDateField.setText(df2.format(temp.getTime()));
				}
				
			}
			
		});
		
		JLabel leadLabel = new JLabel("Lead Analyst");
		leadLabel.setForeground(Color.WHITE);
		final ArrayList<Object> lead = uc.getUsers();
		String[] leadName = (String[])lead.get(1);
		final ArrayList<User> listLead = (ArrayList<User>)lead.get(0);
		
		final JComboBox<String> leadAnalyst = new JComboBox<String>(leadName);
		
		cons.gridy = 8;
		cons.gridx = 0;
		topPane.add(leadLabel,cons);
		
		cons.ipadx = 0;
		cons.gridx = 1;
		topPane.add(leadAnalyst,cons);
		

		cons.fill = GridBagConstraints.NONE;
		JButton submit = new JButton("Submit");
		JButton cancel = new JButton("Cancel");
		
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				
				boolean success = controller.addProject(projectName.getText(),startDateField.getText(),endDateField.getText(),
						description.getText(),reqOrg.getText(),unitsFull.get(units.getSelectedIndex()).getUnitID(),
						expectedDateField.getText(), listLead.get(leadAnalyst.getSelectedIndex()));
				
				if(success){
					createProjectTable(false);
					prompt.dispose();
				}
				

			}
		});
		
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				prompt.dispose();
				
				

			}
		});

		
		cons.gridx = 0;
		cons.gridy = 2;
		cons.anchor = GridBagConstraints.EAST;
		panel.add(submit, cons);
		cons.anchor = GridBagConstraints.WEST;
		cons.gridx = 0;
		
		panel.add(cancel, cons);
		prompt.setVisible(true);
		
		
	}
	
	
	
	protected void addNewUser(){
		final MyDialog prompt = new MyDialog(this, "Add New User to System?", new Dimension(500, 300));
		JPanel panel = prompt.getPanel();
		
		JPanel topPane = new JPanel();
		topPane.setBackground(new Color(0x57637B));
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 150;
		
		panel.add(topPane, cons);
		
		
		
		panel.setBorder(new EmptyBorder(10,10,10,10));
		
		topPane.setLayout(new GridBagLayout());
		
		JPanel label = new JPanel();
		label.setBackground(null);
		
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.CENTER;
		cons.weighty = .1;
		cons.ipady = 0;
		cons.weightx = 0;
		
//		topPane.add(label, cons);
		JLabel firstNameLabel = new JLabel("First Name:");
		Font f = new Font(firstNameLabel.getFont().getFontName(), Font.PLAIN, 12);
		firstNameLabel.setForeground(Color.white);
		firstNameLabel.setFont(f);
		
		cons.weightx = 0;
		cons.ipadx = 0;
		cons.weighty = 0;
		cons.gridx = 0;
		cons.gridy = 1;
		
		topPane.add(firstNameLabel,cons);
		
		final JTextField firstName = new JTextField();
		cons.gridx = 1;
		
		cons.ipadx = 200;
		cons.fill = GridBagConstraints.HORIZONTAL;
		topPane.add(firstName, cons);
		
		JLabel lastNameLabel = new JLabel("Last Name:");
		lastNameLabel.setForeground(Color.white);
		lastNameLabel.setFont(f);
		
		cons.weightx = 0;
		cons.ipadx = 0;
		cons.weighty = 0;
		cons.gridx = 0;
		cons.gridy = 2;
		cons.fill = GridBagConstraints.NONE;
		topPane.add(lastNameLabel,cons);
		
		final JTextField lastName = new JTextField();
		cons.gridx = 1;
		
		cons.ipadx = 200;
		cons.fill = GridBagConstraints.HORIZONTAL;
		topPane.add(lastName, cons);
		
		
		
		JLabel role = new JLabel("Role:");
		role.setForeground(Color.white);
		role.setFont(f);
		
		cons.ipadx = 0;
		cons.gridy = 3;
		cons.gridx = 0;
		cons.fill = GridBagConstraints.NONE;
		
		topPane.add(role,cons);
		
		ArrayList<Object> rolesList = controller.getRoles();
		final ArrayList<Role> rolesFull = (ArrayList<Role>)rolesList.get(0);
		String[] rolesNames = (String[])rolesList.get(1);

		//Create the combo box
		final JComboBox<String> roles = new JComboBox<String>(rolesNames);
		
		cons.gridx = 1;
		
		topPane.add(roles, cons);
		cons.fill = GridBagConstraints.NONE;
		
		final JLabel usernameLabel = new JLabel("Username:");
		JLabel passwordLabel = new JLabel("Password:");
		usernameLabel.setForeground(Color.white);
		passwordLabel.setForeground(Color.white);
		usernameLabel.setFont(f);
		passwordLabel.setFont(f);
		
		final JTextField username = new JTextField();
	
		cons.gridy = 4;
		cons.ipadx = 200;
		cons.fill = GridBagConstraints.HORIZONTAL;
		topPane.add(username,cons);
		
		cons.gridx = 0;
		topPane.add(usernameLabel, cons);
		
		cons.gridy = 5;
		topPane.add(passwordLabel,cons);
		cons.ipadx=200;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridx = 1;
		
		final JTextField password = new JPasswordField();

		topPane.add(password,cons);
		cons.ipadx = 0;
		
		cons.gridy = 6;
		cons.gridx = 0;
		
		
		
		
		cons.fill = GridBagConstraints.NONE;
		cons.ipadx = 0;
		cons.ipady = 0;
		

		
		JButton submit = new JButton("Submit");
		JButton cancel = new JButton("Cancel");
		
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				boolean success = uc.add(firstName.getText(), lastName.getText(), username.getText(), password.getText(), rolesFull.get(roles.getSelectedIndex()).getRoleID(), getFrame());
				if(success)
					prompt.dispose();
				
				

			}
		});
		
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				prompt.dispose();
				
				

			}
		});

		
		cons.gridx = 0;
		cons.gridy = 2;
		cons.anchor = GridBagConstraints.EAST;
		panel.add(submit, cons);
		cons.anchor = GridBagConstraints.WEST;
		cons.gridx = 0;
		
		panel.add(cancel, cons);
		prompt.setVisible(true);
	}
	
	protected void addNewUnit(){
		final MyDialog prompt = new MyDialog(this, "Add New Unit?", new Dimension(300, 200));
		JPanel panel = prompt.getPanel();
		
		JPanel topPane = new JPanel();
		topPane.setBackground(new Color(0x57637B));
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 150;
		
		panel.add(topPane, cons);
		
		
		
		panel.setBorder(new EmptyBorder(10,10,10,10));
		
		topPane.setLayout(new GridBagLayout());
		
		JPanel label = new JPanel();
		label.setBackground(null);
		
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weighty = .1;
		cons.ipady = 0;
		cons.weightx = 0;
		
//		topPane.add(label, cons);
		JLabel unitLabel = new JLabel("Unit Name:");
		Font f = new Font(unitLabel.getFont().getFontName(), Font.PLAIN, 12);
		unitLabel.setForeground(Color.white);
		unitLabel.setFont(f);
		
		cons.weightx = 0;
		cons.ipadx = 0;
		cons.weighty = 0;
		cons.gridx = 0;
		cons.gridy = 1;
		
		topPane.add(unitLabel,cons);
		
		final JTextField unit = new JTextField();
		cons.gridx = 1;
		
		cons.ipadx = 300;
		topPane.add(unit, cons);
		
		
		cons.ipadx = 0;
		
		cons.gridy = 2;
		cons.gridx = 0;
		
		
		
		
		
		cons.ipadx = 0;
		cons.ipady = 0;
		
		final JTextArea description = new JTextArea();
		JScrollPane textPane = new JScrollPane(description);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		JLabel descLabel = new JLabel("Description:");
		cons.ipadx = 0;
		cons.gridy = 2;
		cons.gridx = 0;
		
		descLabel.setForeground(Color.white);
		
		topPane.add(descLabel, cons);
		cons.gridx = 1;
		cons.ipadx = 300;
		cons.ipady = 100;
		
		topPane.add(textPane, cons);

		
		cons.ipadx = 0;
		cons.ipady = 0;
		JButton submit = new JButton("Submit");
		JButton cancel = new JButton("Cancel");
		
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				boolean success = controller.addUnit(unit.getText());
				if(success)
					prompt.dispose();
				
				

			}
		});
		
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				prompt.dispose();
				
				

			}
		});

		
		cons.gridx = 0;
		cons.gridy = 2;
		cons.anchor = GridBagConstraints.EAST;
		panel.add(submit, cons);
		cons.anchor = GridBagConstraints.WEST;
		cons.gridx = 0;
		
		panel.add(cancel, cons);
		prompt.setVisible(true);
	}
	
	public TheFrame getFrame(){
		return this;
	}
	
	protected void promptRemoveProject(){
		
		final MyDialog prompt = new MyDialog(this, "Remove Project?",new Dimension(300, 200));
		JPanel panel = prompt.getPanel();
		
		JPanel topPane = new JPanel();
		topPane.setBackground(new Color(0x57637B));
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 150;
		
		panel.add(topPane, cons);
		
		
		
		panel.setBorder(new EmptyBorder(10,10,10,10));
		
		topPane.setLayout(new GridBagLayout());
		
		JPanel label = new JPanel();
		label.setBackground(null);
		
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTH;
		cons.weighty = .1;
		cons.ipady = 0;
		cons.weightx = 1;
		
//		topPane.add(label, cons);
		JLabel check = new JLabel("Are you certain you want to Remove Project?");
		Font f = new Font(check.getFont().getFontName(), Font.PLAIN, 12);
		check.setForeground(Color.white);
		check.setFont(f);
		
		cons.weightx = 0;
		cons.ipadx = 0;
		cons.weighty = 0;
		cons.gridx = 0;
		cons.gridy = 1;
		
		topPane.add(check,cons);
		
		
		JButton remove = new JButton("Remove Project");
		JButton cancel = new JButton("Cancel");
		
		remove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				//Yes, Remove Project
				int row = table.getSelectedRow();
				controller.removeProject(Integer.parseInt(table.getValueAt(row, 0).toString()),table);
				prompt.dispose();
				
				

			}
		});
		
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				//Cancel Removing Project
				prompt.dispose();
				
				

			}
		});

		
		
		cons.gridy = 2;
		cons.anchor = GridBagConstraints.EAST;
		panel.add(remove, cons);
		cons.anchor = GridBagConstraints.WEST;
		cons.gridx = 0;
		
		panel.add(cancel, cons);
		prompt.setVisible(true);
		
		
	}
	
	protected void promptCompleteProject(){
		final MyDialog prompt = new MyDialog(this, "Complete Project?", new Dimension(300, 200));
		JPanel panel = prompt.getPanel();
		
		JPanel topPane = new JPanel();
		topPane.setBackground(new Color(0x57637B));
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 150;
		
		panel.add(topPane, cons);
		
		
		
		panel.setBorder(new EmptyBorder(10,10,10,10));
		
		topPane.setLayout(new GridBagLayout());
		
		JPanel label = new JPanel();
		label.setBackground(null);
		
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTH;
		cons.weighty = .1;
		cons.ipady = 0;
		cons.weightx = 1;
		
//		topPane.add(label, cons);
		JLabel check = new JLabel("Are you certain you want to Complete Selected Project?");
		Font f = new Font(check.getFont().getFontName(), Font.PLAIN, 12);
		check.setForeground(Color.white);
		check.setFont(f);
		
		cons.weightx = 0;
		cons.ipadx = 0;
		cons.weighty = 0;
		cons.gridx = 0;
		cons.gridy = 1;
		
		topPane.add(check,cons);
		
		
		JButton remove = new JButton("Complete Project");
		JButton cancel = new JButton("Cancel");
		
		remove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				//Yes, Complete Project
				int row = table.getSelectedRow();
				controller.completeProject(Integer.parseInt(table.getValueAt(row, 0).toString()),table);
				prompt.dispose();
				
				

			}
		});
		
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				//Cancel Complete Project
				prompt.dispose();
				
				

			}
		});

		
		
		cons.gridy = 2;
		cons.anchor = GridBagConstraints.EAST;
		panel.add(remove, cons);
		cons.anchor = GridBagConstraints.WEST;
		cons.gridx = 0;
		
		panel.add(cancel, cons);
		prompt.setVisible(true);
		
	}
	
	/**
	 * Slide The View to Task Mode.
	 */
	public void runMove(){

		if(onProject){
			if(onMain){
				headerAction.run();
			}
			else{
				while(!mobility.isEmpty()){
					mobility.pop().run();
				}
			}
			if(onMain){
				headerAction.run();
			}
		}
			
		
	}
	
	public void openLogInDialog(){
		test = new MyDialog(this, "Log In", new Dimension(300, 200));
		JPanel panel = test.getPanel();

		
		GridBagConstraints cons = new GridBagConstraints();
		
		
		
		
		
		panel.setLayout(new GridBagLayout());
		
		JPanel label = new JPanel();
		label.setBackground(null);
		
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTH;
		cons.weighty = .1;
		cons.ipady = 0;
		
		panel.add(label, cons);
		
		final JTextField userField = new JTextField();
		userField.setText("username");
		userField.setForeground(new Color(203,203,203));
		
		userField.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				userField.setText("");
				userField.setForeground(Color.BLACK);
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		final JTextField passField = new JPasswordField();
		passField.setText("password");
		passField.setForeground(new Color(203,203,203));
		
		passField.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				passField.setText("");
				passField.setForeground(Color.BLACK);
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});

		
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridy = 1;
		cons.ipadx = 150;
		panel.add(userField, cons);
		
		cons.gridx = 0;
		cons.gridy = 2;
		
		panel.add(passField, cons);
		
		userField.requestFocusInWindow();
		
		cons.weighty = 0;
		cons.gridx = 0;
		cons.gridy = 3;
		cons.ipady = 50;
		cons.ipadx = 0;
		
		passField.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				signIn(userField.getText(), passField.getText());
				
				

			}
		});
		
		JPanel filler = new JPanel();
		filler.setBackground(null);
		panel.add(filler, cons);
		
		cons.gridy = 4;
		cons.ipady = 0;
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.SOUTH;
		JButton signIn = new JButton("Sign In");
		panel.add(signIn, cons);
		
		signIn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				signIn(userField.getText(), passField.getText());
				
				

			}
		});
		
		
		
		
		SwingUtils.fadeIn(test);
	}
	
	private void signIn(String user, String pass){
		uc.signIn(user, pass);
	}
	

	
	
	public void runCompile(int projectID, boolean complete){
		onProject = true;
		if(UserSingleton.getInstance().getRole().getRoleID()==3){
			onProject = controller.onProject(projectID);
		}
		
		if(onProject){
		controller.setProject(projectID);
		addSubtask.setEnabled(false);
		createProjectHeader();
		createDRDDBox();

		
		
		
		root = new TreeNode(new Object[6]);
		CompileTaskTable temp = new CompileTaskTable(root,projectID, complete);
		temp.run();
		

		treeModel.setRoot(root);
		
		TreeTableNode roott = treeModel.getRoot();
		if(UserSingleton.getInstance().getRole().getRoleID()<1 || !(UserSingleton.getInstance().getUserID() == controller.getManager(projectID))){
			addAnalyst.setEnabled(false);
		}
		
		//Listeners for Tool Buttons. If a table cell is selected, Buttons become enabled.
				ListSelectionModel listSelectionModel = treeTable.getSelectionModel();
				listSelectionModel.addListSelectionListener(new ListSelectionListener() {
				        public void valueChanged(ListSelectionEvent e) { 
				            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
				            removeTask.setEnabled(!lsm.isSelectionEmpty());
				            completeTask.setEnabled(!lsm.isSelectionEmpty());
				            addSubtask.setEnabled(!lsm.isSelectionEmpty());
				            taskDetails.setEnabled(!lsm.isSelectionEmpty());
				            taskComments.setEnabled(!lsm.isSelectionEmpty());
				            attachments.setEnabled(!lsm.isSelectionEmpty());
				            
				            
				}});
		
				createAnalystTable();
		}
	}
	
	public void createAnalystTable(){
		String[] columnNames = {"Selected", "First Name", "Last Name", "Role", "User ID"};


		Object[][] tableData = controller.getAnalysts();


		DefaultTableModel analystModel = new DefaultTableModel(tableData, columnNames){
			 @Override
			    public boolean isCellEditable(int row, int col) {
			      switch(col){
			      case 0 :
			    	  return true;
			      default:
			    	  return false;
			    			  
			      }
			    }

		};
		
		
		
		if(analystTable == null){
		//Table Creation
		analystTable = new JXTable(analystModel){
			
			private static final long serialVersionUID = 1L;

        /*@Override
        public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
        }*/
        @Override
        public Class getColumnClass(int column) {
            switch (column) {
                case 0:
                    return Boolean.class;
                case 1:
                    return String.class;
                case 2:
                    return String.class;
                case 3:
                    return String.class;
                default:
                    return String.class;
            }
        }
    };
    
    

		analystTable.setRowSelectionAllowed(false);
		analystTable.setDragEnabled(true);
		analystTable.setCellSelectionEnabled(true);
		analystTable.setColumnSelectionAllowed(true);
		analystTable.setFillsViewportHeight(true);

			JScrollPane scrollPane = new JScrollPane(analystTable);
			analystBox.setLayout(new GridLayout());
			analystBox.add(scrollPane);
		}
		
		//Set Width of Selected Column
//	    analystTable.getColumnModel().getColumn(0).setMaxWidth(50);
	    
			analystTable.setModel(analystModel);
		
	}
	
	/**
	 * Create the task tools in the top left corner when looking at tasks.
	 */
	private void createTaskTools(){


		taskTools.setLayout(new GridLayout());

		JPanel toolPane = new JPanel();
		toolPane.setBackground(Color.white);
		taskTools.add(toolPane);
		toolPane.setLayout(new GridBagLayout());

		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 20;



		JLabel headerTitle= new JLabel("TASK TOOLS");
		headerTitle.setForeground(Color.white);
		headerTitle.setFont(new Font("Sans", Font.BOLD, 14));

		GradientPanel headerTool = new GradientPanel();
		headerTool.setLayout(new GridLayout());
		headerTool.add(headerTitle);
		toolPane.add(headerTool, cons);

		JButton addRow = new JButton("New Task");
		cons.gridx = 0;
		cons.gridy = 1;
		cons.ipady = 0;
		toolPane.add(addRow, cons);
		
		addRow.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				
				createNewTask();

			}
		});
		
		addSubtask = new JButton("New Subtask");
		cons.gridx = 0;
		cons.gridy = 2;
		cons.ipady = 0;
		toolPane.add(addSubtask, cons);
		
		addSubtask.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				createNewSubtask();
				
			}
		});

		removeTask = new JButton("Remove Task");
		cons.gridx = 0;
		cons.gridy = 3;
		toolPane.add(removeTask, cons);
		removeTask.setEnabled(false);
		
		removeTask.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				int node = treeTable.getPathForRow(treeTable.getSelectedRow()).getPathCount();
				removeTask((int)treeTable.getValueAt(treeTable.getSelectedRow(), 4),node);
				
			}
		});

		completeTask = new JButton("Complete Task");
		cons.gridx = 0;
		cons.gridy = 4;
		toolPane.add(completeTask, cons);
		completeTask.setEnabled(false);
		
		completeTask.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				int node = treeTable.getPathForRow(treeTable.getSelectedRow()).getPathCount();
				completeTask((int)treeTable.getValueAt(treeTable.getSelectedRow(), 4),node);
				
			}
		});

		JButton toExcel = new JButton("Export to Excel");
		cons.gridx = 0;
		cons.gridy = 5;
		toolPane.add(toExcel, cons);
		
		toExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				controller.openExcel(treeTable);
				
			}
		});
		
		JButton backToProjects = new JButton("Back to Projects");
		cons.gridx = 0;
		cons.gridy = 6;
		toolPane.add(backToProjects, cons);
		
		backToProjects.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				headerBackAction.run();
				
				
			}
		});
		
		taskComments = new JButton("Task Comments");
		cons.gridx = 0;
		cons.gridy = 7;
		toolPane.add(taskComments, cons);
		taskComments.setEnabled(false);
		
		taskComments.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				controller.loadCommentData(comments, (int)treeTable.getValueAt(treeTable.getSelectedRow(), 4),
						treeTable.getPathForRow(treeTable.getSelectedRow()).getPathCount());
				commentAction.run();
				
				
			}
		});
		
		taskDetails = new JButton("Task Details");
		cons.gridx = 0;
		cons.gridy = 8;
		toolPane.add(taskDetails, cons);
		taskDetails.setEnabled(false);
		
		taskDetails.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				taskDetails();
				
				
			}
		});


		JPanel filler = new JPanel();
		filler.setBackground(Color.white);
		cons.gridx = 0;
		cons.gridy = 9;
		cons.weighty = 1.0;
		toolPane.add(filler, cons);




	}
	
	/**
	 * Create the task tools in the top left corner when looking at tasks.
	 */
	private void createAnalystTools(){


		analystTools.setLayout(new GridLayout());

		JPanel toolPane = new JPanel();
		toolPane.setBackground(Color.white);
		analystTools.add(toolPane);
		toolPane.setLayout(new GridBagLayout());

		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 20;



		JLabel headerTitle= new JLabel("ANALYST TOOLS");
		headerTitle.setForeground(Color.white);
		headerTitle.setFont(new Font("Sans", Font.BOLD, 14));

		GradientPanel headerTool = new GradientPanel();
		headerTool.setLayout(new GridLayout());
		headerTool.add(headerTitle);
		toolPane.add(headerTool, cons);

		JButton addRow = new JButton("Use Selected Analysts");
		cons.gridx = 0;
		cons.gridy = 1;
		cons.ipady = 0;
		toolPane.add(addRow, cons);
		
		addRow.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				controller.addAnalysts(analystTable);
				createProjectTable(false);
				

			}
		});
		
		JButton back = new JButton("Back To Project");
		cons.gridx = 0;
		cons.gridy = 2;
		cons.ipady = 0;
		toolPane.add(back, cons);
		
		back.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				analystBackAction.run();
				
			}
		});


		JButton toExcel = new JButton("Export to Excel");
		cons.gridx = 0;
		cons.gridy = 3;
		toolPane.add(toExcel, cons);
		
		toExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				controller.openExcel(analystTable);
				
			}
		});

		JPanel filler = new JPanel();
		filler.setBackground(Color.white);
		cons.gridx = 0;
		cons.gridy = 4;
		cons.weighty = 1.0;
		toolPane.add(filler, cons);




	}
	
	private void createAttachTools(){
		attachTools.setLayout(new GridLayout());

		JPanel toolPane = new JPanel();
		toolPane.setBackground(Color.white);
		attachTools.add(toolPane);
		toolPane.setLayout(new GridBagLayout());

		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 20;



		JLabel headerTitle= new JLabel("ATTACHMENT TOOLS");
		headerTitle.setForeground(Color.white);
		headerTitle.setFont(new Font("Sans", Font.BOLD, 14));

		GradientPanel headerTool = new GradientPanel();
		headerTool.setLayout(new GridLayout());
		headerTool.add(headerTitle);
		toolPane.add(headerTool, cons);

		JButton addAtt = new JButton("Add Attachment");
		cons.gridx = 0;
		cons.gridy = 1;
		cons.ipady = 0;
		toolPane.add(addAtt, cons);
		
		addAtt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				addAttachment();
				

			}
		});
		
		removeAtt = new JButton("Remove Attachment");
		cons.gridx = 0;
		cons.gridy = 2;
		cons.ipady = 0;
		toolPane.add(removeAtt, cons);
		
		removeAtt.setEnabled(false);
		removeAtt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				removeAttachment((int)attachmentTable.getValueAt(attachmentTable.getSelectedRow(), 3));
				
				

			}
		});
		
		downloadAtt = new JButton("Download Attachment");
		cons.gridx = 0;
		cons.gridy = 3;
		cons.ipady = 0;
		toolPane.add(downloadAtt, cons);
		
		downloadAtt.setEnabled(false);
		downloadAtt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				controller.downloadAttachment((int)attachmentTable.getValueAt(attachmentTable.getSelectedRow(), 3));
				
				

			}
		});
		
		JButton back = new JButton("Back To Project");
		cons.gridx = 0;
		cons.gridy = 4;
		cons.ipady = 0;
		toolPane.add(back, cons);
		
		back.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				attachBackAction.run();
				
			}
		});


		JButton toExcel = new JButton("Export to Excel");
		cons.gridx = 0;
		cons.gridy = 5;
		toolPane.add(toExcel, cons);
		
		toExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				controller.openExcel(attachmentTable);
				
			}
		});

		JPanel filler = new JPanel();
		filler.setBackground(Color.white);
		cons.gridx = 0;
		cons.gridy = 6;
		cons.weighty = 1.0;
		toolPane.add(filler, cons);

	}
	
	protected void createCommentTools(){
		commentTools.setLayout(new GridLayout());

		JPanel toolPane = new JPanel();
		toolPane.setBackground(Color.white);
		commentTools.add(toolPane);
		toolPane.setLayout(new GridBagLayout());

		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 20;
		
		JLabel headerTitle= new JLabel("COMMENT TOOLS");
		headerTitle.setForeground(Color.white);
		headerTitle.setFont(new Font("Sans", Font.BOLD, 14));

		GradientPanel headerTool = new GradientPanel();
		headerTool.setLayout(new GridLayout());
		headerTool.add(headerTitle);
		toolPane.add(headerTool, cons);
		
		JButton back = new JButton("Back To Project");
		cons.gridx = 0;
		cons.gridy = 1;
		cons.ipady = 0;
		toolPane.add(back, cons);
		
		back.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				commentBackAction.run();
				
			}
		});


		JPanel filler = new JPanel();
		filler.setBackground(Color.white);
		cons.gridx = 0;
		cons.gridy = 2;
		cons.weighty = 1.0;
		toolPane.add(filler, cons);

	}
	
	protected void createDRDDTools(){
		DRDDTools.setLayout(new GridLayout());

		JPanel toolPane = new JPanel();
		toolPane.setBackground(Color.white);
		DRDDTools.add(toolPane);
		toolPane.setLayout(new GridBagLayout());

		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 20;
		
		JLabel headerTitle= new JLabel("DRDD TOOLS");
		headerTitle.setForeground(Color.white);
		headerTitle.setFont(new Font("Sans", Font.BOLD, 14));

		GradientPanel headerTool = new GradientPanel();
		headerTool.setLayout(new GridLayout());
		headerTool.add(headerTitle);
		toolPane.add(headerTool, cons);
		
		JButton back = new JButton("Back To Project");
		cons.gridx = 0;
		cons.gridy = 1;
		cons.ipady = 0;
		toolPane.add(back, cons);
		
		back.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				DRDDBackAction.run();
				
			}
		});


		JPanel filler = new JPanel();
		filler.setBackground(Color.white);
		cons.gridx = 0;
		cons.gridy = 2;
		cons.weighty = 1.0;
		toolPane.add(filler, cons);

	}

	
	/**
	 * Display Dialog, and remove attachments from both visual and database.
	 * @param value ID of value to be removed
	 * @param node Determines whether value is parent or child.
	 */
	protected void removeAttachment(final int value){


			
			final MyDialog prompt = new MyDialog(this, "Remove Attachment?", new Dimension(300, 200));
			JPanel panel = prompt.getPanel();
			
			JPanel topPane = new JPanel();
			topPane.setBackground(new Color(0x57637B));
			
			GridBagConstraints cons = new GridBagConstraints();
			cons.fill = GridBagConstraints.HORIZONTAL;
			cons.weightx = 1;
			cons.weighty = 1;
			cons.anchor = GridBagConstraints.NORTHWEST;
			cons.gridx = 0;
			cons.gridy = 0;
			cons.ipady = 150;
			
			panel.add(topPane, cons);
			
			
			
			panel.setBorder(new EmptyBorder(10,10,10,10));
			
			topPane.setLayout(new GridBagLayout());
			
			JPanel label = new JPanel();
			label.setBackground(null);
			
			cons.fill = GridBagConstraints.NONE;
			cons.anchor = GridBagConstraints.NORTH;
			cons.weighty = .1;
			cons.ipady = 0;
			cons.weightx = 1;
			
//			topPane.add(label, cons);
			JLabel check = new JLabel("Are you certain you want to Remove Attachment?");
			Font f = new Font(check.getFont().getFontName(), Font.PLAIN, 12);
			check.setForeground(Color.white);
			check.setFont(f);
			
			cons.weightx = 0;
			cons.ipadx = 0;
			cons.weighty = 0;
			cons.gridx = 0;
			cons.gridy = 1;
			
			topPane.add(check,cons);
			
			
			JButton remove = new JButton("Remove Attachment");
			JButton cancel = new JButton("Cancel");
			
			remove.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					
					//Yes, Remove Attachment
					controller.removeAttachment((int)attachmentTable.getValueAt(attachmentTable.getSelectedRow(), 3));
					prompt.dispose();
					
					

				}
			});
			
			cancel.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					
					//Cancel Removing Attachment
					prompt.dispose();
					
					

				}
			});

			
			
			cons.gridy = 2;
			cons.anchor = GridBagConstraints.EAST;
			panel.add(remove, cons);
			cons.anchor = GridBagConstraints.WEST;
			cons.gridx = 0;
			
			panel.add(cancel, cons);
			prompt.setVisible(true);
			
			
		
		}

	
	
	private void createAdvSearchTools(){
		advSearchTools.setLayout(new GridLayout());

		JPanel toolPane = new JPanel();
		toolPane.setBackground(Color.white);
		advSearchTools.add(toolPane);
		toolPane.setLayout(new GridBagLayout());

		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 20;



		JLabel headerTitle= new JLabel("SEARCH TOOLS");
		headerTitle.setForeground(Color.white);
		headerTitle.setFont(new Font("Sans", Font.BOLD, 14));

		GradientPanel headerTool = new GradientPanel();
		headerTool.setLayout(new GridLayout());
		headerTool.add(headerTitle);
		toolPane.add(headerTool, cons);
		
		JButton back = new JButton("Back To Projects");
		cons.gridx = 0;
		cons.gridy = 1;
		cons.ipady = 0;
		toolPane.add(back, cons);
		
		back.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				advBackAction.run();
				
			}
		});


		JButton toExcel = new JButton("Export to Excel");
		cons.gridx = 0;
		cons.gridy = 2;
		toolPane.add(toExcel, cons);
		
		toExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				controller.openExcel(analystTable);
				
			}
		});

		JPanel filler = new JPanel();
		filler.setBackground(Color.white);
		cons.gridx = 0;
		cons.gridy = 3;
		cons.weighty = 1.0;
		toolPane.add(filler, cons);
	}
	
	protected void addAttachment(){
		final MyDialog prompt = new MyDialog(this, "Add New Attachment?", new Dimension(500, 400));
		JPanel panel = prompt.getPanel();
		
		JPanel topPane = new JPanel();
		topPane.setBackground(new Color(0x57637B));
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 150;
		
		panel.add(topPane, cons);
		
		
		
		panel.setBorder(new EmptyBorder(10,10,10,10));
		
		topPane.setLayout(new GridBagLayout());
		
		JPanel label = new JPanel();
		label.setBackground(null);
		
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weighty = .1;
		cons.ipady = 0;
		cons.weightx = 0;
		
//		topPane.add(label, cons);
		JLabel check = new JLabel("Attachment Title:");
		Font f = new Font(check.getFont().getFontName(), Font.PLAIN, 12);
		check.setForeground(Color.white);
		check.setFont(f);
		
		cons.weightx = 0;
		cons.ipadx = 0;
		cons.weighty = 0;
		cons.gridx = 0;
		cons.gridy = 1;
		
		topPane.add(check,cons);
		
		final JTextField taskName = new JTextField();
		cons.gridx = 1;
		
		cons.ipadx = 250;
		topPane.add(taskName, cons);
		
		JLabel chooseFileLabel = new JLabel("Attach File:");
		chooseFileLabel.setForeground(Color.white);
		chooseFileLabel.setFont(f);
		
		cons.weightx = 0;
		cons.ipadx = 0;
		cons.weighty = 0;
		cons.gridx = 0;
		cons.gridy = 2;
		
		topPane.add(chooseFileLabel,cons);
		
		final JTextField fileLocation = new JTextField();
		cons.gridx = 1;
		
		cons.ipadx = 250;
		topPane.add(fileLocation, cons);
		
		JButton selectFile = new JButton("Select File");
		
		cons.gridx = 2;
		cons.ipadx = 0;
		topPane.add(selectFile, cons);
		selectFile.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				 file = selectFile();
				 fileLocation.setText(file.getName());
				
				
			}
			
		});
		
				

		
		JButton submit = new JButton("Submit");
		JButton cancel = new JButton("Cancel");
		
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				if(file!=null){
				boolean success = controller.addAttachment(taskName.getText(),file, treeTable);
				if(success)
					prompt.dispose();
				}else{
					JOptionPane.showMessageDialog(getFrame(), "You have not choosen a folder");
				}
				
				

			}
		});
		
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				prompt.dispose();
				
				

			}
		});

		
		cons.gridx = 0;
		cons.gridy = 2;
		cons.anchor = GridBagConstraints.EAST;
		panel.add(submit, cons);
		cons.anchor = GridBagConstraints.WEST;
		cons.gridx = 0;
		
		panel.add(cancel, cons);
		prompt.setVisible(true);
	}
	
	
	
	protected File selectFile(){
	JFileChooser chooser = new JFileChooser(); 
    if (chooser.showOpenDialog(new Dialog(this)) == JFileChooser.APPROVE_OPTION) { 
      return chooser.getSelectedFile();
      
		
	}else{
		return null;
	}
}
	
	protected File selectFolder(){
		JFileChooser chooser = new JFileChooser(); 
		 chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    
	    if (chooser.showOpenDialog(new Dialog(this)) == JFileChooser.APPROVE_OPTION) { 
	      return chooser.getSelectedFile();
	      
			
		}else{
			return null;
		}
	}
	
	protected void addAnalyst(){
		analystAction.run();
	}
	
	protected void createNewTask(){
		final MyDialog prompt = new MyDialog(this, "Add New Task?", new Dimension(600, 500));
		JPanel panel = prompt.getPanel();
		
		JPanel topPane = new JPanel();
		topPane.setBackground(new Color(0x57637B));
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 150;
		
		panel.add(topPane, cons);
		
		
		
		panel.setBorder(new EmptyBorder(10,10,10,10));
		
		topPane.setLayout(new GridBagLayout());
		
		JPanel label = new JPanel();
		label.setBackground(null);
		
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weighty = .1;
		cons.ipady = 0;
		cons.weightx = 0;
		
//		topPane.add(label, cons);
		JLabel check = new JLabel("Task Name:");
		Font f = new Font(check.getFont().getFontName(), Font.PLAIN, 12);
		check.setForeground(Color.white);
		check.setFont(f);
		
		cons.weightx = 0;
		cons.ipadx = 0;
		cons.weighty = 0;
		cons.gridx = 0;
		cons.gridy = 1;
		
		topPane.add(check,cons);
		
		final JTextField taskName = new JTextField();
		cons.gridx = 1;
		
		cons.ipadx = 125;
		topPane.add(taskName, cons);
		
		
		
		JLabel unitLabel = new JLabel("Assigned To:");
		unitLabel.setForeground(Color.white);
		unitLabel.setFont(f);
		
		cons.ipadx = 0;
		cons.gridy = 2;
		cons.gridx = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		
		topPane.add(unitLabel,cons);
		
		ArrayList<Object> analystList = uc.getProjectAnalysts();
		final ArrayList<User> analystFull = (ArrayList<User>)analystList.get(0);
		String[] analystNames = (String[])analystList.get(1);

		//Create the combo box
		final JComboBox<String> units = new JComboBox<String>(analystNames);
		
		cons.gridx = 1;
		
		topPane.add(units, cons);
		cons.fill = GridBagConstraints.HORIZONTAL;
		
		final JLabel startDate = new JLabel("Start Date (mm/dd/yyyy):");
		JLabel endDate = new JLabel("End Date (mm/dd/yyyy):");
		startDate.setForeground(Color.white);
		endDate.setForeground(Color.white);
		startDate.setFont(f);
		endDate.setFont(f);
		
		final JCalendarButton startCalendar = new org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton();
		final JTextField startDateField = new JTextField();
		startDateField.setEditable(false);
		
		
		startCalendar.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date){
					Date temp = (Date)evt.getNewValue();
					DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");

					startDateField.setText(df2.format(temp.getTime()));
				}
				
			}
			
		});
		
		cons.gridy = 3;
		cons.ipadx = 125;
		topPane.add(startDateField,cons);
		cons.ipadx = 0;
		cons.gridx = 2;
		topPane.add(startCalendar,cons);
		
		cons.gridx = 0;
		topPane.add(startDate, cons);
		
		cons.gridy = 4;
		topPane.add(endDate,cons);
		cons.ipadx=125;
		cons.gridx = 1;
		
		final JCalendarButton endCalendar = new org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton();
		final JTextField endDateField = new JTextField();
		endDateField.setEditable(false);
		
		topPane.add(endDateField,cons);
		cons.ipadx = 0;
		cons.gridx = 2;
		topPane.add(endCalendar,cons);
		
		final JCalendarButton expectedCalendar = new org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton();
		final JTextField expectedDateField = new JTextField();
		expectedDateField.setEditable(false);
		
		JLabel expectedDateLabel = new JLabel("Expected End Date");
		expectedDateLabel.setFont(f);
		expectedDateLabel.setForeground(Color.WHITE);
		
		cons.gridx = 0;
		cons.gridy = 5;
		
		topPane.add(expectedDateLabel,cons);
		cons.ipadx = 125;
		cons.gridx = 1;
		topPane.add(expectedDateField, cons);
		
		cons.ipadx = 0;
		cons.gridx = 2;
		topPane.add(expectedCalendar,cons);
		
		expectedCalendar.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date){
					Date temp = (Date)evt.getNewValue();
					DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");

					expectedDateField.setText(df2.format(temp.getTime()));
				}
				
			}
			
		});
		
		cons.gridy = 6;
		cons.gridx = 0;
		
		
		final JTextArea description = new JTextArea();
		JScrollPane textPane = new JScrollPane(description);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		
		JLabel descLabel = new JLabel("Description:");
		cons.ipadx = 0;
		cons.gridy = 7;
		cons.gridx = 0;
		
		descLabel.setForeground(Color.white);
		
		topPane.add(descLabel, cons);
		cons.gridx = 1;
		cons.ipadx = 125;
		cons.ipady = 70;
		
		topPane.add(textPane, cons);
		
		
		cons.ipadx = 0;
		cons.ipady = 0;
		endCalendar.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date){
					Date temp = (Date)evt.getNewValue();
					DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
					
					endDateField.setText(df2.format(temp.getTime()));
				}
				
			}
			
		});
		
		cons.fill = GridBagConstraints.NONE;
		
		JButton submit = new JButton("Submit");
		JButton cancel = new JButton("Cancel");
		
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				boolean success = controller.addTask(taskName.getText(),startDateField.getText(),endDateField.getText(),
						description.getText(),analystFull.get(units.getSelectedIndex()).getUserID(), expectedDateField.getText());
				
				if(success)
					prompt.dispose();
				
				

			}
		});
		
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				prompt.dispose();
				
				

			}
		});

		
		cons.gridx = 0;
		cons.gridy = 2;
		cons.anchor = GridBagConstraints.EAST;
		panel.add(submit, cons);
		cons.anchor = GridBagConstraints.WEST;
		cons.gridx = 0;
		
		panel.add(cancel, cons);
		prompt.setVisible(true);
	}
	
	protected void createNewSubtask(){
		final MyDialog prompt = new MyDialog(this, "Add New Subtask?", new Dimension(600, 500));
		JPanel panel = prompt.getPanel();
		
		JPanel topPane = new JPanel();
		topPane.setBackground(new Color(0x57637B));
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 150;
		
		panel.add(topPane, cons);
		
		
		
		panel.setBorder(new EmptyBorder(10,10,10,10));
		
		topPane.setLayout(new GridBagLayout());
		
		JPanel label = new JPanel();
		label.setBackground(null);
		
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.weighty = .1;
		cons.ipady = 0;
		cons.weightx = 0;
		
//		topPane.add(label, cons);
		JLabel check = new JLabel("Subtask Name:");
		Font f = new Font(check.getFont().getFontName(), Font.PLAIN, 12);
		check.setForeground(Color.white);
		check.setFont(f);
		
		cons.weightx = 0;
		cons.ipadx = 0;
		cons.weighty = 0;
		cons.gridx = 0;
		cons.gridy = 1;
		
		topPane.add(check,cons);
		
		final JTextField taskName = new JTextField();
		cons.gridx = 1;
		
		cons.ipadx = 125;
		topPane.add(taskName, cons);
		
		
		
		JLabel unitLabel = new JLabel("Assigned To:");
		unitLabel.setForeground(Color.white);
		unitLabel.setFont(f);
		
		cons.ipadx = 0;
		cons.gridy = 2;
		cons.gridx = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		
		topPane.add(unitLabel,cons);
		
		ArrayList<Object> analystList = uc.getProjectAnalysts();
		final ArrayList<User> analystFull = (ArrayList<User>)analystList.get(0);
		String[] analystNames = (String[])analystList.get(1);

		//Create the combo box
		final JComboBox<String> units = new JComboBox<String>(analystNames);
		
		cons.gridx = 1;
		
		topPane.add(units, cons);
		cons.fill = GridBagConstraints.HORIZONTAL;
		
		final JLabel startDate = new JLabel("Start Date:");
		JLabel endDate = new JLabel("End Date:");
		startDate.setForeground(Color.white);
		endDate.setForeground(Color.white);
		startDate.setFont(f);
		endDate.setFont(f);
		
		final JCalendarButton startCalendar = new org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton();
		final JTextField startDateField = new JTextField();
		startDateField.setEditable(false);
		
		
		startCalendar.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date){
					Date temp = (Date)evt.getNewValue();
					DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");

					startDateField.setText(df2.format(temp.getTime()));
				}
				
			}
			
		});
		
		cons.gridy = 3;
		cons.ipadx = 125;
		topPane.add(startDateField,cons);
		cons.ipadx = 0;
		cons.gridx = 2;
		topPane.add(startCalendar,cons);
		
		cons.gridx = 0;
		topPane.add(startDate, cons);
		
		cons.gridy = 4;
		topPane.add(endDate,cons);
		cons.ipadx=125;
		cons.gridx = 1;
		
		final JCalendarButton endCalendar = new org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton();
		final JTextField endDateField = new JTextField();
		endDateField.setEditable(false);
		
		topPane.add(endDateField,cons);
		cons.ipadx = 0;
		cons.gridx = 2;
		topPane.add(endCalendar,cons);
		
		final JCalendarButton expectedCalendar = new org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton();
		final JTextField expectedDateField = new JTextField();
		expectedDateField.setEditable(false);
		
		JLabel expectedDateLabel = new JLabel("Expected End Date");
		expectedDateLabel.setFont(f);
		expectedDateLabel.setForeground(Color.WHITE);
		
		cons.gridx = 0;
		cons.gridy = 5;
		
		topPane.add(expectedDateLabel,cons);
		cons.ipadx = 125;
		cons.gridx = 1;
		topPane.add(expectedDateField, cons);
		
		cons.ipadx = 0;
		cons.gridx = 2;
		topPane.add(expectedCalendar,cons);
		
		expectedCalendar.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date){
					Date temp = (Date)evt.getNewValue();
					DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");

					expectedDateField.setText(df2.format(temp.getTime()));
				}
				
			}
			
		});
		
		cons.gridy = 6;
		cons.gridx = 0;
		
		
		final JTextArea description = new JTextArea();
		JScrollPane textPane = new JScrollPane(description);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		JLabel descLabel = new JLabel("Description:");
		cons.ipadx = 0;
		cons.gridy = 7;
		cons.gridx = 0;
		
		descLabel.setForeground(Color.white);
		
		topPane.add(descLabel, cons);
		cons.gridx = 1;
		cons.ipadx = 125;
		cons.ipady = 70;
		
		topPane.add(textPane, cons);
		
		
		cons.ipadx = 0;
		cons.ipady = 0;
		endCalendar.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date){
					Date temp = (Date)evt.getNewValue();
					DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
					
					endDateField.setText(df2.format(temp.getTime()));
				}
				
			}
			
		});
		
		cons.fill = GridBagConstraints.NONE;
		
		JButton submit = new JButton("Submit");
		JButton cancel = new JButton("Cancel");
		
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				boolean success = controller.addSubtask(taskName.getText(),startDateField.getText(),endDateField.getText(), 
						description.getText(),analystFull.get(units.getSelectedIndex()).getUserID(), 
						(int)treeTable.getValueAt(treeTable.getSelectedRow(), 4), expectedDateField.getText(), treeTable);

				if(success)
					prompt.dispose();
				
				

			}
		});
		
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				prompt.dispose();
				
				

			}
		});

		
		cons.gridx = 0;
		cons.gridy = 2;
		cons.anchor = GridBagConstraints.EAST;
		panel.add(submit, cons);
		cons.anchor = GridBagConstraints.WEST;
		cons.gridx = 0;
		
		panel.add(cancel, cons);
		prompt.setVisible(true);
	}
	
	/**
	 * Display Dialog, and remove tasks from both visual and database.
	 * @param value ID of value to be removed
	 * @param node Determines whether value is parent or child.
	 */
	protected void removeTask(final int value, final int node){
		
		final MyDialog prompt = new MyDialog(this, "Remove Task?", new Dimension(300, 200));
		JPanel panel = prompt.getPanel();
		
		JPanel topPane = new JPanel();
		topPane.setBackground(new Color(0x57637B));
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 150;
		
		panel.add(topPane, cons);
		
		
		
		panel.setBorder(new EmptyBorder(10,10,10,10));
		
		topPane.setLayout(new GridBagLayout());
		
		JPanel label = new JPanel();
		label.setBackground(null);
		
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTH;
		cons.weighty = .1;
		cons.ipady = 0;
		cons.weightx = 1;
		
//		topPane.add(label, cons);
		JLabel check = new JLabel("Are you certain you want to Remove Task?");
		Font f = new Font(check.getFont().getFontName(), Font.PLAIN, 12);
		check.setForeground(Color.white);
		check.setFont(f);
		
		cons.weightx = 0;
		cons.ipadx = 0;
		cons.weighty = 0;
		cons.gridx = 0;
		cons.gridy = 1;
		
		topPane.add(check,cons);
		
		
		JButton remove = new JButton("Remove Task");
		JButton cancel = new JButton("Cancel");
		
		remove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				//Yes, Remove Project
				controller.removeTask(value,node);
				prompt.dispose();
				
				

			}
		});
		
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				//Cancel Removing Project
				prompt.dispose();
				
				

			}
		});

		
		
		cons.gridy = 2;
		cons.anchor = GridBagConstraints.EAST;
		panel.add(remove, cons);
		cons.anchor = GridBagConstraints.WEST;
		cons.gridx = 0;
		
		panel.add(cancel, cons);
		prompt.setVisible(true);
		
		
	
	}
	
	/**
	 * Display Dialog, and remove tasks from both visual and database.
	 * @param value ID of value to be removed
	 * @param node Determines whether value is parent or child.
	 */
	protected void completeTask(final int value, final int node){
		
		final MyDialog prompt = new MyDialog(this, "Complete Task?", new Dimension(300, 200));
		JPanel panel = prompt.getPanel();
		
		JPanel topPane = new JPanel();
		topPane.setBackground(new Color(0x57637B));
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.weighty = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 150;
		
		panel.add(topPane, cons);
		
		
		
		panel.setBorder(new EmptyBorder(10,10,10,10));
		
		topPane.setLayout(new GridBagLayout());
		
		JPanel label = new JPanel();
		label.setBackground(null);
		
		cons.fill = GridBagConstraints.NONE;
		cons.anchor = GridBagConstraints.NORTH;
		cons.weighty = .1;
		cons.ipady = 0;
		cons.weightx = 1;
		
//		topPane.add(label, cons);
		JLabel check = new JLabel("Are you certain you want to Complete Task?");
		Font f = new Font(check.getFont().getFontName(), Font.PLAIN, 12);
		check.setForeground(Color.white);
		check.setFont(f);
		
		cons.weightx = 0;
		cons.ipadx = 0;
		cons.weighty = 0;
		cons.gridx = 0;
		cons.gridy = 1;
		
		topPane.add(check,cons);
		
		
		JButton remove = new JButton("Complete Task");
		JButton cancel = new JButton("Cancel");
		
		remove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				//Yes, Remove Project
				int row = table.getSelectedRow();
				controller.completeTask(value,node);
				prompt.dispose();
				
				

			}
		});
		
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				//Cancel Removing Project
				prompt.dispose();
				
				

			}
		});

		
		
		cons.gridy = 2;
		cons.anchor = GridBagConstraints.EAST;
		panel.add(remove, cons);
		cons.anchor = GridBagConstraints.WEST;
		cons.gridx = 0;
		
		panel.add(cancel, cons);
		prompt.setVisible(true);
		
		
	
	}

	public void createYourProjects(){
		currentProjects.removeAll();
		
		currentProjects.setLayout(new GridLayout());
		

		

		
		JPanel toolPane = new JPanel();
		toolPane.setBackground(Color.white);
		currentProjects.add(toolPane);
		toolPane.setLayout(new GridBagLayout());

		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 20;



		JLabel headerTitle= new JLabel("CURRENT PROJECTS");
		headerTitle.setForeground(Color.white);
		headerTitle.setFont(new Font("Sans", Font.BOLD, 14));

		GradientPanel headerTool = new GradientPanel();
		headerTool.setLayout(new GridLayout());
		headerTool.add(headerTitle);
		toolPane.add(headerTool, cons);

		ArrayList<Project> myProjects = controller.getUserProjects();
		int i = 1;
		for(Project project:myProjects){

			IconPanel icon = new IconPanel("data/project.jpg", project.getProjectName(), project.getProjectID(),this);	
			cons.gridx = 0;
			cons.gridy = i;
			toolPane.add(icon, cons);
			if(i>=13){
				break;
			}
			
			i++;
			
		}






		JPanel filler = new JPanel();
		filler.setBackground(Color.white);
		cons.gridx = 0;
		cons.gridy = ++i;
		cons.weighty = 1.0;
		toolPane.add(filler, cons);
	}

	public void createDeadlines(){
		deadlines.removeAll();
		deadlines.setLayout(new GridLayout());


		JPanel toolPane = new JPanel();
		toolPane.setBackground(Color.white);
		deadlines.add(toolPane);
		toolPane.setLayout(new GridBagLayout());

		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 20;



		JLabel headerTitle= new JLabel("UPCOMING DEADLINES");
		headerTitle.setForeground(Color.white);
		headerTitle.setFont(new Font("Sans", Font.BOLD, 14));

		GradientPanel headerTool = new GradientPanel();
		headerTool.setLayout(new GridLayout());
		headerTool.add(headerTitle);
		toolPane.add(headerTool, cons);
		
		ArrayList<TaskIF> deadlines = controller.getDeadlineTasks();
		
		

		int i = 1;
		for(TaskIF project:deadlines){

			IconPanel icon = new IconPanel("data/time.png", project.getTaskName(), project.getProjectID(),this);	
			cons.gridx = 0;
			cons.gridy = i;
			toolPane.add(icon, cons);
			if(i>=6){
				break;
			}
			i++;
			
		}

		JPanel filler = new JPanel();
		filler.setBackground(Color.white);
		cons.gridx = 0;
		cons.gridy = ++i;
		cons.weighty = 1.0;
		toolPane.add(filler, cons);
	}

	public void createNewestTasks(){
		newestTasks.removeAll();
		newestTasks.setLayout(new GridLayout());


		JPanel toolPane = new JPanel();
		toolPane.setBackground(Color.white);
		newestTasks.add(toolPane);
		toolPane.setLayout(new GridBagLayout());

		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.ipady = 20;



		JLabel headerTitle= new JLabel("YOUR TASKS");
		headerTitle.setForeground(Color.white);
		headerTitle.setFont(new Font("Sans", Font.BOLD, 14));

		GradientPanel headerTool = new GradientPanel();
		headerTool.setLayout(new GridLayout());
		headerTool.add(headerTitle);
		toolPane.add(headerTool, cons);

		ArrayList<Task> tasks = controller.getUserAssignedTask(false);
		ArrayList<Subtask> subtasks = controller.getUserAssignedSubtask(false);
		int biggest = tasks.size()>subtasks.size()?tasks.size():subtasks.size();
		int j = 0;
		for(int i = 0; i<biggest; i++){

			
			if(i<tasks.size()){
				Task task = tasks.get(i);
				IconPanel taskIcon = new IconPanel("data/checkmark.png", task.getTaskName(), task.getProjectID(),this);	
				cons.gridx = 0;
				cons.gridy = ++j;
				toolPane.add(taskIcon, cons);
			}
			
			if(i<subtasks.size()){
				Subtask subtask = subtasks.get(i);
				IconPanel subtaskIcon = new IconPanel("data/checkmark.png", subtask.getTaskName(), subtask.getProjectID(),this);
				cons.gridx = 0;
				cons.gridy = ++j;
				toolPane.add(subtaskIcon, cons);
			}
			
			if(j>=11){
				break;
			}
		}

		
		
		

		JPanel filler = new JPanel();
		filler.setBackground(Color.white);
		cons.gridx = 0;
		cons.gridy = ++j;
		cons.weighty = 1.0;
		toolPane.add(filler, cons);
	}

	private void createFooter() {
		footer.removeAll();

		footer.setBackground(new Color(0xBAC8D9));
		footer.setBorder(null);
		//		footer.setBorder(new EmptyBorder(4, 10, 5, 10));
		footer.setLayout(new GridLayout());

		JLabel user = new JLabel("  Currently User: " + UserSingleton.getInstance().getFirstName() + " " + UserSingleton.getInstance().getLastName() );
		user.setHorizontalAlignment(SwingConstants.LEFT);
		footer.add(user);
		
		setSearchResults(table.getRowCount());
		
	}
	
	public void setSearchResults(int value){
		JLabel searchResults = new JLabel("Results Found: " + value + "  ");
		searchResults.setHorizontalAlignment(SwingConstants.RIGHT);
		footer.add(searchResults);
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

	private void disableActions() {
		header.disableAction();
		currentProjects.disableAction();
		tools.disableAction();
		tableBox.disableAction();
		deadlines.disableAction();
	}

	private void enableActions() {
		header.enableAction();
		currentProjects.enableAction();
		tools.enableAction();
		tableBox.enableAction();
		deadlines.enableAction();
	}

	private final Runnable headerAction = new Runnable() {@Override public void run() {
		disableActions();
		previousProject.setEnabled(true);
		onTask = true;
		onMain = false;
		
		panel.createTransition()
		.push(new SLKeyframe(headerCfg, 0.6f)
		//.setEndSide(SLSide.TOP, header)
		.setEndSide(SLSide.BOTTOM, tableBox)
		.setEndSide(SLSide.LEFT, tools)
		.setEndSide(SLSide.TOP, searchBar)
		.setEndSide(SLSide.TOP, header)
		.setStartSide(SLSide.BOTTOM, taskBox)
		.setStartSide(SLSide.TOP, taskBar)
		.setStartSide(SLSide.TOP, headerName)
		.setStartSide(SLSide.LEFT, taskTools)
		.setCallback(new SLKeyframe.Callback() {@Override public void done() {
			header.setAction(headerBackAction);
			header.enableAction();
		}}))
		.play();
	}};
	
	private final Runnable advAction = new Runnable() {@Override public void run() {
		disableActions();
		mobility.push(advBackAction);
		onMain = false;
		
		panel.createTransition()
		.push(new SLKeyframe(advCfg, 0.6f)
		.setEndSide(SLSide.TOP, header)
		.setEndSide(SLSide.LEFT, tools)
		.setEndSide(SLSide.TOP, searchBar)
		.setStartSide(SLSide.TOP, advancedSearch)
		.setStartSide(SLSide.LEFT, advSearchTools)
		.setCallback(new SLKeyframe.Callback() {@Override public void done() {
			header.setAction(headerBackAction);
			header.enableAction();
		}}))
		.play();
	}};
	
	private final Runnable commentAction = new Runnable() {@Override public void run() {
		disableActions();
		mobility.push(commentBackAction);
		onMain = false;
		
		panel.createTransition()
		.push(new SLKeyframe(commentCfg, 0.6f)
		.setEndSide(SLSide.BOTTOM, taskBox)
		.setEndSide(SLSide.TOP, taskBar)
		.setEndSide(SLSide.LEFT, taskTools)
		.setStartSide(SLSide.LEFT, commentTools)
		.setStartSide(SLSide.BOTTOM, commentBox)
		.setCallback(new SLKeyframe.Callback() {@Override public void done() {
			header.enableAction();
		}}))
		.play();
	}};
	
	private final Runnable DRDDAction = new Runnable() {@Override public void run() {
		disableActions();
		mobility.push(DRDDBackAction);
		onMain = false;
		
		panel.createTransition()
		.push(new SLKeyframe(DRDDCfg, 0.6f)
		.setEndSide(SLSide.BOTTOM, taskBox)
		.setEndSide(SLSide.TOP, taskBar)
		.setEndSide(SLSide.LEFT, taskTools)
		.setStartSide(SLSide.LEFT, DRDDTools)
		.setStartSide(SLSide.BOTTOM, DRDDBox)
		.setCallback(new SLKeyframe.Callback() {@Override public void done() {
			header.enableAction();
		}}))
		.play();
	}};
	
	private final Runnable DRDDBackAction = new Runnable() {@Override public void run() {
		disableActions();
		onMain = false;
		
		panel.createTransition()
		.push(new SLKeyframe(headerCfg, 0.6f)
		.setStartSide(SLSide.BOTTOM, taskBox)
		.setStartSide(SLSide.TOP, taskBar)
		.setStartSide(SLSide.LEFT, taskTools)
		.setEndSide(SLSide.LEFT, DRDDTools)
		.setEndSide(SLSide.BOTTOM, DRDDBox)
		.setCallback(new SLKeyframe.Callback() {@Override public void done() {
			header.enableAction();
		}}))
		.play();
	}};
	
	private final Runnable commentBackAction = new Runnable() {@Override public void run() {
		disableActions();
		onMain = false;
		
		panel.createTransition()
		.push(new SLKeyframe(headerCfg, 0.6f)
		.setStartSide(SLSide.BOTTOM, taskBox)
		.setStartSide(SLSide.TOP, taskBar)
		.setStartSide(SLSide.LEFT, taskTools)
		.setEndSide(SLSide.BOTTOM, commentBox)
		.setEndSide(SLSide.LEFT, commentTools)
		.setCallback(new SLKeyframe.Callback() {@Override public void done() {
			header.setAction(headerBackAction);
			header.enableAction();
		}}))
		.play();
	}};
	
	private final Runnable advBackAction = new Runnable() {@Override public void run() {
		disableActions();
		onMain = true;
		
		panel.createTransition()
		.push(new SLKeyframe(mainCfg, 0.6f)
		.setEndSide(SLSide.TOP, advancedSearch)
		.setEndSide(SLSide.LEFT, advSearchTools)
		.setStartSide(SLSide.TOP, searchBar)
		.setStartSide(SLSide.TOP, header)
		.setStartSide(SLSide.LEFT, tools)
		.setCallback(new SLKeyframe.Callback() {@Override public void done() {
			header.setAction(headerBackAction);
			header.enableAction();
		}}))
		.play();
	}};
	
	private final Runnable attachAction = new Runnable() {@Override public void run() {
		disableActions();
		previousProject.setEnabled(true);
		onTask = true;
		mobility.add(attachAction);
		onMain = false;
		
		panel.createTransition()
		.push(new SLKeyframe(attachCfg, 0.6f)
		.setEndSide(SLSide.BOTTOM, taskBox)
		.setEndSide(SLSide.LEFT, taskTools)
		.setEndSide(SLSide.TOP, taskBar)
		.setStartSide(SLSide.LEFT, attachTools)
		.setStartSide(SLSide.BOTTOM, attachmentBox)
		.setCallback(new SLKeyframe.Callback() {@Override public void done() {
		}}))
		.play();
	}};
	
	private final Runnable attachBackAction = new Runnable() {@Override public void run() {
		disableActions();
		previousProject.setEnabled(true);
		onTask = true;
		onMain = false;
		
		panel.createTransition()
		.push(new SLKeyframe(headerCfg, 0.6f)
		.setEndSide(SLSide.BOTTOM, attachmentBox)
		.setEndSide(SLSide.LEFT, attachTools)
		.setStartSide(SLSide.TOP, taskBar)
		.setStartSide(SLSide.LEFT, taskTools)
		.setStartSide(SLSide.BOTTOM, taskBox)
		.setCallback(new SLKeyframe.Callback() {@Override public void done() {
		}}))
		.play();
	}};
	
	private final Runnable analystAction = new Runnable() {@Override public void run() {
		disableActions();
		previousProject.setEnabled(true);
		onTask = true;
		onMain = false;
		mobility.push(analystBackAction);
		
		panel.createTransition()
		.push(new SLKeyframe(analystCfg, 0.6f)
		//.setEndSide(SLSide.TOP, header)
		.setEndSide(SLSide.BOTTOM, taskBox)
		.setEndSide(SLSide.LEFT, taskTools)
		.setEndSide(SLSide.TOP, taskBar)
		.setStartSide(SLSide.LEFT, analystTools)
		.setStartSide(SLSide.BOTTOM, analystBox)
		.setCallback(new SLKeyframe.Callback() {@Override public void done() {
		}}))
		.play();
	}};
	
	private final Runnable analystBackAction = new Runnable() {@Override public void run() {
		disableActions();
		previousProject.setEnabled(true);
		onTask = true;
		onMain = false;
		
		panel.createTransition()
		.push(new SLKeyframe(headerCfg, 0.6f)
		//.setEndSide(SLSide.TOP, header)
		.setEndSide(SLSide.BOTTOM, analystBox)
		.setEndSide(SLSide.LEFT, analystTools)
		.setStartSide(SLSide.LEFT, taskTools)
		.setStartSide(SLSide.TOP, taskBar)
		.setStartSide(SLSide.BOTTOM, taskBox)
		.setCallback(new SLKeyframe.Callback() {@Override public void done() {
		}}))
		.play();
	}};
	
	private final Runnable logInAction = new Runnable() {@Override public void run() {
		disableActions();
		
		onTask = false;
		onMain = true;
		
		panel.createTransition()
		.push(new SLKeyframe(mainCfg, 0.6f)
		//.setEndSide(SLSide.TOP, header)
		.setEndSide(SLSide.LEFT, logIn)
		.setEndSide(SLSide.RIGHT, logIn2)
		.setStartSide(SLSide.TOP, header)
		.setStartSide(SLSide.LEFT, tools)
		.setStartSide(SLSide.BOTTOM, footer)
		.setStartSide(SLSide.LEFT, currentProjects)
		.setStartSide(SLSide.BOTTOM, tableBox)
		.setStartSide(SLSide.TOP, searchBar)
		.setStartSide(SLSide.RIGHT, newestTasks)
		.setStartSide(SLSide.RIGHT, deadlines))
		.play();
	}};
	
	private final Runnable logOutAction = new Runnable() {@Override public void run() {
		disableActions();
		
		logOut.setEnabled(false);
		onTask = false;
		onMain = false;
		
		panel.createTransition()
		.push(new SLKeyframe(logCfg, 0.6f)
		//.setEndSide(SLSide.TOP, header)
		.setStartSide(SLSide.LEFT, logIn)
		.setStartSide(SLSide.RIGHT, logIn2)
		.setEndSide(SLSide.TOP, header)
		.setEndSide(SLSide.LEFT, tools)
		.setEndSide(SLSide.BOTTOM, footer)
		.setEndSide(SLSide.LEFT, currentProjects)
		.setEndSide(SLSide.BOTTOM, tableBox)
		.setEndSide(SLSide.TOP, searchBar)
		.setEndSide(SLSide.RIGHT, newestTasks)
		.setEndSide(SLSide.RIGHT, deadlines))
		.play();
	}};

	private final Runnable headerBackAction = new Runnable() {@Override public void run() {
		disableActions();
		onTask = false;
		onMain = true;
		
		panel.createTransition()
		.push(new SLKeyframe(mainCfg, 0.6f)
		.setStartSide(SLSide.TOP, header)
		.setStartSide(SLSide.BOTTOM, tableBox)
		.setStartSide(SLSide.LEFT, tools)
		.setStartSide(SLSide.TOP, searchBar)
		.setEndSide(SLSide.BOTTOM, taskBox)
		.setEndSide(SLSide.TOP, headerName)
		.setEndSide(SLSide.LEFT, taskTools)
		.setEndSide(SLSide.TOP, taskBar)
		.setCallback(new SLKeyframe.Callback() {@Override public void done() {
			header.setAction(headerAction);
			enableActions();
		}}))
		.play();
	}};
}
