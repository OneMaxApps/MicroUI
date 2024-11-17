package microUI.utils;

import static processing.core.PConstants.SQUARE;

import processing.core.PApplet;

public class Form extends BaseForm {
	protected PApplet app;
    public Color fill;
    public Stroke stroke;
    public Event event;
    private boolean isVisible;
    
    public Form(PApplet app) {
      this(app,0,0,0,0);
    }
    
    public Form(PApplet app, float x, float y, float w, float h) {
      super((int) x,(int)  y,(int)  w,(int)  h);
      this.app = app;
      setTransforms(this);
      fill = new Color(app,app.color(44));
      stroke = new Stroke();
      event = new Event(app);
    }
    
    public void setVisible(boolean v) { isVisible = v; }
    
    public boolean isVisible() { return isVisible; }
    

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