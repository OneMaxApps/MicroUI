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
		
		color.set(0,32);
		thumb = new Button("");
		thumb.setTextVisible(false);
		thumb.onHold(() -> {
			calcDistFromMouseToThumb();
			
			switch(orientation) {
				case HORIZONTAL :
					thumb.setX(constrain(cxt.mouseX+distToThumb,getX(),getX()+getWidth()-thumb.getWidth()));
					value.setWithoutActions(map(thumb.getX(),getX(),getX()+getWidth()-thumb.getWidth(),value.getMin(),value.getMax()));
				break;
				
				case VERTICAL :
					thumb.setY(constrain(cxt.mouseY+distToThumb,getY(),getY()+getHeight()-thumb.getHeight()));
					value.setWithoutActions(map(thumb.getY(),getY()+getHeight()-thumb.getHeight(),getY(),value.getMin(),value.getMax()));
				break;
			}
			
			onChangeValue();
			onStartChangeValue();
		});
		
		thumb.onHoldEnd(() -> needRecalculateDistToThumb = true);
		
		updateThumbTransforms();
		value.set(0,100,50);
	}
	
	public Scroll() {
		this(cxt.width*.25f,cxt.height*.45f,cxt.width*.5f,cxt.height*.1f);

	}

	@Override
	protected void update() {
		super.update();
		thumb.draw();
		
	}
	
	private final void calcDistFromMouseToThumb() {
		if(!needRecalculateDistToThumb) { return; }
		
		switch(orientation) {
			case HORIZONTAL :
				 distToThumb = thumb.getX()-cxt.mouseX;
			break;
			
			case VERTICAL :
				distToThumb = thumb.getY()-cxt.mouseY;
			break;
		}
		
		needRecalculateDistToThumb = false;
		
	}

	@Override
	protected void onChangeBounds() {
		super.onChangeBounds();
		
		if(thumb == null) { return; }
		updateThumbTransforms();
		
	}
	
	@Override
	public void swapOrientation() {
		super.swapOrientation();
		onChangeBounds();
	}

	public final Button getThumb() {
		return thumb;
	}

	private final void updateThumbTransforms() {
		thumb.setBounds(this);
		
		switch(orientation) {
		
		case HORIZONTAL :
			thumb.setWidth(getWidth()/10);
			if(value.hasEqualMinMax()) { return; }
			final float NEW_POS_X = map(value.get(),value.getMin(),value.getMax(),getX(),getX()+getWidth()-thumb.getWidth());
			thumb.setX(constrain(NEW_POS_X,getX(),getX()+getWidth()-thumb.getWidth()));
			break;
		case VERTICAL :
			thumb.setHeight(getHeight()/10);
			if(value.hasEqualMinMax()) { return; }
			final float NEW_POS_Y = map(value.get(),value.getMax(),value.getMin(),getY(),getY()+getHeight()-thumb.getHeight());
			thumb.setY(constrain(NEW_POS_Y,getY(),getY()+getHeight()-thumb.getHeight()));
			break;
		}
		
	}
	
}