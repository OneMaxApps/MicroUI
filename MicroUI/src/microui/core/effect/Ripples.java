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
		setVisible(true);
		color = new Color(0,64);		
		circle = new Circle();
		
		this.component = requireNonNull(component, "bounds cannot be null");
		
		component.onClick(() -> {
			if(pg == null) {
				createGraphics();
			}
			
			checkResizing();
		});
		
		isEnabled = true;
	}

	@Override
	public void render() {
		if(!isEnabled || pg == null || !circle.isLauch) { return; }
		
		
		
		if(pg.width != 0 && pg.height != 0) {
		pg.beginDraw();
		pg.clear();
		circle.draw(pg);
		pg.endDraw();
		ctx.image(pg, component.getPadX(),component.getPadY(),component.getPadWidth(),component.getPadHeight());
		}
		
	}
	
	public final Component getComponent() {
		return component;
	}

	public final void setComponent(Component component) {
		this.component = requireNonNull(component, "bounds cannot be null");
	}

	public final void lauch() {
		circle.resetSize();
		circle.resetPosition();
		circle.isLauch = true;
	}
	
	public final Color getColor() {
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
		if(component.getPadWidth() <= 1 || component.getPadHeight() <= 1) { return false; }
		
		return pg.width != (int) component.getPadWidth() || pg.height != (int) component.getPadHeight();
	}
	
	private final void createGraphics() {
		pg = ctx.createGraphics((int) max(1,component.getPadWidth()),(int) max(1,component.getPadHeight()),ctx.sketchRenderer());
		Metrics.register(pg);
	}

	private final class Circle {
		float x,y,radius;
		boolean isLauch;

		void draw(PGraphics pGraphics) {
			
			pGraphics.noStroke();
			pGraphics.fill(color.get(),constrain(255-radius*.5f,0,255));
			pGraphics.circle(x, y, radius);
			
			if(isLauch) {
				playAnim();
			}
		}
		
		void incSize() {
			radius += constrain(min(component.getPadWidth()*.2f,component.getPadHeight()*.2f),1,20);
		}
		
		void playAnim() {
			incSize();
			if(radius > max(component.getPadWidth()*2,component.getPadHeight()*2)) {
				radius = 0;
				isLauch = false;
			}
		}
		
		void resetSize() {
			radius = 0;
		}
		
		void resetPosition() {
			x = ctx.mouseX-component.getPadX();
			y = ctx.mouseY-component.getPadY();
		}
	}
}