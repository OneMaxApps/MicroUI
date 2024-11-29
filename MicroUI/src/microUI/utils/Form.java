package microUI.utils;

import static processing.core.PConstants.SQUARE;

import microUI.MicroUI;

public class Form extends BaseForm {
    public Color fill;
    public Stroke stroke;
    public Event event;
    
    public Form() {
      this(0,0,0,0);
    }
    
    public Form(float x, float y, float w, float h) {
      super((int) x,(int)  y,(int)  w,(int)  h);
      setTransforms(this);
      fill = new Color(MicroUI.app.color(44));
      stroke = new Stroke();
      event = new Event();
    }
    

  public class Stroke {
    public Color fill;
    private int weight;
    
    public Stroke() {
      fill = new Color(0);
      weight = 2;
    }
    
    public void get() {
      MicroUI.app.strokeCap(SQUARE);
      MicroUI.app.stroke(fill.get());
      MicroUI.app.strokeWeight(weight);
    }
    
    public void setWeight(int size) { this.weight = size; }
    public int getWeight() { return weight; }
    
  }

}