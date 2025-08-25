package microui.component;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static microui.event.EventType.CLICK;
import static processing.core.PConstants.CENTER;

import microui.constants.AutoResizeMode;
import microui.core.AbstractButton;
import microui.core.style.Color;

public class CheckBox extends AbstractButton {
	private boolean isSelected;
	private final Color markColor;
	private float textIndent;
	
	public CheckBox(float x, float y, float w, float h) {
		super(x, y, w, h);
		Color tmpColor = new Color();
		
		tmpColor.set(100);
		setStrokeColor(tmpColor);
		
		color.set(200);
		
		callback.addListener(CLICK, () -> isSelected = !isSelected);

		markColor = new Color(0);
		
		tmpColor.set(200);
		
		text.setColor(tmpColor);
		text.setAutoResizeState(true);
		text.setAutoResizeMode(AutoResizeMode.BIG);
		text.setInCenter(false);
		text.onClick(() -> toggle());
		
		setTextIndent(w*.3f);
	}

	public CheckBox(boolean isSelected) {
		this(0,0,0,0);
		
		final int minSize = 20;
		setSize(max(minSize,app.width+app.height)*.02f);
		setPosition(app.width/2-getSize()/2,app.height/2-getSize()/2);	
		
		setTextIndent(w*.3f);
	
		setSelected(isSelected);
	}

	public CheckBox() {
		this(false);
	}

	@Override
	protected void update() {
		super.update();
		text.draw();
		if (isSelected) {
			markOnDraw();
		}
	}
	
	@Override
	public void onChangeBounds() {
		super.onChangeBounds();
		if(text != null) {
			recalcTextBounds();
		}
	}

	public float getFullWidth() {
		if(text == null) return super.getWidth();
		return super.getWidth()+text.getWidth();
	}

	public final boolean isSelected() {
		return isSelected;
	}

	public final void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public final void toggle() {
		isSelected = !isSelected;
	}

	public final Color getMarkColor() {
		return new Color(markColor);
	}
	
	public final void setMarkColor(Color color) {
		markColor.set(color);
	}
	
	@Override
	public void setText(String text) {
		super.setText(text);
		recalcTextBounds();
	}
	
	public final float getTextIndent() {
		return textIndent;
	}

	public final void setTextIndent(float textIndent) {
		this.textIndent = max(0, textIndent);
		recalcTextBounds();
	}
	
	public final void setTextWidth(float width) {
		text.setWidth(width);
	}
	
	public final float getTextWidth(){
		return text.getWidth();
	}

	private void markOnDraw() {
		app.pushStyle();
		app.strokeWeight((min(w,h)/5)+1);
		app.stroke(markColor.get());
		app.noFill();
		app.rectMode(CENTER);
		app.rect(x + w / 2, y + h / 2, w / 2, h / 2);
		app.popStyle();
	}
	
	private final void recalcTextBounds() {
		text.setBounds(this);
		text.setX(getX()+getWidth()+textIndent);
		text.setWidth(app.textWidth(text.get()));
	}
	
	
}
