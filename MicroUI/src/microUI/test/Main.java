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
		editText = new EditText(this,100,350,600,100);
		
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
