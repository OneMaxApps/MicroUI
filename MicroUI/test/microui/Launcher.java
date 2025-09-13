package microui;

import microui.component.Button;
import microui.core.base.Container;
import microui.core.style.Color;
import microui.event.Event;
import microui.layout.GridLayout;
import microui.layout.GridLayoutParams;
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
		//MicroUI.setDebugModeEnabled(true);
		MicroUI.setContext(this);
		
		containerManager = new ContainerManager();
		containerManager.add(new Container(new GridLayout(9,5)).addComponent(new Button("go next").setPadding(10,20).onClick(() -> containerManager.switchOn(2)), new GridLayoutParams(3,2,3,1),1), 1);
		containerManager.add(new Container(new GridLayout(9,5)).addComponent(new Button("go back").setPadding(10,20).onClick(() -> containerManager.switchOn(1)), new GridLayoutParams(3,2,3,1),2), 2);
		containerManager.getContainerById(1).getComponentById(1).setColor(new Color(200));
		containerManager.getContainerById(1).setColor(new Color(255,0,0,100));
	}

	@Override
	public void draw() {
		background(200);

		containerManager.draw();

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