package microUI;

import microUI.component.TextField;
import microUI.event.Event;
import processing.core.PApplet;

public final class LaunchTest extends PApplet {

	TextField textField;
	
	public static void main(String[] args) {
		PApplet.main("microUI.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		textField = new TextField(0,0,400,100);
		textField.text.set("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
		
	}
		
	@Override
	public void draw() {
		background(128);
		textField.draw();
		
	}

	@Override
	public void keyPressed() {
		Event.keyPressed(this);
		textField.keyPressed();
	}
	
	@Override
	public void keyReleased() {
		Event.keyReleased();
	}
	
}