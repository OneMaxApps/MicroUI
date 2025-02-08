package microUI.utils;

import processing.core.PApplet;

public abstract class View implements Visiable {
	protected PApplet app;
	protected boolean isVisible;
	
	public View(PApplet app) {
		super();
		this.app = app;
	}

	public final boolean isVisible() {
		return isVisible;
	}

	public final void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public void draw() {
		if(isVisible) {
			app.pushStyle();
			update();
			app.popStyle();
		}
	}
	
	public abstract void update();
}