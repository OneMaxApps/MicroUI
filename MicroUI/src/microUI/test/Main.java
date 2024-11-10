package microUI.test;

import microUI.Button;
import microUI.utils.FX;
import processing.core.PApplet;

public class Main extends PApplet {
	Button button;
	FX fx;
	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");
	}
	
	public void settings() { size(400,400); }
	
	public void setup() {
		button = new Button(this);
		fx = new FX(this);
		fx.add(button);
	}
	public void draw() {
		background(128);
		fx.init();
		button.draw();
	
	}
	
}