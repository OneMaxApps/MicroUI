package microui.core.style;

import static microui.MicroUI.getContext;
import static processing.core.PConstants.SQUARE;

import microui.util.Metrics;
import processing.core.PGraphics;

public final class Stroke {
	private static final int DEFAULT_STROKE_WEIGHT = 2;
	private final Color color;
	private float weight;

	public Stroke(Color color, float weight) {
		if(color == null) {
			throw new NullPointerException("color cannot be null");
		}
		Metrics.register("Stroke");
		this.color = new Color(color);
		setWeight(weight);
	}
	
	public Stroke(Color color) {
		this(color,DEFAULT_STROKE_WEIGHT);
	}
	
	public Stroke(float weight) {
		this(new Color(),weight);
	}
	
	public Stroke() {
		this(DEFAULT_STROKE_WEIGHT);
	}

	public void apply() {
		getContext().strokeCap(SQUARE);
		getContext().stroke(color.get());
		getContext().strokeWeight(weight);
	}
	
	public void apply(PGraphics pGraphics) {
		if(pGraphics == null) {
			throw new NullPointerException("pGraphics cannot be null");
		}
		pGraphics.strokeCap(SQUARE);
		pGraphics.stroke(color.get());
		pGraphics.strokeWeight(weight);
	}

	public void setWeight(float weight) {
		if(weight <= 0) {
			throw new IllegalArgumentException("stroke weight cannot be lower than 1");
		}
		this.weight = weight;
	}

	public float getWeight() {
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