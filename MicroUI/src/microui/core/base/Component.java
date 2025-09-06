package microui.core.base;

import static java.lang.Math.max;
import static java.util.Objects.requireNonNull;
import static microui.event.EventType.CLICK;
import static microui.event.EventType.DOUBLE_CLICK;
import static microui.event.EventType.DRAGGED;
import static microui.event.EventType.DRAGGING;
import static microui.event.EventType.HOLD_END;
import static microui.event.EventType.HOLD_START;
import static microui.event.EventType.LONG_PRESSED;
import static microui.event.EventType.MOUSE_INSIDE;
import static microui.event.EventType.MOUSE_INSIDE_LONG;
import static microui.event.EventType.MOUSE_OUTSIDE;
import static microui.event.EventType.PRESS;
import static microui.event.EventType.RELEASE;
import static microui.event.EventType.SHAKE;

import microui.core.style.Color;
import microui.event.Callback;
import microui.event.Event;
import microui.event.Listener;

//Status: STABLE - Do not modify
//Last Reviewed: 05.09.2025
public abstract class Component extends Bounds {
	private Color color;
	private Event event;
	private Callback callback;
	// private Tooltip tooltip;
	private Padding padding;

	public Component(float x, float y, float width, float height) {
		super(x, y, width, height);
		setPaddingEnabled(true);
	}

	public Component() {
		this(0, 0, 0, 0);
	}

	@Override
	public void draw() {
		super.draw();

		if (event != null) {
			event.listen();
		}

		if (callback != null) {
			callback.listen();
		}
		/*
		 * if (tooltip != null) { tooltip.init(); }
		 */
	}

	public void setStyle(Component component) {
		ensureColor();
		color.set(requireNonNull(component, "component cannot be null").getColor());
	}

	public final Color getColor() {
		ensureColor();
		return new Color(color);
	}

	public final void setColor(Color color) {
		ensureColor();
		this.color.set(requireNonNull(color, "color cannot be null"));
	}

	public final boolean isClicked() {
		ensureEvent();
		return event.clicked();
	}

	public final boolean isClicked(int count) {
		ensureEvent();
		return event.clicked(count);
	}

	public final boolean isDragged() {
		ensureEvent();
		return event.dragged();
	}

	public final boolean isDragging() {
		ensureEvent();
		return event.dragging();
	}

	public final boolean isPressed() {
		ensureEvent();
		return event.pressed();
	}

	public final boolean isMouseInside() {
		ensureEvent();
		return event.inside();
	}

	public final boolean isMouseOutside() {
		ensureEvent();
		return event.outside();
	}

	public final boolean isHolding() {
		ensureEvent();
		return event.holding();
	}

	public final void onClick(Listener listener) {
		ensureCallback();
		callback.addListener(CLICK, requireNonNull(listener, "listener cannot be null"));
	}

	public final void onDoubleClick(Listener listener) {
		ensureCallback();
		callback.addListener(DOUBLE_CLICK, requireNonNull(listener, "listener cannot be null"));
	}

	public final void onDragged(Listener listener) {
		ensureCallback();
		callback.addListener(DRAGGED, requireNonNull(listener, "listener cannot be null"));
	}

	public final void onDragging(Listener listener) {
		ensureCallback();
		callback.addListener(DRAGGING, requireNonNull(listener, "listener cannot be null"));
	}

	public final void onHold(Listener listener) {
		ensureCallback();
		callback.addListener(HOLD_START, requireNonNull(listener, "listener cannot be null"));
	}

	public final void onMouseInside(Listener listener) {
		ensureCallback();
		callback.addListener(MOUSE_INSIDE, requireNonNull(listener, "listener cannot be null"));
	}

	public final void onMouseInsideLong(Listener listener) {
		ensureCallback();
		callback.addListener(MOUSE_INSIDE_LONG, requireNonNull(listener, "listener cannot be null"));
	}

	public final void onMouseOutside(Listener listener) {
		ensureCallback();
		callback.addListener(MOUSE_OUTSIDE, requireNonNull(listener, "listener cannot be null"));
	}

	public final void onLongPress(Listener listener) {
		ensureCallback();
		callback.addListener(LONG_PRESSED, requireNonNull(listener, "listener cannot be null"));
	}

	public final void onPress(Listener listener) {
		ensureCallback();
		callback.addListener(PRESS, requireNonNull(listener, "listener cannot be null"));
	}

	public final void onRelease(Listener listener) {
		ensureCallback();
		callback.addListener(RELEASE, requireNonNull(listener, "listener cannot be null"));
	}

	public final void onHoldEnd(Listener listener) {
		ensureCallback();
		callback.addListener(HOLD_END, requireNonNull(listener, "listener cannot be null"));
	}

	public final void onShake(Listener listener) {
		ensureCallback();
		callback.addListener(SHAKE, requireNonNull(listener, "listener cannot be null"));
	}

	public final void setPadding(float left, float right, float up, float down) {
		ensurePadding();
		padding.setLeft(left);
		padding.setRight(right);
		padding.setTop(up);
		padding.setBottom(down);
	}
	
	public final void setPadding(float paddingHorizontal, float paddingVertical) {
		ensurePadding();
		padding.setLeft(paddingHorizontal);
		padding.setRight(paddingHorizontal);
		padding.setTop(paddingVertical);
		padding.setBottom(paddingVertical);
	}
	
	public final void setPadding(float padding) {
		ensurePadding();
		this.padding.setLeft(padding);
		this.padding.setRight(padding);
		this.padding.setTop(padding);
		this.padding.setBottom(padding);
	}
	
	public final void setPadding(Component padding) {
		ensurePadding();
		this.padding.setLeft(requireNonNull(padding, "padding object cannot be null").getPaddingLeft());
		this.padding.setRight(padding.getPaddingRight());
		this.padding.setTop(padding.getPaddingTop());
		this.padding.setBottom(padding.getPaddingBottom());
	}

	public final float getPaddingLeft() {
		ensurePadding();
		return padding.getLeft();
	}

	public final float getPaddingRight() {
		ensurePadding();
		return padding.getRight();
	}

	public final float getPaddingTop() {
		ensurePadding();
		return padding.getTop();
	}

	public final float getPaddingBottom() {
		ensurePadding();
		return padding.getBottom();
	}

	public final float getContentX() {
		ensurePadding();
		return isPaddingEnabled() ? getX()+getPaddingLeft() : getX();
	}
	
	public final float getContentY() {
		ensurePadding();
		return isPaddingEnabled() ?  getY()+getPaddingTop() : getY();
	}
	
	public final float getContentWidth() {
		return isPaddingEnabled() ?  max(0,getWidth()-(getPaddingRight()+getPaddingLeft())) : getWidth();
	}
	
	public final float getContentHeight() {
		return isPaddingEnabled() ?  max(0,getHeight()-(getPaddingBottom()+getPaddingTop())) : getHeight();
	}
	
	public final boolean isPaddingEnabled() {
		ensurePadding();
		return padding.isEnabled();
	}
	
	public final void setPaddingEnabled(boolean isEnabled) {
		ensurePadding();
		padding.setEnabled(isEnabled);
	}
	
	public final void resetPadding() {
		setPadding(0);
	}
	
	/*
	 * public final String getTooltipText() { return tooltip == null ? "" :
	 * tooltip.getText().getAsString(); }
	 */
	/*
	 * public final void setTooltipText(String text) { ensureTooltip();
	 * 
	 * tooltip.getText().set(requireNonNull(text, "text cannot be null")); }
	 */
	/*
	 * public final boolean isTooltipEnabled() { ensureTooltip(); return
	 * tooltip.isEnabled(); }
	 */
	/*
	 * public final void setTooltipEnabled(boolean enabled) { ensureTooltip();
	 * tooltip.setEnabled(enabled); }
	 */
	/*
	 * public final Color getTooltipColor() { ensureTooltip(); return
	 * tooltip.getColor(); }
	 */
	/*
	 * public final void setTooltipColor(Color color) { ensureTooltip();
	 * tooltip.setColor(requireNonNull(color, "color cannot be null")); }
	 */
	/*
	 * public final Color getTooltipTextColor() { ensureTooltip(); return
	 * tooltip.getText().getColor(); }
	 */
	/*
	 * public final void setTooltipTextColor(Color color) { ensureTooltip();
	 * tooltip.getText().setColor(requireNonNull(color, "color cannot be null")); }
	 */
	/*
	 * protected final void setTooltipAdditionalCondition(boolean condition) {
	 * ensureTooltip(); tooltip.setAdditionalCondition(condition); }
	 */

	protected final void setEventListener(Bounds bounds) {
		ensureEvent();
		event.setListener(requireNonNull(bounds, "bounds cannot be null"));
	}

	protected final Event getMutableEvent() {
		ensureEvent();
		return event;
	}

	protected final void setCallbackListener(Bounds bounds) {
		ensureCallback();
		callback.setListener(requireNonNull(bounds, "bounds cannot be null"));
	}

	protected final Color getMutableColor() {
		ensureColor();
		return color;
	}
	
	/*
	 * private void ensureTooltip() { ensureCallback(); if (tooltip == null) {
	 * tooltip = new Tooltip(callback); } }
	 */

	private void ensureCallback() {
		if (callback == null) {
			callback = new Callback(this);
		}
	}

	private void ensureEvent() {
		if (event == null) {
			event = new Event();
		}
	}

	private void ensureColor() {
		if (color == null) {
			color = new Color(200);
		}
	}
	
	private void ensurePadding() {
		if(padding == null) {
			padding = new Padding();
		}
	}
	
	private final class Padding {
		float left, right, top, bottom;
		boolean isEnabled;
		
		boolean isEnabled() {
			return isEnabled;
		}

		void setEnabled(boolean isEnabled) {
			this.isEnabled = isEnabled;
		}

		float getLeft() {
			return left;
		}

		void setLeft(float left) {
			if(!isValidValue(this.left,left)) { return; }
			this.left = left;
			checkCorrectState();
			onChangePositions();
			onChangeDimensions();
			onChangeBounds();
		}

		float getRight() {
			return right;
		}

		void setRight(float right) {
			if(!isValidValue(this.right,right)) { return; }
			this.right = right;
			checkCorrectState();
			onChangePositions();
			onChangeDimensions();
			onChangeBounds();
		}

		float getTop() {
			return top;
		}

		void setTop(float top) {
			if(!isValidValue(this.top,top)) { return; }
			this.top = top;
			checkCorrectState();
			onChangePositions();
			onChangeDimensions();
			onChangeBounds();
		}

		float getBottom() {
			return bottom;
		}

		void setBottom(float bottom) {
			if(!isValidValue(this.bottom,bottom)) { return; }
			this.bottom = bottom;
			checkCorrectState();
			onChangePositions();
			onChangeDimensions();
			onChangeBounds();
		}
		
		private boolean isValidValue(float currentValue, float newValue) {
			if(newValue < 0) {
				throw new IllegalArgumentException("padding cannot be less than zero");
			}
			
			if(newValue == currentValue) {
				return false;
			}
			
			return true;
		}
		
		private void checkCorrectState() {
			if(getWidth() <= 0 || getHeight() <= 0) { return; }
			
			if(left+right > getWidth() || top+bottom > getHeight()) {
				throw new IllegalArgumentException("padding cannot be greater than size of component");
			}
		}

	}
}