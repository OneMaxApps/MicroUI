package microUI.test;

import microUI.Editor;
import processing.core.PApplet;

public class Main extends PApplet {
	Editor editor;
	
	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");

	}
	
	@Override
	public void settings() { size(800,800); }

	
	@Override
	public void setup() {
		editor = new Editor(this);
		
	}
	
	@Override
	public void draw() {
		background(128);
		editor.draw();
		
	}

	@Override
	public void keyPressed() {
		editor.keyPressed();
	}

}
