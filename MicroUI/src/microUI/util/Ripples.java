package microUI.util;

import static processing.core.PApplet.min;
import static processing.core.PApplet.max;
import static processing.core.PApplet.constrain;
import processing.core.PGraphics;

public final class Ripples extends View {
	public Circle circle;
	private BaseForm form;
	private PGraphics pg;
	private int currentWidth,currentHeight;
	
	public Ripples(BaseForm form) {
		super(form.app);
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
		app.image(pg, form.x,form.y,form.w,form.h);
		}
		
	}

	public final void checkResizing() {
		if(!visible || app.mousePressed) { return; }
		
		if(currentWidth != (int) form.w || currentHeight != (int) form.h) {
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
			radius += constrain(min(form.w*.2f,form.h*.2f),1,20);
		}
		
		private final void playAnim() {
			incSize();
			if(radius > max(form.w*2,form.h*2)) {
				radius = 0;
				start = false;
			}
		}
		
		private final void resetSize() {
			radius = 0;
		}
		
		private final void resetPosition() {
			x = app.mouseX-form.x;
			y = app.mouseY-form.y;
		}
	}
}