package microuitest;

import microui.MicroUI;
import microui.container.layout.LinearLayout;
import microui.event.Event;
import microui.service.GlobalTooltip;
import processing.core.PApplet;
import processing.event.MouseEvent;

public final class LaunchTest extends PApplet {
	
	LinearLayout container;
	
	public static void main(String[] args) {
		PApplet.main("microuitest.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		container = new LinearLayout();
		
	}
	
	@Override
	public void draw() {
		background(128);
		container.draw();
		System.out.println(frameRate);
		GlobalTooltip.draw();
	}
	
	@Override
	public void keyPressed() {
		Event.keyPressed();
		
	}
	
	@Override
	public void keyReleased() {
		Event.keyReleased();
		
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		
	}
	
	
}