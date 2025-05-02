package microUI.component;

import static processing.core.PApplet.map;
import static processing.core.PApplet.constrain;

import microUI.Constants;
import microUI.core.RangeControl;

public final class Scroll extends RangeControl {
	public final Button thumb;

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
			
			switch(orientation) {
				case Constants.HORIZONTAL :
					thumb.setX(constrain(app.mouseX,getX(),getX()+getW()-thumb.getW()));
					value.setWithoutActions(map(thumb.getX(),getX(),getX()+getW()-thumb.getW(),value.getMin(),value.getMax()));
				break;
				
				case Constants.VERTICAL :
					thumb.setY(constrain(app.mouseY,getY(),getY()+getH()-thumb.getH()));
					value.setWithoutActions(map(thumb.getY(),getY()+getH()-thumb.getH(),getY(),value.getMin(),value.getMax()));
				break;
			}
			
		}
		
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