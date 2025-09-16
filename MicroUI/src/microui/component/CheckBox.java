package microui.component;

import static java.lang.Math.max;
import static processing.core.PConstants.PROJECT;

import microui.core.AbstractButton;
import microui.core.style.Color;
import microui.event.Listener;

public class CheckBox extends AbstractButton {
	public static final int DEFAULT_SIZE = 16;
	private final Color markColor;
	private boolean isChecked;

	public CheckBox(float x, float y) {
		super(x, y, DEFAULT_SIZE, DEFAULT_SIZE);
		setMinMaxSize(DEFAULT_SIZE);
		onClick(() -> toggle());
		markColor = new Color(200,200,255);
	}

	public CheckBox(boolean isChecked) {
		this(ctx.width/2-DEFAULT_SIZE/2, ctx.height/2-DEFAULT_SIZE/2);
		setChecked(isChecked);
	}

	public CheckBox() {
		this(false);
	}

	@Override
	protected void render() {
		super.render();

		if (isChecked) {
			markOnDraw();
		}
	}

	public final boolean isChecked() {
		return isChecked;
	}

	public final void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public final void toggle() {
		isChecked = !isChecked;
	}

	public final Color getMarkColor() {
		return new Color(markColor);
	}

	public final void setMarkColor(Color color) {
		if (color == null) {
			throw new NullPointerException("the mark color cannot be null");
		}

		markColor.set(color);
	}

	public final void onStateChangedListener(Listener listener) {
		if (listener == null) {
			throw new NullPointerException("on the state changed listener cannot be null");
		}
		onClick(listener);
	}

	private void markOnDraw() {
		ctx.pushStyle();
		ctx.noStroke();
		markColor.apply();
		ctx.rect(getX(), getY(), getWidth(), getHeight());
		ctx.stroke(0, 128);
		ctx.strokeWeight(max(1, getWidth() / 5));
		ctx.strokeCap(PROJECT);
		ctx.line(getX() + getWidth() * .3f, getY() + getHeight() * .6f, getX() + getWidth() / 2,
				getY() + getHeight() * .8f);
		ctx.line(getX() + getWidth() * .8f, getY() + getHeight() * .2f, getX() + getWidth() / 2,
				getY() + getHeight() * .8f);
		ctx.popStyle();
	}
	
}