package microui.core;

import microui.constants.Orientation;
import microui.event.Listener;
import microui.util.Value;
import processing.event.MouseEvent;

public abstract class LinearRangeControl extends RangeControl {
	private Orientation orientation;
	private boolean valueChangeStart, valueChangeEnd;
	private Listener onStartChangeValueListener, onChangeValueListener, onEndChangeValueListener;

	public LinearRangeControl(float x, float y, float width, float height) {
		super(x, y, width, height);
		setMinMaxSize(10,20,200,20);
		getMutableColor().set(164);
		
		value = new Value(0, 100, 0) {
			@Override
			public void action() {
				requestUpdate();
			}
		};
		
		onPress(() -> valueChangeEnd = true);

		
		orientation = Orientation.HORIZONTAL;

	}
	
	@Override
	protected void render() {

		ctx.pushStyle();
		getMutableColor().apply();
		ctx.rect(getPadX(), getPadY(), getPadWidth(), getPadHeight());
		ctx.popStyle();

		if (getScrollingMutable().isScrolling()) {
			value.append(getScrollingMutable().get());
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

		if (!ctx.mousePressed && valueChangeEnd && !getScrollingMutable().isScrolling()) {
			onEndChangeValue();
			valueChangeEnd = false;
		}
	}
	
	@Override
	public void mouseWheel(MouseEvent event) {
		getScrollingMutable().init(event);
		if (isMouseInside()) {
			value.append(getScrollingMutable().get());
		}

		onChangeValue();
	}

	public void mouseWheel(MouseEvent event, boolean additionalCondition) {
		if (isMouseInside() || additionalCondition) {
			getScrollingMutable().init(event);
			value.append(getScrollingMutable().get());
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
	
	protected final void autoScroll() {
		value.append(getScrollingMutable().get());
		onChangeValue();
	}
}