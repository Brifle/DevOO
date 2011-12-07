package aeroport.sgbag.views;

import lombok.Getter;
import lombok.Setter;

import org.eclipse.swt.graphics.Image;

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
}
