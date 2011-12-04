package aeroport.sgbag.kernel;

import java.util.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false) //Equals sert à la classe Circuit
public class Noeud extends ElementCircuit {

	@Getter
	@Setter
	private ArrayList<Rail>  railsSortie;

}