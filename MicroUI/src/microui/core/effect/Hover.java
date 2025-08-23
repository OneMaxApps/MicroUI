package microui.core.effect;

import microui.core.base.Component;
import microui.core.base.View;
import microui.core.style.Color;

public final class Hover extends View {
	private final Color color;
	private final Component component;
	
    public Hover(Component component) {
    	visible();
    	color = new Color(0,100);
		this.component = component;
		
	}

	@Override
	public void update() {
		if(component.getEvent().inside()) {
			app.pushStyle();
			color.use();
			app.rect(component.getX(),component.getY(),component.getWidth(),component.getHeight());
			app.popStyle();
		
			if(component.getEvent().pressed()) {
				app.fill(0,64);
				app.rect(component.getX(),component.getY(),component.getWidth(),component.getHeight());
			}
		
		}
	}

	public final Color getColor() {
		return color;
	}
	
}