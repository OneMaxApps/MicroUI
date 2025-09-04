package microui.container.layout;

import static processing.core.PApplet.constrain;

import java.util.ArrayList;

import microui.component.TextView;
import microui.constants.Orientation;
import microui.core.base.Bounds;
import microui.core.base.Layout;

public class LinearLayout extends Layout {
	private Orientation orientation;
	private final ArrayList<Float> weightList;

	public LinearLayout(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		weightList = new ArrayList<Float>();
		
		orientation = Orientation.VERTICAL;
		
	}
	
	public LinearLayout() {
		this(0,0,ctx.width,ctx.height);
	}

	@Override
	public void draw() {
		super.draw();
		drawElements();
	}
	
	public final void setOrientation(Orientation orientation) {
		if(this.orientation == orientation) { return; }
		this.orientation = orientation;
		recalcListState();
	}
	
	public final boolean isOrientation(Orientation orientation) {
		return this.orientation == orientation;
	}
	
	public final Orientation getOrientation() {
		return orientation;
	}
	
	public final void setHorizontalMode() {
		orientation = Orientation.HORIZONTAL;
		recalcListState();
	}
	
	public final void setVerticalMode() {
		orientation = Orientation.VERTICAL;
		recalcListState();
	}
	
	public final void swapModes() {
		if(orientation == Orientation.HORIZONTAL) { orientation = Orientation.VERTICAL; } else { orientation = Orientation.HORIZONTAL; }
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
		add(new TextView(text,getX(),getY(),getWidth(),weight),weight);
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
		if(elementList == null) { return; }
		
		for(int i = 0; i < elementList.size(); i++) {
	  		Bounds bounds = elementList.get(i);

	  		switch(orientation) {
	  		
		  		case HORIZONTAL :
					if(i == 0) {
					bounds.setBounds(getX(),getY(),getWidth()*weightList.get(i),getHeight());
					} else {
						bounds.setBounds(elementList.get(i-1).getX()+elementList.get(i-1).getWidth(),getY(),getWidth()*weightList.get(i),getHeight());
					}
		  		break;
		  		
		  		
		  		case VERTICAL:
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