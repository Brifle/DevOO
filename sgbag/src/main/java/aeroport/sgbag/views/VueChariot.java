package aeroport.sgbag.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;

import aeroport.sgbag.kernel.Chariot;

@NoArgsConstructor
public class VueChariot extends VueElem {

	@Getter
	@Setter
	private Chariot chariot;

	@Getter
	@Setter
	private GC gc;

	public VueChariot(Canvas parent, Chariot chariot) {
		super((VueHall) parent);
		this.image = new Image(parent.getDisplay(), "data/img/chariot.png");
		gc = this.parent.getGcBuffer();
		
		height = chariot.getLength();
		Rectangle rect = image.getBounds();
		width = rect.height * chariot.getLength() / rect.width;
	}

	public void updateView() {
				
		//TODO

	}

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
}
