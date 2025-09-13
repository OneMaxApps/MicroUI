package microui.layout;

public final class ColumnLayoutParams implements LayoutParams {
	private final float weight;
	private final int alignX;
	
	public ColumnLayoutParams(float weight, int alignX) {
		if(weight < 0) {
			throw new IllegalArgumentException("weight cannot be lower than zero");
		}
		
		if(weight > 1) {
			throw new IllegalArgumentException("weight cannot be greater than 1");
		}
		
		if(alignX < -1) {
			throw new IllegalArgumentException("alignX cannot be lower than -1");
		}
		
		if(alignX > 1) {
			throw new IllegalArgumentException("alignX cannot be greater than 1");
		}
		
		this.weight = weight;
		this.alignX = alignX;
	}
	
	public ColumnLayoutParams(float weight) {
		this(weight,0);
	}

	public float getWeight() {
		return weight;
	}

	public int getAlignX() {
		return alignX;
	}
	
}