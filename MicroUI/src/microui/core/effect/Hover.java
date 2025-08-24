package microui.core.effect;

import static microui.event.EventType.INSIDE;
import static microui.event.EventType.OUTSIDE;
import static microui.event.EventType.PRESSED;
import static microui.event.EventType.UNPRESSED;

import microui.core.base.Component;
import microui.core.base.View;
import microui.core.style.Color;

public final class Hover extends View {
	private final Color color;
	private final Component component;
	private boolean isInside,isPressed,isEnable;
	
    public Hover(Component component) {
    	visible();
    	color = new Color(0,100);
		this.component = component;
		component.addListener(INSIDE, () -> isInside = true);
		component.addListener(OUTSIDE, () -> isInside = false);
		component.addListener(PRESSED, () -> isPressed = true);
		component.addListener(UNPRESSED, () -> isPressed = false);
		isEnable = true;
	}

	@Override
	public void update() {
		if(!isEnable) { return; }
		
		if(isInside) {
			app.pushStyle();
			color.use();
			app.rect(component.getX(),component.getY(),component.getWidth(),component.getHeight());
			app.popStyle();
		
			if(isPressed) {
				app.fill(0,64);
				app.rect(component.getX(),component.getY(),component.getWidth(),component.getHeight());
			}
		
		}
	}

	public final Color getColor() {
		System.out.println("created a new Color object");
		return new Color(color);
	}
	
	public final void setColor(Color color) {
		System.out.println("created a new Color object");
		color.set(color);
	}

	public final boolean isEnabled() {
		return isEnable;
	}

	public final void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}
	
}