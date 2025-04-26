package microUI.util;

import static processing.core.PConstants.SQUARE;

import processing.core.PApplet;

public abstract class Component extends BaseForm {
    public final Color fill;
    public final Stroke stroke;
    public Event event;
    
    public Component(PApplet app) {
      this(app,0,0,0,0);
    }
    
    public Component(PApplet app, float x, float y, float w, float h) {
      super(app, x, y, w, h);
      setTransforms(this);
      fill = new Color(app,app.color(44));
      stroke = new Stroke();
      event = new Event(app);
    }
    
    public void setStyle(Component component) {
    	fill.set(component.fill);
    	stroke.set(component.stroke);
	}
    
    public class Stroke {
    public final Color fill;
    private int weight;
    
    public Stroke() {
      fill = new Color(app,0);
      weight = 2;
    }
    
    public void get() {
      app.strokeCap(SQUARE);
      app.stroke(fill.get());
      app.strokeWeight(weight);
    }
    
    public final void setWeight(int size) { this.weight = size; }
    public final int getWeight() { return weight; }
    
    public final void set(Stroke stroke) {
    	fill.set(stroke.fill);
    	weight = stroke.getWeight();
    }
  }

}