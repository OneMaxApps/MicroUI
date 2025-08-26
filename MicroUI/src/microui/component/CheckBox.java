package microui.component;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static processing.core.PConstants.PROJECT;

import microui.container.EdgeContainer;
import microui.core.AbstractButton;
import microui.core.base.Bounds;
import microui.core.style.Color;
import microui.event.Listener;

public class CheckBox extends AbstractButton {
	private static final int DEFAULT_BOX_SIZE = 16;
	private static final int DEFAULT_TEXT_PADDING = 8;
	
	private final EdgeContainer container;
	private float textPadding;
	private boolean isSelected;
	
	public CheckBox(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		setTextPadding(DEFAULT_TEXT_PADDING);
		
		container = new EdgeContainer(x,y,w,h);
		container.setLeft(true);
		container.set(new Content(this));
		container.setVisible(false);
		
	}

	public CheckBox(boolean isSelected) {
		this(app.width*.3f,app.height*.4f,app.width*.4f,app.height*.2f);
		
		setSelected(isSelected);
	}
	
	public CheckBox() {
		this(false);
	}
	
	@Override
	protected void update() {
		container.draw();
	}

	@Override
	protected void onChangeBounds() {
		super.onChangeBounds();
		if(container == null) { return; }
		
		container.setBounds(this);
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
		return new Color(getBox().markColor.get());
	}
	
	public final void setMarkColor(Color color) {
		if(color == null) {
			throw new NullPointerException("the mark color must be not null");
		}
		
		getBox().markColor.set(color);
	}
	
	public final void onStateChangedListener(Listener listener) {
		if(listener == null) {
			throw new NullPointerException("on the state changed listener must be not null");
		}
		onClick(listener);
	}
	
	public final float getTextPadding() {
		return textPadding;
	}

	public final void setTextPadding(float textPadding) {
		this.textPadding = max(0, textPadding);
	}

	private Box getBox() {
		return ((Content) (container.getElement())).box;
	}
	

	private final class Content extends Bounds {
	    final Box box;
		
		public Content(Bounds otherBounds) {
			super(otherBounds);
			setVisible(true);
			
			setHeight(DEFAULT_BOX_SIZE);
			
			box = new Box();

			text.setAutoResizeEnabled(false);
			text.setTextSize(box.getHeight());
			text.setInCenter(false);
			
		}

		@Override
		protected void update() {
			box.draw();
			text.draw();
		}

		@Override
		protected void onChangeBounds() {
			if(box == null || text == null) { return; }
			
			box.setPosition(x,y);
			box.setSize(min(DEFAULT_BOX_SIZE,w),min(DEFAULT_BOX_SIZE,h));
			text.setSize(getWidth()-(box.getWidth()+textPadding),box.getHeight());
			text.setPosition(box.getX()+box.getWidth()+textPadding,box.getY()+box.getHeight()/2-text.getHeight()/2);
			text.setTextSize(box.getHeight());
		}

	}
	
	private final class Box extends Bounds {
		final Color markColor;
		
		public Box() {
			super(0,0,DEFAULT_BOX_SIZE,DEFAULT_BOX_SIZE);
			setVisible(true);
			
			callback.setListener(this);
			
			ripples.setBounds(this);
			
			hover.setAlternativeBounds(this);
			
			markColor = new Color(0,200,255,100);
			
			onClick(() -> toggle());
		}

		@Override
		protected void update() {
			event.listen(this);
			
			app.pushStyle();
			stroke.apply();
			color.apply();
			app.rect(x, y, w, h);
			hover.draw();
			ripples.draw();
			app.popStyle();
			
			if(isSelected) {
				markOnDraw();
			}
		}

		private void markOnDraw() {
			app.pushStyle();
			app.noStroke();
			markColor.apply();
			app.rect(x,y,w,h);
			app.stroke(0,128);
			app.strokeWeight(max(1,DEFAULT_BOX_SIZE/5));
			app.strokeCap(PROJECT);
			app.line(x+w*.3f, y+h*.6f, x+w/2, y+h*.8f);
			app.line(x+w*.8f, y+h*.2f, x+w/2, y+h*.8f);
			app.popStyle();
		}
	}
}