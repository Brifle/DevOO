package aeroport.sgbag.views;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;

import aeroport.sgbag.kernel.Chariot;

public class VueChariot extends VueElem {
	
	public VueChariot(Canvas parent, Chariot chariot) {
		super(parent);
		this.image = new Image(parent.getDisplay(), "data/img/chariot.png");
		this.chariot = chariot;
	}
	
	public void updateView(){
		
		}
	
	public void draw(){
		chariot.getPosition(); 
		//drawImage(getImage(), X, Y);
	}
	
	private Chariot chariot;
}
