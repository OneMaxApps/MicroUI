package microuitest;

import microui.MicroUI;
import microui.component.MenuButton;
import microui.event.Event;
import processing.core.PApplet;
import processing.event.MouseEvent;

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
		
		/*
		menu = new MenuButton("File",0,0,140,24);
		menu.add("Open","Save");
		
		MenuButton subMenuSaveAs = new MenuButton("Save As...");
		menu.add(subMenuSaveAs);
		subMenuSaveAs.add("PNG");
		
		MenuButton subMenuJPEGQuality = new MenuButton("JPEG");
		subMenuSaveAs.add(subMenuJPEGQuality);
		subMenuJPEGQuality.add("Low Quality","Middle Quality","High Quality");
		
		MenuButton subMenuPDFQuality = new MenuButton("PDF");
		subMenuSaveAs.add(subMenuPDFQuality);
		subMenuPDFQuality.add("Low Quality","Middle Quality","High Quality");
		*/
		
		
		
		menu = new MenuButton("File",0,0,140,24);
		menu.add("Open","Save")
			.addSubMenu(new MenuButton("Save As...")
		    .add("PNG")
		    .addSubMenu(new MenuButton("JPEG"),"Low Quality","Middle Quality","High Quality")
		    .add("PDF"))
			.build();

	}
	
	@Override
	public void draw() {
		background(128);
		
		menu.draw();

		// GlobalTooltip.draw();
		
		

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