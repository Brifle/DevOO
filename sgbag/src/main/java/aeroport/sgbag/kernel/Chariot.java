package aeroport.sgbag.kernel;

import java.util.Vector;

public class Chariot extends ConteneurBagage {

    private ElementCircuit elementCircuit;
    private Noeud destination;
    /**
   * 
   * @element-type ElementCircuit
   */
  private Vector  cheminPrevu;

  public Rail getNextRail() {
  return null;
  }

  public void setParent(ElementCircuit parent) {
  }

  public void setPosition(Integer position) {
  }

  public Boolean isEmpty() {
  return null;
  }

  public Noeud getDestinationId(Integer destinationId) {
  return null;
  }

  public Boolean moveBagageToFile(FileBagage file) {
  return null;
  }

  public Boolean removeBagage() {
  return null;
  }

  public Integer getMaxMoveDistance() {
  return null;
  }

  public Boolean isColliding(Chariot chariotSuivant) {
  return null;
  }

  public Integer getPosition() {
  return null;
  }

  public Integer collideAt(Chariot chariotSuivant) {
  return null;
  }

  public Boolean moveBy(Integer distance) {
  return null;
  }

  public Boolean moveTo(Integer distance) {
  return null;
  }

  public Boolean update() {
  return null;
  }

}