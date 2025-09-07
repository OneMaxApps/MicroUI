package microui;

import microui.component.TextField;
import microui.event.Event;
import processing.core.PApplet;
import processing.event.MouseEvent;

// TODO check full work life cycle of components
// [1] Button;
// [1] CheckBox;
// [0] Dial; (need full refactor)
// [0] EditText; (need full refactor)
// [1] MenuButton;
// [1] Scroll;
// [1] Slider;
// [0] TextField;
// [1] TextView;


public final class Launcher extends PApplet {

	TextField component;
	
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
		
		component = new TextField();
		//component.setText("Hello");
		//component.setAutoResizeModeEnabled(true);
		//component.setFont(createFont("C:\\Windows\\Fonts\\Alibi___.ttf",32));
	}

	@Override
	public void draw() {
		background(54);
		component.draw();
		if(mouseButton == RIGHT) {
			component.setSize(mouseX,mouseY);
		}
		
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		//component.mouseWheel(event);
	}

	@Override
	public void mousePressed() {

	}

	@Override
	public void keyPressed() {
		Event.keyPressed();
		component.keyPressed();
	}

	@Override
	public void keyReleased() {
		Event.keyReleased();
		
	}

}