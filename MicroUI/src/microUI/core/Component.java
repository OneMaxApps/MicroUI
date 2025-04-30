package microUI.core;

import microUI.event.Event;
import microUI.util.Color;
import processing.core.PApplet;

public abstract class Component extends BaseForm {
    public final Color fill;
    public final Event event;
    
    public Component(PApplet app) {
      this(app,0,0,0,0);
    }
    
    public Component(PApplet app, float x, float y, float w, float h) {
      super(app, x, y, w, h);
      fill = new Color(44);
      event = new Event(app);
    }
    
    public void setStyle(Component otherComponent) {
    	fill.set(otherComponent.fill);
	}
     
}