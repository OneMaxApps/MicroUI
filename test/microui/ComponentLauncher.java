package microui;

import microui.component.Button;
import microui.core.base.Container;
import microui.core.base.ContainerManager;
import microui.feedback.TooltipTextViewContent;
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
		size(800,480);
	}

	@Override
	public void setup() {
		MicroUI.setContext(this);
		ContainerManager cm = ContainerManager.getInstance();
		cm.add(new Container(new GridLayout(5,5)).addComponent(button = new Button(), new GridLayoutParams(2,2,1,1)));
		
		button.setTooltipContent(new TooltipTextViewContent("textView = new TextView(text);\r\n"
				+ "		textView.setConstrainDimensionsEnabled(false);\r\n"
				+ "		textView.setBackgroundColor(getTheme().getTooltipBackgroundColor());\r\n"
				+ "		textView.setTextColor(getTheme().getTooltipTextColor());\r\n"
				+ "		textView.setAutoResizeModeEnabled(false);\r\n"
				+ "		textView.setTextSize(DEFAULT_TEXT_SIZE);\r\n"
				+ "		textView.setCenterModeEnabled(false);\r\n"
				+ "		textView.setPadding(4,5);\r\n"
				+ "		textView.setClipModeEnabled(false);"));
	}

	@Override
	public void draw() {
		background(164);
	}
	
}