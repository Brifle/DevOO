package aeroport.sgbag.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
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
	}

	public void updateView() {

	}

	public void draw() {
		
		// We create a transform in order to rotate and translate the image :
		Transform trImage = new Transform(parent.getDisplay());
		
		
		trImage.translate(this.x, this.y);
		
		trImage.rotate(this.getAngle());
		gc.setTransform(trImage);

		Rectangle rect = image.getBounds();

		// draw the chariot on the rail
		gc.drawImage(this.image, 0, 0, rect.width, rect.height, 0,
				chariot.getPosition(), chariot.getLength(),
				rect.height * chariot.getLength() / rect.width);
	}
}
