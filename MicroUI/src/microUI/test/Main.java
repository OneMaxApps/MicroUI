package microUI.test;

import microUI.Button;
import microUI.MicroUI;
import microUI.layouts.GridLayout;
import processing.core.PApplet;

public class Main extends PApplet {
	Button button;
	
	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");

	}
	
	@Override
	public void settings() { size(400,400); }

	@Override
	public void setup() {
		MicroUI.init(this);
		button = new Button();
		System.out.println(MicroUI.getCreatedFormsCount());
	}
	
	@Override
	public void draw() {
		background(128);
		button.draw();
		
		if(mouseButton == RIGHT) { button.setSize(mouseX, mouseY); }
	}
}
