package microUI.component;

import microUI.core.AbstractButton;
import microUI.event.Event;
import microUI.util.Color;

public class CheckBox extends AbstractButton {
	public final Color colorIncluded,colorNotIncluded;
	private final Event eventInside;
	private boolean included;
	
	
	public CheckBox(float x, float y, float w, float h) {
		super(x,y,w,h);
		colorIncluded = new Color();
		colorNotIncluded = new Color();
		colorIncluded.set(0,128,0);
		colorNotIncluded.set(0,34,34);
		eventInside = new Event();
	}
	
	public CheckBox(boolean include) {
		this();
		setState(include);
	}
	
	public CheckBox() {
		this(app.width*.4f,app.height*.4f,app.width*.2f,app.height*.2f);
	}
	
	@Override
	public void update() {
		super.update();	
		eventInside.listen(this);
		if(eventInside.clicked()) { included = !included; }
		if(included) { fill.set(colorIncluded); } else { fill.set(colorNotIncluded); }
	}
	
	public boolean isIncluded() {
		return included;
	}

	public void setState(boolean state) {
		included = state;
	}

}
