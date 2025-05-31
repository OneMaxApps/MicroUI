package microuitest;

import microui.MicroUI;
import microui.component.Button;
import microui.component.Slider;
import microui.container.EdgeContainer;
import microui.container.layout.GridLayout;
import microui.service.GlobalTooltip;
import processing.core.PApplet;

public final class LaunchTest extends PApplet {
	
	EdgeContainer edgeContainer;
	
	public static void main(String[] args) {
		PApplet.main("microuitest.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		
		edgeContainer = new EdgeContainer();
		edgeContainer.resizeHandle.setEnable(true);
		edgeContainer.invisible();
		edgeContainer.setCenter(true);
		
		GridLayout grid = new GridLayout(5,9);
		grid.add(new Button());
		grid.add(new GridLayout(4,1).add(new Slider()).add(new GridLayout(4)));
		
		edgeContainer.set(grid);
		
		grid.setVisibleComponents(true);
		
	}
	
	@Override
	public void draw() {
		background(128);
		
		edgeContainer.draw();
		
		GlobalTooltip.draw();
	}
	
}