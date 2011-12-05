package aeroport.sgbag.views;


import lombok.Getter;
import lombok.Setter;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;

import aeroport.sgbag.kernel.Chariot;
import aeroport.sgbag.kernel.Rail;

public class VueChariot extends VueElem {

	@Getter
	@Setter
	private Chariot chariot;

	private Image imgBagage;

	public VueChariot(Canvas parent, Chariot chariot) {
		super((VueHall) parent);
		this.image = new Image(parent.getDisplay(), "data/img/chariot.png");
		imgBagage = new Image(parent.getDisplay(), "data/img/bagage.png");
	}

	public void updateView() {

	}

	public void draw() {

		GC gc = this.parent.getGcBuffer();

		// Create a working copy of the original chariot image :
		Rectangle rect = image.getBounds();
		Image imageCpy = new Image(parent.getDisplay(), rect);
		GC gcImage = new GC(imageCpy);

		// We create a transform in order to rotate the image :
		Transform trImage = new Transform(parent.getDisplay());

		VueElem parentChariotVueElem; // = this.parent.getVue(chariot.getParent()) TODO

		if (chariot.getParent() instanceof Rail) {

			// trImage.rotate((VueRail)parentChariotVueElem.getAngle()); TODO
			gcImage.setTransform(trImage);
			//x=parentChariotVueElem.getX() + parentChariotVueElem.getLength*angle; To ADD
			//y=parentChariotVueElem.getY()+ parentChariotVueElem.getLength*angle; TO ADD			
		}else{
			//x=parentChariotVueElem.getX(); To ADD
			//y=parentChariotVueElem.getY(); TO ADD
			
		}
			

		// draw the chariot
		gcImage.drawImage(this.image, 0, 0, rect.width, rect.height, 0, 0,
				chariot.getLength(), rect.height * chariot.getLength()
						/ rect.width);
		if (!chariot.isEmpty()) {
			Rectangle bagageRect = imgBagage.getBounds();

			// add the bagage
			gcImage.drawImage(this.imgBagage, 0, 0, bagageRect.width,
					bagageRect.height, chariot.getLength() / 2,
					chariot.getLength() / 2, 30, 30); // TODO change 30
		}

		// We no longer need the GC for imageCpy :
		trImage.dispose();
		gcImage.dispose();
		
		// We will add this imageCpy into the main GC :
		gc.drawImage(imageCpy, this.x, this.y);

		// We no longer need imageCpy :
		imageCpy.dispose();
	}



}
