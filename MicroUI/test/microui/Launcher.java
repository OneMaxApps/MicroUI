package microui;

import microui.component.Slider;
import microui.service.GlobalTooltip;
import microui.util.Metrics;
import processing.core.PApplet;

// TODO Check what's methods and objects must be a part of components API
//[1] Button
//[1] CheckBox
//[0] Dial
//[0] EditText
//[0] MenuButton
//[1] Scroll
//[1] Slider
//[0] TextField
//[0] TextView

public class Launcher extends PApplet {
	Slider component;
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
		background(component.getValue());
		
		component.draw();
		if(mouseButton == RIGHT) {
			component.setSize(mouseX,mouseY);
		}
		
		Metrics.printAll();
		GlobalTooltip.draw();
	}

	
}