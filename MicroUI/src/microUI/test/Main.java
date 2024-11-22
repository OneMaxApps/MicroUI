package microUI.test;

import microUI.CheckBox;
import microUI.layouts.GridLayout;
import microUI.utils.FX;
import processing.core.PApplet;

public class Main extends PApplet {
	GridLayout grid;
	CheckBox cb;
	FX fx;
	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");
	}
	
	public void settings() { size(400,400); }
	
	public void setup() {
		grid = new GridLayout(this,3);
		grid.margin.set(10);
		grid.initShadow();
		
		grid.add(cb = new CheckBox(this), 1, 1);
		cb.image.setImage(loadImage("play.png"));
		cb.colorIncluded.set(0,234,0);
		cb.shadowDestroy();
		
		fx = new FX(this);
		fx.add(cb);
	}
	
	public void draw() {
		background(234);
		grid.draw();
		cb.event.clicked();
		fx.init();
		if(mouseButton == RIGHT)
		grid.setSize(mouseX, mouseY);
	}
	
}