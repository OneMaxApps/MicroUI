package microui.layout;

import microui.core.base.Container.ComponentEntry;
import microui.layout.params.LayoutParams;
import microui.layout.params.RowLayoutParams;

public final class RowLayout extends LinearAxisLayout {
	
	@Override
	public void onAddComponent(ComponentEntry componentEntry) {
		super.onAddComponent(componentEntry);
		if(isOutOfSpace()) {
			throw new IllegalStateException("weight limit out of bounds in RowLayout");
		}
	}
	
	@Override
	protected void checkCorrectParams(LayoutParams layoutParams) {
		if(!(layoutParams instanceof RowLayoutParams)) {
			throw new IllegalArgumentException("using not correct layout params for RowLayout");
		}
	}

}