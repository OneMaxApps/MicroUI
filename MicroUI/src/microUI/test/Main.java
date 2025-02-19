package microUI.test;

import microUI.Button;
import processing.core.PApplet;

public class Main extends PApplet {
	Button button;
	
	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");

	}
	
	@Override
	public void settings() { size(800,800); }
	
	@Override
	public void setup() {
		button = new Button(this);
	}
		
	@Override
	public void draw() {
		background(128);
		println(frameRate);
		button.draw();
		
	}

}