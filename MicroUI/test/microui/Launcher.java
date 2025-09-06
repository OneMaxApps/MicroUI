package microui;

import microui.component.Button;
import processing.core.PApplet;

public final class Launcher extends PApplet {
	Button button;
	
	public static void main(String[] args) {
		PApplet.main("microui.Launcher");
	}

	@Override
	public void settings() {
		size(400,400);
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
		
		if(mouseButton == RIGHT) {
			button.setPosition(mouseX,mouseY);
		}
		
		//Metrics.printAll();
	}
	
}