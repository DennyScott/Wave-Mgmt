package views;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import nattable.BasicNatInstance;
import nattable.EditNatInstance;
import nattable.TempNatTable;

import org.eclipse.nebula.widgets.nattable.examples.PersistentNatExampleWrapper;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.nebula.widgets.nattable.examples.INatExample;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.layout.RowLayout;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Group;
import swing2swt.layout.BoxLayout;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class frontScreen {

	protected Shell shlProject;
	private Label label_1;
	private Text txtEnterYourSearch;
	private GridData gridData_1;
	private GridData gridData_2;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Control exampleControl;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			frontScreen window = new frontScreen();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		createContents(new PersistentNatExampleWrapper(new EditNatInstance()));
		

	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(INatExample example) {
		Display display = Display.getDefault();
		shlProject = new Shell(display);
		shlProject.setText("Project ");
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.verticalSpacing = 8;
		shlProject.setLayout(gridLayout);

		Menu menu = new Menu(shlProject, SWT.BAR);
		shlProject.setMenuBar(menu);

		MenuItem mntmFile_1 = new MenuItem(menu, SWT.CASCADE);
		mntmFile_1.setText("File");

		Menu menu_1 = new Menu(mntmFile_1);
		mntmFile_1.setMenu(menu_1);

		MenuItem mntmToExcel = new MenuItem(menu_1, SWT.NONE);
		mntmToExcel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				toExcel();
			}
		});
		mntmToExcel.setText("To Excel        Ctrl+E");

		MenuItem mntmPrintCtrlp = new MenuItem(menu_1, SWT.NONE);
		mntmPrintCtrlp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				toPrint();
			}
		});
		mntmPrintCtrlp.setText("Print              Ctrl+P");

		MenuItem mntmSaveCtrls = new MenuItem(menu_1, SWT.NONE);
		mntmSaveCtrls.setText("Save              Ctrl+S");

		MenuItem mntmQuit = new MenuItem(menu_1, SWT.NONE);
		mntmQuit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		mntmQuit.setText("Quit");

		// Title Section
		Group cover = new Group(shlProject, SWT.SHADOW_ETCHED_OUT);
		cover.setLayout(new GridLayout(2, false));

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalSpan = 3;
		gridData.horizontalSpan = 10;
		gridData.heightHint = 100;
		gridData.widthHint = 100;
		cover.setLayoutData(gridData);

		// Two Titles
		Label label = new Label(cover, SWT.CENTER);
		label.setFont(SWTResourceManager.getFont("Arial", 18, SWT.BOLD));
		label.setText("Project Record Keeper");
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 10;
		label.setLayoutData(gridData);

		label_1 = new Label(cover, SWT.CENTER);
		label_1.setFont(SWTResourceManager.getFont("Arial", 14, SWT.ITALIC));
		label_1.setText("Health Information Management");
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalSpan = 3;
		gridData.horizontalSpan = 10;
		label_1.setLayoutData(gridData);

		Composite ToolbarContainer = new Composite(shlProject, SWT.NONE);
		ToolbarContainer.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		ToolbarContainer.setLayout(new GridLayout(5, false));
		gridData_2 = new GridData(GridData.FILL_HORIZONTAL);
		gridData_2.heightHint = 36;
		gridData_2.horizontalSpan = 2;
		ToolbarContainer.setLayoutData(gridData_2);

		// ToolBar Section
		ToolBar toolBar = new ToolBar(ToolbarContainer, SWT.FLAT | SWT.RIGHT);
		toolBar.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));

		ToolItem tltmNewItem = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem
				.setImage(SWTResourceManager
						.getImage("\\\\me\\hlt\\HLTUSERSv9\\descott\\My Pictures\\php7OCVMhPM.jpg"));
		tltmNewItem.setText("Add Project");

		ToolItem toolItem = new ToolItem(toolBar, SWT.SEPARATOR);

		ToolItem tltmToExcel = new ToolItem(toolBar, SWT.NONE);
		tltmToExcel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				toExcel();
			}
		});
		tltmToExcel
				.setImage(SWTResourceManager
						.getImage("\\\\me\\hlt\\HLTUSERSv9\\descott\\My Pictures\\phprn48hvPM.jpg"));
		tltmToExcel.setText("To Excel");

		ToolItem toolItem_1 = new ToolItem(toolBar, SWT.SEPARATOR);

		ToolItem tltmPrint = new ToolItem(toolBar, SWT.NONE);
		tltmPrint.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				toPrint();
			}
		});
		tltmPrint
				.setImage(SWTResourceManager
						.getImage("\\\\me\\hlt\\HLTUSERSv9\\descott\\My Pictures\\php0N0oeqPM.jpg"));
		tltmPrint.setText("Print");
		
		ToolItem toolItem_2 = new ToolItem(toolBar, SWT.SEPARATOR);
		
		txtEnterYourSearch = new Text(ToolbarContainer, SWT.BORDER | SWT.SEARCH);
		txtEnterYourSearch.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND));
		txtEnterYourSearch.setText("      Enter your search here      ");
		txtEnterYourSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Button btnSearch = formToolkit.createButton(ToolbarContainer, "Search", SWT.NONE);
		
		Button advancedSearch = new Button(ToolbarContainer, SWT.NONE);
		advancedSearch.setText("Advanced Search");
		gridData_1 = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gridData_1.horizontalAlignment = SWT.RIGHT;
		//gridData.horizontalSpan = 1;
		advancedSearch.setLayoutData(gridData_1);
		
		Button btnProjectDetails = new Button(ToolbarContainer, SWT.NONE);
		btnProjectDetails.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ProjectDetails window = new ProjectDetails();
				window.open();
			}
		});
		formToolkit.adapt(btnProjectDetails, true, true);
		btnProjectDetails.setText("Project Details");
		new Label(ToolbarContainer, SWT.NONE);
		new Label(ToolbarContainer, SWT.NONE);
		new Label(ToolbarContainer, SWT.NONE);
		new Label(ToolbarContainer, SWT.NONE);
		new Label(ToolbarContainer, SWT.NONE);

		// Create example control
		Composite composite = new Composite(shlProject, SWT.NONE);
		composite.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_DARK_GRAY));
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.verticalSpan = 3;
		gridData.horizontalSpan = 10;
		composite.setLayoutData(gridData);
		composite.setLayout(new GridLayout(1, false));

		exampleControl = example.createExampleControl(composite);
		gridData = new GridData(GridData.FILL_BOTH);

		exampleControl.setLayoutData(gridData);

		// Start
		example.onStart();
		shlProject.setMaximized(true);
		shlProject.open();
		while (!shlProject.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		// Stop
		example.onStop();

		exampleControl.dispose();
		display.dispose();

	}
	
	private void toExcel(){
		exampleControl.forceFocus();
		try {
			Robot key = new Robot();
			key.keyPress(KeyEvent.VK_CONTROL);
			key.keyPress(KeyEvent.VK_E);
			key.keyRelease(KeyEvent.VK_E);
			key.keyRelease(KeyEvent.VK_CONTROL);

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void toPrint(){
		exampleControl.forceFocus();
		try {
			Robot key = new Robot();
			key.keyPress(KeyEvent.VK_CONTROL);
			key.keyPress(KeyEvent.VK_P);
			key.keyRelease(KeyEvent.VK_P);
			key.keyRelease(KeyEvent.VK_CONTROL);

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
