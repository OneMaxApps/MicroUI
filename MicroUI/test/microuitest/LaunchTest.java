package microuitest;

import microui.MicroUI;
import microui.service.GlobalTooltip;
import processing.core.PApplet;

public final class LaunchTest extends PApplet {
	
	public static void main(String[] args) {
		PApplet.main("microuitest.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		
		
	}
	
	@Override
	public void draw() {
		background(128);;

		GlobalTooltip.draw();
	}
	
}