package microui;

import microui.component.EditText;
import microui.event.Event;
import microui.service.GlobalTooltip;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

// TODO Check what's methods and objects must be a part of API 
public class Launcher extends PApplet {
	EditText component;
	
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
		
		component = new EditText();
		
	}

	@Override
	public void draw() {
		background(200);
		component.draw();
		
		GlobalTooltip.draw();
		
	}

	@Override
	public void keyPressed(KeyEvent event) {
		Event.keyPressed();
		
		component.keyPressed();
	}

	@Override
	public void keyReleased() {
		Event.keyReleased();
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		component.mouseWheel(event);
	}
	
	
}