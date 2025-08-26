package microui.component;

import static java.lang.Math.min;

import microui.container.EdgeContainer;
import microui.core.AbstractButton;
import microui.core.base.Bounds;
import microui.core.effect.Hover;
import microui.core.effect.Ripples;
import microui.core.style.Color;
import microui.core.style.Stroke;
import microui.event.Listener;

public class CheckBox extends AbstractButton {
	private static final int DEFAULT_BOX_SIZE = 16;
	private final EdgeContainer container;
	private boolean isSelected;
	
	public CheckBox(float x, float y, float w, float h) {
		super(x, y, w, h);
		
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
	public void onChangeBounds() {
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
		getBox().markColor.set(color);
	}
	
	public final void onStateChangedListener(Listener listener) {
		onClick(listener);
	}
	
	private Box getBox() {
		return ((Content) (container.getElement())).box;
	}
	

	private final class Content extends Bounds {
	    final Box box;
		
		public Content(Bounds otherBounds) {
			super(otherBounds);
			
			setHeight(DEFAULT_BOX_SIZE);
			
			setVisible(true);
			
			box = new Box();
			
			text = new TextView(x, y, w, h);
			text.setAutoResizeEnabled(false);
			text.setTextSize(box.getHeight());
			
		}

		@Override
		protected void update() {
			box.draw();
			text.draw();
		}

		@Override
		public void onChangeBounds() {
			if(box == null || text == null) { return; }
			
			box.setPosition(x,y);
			box.setSize(min(DEFAULT_BOX_SIZE,w),min(DEFAULT_BOX_SIZE,h));
			text.setSize(getWidth()-box.getWidth(),box.getHeight());
			text.setPosition(box.getX()+box.getWidth(),box.getY()+box.getHeight()/2-text.getHeight()/2);
			text.setTextSize(box.getHeight());
		}

	}
	
	private final class Box extends AbstractButton {
		final Color markColor;
		
		public Box() {
			super(0,0,DEFAULT_BOX_SIZE,DEFAULT_BOX_SIZE);
			
			CheckBox.this.color = color;
			CheckBox.this.tooltip = tooltip;

			stroke = CheckBox.this.stroke = new Stroke();
			ripples = CheckBox.this.ripples = new Ripples(this);
			hover = CheckBox.this.hover = new Hover(this);
			
			markColor = new Color(0,200,255,100);
			
			onClick(() -> toggle());
		}

		@Override
		protected void update() {
			super.update();
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
			app.strokeWeight(Math.max(1,DEFAULT_BOX_SIZE/5));
			app.line(x+w*.3f, y+h*.6f, x+w/2, y+h*.8f);
			app.line(x+w*.8f, y+h*.2f, x+w/2, y+h*.8f);
			app.popStyle();
		}
	}
}