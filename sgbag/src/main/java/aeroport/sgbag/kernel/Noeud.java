package aeroport.sgbag.kernel;

import java.util.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class Noeud extends ElementCircuit {

	@Getter
	@Setter
	private ArrayList<Rail>  railsSortie;

}