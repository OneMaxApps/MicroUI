package microUI;

import static processing.core.PApplet.constrain;
import static processing.core.PApplet.min;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import microUI.util.BaseForm;
import microUI.util.BaseItem;
import microUI.util.Color;
import microUI.util.Event;
import microUI.util.Shadow;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.event.MouseEvent;


public class ListView extends BaseForm {
	public Event event;
	public Color fill;
	public Items items;
	public Scroll scroll;
	public Shadow shadow;
	
	public ListView(PApplet app, float x, float y, float w, float h) {
		super(app, x, y, w, h);
		
		visible = true;
		
		event = new Event(app);
		
		fill = new Color(app,255);
		
		shadow = new Shadow(app,this);

		items = new Items();
		
		final float scrollW = constrain(w,min(w/20,20),20),
					scrollX = x+w-scrollW;
		
		scroll = new Scroll(app,scrollX,y,h,scrollW) {{
			buttonPlus.fill.set(32,128);
			buttonPlus.text.set("-");
			buttonPlus.text.setTextSize(100);
			buttonMinus.fill.set(32,128);
			buttonMinus.text.set("+");

			button.fill.set(32,128);
			fill.set(234);
			setVerticalMode(true);
			setVisible(false);
			shadowDestroy();
		}};
		
	}

	public ListView(PApplet app) {
		this(app,app.width*.1f,app.height*.1f,app.width*.8f,app.height*.8f);
	}
	
	@Override
	public void update() {
		event.listen(this);
		if(event.inside()) { scroll.appendValue(scroll.scrolling.get()*items.getItemsCount()); }
		
		shadow.draw();
		
		app.pushStyle();
		app.noStroke();
		fill.get();
		app.rect(x, y, w, h);
		app.popStyle();
		
		items.draw();
		scroll.draw();
		
	}
	
	public void mouseWheel(MouseEvent e) {
		scroll.scrolling.init(e,event.inside());
	}
	
	public final void loadText(String path) {
		List<String> lines = new ArrayList<String>(Arrays.asList(app.loadStrings(path)));
		
		items.itemsList.clear();
		
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
 		public Color background;
		private PGraphics pg;
		private List<BaseItem> itemsList;
		private float totalHeight;
		
		Items() {
			background = new Color(app,255);
			pg = app.createGraphics((int) w, (int) h);
			itemsList = new ArrayList<>();
		}
		
		private final void draw() {
			
			pg.beginDraw();
			background.use(pg);
			itemsList.forEach(i -> { if(itemInside(i)) { i.draw(pg); } });
			pg.endDraw();
			
			app.image(pg,x,y,w,h);
			
			if(!app.mousePressed) {
				if((int) w != pg.width || (int) h != pg.height) {
					pg = app.createGraphics((int) w, (int) h);
					updateAbsoluteHeight();
				}
			}
		}
		
		private final boolean itemInside(BaseItem item) {
			return item.getY()+item.getH() > y && item.getY()+item.getH() < y+h+item.getH();
		}
		
		public final void updateAbsoluteHeight() {
			totalHeight = 0;
			itemsList.forEach(i -> {
				totalHeight += i.getH();
			});
			
			if(totalHeight > h) {
				scroll.setVisible(true);
				scroll.setMinMax((h-totalHeight),0);
			} else {
				scroll.setVisible(false);
			}
		}
		
		public final void setTotalHeight(float height) {
			float currentYPosInList = 0;
			
			for(int j = 0; j < itemsList.size(); j++) {
				BaseItem i = itemsList.get(j);
				if(j != 0) { currentYPosInList += i.getH(); }
				i.setH(height);
				i.setY(currentYPosInList);
				i.text.setSize(height/2);
			}
			
			updateAbsoluteHeight();
		}
		
		public final void clear() {
			itemsList.clear();
			totalHeight = 0;
		}
		
		public final void setBackground(Color c) {
			itemsList.forEach(i -> i.fill = c);
		}
		
		public final void setColorText(Color c) {
			itemsList.forEach(i -> i.text.fill = c);
		}
		
		public final int getItemsCount() {
			return itemsList.size();
		}
		
		public final void add(String txt, float itemHeight) {
			float positionInList = 0;
			
			for(BaseItem i : itemsList) {
				positionInList += i.getH();
			}
			
			itemsList.add(new BaseItem(ListView.this,txt,itemsList.isEmpty() ? 0 : positionInList,itemHeight));
			totalHeight += itemHeight;
			
			if(totalHeight > h) {
			scroll.setVisible(true);
			scroll.setMinMax((h-totalHeight),0);
			scroll.setValue(0);
			} else {
				scroll.setVisible(false);
			}
		}
		
		public final void add(String txt) {
			add(txt,h/10);
		}
		
		public final void add(BaseItem customItem) {
			float positionInList = 0;
			for(BaseItem i : itemsList) { positionInList += i.getH(); }
			itemsList.add(customItem);
			customItem.setContext(ListView.this);
			customItem.setY(positionInList);
			
			totalHeight += itemsList.get(itemsList.size()-1).getH();
			
			if(totalHeight > h) {
			scroll.setVisible(true);
			scroll.setMinMax((h-totalHeight),0);
			scroll.setValue(0);
			} else {
				scroll.setVisible(false);
			}
		}

		
	}

}