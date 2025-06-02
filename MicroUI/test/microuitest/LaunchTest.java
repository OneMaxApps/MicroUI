package microuitest;

import microui.MicroUI;
import microui.component.MenuButton;
import microui.service.GlobalTooltip;
import processing.core.PApplet;

public final class LaunchTest extends PApplet {
	MenuButton menu;
	
	public static void main(String[] args) {
		PApplet.main("microuitest.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		menu = new MenuButton("File",0,0,100,20);
		
		menu.add(new MenuButton("New"),
				 new MenuButton("Open File"),
				 new MenuButton("Open Projects from File System"),
				 new MenuButton("Recent Files"),
				 new MenuButton("Close Editor"),
				 new MenuButton("Close All Editor"),
				 new MenuButton("Save"),
				 new MenuButton("Save As..."));
		
		menu.eventCallBack.addOnDoubleClickListener(() -> background(random(255)));
	}
	
	@Override
	public void draw() {
		background(128);
		
		menu.draw();
		
		GlobalTooltip.draw();
	}
	
}