package microui.core.base;

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
    protected final Event event;
    protected final Callback callback;
    protected final Tooltip tooltip;
    
    public Component(float x, float y, float w, float h) {
        super(x, y, w, h);
        
        color = new Color(200);
        event = new Event();
        callback = new Callback(this);
        
        tooltip = new Tooltip(callback);
      }
    
    public Component() {
      this(0,0,0,0);
    }
    
    @Override
	public void draw() {
		super.draw();
		callback.listen();
		tooltip.init();
	}

	public void setStyle(Component otherComponent) {
		if(otherComponent == null) { return; }
    	color.set(otherComponent.getColor());
	}
	
	public final Color getColor() {
		System.out.println("created a new Color object");
		return new Color(color);
	}
	
	public final void setColor(Color color) {
		this.color.set(color);
	}

	public final boolean isClicked() {
		return event.clicked();
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
		return event.holding();
	}
	
	public final void onClick(Listener listener) {
		callback.addListener(CLICK, listener);
	}
	
	public final void onDoubleClick(Listener listener) {
		callback.addListener(DOUBLE_CLICK, listener);
	}
	
	public final void onDragged(Listener listener) {
		callback.addListener(DRAGGED, listener);
	}
	
	public final void onDragging(Listener listener) {
		callback.addListener(DRAGGING, listener);
	}
	
	public final void onHold(Listener listener) {
		callback.addListener(HOLD_START, listener);
	}
	
	public final void onMouseInside(Listener listener) {
		callback.addListener(MOUSE_INSIDE, listener);
	}
	
	public final void onMouseInsideLong(Listener listener) {
		callback.addListener(MOUSE_INSIDE_LONG, listener);
	}
	
	public final void onMouseOutside(Listener listener) {
		callback.addListener(MOUSE_OUTSIDE, listener);
	}
	
	public final void onLongPress(Listener listener) {
		callback.addListener(LONG_PRESSED, listener);
	}
	
	public final void onPress(Listener listener) {
		callback.addListener(PRESS, listener);
	}
	
	public final void onRelease(Listener listener) {
		callback.addListener(RELEASE, listener);
	}
	
	public final void onHoldEnd(Listener listener) {
		callback.addListener(HOLD_END, listener);
	}
	
	public final void onShake(Listener listener) {
		callback.addListener(SHAKE, listener);
	}
	
	public final String getTooltipText() {
		return tooltip.getText().getAsString();
	}
	
	public final void setTooltipText(String text) {
		tooltip.getText().set(text);
	}
	
	public final boolean isTooltipEnabled() {
		return tooltip.isEnabled();
	}
	
	public final void setTooltipEnabled(boolean isEnable) {
		tooltip.setEnable(isEnable);
	}
	
	public final Color getTooltipColor() {
		return tooltip.getColor();
	}
	
	public final void setTooltipColor(Color color) {
		tooltip.setColor(color);
	}
}