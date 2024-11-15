package microUI.test;

import microUI.Button;
import microUI.layouts.ColumnLayout;
import microUI.layouts.GridLayout;
import microUI.layouts.RowLayout;
import processing.core.PApplet;

public class Main extends PApplet {
	
	GridLayout grid;
	
	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");
	}
	
	public void settings() { size(600,400); }
	
	public void setup() {
		grid = new GridLayout(this,3,1);
		grid.add(new ColumnLayout(this).add(new Button(this), .1f).add(new RowLayout(this).add(new Button(this), .5f), .9f), 0, 0);
		grid.setVisibleTotal(false);
	}
	
	public void draw() {
		background(234);
		grid.draw();
		if(mouseButton == RIGHT) { grid.setSize(mouseX, mouseY); }
	}
	
}