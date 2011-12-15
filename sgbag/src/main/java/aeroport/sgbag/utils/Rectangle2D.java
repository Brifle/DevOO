package aeroport.sgbag.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.eclipse.swt.graphics.Point;

/**
 * Rectangle possédant quatre points.
 * 
 * Structure utilitaire utilisée dans des calculs dans plusieurs méthodes
 * de différentes classes.
 * 
 * @author Arnaud Lahache
 */
@AllArgsConstructor
public class Rectangle2D {

	@Getter
	@Setter
	private Point hautGauche;
	
	@Getter
	@Setter
	private Point hautDroit;
	
	@Getter
	@Setter
	private Point basGauche;
	
	@Getter
	@Setter
	private Point basDroit;
	
}
