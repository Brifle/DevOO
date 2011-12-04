package aeroport.sgbag.kernel;

import java.util.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class Noeud extends ElementCircuit {


	@Setter
	private LinkedList<Rail> railsSortie;
	
	@Setter
	private LinkedList<Rail> railsEntree;
}
