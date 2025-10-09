package microui.core.style.theme;

import microui.core.style.AbstractColor;
import microui.core.style.Color;

public class ThemeBlack extends AbstractTheme {

	@Override
	public AbstractColor getBackgroundColor() {
		return new Color(32);
	}

	@Override
	public AbstractColor getTextViewColor() {
		return new Color(200);
	}

	@Override
	public AbstractColor getButtonTextColor() {
		return new Color(164);
	}

	@Override
	public AbstractColor getStrokeColor() {
		return new Color(0);
	}

	@Override
	public AbstractColor getHoverColor() {
		return new Color(200,24);
	}

	@Override
	public AbstractColor getRipplesColor() {
		return new Color(100,10);
	}

	@Override
	public AbstractColor getPrimaryColor() {
		return new Color(232,128);
	}

	@Override
	public AbstractColor getEditableTextColor() {
		return new Color(254);
	}

	@Override
	public AbstractColor getSelectColor() {
		return new Color(0, 0, 154, 32);
	}
	
	@Override
	public AbstractColor getCursorColor() {
		return new Color(255,232);
	}
	
	@Override
	public AbstractColor getEditableBackgroundColor() {
		return new Color(128);
	}

	@Override
	public AbstractColor getTooltipBackgroundColor() {
		return new Color(0,200);
	}

	@Override
	public AbstractColor getTooltipTextColor() {
		return new Color(255);
	}
	
	
}