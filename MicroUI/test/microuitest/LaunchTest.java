package microuitest;

import microui.MicroUI;
import microui.component.Button;
import microui.event.EventType;
import microui.service.GlobalTooltip;
import processing.core.PApplet;

public final class LaunchTest extends PApplet {
	Button component;
	
	public static void main(String[] args) {
		PApplet.main("microuitest.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		
		component = new Button();
		
		// [1] CLICKED
		// [1] DOUBLE_CLICKED
		// [1] DRAGGED
		// [1] DRAGGING
		// [1] HOLDING
		// [1] INSIDE
		// [1] INSIDE_LONG
		// [1] OUTSIDE
		// [1] LONG_PRESSED
		// [1] PRESSED
		// [1] SHAKE
		
		component.callback.addListener(EventType.SHAKE, () -> background(random(255)));
		
	}
	
	@Override
	public void draw() {
		// background(128);
		
		component.draw();
		
		GlobalTooltip.draw();
	}
	
}