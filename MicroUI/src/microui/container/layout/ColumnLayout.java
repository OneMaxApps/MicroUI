package microui.container.layout;

import static processing.core.PApplet.constrain;

import java.util.ArrayList;

import microui.component.TextView;
import microui.core.base.Bounds;
import microui.core.base.Layout;


public class ColumnLayout extends Layout {
	private final ArrayList<Float> weightList;

	public ColumnLayout(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		weightList = new ArrayList<Float>();
		
	}
	
	public ColumnLayout() {
		this(0,0,app.width,app.height);
	}

	@Override
	public void draw() {
		super.draw();
		drawElements();
	}
	
	public ColumnLayout add(Bounds bounds, float weight) {
		elementList.add(bounds);
		
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
	
	public ColumnLayout add(String text, float weight) {
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
		if(elementList == null) { return; }
		
		for(int i = 0; i < elementList.size(); i++) {
	  		Bounds bounds = elementList.get(i);
			if(i == 0) {
				bounds.setBounds(getX(),getY(),getWidth()*weightList.get(i),getHeight());
			} else {
				bounds.setBounds(elementList.get(i-1).getX()+elementList.get(i-1).getWidth(),getY(),getWidth()*weightList.get(i),getHeight());
			}
	  	}
	}
}