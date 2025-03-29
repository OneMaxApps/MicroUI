package microUI.util;

import processing.core.PApplet;

public abstract class View implements Visible {
	protected PApplet app;
	protected boolean visible;
	
	public View(PApplet app) {
		super();
		this.app = app;
	}

	@Override
	public final boolean isVisible() { return visible; }
	
	@Override
	public final boolean isInvisible() { return !visible; }

	@Override
	public final void setVisible(boolean isVisible) { this.visible = isVisible; }
	
	@Override
	public final void visible() { visible = true; }
	
	@Override
	public final void invisible() { visible = false; }
	
	
	public void draw() {
		if(visible) {
			app.pushStyle();
			update();
			app.popStyle();
		}
	}
	
	public abstract void update();
}