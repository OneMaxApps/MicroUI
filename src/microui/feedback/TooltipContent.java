package microui.feedback;

import microui.core.base.SpatialView;
import microui.core.style.Color;
import microui.core.style.theme.ThemeManager;

public abstract class TooltipContent extends SpatialView {
	private final Color backgroundColor;
	
	public TooltipContent() {
		super();
		setConstrainDimensionsEnabled(false);
		setNegativeDimensionsEnabled(false);
		backgroundColor = ThemeManager.getTheme().getTooltipBackgroundColor();
		
	}

	public final Color getBackgroundColor() {
		return new Color(backgroundColor);
	}
	
	public final void setBackgroundColor(Color color) {
		backgroundColor.set(color);
	}
	
	public abstract boolean isPrepared();

	protected Color getMutableBackgroundColor() {
		return backgroundColor;
	}
}