package microui.core.base;

import static java.util.Objects.requireNonNull;

import microui.MicroUI;
import microui.core.interfaces.Visible;
import microui.util.Metrics;
import processing.core.PApplet;

// Status: STABLE - Do not modify
// Last Reviewed: 10.09.2025
public abstract class View implements Visible {
	protected static final PApplet ctx = requireNonNull(MicroUI.getContext(),"context for MicroUI was not sended");
	private static final String DEFAULT_EMPTY_TEXT_ID = "";
	private String textId;
	private int priority,id;
	private boolean isVisible;

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
			render();
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
	
	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}

	public final String getTextId() {
		if(textId == null) {
			return DEFAULT_EMPTY_TEXT_ID;
		}
		return textId;
	}

	public final void setTextId(final String textId) {
		if(textId == null) {
			throw new IllegalArgumentException("text id cannot be null");
		}
		if(textId.trim().isEmpty()) {
			throw new IllegalArgumentException("text id cannot be empty");
		}
		
		this.textId = textId;
	}

	protected abstract void render();

}