package microUI.core;

import microUI.effect.Hover;
import microUI.effect.Ripples;
import microUI.util.Rectangle;

public abstract class AbstractButton extends Rectangle {
	public final Ripples ripples;
	public final Hover hover;
	
	public AbstractButton(float x, float y, float w, float h) {
		super(x, y, w, h);
		ripples = new Ripples(this);
		hover = new Hover(this);
	}

	@Override
	public void update() {
		
		app.pushStyle();
		super.update();
		hover.draw();
		ripples.draw();
		app.popStyle();
	
	}
	
}