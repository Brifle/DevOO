package aeroport.sgbag.utils;

import org.eclipse.swt.graphics.Point;

import aeroport.sgbag.kernel.Noeud;

public class PointNoeud {
	public PointNoeud(Point p, Noeud n) {
		pt = p;
		noeud = n;
	}

	public PointNoeud(int x, int y, Noeud n) {
		pt = new Point(x, y);
		noeud = n;
	}

	public Point pt;
	public Noeud noeud;
}
