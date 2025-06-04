package microui.component;

import microui.core.AbstractButton;
import microui.event.EventType;

public class CheckBox extends AbstractButton {
	private boolean included;
	
	public CheckBox(float x, float y, float w, float h) {
		super(x,y,w,h);
		
		fill.set(0,200,0);
		hover.fill.set(0,10);
		
		eventCallback.addListener(EventType.CLICKED, () -> included = !included);
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
