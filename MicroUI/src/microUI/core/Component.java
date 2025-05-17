package microUI.core;

import microUI.event.Event;
import microUI.feedback.Tooltip;
import microUI.util.Color;

public abstract class Component extends Bounds {
    public final Color fill;
    public final Event event;
    public final Tooltip tooltip;
    
    public Component(float x, float y, float w, float h) {
        super(x, y, w, h);
        
        fill = new Color(44);
        event = new Event();
        
        tooltip = new Tooltip(event);
      }
    
    public Component() {
      this(0,0,0,0);
    }
    
    @Override
	public void draw() {
		super.draw();
		tooltip.init();
	}

	public void setStyle(Component otherComponent) {
		if(otherComponent == null) { return; }
    	fill.set(otherComponent.fill);
	}
     
}