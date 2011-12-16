package aeroport.sgbag.kernel;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import aeroport.sgbag.utils.UtilsCircuit;

/**
 * Modèle représentant un tapis roulant.
 * 
 * @author Jonàs Bru Monserrat, Thibaut Patel, Mathieu Sabourin
 */
@RequiredArgsConstructor
@Log4j
public class TapisRoulant extends FileBagage {
	protected static String kObjName = "Tapis roulant";

	@Getter
	@Setter
	@NonNull
	private int length;

	@Getter
	@Setter
	@NonNull
	private int vitesseTapis; // Ex:5

	@Getter
	@Setter
	@NonNull
	private int distanceEntreBagages; // Ex:30

	@Getter
	@Setter
	@NonNull
	private boolean autoBagageGeneration; // Ex:true

	private int nbCharriotsEnChemin = 0;

	/**
	 * Indique si le tapis présente un bagage prêt à être déposé.
	 * @return true si c'est le cas, false sinon.
	 */
	public boolean hasReadyBagage() {
		for (Bagage b : this.listBagages) {
			if (b.getPosition() == this.length) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Indique si le tapis possède assez de bagage pour accueillir
	 * un nouveau bagage.
	 * @return true si c'est le cas, false sinon.
	 */
	public boolean hasPlaceForBagage(){
		return this.listBagages.isEmpty()
				|| this.listBagages.get(this.listBagages.size() - 1)
				.getPosition() >= distanceEntreBagages;
	}

	/**
	 * Mets à jour le contenu du tapis roulant, en déplaçant les
	 * bagages qu'il contient.
	 */
	public boolean update() {
		log.trace("Update tapis roulant");
		if (!hasReadyBagage()) {

			if (!this.listBagages.isEmpty()) {
				int delta = this.length - this.listBagages.get(0).getPosition();

				int deplacement;

				if (delta < this.vitesseTapis) {
					deplacement = delta;
				} else {
					deplacement = this.vitesseTapis;
				}

				for (Bagage b : this.listBagages) {
					b.moveBy(deplacement);
				}
			}
		}

		if (this.autoBagageGeneration) {
			if (hasPlaceForBagage()) {

				Bagage b = UtilsCircuit.getUtilsCircuit().generateBagage();
				this.addBagage(b);
				b.setParent(this);
			}
		}

		return true;
	}

	/**
	 * Retire un bagage de la file et le retourne, si c'est possible.
	 * @return Un bagage si un bagage est disponible, null sinon.
	 */
	public Bagage getBagageIfReady() {
		for (Bagage b : this.listBagages) {
			if (b.getPosition() == this.length) {
				this.removeBagage(b);

				nbCharriotsEnChemin--;

				return b;
			}
		}

		return null;
	}

	/**
	 * Retourne une note correspondant au besoin en charriots du tapis en
	 * question. Plus un tapis a des bagages et moins il y a des charriots qui
	 * se dirigent actuellement vers ce tapis, plus cette note sera elevée.
	 * 
	 * @return La note du tapis.
	 */
	public int getNoteBesoinBagages() {
		return this.listBagages.size() - nbCharriotsEnChemin;
	}

	/**
	 * Notifie au tapis qu'un charriot est en chemin.
	 */
	public void chariotIncoming() {
		nbCharriotsEnChemin++;
	}

	/**
	 * Remet a zero le nombre de charriots qui se dirigent vers le tapis.
	 */
	public void resetChariotIncomingNumber() {
		nbCharriotsEnChemin = 0;
	}

}
