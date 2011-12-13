/**
 * 
 */
package aeroport.sgbag.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;

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

	@Getter
	@Setter
	private boolean isDisplayingArrow;

	private Image imageFleche;

	public VueRail(VueHall parent) {
		super(parent);
		this.image = new Image(parent.getDisplay(), "data/img/rail.png");
		this.imageFleche = new Image(parent.getDisplay(),
				"data/img/direction.png");

		Rectangle rect = image.getBounds();
		this.width = rect.width;
		this.height = rect.height;
	}

	/**
	 * @see aeroport.sgbag.views.VueElem#updateView()
	 */
	@Override
	public void updateView() {
		this.width = (int) (rail.getLength() * RATIO);
	}

	@Override
	public void draw() {
		super.draw();

		if (isDisplayingArrow) {
			GC gc = this.parent.getGcBuffer();

			// We get the size of the original image :
			Rectangle rect = imageFleche.getBounds();

			// We create a transform in order to rotate and translate the image:
			Transform trImage = new Transform(parent.getDisplay());
			trImage.translate(this.x + offsetX, this.y + offsetY);
			trImage.rotate(this.angle);
			gc.setTransform(trImage);

			// If the element is selected :
			if (selected) {
				gc.setAlpha(100);
			} else {
				gc.setAlpha(255);
			}

			// Then we just draw the image on the GC :
			gc.drawImage(this.imageFleche, 0, 0, rect.width, rect.height,
					-rect.width / 2, -rect.width / 2, rect.width, rect.width);

			// We no longer need the transform :
			gc.setTransform(null);
			trImage.dispose();
		}
	}

}
