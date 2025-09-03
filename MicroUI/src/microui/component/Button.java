package microui.component;

import static microui.constants.AutoResizeMode.BIG;

import java.util.Objects;

import microui.core.AbstractButton;
import microui.core.style.Color;

public class Button extends AbstractButton {
	
	public Button(String plainText, float x, float y, float w, float h) {
		super(x, y, w, h);
		
		text.set(plainText);
		text.setUpperCaseStyle(true);
		text.setAutoResizeEnabled(true);
		text.setAutoResizeMode(BIG);
		text.setColor(new Color(0));
	}

	public Button(float x, float y, float w, float h) {
		this("", x, y, w, h);
	}

	public Button(String text) {
		this(Objects.requireNonNull(text,"text cannot be null"), cxt.width * .3f, cxt.height * .45f, cxt.width * .4f, cxt.height * .1f);
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