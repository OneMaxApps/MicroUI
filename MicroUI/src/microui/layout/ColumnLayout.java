package microui.layout;

import microui.core.base.Component;
import microui.core.base.Container.ComponentEntry;

public final class ColumnLayout extends LayoutManager {
	private static final float EPSILON = .01f;
	private static final float TOTAL_WEIGHT = 1.0f;

	
	
	@Override
	public void onAddComponent(ComponentEntry componentEntry) {
		super.onAddComponent(componentEntry);
		
		float usedWeight = 0;
		
		for (ComponentEntry entry : getComponentEntryList()) {
			ColumnLayoutParams params = (ColumnLayoutParams) entry.getLayoutParams();
			usedWeight += params.getWeight();
		}
		
		if(usedWeight-TOTAL_WEIGHT > EPSILON) {
			throw new IllegalStateException("weight limit out of bounds in ColumnLayout");
		}
	}

	@Override
	public void recalculate() {
		float usedWeight = 0;
		
		float containerContentX = getContainer().getContentX();
		float containerContentY = getContainer().getContentY();
		float containerContentW = getContainer().getContentWidth();
		float containerContentH = getContainer().getContentHeight();
		
		for (ComponentEntry entry : getComponentEntryList()) {
			Component component = entry.getComponent();
			ColumnLayoutParams params = (ColumnLayoutParams) entry.getLayoutParams();

			float usedHeight = containerContentH*usedWeight;

			switch (getContainer().getContainerMode()) {

			case IGNORE_CONSTRAINTS:
				component.setConstrainDimensionsEnabled(false);
				component.setAbsolutePosition(containerContentX, containerContentY+usedHeight);
				component.setAbsoluteWidth(containerContentW);
				component.setAbsoluteHeight(containerContentH*params.getWeight());
				
				break;
				
			case RESPECT_CONSTRAINTS:
				float alignXLeft = containerContentX;
				float alignXCenter = containerContentX+containerContentW/2-component.getAbsoluteWidth()/2;
				float alignXRight = containerContentX+containerContentW-component.getAbsoluteWidth();
				
				component.setAbsoluteX(params.getAlignX() == -1 ? alignXLeft : params.getAlignX() == 1 ? alignXRight : alignXCenter);
				component.setAbsoluteY(containerContentY+usedHeight);
				component.setAbsoluteWidth(containerContentW);
				component.setAbsoluteHeight(containerContentH*params.getWeight());
				
				break;

			}
			
			usedWeight += params.getWeight();
		}

	}

	@Override
	public void debugOnDraw() {
		ctx.pushStyle();
		ctx.stroke(0);
		ctx.noFill();
		
		getComponentEntryList().forEach(entry -> {
			Component component = entry.getComponent();
			ColumnLayoutParams params = (ColumnLayoutParams) entry.getLayoutParams();	
			ctx.rect(getContainer().getContentX(),component.getAbsoluteY(),getContainer().getContentWidth(),getContainer().getContentHeight()*params.getWeight());
		});
		
		ctx.popStyle();
	}

	@Override
	protected void checkCorrectParams(LayoutParams layoutParams) {
		if (!(layoutParams instanceof ColumnLayoutParams)) {
			throw new IllegalArgumentException("using not correct layout params for ColumnLayout");
		}
	}

}
