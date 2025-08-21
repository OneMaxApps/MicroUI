package microui.core.base;

import microui.MicroUI;
import microui.core.interfaces.Visible;
import microui.util.Metrics;

// Status: STABLE - Do not modify
// Last Reviewed: 29.06.2025
public abstract class View extends MicroUI implements Visible {
	protected boolean visible;
	private int priority;
	
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
	
	protected abstract void update();

	public final int getPriority() {
		return priority;
	}

	public final void setPriority(int priority) {
		this.priority = priority;
	}
	
}