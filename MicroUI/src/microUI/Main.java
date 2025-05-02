package microUI;

import microUI.component.Slider;
import microUI.container.layout.GridLayout;
import processing.core.PApplet;
import processing.event.MouseEvent;

public final class Main extends PApplet {
	
	private Slider component;
	private GridLayout gridLayout;
	
	public static void main(String[] args) {
		PApplet.main("microUI.Main");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		gridLayout = new GridLayout(3,9).add(new Slider(), 1, 5);
		component = new Slider();
	}
		
	@Override
	public void draw() {
		background(128);
		gridLayout.draw();
		if(mouseButton == RIGHT) {
			gridLayout.setSize(mouseX,mouseY);
		}
		
	}

	@Override
	public void keyPressed() {
		//component.keyPressed();
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		gridLayout.mouseWheel(event);
	}

	
}