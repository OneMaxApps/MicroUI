package microui.component;

import static microui.constants.Orientation.HORIZONTAL;
import static microui.core.style.theme.ThemeManager.getTheme;
import static processing.core.PApplet.map;

import microui.core.LinearRangeControl;
import microui.core.base.SpatialView;
import microui.core.style.Color;
import microui.core.style.Stroke;

public class Slider extends LinearRangeControl {

	private final Rect indicator;

	public Slider(float x, float y, float w, float h) {
		super(x, y, w, h);
		indicator = new Rect(x,y,w,h);
		
		setValue(0, 100, 0);

		setOrientation(HORIZONTAL);

	}

	public Slider() {
		this(0,0,0,0);
		setWidth(getMaxWidth());
		setPosition(ctx.width/2-getWidth()/2,ctx.height/2-getHeight()/2);
	}

	@Override
	protected void render() {
		super.render();
		indicator.draw();

		if (isHolding()) {
			switch (getOrientation()) {
			case HORIZONTAL:
				setValue(map(ctx.mouseX, getX(), getX() + getWidth(), getMinValue(), getMaxValue()));
				break;
			case VERTICAL:
				setValue(map(ctx.mouseY, getY(), getY() + getHeight(), getMaxValue(), getMinValue()));
				break;
			}
			updateIndicatorBounds();

			onStartChangeValue();
			onChangeValue();

		}

	}

	@Override
	protected void onChangeBounds() {
		updateIndicatorBounds();
	}

	@Override
	public void swapOrientation() {
		super.swapOrientation();
		onChangeBounds();
	}

	public final float getIndicatorStrokeWeight() {
		return indicator.stroke.getWeight();
	}

	public final void setIndicatorStrokeWeight(int weight) {
		indicator.stroke.setWeight(weight);
	}

	public final Color getIndicatorStrokeColor() {
		return indicator.stroke.getColor();
	}

	public final void setIndicatorStrokeColor(Color color) {
		indicator.stroke.setColor(color);
	}

	public final Color getIndicatorColor() {
		return new Color(indicator.color);
	}

	public final void setIndicatorColor(Color color) {
		indicator.color.set(color);
	}

	private final class Rect extends SpatialView {
		public final Stroke stroke;
		public final Color color;

		private Rect(float x, float y, float w, float h) {
			super(x, y, w, h);
			setVisible(true);
			setNegativeDimensionsEnabled(true);

			stroke = new Stroke();
			stroke.setWeight(1);
			color = new Color(getTheme().getPrimaryColor());
		}

		@Override
		public void render() {
			ctx.pushStyle();
			stroke.apply();
			color.apply();
			ctx.rect(getX(), getY(), getWidth(), getHeight());
			ctx.popStyle();

		}

	}
	
	private void updateIndicatorBounds() {
		if (indicator == null) {
			return;
		}

		indicator.setBounds(getX(),getY(),getWidth(),getHeight());

		switch (getOrientation()) {

		case HORIZONTAL:
			indicator.setWidth(map(getValue(), getMinValue(), getMaxValue(), 0, getWidth()));

			break;

		case VERTICAL:
			indicator.setY(getY() + getHeight());
			indicator.setWidth(getWidth());
			indicator.setHeight(map(getValue(), getMinValue(), getMaxValue(), 0, -getHeight()));
			break;

		}
	}

}