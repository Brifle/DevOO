/**
 * 
 */
package aeroport.sgbag.views;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;

import lombok.*;

/**
 * @author Arnaud Lahache
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public abstract class VueElem implements Viewable {

	@Getter
	@Setter
	@NonNull
	protected VueHall parent;
	
	@Getter
	@Setter
	protected int x;

	@Getter
	@Setter
	protected int y;

	@Getter
	@Setter
	protected int width;

	@Getter
	@Setter
	protected int height;

	@Getter
	@Setter
	protected float angle = 0;
	
	@Getter
	@Setter
	protected Image image;

	/**
	 * @see aeroport.sgbag.views.Viewable#updateView()
	 */
	public abstract void updateView();

	/**
	 * @see aeroport.sgbag.views.Viewable#draw()
	 */
	public void draw() {
		GC gc = this.parent.getGcBuffer();

		// We get the size of the original image :
		Rectangle rect = image.getBounds();

		// We create a transform in order to rotate and translate the image :
		Transform trImage = new Transform(parent.getDisplay());
		trImage.translate(this.x, this.y);
		trImage.rotate(this.angle);
		gc.setTransform(trImage);

		// Then we just draw the image on the GC :
		gc.drawImage(this.image, 0, 0, rect.width, rect.height, -this.width / 2,
				-this.height / 2, this.width, this.height);

		// We no longer need the transform :
		gc.setTransform(null);
		trImage.dispose();
	}

	/**
	 * @see aeroport.sgbag.views.Viewable#isClicked()
	 */
	public boolean isClicked() {
		// TODO calcul en fonction de la position de la souris et de des
		// propriétés x, y, width, height.
		return false;
	}

}
