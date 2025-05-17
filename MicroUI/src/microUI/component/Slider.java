package microUI.component;

import static processing.core.PApplet.map;

import microUI.core.Constants;
import microUI.core.RangeControl;
import microUI.graphics.Rect;

public final class Slider extends RangeControl {
	
	public final Rect level;
	
	public Slider(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		level = new Rect(x,y,w,h);
		level.fill.set(234);
		level.allowNegativeDimensions(true);
		
		value.set(0,100,0);
		
		setOrientation(Constants.HORIZONTAL);
		
	}
	
	public Slider() {
		this(app.width*.25f,app.height*.45f,app.width*.5f,app.height*.1f);
	}

	@Override
	public void update() {
		super.update();
		level.draw();
		
		if(event.holding()) {
			switch(orientation) {
			case Constants.HORIZONTAL: value.set(map(app.mouseX,getX(),getX()+getW(),value.getMin(),value.getMax())); break;
			case Constants.VERTICAL: value.set(map(app.mouseY,getY(),getY()+getH(),value.getMin(),value.getMax())); break;
			}
			inTransforms();
		}
		
		
		
	}

	@Override
	public void inTransforms() {
		if(level == null) { return; }
		
		level.setTransforms(this);
		
		switch(orientation) {
		
			case Constants.HORIZONTAL:
				level.setW(map(value.get(),value.getMin(),value.getMax(),0,getW()));
				
			break;
			
			case Constants.VERTICAL:
				level.setY(getY()+getH());
				level.setW(getW());
				level.setH(map(value.get(),value.getMin(),value.getMax(),0,-getH()));
			break;
			
		}
		
	}
	
}