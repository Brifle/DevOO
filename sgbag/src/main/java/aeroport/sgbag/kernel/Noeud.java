package aeroport.sgbag.kernel;

import java.util.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class Noeud extends ElementCircuit {

	@Setter
	@Getter
	private LinkedList<Rail> railsSortie;

	@Setter
	@Getter
	private LinkedList<Rail> railsEntree;
	
	public Boolean update(){
		return null;
	}
}
