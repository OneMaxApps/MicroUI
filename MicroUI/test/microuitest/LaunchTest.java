package microuitest;

import microui.MicroUI;
import microui.component.Scroll;
import microui.service.GlobalTooltip;
import processing.core.PApplet;
import processing.event.MouseEvent;

public final class LaunchTest extends PApplet {
	Scroll rangeController;
	public static void main(String[] args) {
		PApplet.main("microuitest.LaunchTest");
	}
	
	@Override
	public void settings() { size(640,640); }
	
	@Override
	public void setup() {
		MicroUI.setContext(this);
		
		rangeController = new Scroll();
		
		rangeController.setOnStartChangeValueListener(() -> {
			System.out.println("range control start");
		});
		
		rangeController.setOnChangeValueListener(() -> {
			System.out.println("range control in change");
		});
		
		rangeController.setOnEndChangeValueListener(() -> {
			System.out.println("range control end");
		});
	}
	
	@Override
	public void draw() {
		background(128);
		rangeController.draw();
		GlobalTooltip.draw();
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		rangeController.mouseWheel(event);
	}
	
	
	
}