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
//		size(640,480);
		fullScreen();
	}

	@Override
	public void setup() {
		MicroUI.setContext(this);
		MicroUI.setDebugModeEnabled(true);
		ContainerManager cm = ContainerManager.getInstance();
		cm.add(new Container(new GridLayout(5,5)).addComponent(button = new Button("INFO"), new GridLayoutParams(2,2,1,1)));
		
		button.setTooltipText("MicroUI tooltip system is ready\nMicroUI tooltip system is ready\nMicroUI tooltip system is ready\nMicroUI tooltip system is ready");
		button.setBackgroundColor(new GradientColor(new Color(232), new Color(200), () -> button.isHover()));
	}

	@Override
	public void draw() {
		background(164);
	}
	
}