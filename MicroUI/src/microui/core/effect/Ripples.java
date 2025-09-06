package microui.core.effect;

import static java.util.Objects.requireNonNull;
import static processing.core.PApplet.constrain;
import static processing.core.PApplet.max;
import static processing.core.PApplet.min;

import microui.core.base.Component;
import microui.core.base.View;
import microui.core.style.Color;
import microui.util.Metrics;
import processing.core.PGraphics;

public final class Ripples extends View {
	private final Color color;
	private final Circle circle;
	private Component component;
	private PGraphics pg;
	private boolean isEnabled;
	
	public Ripples(Component component) {
		super();
		color = new Color(0,64);
		
		circle = new Circle();
		
		setVisible(true);
		this.component = requireNonNull(component, "bounds cannot be null");
		createGraphics();
		isEnabled = true;
	}

	@Override
	public void update() {
		if(!isEnabled) { return; }
		
		checkResizing();
		
		if(pg.width != 0 && pg.height != 0) {
		pg.beginDraw();
		pg.clear();
		circle.draw(pg);
		pg.endDraw();
		ctx.image(pg, component.getContentX(),component.getContentY(),component.getContentWidth(),component.getContentHeight());
		}
		
	}
	
	public final Component getComponent() {
		return component;
	}

	public final void setComponent(Component component) {
		this.component = requireNonNull(component, "bounds cannot be null");
	}

	public final void initAnim() {
		circle.resetSize();
		circle.resetPosition();
		circle.start = true;
	}
	
	public final Color getColor() {
		System.out.println("created a new Color object");
		return new Color(color);
	}
	
	public final void setColor(Color color) {
		this.color.set(requireNonNull(color, "color cannot be null"));
	}
	
	public final boolean isEnabled() {
		return isEnabled;
	}

	public final void setEnabled(boolean enabled) {
		this.isEnabled = enabled;
	}

	private final void checkResizing() {
		if(!isVisible() || ctx.mousePressed) { return; }
		
		if(isResized()) { createGraphics(); }
		
	}
	
	private final boolean isResized() {
		return pg.width != (int) component.getWidth() || pg.height != (int) component.getHeight();
	}
	
	private final void createGraphics() {
		pg = ctx.createGraphics((int) max(1,component.getWidth()),(int) max(1,component.getHeight()),ctx.sketchRenderer());
		Metrics.register(pg);
	}

	private final class Circle {
		private float x,y,radius;
		private boolean start;

		private final void draw(PGraphics pGraphics) {
			requireNonNull(pGraphics, "pGraphics cannot be null");
			
			pGraphics.noStroke();
			pGraphics.fill(color.get(),constrain(255-radius*.5f,0,255));
			pGraphics.circle(x, y, radius);
			
			if(start) {
				playAnim();
			}
		}
		
		private final void incSize() {
			radius += constrain(min(component.getWidth()*.2f,component.getHeight()*.2f),1,20);
		}
		
		private final void playAnim() {
			incSize();
			if(radius > max(component.getWidth()*2,component.getHeight()*2)) {
				radius = 0;
				start = false;
			}
		}
		
		private final void resetSize() {
			radius = 0;
		}
		
		private final void resetPosition() {
			x = ctx.mouseX-component.getX();
			y = ctx.mouseY-component.getY();
		}
	}
}