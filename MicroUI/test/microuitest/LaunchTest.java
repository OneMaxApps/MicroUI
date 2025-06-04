package microuitest;

import microui.MicroUI;
import microui.component.MenuButton;
import microui.event.EventType;
import microui.service.GlobalTooltip;
import processing.core.PApplet;
import processing.event.MouseEvent;

public final class LaunchTest extends PApplet {
	MenuButton component;
	
	public static void main(String[] args) {
		PApplet.main("microuitest.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		component = new MenuButton();
		component.eventCallback.addOnClickListener(() -> component.fill.set(random(255)));
		component.eventCallback.remove(EventType.CLICKED,2);
	}
	
	@Override
	public void draw() {
		background(128);
		
		component.draw();
		
		GlobalTooltip.draw();
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		component.mouseWheel(event);
	}
	
	
}