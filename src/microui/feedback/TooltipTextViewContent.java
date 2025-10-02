package microui.feedback;

import static java.lang.Math.max;
import static microui.core.style.theme.ThemeManager.getTheme;

import microui.component.TextView;

public final class TooltipTextViewContent extends TooltipContent {
	private static final int DEFAULT_TEXT_SIZE = 12;
	private final TextView textView;
	private boolean isDirtySize;
	public TooltipTextViewContent(String text) {
		super();
		setVisible(true);
		setConstrainDimensionsEnabled(true);
		setMinMaxSize(8,8,ctx.width,ctx.height);
		
		textView = new TextView(text);
		textView.setConstrainDimensionsEnabled(false);
		textView.setBackgroundColor(getTheme().getTooltipBackgroundColor());
		textView.setTextColor(getTheme().getTooltipTextColor());
		textView.setAutoResizeModeEnabled(false);
		textView.setTextSize(DEFAULT_TEXT_SIZE);
		textView.setCenterModeEnabled(false);
		textView.setPadding(4,5);
		textView.setClipModeEnabled(false);

		isDirtySize = true;
	}
	
	@Override
	public boolean isPrepared() {
		if(textView.getText().isEmpty()) {
			return false;
		}
		prepareSize();
		
		return true;
	}
	
	public String getText() {
		return textView.getText();
	}
	
	public void setText(String text) {
		textView.setText(text);
		isDirtySize = true;
	}
	
	public float getTextSize() {
		return textView.getTextSize();
	}
	
	public void setTextSize(float textSize) {
		textView.setTextSize(textSize);
		isDirtySize = true;
	}
	
	@Override
	protected void render() {
		textView.draw();
	}

	@Override
	protected void onChangePositions() {
		super.onChangePositions();
		
		textView.setPositionFrom(this);
	}
	
	private void prepareSize() {
		if(!isDirtySize) { return; }
		
		final String[] lines = textView.getText().split("\n");
		
		float maxTextWidth = 0;
		
		ctx.pushStyle();
		ctx.textSize(textView.getTextSize());
		if(textView.getFont() != null) {
			ctx.textFont(textView.getFont());
		}
		for(int i = 0; i < lines.length; i++) {
			maxTextWidth = max(maxTextWidth, ctx.textWidth(lines[i]));
		}
		
		float totalHeight = (ctx.textAscent()+ctx.textDescent())*(lines.length);
			  
		for(int i = 0; i < lines.length; i++) {
			totalHeight += ctx.textDescent();
		}
		
		ctx.popStyle();
		
		textView.setSize(maxTextWidth,totalHeight);
		
		isDirtySize = false;
	}
}