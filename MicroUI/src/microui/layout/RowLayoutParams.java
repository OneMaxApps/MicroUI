package microui.layout;

public final class RowLayoutParams implements LayoutParams {
	private final float weight;
	private final int alignY;

	
	public RowLayoutParams(float weight, int alignY) {

		if (weight < 0) {
			throw new IllegalArgumentException("weight cannot be lower than zero");
		}

		if (weight > 1) {
			throw new IllegalArgumentException("weight cannot be greater than 1");
		}

		if (alignY < -1) {
			throw new IllegalArgumentException("alignY cannot be lower than -1");
		}

		if (alignY > 1) {
			throw new IllegalArgumentException("alignY cannot be greater than 1");
		}

		this.weight = weight;
		this.alignY = alignY;
	}
	
	public RowLayoutParams(float weight) {
		this(weight,0);
	}

	public float getWeight() {
		return weight;
	}

	public int getAlignY() {
		return alignY;
	}

}