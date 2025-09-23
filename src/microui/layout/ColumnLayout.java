package microui.layout;

import microui.core.base.Container.ComponentEntry;

public final class ColumnLayout extends LinearAxisLayout {
	
	@Override
	public void onAddComponent(ComponentEntry componentEntry) {
		super.onAddComponent(componentEntry);
		if(isOutOfSpace()) {
			throw new IllegalStateException("weight limit out of bounds in ColumnLayout");
		}
	}

	public ColumnLayout() {
		super();
		setVerticalMode(true);
	}

	@Override
	protected void checkCorrectParams(LayoutParams layoutParams) {
		if (!(layoutParams instanceof ColumnLayoutParams)) {
			throw new IllegalArgumentException("using not correct layout params for ColumnLayout");
		}
	}

}