/**
 * 
 */
package aeroport.sgbag.utils;

import static org.junit.Assert.*;

import org.eclipse.swt.graphics.Point;
import org.junit.Test;

/**
 * @author Arnaud Lahache
 * 
 */
public class GeomTest {

	/**
	 * Test method for
	 * {@link aeroport.sgbag.utils.Geom#getRotatedPoint(org.eclipse.swt.graphics.Point, float)}
	 * .
	 */
	@Test
	public void testGetRotatedPoint() {

		Point p = new Point(100, 0);
		Point pOrig = new Point(0, 0);
		Point p2 = Geom.getRotatedPoint(p, pOrig, 90);
		assertEquals(0, p2.x);
		assertEquals(100, p2.y);

	}

}
