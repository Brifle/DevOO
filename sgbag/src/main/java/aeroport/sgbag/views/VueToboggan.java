package aeroport.sgbag.views;

import lombok.Getter;
import lombok.Setter;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

import aeroport.sgbag.kernel.Toboggan;

public class VueToboggan extends VueElem {

	@Getter
	@Setter
	private Toboggan toboggan;

	public VueToboggan(Canvas parent, Toboggan toboggan) {
		super((VueHall) parent);
		
		this.toboggan = toboggan;
		
		this.image = new Image(parent.getDisplay(), "data/img/toboggan.png");
		
		Rectangle rect = image.getBounds();
		this.width = rect.width;
		this.height = rect.height;
	}
	
	@Override
	public void updateView() {
		// Nothing
	}
	
}
