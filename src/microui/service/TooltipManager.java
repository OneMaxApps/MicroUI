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
			tooltip.setPosition(getCorrectPositionX(), getCorrectPositionY());
			tooltip.draw();
		}
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

	private float getCorrectPositionX() {
		return constrain(ctx.mouseX + LEFT_PADDING, 0, ctx.width - tooltip.getWidth());
	}
	
	private float getCorrectPositionY() {
		return constrain(ctx.mouseY, 0, ctx.height - tooltip.getHeight());
	}

	private static float constrain(float value, float min, float max) {
		return value < min ? min : value > max ? max : value;
	}
}