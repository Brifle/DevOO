package aeroport.sgbag.kernel;

import lombok.*;

/**
 * Modèle représentant un toboggan.
 * 
 * @author Jonàs Bru Monserrat, Thibaut Patel.
 */
@AllArgsConstructor
@NoArgsConstructor
public class Toboggan extends FileBagage {
	protected static String kObjName = "Toboggan";

	@Getter
	@Setter
	private int nbTicsBagagesRemains = 10;

	@Getter
	private int remainingNbTics = 10;

	@Getter
	@Setter
	private boolean autoDeleteBagages = true;

	/**
	 * Mets à jour le contenu du toboggan, supprimant les bagages
	 * qui y tombent au bout de quelques tics d'horloges.
	 */
	public boolean update() {
		if (autoDeleteBagages && !this.listBagages.isEmpty()) {
			if (remainingNbTics > 0) {
				remainingNbTics--;
			}

			if (remainingNbTics == 0) {
				Bagage b = this.listBagages.get(0);
				this.listBagages.remove(b);
				this.getConnexionCircuit().getParent().getParent().getBagagesList().remove(b);
				remainingNbTics = nbTicsBagagesRemains;
			}
		} else if(autoDeleteBagages) { // Si on a pas de bagages, on remet le tic à 0
			remainingNbTics = nbTicsBagagesRemains;
		}
		return autoDeleteBagages;
	}
}