package microui.core.effect;

import static processing.core.PApplet.constrain;
import static processing.core.PApplet.max;
import static processing.core.PApplet.min;

import microui.core.base.Bounds;
import microui.core.base.View;
import microui.core.style.Color;
import microui.util.Metrics;
import processing.core.PGraphics;

public final class Ripples extends View {
	public final Color fill;
	private final Circle circle;
	private final Bounds bounds;
	private PGraphics pg;
	
	public Ripples(Bounds bounds) {
		super();
		fill = new Color(0);
		
		circle = new Circle();
		
		visible = true;
		this.bounds = bounds;
		createGraphics();
		
	}

	@Override
	public void update() {
		checkResizing();
		
		if(pg.width != 0 && pg.height != 0) {
		pg.beginDraw();
		pg.clear();
		circle.draw(pg);
		pg.endDraw();
		app.image(pg, bounds.getX(),bounds.getY(),bounds.getWidth(),bounds.getHeight());
		}
		
	}

	public final void initAnim() {
		circle.resetSize();
		circle.resetPosition();
		circle.start = true;
	}
	
	private final void checkResizing() {
		if(!visible || app.mousePressed) { return; }
		
		if(isResized()) { createGraphics(); }
		
	}
	
	private final boolean isResized() {
		return pg.width != (int) bounds.getWidth() || pg.height != (int) bounds.getHeight();
	}
	
	private final void createGraphics() {
		pg = app.createGraphics((int) max(1,bounds.getWidth()),(int) max(1,bounds.getHeight()),app.sketchRenderer());
		Metrics.register(pg);
	}

	private final class Circle {
		private float x,y,radius;
		private boolean start;

		private final void draw(PGraphics pg) {
			pg.noStroke();
			pg.fill(fill.get(),constrain(255-radius*.5f,0,255));
			pg.circle(x, y, radius);
			
			if(start) {
				playAnim();
			}
		}
		
		private final void incSize() {
			radius += constrain(min(bounds.getWidth()*.2f,bounds.getHeight()*.2f),1,20);
		}
		
		private final void playAnim() {
			incSize();
			if(radius > max(bounds.getWidth()*2,bounds.getHeight()*2)) {
				radius = 0;
				start = false;
			}
		}
		
		private final void resetSize() {
			radius = 0;
		}
		
		private final void resetPosition() {
			x = app.mouseX-bounds.getX();
			y = app.mouseY-bounds.getY();
		}
	}
}