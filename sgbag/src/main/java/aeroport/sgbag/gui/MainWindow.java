package aeroport.sgbag.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import lombok.Getter;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import aeroport.sgbag.controler.Simulation;
import aeroport.sgbag.controler.Simulation.Mode;
import aeroport.sgbag.views.VueHall;
import aeroport.sgbag.xml.MalformedCircuitArchiveException;

/**
 * Interface graphique liée à l'affichage de la simulation.
 * 
 * @author Stanislas Signoud, Arnaud Lahache, Jonàs Bru Monserrat,
 * 		   Mathieu Sabourin 
 */
public class MainWindow extends ApplicationWindow {
	private Action actionQuitter;
	private Action actionDemarrer;
	private Action actionPauser;
	private Action actionArreter;
	private Action actionOuvrir;
	private Action actionSetAuto;
	private Action actionSetManuel;

	private VueHall vueHall;

	// (This getter is only used in unit tests)
	@Getter
	private Simulation simulation;

	private PropertiesWidget propertiesWidget;
	private Button btnManuel;
	private Button btnAutomatique;
	private Scale sclVitesse;

	/**
	 * Crée la fenêtre principale.
	 */
	public MainWindow() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
		PropertyConfigurator.configure("log4j.properties");
	}

	/**
	 * Crée les contenus de la fenêtre principale.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		parent.setSize(new Point(400, 400));
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(3, false));

		vueHall = new VueHall(container, 0);
		vueHall.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2,
				3));
		vueHall.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		simulation = new Simulation(vueHall);
		vueHall.setSimulation(simulation);

		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
				1, 2));
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		btnManuel = new Button(composite, SWT.NONE);
		btnManuel.setEnabled(false);
		btnManuel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				actionSetManuel.run();
			}
		});
		btnManuel.setText("Manuel");

		btnAutomatique = new Button(composite, SWT.NONE);
		btnAutomatique.setEnabled(false);
		btnAutomatique.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				actionSetAuto.run();
			}
		});
		btnAutomatique.setText("Automatique");

		Group grpProperties = new Group(container, SWT.NONE);
		GridData gd_grpProperties = new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 2);
		gd_grpProperties.minimumWidth = 325;
		gd_grpProperties.widthHint = 325;
		grpProperties.setLayoutData(gd_grpProperties);
		grpProperties.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpProperties.setText("Propriétés");

		propertiesWidget = new PropertiesWidget(grpProperties, SWT.NONE, null);
		simulation.setPropertiesWidget(propertiesWidget);

		CLabel lblVitesse = new CLabel(container, SWT.NONE);
		lblVitesse.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true,
				false, 1, 1));
		lblVitesse.setAlignment(SWT.RIGHT);
		lblVitesse.setText("Vitesse de la simulation");

		sclVitesse = new Scale(container, SWT.NONE);
		sclVitesse.setEnabled(false);
		sclVitesse.setMaximum(200);
		sclVitesse.setSelection(100);
		sclVitesse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setSpeedFromScale();
			}
		});
		GridData gd_sclVitesse = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_sclVitesse.widthHint = 100;
		gd_sclVitesse.minimumWidth = 100;
		sclVitesse.setLayoutData(gd_sclVitesse);

		return container;
	}

	/**
	 * Crée les actions attachées aux éléments de la classe principale.
	 */
	private void createActions() {
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
			actionDemarrer = new Action("Démarrer",
					ImageDescriptor
							.createFromFile(getClass(), "icons/play.png")) {
				@Override
				public void run() {
					setSpeedFromScale();
					simulation.play();

					if (simulation.getMode() == Mode.AUTO) {
						btnAutomatique.setEnabled(false);
						btnManuel.setEnabled(true);
					} else {
						btnAutomatique.setEnabled(true);
						btnManuel.setEnabled(false);
					}
				}
			};
			actionDemarrer.setEnabled(false);
		}
		{
			actionPauser = new Action("Pauser", ImageDescriptor.createFromFile(
					getClass(), "icons/pause.png")) {
				@Override
				public void run() {
					simulation.pause();
				}
			};
			actionPauser.setEnabled(false);
		}
		{
			actionArreter = new Action("Arrêter",
					ImageDescriptor
							.createFromFile(getClass(), "icons/stop.png")) {
				@Override
				public void run() {
					simulation.stop();
				}
			};
			actionArreter.setEnabled(false);
		}
		{
			actionOuvrir = new Action("Ouvrir", ImageDescriptor.createFromFile(
					getClass(), "icons/open.png")) {
				@Override
				public void run() {
					super.run();
					FileDialog fd = new FileDialog(getShell());
					fd.setFilterNames(new String[] { "Description XML" });
					fd.setFilterExtensions(new String[] { "*.xml" });
					fd.setFilterPath(System.getProperty("user.dir"));
					fd.setFileName("");

					String fileName = fd.open();

					if (fileName != null) {
						MessageBox msgb = new MessageBox(getShell());

						simulation.setXmlFile(new File(fileName));

						try {
							simulation.init();
						} catch (FileNotFoundException e) {
							msgb.setMessage("Le fichier sélectionné n'existe pas, ou ne peut être accédé.");
							msgb.open();
							return;
						} catch (MalformedCircuitArchiveException e) {
							msgb.setMessage("Le fichier sélectionné n'arrive pas à être lu (mal formé)."
									+ " Est-ce bien un fichier de configuration SGBag ?");
							msgb.open();
							return;
						} catch (IOException e) {
							msgb.setMessage("Le fichier sélectionné n'est pas accessible (erreur d'entrée/sortie).");
							msgb.open();
							return;
						}

						simulation.setMode(Simulation.Mode.AUTO);
						propertiesWidget.setSimulation(simulation);

						propertiesWidget.refresh();
						actionDemarrer.setEnabled(true);
						actionPauser.setEnabled(true);
						actionArreter.setEnabled(true);
						sclVitesse.setEnabled(true);
						actionOuvrir.setEnabled(false);
					}

					vueHall.draw();
				}
			};
		}
		{
			actionSetAuto = new Action("Mode automatique") {
				@Override
				public void run() {
					super.run();

					btnAutomatique.setEnabled(false);
					btnManuel.setEnabled(true);

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

					btnAutomatique.setEnabled(true);
					btnManuel.setEnabled(false);

					simulation.setMode(Simulation.Mode.MANUEL);
					setChecked(true);
					actionSetAuto.setChecked(false);

				}
			};
		}
	}

	/**
	 * Crée le gestionnaire de menu.
	 * 
	 * @return Le gestionnaire de menu.
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
	 * Crée le gestionnaire de barre d'outils.
	 * 
	 * @return Le gestionnaire de barre d'outils.
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
	 * Crée le gestionnaire de barre d'état.
	 * 
	 * @return Le gestionnaire de barre d'état.
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Lance l'application SGBag - Prototype.
	 * 
	 * @param args Arguments (inutilisés).
	 */
	public static void main(String args[]) {
		try {
			MainWindow window = new MainWindow();
			window.setBlockOnOpen(true);
			window.open();
			window.getSimulation().pause();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure la fenêtre d'affichage (shell).
	 * 
	 * @param Le shell, configuré.
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("SGBag - Interface de simulation");
		shell.setMinimumSize(600, 500);
	}

	/**
	 * Mets à jour la vitesse de la simulation, en se basant sur la
	 * jauge sclVitesse.
	 * 
	 * @return L'intervalle de temps utilisé lors de la modification
	 * de la vitesse. 
	 */
	private int setSpeedFromScale() {
		int newInterval = (sclVitesse.getMaximum() + 1)
				- sclVitesse.getSelection();
		simulation.setSpeed(newInterval);

		return newInterval;
	}
}
