package microui.component;

import static java.util.Objects.requireNonNull;
import static microui.constants.AutoResizeMode.BIG;

import microui.core.AbstractButton;
import microui.core.style.Color;

public class Button extends AbstractButton {
	
	public Button(String text, float x, float y, float w, float h) {
		super(x, y, w, h);
		setText(text);
		getMutableText().setAutoResizeEnabled(true);
		getMutableText().setAutoResizeMode(BIG);
		setTextColor(new Color(0));
	}

	public Button(float x, float y, float w, float h) {
		this("", x, y, w, h);
	}

	public Button(String text) {
		this(requireNonNull(text,"text cannot be null"), ctx.width * .3f, ctx.height * .45f, ctx.width * .4f, ctx.height * .1f);
	}

	public Button() {
		this("BUTTON");
	}

	@Override
	protected void update() {
		super.update();
		getMutableText().draw();
	}

	public void setStyle(Button otherButton) {
		super.setStyle(requireNonNull(otherButton,"other button cannot be null"));
		getMutableText().set(requireNonNull(otherButton.getMutableText(),"text from other button cannot be null"));
	}

	@Override
	protected void onChangeBounds() {
		super.onChangeBounds();
		if (getMutableText() == null) { return; }
		getMutableText().setBoundsState(this);
	}
}