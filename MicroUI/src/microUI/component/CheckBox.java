package microUI.component;

import static processing.core.PApplet.min;

import microUI.core.AbstractButton;
import microUI.event.Event;
import microUI.util.Color;
import processing.core.PApplet;

public class CheckBox extends AbstractButton {
	public Color colorIncluded,colorNotIncluded;
	private boolean included;
	private final Event eventInside;
	
	public CheckBox(PApplet app, float x, float y, float w, float h) {
		super(app,x,y,w,h);
		colorIncluded = new Color();
		colorNotIncluded = new Color();
		colorIncluded.set(0,128,0);
		colorNotIncluded.set(0,34,34);
		eventInside = new Event(app);
	}
	
	public CheckBox(PApplet app, boolean include) {
		this(app);
		setIncluded(include);
	}
	
	public CheckBox(PApplet app) {
		this(app,app.width*.4f,app.height*.4f,app.width*.2f,app.height*.2f);
	}
	
	@Override
	public void update() {
		super.update();	
		eventInside.listen(this);
		if(event.clicked()) { included = !included; }
		if(included) { fill.set(colorIncluded); } else { fill.set(colorNotIncluded); }
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
