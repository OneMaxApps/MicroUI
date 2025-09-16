package microui.component;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static microui.constants.AutoResizeMode.SMALL;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.CORNER;

import microui.constants.AutoResizeMode;
import microui.core.base.Component;
import processing.core.PFont;

public final class TextView extends Component {
	private static final String DEFAULT_TEXT = "";
	private PFont font;
	private String text;
	private AutoResizeMode autoResizeMode;
	private float textSize;
	private boolean isAutoResizeModeEnabled,isCenterMode;
	
	public TextView(String text, float x, float y, float width, float height) {
		super(x, y, width, height);
		setVisible(true);
		setConstrainDimensionsEnabled(false);
		setNegativeDimensionsEnabled(false);
		setPaddingEnabled(true);
		setMarginEnabled(true);
		
		getMutableColor().set(0);
		
		setText(text);
		setTextSize(max(1, min(width, height)));
		setAutoResizeModeEnabled(true);
		setAutoResizeMode(SMALL);
		setCenterMode(true);
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
		if(text.isEmpty()) { return; }
		
		getMutableColor().apply();

		if (font != null) {
			ctx.textFont(font);
		}
		
		ctx.textAlign(isCenterMode ? CENTER : CORNER,CENTER);
		
		if(isAutoResizeModeEnabled()) {
			ctx.textSize(max(1,min(getWidth(),getHeight())/getAutoResizeMode().getValue()));
		} else {
			ctx.textSize(getTextSize());
		}
		
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

	public boolean isCenterMode() {
		return isCenterMode;
	}

	public void setCenterMode(boolean isCenterMode) {
		this.isCenterMode = isCenterMode;
	}
	
}