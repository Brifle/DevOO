package aeroport.sgbag.utils;

import org.eclipse.swt.graphics.Point;

public class Geom {

	public static Point getRotatedPoint(Point p, float angle) {
		double angleRad = Math.toRadians(angle);

		int newX = (int) (p.x * Math.cos(angleRad) + p.y * Math.sin(angleRad));
		int newY = (int) (-p.x * Math.sin(angleRad) + p.y * Math.cos(angleRad));
		return new Point(newX, newY);
	}

}
