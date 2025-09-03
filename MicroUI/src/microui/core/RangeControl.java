package microui.core;

import microui.constants.Orientation;
import microui.core.base.Component;
import microui.core.interfaces.Scrollable;
import microui.event.Listener;
import microui.event.Scrolling;
import microui.util.Value;
import processing.event.MouseEvent;

public abstract class RangeControl extends Component implements Scrollable {
	protected final Value value;
	protected final Scrolling scrolling;
	protected Orientation orientation;
	protected boolean valueChangeStart,valueChangeEnd;
	private Listener onStartChangeValueListener,onChangeValueListener,onEndChangeValueListener;
	
	public RangeControl(float x, float y, float w, float h) {
		super(x, y, w, h);
		setVisible(true);
		
		getMutableColor().set(24);
		
		value = new Value(0,100,0) {
			@Override
			public void action() {
				onChangeBounds();
				
			}
		};
		
		onPress(() -> valueChangeEnd = true);
		
		scrolling = new Scrolling(getMutableEvent());
		orientation = Orientation.HORIZONTAL;
		
		
	}
	
	public RangeControl() {
		this(cxt.width*.25f,cxt.height*.45f,cxt.width*.5f,cxt.height*.1f);
	}

	@Override
	protected void update() {
		setEventListener(this);
		
		cxt.pushStyle();
		getMutableColor().apply();
		cxt.rect(getX(), getY(), getWidth(), getHeight());
		cxt.popStyle();
		
		if(scrolling.isScrolling()) {
			value.append(scrolling.get());
			onChangeValue();
			valueChangeEnd = true;
			if(!cxt.mousePressed && !valueChangeStart) {
				onStartChangeValue();
			}
		} else {
			if(!cxt.mousePressed) {
				valueChangeStart = false;
			}
		}
		
		if(!cxt.mousePressed && valueChangeEnd && !scrolling.isScrolling()) {
			onEndChangeValue();
			valueChangeEnd = false;
		}
	}
	
	@Override
	public void mouseWheel(MouseEvent e) {
		scrolling.init(e);
		if(isMouseInside()) {
			value.append(scrolling.get());
		}
		
		onChangeValue();
	}
	
	public final void setOrientation(final Orientation orientation) {
		if(this.orientation == orientation) { return; }
		final float w = getWidth(), h = getHeight();
		this.orientation = orientation;
		setWidth(h);
		setHeight(w);
		onChangeBounds();
	}
	
	public void swapOrientation() {
		if(orientation == Orientation.HORIZONTAL) { orientation = Orientation.VERTICAL; } else { orientation = Orientation.HORIZONTAL; }
	}
	
	public final void autoScroll() {
		value.append(scrolling.get());
		onChangeValue();
	}
	
	protected void onChangeValue() {
		if(onChangeValueListener != null) {
			onChangeValueListener.action();
		}
	}
	
	protected void onStartChangeValue() {
		if(!valueChangeStart) {
			if(onStartChangeValueListener != null) {
				onStartChangeValueListener.action();
			}
			valueChangeStart = true;
		}
	}
	
	protected void onEndChangeValue() {
		if(onEndChangeValueListener != null) {
			onEndChangeValueListener.action();
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

	public final Value getValue() {
		return value;
	}

	public final Scrolling getScrolling() {
		return scrolling;
	}
	
}