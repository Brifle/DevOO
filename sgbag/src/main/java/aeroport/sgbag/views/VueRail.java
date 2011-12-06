/**
 * 
 */
package aeroport.sgbag.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import aeroport.sgbag.kernel.Rail;

/**
 * @author Arnaud Lahache
 * 
 */
@NoArgsConstructor
public class VueRail extends VueElem {

	@Getter
	@Setter
	private Rail rail;

	public VueRail(VueHall parent) {
		super(parent);
		this.image = new Image(parent.getDisplay(), "data/img/rail.png");
		
		Rectangle rect = image.getBounds();
		this.width = rect.width;
		this.height = rect.height;
	}

	/**
	 * @see aeroport.sgbag.views.VueElem#updateView()
	 */
	@Override
	public void updateView() {
		this.width = rail.getLength();
	}

}
