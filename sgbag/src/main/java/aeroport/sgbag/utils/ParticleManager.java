package aeroport.sgbag.utils;

import org.eclipse.swt.graphics.GC;

/**
 * Gère les particules.
 * 
 * @author Thibaut Patel
 */
public class ParticleManager {
	private Particle[] particles;
	private int next;
	
	private static ParticleManager particleManager = null;
	
	/**
	 * Retourne une instance du gestionnaire de particule (singleton).
	 * @return Instance de ParticleManager.
	 */
	public static ParticleManager getParticleManager() {
		if(particleManager == null) {
			particleManager = new ParticleManager();
		}
		return particleManager;
	}
	
	/**
	 * Crée un gestionnaire de particule.
	 */
	private ParticleManager() {
		this(150);
	}
	
	/**
	 * Crée un gestionnaire de particule, contenant number particules.
	 * @param number Le nombre de particules à gérer.
	 */
	private ParticleManager(int number) {
		particles = new Particle[number];
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new Particle(2, 1f, 0.8f, 0f, 700);
		}
		next = -1;
	}
	
	/**
	 * Mets à jour les coordonnées de toutes les particules gérées.
	 */
	public void update() {
		for (int i = 0; i < particles.length; i++) {
			particles[i].update();
		}
	}
	
	/**
	 * Affiche toutes les particules gérées.
	 * @param buffer
	 */
	public void draw(GC buffer) {
		for (int i = 0; i < particles.length; i++) {
			particles[i].draw(buffer);
		}
	}
	
	/**
	 * Jette une particule d'une position donnée.
	 * @param x Position horizontale de jet de la particule.
	 * @param y Position verticale de jet de la particule.
	 */
	public void throwParticle(float x, float y) {
		next = (++next) % particles.length;
		int sensx = (Math.random() < 0.5 ? 1 : -1);
		int dx = sensx*((int)(Math.random()*7));
		int sensy = (Math.random() < 0.5 ? 1 : -1);
		int dy = sensy*((int)(Math.random()*7));
		particles[next].initialize(x+2*dx, y+2*dy, x+dx, y+dy, 0, 0);
	}
}
