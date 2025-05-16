package microUI;

import microUI.component.Button;
import microUI.component.CheckBox;
import microUI.component.Scroll;
import microUI.component.Slider;
import microUI.container.layout.GridLayout;
import processing.core.PApplet;

public final class LaunchTest extends PApplet {
	
	GridLayout gridLayout;
	
	public static void main(String[] args) {
		PApplet.main("microUI.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		gridLayout = new GridLayout(3,4);
		
		Button button = new Button();
		button.tooltip.text.set("I'm Button, but you can call me btn");
		
		CheckBox checkBox = new CheckBox();
		checkBox.tooltip.text.set("I'm CheckBox, but YOU can call me like cb:)");
		
		Slider slider = new Slider();
		slider.tooltip.text.set("I'm Slider!\nTELL ME, ARE YOU LIKE ME MORE THAN SCROLL?:)");
		
		Scroll scroll = new Scroll();
		scroll.tooltip.text.set("I'm Scroll!\n AND I LOVE YOU\nA LOT OF\nTHAN SLIDER\nHIHI");
		
		gridLayout.add(button);
		gridLayout.add(checkBox);
		gridLayout.add(slider);
		gridLayout.add(scroll);
		
	}
	
	@Override
	public void draw() {
		background(200);
		gridLayout.draw();
	}

}