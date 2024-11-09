package microUI.test;

import microUI.layouts.GridLayout;
import microUI.utils.View;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class Main extends PApplet {
	public GridLayout grid;
	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");
	}
	
	public void settings() { size(400,400); }
	
	public void setup() {
		grid = new GridLayout(this,3,3).add(new View(this,loadImage("button_texture.JPG")), 1, 1);
		
	}
	
	public void draw() {
		background(128);
		grid.draw();
		// if(mouseButton == LEFT) { grid.setSize(mouseX, mouseY); }
	}
	
	public void mouseWheel(MouseEvent e) {
		grid.mouseWheelInit(e);
	}
}