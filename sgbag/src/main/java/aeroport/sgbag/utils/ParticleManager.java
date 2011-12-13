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
			particles[i] = new Particle(2, 1f, 0.8f, 0f, 700);
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
		int sensx = (Math.random() < 0.5 ? 1 : -1);
		int dx = sensx*((int)(Math.random()*7));
		int sensy = (Math.random() < 0.5 ? 1 : -1);
		int dy = sensy*((int)(Math.random()*7));
		particles[next].initialize(x+2*dx, y+2*dy, x+dx, y+dy, 0, 0);
	}
}
