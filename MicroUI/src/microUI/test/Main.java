package microUI.test;

import microUI.Button;
import microUI.CheckBox;
import microUI.CircleSeekBar;
import microUI.Scroll;
import microUI.Slider;
import microUI.Spinner;
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
		grid = new GridLayout(this,3).add(new GridLayout(this,3).
		add(new Button(this), 0, 0).
		add(new CheckBox(this), 1, 0).
		add(new CircleSeekBar(this), 2, 0).
		add(new Scroll(this), 0, 1).
		add(new Slider(this), 1, 1).
		add(new Spinner(this), 2, 1),1,1);
		
	}
	
	@Override
	public void draw() {
		background(128);
		grid.draw();
		if(mouseButton == RIGHT) { grid.setSize(mouseX, mouseY); }
	}
}
