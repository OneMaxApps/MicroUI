package microui.service;

import microui.core.style.Color;
import microui.feedback.TooltipOld;

public final class GlobalTooltipOld {
	public static final Color DEFAULT_COLOR = new Color(255,230,200,255);
	public static final int DEFAULT_TEXT_SIZE = 14;
	
	private static TooltipOld tooltip;
	
	public static final void onDraw(TooltipOld tooltip) {
		GlobalTooltipOld.tooltip = tooltip;
	}
	
	
	/**
	 * method draw is must be called in the end of main method draw() in PApplet
	 */
	public static final void draw() {
		if(tooltip != null) {
			tooltip.draw();
		}
	}
}