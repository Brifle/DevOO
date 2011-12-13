/**
 * 
 */
package aeroport.sgbag.views;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import aeroport.sgbag.controler.Simulation;
import aeroport.sgbag.controler.Simulation.Etat;
import aeroport.sgbag.kernel.Noeud;

/**
 * @author Arnaud Lahache
 * 
 */
@Log4j
public class TraitementClic extends MouseAdapter {

	@Getter
	private VueHall vueHall;
	
	private VueChariot chariotADeplacer;

	public TraitementClic(VueHall vueHall) {
		this.vueHall = vueHall;
	}

	/**
	 * Implémentation de l'évènement du clic dans un Hall.
	 * 
	 * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
	 */
	@Override
	public void mouseUp(MouseEvent mouse) {
		Simulation s = vueHall.getSimulation();
		Viewable clickedView = vueHall.getClickedView(mouse.x, mouse.y);
		
		log.debug("Clicked @ x=" + mouse.x + ", y=" + mouse.y);
		
		if(clickedView == vueHall) {
			s.setEtat(Etat.NORMAL);
			s.setSelectedElem(null);
		} else {
			VueElem elem = (VueElem) clickedView;
			
			
			if(s.getEtat() == Etat.NORMAL || s.getEtat() == Etat.SELECTION) {
				if(elem instanceof VueChariot) {
					log.debug("Click sur un chariot.");
					chariotADeplacer = (VueChariot)elem;
					s.setEtat(Etat.CHOIX_DESTINATION_CHARIOT);
				} else {
					s.setEtat(Etat.SELECTION);
				}
			} else if(s.getEtat() == Etat.CHOIX_DESTINATION_CHARIOT) {
				//Si on est sur un noeud et qu'on clique sur un rail
				log.debug("Choix destination chariot" + (chariotADeplacer.getChariot().getParent() instanceof Noeud));
				if(elem instanceof VueRail && chariotADeplacer.getChariot().getParent() instanceof Noeud) {
					VueRail nextVueRail = (VueRail)elem;
					//On vérifie que le rail est bien adjacent au noeud
					if(nextVueRail.getRail().getNoeudPrecedent().equals((Noeud)chariotADeplacer.getChariot().getParent())) {
						chariotADeplacer.getChariot().setCheminPrevu(
								vueHall.getHall().getCircuit().calculChemin(
										nextVueRail.getRail().getNoeudPrecedent(),
										nextVueRail.getRail().getNoeudSuivant()
										)
								);
						chariotADeplacer.getChariot().setDestination(nextVueRail.getRail().getNoeudSuivant());
						log.debug("Chariot deplacé");
					} else {
						log.debug("Element non adjacent au chariot.");
					}
				} else {
					log.debug("Clic sur autre chose qu'un chariot...");
				}
				chariotADeplacer = null;
				s.setEtat(Etat.NORMAL);
			} else if(s.getEtat() == Etat.CHOIX_DESTINATION_BAGAGE) {
				s.createBagage(s.getSelectedElem(), elem);
				s.setEtat(Etat.NORMAL);
			} else {
				s.setEtat(Etat.SELECTION);
			}
			
			s.setSelectedElem(elem);
		}
	}

}
