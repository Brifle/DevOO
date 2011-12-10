/**
 * 
 */
package aeroport.sgbag.views;

import lombok.Getter;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

/**
 * @author Arnaud Lahache
 * 
 */
public class TraitementClic extends MouseAdapter {

	@Getter
	private VueHall vueHall;

	public TraitementClic(VueHall _vueHall) {
		vueHall = _vueHall;
	}

	/**
	 * Implémentation de l'évènement du clic dans un Hall.
	 * 
	 * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseUp(MouseEvent mouse) {
		Viewable clickedView = vueHall.getClickedView(mouse.x, mouse.y);
	}

}
