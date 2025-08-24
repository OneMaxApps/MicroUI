package microui.core.base;

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

	
	public final Event getEvent() {
		return event;
	}
	
	public final String getTooltipText() {
		return tooltip.getText().getAsString();
	}
	
	public final void setTooltipText(String text) {
		tooltip.getText().set(text);
	}
	
	public final boolean isTooltipEnable() {
		return tooltip.isEnabled();
	}
	
	public final void setEnableTooltip(boolean isEnable) {
		tooltip.setEnable(isEnable);
	}
	
	public final Color getTooltipColor() {
		return tooltip.getColor();
	}
	
	public final void setTooltipColor(Color color) {
		tooltip.setColor(color);
	}
}