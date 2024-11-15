package microUI.test;

import microUI.Spinner;
import microUI.layouts.GridLayout;
import processing.core.PApplet;

public class Main extends PApplet {
	
	GridLayout grid;
	Spinner spinner;
	
	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");
	}
	
	public void settings() { size(400,400); }
	
	public void setup() {
		grid = new GridLayout(this,3,9);
		grid.add(new Spinner(this,"Spinner").add("One","Two","Three"), 2,1);
	}
	
	public void draw() {
		background(234);
		grid.draw();
	}
	
}