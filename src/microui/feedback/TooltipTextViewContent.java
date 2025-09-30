package microui.feedback;

import static microui.core.style.theme.ThemeManager.getTheme;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.TOP;

import microui.core.style.Color;

public final class TooltipTextViewContent extends TooltipContent {
	private static final int PADDING_AROUND = 4;
	private static final int DEFAULT_TEXT_SIZE = 12;
	private final Color textColor;
	private String text;
	private int textSize;

	public TooltipTextViewContent(String text) {
		super();
		setVisible(true);
		
		textColor = getTheme().getTooltipTextColor();
		setText(text);
		setTextSize(DEFAULT_TEXT_SIZE);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text == null) {
			throw new NullPointerException("the text for tooltip cannot be null");
		}
		this.text = text;
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		if (textSize < 4) {
			throw new IllegalArgumentException("text size cannot be lower than 4");
		}
		this.textSize = textSize;
	}

	public Color getTextColor() {
		return new Color(textColor);
	}

	public void setTextColor(Color color) {
		textColor.set(color);
	}

	@Override
	public boolean isPrepared() {
		if(text.isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	protected void render() {
		getMutableBackgroundColor().apply();
		ctx.rect(getX() - PADDING_AROUND, getY() - PADDING_AROUND, getWidth() + PADDING_AROUND * 2, getHeight() + PADDING_AROUND * 2);
		textColor.apply();
		ctx.textSize(textSize);
		ctx.textAlign(LEFT, TOP);
		ctx.text(text, getX(), getY());
	}
	
}