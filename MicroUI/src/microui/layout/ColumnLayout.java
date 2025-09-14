package microui.layout;

import microui.layout.params.ColumnLayoutParams;
import microui.layout.params.LayoutParams;

public final class ColumnLayout extends AbstractLinearLayout {

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