package microui.component;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Objects.requireNonNull;
import static processing.core.PConstants.PROJECT;

import microui.container.BorderContainer;
import microui.core.AbstractButton;
import microui.core.base.Bounds;
import microui.core.style.Color;
import microui.event.Listener;

public class CheckBox extends AbstractButton {
	private static final int DEFAULT_BOX_SIZE = 16;
	private static final int DEFAULT_TEXT_PADDING = 8;

	private final BorderContainer container;
	private float textPadding;
	private boolean isChecked;

	public CheckBox(float x, float y, float width, float height) {
		super(x, y, width, height);
		setMinSize(DEFAULT_BOX_SIZE);
		setTextPadding(DEFAULT_TEXT_PADDING);

		container = new BorderContainer(x, y, width, height);
		container.set(new Content(this));

	}

	public CheckBox(boolean isChecked) {
		this(ctx.width * .3f, ctx.height * .4f, ctx.width * .4f, ctx.height * .2f);
		setChecked(isChecked);
	}

	public CheckBox(String text) {
		this(ctx.width * .3f, ctx.height * .4f, ctx.width * .4f, ctx.height * .2f);
		setText(text);
	}

	public CheckBox() {
		this(false);
	}

	@Override
	protected void update() {
		container.draw();
	}

	@Override
	protected void onChangeBounds() {
		super.onChangeBounds();
		if (container == null) {
			return;
		}

		container.setBoundsState(this);
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
		return new Color(getBox().markColor);
	}

	public final void setMarkColor(Color color) {
		if (color == null) {
			throw new NullPointerException("the mark color must be not null");
		}

		getBox().markColor.set(color);
	}

	public final void onStateChangedListener(Listener listener) {
		if (listener == null) {
			throw new NullPointerException("on the state changed listener must be not null");
		}
		onClick(listener);
	}

	public final float getTextPadding() {
		return textPadding;
	}

	public final void setTextPadding(float textPadding) {
		this.textPadding = max(0, textPadding);
	}

	private Box getBox() {
		return ((Content) (container.getElement())).box;
	}

	private final class Content extends Bounds {
		private final Box box;

		public Content(Bounds bounds) {
			super(requireNonNull(bounds, "bounds cannot be null"));
			setVisible(true);
			setMaxHeight(DEFAULT_BOX_SIZE);

			box = new Box();

			setTextColor(new Color(32));
			getMutableText().setAutoResizeEnabled(false);
			getMutableText().setTextSize(box.getHeight());
			getMutableText().setInCenter(false);

		}

		@Override
		protected void update() {
			box.draw();
			getMutableText().draw();
		}

		@Override
		protected void onChangeBounds() {
			if (box == null || getMutableText() == null) {
				return;
			}
			box.setPosition(getX(), getY());
			box.setSize(min(DEFAULT_BOX_SIZE, getWidth()), min(DEFAULT_BOX_SIZE, getHeight()));
			getMutableText().setSize(getWidth() - (box.getWidth() + textPadding), box.getHeight());
			getMutableText().setPosition(box.getX() + box.getWidth() + textPadding,
					box.getY() + box.getHeight() / 2 - getMutableText().getHeight() / 2);
			getMutableText().setTextSize(box.getHeight());
		}

	}

	private final class Box extends Bounds {
		private final Color markColor;

		public Box() {
			super(0, 0, DEFAULT_BOX_SIZE, DEFAULT_BOX_SIZE);
			setVisible(true);

			setCallbackListener(this);

			getMutableRipples().setBounds(this);

			getMutableHover().setAlternativeBounds(this);

			markColor = new Color(0, 200, 255, 100);

			onClick(() -> toggle());
		}

		@Override
		protected void update() {
			setEventListener(this);

			ctx.pushStyle();
			getMutableStroke().apply();
			getMutableColor().apply();
			ctx.rect(getX(), getY(), getWidth(), getHeight());
			getMutableHover().draw();
			getMutableRipples().draw();
			ctx.popStyle();

			if (isChecked) {
				markOnDraw();
			}
		}

		private void markOnDraw() {
			ctx.pushStyle();
			ctx.noStroke();
			markColor.apply();
			ctx.rect(getX(), getY(), getWidth(), getHeight());
			ctx.stroke(0, 128);
			ctx.strokeWeight(max(1, DEFAULT_BOX_SIZE / 5));
			ctx.strokeCap(PROJECT);
			ctx.line(getX() + getWidth() * .3f, getY() + getHeight() * .6f, getX() + getWidth() / 2,
					getY() + getHeight() * .8f);
			ctx.line(getX() + getWidth() * .8f, getY() + getHeight() * .2f, getX() + getWidth() / 2,
					getY() + getHeight() * .8f);
			ctx.popStyle();
		}
	}
}