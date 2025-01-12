package microUI;

import microUI.utils.BaseForm;
import microUI.utils.Color;
import microUI.utils.Event;
import microUI.utils.Text;
import processing.core.PApplet;
import processing.core.PGraphics;
import static processing.core.PConstants.CORNER;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;
import static processing.core.PApplet.constrain;
import java.awt.event.KeyEvent;



public class InputText extends BaseForm {
	private PGraphics pg;
	private String hint;
	private boolean isEditing;
	
	public Color fill;
	public Text text;
	public Event event;
	public Cursor cursor;
	
	public InputText(PApplet app, float x, float y, float w, float h) {
		super(app,x,y,w,h);
		pg = app.createGraphics((int) w,(int) h);
		
		fill = new Color(app);
		fill.set(234);
		
		text = new Text(app,0,0,w,h);
		
		hint = "Enter text:";
		text.fill.set(0);
		text.setTextSize(h/2);
		
		event = new Event(app);
		
		cursor = new Cursor();
	}
	
	public InputText(PApplet app) {
		this(app,app.width*.1f,app.height*.45f,app.width*.8f,app.height*.1f);
		
	}

	public void draw() {
		event.listen(this);
		
		if(event.clicked()) { isEditing = true; }
		if(event.outside() && app.mousePressed) { isEditing = false; }
		
		pg.beginDraw();
			pg.background(fill.get());
			text.draw(pg);
			
			cursor.draw();
			
			showHint();
		pg.endDraw();
		
		
		app.image(pg,x, y, w, h);
	}
	
	private void showHint() {
		if(text.isEmpty()) {
			pg.pushStyle();
			pg.fill(text.fill.get());
			pg.textSize(text.getTextSize());
			pg.textAlign(CORNER,CENTER);
			pg.text(hint,0,text.getH()/2);
			pg.popStyle();
		}
	}
	
	@Override
	public void setW(float w) {
		super.setW(w);
		pg = app.createGraphics((int) w,(int) h);
		if(text != null) {
		text.setW(w);
		}
	}

	@Override
	public void setH(float h) {
		super.setH(h);
		pg = app.createGraphics((int) w,(int) h);
		if(text != null) {
		text.setH(h);
		}
	}

	public final String getHint() {
		return hint;
	}

	public final void setHint(String hint) {
		if(hint == null || hint.isEmpty()) { return; }
		this.hint = hint;
	}
	
	public final boolean isEditing() {
		return isEditing;
	}

	public final void setEditing(boolean isEditing) {
		this.isEditing = isEditing;
	}

	public final void keyPressed() {
		if(isEditing) {
			
			if(app.keyCode == KeyEvent.VK_BACK_SPACE) { text.deleteLastChar(); }
			if(Character.isLetter(app.key) || Character.isDigit(app.key)) { text.append(app.key); }
			
			// if cursor in the last one char
			if(pg.textWidth(text.get()) > w) {
				float shift = pg.textWidth(text.get())-w;
				text.setX(-shift);
			} else {
				if(text.isEmpty()) {
					text.setX(0);
				}
			}
			
		
			if(cursor.pos == text.get().length()-1) { cursor.setInEnd(); }
			
			if(app.keyCode == LEFT) { cursor.back(); }
			if(app.keyCode == RIGHT) { cursor.next(); }
			
			cursor.subTextWidthUpdate(pg);
		}
	}
	
	private class Cursor {
		private int pos;
		private float subTextWidth;
		
		private void draw() {
			pg.pushStyle();
			pg.stroke(0);
			pg.strokeWeight(h*.1f);
			pg.line(subTextWidth, 0, subTextWidth, h);
			pg.popStyle();
		}
		
		public void next() {
			pos = constrain(++pos,0,text.get().length());
		}
		public void back() {
			pos = constrain(--pos,0,text.get().length());
		}
		
		public void setInEnd() { pos = text.get().length(); }
		
		public void subTextWidthUpdate(PGraphics pg) {
			subTextWidth = pg.textWidth(text.get().substring(0, pos));
		}
	}
}