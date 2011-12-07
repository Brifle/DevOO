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

	public VueChariot(Canvas parent, Chariot chariot) {
		super((VueHall) parent);
		this.image = new Image(parent.getDisplay(), "data/img/chariot.png");
		
		Rectangle rect = image.getBounds();
		width  = chariot.getLength();
		height = width * rect.height/ rect.width;
		
	}

	public void updateView() {			
		//TODO
	}

}
