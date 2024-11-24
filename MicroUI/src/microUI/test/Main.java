package microUI.test;

import microUI.Button;
import microUI.layouts.ColumnLayout;
import microUI.layouts.EdgeLayout;
import microUI.layouts.GridLayout;
import microUI.layouts.RowLayout;
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
		grid.add(new GridLayout(this,3).add(new GridLayout(this,3), 1, 1), 1, 1);
		EdgeLayout edge;
		grid.add(edge = new EdgeLayout(this), 0, 0);
		edge.set(new GridLayout(this,4));
		edge.margin.set(10);
		grid.add(new RowLayout(this).add(new Button(this), .1f), 1, 0);
		grid.add(new ColumnLayout(this).add(new Button(this), .2f), 2, 0);
		
		grid.margin.set(10);
		grid.setVisibleTotal(false);
	}
	
	@Override
	public void draw() {
		background(128);
		grid.draw();
		
	}
}
