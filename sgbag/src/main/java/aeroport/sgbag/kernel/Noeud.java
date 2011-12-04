package aeroport.sgbag.kernel;

import java.util.LinkedList;

import lombok.Setter;

public class Noeud extends ElementCircuit {

	@Setter
	private LinkedList<Rail> railsSortie;
	
	@Setter
	private LinkedList<Rail> railsEntree;

}