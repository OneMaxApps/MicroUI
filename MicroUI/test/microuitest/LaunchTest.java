package microuitest;

import microui.MicroUI;
import microui.component.EditText;
import microui.event.Event;
import microui.service.GlobalTooltip;
import processing.core.PApplet;
import processing.event.MouseEvent;

public final class LaunchTest extends PApplet {
	
	EditText component;
	
	public static void main(String[] args) {
		PApplet.main("microuitest.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);

		component = new EditText();
		component.tooltip.text.set("It's your working place");
		component.createFont("C:\\Windows\\Fonts\\consolai.ttf");
	}
	
	@Override
	public void draw() {
		background(0);
		
		component.draw();
		
		//Metrics.printAll();
		GlobalTooltip.draw();
	}

	@Override
	public void keyPressed() {
		Event.keyPressed();
		
		component.keyPressed();
	}
	
	@Override
	public void keyReleased() {
		Event.keyReleased();
		
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		component.mouseWheel(event);
	}
	
	
}