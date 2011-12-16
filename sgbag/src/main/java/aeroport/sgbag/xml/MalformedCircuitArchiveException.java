package aeroport.sgbag.xml;

/**
 * Exception représentant l'impossibilité de comprendre le XML indiqué dans
 * un fichier.
 * 
 * Cela signifie que l'arbre XML contenu dans le fichier ne correspond pas aux
 * entités attendues, ou que l'arbre XML est mal formé.
 * 
 * @author Stanislas Signoud
 */
public class MalformedCircuitArchiveException extends Exception {
	private static final long serialVersionUID = 1L;
}
