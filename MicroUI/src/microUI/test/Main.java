package microUI.test;

import microUI.Button;
import microUI.CheckBox;
import microUI.CircleSeekBar;
import microUI.Scroll;
import microUI.Slider;
import microUI.layouts.GridLayout;
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
		fx.buttons.after.fill.set(color(234,234,0));
		fx.add(button,button);
		
		}
	public void draw() {
		background(128);
		fx.init();
		button.draw();
	}
	
}