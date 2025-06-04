package microuitest;

import static microui.event.EventType.CLICKED;
import static microui.event.EventType.HOLDING;
import static microui.event.EventType.LONG_PRESSED;

import microui.MicroUI;
import microui.component.Button;
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
		component.eventCallback.addListener(CLICKED, () -> background(random(255)));
		component.eventCallback.addListener(LONG_PRESSED, () -> component.fill.set(random(255)));
		component.eventCallback.addListener(HOLDING, () -> {
			if(mouseButton == RIGHT) {
				component.setSize(mouseX,mouseY);
			}
		});
		
	}
	
	@Override
	public void draw() {
		//background(128);
		
		component.draw();
		
		GlobalTooltip.draw();
	}
	
}