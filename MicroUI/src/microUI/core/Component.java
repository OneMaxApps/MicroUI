package microUI.core;

import microUI.event.Event;
import microUI.util.Color;
import microUI.util.Metrics;

public abstract class Component extends Bounds {
    public final Color fill;
    public final Event event;
    
    public Component(float x, float y, float w, float h) {
        super(x, y, w, h);
        Metrics.registerComponent();
        fill = new Color(44);
        event = new Event();
      }
    
    public Component() {
      this(0,0,0,0);
    }
    
    public void setStyle(Component otherComponent) {
    	fill.set(otherComponent.fill);
	}
     
}