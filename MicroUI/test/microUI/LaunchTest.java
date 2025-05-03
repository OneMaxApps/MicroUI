package microUI;

import microUI.component.TextField;
import microUI.event.Event;
import processing.core.PApplet;
import processing.event.MouseEvent;

public final class LaunchTest extends PApplet {
	
	private TextField textField;
	
	public static void main(String[] args) {
		PApplet.main("microUI.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		
		textField = new TextField();
		// textField.text.font.set(createFont("C:\\Windows\\Fonts\\BRUSHSCI.TTF",32));
		textField.text.set("Smartphone");
		// textField.text.size.set(80);
	}
		
	@Override
	public void draw() {
		background(128);
		textField.draw();
		if(mouseButton == RIGHT) {
			textField.setPosition(mouseX,mouseY);
		}
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

	@Override
	public void mouseWheel(MouseEvent event) {
		
	}

	
}