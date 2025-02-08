package microUI.test;

import microUI.Window;
import processing.core.PApplet;

public class Main extends PApplet {
	Window component;
	
	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");

	}
	
	@Override
	public void settings() { size(800,800); }
	
	@Override
	public void setup() {
		component = new Window(this,"Window");

	}
	
	@Override
	public void draw() {
		background(128);
		component.draw();
		
		if(mouseButton == RIGHT) { component.setSize(mouseX, mouseY); }
	}
	
	@Override
	public void keyPressed() {
		// component.keyPressed();
	}
}