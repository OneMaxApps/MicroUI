package microui.container.layout;

import static processing.core.PApplet.constrain;

import java.util.ArrayList;

import microui.component.TextView;
import microui.core.base.Bounds;
import microui.core.base.Layout;
import microui.util.Constants;

public class LinearLayout extends Layout {
	private int mode;
	private final ArrayList<Float> weightList;

	public LinearLayout(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		weightList = new ArrayList<Float>();

	}
	
	public LinearLayout() {
		this(0,0,app.width,app.height);
	}

	@Override
	public void draw() {
		super.draw();
		
		if(!elementList.isEmpty()) {
			elementList.forEach(element -> element.draw());
		};
	}
	
	public final void setMode(int mode) {
		if(mode !=  Constants.HORIZONTAL && mode != Constants.VERTICAL) { throw new IndexOutOfBoundsException("out of types exception"); }
		this.mode = mode;
		recalcListState();
	}
	
	public final boolean isMode(int mode) {
		return this.mode == mode;
	}
	
	public final int getMode() {
		return mode;
	}
	
	public final void setHorizontalMode() {
		mode = 0;
		recalcListState();
	}
	
	public final void setVerticalMode() {
		mode = 1;
		recalcListState();
	}
	
	public final void swapModes() {
		if(mode == 0) {
			mode = 1;
			recalcListState();
			return;
		}
		if(mode == 1) { mode = 0; }
		recalcListState();
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
		
		recalcListState();
		
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
		recalcListState();
	}
	
	@Override
	protected final void recalcListState() {
		for(int i = 0; i < elementList.size(); i++) {
	  		Bounds bounds = elementList.get(i);

	  		switch(mode) {
	  		
		  		case Constants.HORIZONTAL :
					if(i == 0) {
					bounds.setBounds(getX(),getY(),getWidth()*weightList.get(i),getHeight());
					} else {
						bounds.setBounds(elementList.get(i-1).getX()+elementList.get(i-1).getWidth(),getY(),getWidth()*weightList.get(i),getHeight());
					}
		  		break;
		  		
		  		
		  		case Constants.VERTICAL:
					if(i == 0) {
						bounds.setBounds(getX(),getY(),getWidth(),getHeight()*weightList.get(0));
					} else {
						bounds.setBounds(getX(),elementList.get(i-1).getY()+elementList.get(i-1).getHeight(),getWidth(),getHeight()*weightList.get(i));
					}
		  		break;
		  		
	  		}
	  		
	  	 }
	}
}