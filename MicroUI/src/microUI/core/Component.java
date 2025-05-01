package microUI.core;

import microUI.event.Event;
import microUI.util.Color;

public abstract class Component extends BaseForm {
    public final Color fill;
    public final Event event;
    
    public Component() {
      this(0,0,0,0);
    }
    
    public Component(float x, float y, float w, float h) {
      super(x, y, w, h);
      fill = new Color(44);
      event = new Event();
    }
    
    public void setStyle(Component otherComponent) {
    	fill.set(otherComponent.fill);
	}
     
}