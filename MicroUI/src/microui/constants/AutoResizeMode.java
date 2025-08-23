package microui.constants;

public enum AutoResizeMode {
	FULL(1),
	BIG(2),
	MIDDLE(3),
	SMALL(4),
	TINY(5);
	
	private final int scale;

	private AutoResizeMode(int scale) {
		this.scale = scale;
	}
	
	public final int getScale() {
		return scale;
	}
	
}