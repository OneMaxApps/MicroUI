package microUI.services;

import microUI.feedback.Tooltip;
import microUI.graphics.Color;

public final class GlobalTooltip {
	public static final Color DEFAULT_COLOR = new Color(255,230,200,255);
	public static final int DEFAULT_TEXT_SIZE = 18;
	
	private static Tooltip tooltip;
	
	public static final void onDraw(Tooltip tooltip) {
		GlobalTooltip.tooltip = tooltip;
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