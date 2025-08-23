package microui.component;

import microui.constants.AutoResizeMode;
import microui.core.AbstractButton;

public class Button extends AbstractButton {
	protected final TextView text;

	public Button(String plainText, float x, float y, float w, float h) {
		super(x, y, w, h);
		
		text = new TextView(plainText, x, y, w, h);
		text.setUpperCaseStyle(true);
		text.setAutoResizeState(true);
		text.setAutoResizeMode(AutoResizeMode.BIG);
		
	}

	public Button(float x, float y, float w, float h) {
		this("", x, y, w, h);
	}

	public Button(String title) {
		this(title, app.width * .3f, app.height * .45f, app.width * .4f, app.height * .1f);
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
	public void onChangeBounds() {
		super.onChangeBounds();
		if (text != null) { text.setBounds(this); }
	}

	public final TextView getText() {
		return text;
	}
	
}