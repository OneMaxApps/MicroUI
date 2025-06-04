package microuitest;

import microui.MicroUI;
import microui.component.Button;
import microui.service.GlobalTooltip;
import processing.core.PApplet;

public final class LaunchTest extends PApplet {
	Button button;
	
	public static void main(String[] args) {
		PApplet.main("microuitest.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		button = new Button();
		button.eventCallBack.addOnShakeListener(() -> button.fill.set(random(255)));
	}
	
	@Override
	public void draw() {
		background(128);
		
		button.draw();
		
		GlobalTooltip.draw();
	}
	
}