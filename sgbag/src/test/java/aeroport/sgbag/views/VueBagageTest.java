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

import aeroport.sgbag.kernel.Bagage;

/**
 * @author Arnaud Lahache
 * 
 */
public class VueBagageTest {

	private VueBagage vueBagage;
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
		vueBagage = new VueBagage(vueHall);
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
		Bagage bagage = new Bagage();
		vueBagage.setBagage(bagage);
		vueBagage.setAngle(90);
		vueBagage.setX(30);
		vueBagage.setY(30);
		vueHall.ajouterVue(vueBagage, 0);

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
