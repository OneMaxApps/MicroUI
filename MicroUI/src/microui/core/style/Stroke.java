package microui.core.style;

import static processing.core.PConstants.SQUARE;

import microui.MicroUI;
import microui.util.Metrics;

public final class Stroke {
	private final Color color;
    private int weight;
    
    public Stroke() {
      Metrics.register("Stroke");
      
      color = new Color(0);
      weight = 2;
    }
    
    public void get() {
      MicroUI.getContext().strokeCap(SQUARE);
      MicroUI.getContext().stroke(color.get());
      MicroUI.getContext().strokeWeight(weight);
    }
    
    public final void setWeight(final int size) { this.weight = size; }
    public final int getWeight() { return weight; }
    
    public final void set(final Stroke stroke) {
    	color.set(stroke.color);
    	weight = stroke.getWeight();
    }
    
	public final Color getColor() {
		return new Color(color);
	}
	
	public final void setColor(Color color) {
		this.color.set(color);
	}
}