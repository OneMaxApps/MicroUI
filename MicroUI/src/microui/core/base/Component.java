package microui.core.base;

import static microui.event.EventType.CLICKED;
import static microui.event.EventType.DOUBLE_CLICKED;
import static microui.event.EventType.DRAGGED;
import static microui.event.EventType.DRAGGING;
import static microui.event.EventType.HOLDING;
import static microui.event.EventType.LONG_PRESSED;
import static microui.event.EventType.MOUSE_ENTER;
import static microui.event.EventType.MOUSE_ENTER_LONG;
import static microui.event.EventType.MOUSE_EXIT;
import static microui.event.EventType.PRESSED;
import static microui.event.EventType.RELEASE;
import static microui.event.EventType.SHAKE;
import static microui.event.EventType.UNHOLDED;

import microui.core.style.Color;
import microui.event.Callback;
import microui.event.Event;
import microui.event.EventType;
import microui.event.Listener;
import microui.feedback.Tooltip;

public abstract class Component extends Bounds {
    protected final Color color;
    protected final Event event;
    protected final Callback callback;
    protected final Tooltip tooltip;
    
    public Component(float x, float y, float w, float h) {
        super(x, y, w, h);
        
        color = new Color(44);
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
    
	public final void addListener(EventType type, Listener listener) {
		callback.addListener(type, listener);
	}
	
	
	public final Color getColor() {
		System.out.println("created a new Color object");
		return new Color(color);
	}
	
	public final void setColor(Color color) {
		this.color.set(color);
	}

	@Deprecated
	public final Event getEvent() {
		return event;
	}
	
	
	public final void onClick(Listener listener) {
		callback.addListener(CLICKED, listener);
	}
	
	public final void onDoubleClick(Listener listener) {
		callback.addListener(DOUBLE_CLICKED, listener);
	}
	
	public final void onDragged(Listener listener) {
		callback.addListener(DRAGGED, listener);
	}
	
	public final void onDragging(Listener listener) {
		callback.addListener(DRAGGING, listener);
	}
	
	public final void onHold(Listener listener) {
		callback.addListener(HOLDING, listener);
	}
	
	public final void onMouseEnter(Listener listener) {
		callback.addListener(MOUSE_ENTER, listener);
	}
	
	public final void onMouseEnterLong(Listener listener) {
		callback.addListener(MOUSE_ENTER_LONG, listener);
	}
	
	public final void onMouseExit(Listener listener) {
		callback.addListener(MOUSE_EXIT, listener);
	}
	
	public final void onLongPress(Listener listener) {
		callback.addListener(LONG_PRESSED, listener);
	}
	
	public final void onPress(Listener listener) {
		callback.addListener(PRESSED, listener);
	}
	
	public final void onRelease(Listener listener) {
		callback.addListener(RELEASE, listener);
	}
	
	public final void onUnhold(Listener listener) {
		callback.addListener(UNHOLDED, listener);
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