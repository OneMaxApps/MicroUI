package microUI;

import microUI.utils.Rectangle;
import processing.core.PApplet;

public class CheckBox extends Rectangle {
	private boolean included;
	
	public CheckBox(PApplet app, float x, float y, float w, float h) {
		super(app, x, y, w, h);
	}
	
	public CheckBox(PApplet app, boolean include) {
		this(app);
		setIncluded(include);
	}
	
	public CheckBox(PApplet app) {
		super(app, app.width*.4f,app.height*.4f,app.width*.2f,app.height*.2f);
	}
	
	
	
	@Override
	public void draw() {
		if(event.clicked()) { included = !included; }
		super.draw();
		if(included) { fill.set(0xFF8888FF); } else { fill.set(0xFF444444); }
	}

	public boolean isIncluded() {
		return included;
	}

	public void setIncluded(boolean included) {
		this.included = included;
	}

}
