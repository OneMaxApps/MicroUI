package microui.layout;

import microui.core.base.Component;
import microui.core.base.Container.ComponentEntry;
import microui.layout.params.LayoutParams;
import microui.layout.params.LinearLayoutParams;

public abstract class AbstractLinearLayout extends LayoutManager {
	private static final float EPSILON = .01f;
	private static final float TOTAL_WEIGHT = 1.0f;
	private boolean isVerticalMode;
	
	@Override
	public void onAddComponent(ComponentEntry componentEntry) {
		super.onAddComponent(componentEntry);
		
		float usedWeight = 0;
		
		for (ComponentEntry entry : getComponentEntryList()) {
			LinearLayoutParams params = (LinearLayoutParams) entry.getLayoutParams();
			usedWeight += params.getWeight();
		}
		
		if(usedWeight-TOTAL_WEIGHT > EPSILON) {
			throw new IllegalStateException("weight limit out of bounds in ColumnLayout");
		}
	}
	
	@Override
	public void recalculate() {
		float usedWeight = 0;
		
		float containerX = getContainer().getContentX();
		float containerY = getContainer().getContentY();
		float containerW = getContainer().getContentWidth();
		float containerH = getContainer().getContentHeight();
		
		for (ComponentEntry entry : getComponentEntryList()) {
			Component component = entry.getComponent();
			LinearLayoutParams params = (LinearLayoutParams) entry.getLayoutParams();
			
			float usedSpace = isVerticalMode ? containerH*usedWeight : containerW*usedWeight;
			
			switch (getContainer().getContainerMode()) {

			case IGNORE_CONSTRAINTS:
				component.setConstrainDimensionsEnabled(false);
				component.setAbsolutePosition(isVerticalMode ? containerX : containerX+usedSpace, isVerticalMode ? containerY+usedSpace : containerY);
				component.setAbsoluteWidth(isVerticalMode ? containerW : containerW*params.getWeight());
				component.setAbsoluteHeight(isVerticalMode ? containerH*params.getWeight() : containerH);
				
			break;
				
			case RESPECT_CONSTRAINTS:
				float alignXLeft = containerX;
				float alignXCenter = containerX+containerW/2-component.getAbsoluteWidth()/2;
				float alignXRight = containerX+containerW-component.getAbsoluteWidth();
		
				float alignYTop = containerY;
				float alignYCenter = containerY+containerH/2-component.getAbsoluteHeight()/2;
				float alignYBottom = containerY+containerH-component.getAbsoluteHeight();

				component.setAbsoluteX(isVerticalMode ? params.getAlignX() == -1 ? alignXLeft : params.getAlignX() == 1 ? alignXRight : alignXCenter : containerX+usedSpace);
				component.setAbsoluteY(isVerticalMode ? containerY+usedSpace : params.getAlignY() == -1 ? alignYTop : params.getAlignY() == 1 ? alignYBottom : alignYCenter);
				component.setAbsoluteWidth(isVerticalMode ? containerW : containerW*params.getWeight());
				component.setAbsoluteHeight(isVerticalMode ? containerH*params.getWeight() : containerH);
				
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
			LinearLayoutParams params = (LinearLayoutParams) entry.getLayoutParams();	
			if(isVerticalMode) {
			ctx.rect(getContainer().getContentX(),component.getAbsoluteY(),getContainer().getContentWidth(),getContainer().getContentHeight()*params.getWeight());
			} else {
				ctx.rect(component.getAbsoluteX(),getContainer().getContentY(),getContainer().getContentWidth()*params.getWeight(),getContainer().getContentHeight());
			}
		});
		
		ctx.popStyle();
	}

	@Override
	protected void checkCorrectParams(LayoutParams layoutParams) {
		if (!(layoutParams instanceof LinearLayoutParams)) {
			throw new IllegalArgumentException("using not correct layout params for LinearLayout");
		}
	}

	protected boolean isVerticalMode() {
		return isVerticalMode;
	}

	protected void setVerticalMode(boolean isVerticalMode) {
		if(this.isVerticalMode == isVerticalMode) { return; }
		this.isVerticalMode = isVerticalMode;
		if(getContainer() != null) {
			recalculate();
		}
	}
	
}