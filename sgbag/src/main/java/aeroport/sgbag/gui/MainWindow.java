package aeroport.sgbag.gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import javax.swing.plaf.FileChooserUI;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.wb.swt.SWTResourceManager;

import aeroport.sgbag.controler.Simulation;
import aeroport.sgbag.views.VueHall;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.Realm;

/**
 * SGBag GUI root window.
 * 
 * @author Stanislas Signoud <signez@stanisoft.net>
 *
 */
public class MainWindow extends ApplicationWindow {
	private DataBindingContext m_bindingContext;
	private Action actionQuitter;
	private Action actionDemarrer;
	private Action actionPauser;
	private Action actionArreter;
	private Action actionOuvrir;
	private Action actionSetAuto;
	private Action actionSetManuel;

	private VueHall vueHall;
	private Simulation simulation;
	private PropertiesWidget propertiesWidget;
	private Button btnManuel;
	private Button butAutomatique;
	
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
		
		vueHall = new VueHall(container, SWT.BORDER);
		GridData gd_vueHall = new GridData(SWT.FILL, SWT.FILL, false, true, 2, 5);
		gd_vueHall.heightHint = 150;
		gd_vueHall.widthHint = 400;
		gd_vueHall.minimumHeight = 200;
		gd_vueHall.minimumWidth = 400;
		vueHall.setLayoutData(gd_vueHall);
		vueHall.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2));
		
		btnManuel = new Button(composite, SWT.TOGGLE);
		btnManuel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnManuel.setText("Manuel");
		
		butAutomatique = new Button(composite, SWT.TOGGLE);
		butAutomatique.setText("Automatique");
		
		Tree treeViews = new Tree(container, SWT.BORDER);
		GridData gd_treeViews = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2);
		gd_treeViews.minimumWidth = 120;
		gd_treeViews.heightHint = 150;
		treeViews.setLayoutData(gd_treeViews);
		
		Group grpProperties = new Group(container, SWT.NONE);
		grpProperties.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpProperties.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2));
		grpProperties.setText("Propriétés");
		
		propertiesWidget = new PropertiesWidget(grpProperties, SWT.NONE, null);
		
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
		m_bindingContext = initDataBindings();
		
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
			actionDemarrer = new Action("Démarrer", ImageDescriptor.createFromFile(getClass(), "icons/play.png") ) {
			};
		}
		{
			actionPauser = new Action("Pauser", ImageDescriptor.createFromFile(getClass(), "icons/pause.png") ) {
			};
		}
		{
			actionArreter = new Action("Arrêter", ImageDescriptor.createFromFile(getClass(), "icons/stop.png") ) {
			};
		}
		{
			actionOuvrir = new Action("Ouvrir", ImageDescriptor.createFromFile(getClass(), "icons/open.png") ) {
				@Override
				public void run() {
					super.run();
					FileDialog fd = new FileDialog(getShell());
					fd.setFilterNames(new String[] { "Description XML" });
				    fd.setFilterExtensions(new String[] { "*.xml" }); 
				    fd.setFilterPath(System.getProperty("user.dir"));
				    fd.setFileName("");
				    
				    String fileName = fd.open();
				    
				    if(fileName != null){
				    	simulation = new Simulation(new File(fileName));
				    	propertiesWidget.setSimulation(simulation);
				    	propertiesWidget.refresh();
				    }
				}
			};
		}
		{
			actionSetAuto = new Action("Mode automatique") {
				@Override
				public void run() {
					super.run();
					
					simulation.setMode(Simulation.Mode.AUTO);
					setChecked(true);
					actionSetManuel.setChecked(false);
				}
			};
		}
		{
			actionSetManuel = new Action("Mode manuel") {
				@Override
				public void run() {
					super.run();
					
					simulation.setMode(Simulation.Mode.MANUEL);
					setChecked(true);
					actionSetAuto.setChecked(false);
				}
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
		toolBarManager.add(actionOuvrir);
		toolBarManager.add(new Separator());
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
		Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.setBlockOnOpen(true);
					window.open();
					Display.getCurrent().dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("SGBag - Interface de simulation");
		shell.setMinimumSize(600,500);
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue btnManuelObserveSelectionObserveWidget = SWTObservables.observeDelayedValue(30, SWTObservables.observeSelection(btnManuel));
		IObservableValue actionSetManuelCheckedObserveValue = PojoObservables.observeValue(actionSetManuel, "checked");
		bindingContext.bindValue(btnManuelObserveSelectionObserveWidget, actionSetManuelCheckedObserveValue, null, null);
		//
		IObservableValue butAutomatiqueObserveSelectionObserveWidget = SWTObservables.observeDelayedValue(30, SWTObservables.observeSelection(butAutomatique));
		IObservableValue actionSetAutoCheckedObserveValue = PojoObservables.observeValue(actionSetAuto, "checked");
		bindingContext.bindValue(butAutomatiqueObserveSelectionObserveWidget, actionSetAutoCheckedObserveValue, null, null);
		//
		return bindingContext;
	}
}
