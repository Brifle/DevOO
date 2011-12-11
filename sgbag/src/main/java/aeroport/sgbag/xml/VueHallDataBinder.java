package aeroport.sgbag.xml;

import java.util.LinkedList;
import java.util.TreeMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import aeroport.sgbag.kernel.Hall;
import aeroport.sgbag.views.VueElem;

@NoArgsConstructor
@AllArgsConstructor
public class VueHallDataBinder {
	@Getter
	@Setter
	private Hall hall;
	
	@Getter
	@Setter
	private TreeMap<Integer, LinkedList<VueElem>> calques;
}