package microui.core.style;

import static microui.MicroUI.getContext;

import microui.util.Metrics;

public final class Stroke {
	private static final int SQUARE = 1;
	private final Color color;
	private short weight;

	public Stroke() {
		Metrics.register("Stroke");

		color = new Color(0);
		weight = 2;
	}

	public void apply() {
		getContext().strokeCap(SQUARE);
		getContext().stroke(color.get());
		getContext().strokeWeight(weight);
	}

	public void setWeight(short weight) {
		if(weight <= 0) {
			throw new IllegalArgumentException("stroke weight cannot be lower than 1");
		}
		this.weight = (short) weight;
	}

	public short getWeight() {
		return weight;
	}

	public void set(Stroke stroke) {
		if(stroke == null) {
			throw new NullPointerException("stroke cannot be null");
		}
		
		color.set(stroke.color);
		weight = stroke.getWeight();
	}

	public Color getColor() {
		return new Color(color);
	}

	public void setColor(Color color) {
		if(color == null) {
			throw new NullPointerException("color cannot be null");
		}
		this.color.set(color);
	}
}