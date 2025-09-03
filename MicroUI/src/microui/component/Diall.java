package microui.component;

import static java.util.Objects.requireNonNull;
import static microui.constants.AutoResizeMode.TINY;
import static processing.core.PApplet.map;
import static processing.core.PApplet.min;
import static processing.core.PConstants.CORNER;
import static processing.core.PConstants.HALF_PI;
import static processing.core.PConstants.TWO_PI;

import microui.core.Texture;
import microui.core.base.Component;
import microui.core.interfaces.Scrollable;
import microui.core.style.Color;
import microui.core.style.Stroke;
import microui.util.Value;
import processing.event.MouseEvent;

public final class Diall extends Component implements Scrollable {
	private static final float RADIUS_START = HALF_PI+HALF_PI/4,
							   RADIUS_END = TWO_PI+HALF_PI-HALF_PI/4;
	private final Stroke stroke;
	private final Value value;
	private final TextView hint;
	private final Image image;
	private final Arrow arrow;
	
	private float centerX,centerY;
	private boolean valueVisible;
	
	public Diall(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		setVisible(true);
		
		stroke = new Stroke();
		value = new Value();
		hint = new TextView(x,y,min(w,h),min(w,h));
		hint.setColor(new Color(0));
		
		hint.setAutoResizeEnabled(true);
		hint.setAutoResizeMode(TINY);
		
		image = new Image();
		arrow = new Arrow();
		
		calculateCenter();
		valueVisible = true;
	}

	public Diall() {
		this(cxt.width*.4f,cxt.height*.4f,cxt.width*.2f,cxt.height*.2f);
	}

	@Override
	protected void update() {
		setEventListener(this);
		
		cxt.pushStyle();
		
		stroke.apply();
		getMutableColor().apply();
		cxt.ellipseMode(CORNER);
		cxt.circle(getX(), getY(), min(getWidth(),getHeight()));
		
		cxt.push();
		cxt.translate(centerX,centerY);
		cxt.rotate(map(value.get(),value.getMin(),value.getMax(),RADIUS_START,RADIUS_END));
		image.draw();
		arrow.draw();
		cxt.pop();
		
		if(isMouseInside() || isHolding()) {
			
			if(isHolding()) { value.append((cxt.pmouseY-cxt.mouseY)/2); }
			
			valueOnDraw();
			
		}
		
		cxt.popStyle();
		
	}

	

	public final boolean isValueVisible() {
		return valueVisible;
	}
	

	public final void setValueVisible(boolean valueVisible) {
		this.valueVisible = valueVisible;
	}
	

	@Override
	protected void onChangeBounds() {
		super.onChangeBounds();
		
		if(hint != null) {
			hint.setPosition(getX(),getY());
			hint.setSize(min(getWidth(),getHeight()));
		}
		
		if(image != null) {
			image.setSize(min(getWidth(),getHeight()));
		}
		
		if(arrow != null) {
			arrow.onChangeBounds();
		}
		
		calculateCenter();
		
	}

	@Override
	public void mouseWheel(MouseEvent mouseEvent) {
		requireNonNull(mouseEvent,"mouseEvent cannot be null");
		
		if(isMouseInside()) {
			value.append(-mouseEvent.getCount());
		}
	}
	
	public final class Image extends Texture {

		private Image() {
			super();
			setVisible(true);
			setSize(min(Diall.this.getWidth(),Diall.this.getHeight()));
		}

		@Override
		public void update() {
			cxt.pushStyle();
			tint.apply();
			cxt.image(image,-getWidth()/2,-getHeight()/2,getWidth(),getHeight());
			cxt.popStyle();
		}
		
	}
	
	public final class Arrow extends Texture {
		private final Color color;
		
		public Arrow() {
			super();
			setVisible(true);
			onChangeBounds();
			color = new Color(32);
		}
	
		@Override
		public void draw() {
			update();
		}

		@Override
		protected void update() {
			cxt.push();
			cxt.translate(getX(),getY());
			if(image != null) {
				cxt.image(image, 0,0,getWidth(),getHeight());
			} else {
				color.apply();
				cxt.rect(0, 0, getWidth(), getHeight());
			}
			cxt.pop();
		}
		
		@Override
		protected void onChangeBounds() {
			setWidth(min(Diall.this.getWidth()*.16f,Diall.this.getHeight()*.16f));
			setHeight(min(Diall.this.getWidth()*.08f,Diall.this.getHeight()*.08f));
			setX(min(Diall.this.getWidth(),Diall.this.getHeight())/2-getWidth());
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
	
	private final void valueOnDraw() {
		if(!isValueVisible()) { return; }
		hint.draw();
		hint.set((int) value.get());
	}
	
	private final void calculateCenter() {
		centerX = getX()+min(getWidth()/2,getHeight()/2);
		centerY = getY()+min(getWidth()/2,getHeight()/2);
	}
}