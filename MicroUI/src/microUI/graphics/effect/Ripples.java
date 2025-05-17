package microUI.graphics.effect;

import static processing.core.PApplet.constrain;
import static processing.core.PApplet.max;
import static processing.core.PApplet.min;

import microUI.core.Bounds;
import microUI.core.View;
import microUI.event.Event;
import microUI.graphics.Color;
import microUI.util.Metrics;
import processing.core.PGraphics;

public final class Ripples extends View {
	public final Circle circle;
	private final Event event;
	private final Bounds form;
	private PGraphics pg;
	
	public Ripples(Bounds form) {
		super();
		circle = new Circle();
		event = new Event();
		visible = true;
		this.form = form;
		createGraphics();
		
	}

	@Override
	public void update() {
		event.listen(form);
		
		checkResizing();
		
		if(pg.width != 0 && pg.height != 0) {
		pg.beginDraw();
		pg.clear();
		circle.draw(pg);
		pg.endDraw();
		app.image(pg, form.getX(),form.getY(),form.getW(),form.getH());
		}
		
		if(event.clicked()) { initAnim(); }
	}

	public final void checkResizing() {
		if(!visible || app.mousePressed) { return; }
		
		if(isResized()) { createGraphics(); }
		
	}
	
	private final boolean isResized() {
		return pg.width != (int) form.getW() || pg.height != (int) form.getH();
	}
	
	public final void initAnim() {
		circle.resetSize();
		circle.resetPosition();
		circle.start = true;
	}
	
	private final void createGraphics() {
		pg = app.createGraphics((int) max(1,form.getW()),(int) max(1,form.getH()));
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
			radius += constrain(min(form.getW()*.2f,form.getH()*.2f),1,20);
		}
		
		private final void playAnim() {
			incSize();
			if(radius > max(form.getW()*2,form.getH()*2)) {
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