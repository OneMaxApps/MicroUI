package microui.core;

import microui.core.base.Component;
import microui.core.interfaces.Scrollable;
import microui.core.style.Stroke;
import microui.event.Scrolling;
import microui.util.Value;

public abstract class RangeControl extends Component implements Scrollable {
	protected Value value;
	private Scrolling scrolling;
	private final Stroke stroke;
	
	public RangeControl(float x, float y, float width, float height) {
		super(x, y, width, height);
		scrolling = new Scrolling(getMutableEvent());
		stroke = new Stroke();
	}

	public final void setValue(float value) {
		this.value.set(value);
	}

	public final void setValue(float min, float max, float value) {
		this.value.set(min, max, value);
	}

	public final float getValue() {
		return value.get();
	}

	public final void setMinValue(float min) {
		this.value.setMin(min);
	}

	public final float getMinValue() {
		return value.getMin();
	}

	public final void setMaxValue(float max) {
		this.value.setMax(max);
	}

	public final float getMaxValue() {
		return value.getMax();
	}

	public final void setMinMaxValue(float min, float max) {
		this.value.setMinMax(min, max);
	}

	public final void appendValue(float value) {
		this.value.append(value);
	}

	public final void setScrollingVelocity(float velocity) {
		scrolling.setVelocity(velocity);
	}

	protected final boolean hasEqualMinMax() {
		return value.hasEqualMinMax();
	}

	protected final void setValueWithoutActions(float value) {
		this.value.setWithoutActions(value);
	}
	protected final Scrolling getScrollingMutable() {
		return scrolling;
	}

	protected final Stroke getMutableStroke() {
		return stroke;
	}
}