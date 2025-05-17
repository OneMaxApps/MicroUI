package microUI;

import microUI.component.TextField;
import microUI.core.Constants;
import microUI.core.GlobalTooltip;
import microUI.core.MicroUI;
import microUI.event.Event;
import processing.core.PApplet;
import processing.event.MouseEvent;

public final class LaunchTest extends PApplet {
	
	TextField component;
	
	public static void main(String[] args) {
		PApplet.main("microUI.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		
		component = new TextField();
		component.tooltip.text.set("Hello i'm "+component.getClass().getSimpleName());
		component.text.setHint("Input your phone:");
		component.text.setValidationMode(Constants.VALIDATION_ONLY_DIGITS);
		component.text.setConstrain(true);
		component.text.setMaxChars(-11);
		
		//Metrics.printAll();
		//exit();
	}
	
	@Override
	public void draw() {
		background(200);
		
		component.draw();
		
		// Metrics.printAll();
		
		GlobalTooltip.draw();
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		// component.mouseWheel(event);
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