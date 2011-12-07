package aeroport.sgbag.views;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;

import lombok.Getter;
import lombok.Setter;
import aeroport.sgbag.kernel.Chariot;
import aeroport.sgbag.kernel.TapisRoulant;

public class VueTapisRoulant extends VueElem {

	@Getter
	@Setter
	private TapisRoulant tapisRoulant;

	public VueTapisRoulant(Canvas parent, TapisRoulant tapisRoulant) {
		super((VueHall) parent);
		
		this.tapisRoulant = tapisRoulant;
		
		this.image = new Image(parent.getDisplay(), "data/img/tapis.png");
	}

	@Override
	public void updateView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw() {
		GC gc = this.parent.getGcBuffer();

		// Create a working copy of the original image :
		Rectangle rect = image.getBounds();
		Image imageCpy = new Image(parent.getDisplay(), rect);
		GC gcImage = new GC(imageCpy);

		// We create a transform in order to rotate the image :
		Transform trImage = new Transform(parent.getDisplay());
		trImage.rotate(getAngle());
		gcImage.setTransform(trImage);

		// Now we're going to draw the expanded image :
		gcImage.drawImage(this.image, 0, 0, rect.width, rect.height, 0, 0,
				this.getWidth(), rect.height);

		// We no longer need the GC for imageCpy :
		trImage.dispose();
		gcImage.dispose();

		// We will add this imageCpy into the main GC :
		//gc.drawImage(imageCpy, this.x, this.y);

		// We no longer need imageCpy :
		imageCpy.dispose();

	}

}
