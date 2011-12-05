package aeroport.sgbag.kernel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class TapisRoulant extends FileBagage {

	@Getter
	@Setter
	private int length;

	@Getter
	@Setter
	private int vitesseTapis = 5;

	@Getter
	@Setter
	private int distanceEntreBagages = 30;

	@Getter
	@Setter
	private boolean autoBagageGeneration = true;

	public Boolean hasReadyBagage() {
		for (Bagage b : this.listBagages) {
			if (b.getPosition() == this.length) {
				return true;
			}
		}

		return false;
	}

	public Boolean update() {
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
			if (this.listBagages.isEmpty()
					|| this.listBagages.get(this.listBagages.size() - 1)
							.getPosition() >= distanceEntreBagages) {
				
				Bagage b = BagageFactory.getBagageFactory().generateBagage();
				this.addBagage(b);
			}
		}

		return true;
	}

	public Bagage getBagageIfReady() {
		for (Bagage b : this.listBagages) {
			if (b.getPosition() == this.length) {
				this.removeBagage(b);
				return b;
			}
		}

		return null;
	}

}