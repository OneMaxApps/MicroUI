package microUI;

import microUI.component.EditText;
import microUI.core.GlobalTooltip;
import microUI.core.MicroUI;
import microUI.event.Event;
import microUI.util.Metrics;
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
		
		component = new EditText();
		component.tooltip.text.set("Hello i'm "+component.getClass());
		Metrics.printAll();
		
		// exit();
	}
	
	@Override
	public void draw() {
		background(200);
		final long start = System.currentTimeMillis();
		
		component.draw();
		
		println(start-System.currentTimeMillis());
		
		GlobalTooltip.draw();
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		component.mouseWheel(event);
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
	
	
}