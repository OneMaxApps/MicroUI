package microUI.container.layout;

import static processing.core.PApplet.constrain;

import java.util.ArrayList;

import microUI.component.TextView;
import microUI.core.base.Bounds;
import microUI.core.base.Layout;

public class LinearLayout extends Layout {
	public final static int MODE_ROW = 0,MODE_COLUMN = 1;
	private int mode;
	private ArrayList<Float> weightList;
	public final Transforming transforming;
	
	public LinearLayout(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		weightList = new ArrayList<Float>();
		transforming = new Transforming() {
		@Override
			public final void updateForce() {
		  	for(int i = 0; i < elementList.size(); i++) {
		  		Bounds baseRectangle = elementList.get(i);

		  		switch(mode) {
		  		
			  		case MODE_ROW :
				  		if(isElementsResizable()) {
							if(i == 0) {
							baseRectangle.setTransforms(getX(),getY(),getW()*weightList.get(i),getH());
							} else {
								baseRectangle.setTransforms(elementList.get(i-1).getX()+elementList.get(i-1).getW(),getY(),getW()*weightList.get(i),getH());
							}
				  		} else {
				  			if(i == 0) {
								baseRectangle.setPosition(getX(),getY());
							} else {
								baseRectangle.setPosition(elementList.get(i-1).getX()+elementList.get(i-1).getW(),getY());
							}
				  		}
			  		break;
			  		
			  		
			  		case MODE_COLUMN:
				  		if(isElementsResizable()) {
							if(i == 0) {
								baseRectangle.setTransforms(getX(),getY(),getW(),getH()*weightList.get(0));
							} else {
								baseRectangle.setTransforms(getX(),elementList.get(i-1).getY()+elementList.get(i-1).getH(),getW(),getH()*weightList.get(i));
							}
				  		} else {
				  			if(i == 0) {
				  				baseRectangle.setPosition(getX(), getY());
								} else {
									baseRectangle.setPosition(getX(), elementList.get(i-1).getY()+elementList.get(i-1).getH());
								}
				  		}
			  		break;
			  		
		  		}
		  		
		  	 }
			}
		};
		
		isElementsResizable = true;
	}
	
	public LinearLayout() {
		this(0,0,app.width,app.height);
	}

	@Override
	public void draw() {
		super.draw();
		update();
	}
	
	public final void setMode(int mode) {
		if(mode !=  MODE_ROW && mode != MODE_COLUMN) { throw new IndexOutOfBoundsException("out of types exception"); }
		this.mode = mode;
		transforming.updateForce();
	}
	
	public final boolean isMode(int mode) {
		return this.mode == mode;
	}
	
	public final int getMode() {
		return mode;
	}
	
	public final void setHorizontalMode() {
		mode = 0;
		transforming.updateForce();
	}
	
	public final void setVerticalMode() {
		mode = 1;
		transforming.updateForce();
	}
	
	public final void swapModes() {
		if(mode == 0) {
			mode = 1;
			transforming.updateForce();
			return;
		}
		if(mode == 1) { mode = 0; }
		transforming.updateForce();
	}
	
	@Override
	public void update() {
		if(visible) {
			super.update();
		}
		if(!elementList.isEmpty()) {
			elementList.forEach(element -> element.draw());
		};
		
		if(app.frameCount == 1 || app.frameCount%60*60 == 0) { transforming.updateForce(); }

	}
	
	public LinearLayout add(Bounds baseRectangle, float weight) {
		elementList.add(baseRectangle);
		
		if(weightList.isEmpty()) {
			weightList.add(constrain(weight,0,1f));
		} else {
		
		float maxSize = 0f;
		for(int i = 0; i < elementList.size()-1; i++) {
			maxSize += weightList.get(i);
		}
		
		weightList.add(constrain(weight,0,1f-maxSize));
		}
		
		transforming.updateForce();
		
		return this;
	}
	
	public LinearLayout add(String text, float weight) {
		add(new TextView(text,x,y,w,weight),weight);
		return this;
	}
	
	public void remove(int index) {
		if(index < 0 || index > elementList.size()-1) { throw new IndexOutOfBoundsException(); }
		elementList.remove(index);
		weightList.remove(index);
		transforming.updateForce();
		
	}

	@Override
	public void setX(float x) {
		super.setX(x);
		if(transforming != null) {
			transforming.autoUpdate();
		}
	}
	
	@Override
	public void setY(float y) {
		super.setY(y);
		if(transforming != null) {
			transforming.autoUpdate();
		}
	}
	
	@Override
	public void setW(float w) {
		super.setW(w);
		if(transforming != null) {
			transforming.autoUpdate();
		}
	}
	
	@Override
	public void setH(float h) {
		super.setH(h);
		if(transforming != null) {
			transforming.autoUpdate();
		}
	}
	
	
}