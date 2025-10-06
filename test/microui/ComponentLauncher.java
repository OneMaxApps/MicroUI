package microui;

import microui.component.Button;
import microui.core.base.Container;
import microui.core.base.ContainerManager;
import microui.core.style.Color;
import microui.core.style.GradientColor;
import microui.layout.GridLayout;
import microui.layout.GridLayoutParams;
import microui.util.Metrics;
import processing.core.PApplet;

public class ComponentLauncher extends PApplet {
	
	private Button button;
	
	public static void main(String[] args) {
		PApplet.main("microui.ComponentLauncher");
	}

	@Override
	public void settings() {
		size(800,240);
	}

	@Override
	public void setup() {
		MicroUI.setContext(this);
		ContainerManager cm = ContainerManager.getInstance();
		cm.add(new Container(new GridLayout(5,5)).addComponent(button = new Button(), new GridLayoutParams(2,2,1,1)));
		
		//frameRate(2);
		
		button.setTooltipText("here you can set your own custom text for hint");
		button.setBackgroundColor(new GradientColor(new Color(232), new Color(200), () -> button.isHover()));
	}

	@Override
	public void draw() {
		background(164);
		Metrics.printAll();
	}
	
}