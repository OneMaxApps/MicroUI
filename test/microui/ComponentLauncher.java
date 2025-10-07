package microui;

import microui.component.Button;
import microui.core.base.Container;
import microui.core.base.ContainerManager;
import microui.core.style.Color;
import microui.core.style.GradientColor;
import microui.layout.GridLayout;
import microui.layout.GridLayoutParams;
import processing.core.PApplet;

public class ComponentLauncher extends PApplet {
	
	private Button button;
	
	public static void main(String[] args) {
		PApplet.main("microui.ComponentLauncher");
	}

	@Override
	public void settings() {
		size(640,480);
	}

	@Override
	public void setup() {
		MicroUI.setContext(this);
		//MicroUI.setDebugModeEnabled(true);
		ContainerManager cm = ContainerManager.getInstance();
		cm.add(new Container(new GridLayout(5,5)).addComponent(button = new Button(), new GridLayoutParams(2,2,1,1)));
		
		//frameRate(2);
		
		button.setTooltipText("MicroUI.setContext(this);\r\n"
				+ "		//MicroUI.setDebugModeEnabled(true);\r\n"
				+ "		ContainerManager cm = ContainerManager.getInstance();\r\n"
				+ "		cm.add(new Container(new GridLayout(5,5)).addComponent(button = new Button(), new GridLayoutParams(2,2,1,1)));\r\n"
				+ "");
		button.setBackgroundColor(new GradientColor(new Color(232), new Color(200), () -> button.isHover()));
	}

	@Override
	public void draw() {
		background(164);
	}
	
}