package aeroport.sgbag.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Template contenant une paire d'objets.
 * 
 * @author Thibaut Patel
 *
 * @param <X> Classe du premier objet.
 * @param <Y> Classe du second objet.
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Pair<X,Y> {
	@Getter
	@Setter
	private X x;
	
	@Getter
	@Setter
	private Y y;

}
