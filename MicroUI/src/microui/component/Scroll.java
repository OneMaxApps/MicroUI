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
		thumb.setConstrainDimensionsEnabled(false);
		thumb.setRipplesEnabled(false);	
		thumb.setTextVisible(false);
		
		thumb.onHold(() -> {
			calcDistFromMouseToThumb();
			
			switch(getOrientation()) {
				case HORIZONTAL :
					thumb.setX(constrain(ctx.mouseX+distToThumb,getContentX(),getContentX()+getContentWidth()-thumb.getContentWidth()));
					setValueWithoutActions(map(thumb.getContentX(),getContentX(),getContentX()+getContentWidth()-thumb.getContentWidth(),getMinValue(),getMaxValue()));
				break;
				
				case VERTICAL :
					thumb.setY(constrain(ctx.mouseY+distToThumb,getContentY(),getContentY()+getContentHeight()-thumb.getContentHeight()));
					setValueWithoutActions(map(thumb.getContentY(),getContentY()+getContentHeight()-thumb.getContentHeight(),getContentY(),getMinValue(),getMaxValue()));
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
		setPosition(ctx.width/2-getContentWidth()/2,ctx.height/2-getHeight()/2);
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
				 distToThumb = thumb.getContentX()-ctx.mouseX;
			break;
			
			case VERTICAL :
				distToThumb = thumb.getContentY()-ctx.mouseY;
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

	@Override
	protected void onChangeBounds() {
		super.onChangeBounds();
		
		if(thumb == null) { return; }
		updateThumbTransforms();
		
	}
	
	@Override
	protected void onChangePadding() {
		if(thumb == null) { return; }
		updateThumbTransforms();
	}

	private final void updateThumbTransforms() {
		//thumb.setBoundsProperty(this);
		thumb.setBounds(getContentX(),getContentY(),getContentWidth(),getContentHeight());

		switch(getOrientation()) {
		
		case HORIZONTAL :
			thumb.setWidth(getContentWidth()/10);
			if(hasEqualMinMax()) { return; }
			final float NEW_POS_X = map(getValue(),getMinValue(),getMaxValue(),getContentX(),getContentX()+getContentWidth()-thumb.getContentWidth());
			thumb.setX(constrain(NEW_POS_X,getContentX(),getContentX()+getContentWidth()-thumb.getContentWidth()));
			break;
		case VERTICAL :
			thumb.setHeight(getContentHeight()/10);
			if(hasEqualMinMax()) { return; }
			final float NEW_POS_Y = map(getValue(),getMaxValue(),getMinValue(),getContentY(),getContentY()+getContentHeight()-thumb.getContentHeight());
			thumb.setY(constrain(NEW_POS_Y,getContentY(),getContentY()+getContentHeight()-thumb.getContentHeight()));
			break;
		}
		
	}
	
}