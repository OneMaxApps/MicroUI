package microui.layout;

import microui.core.base.Component;
import microui.core.base.Container.ComponentEntry;

public final class RowLayout extends LayoutManager {
	private static final float EPSILON = .01f;
	private static final float TOTAL_WEIGHT = 1.0f;
	
	@Override
	public void onAddComponent(ComponentEntry componentEntry) {
		super.onAddComponent(componentEntry);
		
		float usedWeight = 0;
		
		for (ComponentEntry entry : getComponentEntryList()) {
			RowLayoutParams params = (RowLayoutParams) entry.getLayoutParams();
			usedWeight += params.getWeight();
		}
		
		if(usedWeight-TOTAL_WEIGHT > EPSILON) {
			throw new IllegalStateException("weight limit out of bounds in RowLayout");
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
			RowLayoutParams params = (RowLayoutParams) entry.getLayoutParams();

			float usedWidth = containerContentW*usedWeight;

			switch (getContainer().getContainerMode()) {

			case IGNORE_CONSTRAINTS:
				component.setConstrainDimensionsEnabled(false);
				component.setAbsolutePosition(containerContentX+usedWidth, containerContentY);
				component.setAbsoluteWidth(containerContentW*params.getWeight());
				component.setAbsoluteHeight(containerContentH);
				
				break;
				
			case RESPECT_CONSTRAINTS:
				float alignYTop = containerContentY;
				float alignYCenter = containerContentY+containerContentH/2-component.getAbsoluteHeight()/2;
				float alignYBottom = containerContentY+containerContentH-component.getAbsoluteHeight();
				
				component.setAbsoluteX(containerContentX+usedWidth);
				component.setAbsoluteY(params.getAlignY() == -1 ? alignYTop : params.getAlignY() == 1 ? alignYBottom : alignYCenter);
				component.setAbsoluteWidth(containerContentW*params.getWeight());
				component.setAbsoluteHeight(containerContentH);
				
				break;

			}
			
			usedWeight += params.getWeight();
		}
	}

	@Override
	public void debugOnDraw() {
		ctx.pushStyle();
		ctx.stroke(0);
		ctx.fill(200,0,0,32);
		
		getComponentEntryList().forEach(entry -> {
			Component component = entry.getComponent();
			RowLayoutParams params = (RowLayoutParams) entry.getLayoutParams();	
			ctx.rect(component.getAbsoluteX(),
					getContainer().getContentY(),
					getContainer().getContentWidth()*params.getWeight(),
					getContainer().getContentHeight());
		});
		
		ctx.popStyle();
	}

	@Override
	protected void checkCorrectParams(LayoutParams layoutParams) {
		if(!(layoutParams instanceof RowLayoutParams)) {
			throw new IllegalArgumentException("using not correct layout params for RowLayout");
		}
	}

}
