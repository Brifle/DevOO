package aeroport.sgbag.kernel;

import lombok.Getter;
import lombok.Setter;

/*
 */
public class ConnexionCircuit extends Noeud {

	@Getter
	@Setter
	private FileBagage fileBagage;
	
	public Boolean update() {
		if(hasChariot() && ++ticksToUpdate >= tickThresholdToUpdate) {
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
		} else if(!hasChariot()){
			ticksToUpdate = 0;
		}
		return true;
	}

}