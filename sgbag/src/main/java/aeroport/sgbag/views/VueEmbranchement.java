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
	
	
	public static int defaultSizeOfNode = 50;

	public VueEmbranchement(VueHall parent) {
		super(parent);
		image = new Image(parent.getDisplay(), "data/img/embranchement.png");
		
		this.width = defaultSizeOfNode;
		this.height = defaultSizeOfNode;
	}

	@Override
	public void updateView() {
	}
}
