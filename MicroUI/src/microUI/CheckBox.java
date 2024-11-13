package microUI;

import microUI.utils.Color;
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
		super(app, app.width*.4f,app.height*.4f,app.width*.2f,app.height*.2f);
		colorIncluded = new Color(app);
		colorNotIncluded = new Color(app);
		colorIncluded.set(app.color(0,128,0));
		colorNotIncluded.set(app.color(64,0,0));
	}
	
	
	
	@Override
	public void draw() {
		if(event.clicked()) { included = !included; }
		if(included) { fill.set(colorIncluded.get()); } else { fill.set(colorNotIncluded.get()); }
		super.draw();
		
	}

	public boolean isIncluded() {
		return included;
	}

	public void setIncluded(boolean included) {
		this.included = included;
	}

}
