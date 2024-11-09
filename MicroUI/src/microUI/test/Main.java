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
		grid.margin.set(10);
		grid.add(new Button(this), 0,0);
		grid.add(new EdgeLayout(this).set(new Button(this,"Button inside EdgeLayout",0,0,100,50)), 1,0);
		grid.add(new Slider(this), 2,0);
		grid.add(new Scroll(this), 1,1);
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