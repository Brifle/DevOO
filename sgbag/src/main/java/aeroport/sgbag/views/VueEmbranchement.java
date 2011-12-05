package aeroport.sgbag.views;

import lombok.Getter;
import lombok.Setter;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import aeroport.sgbag.kernel.Noeud;

public class VueEmbranchement extends VueElem {

	@Getter
	@Setter
	private Noeud noeud;

	private Image image;

	public VueEmbranchement(VueHall parent) {
		super(parent);
		image = new Image(parent.getDisplay(), "data/img/embranchement.png");
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

		// Draw image
		gcImage.drawImage(this.image, rect.x, rect.y);

		// We no longer need the GC for imageCpy :
		gcImage.dispose();

		// We will add this imageCpy into the main GC :
		gc.drawImage(imageCpy, this.x, this.y);

		// We no longer need imageCpy :
		imageCpy.dispose();
	}

}
