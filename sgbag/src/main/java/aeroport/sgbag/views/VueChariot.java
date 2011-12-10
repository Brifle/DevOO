package aeroport.sgbag.views;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

import aeroport.sgbag.controler.ViewSelector;
import aeroport.sgbag.kernel.*;
import aeroport.sgbag.utils.Rectangle2D;

@NoArgsConstructor
public class VueChariot extends VueElem {

	@Getter
	@Setter
	private Chariot chariot;

	public VueChariot(Canvas parent, Chariot chariot) {
		super((VueHall) parent);
		this.image = new Image(parent.getDisplay(), "data/img/chariot.png");

		this.chariot = chariot;
		Rectangle rect = image.getBounds();
		width = chariot.getLength();
		height = width * rect.height / rect.width;

	}

	public void updateView() {
		ElementCircuit parent = chariot.getParent();
		VueElem vueParent = (VueElem) ViewSelector.getInstance()
				.getViewForKernelObject(parent);

		if (parent instanceof Noeud) {
			this.x = vueParent.x;
			this.y = vueParent.y;
		} else if (parent instanceof Rail) {
			Rail railParent = (Rail) parent;

			// Calculate the Chariot's position :

			Rectangle2D rect = vueParent.getRectangle2D();
			double rapport = ((double) chariot.getPosition())
					/ ((double) railParent.getLength());

			int xDebutRail = rect.getBasGauche().x
					+ (rect.getHautGauche().x - rect.getBasGauche().x) / 2;
			int yDebutRail = rect.getBasGauche().y
					+ (rect.getHautGauche().y - rect.getBasGauche().y) / 2;
			int xFinRail = rect.getBasDroit().x
					+ (rect.getHautDroit().x - rect.getBasDroit().x) / 2;
			int yFinRail = rect.getBasDroit().y
					+ (rect.getHautDroit().y - rect.getBasDroit().y) / 2;

			int offsetX = (int) (rapport * (xFinRail - xDebutRail));
			int offsetY = (int) (rapport * (yFinRail - yDebutRail));

			this.x = xDebutRail + offsetX;
			this.y = yDebutRail + offsetY;

			// Set the Chariot's angle :

			this.angle = vueParent.angle;
		}

	}

}
