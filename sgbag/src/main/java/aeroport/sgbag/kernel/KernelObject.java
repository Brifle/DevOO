package aeroport.sgbag.kernel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Base de tous les objets métiers.
 * 
 * Ce modèle permet d'associer un id et un nom à tous les objets
 * métiers, générés de manière cohérente.
 * 
 * @author Stanislas Signoud, Thibaut Patel
 */
@EqualsAndHashCode(exclude={"name"})
public abstract class KernelObject {
	private static int counter = 0;

	protected static String kTypeName = "Objet";
	protected static int kTypeCounter = 1; 

	@Getter
	final private int id;
	
	@Setter
	protected String name = null;
	
	/**
	 * Retourne le nom de l'objet.
	 * @return Chaîne contenant le nom de l'objet.
	 */
	public String getName(){
		if(name == null){
			name = kTypeName + " n°" + kTypeCounter;
			kTypeCounter++;
			return name;
		}else{
			return name;
		}
	}
	
	public KernelObject() {
		id = counter++;
	}
}
