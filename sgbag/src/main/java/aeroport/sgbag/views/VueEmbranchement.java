package aeroport.sgbag.views;

import lombok.Getter;
import lombok.Setter;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import aeroport.sgbag.kernel.Noeud;

public class VueEmbranchement extends VueElem {

	@Getter
	@Setter
	private Noeud noeud;

	public VueEmbranchement(VueHall parent) {
		super(parent);
		image = new Image(parent.getDisplay(), "data/img/embranchement.png");
		
		Rectangle rect = image.getBounds();
		this.width = rect.width;
		this.height = rect.height;
	}

	@Override
	public void updateView() {
		// Nothing
	}
}
