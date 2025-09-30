package microui;

import microui.component.TextArea;
import processing.core.PApplet;

public class TextAreaLauncher extends PApplet {

	private TextArea textArea;
	
	public static void main(String[] args) {
		PApplet.main("microui.TextAreaLauncher");
	}

	@Override
	public void settings() {
		super.settings();
		size(1080,720);
	}

	@Override
	public void setup() {
		super.setup();
		MicroUI.setContext(this);
		MicroUI.setFlexibleRenderModeEnabled(true);
		
		textArea = new TextArea();
	}

	@Override
	public void draw() {
		super.draw();
		background(232);
		
		textArea.draw();
	}
	
}