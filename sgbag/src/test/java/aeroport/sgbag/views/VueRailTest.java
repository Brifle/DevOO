/**
 * 
 */
package aeroport.sgbag.views;

import static org.junit.Assert.*;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Arnaud Lahache
 *
 */
public class VueRailTest {
	
	private VueRail vueRail;
	private VueHall vueHall;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		vueHall = new VueHall(new Shell(new Display()), 0);
		vueRail = new VueRail(vueHall);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		vueRail = null;
		vueHall = null;
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
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link aeroport.sgbag.views.VueElem#isClicked()}.
	 */
	@Test
	public void testIsClicked() {
		fail("Not yet implemented");
	}

}
