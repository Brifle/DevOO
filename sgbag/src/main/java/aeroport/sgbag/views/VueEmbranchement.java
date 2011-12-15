package aeroport.sgbag.views;

import lombok.Getter;
import lombok.Setter;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import aeroport.sgbag.kernel.Noeud;

/**
 * Représente un embranchement (nœud reliant deux rails ou plus) à l'écran.
 * 
 * @author Michael Fagno, Mathieu Sabourin.
 */
public class VueEmbranchement extends VueElem {

	@Getter
	@Setter
	private Noeud noeud;
	
	
	public static int defaultSizeOfNode = 50;

	/**
	 * Crée une vue d'Embranchement, à l'aide de sa vue de Hall parente.
	 * @param parent VueHall parente.
	 */
	public VueEmbranchement(VueHall parent) {
		super(parent);
		image = new Image(parent.getDisplay(), "data/img/embranchement.png");
		
		this.width = defaultSizeOfNode;
		this.height = defaultSizeOfNode;
	}
	
	/**
	 * @see aeroport.sgbag.views.VueElem#updateView()
	 */
	@Override
	public void updateView() {
	}
}
