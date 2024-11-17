package microUI.test;

import microUI.layouts.ColumnLayout;
import microUI.layouts.EdgeLayout;
import microUI.layouts.GridLayout;
import processing.core.PApplet;

// make margin working correct for all and special in GridLayout
public class Margin extends PApplet {
	ColumnLayout columnLayout;
	
	public static void main(String[] args) {
		PApplet.main("microUI.test.Margin");
	}
	
	@Override
	public void settings() { size(400,400); }
	
	
	@Override
	public void setup() {
		columnLayout = new ColumnLayout(this);
		columnLayout.margin.set(10);
		
		
		EdgeLayout edge = new EdgeLayout(this);
		edge.margin.set(10);
		
		columnLayout.add(edge, 1f);
		
	}
	
	@Override
	public void draw() {
		background(232);
		columnLayout.draw();
		if(mouseButton == RIGHT) { columnLayout.setSize(mouseX, mouseY); }
	}
	
}
