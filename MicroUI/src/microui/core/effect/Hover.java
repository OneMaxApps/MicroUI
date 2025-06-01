package microui.core.effect;

import microui.core.base.Component;
import microui.core.base.View;
import microui.core.style.Color;

public final class Hover extends View {
	public final Color fill;
	private final Component component;
	
    public Hover(Component component) {
    	visible();
    	fill = new Color(0,100);
		this.component = component;
		
	}

	@Override
	public void update() {
		if(component.event.inside()) {
			app.pushStyle();
			fill.use();
			app.rect(component.getX(),component.getY(),component.getWidth(),component.getHeight());
			app.popStyle();
		
			if(component.event.pressed()) {
				app.fill(0,64);
				app.rect(component.getX(),component.getY(),component.getWidth(),component.getHeight());
			}
		
		}
	}
	
}