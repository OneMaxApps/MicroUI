package microui;

import microui.component.Button;
import microui.constants.ContainerMode;
import microui.core.base.Container;
import microui.event.Event;
import microui.layout.GridLayout;
import microui.layout.LinearLayout;
import microui.layout.params.GridLayoutParams;
import microui.layout.params.LinearLayoutParams;
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
	LinearLayout linearLayout;
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
		containerManager.add(new Container(new GridLayout(2, 1)), "container");
		containerManager.getContainerByTextId("container").setPadding(20);
		
		containerManager.getContainerByTextId("container").addComponent(new Container(linearLayout = new LinearLayout()), new GridLayoutParams(0,0,1,1), "container_inner_left");
		//containerManager.getContainerByTextId("container").addComponent(new Container(new RowLayout()), new GridLayoutParams(1,0,1,1), "container_inner_right");
		
		
		
		Container innerLeft = (Container) containerManager.getContainerByTextId("container").getComponentByTextId("container_inner_left");
		innerLeft.setContainerMode(ContainerMode.IGNORE_CONSTRAINTS);
		//Container innerRight = (Container) containerManager.getContainerByTextId("container").getComponentByTextId("container_inner_right");

		for(int i = 0; i < 10; i++) {
			innerLeft.addComponent(new Button("column").setPadding(5), new LinearLayoutParams(.1f,-1,1));
			//innerRight.addComponent(new Button("row").setPadding(5), new RowLayoutParams(.1f));
		}
		
	}

	@Override
	public void draw() {
		background(200);

		containerManager.draw();

		if (mouseButton == RIGHT) {
			//containerManager.getContainerByTextId("container").setSize(mouseX, mouseY);
		}
		
		GlobalTooltip.draw();
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		containerManager.mouseWheel(event);
	}

	@Override
	public void mousePressed() {
		if(mouseButton == RIGHT) {
			linearLayout.setVerticalMode(!linearLayout.isVerticalMode());
		}
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