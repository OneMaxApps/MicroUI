package microuitest;

import microui.MicroUI;
import microui.component.MenuButton;
import microui.core.style.Color;
import microui.event.Event;
import microui.service.GlobalTooltip;
import processing.core.PApplet;
import processing.event.MouseEvent;

public final class LaunchTest extends PApplet {
	
	Color color = new Color();
	MenuButton menu;
	
	public static void main(String[] args) {
		PApplet.main("microuitest.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);

		menu = new MenuButton("File",0,0,140,24);
		menu.add("Open","Save")
			.addSubMenu(new MenuButton("Save As...")
			    .add("PNG")
			    .addSubMenu(new MenuButton("JPEG"),"Low Quality","Middle Quality","High Quality")
			    .add("PDF")
			    .add("XML"))
			.add("Rename");
		
		menu.getItem("PDF").fill.set(255,0,0);
		menu.getItem("XML").tooltip.text.set("XML (eXtensible Markup Language) – это язык разметки,\nпредназначенный для хранения\nи передачи структурированных данных.");
		menu.fill.set(32);
		menu.setItemsColor(menu.fill);
	}
	
	@Override
	public void draw() {
		background(color.get());
		
		menu.draw();
		
		GlobalTooltip.draw();
		
		if(mouseButton == RIGHT) { menu.setPosition(mouseX,mouseY); }
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
		menu.mouseWheel(event);
	}
	
	
}