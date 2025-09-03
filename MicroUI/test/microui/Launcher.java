package microui;

import microui.component.Slider;
import microui.service.GlobalTooltip;
import processing.core.PApplet;

// TODO Check what's methods and objects must be a part of components API
//[1] Button
//[1] CheckBox
//[0] Dial
//[0] EditText
//[0] MenuButton
//[0] Scroll
//[0] Slider
//[0] TextField
//[0] TextView

public class Launcher extends PApplet {
	private Slider component;
	
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
		component = new Slider();
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