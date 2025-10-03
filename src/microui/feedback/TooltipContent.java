package microui.feedback;

import microui.core.base.SpatialView;

public abstract class TooltipContent extends SpatialView {
	private Tooltip tooltip;
	
	public TooltipContent() {
		super();
		setConstrainDimensionsEnabled(false);
		setNegativeDimensionsEnabled(false);

	}
	
	public abstract boolean isPrepared();

	protected final Tooltip getTooltip() {
		if(tooltip == null) {
			throw new IllegalStateException("tooltip is not initialized");
		}
		return tooltip;
	}

	public final void setTooltip(Tooltip tooltip) {
		if(tooltip == null) {
			throw new NullPointerException("the tooltip cannot be null");
		}
		this.tooltip = tooltip;
	}
	
}