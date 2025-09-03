package microui.component;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static processing.core.PConstants.PROJECT;

import java.util.Objects;

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
	private boolean isChecked;
	
	public CheckBox(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		setTextPadding(DEFAULT_TEXT_PADDING);
		
		container = new EdgeContainer(x,y,w,h);
		container.setLeft(true);
		container.set(new Content(this));
		container.setVisible(false);
		
	}

	public CheckBox(boolean isChecked) {
		this(cxt.width*.3f,cxt.height*.4f,cxt.width*.4f,cxt.height*.2f);
		
		setChecked(isChecked);
	}
	
	public CheckBox(final String text) {
		this(cxt.width*.3f,cxt.height*.4f,cxt.width*.4f,cxt.height*.2f);
		
		setText(text);
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

	public final boolean isChecked() {
		return isChecked;
	}

	public final void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	public final void toggle() {
		isChecked = !isChecked;
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
		
		public Content(Bounds bounds) {
			super(Objects.requireNonNull(bounds, "bounds cannot be null"));
			setVisible(true);
			
			setHeight(DEFAULT_BOX_SIZE);
			
			box = new Box();

			text.setColor(new Color(32));
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
			
			box.setPosition(getX(),getY());
			box.setSize(min(DEFAULT_BOX_SIZE,getWidth()),min(DEFAULT_BOX_SIZE,getHeight()));
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
			
			setCallbackListener(this);
			
			ripples.setBounds(this);
			
			hover.setAlternativeBounds(this);
			
			markColor = new Color(0,200,255,100);
			
			onClick(() -> toggle());
		}

		@Override
		protected void update() {
			setEventListener(this);
			
			cxt.pushStyle();
			stroke.apply();
			color.apply();
			cxt.rect(getX(), getY(), getWidth(), getHeight());
			hover.draw();
			ripples.draw();
			cxt.popStyle();
			
			if(isChecked) {
				markOnDraw();
			}
		}

		private void markOnDraw() {
			cxt.pushStyle();
			cxt.noStroke();
			markColor.apply();
			cxt.rect(getX(),getY(),getWidth(),getHeight());
			cxt.stroke(0,128);
			cxt.strokeWeight(max(1,DEFAULT_BOX_SIZE/5));
			cxt.strokeCap(PROJECT);
			cxt.line(getX()+getWidth()*.3f, getY()+getHeight()*.6f, getX()+getWidth()/2, getY()+getHeight()*.8f);
			cxt.line(getX()+getWidth()*.8f, getY()+getHeight()*.2f, getX()+getWidth()/2, getY()+getHeight()*.8f);
			cxt.popStyle();
		}
	}
}