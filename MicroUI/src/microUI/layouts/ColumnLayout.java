package microUI.layouts;

import static processing.core.PApplet.constrain;

import java.util.ArrayList;

import microUI.utils.BaseForm;
import processing.core.PApplet;

public class ColumnLayout extends Layout {
	private ArrayList<BaseForm> elementList;
	private ArrayList<Float> weightList;

	public ColumnLayout(PApplet app, float x, float y, float w, float h) {
		super(app, x, y, w, h);
		elementList = new ArrayList<BaseForm>();
		weightList = new ArrayList<Float>();
	}
	
	public ColumnLayout(PApplet app) {
		this(app, 0,0, app.width, app.height);
	}

	@Override
	public void draw() {
		super.draw();
		if(!elementList.isEmpty()) {
			for(int i = 0; i < elementList.size(); i++) {
				BaseForm form = elementList.get(i);
				if(i == 0) {
				form.setTransforms(getX(),getY(),getW(),getH()*weightList.get(0));
				} else {
					form.setTransforms(getX(),elementList.get(i-1).getY()+elementList.get(i-1).getH(),getW(),getH()*weightList.get(i));
				}
				form.draw();
			}
		}
		
	}
	
	public ColumnLayout add(BaseForm form, float weight) {
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
