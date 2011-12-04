package aeroport.sgbag.kernel;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class Rail extends ElementCircuit {

	@Getter
	@Setter
	private Noeud noeudSuivant;

}