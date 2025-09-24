package microui;

import microui.component.EditText;
import microui.constants.ContainerMode;
import microui.core.base.Component;
import microui.core.base.Container;
import microui.core.base.ContainerManager;
import microui.core.style.Color;
import microui.layout.GridLayout;
import microui.layout.GridLayoutParams;
import processing.core.PApplet;


// NOTE: ///////////////////////////////////////////////////
// getX,Y,Width,Height = content area;
// getPadX,Y,Width,Height = padded area;
// getAbsoluteX,Y,Width,Height = margin area;
////////////////////////////////////////////////////////////

public final class Launcher extends PApplet {
	ContainerManager cm;

	public static void main(String[] args) {
		PApplet.main("microui.Launcher");
	}

	@Override
	public void settings() {
//		fullScreen();
		size(720,480);
	}
 
	@Override
	public void setup() {
		MicroUI.setContext(this);
		//MicroUI.setDebugModeEnabled(true);

		cm = ContainerManager.getInstance();
		cm.add(getContainerWith(new EditText()), "container_main");
		
	}

	@Override
	public void draw() {
		background(32);
		// cm.getContainerByTextId("container_main").getComponentByTextId("edit_text").setSize(mouseX,mouseY);
	}

	private Container getContainerWith(Component component) {
		Container container = new Container(new GridLayout(12,12));
		container.setContainerMode(ContainerMode.IGNORE_CONSTRAINTS);
		
		container.addComponent(component, new GridLayoutParams(1, 1, 10, 10),"edit_text");

		return container;
	}
}