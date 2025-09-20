package microui.component;

import static microui.core.style.theme.ThemeManager.getTheme;
import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;

import microui.core.LinearRangeControl;
import microui.core.style.Color;

public class Scroll extends LinearRangeControl {
	private final Button thumb;
	private float distToThumb;
	private boolean needRecalculateDistToThumb;
	
	public Scroll(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		//getMutableBackgroundColor().set(0,32);
		thumb = new Button("");
		thumb.setConstrainDimensionsEnabled(false);
		thumb.setRipplesEnabled(false);	
		thumb.setTextVisible(false);
		thumb.setBackgroundColor(getTheme().getPrimaryColor());
		
		thumb.onHold(() -> {
			calcDistFromMouseToThumb();
			
			switch(getOrientation()) {
				case HORIZONTAL :
					thumb.setX(constrain(ctx.mouseX+distToThumb,getX(),getX()+getWidth()-thumb.getWidth()));
					setValueWithoutActions(map(thumb.getX(),getX(),getX()+getWidth()-thumb.getWidth(),getMinValue(),getMaxValue()));
				break;
				
				case VERTICAL :
					thumb.setY(constrain(ctx.mouseY+distToThumb,getY(),getY()+getHeight()-thumb.getHeight()));
					setValueWithoutActions(map(thumb.getY(),getY()+getHeight()-thumb.getHeight(),getY(),getMinValue(),getMaxValue()));
				break;
			}
			
			onChangeValue();
			onStartChangeValue();
		});
		
		thumb.onHoldEnd(() -> needRecalculateDistToThumb = true);
		
		updateThumbTransforms();
		setValue(0,100,50);
	}
	
	public Scroll() {
		this(0,0,0,0);
		setWidth(getMaxWidth());
		setPosition(ctx.width/2-getWidth()/2,ctx.height/2-getHeight()/2);
	}

	@Override
	protected void render() {
		super.render();
		thumb.draw();
		
	}
	
	private final void calcDistFromMouseToThumb() {
		if(!needRecalculateDistToThumb) { return; }
		
		switch(getOrientation()) {
			case HORIZONTAL :
				 distToThumb = thumb.getX()-ctx.mouseX;
			break;
			
			case VERTICAL :
				distToThumb = thumb.getY()-ctx.mouseY;
			break;
		}
		
		needRecalculateDistToThumb = false;
		
	}

	
	@Override
	public void swapOrientation() {
		super.swapOrientation();
		onChangeBounds();
	}
	
	public final boolean isThumbClicked() {
		return thumb.isClicked();
	}

	public final boolean isThumbClicked(int count) {
		return thumb.isClicked(count);
	}

	public final boolean isThumbDragged() {
		return thumb.isDragged();
	}

	public final boolean isThumbDragging() {
		return thumb.isDragging();
	}

	public final boolean isThumbPressed() {
		return thumb.isPressed();
	}

	public final boolean isMouseInsideThumb() {
		return thumb.isMouseInside();
	}

	public final boolean isMouseOutsideFromThumb() {
		return thumb.isMouseOutside();
	}

	public final boolean isThumbHolding() {
		return thumb.isHolding();
	}
	
	public final Color getThumbRipplesColor() {
		return thumb.getRipplesColor();
	}

	public final void setThumbRipplesColor(Color color) {
		thumb.setRipplesColor(color);
	}

	public final boolean isThumbRipplesEnabled() {
		return thumb.isRipplesEnabled();
	}

	public final void setThumbRipplesEnabled(boolean isEnabled) {
		thumb.setRipplesEnabled(isEnabled);
	}

	public final boolean isThumbHoverEnabled() {
		return thumb.isHoverEnabled();
	}

	public final void setThumbHoverEnabled(boolean isEnabled) {
		thumb.setHoverEnabled(isEnabled);
	}
	
	public Color getThumbHoverColor() {
		return thumb.getHoverColor();
	}
	
	public void setThumbHoverColor(Color color) {
		thumb.setHoverColor(color);
	}
	
	public final float getThumbHoverSpeed() {
		return thumb.getHoverSpeed();
	}

	public final void setThumbHoverSpeed(float speed) {
		thumb.setHoverSpeed(speed);
	}

	public final float getThumbStrokeWeight() {
		return thumb.getStrokeWeight();
	}

	public final void setThumbStrokeWeight(int weight) {
		thumb.setStrokeWeight(weight);
	}

	public final Color getThumbStrokeColor() {
		return thumb.getStrokeColor();
	}

	public final void setThumbStrokeColor(Color color) {
		thumb.setStrokeColor(color);
	}

	@Override
	protected void onChangeBounds() {
		super.onChangeBounds();
		
		if(thumb == null) { return; }
		updateThumbTransforms();
		
	}

	private final void updateThumbTransforms() {
		//thumb.setBoundsProperty(this);
		thumb.setBounds(getX(),getY(),getWidth(),getHeight());

		switch(getOrientation()) {
		
		case HORIZONTAL :
			thumb.setWidth(getWidth()/10);
			if(hasEqualMinMax()) { return; }
			final float NEW_POS_X = map(getValue(),getMinValue(),getMaxValue(),getX(),getX()+getWidth()-thumb.getWidth());
			thumb.setX(constrain(NEW_POS_X,getX(),getX()+getWidth()-thumb.getWidth()));
			break;
		case VERTICAL :
			thumb.setHeight(getHeight()/10);
			if(hasEqualMinMax()) { return; }
			final float NEW_POS_Y = map(getValue(),getMaxValue(),getMinValue(),getY(),getY()+getHeight()-thumb.getHeight());
			thumb.setY(constrain(NEW_POS_Y,getY(),getY()+getHeight()-thumb.getHeight()));
			break;
		}
		
	}
	
}