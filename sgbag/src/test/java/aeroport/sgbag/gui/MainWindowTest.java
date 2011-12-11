package aeroport.sgbag.gui;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.widgets.Display;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainWindowTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PropertyConfigurator.configure("log4j.properties");
	}

	@Test
	public void test() {
		MainWindow win = new MainWindow();
		win.setBlockOnOpen(true);
		win.open();
		Display.getCurrent().dispose();
	}

}
