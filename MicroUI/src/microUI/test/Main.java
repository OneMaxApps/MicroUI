package microUI.test;

import microUI.util.Color;
import processing.core.PApplet;

public class Main extends PApplet {

	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");

	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		background(128);
		Color c = new Color(255,0,0,0);
		System.out.println(c.getRed()+" "+c.getGreen()+" "+c.getBlue()+" "+c.getAlpha());
		background(c.get());
	}
		/*
	@Override
	public void draw() {
		background(128);
	}*/
	
}