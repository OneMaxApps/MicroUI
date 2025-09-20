package microui.core.style.theme;

import microui.core.style.Color;

public class ThemeGray extends AbstractTheme {

	@Override
	public Color getBackgroundColor() {
		return new Color(124);
	}

	@Override
	public Color getTextViewColor() {
		return new Color(12);
	}

	@Override
	public Color getButtonTextColor() {
		return new Color(32);
	}

	@Override
	public Color getStrokeColor() {
		return new Color(128);
	}

	@Override
	public Color getHoverColor() {
		return new Color(132,128);
	}

	@Override
	public Color getRipplesColor() {
		return new Color(200,128);
	}

	@Override
	public Color getPrimaryColor() {
		return new Color(64);
	}

	@Override
	public Color getEditableTextColor() {
		return new Color(0);
	}

	@Override
	public Color getSelectColor() {
		return new Color(0,200,255,32);
	}

	@Override
	public Color getCursorColor() {
		return new Color(0);
	}

	@Override
	public Color getEditableBackgroundColor() {
		return new Color(232);
	}

}
