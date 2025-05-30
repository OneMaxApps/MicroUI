package microui.container.layout;

import static processing.core.PApplet.constrain;

import java.util.ArrayList;

import microui.component.TextView;
import microui.core.base.Bounds;
import microui.core.base.Layout;


public class RowLayout extends Layout {
	private final ArrayList<Float> weightList;

	public RowLayout(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		weightList = new ArrayList<Float>();
		
		isElementsResizable = true;
	}
	
	public RowLayout() {
		this(0,0,app.width,app.height);
	}

	@Override
	public void draw() {
		super.draw();
		if(!elementList.isEmpty()) {
			elementList.forEach(element -> element.draw());
		};
	}
	
	public RowLayout add(Bounds baseRectanle, float weight) {
		elementList.add(baseRectanle);
		
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
	
	public RowLayout add(String text, float weight) {
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
	  		if(isElementsResizable()) {
				if(i == 0) {
				bounds.setTransforms(getX(),getY(),getW()*weightList.get(i),getH());
				} else {
					bounds.setTransforms(elementList.get(i-1).getX()+elementList.get(i-1).getW(),getY(),getW()*weightList.get(i),getH());
				}
	  		} else {
	  			if(i == 0) {
					bounds.setPosition(getX(),getY());
				} else {
					bounds.setPosition(elementList.get(i-1).getX()+elementList.get(i-1).getW(),getY());
				}
	  		}
	  	}
	}
}