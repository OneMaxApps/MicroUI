package microui.layout.params;

public class LinearLayoutParams extends LinearAxisLayoutParams {

	public LinearLayoutParams(float weight, int alignX, int alignY) {
		super(weight, alignX, alignY);
	}
	
	public LinearLayoutParams(float weight, int align) {
		this(weight, align, align);
	}

	@Override
	public int getAlignX() {
		return super.getAlignX();
	}
	
	@Override
	public int getAlignY() {
		return super.getAlignY();
	}
	
}