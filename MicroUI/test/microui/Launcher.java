package microui;

import microui.component.MenuButton;
import microui.container.BorderContainer;
import microui.service.GlobalTooltip;
import processing.core.PApplet;

// TODO Check what's methods and objects must be a part of components API
//[1] Button
//[0] CheckBox
//[0] Dial
//[0] EditText
//[0] MenuButton
//[0] Scroll
//[0] Slider
//[0] TextField
//[0] TextView

public class Launcher extends PApplet {
	BorderContainer borderContainer;
	
	public static void main(String[] args) {
		PApplet.main("microui.Launcher");
	}
	
	@Override
	public void settings() {
		size(640,480);
	}

	@Override
	public void setup() {
		MicroUI.setContext(this);
		borderContainer = new BorderContainer();
		borderContainer.set(new MenuButton());
		borderContainer.setRight(true);
	}

	@Override
	public void draw() {
		background(200);
		borderContainer.draw();
		if(mouseButton == RIGHT) {
			borderContainer.setSize(mouseX,mouseY);
		}
		GlobalTooltip.draw();
	}

}