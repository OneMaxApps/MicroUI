package microUI.util;

import static processing.core.PConstants.SQUARE;

import processing.core.PApplet;

public final class Stroke {
    public final Color fill;
    private int weight;
    private final PApplet app;
    
    public Stroke(PApplet app) {
      this.app = app;
      fill = new Color(0);
      weight = 2;
    }
    
    public void get() {
      app.strokeCap(SQUARE);
      app.stroke(fill.get());
      app.strokeWeight(weight);
    }
    
    public final void setWeight(final int size) { this.weight = size; }
    public final int getWeight() { return weight; }
    
    public final void set(final Stroke stroke) {
    	fill.set(stroke.fill);
    	weight = stroke.getWeight();
    }
}