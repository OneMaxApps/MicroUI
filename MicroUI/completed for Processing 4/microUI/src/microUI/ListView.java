package microUI;

import static processing.core.PApplet.constrain;
import static processing.core.PApplet.min;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import microUI.utils.BaseForm;
import microUI.utils.Event;
import microUI.utils.Item;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.event.MouseEvent;


public final class ListView extends BaseForm {
	public Scroll scroll;
	public Items items;
	public Event event;

	public ListView(PApplet app, float x, float y, float w, float h) {
		super(app, x, y, w, h);
		float scrollW = constrain(w,min(w/20,20),20);
		scroll = new Scroll(app,x+w-scrollW,y,h,scrollW);
		scroll.setVerticalMode(true);
		scroll.setVisible(false);
		scroll.shadowDestroy();
		
		items = new Items();
		
		event = new Event(app);
		
	}

	
	@Override
	public void draw() {
		event.listen(this);
		
		items.draw();
		scroll.draw();
		
		if(event.inside()) { scroll.appendValue(scroll.scrolling.get()); }
		
	}
	
	public void mouseWheel(MouseEvent e) {
		scroll.scrolling.init(e,event.inside());
	}
	
	public final void loadText(String path) {
		List<String> lines = new ArrayList<String>(Arrays.asList(app.loadStrings(path)));
		
		items.list.clear();
		
		for(String line : lines) {
			items.add(line,h/20);
		}
		
		scroll.setValue(0);
	}

 	public final PApplet getPApplet() {
		return app;
	}
	
	@Override
	public void setX(float x) {
		super.setX(x);
		if(scroll == null) { return; }
		float scrollW = constrain(w,min(w/20,20),20);
		scroll.setX(x+w-scrollW);
	}
	
	@Override
	public void setY(float y) {
		super.setY(y);
		if(scroll == null) { return; }
		scroll.setY(y);
	}
	
	@Override
	public void setW(float w) {
		super.setW(w);
		if(scroll == null) { return; }
		float scrollW = constrain(w,min(w/20,20),20);
		scroll.setX(x+w-scrollW);
		scroll.setW(scrollW);
	}
	
	@Override
	public void setH(float h) {
		super.setH(h);
		if(scroll == null) { return; }
		scroll.setH(h);
	}

 	public final class Items {
		private PGraphics p;
		private List<Item> list;
		private float absoluteHeight;
		
		Items() {
			p = app.createGraphics((int) w, (int) h);
			list = new ArrayList<>();
		}
		
		private final void draw() {
			p.beginDraw();
			p.background(255);
			list.forEach(i -> {
				if(i.getY()+i.getH() > y && i.getY()+i.getH() < y+h+i.getH()) {
					i.draw(p);
				}
			});
			p.endDraw();
			
			app.image(p,x,y,w,h);
			
			if(!app.mousePressed) {
				if((int) w != p.width || (int) h != p.height) {
					p = app.createGraphics((int) w, (int) h);
					updateAbsoluteHeight();
				}
			}
		}
		
		public final void updateAbsoluteHeight() {
			absoluteHeight = 0;
			list.forEach(i -> {
				absoluteHeight += i.getH();
			});
			
			scroll.setVisible(true);
			scroll.setMinMax((h-absoluteHeight),0);
		}
		
		public final void clear() {
			list.clear();
			absoluteHeight = 0;
		}
		
		public final int getItemsCount() {
			return list.size();
		}
		
		public final void add(String txt, float itemHeight) {
			float positionInList = 0;
			
			for(Item i : list) {
				positionInList += i.getH();
			}
			
			list.add(new Item(ListView.this,txt,list.isEmpty() ? 0 : positionInList,itemHeight));
			absoluteHeight += itemHeight;
			
			if(absoluteHeight > h) {
			scroll.setVisible(true);
			scroll.setMinMax((h-absoluteHeight),0);
			} else {
				scroll.setVisible(false);
			}
		}
		
		public final void add(Item customItem) {
			float positionInList = 0;
			for(Item i : list) { positionInList += i.getH(); }
			list.add(customItem);
			customItem.setContext(ListView.this);
			customItem.setY(positionInList);
			
			
			absoluteHeight += list.get(list.size()-1).getH();
			
			if(absoluteHeight > h) {
			scroll.setVisible(true);
			scroll.setMinMax((h-absoluteHeight),0);
			} else {
				scroll.setVisible(false);
			}
		}

		
	}

}