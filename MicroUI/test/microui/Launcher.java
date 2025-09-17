package microui;

import microui.component.Button;
import microui.component.Slider;
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

// NOTE: ///////////////////////////////////////////////////
// getX,Y,Width,Height = content area;
// getPadX,Y,Width,Height = padded area;
// getAbsoluteX,Y,Width,Height = margin area;
////////////////////////////////////////////////////////////

public final class Launcher extends PApplet {

	ContainerManager containerManager;
	
	public static void main(String[] args) {
		PApplet.main("microui.Launcher");
	}

	@Override
	public void settings() {
//		size(640, 480);
		fullScreen();
	}

	@Override
	public void setup() {
		MicroUI.setDebugModeEnabled(true);
		MicroUI.setContext(this);

		containerManager = ContainerManager.getInstance();
		
		Container containerMenu = new Container(new GridLayout(5,11));
		Container containerGameplay = new Container(new GridLayout(5,11));
		
		containerMenu.addComponent(new Slider(), new GridLayoutParams(2,5),() -> containerManager.switchOn(containerGameplay));
		containerGameplay.addComponent(new Button("menu"), new GridLayoutParams(2,5),() -> containerManager.switchOn(containerMenu));
		
		containerManager.add(containerMenu);
		containerManager.add(containerGameplay);
		
		
	}

	@Override
	public void draw() {
		background(200);

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