package microUI;

import microUI.component.EditText;
import microUI.component.TextField;
import microUI.event.Event;
import processing.core.PApplet;

public final class LaunchTest extends PApplet {

	TextField textField;
	EditText editText;
	
	public static void main(String[] args) {
		PApplet.main("microUI.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		textField = new TextField();
		
		textField.text.setHint("you my love:)");
		textField.text.font.set(createFont("C:\\Windows\\Fonts\\consola.ttf",32));
		textField.fill.set(128,200,200);
		textField.text.fill.set(255,0,255,128);
		textField.cursor.fill.set(255,200);
		textField.selection.fill.set(0,200,0,128);
		textField.text.size.set(24);
		
		editText = new EditText();
	}
	
	@Override
	public void draw() {
		background(128);
		editText.draw();
		
	}

	@Override
	public void keyPressed() {
		Event.keyPressed(this);
		editText.keyPressed();
	}
	
	@Override
	public void keyReleased() {
		Event.keyReleased();
	}
	
}