/**
 * 
 */
package aeroport.sgbag.views;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import aeroport.sgbag.kernel.Rail;
import aeroport.sgbag.utils.Rectangle2D;

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
		shell.setSize(400, 400);
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
		vueRail.setRail(rail);
		vueRail.setAngle(-45);
		vueRail.setX(300);
		vueRail.setY(300);
		vueRail.setWidth(100);
		vueHall.ajouterVue(vueRail, 0);

		shell.open();
		vueHall.draw();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	/**
	 * Test method for {@link aeroport.sgbag.views.VueElem#getRectangleElem()}.
	 */
	@Test
	public void testGetRectangle2D() {
		Rail rail = new Rail();
		vueRail.setRail(rail);
		vueRail.setAngle(-90);
		vueRail.setX(150);
		vueRail.setY(150);
		vueRail.setWidth(100);
		vueRail.setHeight(100);
		Rectangle2D rect = vueRail.getRectangle2D();
		
		assertEquals(100, rect.getHautGauche().x);
		assertEquals(200, rect.getHautGauche().y);
		assertEquals(100, rect.getHautDroit().x);
		assertEquals(100, rect.getHautDroit().y);
		assertEquals(200, rect.getBasGauche().x);
		assertEquals(200, rect.getBasGauche().y);
		assertEquals(200, rect.getBasDroit().x);
		assertEquals(100, rect.getBasDroit().y);
	}

	/**
	 * Test method for {@link aeroport.sgbag.views.VueElem#isContening()}.
	 */
	@Test
	public void testIsContening() {
		Rail rail = new Rail();
		vueRail.setRail(rail);
		vueRail.setAngle(-90);
		vueRail.setX(150);
		vueRail.setY(150);
		vueRail.setWidth(200);
		vueRail.setHeight(100);
		
		//In
		Point p1 = new Point(120,120);
		Point p2 = new Point(150,220);
		
		//Out
		Point p3 = new Point(50,50);
		Point p4 = new Point(220,150);

		assertTrue(vueRail.isContening(p1));
		assertTrue(vueRail.isContening(p2));
		assertFalse(vueRail.isContening(p3));
		assertFalse(vueRail.isContening(p4));
		
	}
	
	/**
	 * Test method for {@link aeroport.sgbag.views.VueElem#isClicked()}.
	 */
	@Test
	public void testIsClicked() {
		fail("Not yet implemented");
	}

}
