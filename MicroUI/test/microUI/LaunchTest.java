package microUI;

import microUI.component.Button;
import microUI.container.layout.GridLayout;
import microUI.event.Event;
import microUI.util.Metrics;
import processing.core.PApplet;
import processing.event.MouseEvent;

public final class LaunchTest extends PApplet {
	
	GridLayout gridLayout;
	
	public static void main(String[] args) {
		PApplet.main("microUI.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		
		gridLayout = new GridLayout(9,12);
		
		for(; !gridLayout.isFull(); gridLayout.add(new Button()));
	}
	
	@Override
	public void draw() {
		background(128);
		
		if(frameCount % 30 == 0) {
			Metrics.printAll();
		}
		
		gridLayout.draw();
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
	public void mouseWheel(final MouseEvent event) {
		
	}
}