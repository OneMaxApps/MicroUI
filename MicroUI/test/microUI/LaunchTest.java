package microUI;

import microUI.component.Dial;
import microUI.event.Event;
import microUI.util.Clipboard;
import processing.core.PApplet;
import processing.event.MouseEvent;

public final class LaunchTest extends PApplet {

	private Dial dial;

	public static void main(String[] args) {
		PApplet.main("microUI.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		Clipboard.usingLocalBuffer();
		
		dial = new Dial();
		dial.hint.set("Bright Level");
		dial.image.set("C:\\Users\\002\\Desktop\\dial_texture.PNG");
		dial.arrow.set("C:\\Users\\002\\Desktop\\narrow.png");
	}
	
	@Override
	public void draw() {
		background(128);
		dial.draw();
		
	}

	@Override
	public void keyPressed() {
		Event.keyPressed(this);
	}
	
	@Override
	public void keyReleased() {
		Event.keyReleased();
	}
	
	@Override
	public void mouseWheel(final MouseEvent event) {
		dial.mouseWheel(event);
	}
}