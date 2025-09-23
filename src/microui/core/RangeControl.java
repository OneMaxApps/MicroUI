package microui.core;

import microui.core.base.Component;
import microui.core.interfaces.Scrollable;
import microui.core.style.Color;
import microui.core.style.Stroke;
import microui.event.Scrolling;
import microui.util.Value;

public abstract class RangeControl extends Component implements Scrollable {
	private final Value value;
	private final Scrolling scrolling;
	private final Stroke stroke;
	
	public RangeControl(float x, float y, float width, float height) {
		super(x, y, width, height);
		
		value = new Value(0,100,0);
		scrolling = new Scrolling(this);
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
	
	public final float getStrokeWeight() {
		return stroke.getWeight();
	}

	public final void setStrokeWeight(int weight) {
		stroke.setWeight(weight);
	}

	public final Color getStrokeColor() {
		return stroke.getColor();
	}

	public final void setStrokeColor(Color color) {
		stroke.setColor(color);
	}

	protected final boolean hasEqualMinMax() {
		return value.hasEqualMinMax();
	}

	protected final void setValueWithoutActions(float value) {
		this.value.setSilently(value);
	}
	
	protected final Value getMutableValue() {
		return value;
		
	}
	
	protected final Scrolling getMutableScrolling() {
		return scrolling;
	}
	
	protected final Stroke getMutableStroke() {
		return stroke;
	}
}