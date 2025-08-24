package microui.component;

import static microui.constants.AutoResizeMode.BIG;

import microui.core.AbstractButton;
import microui.core.style.Color;
import processing.core.PFont;

public class Button extends AbstractButton {
	protected final TextView text;

	public Button(String plainText, float x, float y, float w, float h) {
		super(x, y, w, h);
		
		text = new TextView(plainText, x, y, w, h);
		text.setUpperCaseStyle(true);
		text.setAutoResizeState(true);
		text.setAutoResizeMode(BIG);
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
	public void onChangeBounds() {
		super.onChangeBounds();
		if (text != null) { text.setBounds(this); }
	}
	
	public final String getText() {
		return text.get();
	}
	
	public final void setText(String text) {
		this.text.set(text);
	}
	
	public final PFont getFont() {
		return text.getFont();
	}
	
	public final void setFont(PFont font) {
		text.setFont(font);
	}
	
	public final Color getTextColor() {
		return text.getColor();
	}
	
	public final void setTextColor(Color color) {
		text.setColor(color);
	}
}