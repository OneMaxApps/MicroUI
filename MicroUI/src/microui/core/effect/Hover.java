package microui.core.effect;

import microui.core.base.Bounds;
import microui.core.base.Component;
import microui.core.base.View;
import microui.core.style.Color;

public final class Hover extends View {
	private final Color color;
	private Component component;
	private Bounds alternativeBounds;
	private boolean isInside,isPressed,isEnabled;
	
    public Hover(Component component) {
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
	public void update() {
		if(!isEnabled) { return; }
		
		if(isInside) {
			app.pushStyle();
			app.fill(color.get());
			rectOnDraw();
			if(isPressed) {
			app.fill(0,64);
			rectOnDraw();
			}
			app.popStyle();
		}
	}
	
	public final Component getComponent() {
		return component;
	}

	public final void setComponent(Component component) {
		if(this.component == component) { return; }
		
		this.component = component;
		
		component.onMouseInside(() -> isInside = true);
		component.onMouseOutside(() -> isInside = false);
		component.onPress(() -> isPressed = true);
		component.onRelease(() -> isPressed = false);
	}
	
	public final void setAlternativeBounds(Bounds alternativeBounds) {
		if(alternativeBounds != null) {
			this.alternativeBounds = alternativeBounds;
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
	
	private void rectOnDraw() {
		if(alternativeBounds != null) {
			app.rect(alternativeBounds.getX(),alternativeBounds.getY(),alternativeBounds.getWidth(),alternativeBounds.getHeight());
			} else {
				app.rect(component.getX(),component.getY(),component.getWidth(),component.getHeight());
			}
	}
}