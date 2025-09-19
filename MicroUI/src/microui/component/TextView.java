package microui.component;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.CORNER;

import microui.constants.AutoResizeMode;
import microui.core.base.Component;
import microui.core.style.Color;
import microui.core.style.theme.ThemeManager;
import processing.core.PFont;

public final class TextView extends Component {
	private static final String DEFAULT_TEXT = "";
	private final Color textColor;
	private PFont font;
	private String text;
	private AutoResizeMode autoResizeMode;
	private float textSize;
	private boolean isAutoResizeModeEnabled,isCenterModeEnabled;
	
	public TextView(String text, float x, float y, float width, float height) {
		super(x, y, width, height);
		setMinSize(10);
		setMaxSize(100,40);
		
		textColor = new Color(ThemeManager.getTheme().getTextViewColor());
		getMutableBackgroundColor().set(0,0);
		
		setText(text);
		setTextSize(max(1, min(width, height)));
		setAutoResizeModeEnabled(true);
		setAutoResizeMode(AutoResizeMode.BIG);
		setCenterModeEnabled(true);
	}

	public TextView(float x, float y, float width, float height) {
		this(DEFAULT_TEXT, x, y, width, height);
	}

	public TextView(String text) {
		this(ctx.width * .4f, ctx.height * .45f, ctx.width * .2f, ctx.height * .1f);
		setText(text);
	}

	public TextView() {
		this(DEFAULT_TEXT);
	}

	@Override
	protected void render() {
		getMutableBackgroundColor().apply();
		ctx.rect(getPadX(), getPadY(), getPadWidth(), getPadHeight());
		
		if(text.isEmpty()) { return; }
		

		if (font != null) {
			ctx.textFont(font);
		}
		
		ctx.textAlign(isCenterModeEnabled ? CENTER : CORNER,CENTER);
		
		if(isAutoResizeModeEnabled()) {
			ctx.textSize(max(1,min(getWidth(),getHeight())/getAutoResizeMode().getValue()));
		} else {
			ctx.textSize(getTextSize());
		}
		textColor.apply();
		ctx.text(text, getX(), getY(), getWidth(), getHeight());
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		if (textSize <= 0) {
			throw new IllegalArgumentException("text size cannot be equal to zero and lower");
		}
		this.textSize = textSize;
	}

	public PFont getFont() {
		return font;
	}

	public void setFont(PFont font) {
		if (font == null) {
			throw new IllegalArgumentException("font cannot be null");
		}
		this.font = font;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text == null) {
			throw new IllegalArgumentException("text cannot be null");
		}
		this.text = text;
	}

	public boolean isAutoResizeModeEnabled() {
		return isAutoResizeModeEnabled;
	}

	public void setAutoResizeModeEnabled(boolean isAutoResizeModeEnabled) {
		this.isAutoResizeModeEnabled = isAutoResizeModeEnabled;
	}

	public AutoResizeMode getAutoResizeMode() {
		return autoResizeMode;
	}

	public void setAutoResizeMode(AutoResizeMode autoResizeMode) {
		this.autoResizeMode = autoResizeMode;
	}

	public boolean isCenterModeEnabled() {
		return isCenterModeEnabled;
	}

	public void setCenterModeEnabled(boolean isEnabled) {
		this.isCenterModeEnabled = isEnabled;
	}

	public Color getTextColor() {
		return new Color(textColor);
	}
	
	public void setTextColor(Color color) {
		textColor.set(color);
	}
}