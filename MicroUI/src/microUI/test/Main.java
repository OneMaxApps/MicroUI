package microUI.test;

import microUI.Button;
import microUI.Scroll;
import microUI.Slider;
import microUI.layouts.EdgeLayout;
import microUI.layouts.GridLayout;
import processing.core.PApplet;

public class Main extends PApplet {
	public GridLayout grid;
	
	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");
	}
	
	public void settings() { size(400,400); }
	
	public void setup() {
		grid = new GridLayout(this,3);
		
	}
	
	public void draw() {
		background(128);
		grid.draw();
		if(mouseButton == LEFT) {
			grid.setSize(mouseX, mouseY);
		}
		
		if(mouseButton == RIGHT) {
			grid.setPosition(mouseX, mouseY);
		}
	}
	
}