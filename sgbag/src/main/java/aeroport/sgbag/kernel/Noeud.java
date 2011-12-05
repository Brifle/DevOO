package aeroport.sgbag.kernel;

import java.util.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false) //Utilise par la classe Circuit
public class Noeud extends ElementCircuit {

	@Setter
	@Getter
	private LinkedList<Rail> railsSortie;

	@Setter
	@Getter
	private LinkedList<Rail> railsEntree;
	
	public Boolean update(){
		//TODO
		return null;
	}
	
	public Boolean moveToNextRail(){
		
		Chariot chariot = listeChariot.getLast();
		
		Rail prochainRail = chariot.getNextRail();
		
		if (!railsSortie.contains(prochainRail))
			return false;
		
		if (!prochainRail.registerChariot(chariot))
			return false;
			
		unregisterChariot();
		
		return true;
	}
}
