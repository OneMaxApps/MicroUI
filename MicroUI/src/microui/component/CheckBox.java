package microui.component;

import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.CORNER;

import microui.container.EdgeContainer;
import microui.core.AbstractButton;
import microui.core.base.Component;
import microui.core.style.Color;

public class CheckBox extends AbstractButton {
	private static final int DEFAULT_BOX_SIZE = 16;
	private final EdgeContainer container;
	private String text;
	private float textPadding;
	private boolean isSelected;
	
	public CheckBox(float x, float y, float w, float h) {
		super(x, y, w, h);
		callback.clearAll();
		
		textPadding = DEFAULT_BOX_SIZE/2;
		
		container = new EdgeContainer(x,y,w,h);
		container.setLeft(true);
		
		container.set(new Box());
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
		event.listen(this);
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
	
	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}
	
	public final float getTextPadding() {
		return textPadding;
	}

	public final void setTextPadding(float textPadding) {
		if(textPadding <= 0) { return; }
		this.textPadding = textPadding;
	}



	private final class Box extends Component {
		
		public Box() {
			super();
			setVisible(true);
			setSize(DEFAULT_BOX_SIZE);
			
			color.set(255,32);
			setStrokeColor(new Color(255,0));
			
			hover.setComponent(this);
			ripples.setComponent(this);
			CheckBox.this.tooltip.setCallbacksFor(callback);
			onClick(() -> toggle());
		}

		@Override
		protected void update() {
			app.pushStyle();
			stroke.apply();
			color.apply();
			app.rect(x, y, w, h);
			hover.draw();
			ripples.draw();
			
			textOnDraw();
			
			app.popStyle();
			
			System.out.println(isSelected);
		}
		
		private void textOnDraw() {
			if(text == null) { return; }
			app.fill(255);
			app.textSize(h);
			app.textAlign(CORNER,CENTER);
			app.text(text,x+w+textPadding,y,CheckBox.this.getWidth()-w-textPadding,h);
		}
	}
}