package microui.layout;

import microui.core.base.Container.ComponentEntry;

public class LinearLayout extends LinearAxisLayout {

	@Override
	public void onAddComponent(ComponentEntry componentEntry) {
		super.onAddComponent(componentEntry);
		if(isOutOfSpace()) {
			throw new IllegalStateException("weight limit out of bounds in LinearLayout");
		}
	}
	
	public LinearLayout() {
		super();
		setVerticalMode(true);
	}

	@Override
	public boolean isVerticalMode() {
		return super.isVerticalMode();
	}

	@Override
	public void setVerticalMode(boolean isVerticalMode) {
		super.setVerticalMode(isVerticalMode);
	}
	
}