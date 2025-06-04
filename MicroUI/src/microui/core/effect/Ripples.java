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
	public final Circle circle;
	private final Bounds form;
	private PGraphics pg;
	
	public Ripples(Bounds form) {
		super();
		circle = new Circle();

		visible = true;
		this.form = form;
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
		app.image(pg, form.getX(),form.getY(),form.getWidth(),form.getHeight());
		}
		
	}

	public final void checkResizing() {
		if(!visible || app.mousePressed) { return; }
		
		if(isResized()) { createGraphics(); }
		
	}
	
	private final boolean isResized() {
		return pg.width != (int) form.getWidth() || pg.height != (int) form.getHeight();
	}
	
	public final void initAnim() {
		circle.resetSize();
		circle.resetPosition();
		circle.start = true;
	}
	
	private final void createGraphics() {
		pg = app.createGraphics((int) max(1,form.getWidth()),(int) max(1,form.getHeight()));
		Metrics.register(pg);
	}

	public final class Circle {
		public Color fill;
		private float x,y,radius;
		private boolean start;
		
		public Circle() {
			fill = new Color(0);
		}

		private final void draw(PGraphics pg) {
			pg.noStroke();
			pg.fill(fill.get(),constrain(255-radius*.5f,0,255));
			pg.circle(x, y, radius);
			
			if(start) {
				playAnim();
			}
		}
		
		private final void incSize() {
			radius += constrain(min(form.getWidth()*.2f,form.getHeight()*.2f),1,20);
		}
		
		private final void playAnim() {
			incSize();
			if(radius > max(form.getWidth()*2,form.getHeight()*2)) {
				radius = 0;
				start = false;
			}
		}
		
		private final void resetSize() {
			radius = 0;
		}
		
		private final void resetPosition() {
			x = app.mouseX-form.getX();
			y = app.mouseY-form.getY();
		}
	}
}