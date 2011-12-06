/**
 * 
 */
package aeroport.sgbag.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import aeroport.sgbag.kernel.Bagage;

/**
 * @author Arnaud Lahache
 *
 */
@NoArgsConstructor
public class VueBagage extends VueElem {

	@Getter
	@Setter
	private Bagage bagage;
	
	public VueBagage(VueHall parent) {
		super(parent);
		this.image = new Image(parent.getDisplay(), "data/img/bagage.png");
		
		Rectangle rect = image.getBounds();
		this.width = rect.width;
		this.height = rect.height;
	}
	
	/**
	 * @see aeroport.sgbag.views.VueElem#updateView()
	 */
	@Override
	public void updateView() {
		// TODO implémentation méthode update

	}

}
