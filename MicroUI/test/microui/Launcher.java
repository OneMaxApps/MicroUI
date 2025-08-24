package microui;

import microui.component.Button;
import microui.service.GlobalTooltip;
import processing.core.PApplet;

// TODO Check what's methods and objects must be a part of API 
public class Launcher extends PApplet {
	Button button;
	
	public static void main(String[] args) {
		PApplet.main("microui.Launcher");
	}
	
	@Override
	public void settings() {
		size(640,480);
	}

	@Override
	public void setup() {
		MicroUI.setContext(this);
		button = new Button();
	}

	@Override
	public void draw() {
		background(200);
		button.draw();
		
		GlobalTooltip.draw();
	}
	
}