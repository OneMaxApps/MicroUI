package microui.core;

import microui.constants.Orientation;
import microui.core.base.Component;
import microui.core.interfaces.Scrollable;
import microui.event.Listener;
import microui.event.Scrolling;
import microui.util.Value;
import processing.event.MouseEvent;

public abstract class RangeControl extends Component implements Scrollable {
	private final Value value;
	private final Scrolling scrolling;
	private Orientation orientation;
	private boolean valueChangeStart, valueChangeEnd;
	private Listener onStartChangeValueListener, onChangeValueListener, onEndChangeValueListener;

	public RangeControl(float x, float y, float width, float height) {
		super(x, y, width, height);
		setVisible(true);
		setConstrainDimensionsEnabled(true);
		setMinMaxSize(10,200);
		getMutableColor().set(24);

		value = new Value(0, 100, 0) {
			@Override
			public void action() {
				requestUpdate();
			}
		};

		onPress(() -> valueChangeEnd = true);

		scrolling = new Scrolling(getMutableEvent());
		orientation = Orientation.HORIZONTAL;

		setEventListener(this);

	}

	@Override
	protected void render() {

		ctx.pushStyle();
		getMutableColor().apply();
		ctx.rect(getPadX(), getPadY(), getPadWidth(), getPadHeight());
		ctx.popStyle();

		if (scrolling.isScrolling()) {
			value.append(scrolling.get());
			onChangeValue();
			valueChangeEnd = true;
			if (!ctx.mousePressed && !valueChangeStart) {
				onStartChangeValue();
			}
		} else {
			if (!ctx.mousePressed) {
				valueChangeStart = false;
			}
		}

		if (!ctx.mousePressed && valueChangeEnd && !scrolling.isScrolling()) {
			onEndChangeValue();
			valueChangeEnd = false;
		}
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		scrolling.init(event);
		if (isMouseInside()) {
			value.append(scrolling.get());
		}

		onChangeValue();
	}

	public void mouseWheel(MouseEvent event, boolean additionalCondition) {
		if (isMouseInside() || additionalCondition) {
			scrolling.init(event);
			value.append(scrolling.get());
		}
		onChangeValue();
	}

	public final Orientation getOrientation() {
		return orientation;
	}

	public final void setOrientation(final Orientation orientation) {
		if (this.orientation == orientation) {
			return;
		}
		final float w = getWidth(), h = getHeight();
		this.orientation = orientation;

		setWidth(h);
		setHeight(w);
		requestUpdate();
	}

	public void swapOrientation() {
		if (orientation == Orientation.HORIZONTAL) {
			orientation = Orientation.VERTICAL;
		} else {
			orientation = Orientation.HORIZONTAL;
		}
		
	}

	public final Listener getOnChangeValueListener() {
		return onChangeValueListener;
	}

	public final void setOnChangeValueListener(Listener onChangeValueListener) {
		this.onChangeValueListener = onChangeValueListener;
	}

	public final Listener getOnStartChangeValueListener() {
		return onStartChangeValueListener;
	}

	public final void setOnStartChangeValueListener(Listener onStartChangeValueListener) {
		this.onStartChangeValueListener = onStartChangeValueListener;
	}

	public final Listener getOnEndChangeValueListener() {
		return onEndChangeValueListener;
	}

	public final void setOnEndChangeValueListener(Listener onEndChangeValueListener) {
		this.onEndChangeValueListener = onEndChangeValueListener;
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

	protected void onChangeValue() {
		if (onChangeValueListener != null) {
			onChangeValueListener.action();
		}
	}

	protected void onStartChangeValue() {
		if (!valueChangeStart) {
			if (onStartChangeValueListener != null) {
				onStartChangeValueListener.action();
			}
			valueChangeStart = true;
		}
	}

	protected void onEndChangeValue() {
		if (onEndChangeValueListener != null) {
			onEndChangeValueListener.action();
		}
	}

	protected final boolean hasEqualMinMax() {
		return value.hasEqualMinMax();
	}

	protected final void setValueWithoutActions(float value) {
		this.value.setWithoutActions(value);
	}

	protected final void autoScroll() {
		value.append(scrolling.get());
		onChangeValue();
	}
}