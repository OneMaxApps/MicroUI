package microui.core.base;

import microui.core.style.Color;
import microui.event.Callback;
import microui.event.Event;
import microui.event.EventType;
import microui.event.Listener;
import microui.feedback.Tooltip;

public abstract class Component extends Bounds {
    public final Color fill;
    public final Event event;
    public final Callback callback;
    public final Tooltip tooltip;
    
    public Component(float x, float y, float w, float h) {
        super(x, y, w, h);
        
        fill = new Color(44);
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
    	fill.set(otherComponent.fill);
	}
    
	public final void on(EventType type, Listener listener) {
		callback.addListener(type, listener);
	}
}