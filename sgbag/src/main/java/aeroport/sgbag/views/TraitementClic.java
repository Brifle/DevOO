/**
 * 
 */
package aeroport.sgbag.views;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;

import aeroport.sgbag.controler.Simulation;
import aeroport.sgbag.controler.Simulation.Etat;
import aeroport.sgbag.controler.Simulation.Mode;
import aeroport.sgbag.kernel.Noeud;

/**
 * Gère les clics sur une VueHall.
 * 
 * @author Arnaud Lahache
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
		Point clickedPoint = new Point(mouse.x - vueHall.getOrigin().x, mouse.y
				- vueHall.getOrigin().y);
		Viewable clickedView = vueHall.getClickedView(clickedPoint.x,
				clickedPoint.y);

		log.debug("Clicked @ x=" + clickedPoint.x + ", y=" + clickedPoint.y);

		if (clickedView == vueHall) {

			// On clique sur la fenêtre principale

			if (s.getMode() == Mode.MANUEL) {

				// mode MANUEL :

				if (s.getEtat() == Etat.CHOIX_DESTINATION_CHARIOT) {
					chariotADeplacer = null;

					// Supprimer les flèches sur les rails disponibles :
					s.deleteFleches();
				}

			}

			s.setEtat(Etat.NORMAL);
			s.setSelectedElem(null);
		} else {
			VueElem elem = (VueElem) clickedView;

			if (s.getMode() == Mode.MANUEL) {

				// Contrôles mode MANUEL :

				if (s.getEtat() == Etat.NORMAL || s.getEtat() == Etat.SELECTION) {

					if (elem instanceof VueChariot) {

						VueChariot vueChariot = (VueChariot) elem;

						if (vueChariot.getChariot().getParent() instanceof Noeud) {

							log.debug("Click sur un chariot.");

							chariotADeplacer = vueChariot;
							s.setEtat(Etat.CHOIX_DESTINATION_CHARIOT);

							// Afficher les flèches :
							Noeud n = (Noeud) vueChariot.getChariot()
									.getParent();
							s.displayFleches(n.getRailsSortie());
						}

					} else {
						s.setEtat(Etat.SELECTION);
					}

				} else if (s.getEtat() == Etat.CHOIX_DESTINATION_CHARIOT) {
					// Si on est sur un noeud et qu'on clique sur un rail
					log.debug("Choix destination chariot"
							+ (chariotADeplacer.getChariot().getParent() instanceof Noeud));
					if (elem instanceof VueRail
							&& chariotADeplacer.getChariot().getParent() instanceof Noeud) {
						VueRail nextVueRail = (VueRail) elem;
						// On vérifie que le rail est bien adjacent au noeud
						if (nextVueRail
								.getRail()
								.getNoeudPrecedent()
								.equals((Noeud) chariotADeplacer.getChariot()
										.getParent())) {
							chariotADeplacer
									.getChariot()
									.setCheminPrevu(
											vueHall.getHall()
													.getCircuit()
													.calculChemin(
															nextVueRail
																	.getRail()
																	.getNoeudPrecedent(),
															nextVueRail
																	.getRail()
																	.getNoeudSuivant()));
							if (chariotADeplacer.getChariot().getBagage() == null) {
								chariotADeplacer.getChariot()
										.setDestination(
												nextVueRail.getRail()
														.getNoeudSuivant());
							} else {
								chariotADeplacer.getChariot().setDestination(
										chariotADeplacer.getChariot()
												.getBagage().getDestination());
							}
							log.debug("Chariot deplacé");
						} else {
							log.debug("Element non adjacent au chariot.");
						}
					} else {
						log.debug("Clic sur autre chose qu'un chariot...");
					}
					s.deleteFleches();
					chariotADeplacer = null;
					s.setEtat(Etat.NORMAL);
				} else if (s.getEtat() == Etat.CHOIX_DESTINATION_BAGAGE) {
					s.createBagage(s.getSelectedElem(), elem);
					s.setEtat(Etat.NORMAL);
				} else {
					s.setEtat(Etat.SELECTION);
				}

			} else {
				s.setEtat(Etat.SELECTION);
			}

			s.setSelectedElem(elem);
		}
	}

}
