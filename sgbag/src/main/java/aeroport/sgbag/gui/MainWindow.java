package aeroport.sgbag.gui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;

import aeroport.sgbag.views.VueHall;

/**
 * SGBag GUI root window.
 * 
 * @author Stanislas Signoud <signez@stanisoft.net>
 *
 */
public class MainWindow extends ApplicationWindow {
	private Action actionQuitter;
	private Action actionDemarrer;
	private Action actionPause;
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
		
		VueHall vueHall = new VueHall(container, SWT.BORDER);
		vueHall.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Tree tree = new Tree(container, SWT.BORDER);
		
		Scale scale = new Scale(container, SWT.NONE);
		
		CLabel lblHorloge_1 = new CLabel(container, SWT.NONE);
		lblHorloge_1.setAlignment(SWT.RIGHT);
		lblHorloge_1.setText("Horloge");
		GroupLayout gl_container = new GroupLayout(container);
		gl_container.setHorizontalGroup(
			gl_container.createParallelGroup(GroupLayout.LEADING)
				.add(gl_container.createSequentialGroup()
					.add(12)
					.add(gl_container.createParallelGroup(GroupLayout.LEADING)
						.add(GroupLayout.TRAILING, gl_container.createSequentialGroup()
							.addPreferredGap(LayoutStyle.RELATED)
							.add(lblHorloge_1, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(scale, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
						.add(vueHall, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
					.addPreferredGap(LayoutStyle.RELATED)
					.add(tree, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
					.add(0))
		);
		gl_container.setVerticalGroup(
			gl_container.createParallelGroup(GroupLayout.LEADING)
				.add(gl_container.createSequentialGroup()
					.add(12)
					.add(gl_container.createParallelGroup(GroupLayout.LEADING)
						.add(tree, GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
						.add(gl_container.createSequentialGroup()
							.add(vueHall, GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(gl_container.createParallelGroup(GroupLayout.TRAILING)
								.add(lblHorloge_1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.add(scale, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))))
					.add(0))
		);
		container.setLayout(gl_container);
		
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
			actionPause = new Action("Pause") {
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
		toolBarManager.add(actionPause);
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
		shell.setText("SGBag");
	}
}
