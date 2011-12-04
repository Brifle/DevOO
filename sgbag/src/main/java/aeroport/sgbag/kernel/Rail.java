package aeroport.sgbag.kernel;

import lombok.Getter;
import lombok.Setter;

public class Rail extends ElementCircuit {

	@Getter
	@Setter
	private Noeud noeudSuivant;

	@Getter
	@Setter
	private Noeud noeudPrecedent;

	public Boolean update() {
		//TODO
		return null;
	}

}