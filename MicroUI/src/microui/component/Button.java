package microui.component;

import static microui.constants.AutoResizeMode.BIG;

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
		this(text, app.width * .3f, app.height * .45f, app.width * .4f, app.height * .1f);
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
		if (text != null) { text.setBounds(this); }
	}
}