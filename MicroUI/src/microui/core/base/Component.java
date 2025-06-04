package microui.core.base;

import microui.core.style.Color;
import microui.event.Event;
import microui.event.EventCallback;
import microui.feedback.Tooltip;

public abstract class Component extends Bounds {
    public final Color fill;
    public final Event event;
    public final EventCallback eventCallback;
    public final Tooltip tooltip;
    
    public Component(float x, float y, float w, float h) {
        super(x, y, w, h);
        
        fill = new Color(44);
        event = new Event();
        eventCallback = new EventCallback(this);
        
        tooltip = new Tooltip(eventCallback);
      }
    
    public Component() {
      this(0,0,0,0);
    }
    
    @Override
	public void draw() {
		super.draw();
		eventCallback.listen();
		tooltip.init();
	}

	public void setStyle(Component otherComponent) {
		if(otherComponent == null) { return; }
    	fill.set(otherComponent.fill);
	}
     
}