package microui;

import microui.component.MenuButton;
import microui.core.base.Container;
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
	
	Container containerFirst,containerSecond;
	ContainerManager containerManager;
	
	public static void main(String[] args) {
		PApplet.main("microui.Launcher");
	}

	@Override
	public void settings() {
		//size(640, 480);
		fullScreen();
	}

	@Override
	public void setup() {
		MicroUI.setContext(this);
		MicroUI.setDebugModeEnabled(true);
		
		containerFirst = new Container(new GridLayout(80,40));
		containerFirst.setPadding(5);
		MenuButton menuFile = new MenuButton("File");
		menuFile.addSubMenu(new MenuButton("New"),"Class","Enum","Interface","Record","Other");
		menuFile.add("Save","Save As","Import","Export");
		((MenuButton) (menuFile.getItemDeep("New"))).addSubMenu(new MenuButton("category"),"1","2","3","4");
		
		containerFirst.addComponent(menuFile, new GridLayoutParams(0,0,4,1),"menu_file");
		containerFirst.addComponent(new MenuButton("Edit"), new GridLayoutParams(4,0,4,1),"menu_edit");
		containerFirst.addComponent(new MenuButton("Source"), new GridLayoutParams(8,0,4,1),"menu_source");
		containerFirst.addComponent(new MenuButton("Refactor"), new GridLayoutParams(12,0,4,1),"menu_refactor");
		containerFirst.addComponent(new MenuButton("Navigate"), new GridLayoutParams(16,0,4,1),"menu_navigate");
		containerFirst.addComponent(new MenuButton("Search"), new GridLayoutParams(20,0,4,1),"menu_search");
		containerFirst.addComponent(new MenuButton("Project"), new GridLayoutParams(24,0,4,1),"menu_project");
		containerFirst.addComponent(new MenuButton("Run"), new GridLayoutParams(28,0,4,1),"menu_run");
		containerFirst.addComponent(new MenuButton("Window"), new GridLayoutParams(32,0,4,1),"menu_window");
		containerFirst.addComponent(new MenuButton("Help"), new GridLayoutParams(36,0,4,1),"menu_help");
		
		containerFirst.getComponentEntryList().forEach(entry -> {
			entry.getComponent().setPadding(5,0);
		});
		
		
		
		containerSecond = new Container(new GridLayout(20,20));
		
		containerManager = new ContainerManager();
		containerManager.add(containerFirst,"container_first");
		containerManager.add(containerSecond,"container_second");
		
		containerManager.setFocusOn(containerFirst);
		
		containerManager.remove("container_first");
	
		
	}

	@Override
	public void draw() {
		background(200);
		containerManager.draw();
		
		if(mouseButton == RIGHT) {
			//containerManager.setSize(mouseX,mouseY);
		}
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		containerFirst.mouseWheel(event);
		
	}

	@Override
	public void mousePressed() {
		//containerManager.setFocusOn(containerSecond);
		
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