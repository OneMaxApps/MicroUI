package microui.core.base;

import static java.util.Objects.requireNonNull;
import static microui.core.style.theme.ThemeManager.getTheme;

import microui.MicroUI;
import microui.core.style.Color;
import microui.event.Event;
import microui.event.EventType;
import microui.event.InteractionHandler;
import microui.event.Listener;
import microui.feedback.Tooltip;

//Status: STABLE - Do not modify
//Last Reviewed: 26.09.2025
public abstract class Component extends SpatialView {
	private final Padding padding;
	private final Margin margin;
	private final Color backgroundColor;
	private final Event event;
	private final InteractionHandler interactionHandler;
	private Tooltip tooltip;

	public Component(float x, float y, float width, float height) {
		super(x, y, width, height);
		setVisible(true);
		setConstrainDimensionsEnabled(true);
		setNegativeDimensionsEnabled(false);
		
		padding = new Padding();
		margin = new Margin();
		backgroundColor = getTheme().getBackgroundColor();
		event = new Event(this);
		interactionHandler = new InteractionHandler(this);
		
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

		event.listen();
		
		interactionHandler.listen();

		if (tooltip != null) {
			tooltip.listen();
		}

		debugOnDraw();
	}

	public final Color getBackgroundColor() {
		return new Color(backgroundColor);
	}

	public final Component setBackgroundColor(Color color) {
		this.backgroundColor.set(color);
		return this;
	}

	public final boolean isClicked() {
		return event.clicked();
	}

	public final boolean isClicked(int count) {
		return event.clicked(count);
	}

	public final boolean isDragged() {
		return event.dragged();
	}

	public final boolean isDragging() {
		return event.dragging();
	}

	public final boolean isPressed() {
		return event.pressed();
	}

	public final boolean isMouseInside() {
		return event.inside();
	}

	public final boolean isMouseOutside() {
		return event.outside();
	}

	public final boolean isHolding() {
		return event.isHolding();
	}
	
	
	
	public final Component onHover(Listener listener) {
		interactionHandler.addListener(EventType.HOVER, listener);
		
		return this;
	}
	
	public final Component onPress(Listener listener) {
		interactionHandler.addListener(EventType.PRESS, listener);
		
		return this;
	}
	
	public final Component onPressed(Listener listener) {
		interactionHandler.addListener(EventType.PRESSED, listener);
		
		return this;
	}
	
	public final Component onRelease(Listener listener) {
		interactionHandler.addListener(EventType.RELEASE, listener);
		
		return this;
	}
	
	public final Component onReleased(Listener listener) {
		interactionHandler.addListener(EventType.RELEASED, listener);
		
		return this;
	}
	
	public final Component onLongPress(Listener listener) {
		interactionHandler.addListener(EventType.LONG_PRESS, listener);
		
		return this;
	}
	
	public final Component onLongPressed(Listener listener) {
		interactionHandler.addListener(EventType.LONG_PRESSED, listener);
		
		return this;
	}
	
	public final Component onEnter(Listener listener) {
		interactionHandler.addListener(EventType.ENTER, listener);
		
		return this;
	}
	
	public final Component onLeave(Listener listener) {
		interactionHandler.addListener(EventType.LEAVE, listener);
		
		return this;
	}
	
	public final Component onEnterLong(Listener listener) {
		interactionHandler.addListener(EventType.ENTER_LONG, listener);
		
		return this;
	}
	
	public final Component onLeaveLong(Listener listener) {
		interactionHandler.addListener(EventType.LEAVE_LONG, listener);
		
		return this;
	}
	
	public final Component onClick(Listener listener) {
		interactionHandler.addListener(EventType.CLICK, listener);
		
		return this;
	}
	
	public final Component onDoubleClick(Listener listener) {
		interactionHandler.addListener(EventType.DOUBLE_CLICK, listener);
		
		return this;
	}
	
	public final Component onDragStart(Listener listener) {
		interactionHandler.addListener(EventType.DRAG_START, listener);
		
		return this;
	}
	
	public final Component onDragging(Listener listener) {
		interactionHandler.addListener(EventType.DRAGGING, listener);
		
		return this;
	}
	
	public final Component onDragEnd(Listener listener) {
		interactionHandler.addListener(EventType.DRAG_END, listener);
		
		return this;
	}

	public final Component setPaddingLeft(float left) {
		padding.setLeft(left);

		return this;
	}

	public final Component setPaddingRight(float right) {
		padding.setRight(right);

		return this;
	}

	public final Component setPaddingTop(float top) {
		padding.setTop(top);

		return this;
	}

	public final Component setPaddingBottom(float bottom) {
		padding.setBottom(bottom);

		return this;
	}

	public final Component setPadding(float left, float right, float top, float bottom) {
		padding.setLeft(left);
		padding.setRight(right);
		padding.setTop(top);
		padding.setBottom(bottom);
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
		return padding.getLeft();
	}

	public final float getPaddingRight() {
		return padding.getRight();
	}

	public final float getPaddingTop() {
		return padding.getTop();
	}

	public final float getPaddingBottom() {
		return padding.getBottom();
	}

	public final float getPadX() {
		return getX() - getPaddingLeft();
	}

	public final float getPadY() {
		return getY() - getPaddingTop();
	}

	public final float getPadWidth() {
		return getWidth() + getPaddingRight() + getPaddingLeft();
	}

	public final float getPadHeight() {
		return getHeight() + getPaddingBottom() + getPaddingTop();
	}

	public final boolean isPaddingEnabled() {
		return padding.isEnabled();
	}

	public final Component setPaddingEnabled(boolean isEnabled) {
		padding.setEnabled(isEnabled);
		return this;
	}

	public final Component resetPadding() {
		setPadding(0);
		return this;
	}

	public final boolean hasPadding() {
		return padding.isEnabled()
				&& (padding.getLeft() > 0 || padding.getRight() > 0 || padding.getTop() > 0 || padding.getBottom() > 0);
	}

	public final float getMarginLeft() {
		return margin.getLeft();
	}

	public final float getMarginRight() {
		return margin.getRight();
	}

	public final float getMarginTop() {
		return margin.getTop();
	}

	public final float getMarginBottom() {
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
		margin.setLeft(left);
		return this;
	}

	public final Component setMarginRight(float right) {
		margin.setRight(right);
		return this;
	}

	public final Component setMarginTop(float top) {
		margin.setTop(top);
		return this;
	}

	public final Component setMarginBottom(float bottom) {
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
		return margin.isEnabled();
	}

	public final Component setMarginEnabled(boolean isEnabled) {
		margin.setEnabled(isEnabled);
		return this;
	}

	public final boolean hasMargin() {
		return margin.isEnabled()
				&& (margin.getLeft() > 0 || margin.getRight() > 0 || margin.getTop() > 0 || margin.getBottom() > 0);
	}

	public final float getAbsoluteX() {
		return getPadX() - getMarginLeft();
	}

	public final float getAbsoluteY() {
		return getPadY() - getMarginTop();
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
	 * @param x      change position with margin left
	 * @param y      change position with margin top
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
		getOrCreateTooltip().getText().set(requireNonNull(text, "text cannot be null"));
		return this;
	}

	public final boolean isTooltipEnabled() {
		return getOrCreateTooltip().isEnabled();
	}

	public final Component setTooltipEnabled(boolean enabled) {
		getOrCreateTooltip().setEnabled(enabled);
		return this;
	}

	public final Color getTooltipColor() {
		return getOrCreateTooltip().getColor();
	}

	public final Component setTooltipColor(Color color) {
		getOrCreateTooltip().setColor(requireNonNull(color, "color cannot be null"));
		return this;
	}

	public final Color getTooltipTextColor() {
		return getOrCreateTooltip().getText().getColor();
	}

	public final Component setTooltipTextColor(Color color) {
		getOrCreateTooltip().getText().setColor(requireNonNull(color, "color cannot be null"));
		return this;
	}

	protected final Component setTooltipAdditionalCondition(boolean condition) {
		getOrCreateTooltip().setAdditionalCondition(condition);
		return this;
	}

	protected final Component setEventListener(SpatialView bounds) {
		event.setListener(requireNonNull(bounds, "bounds cannot be null"));
		return this;
	}

	protected final Color getMutableBackgroundColor() {
		return backgroundColor;
	}

	private Tooltip getOrCreateTooltip() {
		return tooltip == null ? tooltip = new Tooltip(interactionHandler) : tooltip;
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
			if (isEnabled) {
				checkCorrectState();
			}
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

		void setLeft(float left) {
			if (!isCorrectNewValue(this.left, left)) {
				return;
			}
			this.left = left;
		}

		void setRight(float right) {
			if (!isCorrectNewValue(this.right, right)) {
				return;
			}
			this.right = right;
		}

		void setTop(float top) {
			if (!isCorrectNewValue(this.top, top)) {
				return;
			}
			this.top = top;
		}

		void setBottom(float bottom) {
			if (!isCorrectNewValue(this.bottom, bottom)) {
				return;
			}
			this.bottom = bottom;
		}

		boolean isCorrectNewValue(float currentValue, float newValue) {
			if (newValue < 0) {
				throw new IllegalArgumentException("padding cannot be less than zero");
			}

			if (newValue == currentValue) {
				return false;
			}

			return true;
		}

		void checkCorrectState() {
			if (isNegativeDimensionsEnabled()) {
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

			if (isEnabled) {
				checkCorrectState();
			}
		}

		void checkValue(float value) {
			if (value < 0) {
				throw new IllegalArgumentException("margin cannot be less than zero");
			}
		}

		void checkCorrectState() {
			if (isNegativeDimensionsEnabled()) {
				throw new IllegalStateException("negative dimensions must be disabled for using Margin system");
			}
		}
	}
}