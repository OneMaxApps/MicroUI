package microUI.layouts;

import static processing.core.PApplet.constrain;

import java.util.ArrayList;

import microUI.MicroUI;
import microUI.utils.BaseForm;
import processing.core.PApplet;


public class RowLayout extends Layout {
	private ArrayList<Float> weightList;
	
	public RowLayout(float x, float y, float w, float h) {
		super(MicroUI.app, x, y, w, h);
		weightList = new ArrayList<Float>();
	}
	
	public RowLayout() {
		this(0,0, MicroUI.app.width, MicroUI.app.height);
	}

	@Override
	public void draw() {
		super.draw();
		if(!elementList.isEmpty()) {
			for(int i = 0; i < elementList.size(); i++) {
				BaseForm form = elementList.get(i);
					if(i == 0) {
					form.setTransforms(getX(),getY(),getW()*weightList.get(i),getH());
					} else {
						form.setTransforms(elementList.get(i-1).getX()+elementList.get(i-1).getW(),getY(),getW()*weightList.get(i),getH());
					}
				
				form.draw();
			}
		};
	}
	
	public RowLayout add(BaseForm form, float weight) {
		elementList.add(form);
		
		if(weightList.isEmpty()) {
			weightList.add(constrain(weight,0,1f));
		} else {
		
		float maxSize = 0f;
		for(int i = 0; i < elementList.size()-1; i++) {
			maxSize += weightList.get(i);
		}
		
		weightList.add(constrain(weight,0,1f-maxSize));
		}
		return this;
	}
	
	public void remove(int index) {
		if(index < 0 && index > elementList.size()-1) { throw new IndexOutOfBoundsException("Index out of bounds of Row exception"); }
		elementList.remove(index);
		weightList.remove(index);
		
	}
}