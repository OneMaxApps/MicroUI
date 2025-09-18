package microui.component;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.min;
import static processing.core.PApplet.dist;
import static processing.core.PApplet.map;

import microui.core.base.Component;
import microui.core.interfaces.Scrollable;
import microui.core.style.Color;
import microui.core.style.Stroke;
import microui.event.Scrolling;
import microui.util.Value;
import processing.event.MouseEvent;

public final class Knob extends Component implements Scrollable {
	private static final float START = 0, END = (float) (PI*2);
	private final Value value;
	private final Scrolling scrolling;
	private final Stroke stroke;
	private final Color indicatorColor;
	private float centerX,centerY,radius;
	private boolean isCanDrag;
	
	public Knob(float x, float y, float width, float height) {
		super(x, y, width, height);
		setMinMaxSize(10,300);
		getMutableColor().set(200);
	
		value = new Value(0,100,0);
		scrolling = new Scrolling(getMutableEvent());
		stroke = new Stroke();
		stroke.setWeight(1);
		indicatorColor = new Color(0,164,0,200);
		
	}
	
	public Knob() {
		this(ctx.width*.45f,ctx.height*.45f,ctx.width*.1f,ctx.height*.1f);
	}

	@Override
	protected void render() {
		stroke.apply();
		getMutableColor().apply();
		ctx.ellipse(centerX, centerY, radius, radius);
		
		indicatorOnDraw();
		
		if(isDragging() && isMouseInRadius()) {
			isCanDrag = true;
		}
		
		if(!ctx.mousePressed) {
			isCanDrag = false;
		}
		
		if(isCanDrag) {
			value.append(ctx.pmouseY-ctx.mouseY);
		}
		
		value.append(scrolling.get());
	}
	
	@Override
	protected void onChangeBounds() {
		super.onChangeBounds();
		recalculateCenter();
		recalculateRadius();
	}

	@Override
	public void mouseWheel(MouseEvent mouseEvent) {
		if(isMouseInRadius()) {
			scrolling.init(mouseEvent);
		}
	}
	
	public void setValue(float value) {
		this.value.set(value);
	}

	public void setValue(float min, float max, float value) {
		this.value.set(min, max, value);
	}

	public float getValue() {
		return value.get();
	}

	public void setMinValue(float min) {
		value.setMin(min);
	}

	public float getMinValue() {
		return value.getMin();
	}

	public void setMaxValue(float max) {
		value.setMax(max);
	}

	public float getMaxValue() {
		return value.getMax();
	}

	public void setMinMaxValue(float min, float max) {
		value.setMinMax(min, max);
	}

	public void appendValue(float value) {
		this.value.append(value);
	}

	public void setScrollingVelocity(float velocity) {
		scrolling.setVelocity(velocity);
	}
	
	public Color getIndicatorColor() {
		return new Color(indicatorColor);
	}
	
	public void setIndicatorColor(Color color) {
		indicatorColor.set(color);
	}
	
	private void recalculateCenter() {
		centerX = getX()+getWidth()/2;
		centerY = getY()+getHeight()/2;
	}
	
	private void recalculateRadius() {
		radius = min(getWidth(),getHeight());
	}
	
	private boolean isMouseInRadius() {
		return dist(centerX, centerY, ctx.mouseX, ctx.mouseY) < radius/2;
	}
	
	private void indicatorOnDraw() {
		ctx.push();
		
		ctx.translate(centerX, centerY);
		ctx.rotate((float)PI/2);
		ctx.noFill();
		ctx.stroke(indicatorColor.get());
		ctx.strokeWeight(getIndicatorWeight());
		ctx.arc(0,0,radius*.9f, radius*.9f,START,map(value.get(),value.getMin(),value.getMax(),START,END));
		
		if(value.get() == value.getMax()) {
			ctx.ellipse(0, 0, radius/4, radius/4);
		}
		
		ctx.pop();
	}
	
	private float getIndicatorWeight() {
		return radius*.1f+abs(scrolling.get()*5)+map(value.get(),value.getMin(),value.getMax(),0,radius*.1f);
	}
}