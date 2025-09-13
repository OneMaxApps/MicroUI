package microui;

import microui.component.Button;
import microui.constants.ContainerMode;
import microui.core.base.Container;
import microui.event.Event;
import microui.layout.ColumnLayout;
import microui.layout.ColumnLayoutParams;
import microui.service.ContainerManager;
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

	ContainerManager containerManager;
	
	public static void main(String[] args) {
		PApplet.main("microui.Launcher");
	}

	@Override
	public void settings() {
		size(640, 480);
//		fullScreen();
	}

	@Override
	public void setup() {
		MicroUI.setDebugModeEnabled(true);
		MicroUI.setContext(this);
		
		containerManager = new ContainerManager();
		containerManager.add(new Container(new ColumnLayout()).setContainerMode(ContainerMode.RESPECT_CONSTRAINTS), "container");

		containerManager.getContainerByTextId("container").addComponent(new Button(), new ColumnLayoutParams(.1f));
		containerManager.getContainerByTextId("container").addComponent(new Button().setMarginTop(50), new ColumnLayoutParams(.4f));
		containerManager.getContainerByTextId("container").addComponent(new Button(), new ColumnLayoutParams(.1f));
		
	}

	@Override
	public void draw() {
		background(200);

		containerManager.draw();
		
		if(mouseButton == RIGHT) {
			containerManager.getContainerByTextId("container").setSize(mouseX,mouseY);
		}

		GlobalTooltip.draw();
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		containerManager.mouseWheel(event);
	}

	@Override
	public void mousePressed() {

	}

	@Override
	public void keyPressed() {
		Event.keyPressed();
		containerManager.keyPressed();
	}

	@Override
	public void keyReleased() {
		Event.keyReleased();

	}

}