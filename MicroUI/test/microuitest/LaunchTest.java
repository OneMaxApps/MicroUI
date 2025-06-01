package microuitest;

import microui.MicroUI;
import microui.component.Button;
import microui.core.style.Color;
import microui.service.GlobalTooltip;
import processing.core.PApplet;

public final class LaunchTest extends PApplet {
	
	Button button;
	Color color = new Color();
	
	public static void main(String[] args) {
		PApplet.main("microuitest.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		button = new Button();
		button.text.setAutoResize(false);
		
		button.eventCallBack.setOnDoubleClickListener(() -> color.set(random(255),random(255),random(255)));
		button.eventCallBack.setOnClickListener(() -> button.text.setTextSize(random(10,20)));
		button.eventCallBack.setOnLongPressedListener(() -> button.text.setTextSize(random(10,20)));
		
	}
	
	@Override
	public void draw() {
		background(color.get());
		
		button.draw();
		
		GlobalTooltip.draw();
	}
	
}