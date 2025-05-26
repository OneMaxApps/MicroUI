package microui.component;

import static processing.core.PApplet.map;
import static processing.core.PApplet.max;
import static processing.core.PApplet.min;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.CORNER;
import static processing.core.PConstants.HALF_PI;
import static processing.core.PConstants.TWO_PI;

import microui.core.AbstractImage;
import microui.core.base.Component;
import microui.core.interfaces.Scrollable;
import microui.core.style.Stroke;
import microui.util.Constants;
import microui.util.Value;
import processing.event.MouseEvent;

public final class Dial extends Component implements Scrollable {
	private final static float RADIUS_START = HALF_PI*1.2f,
							   RADIUS_END = TWO_PI+HALF_PI*.8f;
	public final Stroke stroke;
	public final Value value;
	public final TextView hint;
	public final Image image;
	public final Arrow arrow;
	
	private float centerX,centerY;
	private boolean valueVisible;
	
	public Dial(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		
		visible();
		fill.set(32);
		
		stroke = new Stroke();
		value = new Value();
		hint = new TextView(x,y,min(w,h),min(w,h));
		hint.setAutoResize(true);
		hint.setAutoResizeMode(Constants.AUTO_RESIZE_MODE_TINY);
		
		image = new Image();
		arrow = new Arrow();
		
		calculateCenter();
		valueVisible = true;
	}

	public Dial() {
		this(app.width*.4f,app.height*.4f,app.width*.2f,app.height*.2f);
	}

	@Override
	public void update() {
		event.listen(this);
		
		app.pushStyle();
		
		stroke.get();
		fill.use();
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
			
			if(isValueVisible()) { valueOnDraw(); }
			
		} else { hint.draw(); }
		
		app.popStyle();
		
	}
	
	private final void valueOnDraw() {
		app.pushStyle();
		app.fill(hint.fill.get());
		if(hint.getFont() != null) { app.textFont(hint.getFont()); }
		
		if(hint.isAutoResize()) {
		app.textSize(max(1,min(w,h)/hint.getAutoResizeMode()));
		} else {
		app.textSize(hint.getTextSize());
		}
		
		app.textAlign(CENTER,CENTER);
		app.text((int) value.get(),x+min(w/2,h/2), y+min(w/2,h/2));
		app.popStyle();
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
	protected void inTransforms() {
		super.inTransforms();
		
		if(hint != null) {
			hint.setPosition(x,y);
			hint.setSize(min(w,h));
		}
		
		if(image != null) {
			image.setSize(min(w,h));
		}
		
		if(arrow != null) {
			arrow.updateTransforms();
		}
		calculateCenter();
		
		
	}

	@Override
	public void mouseWheel(MouseEvent e) {
		if(event.inside()) {
			value.append(-e.getCount());
		}
	}
	
	public final class Image extends AbstractImage {

		private Image() {
			super();
			visible();
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
	
	public final class Arrow extends AbstractImage {

		public Arrow() {
			super();
			visible();
			updateTransforms();
		}

		@Override
		public void update() {
			app.push();
			app.translate(x,y);
			app.image(image, 0,0,w,h);
			app.pop();
		}
		
		private final void updateTransforms() {
			
			w = min(Dial.this.getW()*.16f,Dial.this.getH()*.16f);
			h = min(Dial.this.getW()*.08f,Dial.this.getH()*.08f);
			x = min(Dial.this.getW(),Dial.this.getH())/2-w;
			
		}
	}
}