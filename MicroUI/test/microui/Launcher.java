package microui;

import microui.component.Button;
import microui.core.base.Container;
import microui.core.style.Color;
import microui.event.Event;
import microui.layout.GridLayout;
import microui.layout.GridLayoutParams;
import microui.service.ContainerManager;
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
		MicroUI.setContext(this);
		MicroUI.setDebugModeEnabled(true);
		
		containerManager = new ContainerManager();
		
		Container menu,workspace;
		
		containerManager.add(menu = new Container(new GridLayout(12,12)),"menu");
		containerManager.add(workspace = new Container(new GridLayout(12,12)),"settings");

		menu.setColor(new Color(255,200,0));
		menu.addComponent(new Button("Start"), new GridLayoutParams(4,5,4,1), "start");
		menu.addComponent(new Button("Settings"), new GridLayoutParams(4,6,4,1), "settings");
		menu.addComponent(new Button("Quit"), new GridLayoutParams(4,7,4,1), "quit");
		
		menu.getComponentByTextID("settings").onClick(() -> {
			containerManager.switchOn("settings");
		});
		
		
		workspace.setColor(new Color(0,64,0));
		workspace.addComponent(new Button("Graphics"), new GridLayoutParams(4,5,4,1), "graphics");
		workspace.addComponent(new Button("Sounds"), new GridLayoutParams(4,6,4,1), "sounds");
		workspace.addComponent(new Button("Menu"), new GridLayoutParams(4,7,4,1), "menu");
		
		workspace.getComponentByTextID("menu").onClick(() -> {
			containerManager.switchOn("menu");
		});
		
		menu.getComponentEntryList().forEach(entry -> {
			entry.getComponent().setPadding(10,0);
		});
		containerManager.setAnimationType(ContainerManager.AnimationType.SLIDE_RANDOM);
		
		//menu.setPadding(100);
		
		menu.setBackgroundImage(loadImage("C:\\Users\\002\\Desktop\\i.jpg"));
	}

	@Override
	public void draw() {
		background(200);
		
		containerManager.draw();
//		System.out.println(frameRate);
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