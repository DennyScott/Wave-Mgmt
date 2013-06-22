package views;

import nattable.BasicNatInstance;
import nattable.EditNatInstance;
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

public class ProjectDetails {

	protected Shell shlProject;
	private Label lblUnit;
	private Text txtEnterYourSearch;
	private GridData gridData_1;
	private GridData gridData_2;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private GridData gd_lblProjectName;
	private GridData gd_lblUnit;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
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

//		shlProject.setSize(655, 523);
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
		mntmToExcel.setText("To Excel        Ctrl+E");

		MenuItem mntmPrintCtrlp = new MenuItem(menu_1, SWT.NONE);
		mntmPrintCtrlp.setText("Print              Ctrl+P");

		MenuItem mntmSaveCtrls = new MenuItem(menu_1, SWT.NONE);
		mntmSaveCtrls.setText("Save              Ctrl+S");

		MenuItem mntmQuit = new MenuItem(menu_1, SWT.NONE);
		mntmQuit.setText("Quit");

		// Title Section
		Group cover = new Group(shlProject, SWT.SHADOW_ETCHED_OUT);
		cover.setText("Project Details");
		GridLayout gl_cover = new GridLayout(4, false);
		gl_cover.horizontalSpacing = 6;
		cover.setLayout(gl_cover);

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalSpan = 3;
		gridData.horizontalSpan = 10;
		gridData.heightHint = 100;
		gridData.widthHint = 100;
		cover.setLayoutData(gridData);

		// Two Titles
		Label lblProjectName = new Label(cover, SWT.CENTER);
		lblProjectName.setAlignment(SWT.LEFT);
		lblProjectName.setFont(SWTResourceManager.getFont("Arial", 10, SWT.BOLD));
		lblProjectName.setText("Project Name:");
		gd_lblProjectName = new GridData(GridData.FILL_HORIZONTAL);
		gd_lblProjectName.grabExcessHorizontalSpace = false;
		gd_lblProjectName.horizontalAlignment = SWT.LEFT;
		lblProjectName.setLayoutData(gd_lblProjectName);
		
		text = new Text(cover, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		formToolkit.adapt(text, true, true);
				
						lblUnit = new Label(cover, SWT.CENTER);
						lblUnit.setAlignment(SWT.LEFT);
						lblUnit.setFont(SWTResourceManager.getFont("Arial", 10, SWT.BOLD));
						lblUnit.setText("Unit:");
						gd_lblUnit = new GridData(GridData.FILL_HORIZONTAL);
						gd_lblUnit.grabExcessHorizontalSpace = false;
						gd_lblUnit.horizontalAlignment = SWT.LEFT;
						lblUnit.setLayoutData(gd_lblUnit);
				
				text_1 = new Text(cover, SWT.BORDER);
				text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
				formToolkit.adapt(text_1, true, true);
		
		Label lblAnalyst = new Label(cover, SWT.CENTER);
		lblAnalyst.setText("Analyst(s):       ");
		lblAnalyst.setFont(SWTResourceManager.getFont("Arial", 10, SWT.BOLD));
		lblAnalyst.setAlignment(SWT.LEFT);
		formToolkit.adapt(lblAnalyst, true, true);
		
		text_2 = new Text(cover, SWT.BORDER);
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		formToolkit.adapt(text_2, true, true);
		
		Label lblDescription = new Label(shlProject, SWT.NONE);
		lblDescription.setFont(SWTResourceManager.getFont("Arial", 10, SWT.BOLD));
		formToolkit.adapt(lblDescription, true, true);
		lblDescription.setText("Description");
		new Label(shlProject, SWT.NONE);
		
		text_3 = new Text(shlProject, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_text_3 = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 3);
		gd_text_3.heightHint = 126;
		text_3.setLayoutData(gd_text_3);
		formToolkit.adapt(text_3, true, true);
		new Label(shlProject, SWT.NONE);
		new Label(shlProject, SWT.NONE);

		Composite ToolbarContainer = new Composite(shlProject, SWT.NONE);
		ToolbarContainer.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		ToolbarContainer.setLayout(new GridLayout(4, false));
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
		tltmNewItem.setText("Add Activity");

		ToolItem toolItem = new ToolItem(toolBar, SWT.SEPARATOR);

		ToolItem tltmToExcel = new ToolItem(toolBar, SWT.NONE);
		tltmToExcel
				.setImage(SWTResourceManager
						.getImage("\\\\me\\hlt\\HLTUSERSv9\\descott\\My Pictures\\phprn48hvPM.jpg"));
		tltmToExcel.setText("To Excel");

		ToolItem toolItem_1 = new ToolItem(toolBar, SWT.SEPARATOR);

		ToolItem tltmPrint = new ToolItem(toolBar, SWT.NONE);
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

		Control exampleControl = example.createExampleControl(composite);
		gridData = new GridData(GridData.FILL_BOTH);

		exampleControl.setLayoutData(gridData);

		// Start
		example.onStart();
		shlProject.open();
		while (!shlProject.isDisposed()) {
			if (!display.readAndDispatch()) {
				//display.sleep();
			}
		}
		// Stop
		example.onStop();

		exampleControl.dispose();
		

	}
}
