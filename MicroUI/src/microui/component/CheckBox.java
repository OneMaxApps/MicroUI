package microui.component;

import static java.lang.Math.max;
import static processing.core.PConstants.PROJECT;

import microui.core.AbstractButton;
import microui.core.style.Color;
import microui.event.Listener;

public class CheckBox extends AbstractButton {
	private static final int DEFAULT_SIZE = 16;
	private final Color markColor;
	private boolean isChecked;

	public CheckBox(float x, float y) {
		super(x, y, DEFAULT_SIZE, DEFAULT_SIZE);
		setPaddingEnabled(true);
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
	protected void update() {
		super.update();

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
		ctx.rect(getContentX(), getContentY(), getContentWidth(), getContentHeight());
		ctx.stroke(0, 128);
		ctx.strokeWeight(max(1, getContentWidth() / 5));
		ctx.strokeCap(PROJECT);
		ctx.line(getContentX() + getContentWidth() * .3f, getContentY() + getContentHeight() * .6f, getContentX() + getContentWidth() / 2,
				getContentY() + getContentHeight() * .8f);
		ctx.line(getContentX() + getContentWidth() * .8f, getContentY() + getContentHeight() * .2f, getContentX() + getContentWidth() / 2,
				getContentY() + getContentHeight() * .8f);
		ctx.popStyle();
	}
	
}