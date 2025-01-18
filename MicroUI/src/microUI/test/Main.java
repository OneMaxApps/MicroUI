package microUI.test;

import microUI.EditText;
import processing.core.PApplet;

public class Main extends PApplet {
	EditText editText;
	
	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");

	}
	
	@Override
	public void settings() { size(800,800); }

	
	@Override
	public void setup() {
		editText = new EditText(this);
		//editText.setHint("Enter any digit");
		//editText.setEnterType(EditText.DIGITS);
		
		editText.fill.set(0);
		editText.text.fill.set(0,255,0);
		editText.cursor.fill.set(0,255,0,128);
		editText.select.fill.set(255,255,0,128);
		
	}
	
	@Override
	public void draw() {
		background(128);
		editText.draw();
		if(mouseButton == RIGHT) { editText.setW(mouseX); }
	}

	@Override
	public void keyPressed() {
		editText.keyPressed();
	}

}
