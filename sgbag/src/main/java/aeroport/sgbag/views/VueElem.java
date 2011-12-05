/**
 * 
 */
package aeroport.sgbag.views;

import lombok.*;

/**
 * @author Arnaud Lahache
 * 
 */
public abstract class VueElem implements Viewable {

	@Getter
	@Setter
	private int x;

	@Getter
	@Setter
	private int y;

	@Getter
	@Setter
	private int width;

	@Getter
	@Setter
	private int height;

	/**
	 * @see aeroport.sgbag.views.Viewable#updateView()
	 */
	public abstract void updateView();

	/**
	 * @see aeroport.sgbag.views.Viewable#draw()
	 */
	public abstract void draw();

	/**
	 * @see aeroport.sgbag.views.Viewable#isClicked()
	 */
	public boolean isClicked() {
		// TODO calcul en fonction de la position de la souris et de des
		// propriétés x, y, width, height.
		return false;
	}

}
