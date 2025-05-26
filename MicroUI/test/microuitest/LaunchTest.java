package microuitest;

import microui.MicroUI;
import microui.component.MenuButton;
import microui.event.Event;
import microui.service.GlobalTooltip;
import processing.core.PApplet;
import processing.event.MouseEvent;

public final class LaunchTest extends PApplet {
	
	MenuButton component;
	
	public static void main(String[] args) {
		PApplet.main("microuitest.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);

		component = new MenuButton("File",200,200,100,30);
		
		MenuButton subOpenMenu = new MenuButton("Open");
		component.add(subOpenMenu);
		
		subOpenMenu.add("From System Files","From Recents","From NetWork");
		
		
		component.add("Save");
		
		MenuButton subMenuAbout = new MenuButton("About");
		
		component.add(subMenuAbout);
		
		subMenuAbout.add("MicroUI","View","Bounds","Component");
		
		MenuButton subMenuAbstractButton = new MenuButton("AbstractButton");
				
		subMenuAbout.add(subMenuAbstractButton);
		
		subMenuAbstractButton.add("Button","MenuButton","CheckBox");
		
		
	}
	
	@Override
	public void draw() {
		background(232);
		
		component.draw();
		//Metrics.printAll();
		GlobalTooltip.draw();
		
		if(mouseButton == RIGHT) { component.closeAllSubMenu(); }
	}

	@Override
	public void keyPressed() {
		Event.keyPressed();
		
		//component.keyPressed();
	}
	
	@Override
	public void keyReleased() {
		Event.keyReleased();
		
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		//component.mouseWheel(event);
	}
	
	
}