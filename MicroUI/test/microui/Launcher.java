package microui;

import microui.component.Button;
import microui.core.base.Container;
import microui.event.Event;
import microui.layout.ColumnLayout;
import microui.layout.ColumnLayoutParams;
import microui.layout.GridLayout;
import microui.layout.GridLayoutParams;
import microui.layout.LinearLayout;
import microui.layout.LinearLayoutParams;
import microui.layout.RowLayout;
import microui.layout.RowLayoutParams;
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
//		fullScreen();
	}

	@Override
	public void setup() {
		MicroUI.setDebugModeEnabled(true);
		MicroUI.setContext(this);

		container = new Container(new GridLayout(2,2));
		container.addComponent(new Container(new GridLayout(4,4)), new GridLayoutParams(0,0,1,1),1);
		container.addComponent(new Container(new LinearLayout()), new GridLayoutParams(1,0,1,1),2);
		container.addComponent(new Container(new ColumnLayout()), new GridLayoutParams(0,1,1,1),3);
		container.addComponent(new Container(new RowLayout()), new GridLayoutParams(1,1,1,1),4);
		
		container.getContainerById(1).addComponent(new Button(), new GridLayoutParams(0,0,1,1));
		
		for(int i = 0; i < 10; i++) {
		container.getContainerById(2).addComponent(new Button(), new LinearLayoutParams(.1f));
		container.getContainerById(3).addComponent(new Button(), new ColumnLayoutParams(.1f));
		container.getContainerById(4).addComponent(new Button(), new RowLayoutParams(.1f));
		}
		
		container.getContainerById(1).setPadding(10);
		container.getContainerById(2).setPadding(20);
		container.getContainerById(3).setPadding(30);
		container.getContainerById(4).setPadding(40);
	}

	@Override
	public void draw() {
		background(200);

		container.draw();
		if(mouseButton == RIGHT) {
			container.setSize(mouseX,mouseY);
			
		}
		
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