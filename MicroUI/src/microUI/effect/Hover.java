package microUI.effect;

import microUI.core.Component;
import microUI.core.View;
import microUI.util.Color;
import microUI.util.Metrics;


public final class Hover extends View {
	public final Color fill;
	private final Component component;
	
    public Hover(Component component) {
		super();
		this.component = component;
		fill = new Color(0,100);
		visible();
		Metrics.registerHover();
	}

	@Override
	public void update() {
		if(component.event.inside()) {
			app.pushStyle();
			fill.use();
			app.rect(component.getX(),component.getY(),component.getW(),component.getH());
			app.popStyle();
		
			if(component.event.pressed()) {
				app.fill(0,64);
				app.rect(component.getX(),component.getY(),component.getW(),component.getH());
			}
		
		}
	}
	
}