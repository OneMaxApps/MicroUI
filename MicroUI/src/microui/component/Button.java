package microui.component;

import microui.core.AbstractButton;
import microui.util.Constants;

public class Button extends AbstractButton {
	public final TextView text;

	public Button(String plainText, float x, float y, float w, float h) {
		super(x, y, w, h);
		
		text = new TextView(plainText, x, y, w, h);
		text.setUpperCaseStyle(true);
		text.setAutoResize(true);
		text.setAutoResizeMode(Constants.AUTO_RESIZE_MODE_BIG);
		
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
	public void update() {
		super.update();
		text.draw();
	}

	public void setStyle(Button otherButton) {
		super.setStyle(otherButton);
		text.set(otherButton.text);
	}

	@Override
	public void inTransforms() {
		super.inTransforms();
		if (text != null) { text.setTransforms(this); }
	}

}