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
		isElementsResizable = true;
		
	}
	
	public ColumnLayout() {
		this(0,0, app.width, app.height);
	}
	
	@Override
	public void draw() {
		super.draw();
		if(!elementList.isEmpty()) {	
			elementList.forEach(element -> element.draw());
		}
	}
	
	public ColumnLayout add(Bounds form, float weight) {
		elementList.add(form);
		
		if(weightList.isEmpty()) {
			weightList.add(constrain(weight,0,1f));
		} else {
		
		float maxSize = 0f;
		for(int i = 0; i < weightList.size()-1; i++) {
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
		if(index < 0 || index >= elementList.size()) { throw new IndexOutOfBoundsException("Index out of bounds of Column exception"); }
		elementList.remove(index);
		weightList.remove(index);
		recalcListState();
	}
	
	@Override
	protected final void recalcListState() {
		for(int i = 0; i < elementList.size(); i++) {
	  		Bounds baseForm = elementList.get(i);
	  		if(isElementsResizable()) {
				if(i == 0) {
				baseForm.setBounds(getX(),getY(),getWidth(),getHeight()*weightList.get(0));
				} else {
				baseForm.setBounds(getX(),elementList.get(i-1).getY()+elementList.get(i-1).getHeight(),getWidth(),getHeight()*weightList.get(i));
				}
	  		} else {
	  			if(i == 0) {
					baseForm.setPosition(getX(), getY());
					} else {
					baseForm.setPosition(getX(), elementList.get(i-1).getY()+elementList.get(i-1).getHeight());
					}
	  		}
	  	}
	}
}