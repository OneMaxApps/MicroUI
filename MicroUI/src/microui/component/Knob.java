package microui.component;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.min;
import static processing.core.PApplet.dist;
import static processing.core.PApplet.map;

import microui.core.RangeControl;
import microui.core.style.Color;
import microui.core.style.Stroke;
import microui.util.Value;
import processing.event.MouseEvent;

public final class Knob extends RangeControl {
	private static final float START = 0, END = (float) (PI*2);
	private final Stroke stroke;
	private final Color indicatorColor;
	private float centerX,centerY,radius;
	private boolean isCanDrag;
	
	public Knob(float x, float y, float width, float height) {
		super(x, y, width, height);
		setMinMaxSize(10,50);
		getMutableColor().set(200);
	
		value = new Value(0,100,0);
		stroke = new Stroke();
		stroke.setWeight(1);
		indicatorColor = new Color(0,200,255,164);
		
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
		
		value.append(getScrollingMutable().get());
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
			getScrollingMutable().init(mouseEvent);
		}
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
		ctx.strokeWeight(getIndicatorWeight());

		ctx.stroke(indicatorColor.get(),64);
		ctx.arc(0,0,radius*.8f, radius*.8f,START,END);
		
		ctx.stroke(indicatorColor.get());
		ctx.arc(0,0,radius*.8f, radius*.8f,START,map(value.get(),value.getMin(),value.getMax(),START,END));
		
		if(value.get() == value.getMax()) {
			ctx.ellipse(0, 0, radius/4, radius/4);
		}
		
		ctx.pop();
	}
	
	private float getIndicatorWeight() {
		return radius*.1f+abs(getScrollingMutable().get()*2);
	}
}