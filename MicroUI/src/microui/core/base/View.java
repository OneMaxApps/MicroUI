package microui.core.base;

import static java.util.Objects.requireNonNull;

import microui.MicroUI;
import microui.core.interfaces.Visible;
import microui.util.Metrics;
import processing.core.PApplet;

// Status: STABLE - Do not modify
// Last Reviewed: 03.09.2025
public abstract class View implements Visible {
	protected static final PApplet ctx = requireNonNull(MicroUI.getContext(),"context for MicroUI was not sended");
	private boolean isVisible;
	private int priority;

	public View() {
		Metrics.register(this);
	}

	@Override
	public final boolean isVisible() {
		return isVisible;
	}

	@Override
	public final void setVisible(final boolean isVisible) {
		this.isVisible = isVisible;
	}

	public void draw() {
		if (isVisible()) {
			ctx.pushStyle();
			update();
			ctx.popStyle();
		}
	}

	public final int getPriority() {
		return priority;
	}

	public final void setPriority(final int priority) {
		if (priority < 0) {
			throw new IllegalArgumentException("priority cannot be less than zero");
		}
		this.priority = priority;
	}

	protected abstract void update();

}