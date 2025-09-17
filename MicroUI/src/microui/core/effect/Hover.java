package microui.core.effect;

import static java.util.Objects.requireNonNull;
import static processing.core.PApplet.map;

import microui.core.base.Component;
import microui.core.base.View;
import microui.core.style.Color;

//Status: STABLE - Do not modify
//Last Reviewed: 16.09.2025
public final class Hover extends View {
	private final Color color;
	private final Component component;
	private float timer,timerMax,speed;
	private boolean isEnabled;
	
	public Hover(Component component) {
		super();
		setVisible(true);
		
		color = new Color(0,100,255,24);
		
		this.component = requireNonNull(component,"component cannot be null");
	
		timerMax = 100;
		
		setSpeed(10);
		
		setEnabled(true);
	}

	@Override
	public void draw() {
		if(!isEnabled ) { return; }
		super.draw();
	}

	@Override
	protected void render() {
		ctx.noStroke();
		if(component.isMouseInside()) {
			if(timer < timerMax) { timer+=speed; }
		} else {
			if(timer > 0) { timer-=speed*2; }
		}
		
		if(component.isPressed()) {
			ctx.fill(0,getAlpha());
		} else {
			ctx.fill(color.getRed(),color.getGreen(),color.getBlue(),getAlpha());
		}
		
		ctx.rect(component.getPadX(),component.getPadY(),component.getPadWidth(),component.getPadHeight());
		
	}

	public Color getColor() {
		return new Color(color);
	}
	
	public void setColor(Color color) {
		requireNonNull(color,"color cannot be null");
		this.color.set(color);
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		if(speed <= 0) { 
			throw new IllegalArgumentException("speed for hover animation cannot be less or equal to zero");
		}
		this.speed = speed;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	private float getAlpha() {
		return map(timer,0,timerMax,0,color.getAlpha());
	}
	
}