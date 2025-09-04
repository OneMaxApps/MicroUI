package microui.component;

import static java.util.Objects.requireNonNull;
import static microui.constants.AutoResizeMode.BIG;

import microui.core.AbstractButton;
import microui.core.style.Color;

public class Button extends AbstractButton {
	
	public Button(String text, float x, float y, float w, float h) {
		super(x, y, w, h);
		
		this.text.set(requireNonNull(text,"text cannot be null"));
		this.text.setAutoResizeEnabled(true);
		this.text.setAutoResizeMode(BIG);
		this.text.setColor(new Color(0));
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
		text.draw();
	}

	public void setStyle(Button otherButton) {
		super.setStyle(otherButton);
		text.set(otherButton.text);
	}

	@Override
	protected void onChangeBounds() {
		super.onChangeBounds();
		if (text == null) { return; }
		text.setBoundsState(this);
	}
}