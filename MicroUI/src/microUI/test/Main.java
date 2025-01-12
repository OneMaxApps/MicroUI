package microUI.test;

import microUI.layouts.GridLayout;
import processing.core.PApplet;

public class Main extends PApplet {
	GridLayout grid;
	
	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");

	}
	
	@Override
	public void settings() { size(400,400); }

	@Override
	public void setup() {
		grid = new GridLayout(this,3);
	}
	
	@Override
	public void draw() {
		background(128);
		grid.draw();
	}
	
}
