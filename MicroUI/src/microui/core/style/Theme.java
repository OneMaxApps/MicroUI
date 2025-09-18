package microui.core.style;

public final class Theme {
	private static final Color defaultButtonColor = new Color(200);
	private static final Color defaultTextViewColor = new Color(0);
	private static final Color defaultHoverColor = new Color(0,100,255,24);
	private static final Color defaultRipplesColor = new Color(200,255,255);
	private static final Color defaultStrokeColor = new Color(0);
	private static final Color defaultContainerColor = new Color(0,0);
	
	private Theme() {}

	public static final Color getDefaultButtonColor() {
		return defaultButtonColor;
	}

	public static final Color getDefaultTextViewColor() {
		return defaultTextViewColor;
	}

	public static final Color getDefaultHoverColor() {
		return defaultHoverColor;
	}

	public static final Color getDefaultRipplesColor() {
		return defaultRipplesColor;
	}
	
	public static final Color getDefaultStrokeColor() {
		return defaultStrokeColor;
	}
	
	public static final Color getDefaultContainerColor() {
		return defaultContainerColor;
	}
}