package microUI.layout;

import static processing.core.PApplet.constrain;

import java.util.ArrayList;

import microUI.util.BaseForm;
import microUI.util.Text;
import processing.core.PApplet;

public class ColumnLayout extends Layout {
	private final ArrayList<Float> weightList;
	private final Transforming transforming;
	
	public ColumnLayout(PApplet app, float x, float y, float w, float h) {
		super(app, x, y, w, h);
		weightList = new ArrayList<Float>();
		transforming = new Transforming();
		isElementsResizable = true;
	}
	
	public ColumnLayout(PApplet app) {
		this(app,0,0, app.width, app.height);
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
	
	
	
	public ColumnLayout add(BaseForm form, float weight) {
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
		add(new Text(app,text,x,y,w,weight),weight);
		
		return this;
	}
	
	public void remove(int index) {
		if(index < 0 || index > elementList.size()-1) { throw new IndexOutOfBoundsException("Index out of bounds of Column exception"); }
		elementList.remove(index);
		weightList.remove(index);
		transforming.updateForce();
	}

	
	public final class Transforming {
		  private float layX,layY,layW,layH;
		  
		  public final void autoUpdate() {
			  if(layX != x) {
				  layX = x;
				  updateForce();
			  }
			  
			  if(layY != y) {
				  layY = y;
				  updateForce();
			  }
			  
			  if(layW != w) {
				  layW = w;
				  updateForce();
			  }
			  
			  if(layH != h) {
				  layH = h;
				  updateForce();
			  }
		  }
		  
		  
		  public final void updateForce() {
			  	for(int i = 0; i < elementList.size(); i++) {
			  		BaseForm baseForm = elementList.get(i);
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