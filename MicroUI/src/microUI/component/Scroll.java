package microUI.component;

import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;

import microUI.core.Constants;
import microUI.core.RangeControl;

public final class Scroll extends RangeControl {
	public final Button thumb;
	private float distToThumb;
	private boolean needRecalculateDistToThumb;
	
	public Scroll(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		fill.set(0,32);
		thumb = new Button("");
		thumb.text.invisible();
		updateThumbTransforms();
		value.set(0,100,50);
	}
	
	public Scroll() {
		this(app.width*.25f,app.height*.45f,app.width*.5f,app.height*.1f);

	}

	@Override
	public void update() {
		super.update();
		thumb.draw();
		
		
		
		if(thumb.event.holding()) {
			calcDistFromMouseToThumb();
			
			switch(orientation) {
				case Constants.HORIZONTAL :
					thumb.setX(constrain(app.mouseX+distToThumb,getX(),getX()+getW()-thumb.getW()));
					value.setWithoutActions(map(thumb.getX(),getX(),getX()+getW()-thumb.getW(),value.getMin(),value.getMax()));
				break;
				
				case Constants.VERTICAL :
					thumb.setY(constrain(app.mouseY+distToThumb,getY(),getY()+getH()-thumb.getH()));
					value.setWithoutActions(map(thumb.getY(),getY()+getH()-thumb.getH(),getY(),value.getMin(),value.getMax()));
				break;
			}
			
		} else {
			needRecalculateDistToThumb = true;
		}
		
	}
	
	private final void calcDistFromMouseToThumb() {
		if(!needRecalculateDistToThumb) { return; }
		
		switch(orientation) {
			case Constants.HORIZONTAL :
				 distToThumb = thumb.getX()-app.mouseX;
			break;
			
			case Constants.VERTICAL :
				distToThumb = thumb.getY()-app.mouseY;
			break;
		}
		
		needRecalculateDistToThumb = false;
		
	}

	@Override
	public void inTransforms() {
		super.inTransforms();
		
		if(thumb == null) { return; }
		updateThumbTransforms();
		
	}
	
	private final void updateThumbTransforms() {
		thumb.setTransforms(this);
		
		switch(orientation) {
		
		case Constants.HORIZONTAL :
			thumb.setW(getW()/10);
			thumb.setX(map(value.get(),value.getMin(),value.getMax(),getX(),getX()+getW()-getW()/10));
			break;
		case Constants.VERTICAL :
			thumb.setH(getH()/10);
			thumb.setY(map(value.get(),value.getMin(),value.getMax(),getY()+getH()-thumb.getH(),getY()));
			break;
		}
		
	}
	
}