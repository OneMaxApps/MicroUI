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
	
	public LinearLayout(boolean isVerticalMode) {
		super();
		setVerticalMode(isVerticalMode);
	}
	
	public LinearLayout() {
		this(true);
	}
	
	@Override
	protected void checkCorrectParams(LayoutParams layoutParams) {
		if (!(layoutParams instanceof LinearLayoutParams)) {
			throw new IllegalArgumentException("using not correct layout params for LinearLayoutParams");
		}
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