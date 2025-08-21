package microui.core;

import microui.core.base.Component;
import microui.core.interfaces.Scrollable;
import microui.event.EventType;
import microui.event.Listener;
import microui.event.Scrolling;
import microui.util.Constants;
import microui.util.Value;
import processing.event.MouseEvent;

public abstract class RangeControl extends Component implements Scrollable {
	public final Value value;
	public final Scrolling scrolling;
	protected int orientation;
	protected boolean valueChangeStart,valueChangeEnd;
	private Listener onStartChangeValueListener,onChangeValueListener,onEndChangeValueListener;
	
	public RangeControl(float x, float y, float w, float h) {
		super(x, y, w, h);
		value = new Value(0,100,0) {
			@Override
			public void action() {
				onChangeBounds();
				
			}
		};
		
		callback.addListener(EventType.PRESSED, () -> { valueChangeEnd = true; });
		
		scrolling = new Scrolling(event);
		orientation = Constants.HORIZONTAL;
		visible();
		
	}
	
	public RangeControl() {
		this(app.width*.25f,app.height*.45f,app.width*.5f,app.height*.1f);
	}

	@Override
	protected void update() {
		event.listen(this);
		
		app.pushStyle();
		fill.use();
		app.rect(x, y, w, h);
		app.popStyle();
		
		if(scrolling.isScrolling()) {
			value.append(scrolling.get());
			onChangeValue();
			valueChangeEnd = true;
			if(!app.mousePressed && !valueChangeStart) {
				onStartChangeValue();
			}
		} else {
			if(!app.mousePressed) {
				valueChangeStart = false;
			}
		}
		
		if(!app.mousePressed && valueChangeEnd && !scrolling.isScrolling()) {
			onEndChangeValue();
			valueChangeEnd = false;
		}
	}
	
	@Override
	public void mouseWheel(MouseEvent e) {
		scrolling.init(e);
		if(event.inside()) {
			value.append(scrolling.get());
		}
		
		onChangeValue();
	}
	
	public final void setOrientation(final int orientation) {
		if(orientation != Constants.HORIZONTAL && orientation != Constants.VERTICAL) { return; }
		if(this.orientation == orientation) { return; }
		final float w = getWidth(), h = getHeight();
		this.orientation = orientation;
		setWidth(h);
		setHeight(w);
		onChangeBounds();
	}
	
	public void swapOrientation() {
		if(orientation == 0) { orientation = 1; } else { orientation = 0; }
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
	
	
}