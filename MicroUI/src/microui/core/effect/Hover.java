package microui.core.effect;

import static java.util.Objects.requireNonNull;

import microui.core.base.Component;
import microui.core.base.View;
import microui.core.style.Color;

public final class Hover extends View {
	private final Color color;
	private Component component;
	private boolean isInside,isPressed,isEnabled;
	
    public Hover(Component component) {
    	requireNonNull(component, "component cannot be null");
    	setVisible(true);
    	color = new Color(0,100,255,24);
		this.component = component;
		component.onMouseInside(() -> isInside = true);
		component.onMouseOutside(() -> isInside = false);
		component.onPress(() -> isPressed = true);
		component.onRelease(() -> isPressed = false);
		isEnabled = true;
	}

	@Override
	public void render() {
		if(!isEnabled) { return; }
		
		if(isInside) {
			ctx.pushStyle();
			ctx.fill(color.get());
			rectOnDraw();
			if(isPressed) {
			ctx.fill(0,64);
			rectOnDraw();
			}
			ctx.popStyle();
		}
	}
	
	public final Component getComponent() {
		return component;
	}

	public final void setComponent(Component component) {
		requireNonNull(component, "component cannot be null");
		if(this.component == component) { return; }
		
		this.component = component;
		
		component.onMouseInside(() -> isInside = true);
		component.onMouseOutside(() -> isInside = false);
		component.onPress(() -> isPressed = true);
		component.onRelease(() -> isPressed = false);
	}

	public final Color getColor() {
		System.out.println("created a new Color object");
		return new Color(color);
	}
	
	public final void setColor(Color color) {
		requireNonNull(color, "color cannot be null");
		this.color.set(color);
	}

	public final boolean isEnabled() {
		return isEnabled;
	}

	public final void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	private void rectOnDraw() {
		ctx.rect(component.getContentX(),component.getContentY(),component.getContentWidth(),component.getContentHeight());
	}
}