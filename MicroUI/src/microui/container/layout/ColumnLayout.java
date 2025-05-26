package microui.container.layout;

import static processing.core.PApplet.constrain;

import java.util.ArrayList;

import microui.component.TextView;
import microui.core.base.Bounds;
import microui.core.base.Layout;

public class ColumnLayout extends Layout {
	private final ArrayList<Float> weightList;
	private final Transforming transforming;
	
	public ColumnLayout(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		weightList = new ArrayList<Float>();
		transforming = new Transforming() {
			@Override
			public final void updateForce() {
			  	for(int i = 0; i < elementList.size(); i++) {
			  		Bounds baseForm = elementList.get(i);
			  		if(isElementsResizable()) {
						if(i == 0) {
						baseForm.setTransforms(getX(),getY(),getW(),getH()*weightList.get(0));
						} else {
						baseForm.setTransforms(getX(),elementList.get(i-1).getY()+elementList.get(i-1).getH(),getW(),getH()*weightList.get(i));
						}
			  		} else {
			  			if(i == 0) {
							baseForm.setPosition(getX(), getY());
							} else {
							baseForm.setPosition(getX(), elementList.get(i-1).getY()+elementList.get(i-1).getH());
							}
			  		}
			  	}
		 }
		};
		isElementsResizable = true;
	}
	
	public ColumnLayout() {
		this(0,0, app.width, app.height);
	}
	
	@Override
	public void draw() {
		super.draw();
		update();
	}

	@Override
	public void update() {
		if(visible) {
			super.update();
		}
		
		if(!elementList.isEmpty()) {	
			elementList.forEach(element -> element.draw());
		}
		
		if(app.frameCount == 1 || app.frameCount%60*60 == 0) { transforming.updateForce(); }

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
		
		transforming.updateForce();
		
		return this;
	}
	
	public ColumnLayout add(String text, float weight) {
		add(new TextView(text,x,y,w,weight),weight);
		
		return this;
	}
	
	public void remove(int index) {
		if(index < 0 || index > elementList.size()-1) { throw new IndexOutOfBoundsException("Index out of bounds of Column exception"); }
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