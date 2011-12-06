package aeroport.sgbag.views;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;

import lombok.Getter;
import lombok.Setter;
import aeroport.sgbag.kernel.Chariot;
import aeroport.sgbag.kernel.TapisRoulant;

public class VueTapisRoulant extends VueElem{

	@Getter
	@Setter
	private TapisRoulant tapisRoulant;
	
	public VueTapisRoulant(Canvas parent, Chariot chariot) {
		super((VueHall) parent);
		this.image = new Image(parent.getDisplay(), "data/img/tapis.png");
	}
	
	@Override
	public void updateView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

}
