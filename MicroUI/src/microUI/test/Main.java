package microUI.test;

import microUI.ListView;
import microUI.Window;
import microUI.utils.Item;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.event.MouseEvent;

public class Main extends PApplet {
	ListView listview;
	Window window;
	
	public static void main(String[] args) {
		PApplet.main("microUI.test.Main");

	}
	
	@Override
	public void settings() { size(800,800); }

	
	@Override
	public void setup() {
		listview = new ListView(this,100,100,600,600);
		listview.items.add("THIS IS THE BIG TEXT",100);
		listview.items.add("THIS IS THE BIG TEXT but a little smaller",80);
		
		class CustomItem extends Item {
			
			CustomItem(float r, float g, float b) {
				super(listview);
				fill.set(r,g,b);
				setH(10);
			}

			@Override
			public void draw(PGraphics p) {
				super.draw(p);
				if(event.clicked()) {
					app.exit();
				}
			}
			
			
		}
		
		for(int i = 0; i < 255; i++)
		listview.items.add(new CustomItem(i,0,0));
		for(int i = 0; i < 255; i++)
			listview.items.add(new CustomItem(0,i,0));
		for(int i = 0; i < 255; i++)
			listview.items.add(new CustomItem(0,0,i));
			
		window = new Window(this,"ListView");
		window.setForm(listview);
	}
	
	@Override
	public void draw() {
		background(128);
		window.draw();
		
	}
	
	@Override
	public void mouseWheel(MouseEvent event) {
		listview.mouseWheel(event);
	}
	
}