package microUI.test;

import microUI.InputText;
import processing.core.PApplet;

public class Main extends PApplet {
	InputText inputText;
	
	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");

	}
	
	@Override
	public void settings() { size(400,400); }

	@Override
	public void setup() {
		inputText = new InputText(this);

	}
	
	@Override
	public void draw() {
		background(128);
		inputText.draw();
	}
}
