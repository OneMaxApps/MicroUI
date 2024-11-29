package microUI;

import static processing.core.PApplet.min;

import microUI.utils.Color;
import microUI.utils.Event;
import microUI.utils.Rectangle;
import processing.core.PApplet;

public class CheckBox extends Rectangle {
	private boolean included;
	
	public Color colorIncluded,colorNotIncluded;
	
	public CheckBox(PApplet app, float x, float y, float w, float h) {
		this(app);
		setTransforms(x,y,w,h);
	}
	
	public CheckBox(PApplet app, boolean include) {
		this(app);
		setIncluded(include);
	}
	
	public CheckBox(PApplet app) {
		super(app,app.width*.4f,app.height*.4f,app.width*.2f,app.height*.2f);
		colorIncluded = new Color(app);
		colorNotIncluded = new Color(app);
		colorIncluded.set(0,128,0);
		colorNotIncluded.set(0,34,34);
		event = new Event(app) {
			@Override
			public void action() {
				included = !included;
			}
		};
	}
	
	
	
	@Override
	public void draw() {
		
		if(image.isLoaded()) {
			if(included) { image.tint.set(colorIncluded); } else { image.tint.set(colorNotIncluded); }
		} else {
			if(included) { fill.set(colorIncluded); } else { fill.set(colorNotIncluded); }
		}
		super.draw();
		
	}
	
	@Override
	public void setSize(float w, float h) {
		float size = min(w,h);
		super.setSize(size,size);
	}
	
	
	
	@Override
	public void setTransforms(float x, float y, float w, float h) {
		float size = min(w,h);
		super.setTransforms(x, y, size,size);
	}
	
	

	public boolean isIncluded() {
		return included;
	}

	public void setIncluded(boolean included) {
		this.included = included;
	}

}
