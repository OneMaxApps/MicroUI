package microui.core.style;

public class ThemeBlack extends AbstractTheme {

	@Override
	public Color getBackgroundColor() {
		return new Color(64);
	}

	@Override
	public Color getTextViewColor() {
		return new Color(254);
	}

	@Override
	public Color getButtonTextColor() {
		return new Color(254);
	}

	@Override
	public Color getStrokeColor() {
		return new Color(0);
	}

	@Override
	public Color getHoverColor() {
		return new Color(0, 80, 200, 24);
	}

	@Override
	public Color getRipplesColor() {
		return new Color(150, 200, 255);
	}

	@Override
	public Color getPrimaryColor() {
		return new Color(232,128);
	}

	@Override
	public Color getEditableTextColor() {
		return new Color(254);
	}

	@Override
	public Color getSelectColor() {
		return new Color(0, 0, 154, 32);
	}
	
	@Override
	public Color getCursorColor() {
		return new Color(255,232);
	}
	
	@Override
	public Color getEditableBackgroundColor() {
		return new Color(128);
	}
}