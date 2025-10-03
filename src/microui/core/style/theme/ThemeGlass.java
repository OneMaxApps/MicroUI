package microui.core.style.theme;

import microui.core.style.Color;

public class ThemeGlass extends AbstractTheme {

	@Override
	public Color getBackgroundColor() {
		return new Color(200,32);
	}

	@Override
	public Color getTextViewColor() {
		return new Color(255,232);
	}

	@Override
	public Color getButtonTextColor() {
		return new Color(200,200);
	}

	@Override
	public Color getStrokeColor() {
		return new Color(224,224,255,128);
	}

	@Override
	public Color getHoverColor() {
		return new Color(0,200,255,32);
	}

	@Override
	public Color getRipplesColor() {
		return new Color(132,132,154,32);
	}

	@Override
	public Color getPrimaryColor() {
		return new Color(0,0,164,64);
	}

	@Override
	public Color getEditableTextColor() {
		return new Color(232,232,254,232);
	}

	@Override
	public Color getSelectColor() {
		return new Color(100,100);
	}

	@Override
	public Color getCursorColor() {
		return new Color(200,200,232,232);
	}

	@Override
	public Color getEditableBackgroundColor() {
		return new Color(200,32);
	}
	
	@Override
	public Color getTooltipBackgroundColor() {
		return new Color(200,200,232,200);
	}

	@Override
	public Color getTooltipTextColor() {
		return new Color(0);
	}
	
}