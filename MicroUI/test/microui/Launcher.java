package microui;

import microui.component.Button;
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
	Button component;
	
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
		component = new Button("c",200,200,400,200);
		component.setMinSize(10,10);
		component.setMaxSize(100,100);
	}

	@Override
	public void draw() {
		background(200);
		component.draw();
		if(mouseButton == RIGHT) {
			component.setSize(mouseX,mouseY);
		}
		GlobalTooltip.draw();
	}

}