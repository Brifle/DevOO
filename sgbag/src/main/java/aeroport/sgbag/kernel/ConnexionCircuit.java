package aeroport.sgbag.kernel;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class ConnexionCircuit extends Noeud {

	@Getter
	@Setter
	private FileBagage fileBagage;

	public ConnexionCircuit(FileBagage f) {
		this.fileBagage = f;
		if (f != null)
			f.setConnexionCircuit(this);
	}

	public Boolean update() {
		log.trace("Update de la ConnexionCircuit " + this);
		if (hasChariot() && ++ticksToUpdate >= tickThresholdToUpdate) {
			if (getListeChariot().getFirst().getDestination() == this) {

				// Le chariot est arrivé à destination
				log.debug("Chariot arrivé à destination " + getListeChariot().getFirst());

				if (fileBagage instanceof TapisRoulant) {
					
					if (((TapisRoulant) fileBagage).hasReadyBagage()
							&& getListeChariot().getFirst().getBagage() == null) {
						this.getListeChariot()
								.getFirst()
								.setBagage(
										((TapisRoulant) fileBagage)
												.getBagageIfReady());
						getListeChariot().getFirst().getBagage().setParent(getListeChariot().getFirst());
						log.debug("Bagage du chariot : " + getListeChariot().getFirst().getBagage());
						moveToNextRail();
					}
				} else if (getListeChariot().getFirst().hasBagage()) {

					// On retire le bagage

					this.getListeChariot().getFirst()
							.moveBagageToFile(fileBagage);
					moveToNextRail();
				} else {

					// Le chariot est arrivé à destination mais n'a pas de
					// bagage à retirer
					
					moveToNextRail();

				}
			} else {
				moveToNextRail();
			}
		} else if (!hasChariot()) {
			ticksToUpdate = 0;
		}
		return true;
	}

}
