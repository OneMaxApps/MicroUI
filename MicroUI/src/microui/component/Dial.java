package microui.component;

import static processing.core.PApplet.map;
import static processing.core.PApplet.min;
import static processing.core.PConstants.CORNER;
import static processing.core.PConstants.HALF_PI;
import static processing.core.PConstants.TWO_PI;

import microui.constants.AutoResizeMode;
import microui.core.Texture;
import microui.core.base.Component;
import microui.core.interfaces.Scrollable;
import microui.core.style.Stroke;
import microui.util.Value;
import processing.event.MouseEvent;

public final class Dial extends Component implements Scrollable {
	private static final float RADIUS_START = HALF_PI*1.2f,
							   RADIUS_END = TWO_PI+HALF_PI*.8f;
	private final Stroke stroke;
	private final Value value;
	private final TextView hint;
	private final Image image;
	private final Arrow arrow;
	
	private float centerX,centerY;
	private boolean valueVisible;
	
	public Dial(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		setVisible(true);
		
		color.set(32);
		
		stroke = new Stroke();
		value = new Value();
		hint = new TextView(x,y,min(w,h),min(w,h));
		hint.setAutoResizeState(true);
		hint.setAutoResizeMode(AutoResizeMode.TINY);
		
		image = new Image();
		arrow = new Arrow();
		
		calculateCenter();
		valueVisible = true;
	}

	public Dial() {
		this(app.width*.4f,app.height*.4f,app.width*.2f,app.height*.2f);
	}

	@Override
	protected void update() {
		event.listen(this);
		
		app.pushStyle();
		
		stroke.get();
		color.use();
		app.ellipseMode(CORNER);
		app.circle(x, y, min(w,h));
		
		app.push();
		app.translate(centerX,centerY);
		app.rotate(map(value.get(),value.getMin(),value.getMax(),RADIUS_START,RADIUS_END));
		image.draw();
		arrow.draw();
		app.pop();
		
		if(event.inside() || event.holding()) {
			
			if(event.holding()) { value.append((app.pmouseY-app.mouseY)/2); }
			
			valueOnDraw();
			
		}
		
		app.popStyle();
		
	}

	private final void valueOnDraw() {
		if(!isValueVisible()) { return; }
		hint.draw();
		hint.set((int) value.get());
	}
	
	private final void calculateCenter() {
		centerX = x+min(w/2,h/2);
		centerY = y+min(w/2,h/2);
	}

	public final boolean isValueVisible() {
		return valueVisible;
	}
	

	public final void setValueVisible(boolean valueVisible) {
		this.valueVisible = valueVisible;
	}
	

	@Override
	public void onChangeBounds() {
		super.onChangeBounds();
		
		if(hint != null) {
			hint.setPosition(x,y);
			hint.setSize(min(w,h));
		}
		
		if(image != null) {
			image.setSize(min(w,h));
		}
		
		if(arrow != null) {
			arrow.onChangeBounds();
		}
		
		calculateCenter();
		
	}

	@Override
	public void mouseWheel(MouseEvent e) {
		if(event.inside()) {
			value.append(-e.getCount());
		}
	}
	
	public final class Image extends Texture {

		private Image() {
			super();
			setVisible(true);
			setSize(min(Dial.this.w,Dial.this.h));
		}

		@Override
		public void update() {
			app.pushStyle();
			tint.use();
			app.image(image,-w/2,-h/2,w,h);
			app.popStyle();
		}
		
	}
	
	public final class Arrow extends Texture {

		public Arrow() {
			super();
			setVisible(true);
			onChangeBounds();
		}

		@Override
		protected void update() {
			app.push();
			app.translate(x,y);
			app.image(image, 0,0,w,h);
			app.pop();
		}
		
		@Override
		public void onChangeBounds() {
			w = min(Dial.this.getWidth()*.16f,Dial.this.getHeight()*.16f);
			h = min(Dial.this.getWidth()*.08f,Dial.this.getHeight()*.08f);
			x = min(Dial.this.getWidth(),Dial.this.getHeight())/2-w;
		}
		
	}
	
	public final Stroke getStroke() {
		return stroke;
	}

	public final Value getValue() {
		return value;
	}

	public final TextView getHint() {
		return hint;
	}

	public final Image getImage() {
		return image;
	}

	public final Arrow getArrow() {
		return arrow;
	}
}