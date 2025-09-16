package microui;

import microui.component.Button;
import microui.core.base.Container;
import microui.event.Event;
import microui.layout.GridLayout;
import microui.layout.GridLayoutParams;
import microui.service.ContainerManager;
import microui.service.GlobalTooltip;
import microui.util.Metrics;
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

// NOTE: ///////////////////////////////////////////////////
// getX,Y,Width,Height = content area;
// getPadX,Y,Width,Height = padded area;
// getAbsoluteX,Y,Width,Height = margin area;
////////////////////////////////////////////////////////////

public final class Launcher extends PApplet {

	ContainerManager containerManager;
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

		containerManager = new ContainerManager();
		
		Container container = new Container(new GridLayout(20,20));
		
		for(int c = 0; c < 20; c++) {
			for(int r = 0; r < 20; r++) {
				container.addComponent(new Button(), new GridLayoutParams(c,r));
			}	
		}
		
		containerManager.add(container);
		
	}

	@Override
	public void draw() {
		background(200);

		containerManager.draw();
		if(mouseButton == RIGHT) {
			container.setSize(mouseX,mouseY);
		}
		
		System.out.println(frameRate);
		
		Metrics.print("PGraphicsJava2D");
		
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