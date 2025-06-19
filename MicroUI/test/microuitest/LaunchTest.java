package microuitest;

import microui.MicroUI;
import microui.component.Button;
import microui.event.EventType;
import microui.event.Listener;
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
		
	
		Listener listener = () -> {
			background(random(255));
		};
		
		component.callback.addListener(EventType.CLICKED , listener);
		
		component.callback.remove(listener);
	}
	
	@Override
	public void draw() {
		
		component.draw();
		
		GlobalTooltip.draw();
	}
	
}