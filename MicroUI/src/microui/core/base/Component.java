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

import microui.core.style.Color;
import microui.event.Callback;
import microui.event.Event;
import microui.event.Listener;
import microui.feedback.Tooltip;

public abstract class Component extends Bounds {
	protected final Color color;
    private Event event;
    private Callback callback;
    private Tooltip tooltip;
    
    public Component(float x, float y, float w, float h) {
        super(x, y, w, h);
        
        color = new Color(200);
        
      }
    
    public Component() {
      this(0,0,0,0);
    }
    
    @Override
	public void draw() {
		super.draw();
		if(callback != null) {
			callback.listen();
		}
		
		if(tooltip != null) {
			tooltip.init();
		}
	}

	public void setStyle(Component otherComponent) {
		if(otherComponent == null) { return; }
    	color.set(otherComponent.getColor());
	}
	
	public final Color getColor() {
		return new Color(color);
	}
	
	public final void setColor(Color color) {
		this.color.set(color);
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
	
	public final boolean isInside() {
		ensureEvent();
		return event.inside();
	}
	
	public final boolean isOutside() {
		ensureEvent();
		return event.outside();
	}
	
	public final void onClick(Listener listener) {
		ensureCallback();
		callback.addListener(CLICK, listener);
	}
	
	public final void onDoubleClick(Listener listener) {
		ensureCallback();
		callback.addListener(DOUBLE_CLICK, listener);
	}
	
	public final void onDragged(Listener listener) {
		ensureCallback();
		callback.addListener(DRAGGED, listener);
	}
	
	public final void onDragging(Listener listener) {
		ensureCallback();
		callback.addListener(DRAGGING, listener);
	}
	
	public final void onHold(Listener listener) {
		ensureCallback();
		callback.addListener(HOLD_START, listener);
	}
	
	public final void onMouseInside(Listener listener) {
		ensureCallback();
		callback.addListener(MOUSE_INSIDE, listener);
	}
	
	public final void onMouseInsideLong(Listener listener) {
		ensureCallback();
		callback.addListener(MOUSE_INSIDE_LONG, listener);
	}
	
	public final void onMouseOutside(Listener listener) {
		ensureCallback();
		callback.addListener(MOUSE_OUTSIDE, listener);
	}
	
	public final void onLongPress(Listener listener) {
		ensureCallback();
		callback.addListener(LONG_PRESSED, listener);
	}
	
	public final void onPress(Listener listener) {
		ensureCallback();
		callback.addListener(PRESS, listener);
	}
	
	public final void onRelease(Listener listener) {
		ensureCallback();
		callback.addListener(RELEASE, listener);
	}
	
	public final void onHoldEnd(Listener listener) {
		ensureCallback();
		callback.addListener(HOLD_END, listener);
	}
	
	public final void onShake(Listener listener) {
		ensureCallback();
		callback.addListener(SHAKE, listener);
	}
	
	public final String getTooltipText() {
		return tooltip == null ? "" : tooltip.getText().getAsString();
	}
	
	public final void setTooltipText(String text) {
		ensureTooltip();
		
		tooltip.getText().set(requireNonNull(text, "text cannot be null"));
	}
	
	public final boolean isTooltipEnabled() {
		ensureTooltip();
		return tooltip.isEnabled();
	}
	
	public final void setTooltipEnabled(boolean enabled) {
		ensureTooltip();
		tooltip.setEnabled(enabled);
	}
	
	public final Color getTooltipColor() {
		ensureTooltip();
		return tooltip.getColor();
	}
	
	public final void setTooltipColor(Color color) {
		ensureTooltip();
		tooltip.setColor(color);
	}
	
	public final Color getTooltipTextColor() {
		ensureTooltip();
		return tooltip.getText().getColor();
	}
	
	public final void setTooltipTextColor(Color color) {
		ensureTooltip();
		tooltip.getText().setColor(color);
	}
	
	protected final void setTooltipAdditionalCondition(boolean condition) {
		ensureTooltip();
		tooltip.setAdditionalCondition(condition);
	}
	
	protected final void setEventListener(Bounds bounds) {
		ensureEvent();
		event.listen(bounds);
	}
	
	@Deprecated
	protected final Event getEvent() {
		ensureEvent();
		return event;
	}
	
	protected final void setCallbackListener(Bounds bounds) {
		ensureCallback();
		callback.setListener(bounds);
	}
	
	private final void ensureTooltip() {
		if(tooltip == null) { tooltip = new Tooltip(callback); }
	}
	
	private final void ensureCallback() {
		if(callback == null) { callback = new Callback(this); }
	}
	
	private final void ensureEvent() {
		if(event == null) { event = new Event(); }
	}
}