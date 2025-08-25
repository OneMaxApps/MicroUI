package microui.core.effect;

import microui.core.base.Component;
import microui.core.base.View;
import microui.core.style.Color;

public final class Hover extends View {
	private final Color color;
	private final Component component;
	private boolean isInside,isPressed,isEnabled;
	
    public Hover(Component component) {
    	setVisible(true);
    	color = new Color(0,100);
		this.component = component;
		component.onMouseInside(() -> isInside = true);
		component.onMouseOutside(() -> isInside = false);
		component.onPress(() -> isPressed = true);
		component.onRelease(() -> isPressed = false);
		isEnabled = true;
	}

	@Override
	public void update() {
		if(!isEnabled) { return; }
		
		if(isInside) {
			app.pushStyle();
			app.fill(color.get());
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
		this.color.set(color);
	}

	public final boolean isEnabled() {
		return isEnabled;
	}

	public final void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
}