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
	
	/**
	 * Permet de connaitre l'angle du segment [p1,p2] par rapport à  l'axe x (cf canvas)
	 * 
	 * @param p1 Extrémité du rail
	 * @param p2 Extrémité du rail
	 * @return l'angle du segment [p1,p2] par rapport à  l'axe x (cf canvas)
	 */
		public double getAngleSegment(Point p1, Point p2) {
	 
			double angle = 0;

			// Point au milieu des 2 points
			Point centre = new Point((p2.x + p1.x) / 2, (p2.y + p1.y) / 2);

			double hypo = Math.sqrt(Math.pow(centre.x - p1.x, 2)
					+ Math.pow(centre.y - p1.y, 2));
			double adjacent = Math.sqrt(Math.pow(centre.x - p1.x, 2));

			if (hypo != 0) {
				angle = Math.acos(adjacent / hypo);

				//Si p1 en haut à droite
				if (p1.x > centre.x && p1.y < centre.y){
					angle = -angle + -Math.PI;
				}
				
				//Si p1 en haut à gauche
				if (p1.x < centre.x && p1.y < centre.y){
					angle = angle;
				}
				
				//Si p1 en bas à droite
				if (p1.x > centre.x && p1.y > centre.y){
					angle = angle + Math.PI;
				}
				
				//Si p1 en bas à gauche
				if (p1.x < centre.x && p1.y > centre.y){
					angle = -angle;
				}
				
				//Si horizontal de droite à gauche
				if(p1.y == centre.y && p1.x > centre.x){
						angle = Math.PI;
				}
				
				if(p1.x == centre.x){
					if(p1.y > centre.y){//Dirigé vers le bas
						angle = -Math.PI / 2;
					}else{
						angle = + Math.PI / 2;
					}
				}
				
				System.out.println(angle);
			} 
			return angle;
		}

}
