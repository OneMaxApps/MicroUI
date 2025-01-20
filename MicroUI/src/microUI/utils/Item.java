package microUI.utils;

import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;

import microUI.ListView;
import processing.core.PGraphics;

public class Item extends BaseForm {
	public Event event;
	public Color fill;
	public Text text;
	private float positionInList;
	private boolean focused;
	private ListView view;
	
	public Item(ListView lv, String txt, float y, float h) {
		super(lv.getPApplet(),0,y,lv.w,h);
		this.view = lv; 
		event = new Event(app);
		fill = new Color(lv.getPApplet(),255);
		text = new Text(txt);
	}
	
	public Item(ListView lv) {
		super(lv.getPApplet());
		this.view = lv; 
		event = new Event(app);
		fill = new Color(lv.getPApplet(),255);
		text = new Text();
		text.setSize(h/2);
	}
	
	public void draw(PGraphics p) {
		
		event.listen(this);
		if(event.pressed()) { focused = true; }
		if(app.mousePressed && event.outside()) { focused = false; }
		
		positionInList = y+view.scroll.getValue();
		
		w = view.w;
		p.pushStyle();
		fill.use(p);
		p.rect(x,positionInList,w,h);
		
		if(focused) {
			p.fill(0,32);
			p.rect(x,positionInList,w,h);
		}
		
		text.draw(p);
		p.popStyle();
		
		
	}
		
	@Override
	public float getX() {
		return view.x;
	}

	@Override
	public float getY() {
		return y+view.scroll.getValue()+view.y;
	}

	public final boolean isFocused() {
		return focused;
	}

	public final void setFocused(boolean focused) {
		this.focused = focused;
	}
	
	public void setContext(ListView lv) {
		this.view = lv;
	}
	
	public ListView getContext() {
		return view;
	}  
	
	public final class Text {
		public Color fill;
		private StringBuilder sb;
		private float size;
		private float shiftX;
		
		Text(String txt) {
			fill = new Color(view.getPApplet(),0);
			sb = new StringBuilder(txt);
			size = h/2;
			shiftX = x+w*.02f;
		}
		
		Text() {
			this("");
		}
		
		private final void draw(PGraphics p) {
			fill.use(p);
			p.textAlign(LEFT,CENTER);
			p.text(sb.toString(),x+shiftX,positionInList+h/2);
		}

		public final float getSize() {
			return size;
		}

		public final void setSize(float size) {
			this.size = size;
		}
		
		public final void setText(String txt) {
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