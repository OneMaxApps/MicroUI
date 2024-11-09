package microUI.test;

import microUI.Button;
import microUI.layouts.EdgeLayout;
import microUI.layouts.GridLayout;
import processing.core.PApplet;

public class Main extends PApplet {
	public EdgeLayout edge;
	
	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");
	}
	
	public void settings() { size(400,400); }
	
	public void setup() {
		edge = new EdgeLayout(this);
		edge.margin.set(10);
		edge.set(new GridLayout(this,0,0,200,200,3,3).add(new Button(this), 1, 1));
		edge.setUp(true).setRight(true);
	}
	
	public void draw() {
		background(128);
		edge.draw();
		if(mouseButton == LEFT) {
			edge.setSize(mouseX, mouseY);
		}
		
		if(mouseButton == RIGHT) {
			edge.setPosition(mouseX, mouseY);
		}
	}
	
}