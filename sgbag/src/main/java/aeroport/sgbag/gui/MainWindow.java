package aeroport.sgbag.gui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.wb.swt.SWTResourceManager;

import aeroport.sgbag.views.VueHall;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;

/**
 * SGBag GUI root window.
 * 
 * @author Stanislas Signoud <signez@stanisoft.net>
 *
 */
public class MainWindow extends ApplicationWindow {
	private Action actionQuitter;
	private Action actionDemarrer;
	private Action actionPauser;
	private Action actionArreter;

	/**
	 * Create the application window.
	 */
	public MainWindow() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		parent.setSize(new Point(400, 400));
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(3, false));
		
		VueHall vueHall = new VueHall(container, SWT.BORDER);
		GridData gd_vueHall = new GridData(SWT.FILL, SWT.FILL, false, true, 2, 3);
		gd_vueHall.heightHint = 150;
		gd_vueHall.widthHint = 400;
		gd_vueHall.minimumHeight = 200;
		gd_vueHall.minimumWidth = 400;
		vueHall.setLayoutData(gd_vueHall);
		vueHall.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Tree treeViews = new Tree(container, SWT.BORDER);
		GridData gd_treeViews = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2);
		gd_treeViews.heightHint = 150;
		treeViews.setLayoutData(gd_treeViews);
		
		Group grpProperties = new Group(container, SWT.NONE);
		GridData gd_grpProperties = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2);
		gd_grpProperties.widthHint = 144;
		grpProperties.setLayoutData(gd_grpProperties);
		grpProperties.setText("Propriétés");
		
		CLabel lblVitesse = new CLabel(container, SWT.NONE);
		lblVitesse.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		lblVitesse.setAlignment(SWT.RIGHT);
		lblVitesse.setText("Vitesse de la simulation");
		
		Scale sclVitesse = new Scale(container, SWT.NONE);
		GridData gd_sclVitesse = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_sclVitesse.heightHint = 30;
		gd_sclVitesse.minimumHeight = 30;
		gd_sclVitesse.widthHint = 120;
		gd_sclVitesse.minimumWidth = 120;
		sclVitesse.setLayoutData(gd_sclVitesse);
		
		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
		{
			actionQuitter = new Action("Quitter") {
				@Override
				public void run() {
					super.run();
					close();
				}
			};
		}
		{
			actionDemarrer = new Action("Démarrer") {
			};
		}
		{
			actionPauser = new Action("Pauser") {
			};
		}
		{
			actionArreter = new Action("Arrêter") {
			};
		}
	}

	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuBar = new MenuManager("menu");
		{
			MenuManager menuFichier = new MenuManager("Fichier");
			menuBar.add(menuFichier);
			menuFichier.add(actionDemarrer);
			menuFichier.add(actionPauser);
			menuFichier.add(actionArreter);
			menuFichier.add(new Separator());
			menuFichier.add(actionQuitter);
		}
		return menuBar;
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		toolBarManager.add(actionDemarrer);
		toolBarManager.add(actionPauser);
		toolBarManager.add(actionArreter);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			MainWindow window = new MainWindow();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("SGBag - Interface de simulation");
	}
}
