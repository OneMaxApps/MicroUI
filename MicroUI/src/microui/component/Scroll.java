package microui.component;

import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;

import microui.core.RangeControl;

public class Scroll extends RangeControl {
	private final Button thumb;
	private float distToThumb;
	private boolean needRecalculateDistToThumb;
	
	public Scroll(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		getMutableColor().set(0,32);
		thumb = new Button("");
		thumb.setRipplesEnabled(false);
		
		thumb.setTextVisible(false);
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
	protected void update() {
		super.update();
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

	@Deprecated
	public final Button getThumb() {
		return thumb;
	}

	@Override
	protected void onChangeBounds() {
		super.onChangeBounds();
		
		if(thumb == null) { return; }
		updateThumbTransforms();
		
	}
	
	private final void updateThumbTransforms() {
		thumb.setBoundsProperty(this);
		
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