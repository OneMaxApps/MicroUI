package microUI.utils;

import processing.core.PApplet;

public abstract class View implements Visible {
	protected PApplet app;
	protected boolean isVisible;
	
	public View(PApplet app) {
		super();
		this.app = app;
	}

	@Override
	public final boolean isVisible() { return isVisible; }
	
	@Override
	public final boolean isInvisible() { return !isVisible; }

	@Override
	public final void setVisible(boolean isVisible) { this.isVisible = isVisible; }
	
	@Override
	public final void visible() { isVisible = true; }
	
	@Override
	public final void invisible() { isVisible = false; }
	
	
	public void draw() {
		if(isVisible) {
			app.pushStyle();
			update();
			app.popStyle();
		}
	}
	
	public abstract void update();
}