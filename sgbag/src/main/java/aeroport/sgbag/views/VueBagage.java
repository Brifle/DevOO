/**
 * 
 */
package aeroport.sgbag.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import aeroport.sgbag.controler.ViewSelector;
import aeroport.sgbag.kernel.Bagage;
import aeroport.sgbag.kernel.Chariot;
import aeroport.sgbag.kernel.TapisRoulant;
import aeroport.sgbag.kernel.Toboggan;
import aeroport.sgbag.utils.Rectangle2D;

/**
 * @author Arnaud Lahache
 *
 */
@NoArgsConstructor
public class VueBagage extends VueElem {

	@Getter
	@Setter
	private Bagage bagage;
	
	public VueBagage(VueHall parent) {
		super(parent);
		this.image = new Image(parent.getDisplay(), "data/img/bagage.png");
		
		Rectangle rect = image.getBounds();
		this.width = rect.width;
		this.height = rect.height;
	}
	
	/**
	 * @see aeroport.sgbag.views.VueElem#updateView()
	 */
	@Override
	public void updateView() {
		Object parent = bagage.getParent();
		VueElem vueParent = (VueElem) ViewSelector.getInstance()
				.getViewForKernelObject(parent);

		if (parent instanceof Chariot) {
			this.x = vueParent.x + vueParent.getOffsetX();
			this.y = vueParent.y +  vueParent.getOffsetY();
			this.angle = vueParent.angle;
		} else if (parent instanceof TapisRoulant) {
			TapisRoulant tapisParent = (TapisRoulant) parent;

			// Calculate the Bagage's position :

			Rectangle2D rect = vueParent.getRectangle2D();
			double rapport = ((double) bagage.getPosition())
					/ ((double) tapisParent.getLength());

			int xDebutTapis = rect.getBasGauche().x
					+ (rect.getHautGauche().x - rect.getBasGauche().x) / 2;
			int yDebutTapis = rect.getBasGauche().y
					+ (rect.getHautGauche().y - rect.getBasGauche().y) / 2;
			int xFinTapis = rect.getBasDroit().x
					+ (rect.getHautDroit().x - rect.getBasDroit().x) / 2;
			int yFinTapis = rect.getBasDroit().y
					+ (rect.getHautDroit().y - rect.getBasDroit().y) / 2;

			int offsetX = (int) (rapport * (xFinTapis - xDebutTapis));
			int offsetY = (int) (rapport * (yFinTapis - yDebutTapis));

			this.x = xDebutTapis + offsetX;
			this.y = yDebutTapis + offsetY;

			// Set the Chariot's angle :

			this.angle = vueParent.angle;
		} else {
			
			if (parent instanceof Toboggan) {
				opacity = ((float) ((Toboggan) parent).getRemainingNbTics() / (float) ((Toboggan) parent).getNbTicsBagagesRemains());
			} 
			
			// Calculate the Bagage's position :

			Rectangle2D rect = vueParent.getRectangle2D();
			double rapport = 0.79;

			int xDebutTobo = (rect.getBasDroit().x + rect.getBasGauche().x) / 2;
			int yDebutTobo = (rect.getBasDroit().y + rect.getBasGauche().y) / 2;
			int xFinTobo = (rect.getHautGauche().x + rect.getHautDroit().x) / 2;
			int yFinTobo = (rect.getHautGauche().y + rect.getHautDroit().y) / 2;

			int offsetX = (int) (rapport * (xFinTobo - xDebutTobo));
			int offsetY = (int) (rapport * (yFinTobo - yDebutTobo));

			this.x = xDebutTobo + offsetX;
			this.y = yDebutTobo + offsetY;
			
			this.angle = vueParent.angle;

		}
	}

}
