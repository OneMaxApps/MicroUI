package microui;

import microui.component.Scroll;
import microui.event.Event;
import processing.core.PApplet;

public final class Launcher extends PApplet {

	Scroll component;
	
	public static void main(String[] args) {
		PApplet.main("microui.Launcher");
	}

	@Override
	public void settings() {
		size(640, 480);
	}

	@Override
	public void setup() {
		MicroUI.setContext(this);
		
		component = new Scroll();
		
	}

	@Override
	public void draw() {
		background(200);
		
		component.draw();
		
		if(mouseButton == RIGHT) {
			component.setSize(mouseX,mouseY);
		}
	}

	@Override
	public void mousePressed() {

	}

	@Override
	public void keyPressed() {
		Event.keyPressed();
	}

	@Override
	public void keyReleased() {
		Event.keyReleased();
	}

}