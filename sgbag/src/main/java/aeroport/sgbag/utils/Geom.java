package aeroport.sgbag.utils;

import org.eclipse.swt.graphics.Point;

public class Geom {

	public static Point getRotatedPoint(Point p, Point centre, float angle) {
		double angleRad = Math.toRadians(angle);

		int x = p.x - centre.x;
		int y = p.y - centre.y;

		int xRot = (int) (x * Math.cos(angleRad) + (-y)
				* Math.sin(angleRad));
		int yRot = (int) -(-x * Math.sin(angleRad) + (-y)
				* Math.cos(angleRad));

		int newX = xRot + centre.x;
		int newY = yRot + centre.y;

		return new Point(newX, newY);
	}

}
