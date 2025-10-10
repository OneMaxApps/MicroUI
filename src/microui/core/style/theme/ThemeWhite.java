package microui.core.style.theme;

import microui.core.style.AbstractColor;
import microui.core.style.Color;
import microui.core.style.GradientLoopColor;

public class ThemeWhite extends AbstractTheme {

	@Override
	public AbstractColor getBackgroundColor() {
		return new GradientLoopColor(new Color(232), new Color(232,232,255));
	}

	@Override
	public AbstractColor getTextViewColor() {
		return new Color(0);
	}
	
	@Override
	public AbstractColor getButtonTextColor() {
		return new Color(0);
	}

	@Override
	public AbstractColor getStrokeColor() {
		return new Color(0);
	}

	@Override
	public AbstractColor getHoverColor() {
		return new Color(100,200,255,24);
	}

	@Override
	public AbstractColor getRipplesColor() {
		return new Color(255);
	}

	@Override
	public AbstractColor getPrimaryColor() {
		return new Color(128);
	}

	@Override
	public AbstractColor getEditableTextColor() {
		return new Color(0);
	}

	@Override
	public AbstractColor getSelectColor() {
		return new Color(0,0,200,32);
	}

	@Override
	public AbstractColor getCursorColor() {
		return new Color(0,232);
	}

	@Override
	public AbstractColor getEditableBackgroundColor() {
		return new Color(255);
	}
	
	@Override
	public AbstractColor getTooltipBackgroundColor() {
		return new Color(255);
	}

	@Override
	public AbstractColor getTooltipTextColor() {
		return new Color(0);
	}
}