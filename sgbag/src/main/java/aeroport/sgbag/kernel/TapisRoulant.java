package aeroport.sgbag.kernel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class TapisRoulant extends FileBagage {

	@Getter
	@Setter
	private int length;
	
	@Getter
	@Setter
	private int nbTicsSpawnBagage = 30;
	
	private int nbTicsRemaining = nbTicsSpawnBagage;

	public Boolean hasReadyBagage() {
		for (Bagage b : this.listBagages) {
			if (b.getPosition() == this.length) {
				return true;
			}
		}

		return false;
	}

	public Boolean update() {
		
		
		return null;
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