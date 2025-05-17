package microUI.component;

import microUI.core.AbstractButton;
import microUI.event.Event;

public class CheckBox extends AbstractButton {
	private final Event eventInside;
	private boolean included;
	
	public CheckBox(float x, float y, float w, float h) {
		super(x,y,w,h);
		
		fill.set(0,200,0);
		hover.fill.set(0,10);
		
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
		
		if(!included) {
			app.pushStyle();
			app.fill(0,128);
			app.rect(x, y, w, h);
			app.popStyle();
		}
	}
	
	public boolean isIncluded() {
		return included;
	}

	public void setState(boolean state) {
		included = state;
	}

}
