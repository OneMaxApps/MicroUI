package microui.component;

import static microui.constants.Orientation.HORIZONTAL;
import static processing.core.PApplet.map;

import microui.core.RangeControl;
import microui.core.base.Bounds;
import microui.core.style.Color;
import microui.core.style.Stroke;

public class Slider extends RangeControl {

	private final Rect level;

	public Slider(float x, float y, float w, float h) {
		super(x, y, w, h);

		level = new Rect(x, y, w, h);
		level.color.set(234);

		setValue(0, 100, 0);

		setOrientation(HORIZONTAL);

	}

	public Slider() {
		this(0,0,0,0);
		setWidth(getMaxWidth());
		setPosition(ctx.width/2-getWidth()/2,ctx.height/2-getHeight()/2);
	}

	@Override
	protected void update() {
		super.update();
		level.draw();

		if (isHolding()) {
			switch (orientation) {
			case HORIZONTAL:
				setValue(map(ctx.mouseX, getX(), getX() + getWidth(), getMinValue(), getMaxValue()));
				break;
			case VERTICAL:
				setValue(map(ctx.mouseY, getY(), getY() + getHeight(), getMaxValue(), getMinValue()));
				break;
			}
			onChangeBounds();

			onStartChangeValue();
			onChangeValue();

		}

	}

	@Override
	protected void onChangeBounds() {
		if (level == null) {
			return;
		}

		level.setBounds(this);

		switch (orientation) {

		case HORIZONTAL:
			level.setWidth(map(getValue(), getMinValue(), getMaxValue(), 0, getWidth()));

			break;

		case VERTICAL:
			level.setY(getY() + getHeight());
			level.setWidth(getWidth());
			level.setHeight(map(getValue(), getMinValue(), getMaxValue(), 0, -getHeight()));
			break;

		}

	}

	@Override
	public void swapOrientation() {
		super.swapOrientation();
		onChangeBounds();
	}

	public final int getLevelStrokeWeight() {
		return level.stroke.getWeight();
	}

	public final void setLevelStrokeWeight(int weight) {
		level.stroke.setWeight(weight);
	}

	public final Color getLevelStrokeColor() {
		return level.stroke.getColor();
	}

	public final void setLevelStrokeColor(Color color) {
		level.stroke.setColor(color);
	}

	public final Color getLevelColor() {
		return new Color(level.color);
	}

	public final void setLevelColor(Color color) {
		level.color.set(color);
	}

	private final class Rect extends Bounds {
		public final Stroke stroke;
		public final Color color;

		private Rect(float x, float y, float w, float h) {
			super(x, y, w, h);
			setVisible(true);
			setNegativeDimensionsEnabled(true);

			stroke = new Stroke();
			color = new Color(44);
		}

		@Override
		public void update() {
			ctx.pushStyle();
			stroke.apply();
			color.apply();
			ctx.rect(getX(), getY(), getWidth(), getHeight());
			ctx.popStyle();

		}

	}

}