package microui;

import microui.component.Button;
import microui.core.base.Container;
import microui.event.Event;
import microui.layout.GridLayout;
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

// absoluteX,Y,Width,Height its with margin; (FOR CONTAINERS)
// contentX,Y,Width,Height its with padding; (FOR RENDER, EVENT AND CALLBACK LOGIC)
// getX,Y,Width,Height its raw data about bounds without padding and margin; (FOR REAL BOUNDS MANIPULATIONS)

public final class Launcher extends PApplet {

	Container container;
	
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
		MicroUI.setDebugModeEnabled(true);
		container = new Container();
		//container.setPadding(20);
		//container.setMargin(20);
		
		GridLayout gridLayout = new GridLayout(4,4);
		container.setLayoutManager(gridLayout);
		
		Button buttonFirst = new Button("First");
		container.addComponent(buttonFirst);
		gridLayout.setComponentOn(buttonFirst, 1, 1, 1, 1);
		//component.setText("Hello");
		//component.setAutoResizeModeEnabled(true);
		//component.setFont(createFont("C:\\Windows\\Fonts\\Alibi___.ttf",32));
	}

	@Override
	public void draw() {
		background(200);
		container.draw();
		if(mouseButton == RIGHT) {
			container.setSize(mouseX,mouseY);
		}
		System.out.println(frameRate);
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		container.mouseWheel(event);
	}

	@Override
	public void mousePressed() {

	}

	@Override
	public void keyPressed() {
		Event.keyPressed();
		container.keyPressed();
	}

	@Override
	public void keyReleased() {
		Event.keyReleased();
		
	}

}