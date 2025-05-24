package microUI.core.effect;

import microUI.core.base.Component;
import microUI.core.base.View;
import microUI.core.style.Color;

public final class Hover extends View {
	public final Color fill;
	private final Component component;
	
    public Hover(Component component) {
		super();
		this.component = component;
		fill = new Color(0,100);
		visible();
		
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