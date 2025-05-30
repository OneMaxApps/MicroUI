package microuitest;

import microui.MicroUI;
import microui.container.EdgeContainer;
import microui.container.layout.LinearLayout;
import microui.service.GlobalTooltip;
import processing.core.PApplet;

public final class LaunchTest extends PApplet {
	
	private EdgeContainer edgeContainer;
	private LinearLayout layout;
	
	public static void main(String[] args) {
		PApplet.main("microuitest.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		edgeContainer = new EdgeContainer();
		edgeContainer.margin.set(10);
		
		
		layout = new LinearLayout();
		layout.add(edgeContainer, .5f);
		layout.resizeHandle.visible();
	}
	
	@Override
	public void draw() {
		background(128);

		layout.draw();
		
		GlobalTooltip.draw();
		
	}
	
}