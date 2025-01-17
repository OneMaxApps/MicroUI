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
		editText = new EditText(this,100,100,200,50);
	}
	
	@Override
	public void draw() {
		background(128);
		editText.draw();
	}

	@Override
	public void keyPressed() {
		editText.keyPressed();
	}

}
