package microui.feedback;

import microui.core.base.SpatialView;

public abstract class TooltipContent extends SpatialView {

	public TooltipContent() {
		super();
		setConstrainDimensionsEnabled(false);
		setNegativeDimensionsEnabled(false);

	}
	
	public abstract boolean isPrepared();
	
}