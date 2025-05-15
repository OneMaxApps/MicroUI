package microUI;

import microUI.component.Button;
import microUI.event.Event;
import microUI.util.Metrics;
import processing.core.PApplet;
import processing.event.MouseEvent;

public final class LaunchTest extends PApplet {
	
	private Button component;
	
	public static void main(String[] args) {
		PApplet.main("microUI.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		component = new Button();
		Metrics.printAll();
	}
	
	@Override
	public void draw() {
		background(128);
		component.draw();
		
	}

	@Override
	public void keyPressed() {
		Event.keyPressed();
		// component.keyPressed();
	}
	
	@Override
	public void keyReleased() {
		Event.keyReleased();
	}
	
	@Override
	public void mouseWheel(final MouseEvent event) {
		// component.mouseWheel(event);
	}
}