package microui.component;

import static java.util.Objects.requireNonNull;
import static microui.constants.AutoResizeMode.BIG;

import microui.core.AbstractButton;
import microui.core.style.Color;
import processing.core.PFont;

public class Button extends AbstractButton {
	private final TextView text;

	public Button(String text, float x, float y, float w, float h) {
		super(x, y, w, h);
		setMinSize(80, 30);
		
		this.text = new TextView(x, y, w, h);
		this.text.setAutoResizeEnabled(true);
		this.text.setAutoResizeMode(BIG);
		setTextColor(new Color(0));
		setText(text);
	}

	public Button(float x, float y, float w, float h) {
		this("", x, y, w, h);
	}

	public Button(String text) {
		this(requireNonNull(text, "text cannot be null"), ctx.width * .3f, ctx.height * .45f, ctx.width * .4f,
				ctx.height * .1f);
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
		super.setStyle(requireNonNull(otherButton, "other button cannot be null"));
		text.set(requireNonNull(otherButton.text, "text from other button cannot be null"));
	}

	public final String getText() {
		return text.get();
	}

	public void setText(String text) {
		this.text.set(requireNonNull(text, "text cannot be null"));
	}

	public final PFont getFont() {
		return text.getFont();
	}

	public final void setFont(PFont font) {
		text.setFont(requireNonNull(font, "font cannot be null"));
	}

	public final Color getTextColor() {
		return text.getColor();
	}

	public final void setTextColor(Color color) {
		text.setColor(requireNonNull(color, "color cannot be null"));
	}

	public final boolean isTextVisible() {
		return text.isVisible();
	}

	public final void setTextVisible(boolean isVisible) {
		text.setVisible(isVisible);
	}

	@Override
	protected void onChangeBounds() {
		super.onChangeBounds();
		text.setBoundsProperty(this);
	}
}