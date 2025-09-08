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

import microui.MicroUI;
import microui.core.style.Color;
import microui.event.Callback;
import microui.event.Event;
import microui.event.Listener;

//Status: STABLE - Do not modify
//Last Reviewed: 07.09.2025
public abstract class Component extends Bounds {
	private Color color;
	private Event event;
	private Callback callback;
	// private Tooltip tooltip;
	private Padding padding;
	private Margin margin;

	public Component(float x, float y, float width, float height) {
		super(x, y, width, height);
		setPaddingEnabled(true);
		setMarginEnabled(true);
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
		
		debugOnDraw();
	}

	public void setStyle(Component component) {
		ensureColor();
		color.set(requireNonNull(component, "component cannot be null").getColor());
		copyPaddingFrom(component);
		copyMarginFrom(component);
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

	public final void setPadding(float left, float right, float top, float bottom) {
		ensurePadding();
		padding.setLeft(left);
		padding.setRight(right);
		padding.setTop(top);
		padding.setBottom(bottom);
	}

	public final void setPadding(float paddingHorizontal, float paddingVertical) {
		setPadding(paddingHorizontal, paddingHorizontal, paddingVertical, paddingVertical);
	}

	public final void setPadding(float padding) {
		setPadding(padding, padding);
	}

	public final void copyPaddingFrom(Component otherComponent) {
		setPadding(requireNonNull(otherComponent, "other component cannot be null").getPaddingLeft(),
				otherComponent.getPaddingRight(), otherComponent.getPaddingTop(), otherComponent.getPaddingBottom());
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
		return isPaddingEnabled() ? getX() + getPaddingLeft() : getX();
	}

	public final float getContentY() {
		ensurePadding();
		return isPaddingEnabled() ? getY() + getPaddingTop() : getY();
	}

	public final float getContentWidth() {
		return isPaddingEnabled() ? max(0, getWidth() - (getPaddingRight() + getPaddingLeft())) : getWidth();
	}

	public final float getContentHeight() {
		return isPaddingEnabled() ? max(0, getHeight() - (getPaddingBottom() + getPaddingTop())) : getHeight();
	}

	public final boolean isPaddingEnabled() {
		if (padding == null) {
			return false;
		}
		return padding.isEnabled();
	}

	public final void setPaddingEnabled(boolean isEnabled) {
		ensurePadding();
		padding.setEnabled(isEnabled);
	}

	public final void resetPadding() {
		setPadding(0);
	}

	public final boolean hasPadding() {
		if (padding == null) {
			return false;
		}
		return padding.isEnabled() && (padding.getLeft() > 0 || padding.getRight() > 0 || padding.getTop() > 0 || padding.getBottom() > 0);
	}

	public final float getMarginLeft() {
		ensureMargin();
		return margin.getLeft();
	}

	public final float getMarginRight() {
		ensureMargin();
		return margin.getRight();
	}

	public final float getMarginTop() {
		ensureMargin();
		return margin.getTop();
	}

	public final float getMarginBottom() {
		ensureMargin();
		return margin.getBottom();
	}

	public final void setMarginLeft(float left) {
		ensureMargin();
		margin.setLeft(left);
	}

	public final void setMarginRight(float right) {
		ensureMargin();
		margin.setRight(right);
	}

	public final void setMarginTop(float top) {
		ensureMargin();
		margin.setTop(top);
	}

	public final void setMarginBottom(float bottom) {
		ensureMargin();
		margin.setBottom(bottom);
	}

	public final void setMargin(float left, float right, float top, float bottom) {
		setMarginLeft(left);
		setMarginRight(right);
		setMarginTop(top);
		setMarginBottom(bottom);
	}

	public final void setMargin(float marginHorizontal, float marginVertical) {
		setMargin(marginHorizontal, marginHorizontal, marginVertical, marginVertical);
	}

	public final void setMargin(float margin) {
		setMargin(margin, margin);
	}

	public final void copyMarginFrom(Component otherComponent) {
		setMargin(requireNonNull(otherComponent, "other component cannot be null").getMarginLeft(),
				otherComponent.getMarginRight(), otherComponent.getMarginTop(), otherComponent.getMarginBottom());
	}

	public final void resetMargin() {
		setMargin(0);
	}

	public final boolean isMarginEnabled() {
		if (margin == null) {
			return false;
		}
		return margin.isEnabled();
	}

	public final void setMarginEnabled(boolean isEnabled) {
		ensureMargin();
		margin.setEnabled(isEnabled);
	}

	public final boolean hasMargin() {
		if (margin == null) {
			return false;
		}
		return margin.isEnabled() && (margin.getLeft() > 0 || margin.getRight() > 0 || margin.getTop() > 0 || margin.getBottom() > 0);
	}

	public final float getAbsoluteX() {
		return isMarginEnabled() ? getX() - getMarginLeft() : getX();
	}

	public final float getAbsoluteY() {
		return isMarginEnabled() ? getY() - getMarginTop() : getY();
	}

	public final float getAbsoluteWidth() {
		return isMarginEnabled() ? getWidth() + getMarginLeft() + getMarginRight() : getWidth();
	}

	public final float getAbsoluteHeight() {
		return isMarginEnabled() ? getHeight() + getMarginTop() + getMarginBottom() : getHeight();
	}
	
	//Demo settings///////////////////////////
	public final void setAbsoluteX(float x) {
		setX(x+getMarginLeft());
	}
	
	public final void setAbsoluteY(float y) {
		setY(y+getMarginTop());
	}
	
	public final void setAbsoluteWidth(float width) {
		setWidth(width-(getMarginLeft()+getMarginRight()));
	}
	
	public final void setAbsoluteHeight(float height) {
		setHeight(height-(getMarginTop()+getMarginBottom()));
	}
	
	public final void setAbsolutePosition(float x, float y) {
		setAbsoluteX(x);
		setAbsoluteY(y);
	}
	
	public final void setAbsoluteSize(float width, float height) {
		setAbsoluteWidth(width);
		setAbsoluteHeight(height);
	}
	///////////////////////////////////////////
	
	
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
		if (padding == null) {
			padding = new Padding();
		}
	}

	private void ensureMargin() {
		if (margin == null) {
			margin = new Margin();
		}
	}
	
	private void debugOnDraw() {
		if (MicroUI.isDebugModeEnabled()) {
			ctx.push();
			ctx.noFill();
			ctx.strokeWeight(5);
			
			// for showing margin area (Red rectangle)
			ctx.stroke(200, 0, 0, 100);
			ctx.rect(getAbsoluteX(), getAbsoluteY(), getAbsoluteWidth(), getAbsoluteHeight());

			// for showing padding area (Green rectangle)
			ctx.stroke(0, 200, 0, 100);
			ctx.rect(getX(), getY(), getWidth(), getHeight());
			
			// for showing content area (Blue rectangle)
			ctx.stroke(0, 0, 200, 100);
			ctx.rect(getContentX(), getContentY(), getContentWidth(), getContentHeight());

			ctx.noStroke();
			ctx.pop();
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
			if (!isValidValue(this.left, left)) {
				return;
			}
			this.left = left;
			onChangePadding();
		}

		float getRight() {
			return right;
		}

		void setRight(float right) {
			if (!isValidValue(this.right, right)) {
				return;
			}
			this.right = right;
			onChangePadding();
		}

		float getTop() {
			return top;
		}

		void setTop(float top) {
			if (!isValidValue(this.top, top)) {
				return;
			}
			this.top = top;
			onChangePadding();
		}

		float getBottom() {
			return bottom;
		}

		void setBottom(float bottom) {
			if (!isValidValue(this.bottom, bottom)) {
				return;
			}
			this.bottom = bottom;
			onChangePadding();
		}

		private void onChangePadding() {
			checkCorrectState();
			onChangePositions();
			onChangeDimensions();
			onChangeBounds();
		}

		private boolean isValidValue(float currentValue, float newValue) {
			if (newValue < 0) {
				throw new IllegalArgumentException("padding cannot be less than zero");
			}

			if (newValue == currentValue) {
				return false;
			}

			return true;
		}

		private void checkCorrectState() {
			if (isEnabled() && isNegativeDimensionsEnabled()) {
				throw new IllegalStateException("negative dimensions must be disabled for using Padding system");
			}

			if (left + right > getWidth() || top + bottom > getHeight()) {
				throw new IllegalArgumentException("padding cannot be greater than size of component");
			}
		}

	}

	private final class Margin {
		private float left, right, top, bottom;
		private boolean isEnabled;

		float getLeft() {
			return left;
		}

		void setLeft(float left) {
			checkValue(left);
			this.left = left;
		}

		float getRight() {
			return right;
		}

		void setRight(float right) {
			checkValue(right);
			this.right = right;
		}

		float getTop() {
			return top;
		}

		void setTop(float top) {
			checkValue(top);
			this.top = top;
		}

		float getBottom() {
			return bottom;
		}

		void setBottom(float bottom) {
			checkValue(bottom);
			this.bottom = bottom;
		}

		boolean isEnabled() {
			return isEnabled;
		}

		void setEnabled(boolean isEnabled) {
			this.isEnabled = isEnabled;
		}

		private void checkValue(float value) {
			if (value < 0) {
				throw new IllegalArgumentException("margin cannot be less than zero");
			}
		}
	}
}