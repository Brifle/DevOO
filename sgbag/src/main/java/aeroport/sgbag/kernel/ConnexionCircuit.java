package aeroport.sgbag.kernel;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class ConnexionCircuit extends Noeud {

	@Getter
	@Setter
	private FileBagage fileBagage;

	public Boolean update() {
		log.trace("Update de la ConnexionCircuit " + this);
		if(hasChariot()) {
			if(getListeChariot().getFirst().getDestination() == this) { //Chariot arrivé à destination
				if(fileBagage instanceof TapisRoulant) {
					if(((TapisRoulant)fileBagage).hasReadyBagage()) {
						this.getListeChariot().getFirst().setBagage(
								((TapisRoulant)fileBagage).getBagageIfReady());
						moveToNextRail();
					}   				
				} else { //Type toboggan
					this.getListeChariot().getFirst().moveBagageToFile(fileBagage);
					moveToNextRail();
				}
			} else {
				moveToNextRail();
			}  			
		}
		return true;
	}

}