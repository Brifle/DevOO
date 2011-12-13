package aeroport.sgbag.utils;

import org.eclipse.swt.graphics.GC;

public class ParticleManager {
	private Particle[] particles;
	private int next;
	
	private static ParticleManager particleManager = null;
	
	public static ParticleManager getParticleManager() {
		if(particleManager == null) {
			particleManager = new ParticleManager();
		}
		return particleManager;
	}
	
	private ParticleManager() {
		this(150);
	}
	
	private ParticleManager(int number) {
		particles = new Particle[number];
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new Particle(3, 0.6f, 0.6f, 0.6f, 700);
		}
		next = -1;
	}
	
	public void update() {
		for (int i = 0; i < particles.length; i++) {
			particles[i].update();
		}
	}
	
	public void draw(GC buffer) {
		for (int i = 0; i < particles.length; i++) {
			particles[i].draw(buffer);
		}
	}
	
	public void throwParticle(float x, float y) {
		next = (++next) % particles.length;
		particles[next].initialize(x, y, x+((int)(Math.random()*11-5)), y+((int)(Math.random()*11-5)), 0, 0);
	}
}
