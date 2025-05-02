package microUI;

import microUI.component.TextInput;
import microUI.container.layout.GridLayout;
import microUI.event.Event;
import processing.core.PApplet;
import processing.event.MouseEvent;

public final class Main extends PApplet {
	
	private GridLayout gridLayout;
	
	
	public static void main(String[] args) {
		PApplet.main("microUI.Main");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		
		gridLayout = new GridLayout(8);
		
		for(int r = 0; r < gridLayout.getRows(); r++) {
			for(int c = 0; c < gridLayout.getColumns(); c++) {
				gridLayout.add(new TextInput(), r, c);
			}
		}
		
		gridLayout.setFillTheGrid(true);
	}
		
	@Override
	public void draw() {
		background(128);
		gridLayout.draw();
		if(mouseButton == RIGHT) {
			gridLayout.setSize(mouseX,mouseY);
		}
		System.out.println((int) frameRate);
		// if(frameCount > 100) { exit(); }
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
	public void mouseWheel(MouseEvent event) {
		
	}

	
}