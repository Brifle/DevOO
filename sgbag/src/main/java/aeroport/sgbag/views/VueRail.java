/**
 * 
 */
package aeroport.sgbag.views;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;

/**
 * @author Arnaud Lahache
 *
 */
public class VueRail extends VueElem {

	public VueRail(Canvas parent) {
		super(parent);
		this.image = new Image(parent.getDisplay(), "data/img/rail.png");
	}
	
	/**
	 * @see aeroport.sgbag.views.VueElem#updateView()
	 */
	@Override
	public void updateView() {
		// TODO implémentation méthode update

	}

	/**
	 * @see aeroport.sgbag.views.VueElem#draw()
	 */
	@Override
	public void draw() {
		// TODO implémentation méthode draw

	}

}
