/**
 * 
 */
package aeroport.sgbag.views;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import aeroport.sgbag.kernel.Rail;

/**
 * @author Arnaud Lahache
 * 
 */
public class VueRailTest {

	private VueRail vueRail;
	private VueHall vueHall;
	private Shell shell;
	private Display display;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		display = new Display();
		shell = new Shell(display);
		shell.setText("VueRailTest");
		shell.setLayout(new FillLayout());
		shell.setSize(300, 300);
		vueHall = new VueHall(shell, SWT.NONE);
		vueHall.setSize(300, 300);
		vueRail = new VueRail(vueHall);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		vueHall.dispose();
		shell.dispose();
		display.dispose();
	}

	/**
	 * Test method for {@link aeroport.sgbag.views.VueRail#updateView()}.
	 */
	@Test
	public void testUpdateView() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link aeroport.sgbag.views.VueRail#draw()}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testDraw() throws InterruptedException {
		Rail rail = new Rail();
		rail.setLength(150);
		vueRail.setRail(rail);
		vueRail.setAngle(0);
		vueRail.setX(30);
		vueRail.setY(30);
		vueHall.ajouterVue(vueRail, 0);

		shell.open();
		vueHall.draw();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * Test method for {@link aeroport.sgbag.views.VueElem#isClicked()}.
	 */
	@Test
	public void testIsClicked() {
		fail("Not yet implemented");
	}

}
