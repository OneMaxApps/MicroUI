package microui.core.base;

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
import microui.feedback.Tooltip;

//Status: STABLE - Do not modify
//Last Reviewed: 12.09.2025
public abstract class Component extends SpatialView {
	private Color color;
	private Event event;
	private Callback callback;
	private Tooltip tooltip;
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
		if (!isVisible()) {
			return;
		}

		super.draw();

		if (event != null) {
			event.listen();
		}

		if (callback != null) {
			callback.listen();
		}

		if (tooltip != null) {
			tooltip.listen();
		}

		debugOnDraw();
	}

	public final Color getColor() {
		ensureColor();
		return new Color(color);
	}

	public final Component setColor(Color color) {
		ensureColor();
		this.color.set(requireNonNull(color, "color cannot be null"));
		return this;
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

	public final Component onClick(Listener listener) {
		ensureCallback();
		callback.addListener(CLICK, requireNonNull(listener, "listener cannot be null"));
		return this;
	}

	public final Component onDoubleClick(Listener listener) {
		ensureCallback();
		callback.addListener(DOUBLE_CLICK, requireNonNull(listener, "listener cannot be null"));
		return this;
	}

	public final Component onDragged(Listener listener) {
		ensureCallback();
		callback.addListener(DRAGGED, requireNonNull(listener, "listener cannot be null"));
		return this;
	}

	public final Component onDragging(Listener listener) {
		ensureCallback();
		callback.addListener(DRAGGING, requireNonNull(listener, "listener cannot be null"));
		return this;
	}

	public final Component onHold(Listener listener) {
		ensureCallback();
		callback.addListener(HOLD_START, requireNonNull(listener, "listener cannot be null"));
		return this;
	}

	public final Component onMouseInside(Listener listener) {
		ensureCallback();
		callback.addListener(MOUSE_INSIDE, requireNonNull(listener, "listener cannot be null"));
		return this;
	}

	public final Component onMouseInsideLong(Listener listener) {
		ensureCallback();
		callback.addListener(MOUSE_INSIDE_LONG, requireNonNull(listener, "listener cannot be null"));
		return this;
	}

	public final Component onMouseOutside(Listener listener) {
		ensureCallback();
		callback.addListener(MOUSE_OUTSIDE, requireNonNull(listener, "listener cannot be null"));
		return this;
	}

	public final Component onLongPress(Listener listener) {
		ensureCallback();
		callback.addListener(LONG_PRESSED, requireNonNull(listener, "listener cannot be null"));
		return this;
	}

	public final Component onPress(Listener listener) {
		ensureCallback();
		callback.addListener(PRESS, requireNonNull(listener, "listener cannot be null"));
		return this;
	}

	public final Component onRelease(Listener listener) {
		ensureCallback();
		callback.addListener(RELEASE, requireNonNull(listener, "listener cannot be null"));
		return this;
	}

	public final Component onHoldEnd(Listener listener) {
		ensureCallback();
		callback.addListener(HOLD_END, requireNonNull(listener, "listener cannot be null"));
		return this;
	}

	public final Component onShake(Listener listener) {
		ensureCallback();
		callback.addListener(SHAKE, requireNonNull(listener, "listener cannot be null"));
		return this;
	}

	public final Component setPadding(float left, float right, float top, float bottom) {
		ensurePadding();
		padding.set(left, right, top, bottom);
		return this;
	}

	public final Component setPadding(float paddingHorizontal, float paddingVertical) {
		setPadding(paddingHorizontal, paddingHorizontal, paddingVertical, paddingVertical);
		return this;
	}

	public final Component setPadding(float padding) {
		setPadding(padding, padding);
		return this;
	}

	public final Component copyPaddingFrom(Component otherComponent) {
		setPadding(requireNonNull(otherComponent, "other component cannot be null").getPaddingLeft(),
				otherComponent.getPaddingRight(), otherComponent.getPaddingTop(), otherComponent.getPaddingBottom());
		return this;
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

	public final float getPadX() {
		ensurePadding();
		return getX() - getPaddingLeft();
	}

	public final float getPadY() {
		ensurePadding();
		return getY() - getPaddingTop();
	}

	public final float getPadWidth() {
		return getWidth() + getPaddingRight() + getPaddingLeft();
	}

	public final float getPadHeight() {
		return getHeight() + getPaddingBottom() + getPaddingTop();
	}

	public final boolean isPaddingEnabled() {
		if (padding == null) {
			return false;
		}
		return padding.isEnabled();
	}

	public final Component setPaddingEnabled(boolean isEnabled) {
		ensurePadding();
		padding.setEnabled(isEnabled);
		return this;
	}

	public final Component resetPadding() {
		setPadding(0);
		return this;
	}

	public final boolean hasPadding() {
		if (padding == null) {
			return false;
		}
		return padding.isEnabled()
				&& (padding.getLeft() > 0 || padding.getRight() > 0 || padding.getTop() > 0 || padding.getBottom() > 0);
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

	public final Component setMargin(float left, float right, float top, float bottom) {
		margin.setLeft(left);
		margin.setRight(right);
		margin.setTop(top);
		margin.setBottom(bottom);
		return this;
	}
	
	public final Component setMarginLeft(float left) {
		ensureMargin();
		margin.setLeft(left);
		return this;
	}

	public final Component setMarginRight(float right) {
		ensureMargin();
		margin.setRight(right);
		return this;
	}

	public final Component setMarginTop(float top) {
		ensureMargin();
		margin.setTop(top);
		return this;
	}

	public final Component setMarginBottom(float bottom) {
		ensureMargin();
		margin.setBottom(bottom);
		return this;
	}

	public final Component setMargin(float marginHorizontal, float marginVertical) {
		setMargin(marginHorizontal, marginHorizontal, marginVertical, marginVertical);
		return this;
	}

	public final Component setMargin(float margin) {
		setMargin(margin, margin);
		return this;
	}

	public final Component copyMarginFrom(Component otherComponent) {
		setMargin(requireNonNull(otherComponent, "other component cannot be null").getMarginLeft(),
				otherComponent.getMarginRight(), otherComponent.getMarginTop(), otherComponent.getMarginBottom());
		return this;
	}

	public final Component resetMargin() {
		setMargin(0);
		return this;
	}

	public final boolean isMarginEnabled() {
		if (margin == null) {
			return false;
		}
		return margin.isEnabled();
	}

	public final Component setMarginEnabled(boolean isEnabled) {
		ensureMargin();
		margin.setEnabled(isEnabled);
		return this;
	}

	public final boolean hasMargin() {
		if (margin == null) {
			return false;
		}
		return margin.isEnabled()
				&& (margin.getLeft() > 0 || margin.getRight() > 0 || margin.getTop() > 0 || margin.getBottom() > 0);
	}

	public final float getAbsoluteX() {
		return getPadX()-getMarginLeft();
	}

	public final float getAbsoluteY() {
		return getPadY()-getMarginTop();
	}

	public final float getAbsoluteWidth() {
		return getPadWidth() + getMarginLeft() + getMarginRight();
	}

	public final float getAbsoluteHeight() {
		return getPadHeight() + getMarginTop() + getMarginBottom();
	}

	public final void setAbsoluteX(float x) {
		setX(x + getMarginLeft() + getPaddingLeft());
	}

	public final void setAbsoluteY(float y) {
		setY(y + getMarginTop() + getPaddingTop());
	}

	public final void setAbsoluteWidth(float width) {
		setWidth(width - (getMarginLeft() + getMarginRight()) - (getPaddingLeft() + getPaddingRight()));
	}

	public final void setAbsoluteHeight(float height) {
		setHeight(height - (getMarginTop() + getMarginBottom()) - (getPaddingTop() + getPaddingBottom()));
	}

	public final void setAbsolutePosition(float x, float y) {
		setAbsoluteX(x);
		setAbsoluteY(y);
	}

	/**
	 * Resize using margin. this method can change real width and height but it's
	 * don't ignore constrain system. Also this method can't modify margin
	 * parameters
	 * 
	 * @param width  change real width with using margin
	 * @param height change real height with using margin
	 * 
	 */
	public final void setAbsoluteSize(float width, float height) {
		setAbsoluteWidth(width);
		setAbsoluteHeight(height);
	}

	/**
	 * Change bounds using margin. this method can change real bounds, but it's
	 * don't ignore constrain system. Also this method can't modify margin
	 * parameters
	 * 
	 * @param x change position with margin left
	 * @param y change position with margin top
	 * @param width  change real width with using margin
	 * @param height change real height with using margin
	 * 
	 */
	public final void setAbsoluteBounds(float x, float y, float width, float height) {
		setAbsoluteSize(width, height);
		setAbsolutePosition(x, y);
	}

	public final String getTooltipText() {
		return tooltip == null ? "" : tooltip.getText().getAsString();
	}

	public final Component setTooltipText(String text) {
		ensureTooltip();

		tooltip.getText().set(requireNonNull(text, "text cannot be null"));
		return this;
	}

	public final boolean isTooltipEnabled() {
		ensureTooltip();
		return tooltip.isEnabled();
	}

	public final Component setTooltipEnabled(boolean enabled) {
		ensureTooltip();
		tooltip.setEnabled(enabled);
		return this;
	}

	public final Color getTooltipColor() {
		ensureTooltip();
		return tooltip.getColor();
	}

	public final Component setTooltipColor(Color color) {
		ensureTooltip();
		tooltip.setColor(requireNonNull(color, "color cannot be null"));
		return this;
	}

	public final Color getTooltipTextColor() {
		ensureTooltip();
		return tooltip.getText().getColor();
	}

	public final Component setTooltipTextColor(Color color) {
		ensureTooltip();
		tooltip.getText().setColor(requireNonNull(color, "color cannot be null"));
		return this;
	}

	protected final Component setTooltipAdditionalCondition(boolean condition) {
		ensureTooltip();
		tooltip.setAdditionalCondition(condition);
		return this;
	}

	protected final Component setEventListener(SpatialView bounds) {
		ensureEvent();
		event.setListener(requireNonNull(bounds, "bounds cannot be null"));
		return this;
	}

	protected final Event getMutableEvent() {
		ensureEvent();
		return event;
	}

	protected final Component setCallbackListener(SpatialView bounds) {
		ensureCallback();
		callback.setListener(requireNonNull(bounds, "bounds cannot be null"));
		return this;
	}

	protected final Color getMutableColor() {
		ensureColor();
		return color;
	}

	private void ensureTooltip() {
		ensureCallback();
		if (tooltip == null) {
			tooltip = new Tooltip(callback);
		}
	}

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
			ctx.pushStyle();
			ctx.noFill();
			ctx.strokeWeight(4);

			// for showing margin area (Red rectangle)
			ctx.stroke(200, 0, 0, 100);
			ctx.rect(getAbsoluteX(), getAbsoluteY(), getAbsoluteWidth(), getAbsoluteHeight());

			// for showing pad area (Green rectangle)
			ctx.stroke(0, 200, 0, 100);
			ctx.rect(getPadX(), getPadY(), getPadWidth(), getPadHeight());
			
			// for showing content area (Blue rectangle)
			ctx.stroke(0, 0, 200, 100);
			ctx.rect(getX(), getY(), getWidth(), getHeight());

			ctx.noStroke();
			ctx.popStyle();
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
			return isEnabled ? left : 0;
		}

		float getRight() {
			return isEnabled ? right : 0;
		}

		float getTop() {
			return isEnabled ? top : 0;
		}

		float getBottom() {
			return isEnabled ? bottom : 0;
		}

		public void set(float left, float right, float top, float bottom) {
			boolean hasChanges = isCorrectNewValue(this.left, left) || isCorrectNewValue(this.right, right)
					|| isCorrectNewValue(this.top, top) || isCorrectNewValue(this.bottom, bottom);

			if (!hasChanges) {
				return;
			}

			this.left = left;
			this.right = right;
			this.top = top;
			this.bottom = bottom;

			onChangePadding();
		}

		private void onChangePadding() {
			checkCorrectState();
			requestUpdate();
		}

		private boolean isCorrectNewValue(float currentValue, float newValue) {
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
		}

	}

	private final class Margin {
		private float left, right, top, bottom;
		private boolean isEnabled;

		float getLeft() {
			return isEnabled ? left : 0;
		}

		float getRight() {
			return isEnabled ? right : 0;
		}

		float getTop() {
			return isEnabled ? top : 0;
		}

		float getBottom() {
			return isEnabled ? bottom : 0;
		}
		
		void setLeft(float left) {
			checkValue(left);
			this.left = left;
		}

		void setRight(float right) {
			checkValue(right);
			this.right = right;
		}

		void setTop(float top) {
			checkValue(top);
			this.top = top;
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