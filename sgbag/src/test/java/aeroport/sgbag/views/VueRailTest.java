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

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		shell = new Shell(new Display());
		shell.setText("VueRailTest");
	    shell.setLayout(new FillLayout());
		shell.setSize(300,300);
		vueHall = new VueHall(shell, SWT.NONE);
		vueRail = new VueRail(vueHall);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		vueRail = null;
		vueHall = null;
		shell = null;
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
	 */
	@Test
	public void testDraw() {
		Rail rail = new Rail();
		rail.setLength(100);
		vueRail.setRail(rail);
		vueRail.setAngle(0);
		vueRail.setX(30);
		vueRail.setY(30);
		vueHall.ajouterVue(vueRail, 0);
		vueHall.draw();
		
		shell.open();
	}

	/**
	 * Test method for {@link aeroport.sgbag.views.VueElem#isClicked()}.
	 */
	@Test
	public void testIsClicked() {
		fail("Not yet implemented");
	}

}
