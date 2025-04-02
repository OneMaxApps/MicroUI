package microUI.util;

import static processing.core.PConstants.SQUARE;

import processing.core.PApplet;

public class Component extends BaseForm {
    public Color fill;
    public Stroke stroke;
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
		fill = component.fill;
		stroke = component.stroke;
	}
    
    
    
  public class Stroke {
    public Color fill;
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
    
    public void setWeight(int size) { this.weight = size; }
    public int getWeight() { return weight; }
    
  }

}