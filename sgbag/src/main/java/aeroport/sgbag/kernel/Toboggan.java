package aeroport.sgbag.kernel;

import lombok.Getter;
import lombok.Setter;

public class Toboggan extends FileBagage {

	@Getter
	@Setter
	private int nbTicsBagagesRemains = 10;

	private int remainingNbTics = 10;

	@Getter
	@Setter
	private boolean autoDeleteBagages = true;

	public boolean update() {
		if (autoDeleteBagages) {
			if (!this.listBagages.isEmpty()) {
				if (remainingNbTics > 0)
					remainingNbTics--;

				if (remainingNbTics == 0) {
					this.listBagages.remove(0);
					remainingNbTics = nbTicsBagagesRemains;
				}
			}

			return true;
		} else {
			return false;
		}

	}
}