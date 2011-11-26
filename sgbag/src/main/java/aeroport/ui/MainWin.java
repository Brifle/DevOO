package aeroport.ui;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MainWin extends ApplicationWindow {
	private Text textInput;
	private Button btnClear;
	private StyledText styledTextOutput;

	/**
	 * Create the application window.
	 */
	public MainWin() {
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
		Composite container = new Composite(parent, SWT.NONE);
		
		btnClear = new Button(container, SWT.NONE);
		btnClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				styledTextOutput.setText("");
			}
		});
		btnClear.setText("Clear");
		
		Label lblEnterText = new Label(container, SWT.NONE);
		lblEnterText.setText("Enter text :");
		
		textInput = new Text(container, SWT.BORDER);
		
		styledTextOutput = new StyledText(container, SWT.BORDER);
		styledTextOutput.setEditable(false);
		
		Button btnDo = new Button(container, SWT.NONE);
		btnDo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				styledTextOutput.append(textInput.getText() + "\n");
			}
		});
		btnDo.setText("Do");
		
		Label lblAndWhatDo = new Label(container, SWT.NONE);
		lblAndWhatDo.setText("And what do you see :");
		
		
		GroupLayout gl_container = new GroupLayout(container);
		gl_container.setHorizontalGroup(
			gl_container.createParallelGroup(GroupLayout.LEADING)
				.add(gl_container.createSequentialGroup()
					.addContainerGap()
					.add(gl_container.createParallelGroup(GroupLayout.LEADING)
						.add(styledTextOutput, GroupLayout.DEFAULT_SIZE, 976, Short.MAX_VALUE)
						.add(GroupLayout.TRAILING, btnClear)
						.add(GroupLayout.TRAILING, gl_container.createSequentialGroup()
							.add(textInput, GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
							.add(18)
							.add(btnDo))
						.add(lblAndWhatDo)
						.add(lblEnterText))
					.addContainerGap())
		);
		gl_container.setVerticalGroup(
			gl_container.createParallelGroup(GroupLayout.LEADING)
				.add(gl_container.createSequentialGroup()
					.addContainerGap()
					.add(lblEnterText)
					.addPreferredGap(LayoutStyle.RELATED)
					.add(gl_container.createParallelGroup(GroupLayout.LEADING)
						.add(btnDo)
						.add(textInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.add(16)
					.add(lblAndWhatDo)
					.addPreferredGap(LayoutStyle.RELATED)
					.add(styledTextOutput, GroupLayout.PREFERRED_SIZE, 317, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.RELATED, 15, Short.MAX_VALUE)
					.add(btnClear)
					.addContainerGap())
		);
		container.setLayout(gl_container);

		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
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
			MainWin window = new MainWin();
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
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("New Application");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}
}
