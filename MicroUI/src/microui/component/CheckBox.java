package microui.component;

import microui.container.EdgeContainer;
import microui.core.AbstractButton;
import microui.core.style.Color;

public class CheckBox extends AbstractButton {
	private final EdgeContainer container;
	private boolean isSelected;
	private final Box box;
	
	public CheckBox(float x, float y, float w, float h) {
		super(x, y, w, h);
		stroke.setColor(new Color(255,0));
		color.set(255,0);
		hover.setEnabled(false);
		ripples.setEnabled(false);
		
		container = new EdgeContainer(x,y,w,h);
		container.setLeft(true);
		container.set(new Box());
		//container.setVisible(false);
		
		box = new Box();
		
		container.set(box);
		
		text.setAutoResizeEnabled(false);
		text.setTextSize(box.getHeight());
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
		super.update();
		container.draw();
	}

	@Override
	public void onChangeBounds() {
		super.onChangeBounds();
		if(container == null) { return; }
		
		container.setBounds(this);
		recalcTextState();
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

	@Override
	public final void setText(String text) {
		this.text.set(text);
		recalcTextState();
	}
	
	private void recalcTextState() {
		text.setSize(getWidth()-box.getWidth(),box.getHeight());
		text.setPosition(box.getX()+box.getWidth(),box.getY()+box.getHeight()/2-text.getHeight()/2);
		text.setTextSize(box.getHeight());
	}

	private final class Box extends AbstractButton {
		private static final int DEFAULT_BOX_SIZE = 16;
		private final Color markColor;
		
		public Box() {
			super(0,0,DEFAULT_BOX_SIZE,DEFAULT_BOX_SIZE);
			markColor = new Color(0,200,255,100);
			onClick(() -> toggle());
		}

		@Override
		protected void update() {
			super.update();
			if(isSelected) {
				markOnDraw();
			}
			CheckBox.this.text.draw();
		}
	
		private void markOnDraw() {
			app.pushStyle();
			app.noStroke();
			markColor.apply();
			app.rect(x,y,w,h);
			app.popStyle();
		}
	}
}