package microui.layout.params;

public final class RowLayoutParams extends AbstractLinearLayoutParams {
	
	public RowLayoutParams(float weight, int alignY) {
		super(weight,0,alignY);
	}
	
	public RowLayoutParams(float weight) {
		this(weight,0);
	}

	@Override
	public int getAlignY() {
		return super.getAlignY();
	}

}