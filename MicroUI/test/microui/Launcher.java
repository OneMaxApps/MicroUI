package microui;

import microui.component.Button;
import microui.core.style.Color;
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
		component = new Button();
		component.setFont(createFont("C:\\Windows\\Fonts\\LetterGothicStd.otf",64));
		Color color = new Color(128);
		component.setColor(color);
		color.set(32,232);
		component.setTextColor(color);
		color.set(0,0,200);
		component.setRipplesColor(color);
		color.set(255,0,0);
		component.setStrokeColor(color);
		component.setStrokeWeight(4);
	}

	@Override
	public void draw() {
		background(200);
		component.draw();
		
		GlobalTooltip.draw();
	}
	
}