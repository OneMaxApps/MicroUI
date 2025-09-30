package microui.feedback;

import static microui.core.style.theme.ThemeManager.getTheme;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.TOP;

import microui.core.base.Component;
import microui.core.base.SpatialView;
import microui.core.style.Color;
import microui.service.TooltipManager;
import microui.util.Metrics;

public final class Tooltip extends SpatialView {
	private static final int PADDING_AROUND = 4;
	private final Color backgroundColor;
	private final TextViewInternal textView;

	public Tooltip(Component component) {
		super();
		setConstrainDimensionsEnabled(true);
		setMinMaxSize(4, 4, ctx.width / 2, 400);

		backgroundColor = getTheme().getTooltipBackgroundColor();

		textView = new TextViewInternal(this);

		component.onEnterLong(() -> {
			if (getText().isEmpty()) {
				return;
			}
			prepareContent();
			setVisible(true);
		});

		component.onLeave(() -> {
			setVisible(false);
		});

		Metrics.register(this);

	}

	public void listen() {
		if (isVisible()) {
			TooltipManager.setTooltip(this);
		}
	}

	public String getText() {
		return textView.getText();
	}

	public void setText(String text) {
		textView.setText(text);
	}

	public int getTextSize() {
		return textView.getTextSize();
	}

	public void setTextSize(int textSize) {
		textView.setTextSize(textSize);
	}

	public Color getTextColor() {
		return textView.getTextColor();
	}

	public void setTextColor(Color color) {
		textView.setTextColor(color);
	}

	@Override
	protected void render() {
		backgroundColor.apply();
		ctx.rect(getX() - PADDING_AROUND, getY() - PADDING_AROUND, getWidth() + PADDING_AROUND * 2, getHeight() + PADDING_AROUND * 2);
		textView.draw();
	}

	@Override
	protected void onChangeBounds() {
		super.onChangeBounds();
		textView.setBoundsFrom(this);
	}

	private void prepareContent() {
		prepareSize();
	}

	private void prepareSize() {
		setWidth(textView.getTextWidth());
		final int lines = (int) Math.ceil(textView.getTextWidth() / getWidth());
		final float newHeight = lines * textView.getTextHeight();
		setHeight(newHeight);
	}

	private final class TextViewInternal extends SpatialView {
		private static final String EMPTY_TEXT = "";
		private static final int DEFAULT_TEXT_SIZE = 12;
		private final Color textColor;
		private String text;
		private int textSize;

		public TextViewInternal(SpatialView spatialView) {
			super(spatialView);
			setVisible(true);

			textColor = getTheme().getTooltipTextColor();
			text = EMPTY_TEXT;
			textSize = DEFAULT_TEXT_SIZE;
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

		public float getTextWidth() {
			return getTextWidth(getText());
		}

		public float getTextWidth(String text) {
			float width = 0;

			ctx.pushStyle();
			ctx.textSize(textSize);
			width = ctx.textWidth(text);
			ctx.popStyle();

			return width;
		}

		public float getTextHeight() {
			float height = 0;
			ctx.pushStyle();
			ctx.textSize(textSize);
			height = ctx.textAscent() + ctx.textDescent();
			ctx.popStyle();

			return height;
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
		protected void render() {
			textColor.apply();
			ctx.textSize(textSize);
			ctx.textAlign(LEFT, TOP);
			ctx.text(text, getX(), getY());
		}

	}
}