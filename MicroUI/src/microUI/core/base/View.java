package microUI.core.base;

import microUI.MicroUI;
import microUI.core.interfaces.Visible;
import microUI.util.Metrics;

public abstract class View extends MicroUI implements Visible {
	protected boolean visible;
	
	public View() {
		Metrics.register(this);
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
		if(isVisible()) {
			app.pushStyle();
			update();
			app.popStyle();
		}
	}
	
	public abstract void update();
	
}