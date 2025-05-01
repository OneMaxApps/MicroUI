package microUI.core;

import microUI.MicroUI;

public abstract class View extends MicroUI implements Visible {
	protected boolean visible;

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
		if(isVisible()) {
			app.pushStyle();
			update();
			app.popStyle();
		}
	}
	
	public abstract void update();
	
}