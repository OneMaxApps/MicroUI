package microUI.util;

import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;

import microUI.ListView;
import processing.core.PGraphics;

public class BaseItem extends BaseForm {
	public Event event;
	public Color fill;
	public Text text;
	private float posInListWithoutViewY;
	private boolean focused;
	private ListView context;
	
	public BaseItem(ListView listView, String txt, float y, float h) {
		super(listView.getPApplet(),0,y,listView.w,h);
		init(listView,txt);
		
	}
	
	public BaseItem(ListView listView) {
		super(listView.getPApplet());
		init(listView,"");
	}
	
	private final void init(ListView listView, String txt) {
		context = listView; 
		event = new Event(app);
		fill = new Color(listView.getPApplet(),255);
		text = new Text(txt);
		text.setSize(h);
	}
	
	public void draw(PGraphics pg) {
		event.listen(this);
		if(event.pressed() && !context.scroll.event.moved()) { focused = true; }
		if(app.mousePressed && event.outside()) { focused = false; }
		
		posInListWithoutViewY = y+context.scroll.getValue();
		w = context.w;
		
		pg.pushStyle();
			fill.use(pg);
			pg.noStroke();
			pg.rect(x,posInListWithoutViewY,w,h);
			
			if(focused) {
				pg.fill(0,32);
				pg.rect(x,posInListWithoutViewY,w,h);
			}
			
			text.draw(pg);
		pg.popStyle();
		
		
	}
		
	@Override
	public float getX() {
		return context.x;
	}

	@Override
	public float getY() {
		return y+context.scroll.getValue()+context.y;
	}

	public final boolean isFocused() {
		return focused;
	}

	public final void setFocused(boolean focused) {
		this.focused = focused;
	}
	
	public final void setContext(ListView lv) {
		this.context = lv;
	}
	
	public final class Text {
		public Color fill;
		private StringBuilder sb;
		private float size,shiftX;
		private boolean inCenter;
		
		Text(String txt) {
			fill = new Color(context.getPApplet(),0);
			sb = new StringBuilder(txt);
			size = h/2;
			shiftX = x+w*.02f;
			inCenter = false;
		}
		
		Text() {
			this("");
		}
		
		private final void draw(PGraphics p) {
			p.pushStyle();
				fill.use(p);
				p.textSize(size);
				p.textAlign(inCenter ? CENTER : LEFT,CENTER);
				p.text(sb.toString(),inCenter ? x+w/2 : x+shiftX,posInListWithoutViewY+h/2);
			p.popStyle();
		}
		
		
		
		public final boolean isInCenter() {
			return inCenter;
		}

		public final void setInCenter(boolean inCenter) {
			this.inCenter = inCenter;
		}

		public final float getSize() {
			return size;
		}

		public final void setSize(float size) {
			this.size = size;
		}
		
		public final void set(String txt) {
			if(sb.length() > 0) {
				sb.delete(0,sb.length());
			}
			sb.append(txt);
		}

		public final float getShiftX() {
			return shiftX;
		}

		public final void setShiftX(float shiftX) {
			this.shiftX = shiftX;
		}
	
	}
}