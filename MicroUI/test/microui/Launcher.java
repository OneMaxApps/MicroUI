package microui;

import microui.component.Button;
import microui.core.base.Container;
import microui.event.Event;
import microui.layout.GridLayout;
import microui.layout.GridLayoutParams;
import microui.service.GlobalTooltip;
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
		
		container = new Container(new GridLayout(3,3));
		container.setPadding(20);
		//container.setContainerMode(ContainerMode.FLEXIBLE);
		
		Button buttonSearch = new Button("Search");
		buttonSearch.setMargin(40,10);
		buttonSearch.setMinMaxSize(20,100);
		buttonSearch.setMinSize(40,20);
		buttonSearch.setMaxSize(200,50);
		
		
		container.addComponent(buttonSearch, new GridLayoutParams(1,1,2,1));
		
	}

	@Override
	public void draw() {
		background(200);
		container.draw();
		
		if(mouseButton == RIGHT) {
			container.setSize(mouseX,mouseY);
			//container.setPosition(mouseX,mouseY);

		}
		
		//System.out.println(frameRate);
		//Metrics.printAll();
		
		GlobalTooltip.draw();
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