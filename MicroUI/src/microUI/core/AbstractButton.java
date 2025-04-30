package microUI.core;

import microUI.effect.Hover;
import microUI.effect.Ripples;
import microUI.util.Rectangle;
import microUI.util.Stroke;
import processing.core.PApplet;

public abstract class AbstractButton extends Rectangle {
	public final Ripples ripples;
	public final Hover hover;
	
	public AbstractButton(PApplet app, float x, float y, float w, float h) {
		super(app, x, y, w, h);
		ripples = new Ripples(this);
		hover = new Hover(app,this);
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