package microui.service;

import microui.core.base.View;
import microui.feedback.Tooltip;

public final class TooltipManager extends View {
	private static final TooltipManager INSTANCE = new TooltipManager();
	private static Tooltip tooltip;
	private static final int LEFT_PADDING = 14;

	private TooltipManager() {
		super();
		setVisible(true);
	}

	@Override
	protected void render() {
		if (tooltip != null && tooltip.isVisible()) {
			tooltip.setPosition(getCorrectTooltipPositionX(), ctx.mouseY);
			tooltip.draw();
		}
	}

	public static Tooltip getTooltip() {
		return tooltip;
	}

	public static void setTooltip(Tooltip tooltip) {
		if (tooltip == null) {
			throw new NullPointerException("the tooltip cannot be null");
		}
		TooltipManager.tooltip = tooltip;
	}

	public static TooltipManager getInstance() {
		return INSTANCE;
	}

	private float getCorrectTooltipPositionX() {
		return constrain(ctx.mouseX + LEFT_PADDING, 0, ctx.width - tooltip.getWidth());
	}

	private static final float constrain(float value, float min, float max) {
		return value < min ? min : value > max ? max : value;
	}
}