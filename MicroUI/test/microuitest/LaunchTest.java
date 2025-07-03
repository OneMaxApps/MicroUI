package microuitest;

import microui.MicroUI;
import microui.container.Panel.Panel;
import microui.container.layout.GridLayout;
import microui.service.GlobalTooltip;
import processing.core.PApplet;

public final class LaunchTest extends PApplet {
	Panel panel;
	
	public static void main(String[] args) {
		PApplet.main("microuitest.LaunchTest");
	}
	
	@Override
	public void settings() { fullScreen(); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		panel = new Panel("My Panel");
		panel.setContainer(new GridLayout(3));
	}
	
	@Override
	public void draw() {
		background(128);
		panel.draw();
		
		GlobalTooltip.draw();
	}
	
}