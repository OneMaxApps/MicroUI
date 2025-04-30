package microUI.effect;

import static processing.core.PApplet.constrain;
import static processing.core.PApplet.max;
import static processing.core.PApplet.min;

import microUI.core.BaseForm;
import microUI.core.View;
import microUI.util.Color;
import processing.core.PGraphics;

public final class Ripples extends View {
	public Circle circle;
	private BaseForm form;
	private PGraphics pg;
	private int currentWidth,currentHeight;
	
	public Ripples(BaseForm form) {
		super(form.getContext());
		circle = new Circle();
		visible = true;
		this.form = form;
		pg = app.createGraphics(currentWidth = (int) form.getW(),currentHeight = (int) form.getH(),app.sketchRenderer());
	}

	@Override
	public void update() {
		checkResizing();
		
		if(pg.width != 0 && pg.height != 0) {
		pg.beginDraw();
		pg.clear();
		circle.draw(pg);
		pg.endDraw();
		app.image(pg, form.getX(),form.getY(),form.getW(),form.getH());
		}
		
	}

	public final void checkResizing() {
		if(!visible || app.mousePressed) { return; }
		
		if(currentWidth != (int) form.getW() || currentHeight != (int) form.getH()) {
			pg = app.createGraphics(currentWidth = (int) form.getW(),currentHeight = (int) form.getH());
		}
	}
	
	public final void initAnim() {
		circle.resetSize();
		circle.resetPosition();
		circle.start = true;
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