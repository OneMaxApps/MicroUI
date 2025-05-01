package microUI;

import microUI.component.EditText;
import processing.core.PApplet;
import processing.event.MouseEvent;

public final class Main extends PApplet {
	
	private EditText component;
	
	public static void main(String[] args) {
		PApplet.main("microUI.Main");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		component = new EditText();
	}
		
	@Override
	public void draw() {
		background(128);
		component.draw();
	}

	@Override
	public void keyPressed() {
		component.keyPressed();
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		component.mouseWheel(event);
	}

	
}
