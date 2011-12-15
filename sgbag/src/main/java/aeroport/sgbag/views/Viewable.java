package aeroport.sgbag.views;

import org.eclipse.swt.graphics.Point;

/**
 * Affichable à l'écran, lié à un model.
 * 
 * @author Arnaud Lahache
 */
public interface Viewable {

	/**
	 * Mets à jour la vue, se synchronisant avec les propriétés de l'objet du noyau.
	 */
	public void updateView();

	/**
	 * Dessine l'objet à l'écran.
	 */
	public void draw();

	/**
	 * Indique si la vue est cliquée.
	 * 
	 * Note : Ceci ne prends pas en compte le calque dans lequel la vue est placée.
	 * 
	 * @return true si la vue est cliquée.
	 */
	public boolean isClicked(Point p);

}
