package aeroport.sgbag.utils;

import org.eclipse.swt.graphics.GC;
import org.eclipse.wb.swt.SWTResourceManager;

public class Particle {
	private float x;
	private float y;
	
	private float oldx;
	private float oldy;
	
	private float ax;
	private float ay;

	private float r;
	private float g;
	private float b;
	private float a;

	private float radius;
	
	private long init;
	private int life;

	private boolean visible;
	
	private Particle() {
		//No code here
	}

	public Particle(float radius, float r, float g, float b, int life) {
		this.x = 0;
		this.y = 0;
		this.oldx = 0;
		this.oldy = 0;
		this.radius = radius;
		this.r = r;
		this.g = g;
		this.b = b;
		this.life = life;
		visible = false;
	}
	
	public void initialize(float x, float y, float oldx, float oldy, float ax, float ay) {
		this.x = x;
		this.y = y;
		this.oldx = oldx;
		this.oldy = oldy;
		this.ax = ax;
		this.ay = ay;
		init = System.currentTimeMillis();
		visible = true;
	}
	
	public void update() {
		if(visible) {
			float tmpx = x;
			float tmpy = y;
			x += x-oldx + ax;
			y += y-oldy + ay;
			oldx = tmpx;
			oldy = tmpy;
			if(System.currentTimeMillis() > init + life) {
				visible = false;
			}
			//System.out.println("update v=" + visible);
		}
	}

	public void draw(GC buffer) {
		
		if(visible) {
			a = (System.currentTimeMillis()-init)/(float)life;
			
			buffer.setBackground(SWTResourceManager.getColor(((int)r*255), ((int)g*255), ((int)b*255)));
			buffer.setAlpha((int)(a*255));
			buffer.fillOval((int)x, (int)y, (int)(radius*2), (int)(radius*2));
		}
	}

}
