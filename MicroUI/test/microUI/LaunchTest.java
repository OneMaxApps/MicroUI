package microUI;

import microUI.component.EditText;
import microUI.event.Event;
import microUI.service.GlobalTooltip;
import processing.core.PApplet;
import processing.event.MouseEvent;

public final class LaunchTest extends PApplet {
	
	EditText component;
	
	public static void main(String[] args) {
		PApplet.main("microUI.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		// Metrics.printAll();
		
		component = new EditText();
		component.tooltip.text.set("It's yout working place");
	}
	
	@Override
	public void draw() {
		background(0);
		
		component.draw();
		
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