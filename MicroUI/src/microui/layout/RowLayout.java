package microui.layout;

import microui.layout.params.LayoutParams;
import microui.layout.params.RowLayoutParams;

public final class RowLayout extends AbstractLinearLayout {

	@Override
	protected void checkCorrectParams(LayoutParams layoutParams) {
		if(!(layoutParams instanceof RowLayoutParams)) {
			throw new IllegalArgumentException("using not correct layout params for RowLayout");
		}
	}

}