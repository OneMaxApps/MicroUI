package microui.layout.params;

public final class ColumnLayoutParams extends AbstractLinearLayoutParams {
	
	public ColumnLayoutParams(float weight, int alignX) {
		super(weight,alignX,0);
	}
	
	public ColumnLayoutParams(float weight) {
		this(weight,0);
	}

	@Override
	public int getAlignX() {
		return super.getAlignX();
	}
	
}