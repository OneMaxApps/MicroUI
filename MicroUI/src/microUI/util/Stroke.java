package microUI.util;

import static processing.core.PConstants.SQUARE;

import microUI.MicroUI;

public final class Stroke {
    public final Color fill;
    private int weight;
    
    public Stroke() {
      Metrics.Util.registerStroke();
      fill = new Color(0);
      weight = 2;
    }
    
    public void get() {
      MicroUI.getContext().strokeCap(SQUARE);
      MicroUI.getContext().stroke(fill.get());
      MicroUI.getContext().strokeWeight(weight);
    }
    
    public final void setWeight(final int size) { this.weight = size; }
    public final int getWeight() { return weight; }
    
    public final void set(final Stroke stroke) {
    	fill.set(stroke.fill);
    	weight = stroke.getWeight();
    }
}