package microUI;

import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;
import static processing.core.PApplet.max;
import static processing.core.PApplet.min;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;

import microUI.utils.BaseForm;
import microUI.utils.Color;
import microUI.utils.Event;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;

public final class EditText extends BaseForm {
	public final static int DIGITS = 0, LETTERS = 1, ANY_SYMBOLS = 2;
	public PFont font;
	public Color fill;
	public Text text;
	public Cursor cursor;
	public Select select;
	public Event event;
	private String hint;
	private boolean focused;
	private int enterType = 2;
	
	public EditText(PApplet app, float x, float y, float w, float h) {
		super(app, x, y, w, h);
		fill = new Color(app,255);
		text = new Text();
		cursor = new Cursor();
		select = new Select();
		event = new Event(app);
		hint = "";
		font = app.createFont("Consolas",h);
	}
	
	public EditText(PApplet app, String hint) {
		this(app,app.width*.1f,app.height*.45f,app.width*.8f,app.height*.1f);
		this.hint = hint;
	}
	
	public EditText(PApplet app) {
		this(app,app.width*.1f,app.height*.45f,app.width*.8f,app.height*.1f);
	}
	
	

	@Override
	public void update() {
		event.listen(this);
		if(event.clicked()) { focused = true; }
		if(event.outside() && app.mousePressed) { focused = false; }
		
		app.fill(fill.get());
		app.rect(x, y, w, h);
		if(focused) {
			app.fill(0,32);
			app.rect(x, y, w, h);
		}
		text.draw();
		cursor.draw();
		select.draw();
		
		if(event.pressed()) {
			cursor.setMousePosition();
			
			if(app.mouseX > x+w*.9f) {
				if(cursor.get() < text.sb.length()) {
					if(app.frameCount%10  == 0) {
						text.shift += text.getCharWidth();
					}
				}
			}
			
			if(app.mouseX < x+w*.1f) {
				if(cursor.get() > 0 && text.getWidth() >= w) {
					if(app.frameCount%10 == 0) {
					text.shift -= text.getCharWidth();
					}
				}
			}
		}
		
		if(!app.mousePressed ) {
			if((int) w != text.pg.width || (int) h != text.pg.height) {
				text.pg = app.createGraphics((int) w, (int) h);
				select.pg = app.createGraphics((int) w, (int) h);
			}
			
			
			
		}
	}
	
	public final String getHint() {
		return hint;
	}

	public final EditText setHint(String hint) {
		if(hint == null) { return this; }
		this.hint = hint;
		return this;
	}
	
	public final PFont getFont() {
		return font;
	}

	public final void setFont(PFont font) {
		this.font = font;
	}

	public final boolean isFocused() {
		return focused;
	}

	public final void setFocused(boolean focused) {
		this.focused = focused;
	}

	public final void setText(String text) {
		this.text.sb.append(text);
		cursor.setOnEnd();
	}
	
	public final String getText() {
		return text.sb.toString();
	}
	
	public final void setEnterType(int type) {
		enterType = type;
	} 

	public final void deleteSelectedText() {
		text.sb.delete(select.getFirstChar(),select.getLastChar());
		if(text.sb.toString().isEmpty()) {
			cursor.setOnStart();
		} else {
			if(cursor.get() > text.sb.length()) {
				cursor.setOnEnd();
			}
		}
		select.setEmpty();
	}
	
	public final void checkPressedBackspace() {
		if(app.key == java.awt.event.KeyEvent.VK_BACK_SPACE) {
			if(!text.sb.toString().isEmpty()) {
				if(!cursor.isOnStart()) {
					text.sb.delete(cursor.get()-1,cursor.get());
					cursor.left();
					if(cursor.cursorX <= x+text.getCharWidth()) {
						text.shift -= text.getCharWidth();
					}
				}
			}
		}
	}
	
	public final void checkPressedCTRLC() {
		if(app.key == 0x03) {
			select.setBuffer(text.getSubText(select.getFirstChar(), select.getLastChar()));
			select.isEmpty();
		}
	}
	
	public final void checkPressedCTRLV() {
		if(app.key == 0x16) {
			if(select.getBuffer() == null) { return; }
			for(int i = 0; i <select.getBuffer().length(); i++) {
					text.sb.insert(min(cursor.get(),text.sb.length()),select.getBuffer().charAt(i));
					cursor.right();
					// THINK ABOUT IT...
					if(cursor.isOnEnd() && text.getWidth() > w) {
						text.shift += text.getCharWidth();
					}
			}
			select.setEmpty();
		}
		
	}
	
	public final void checkPressedCTRLX() {
		if(app.key == 0x18) {
			select.setBuffer(text.getSubText(select.getFirstChar(), select.getLastChar()));
			text.sb.delete(select.getFirstChar(), min(text.sb.length(),select.getLastChar()));
			if(cursor.get() > text.sb.length()) { cursor.setOnEnd(); }
			return;
		}
	}
	
	public final void checkPressedCTRLA() {
		if(app.key == 0x01) {
			select.setFull();
		}
	}
	
	public final void keyPressed() {
		boolean correctSymbol;
		
		switch(enterType) {
			case DIGITS : 
				correctSymbol = Character.isDigit(app.key);
				break;
				
			case LETTERS : 
				correctSymbol = Character.isLetter(app.key);
				break;
				
			case ANY_SYMBOLS : 
				correctSymbol = Character.isDigit(app.key) || Character.isLetter(app.key) || Character.isWhitespace(app.key);
				break;
				
			default: correctSymbol = Character.isDigit(app.key) || Character.isLetter(app.key);
			
		}
		
		if(focused) {
			cursor.timerReset();
			checkPressedCTRLV();
			checkPressedCTRLA();
			if(select.isSelected()) {
				checkPressedCTRLX();
				checkPressedCTRLC();
				if(correctSymbol || app.key == java.awt.event.KeyEvent.VK_BACK_SPACE) {
					deleteSelectedText();
				}
			}else {
				checkPressedBackspace();
			}
			
			switch(app.keyCode) {
			case LEFT :
				cursor.left();
				if(cursor.cursorX <= x+text.getCharWidth()) {
					text.shift -= text.getCharWidth();
				}
				
				break;
			case RIGHT:
				cursor.right();
				if(cursor.cursorX >= x+w-text.getCharWidth()) {
					text.shift += text.getCharWidth();
				}
				break;
			}
				if(correctSymbol) {
					
					if(text.getWidth() > w-text.getCharWidth()) {
						text.shift += text.getCharWidth();
					}
					
					if(cursor.isOnEnd()) {
						text.sb.append(app.key);
						cursor.setOnEnd();
					} else {
						if(text.sb.length() == 0) {
							text.sb.append(app.key);
							cursor.right();
						} else {
							text.sb.insert(min(text.sb.length(),cursor.get()), app.key);
							cursor.right();
						}
					  }
				}
			
			
		}
	}


	public final class Text {
		
		public StringBuilder sb;
		public Color fill;
		private float size,targetSize,shift;
		private PGraphics pg;
		
		public Text() {
			super();
			sb = new StringBuilder();
			fill = new Color(app,0);
			size = targetSize = h/2;
			pg = app.createGraphics((int) w, (int) h);
		}
		
		public void draw() {
			size = constrain(size,1,targetSize);
			if(size < targetSize) { size++; }
			if(size > targetSize) { size--; }
			
			pg.beginDraw();
			pg.clear();
			pg.pushStyle();
			pg.fill(fill.get());
			pg.textSize(size);
			pg.textAlign(LEFT,CENTER);
			pg.textFont(font,size);
			pg.text(sb.toString().isEmpty() ? hint : sb.toString(), -shift,h/2);
			pg.popStyle();
			pg.endDraw();
			
			if(sb.toString().isEmpty() || cursor.isOnStart()) {
				shift = 0;
			}
			
			app.image(pg,x,y,w,h);
		}

		public final float getCharWidth() {
			pg.beginDraw();
			pg.textFont(font,size);
			float width = pg.textWidth(' ');
			pg.endDraw();
			return width;
		}
		
		public final float getSize() {
			return size;
		}

		public final void setSize(float size) {
			if(size < 1 || size > h) { return; }
			this.size = size;
		}
		
		public final float getSubTextWidth(int index) {
			index = constrain(index,0,sb.length());
			float width;
			pg.beginDraw();
			pg.pushStyle();
			pg.textSize(size);
			pg.textFont(font,size);
			width = pg.textWidth(sb.toString().substring(0,index));
			pg.popStyle();
			pg.endDraw();
			return width;
		}
		
		public final float getSubTextWidth(int indexStart, int indexEnd) {
			indexStart = constrain(indexStart,0,sb.length());
			indexEnd = constrain(indexEnd,0,sb.length());
			float width;
			pg.beginDraw();
			pg.pushStyle();
			pg.textSize(size);
			pg.textFont(font,size);
			width = pg.textWidth(sb.toString().substring(indexStart,indexEnd));
			pg.popStyle();
			pg.endDraw();
			return width;
		}
		
		public final String getSubText(int indexStart, int indexEnd) {
			return sb.toString().substring(indexStart,indexEnd);
		}
		
		public final float getWidth() {
			return getSubTextWidth(sb.length());
		}
		
		public final boolean isFull() {
			return getSubTextWidth(sb.length()) > w*.94f;
		}
		
		public final char cutLastChar() {
			char ch = sb.charAt(sb.length()-1);
			sb.delete(sb.length()-1,sb.length());
			return ch;
		}
	}
	
	public final class Cursor {
		public Color fill;
		private int position,timer;
		private float cursorX;
		private final int TIMER_MAX;
		
		public Cursor() {
			super();
			fill = new Color(app,0);
			TIMER_MAX = 60;
		}

		public final void draw() {
			
			if(focused) {
				
				cursorX = constrain(x+text.getSubTextWidth(position)-text.shift,x,x+w);
				
				if(timer < TIMER_MAX/2) {
					app.pushStyle();
					app.stroke(fill.get());
					app.strokeWeight((h*.02f)+1);
					app.line(cursorX, y+h*.05f, cursorX, y+h-h*.05f);
					app.popStyle();
				}
				
				
				if(timer < TIMER_MAX) {
					timer++;
				} else {
					timer = 0;
				}
			}
		}
		
		public final void timerReset() {
			timer = 0;
		}
		
		public final void setMousePosition() {
			if(text.sb.length() == 0) { return; }
			position = (int) constrain(map(app.mouseX+text.shift,x,x+text.getSubTextWidth(text.sb.length()),0,text.sb.length()),0,text.sb.length());
			timerReset();
		}
		
		public final void left() {
			if(position > 0) { position--; }
		}
		
		public final void right() {
			if(position < text.sb.length()) { position++; }
		}
		
		public final void setOnStart() {
			position = 0;
		}
		
		public final void setOnEnd() {
			if(text.sb.toString().isEmpty()) { return; }
			position = text.sb.length();
		}
		
		public final boolean isOnStart() {
			return position == 0;
		}
		
		public final boolean isOnEnd() {
			return position == text.sb.toString().length();
		}
		
		public final int get() {
			return position;
		}
	}

	public final class Select {
		private PGraphics pg;
		private boolean selecting;
		private int firstChar,lastChar;
		private String buffer;
		private Event event;
		public Color fill;
		
		public Select() {
			fill = new Color(app,app.color(0,0,255,32));
			event = new Event(app);
			pg = app.createGraphics((int) w, (int) h);
		}
		
		public final void draw() {
			event.listen(x,y,w,h);
			
			if(!isEmpty()) {
				pg.beginDraw();
				pg.clear();
				pg.pushStyle();
				pg.fill(fill.get());
				
				float startX = text.getSubTextWidth(firstChar)-text.shift,
					  startY = h*.1f,
					  width = getFirstChar() < cursor.get() ? text.getSubTextWidth(min(firstChar,lastChar),max(firstChar,lastChar)) : -text.getSubTextWidth(min(firstChar,lastChar),max(firstChar,lastChar)),
					  height = h*.8f;
				
				pg.rect(startX,startY,width,height);
					
				pg.popStyle();
				pg.endDraw();
				
				app.image(pg,x,y,w,h);
			}
			
			
			
			logic();
		}
		
		private final void logic() {
			if(event.pressed() && !event.moved()) { setEmpty(); }
			
			if(event.pressed()) {
				if(!selecting) {
				firstChar = cursor.get();
				selecting = true;
				}
			} else {
				selecting = false;
			}
			
			if(event.moved()) {
				lastChar = cursor.get();
			}
			
			if(event.clicked(2)) {
				if(isFullSelected()) {
					setEmpty();
				} else {
					setFull();
				}
			}
		}
		
		public final boolean isFullSelected() {
			return firstChar == 0 && lastChar == text.sb.length();
		}
		
		public final boolean isSelected() {
			return firstChar != lastChar;
		}
		
		public final boolean isEmpty() {
			return firstChar == lastChar;
		}
		
		public final void setEmpty() {
			firstChar = lastChar = 0;
		}
		
		public final int getFirstChar() {
			return min(firstChar,lastChar);
		}
		
		public final int getLastChar() {
			return max(firstChar,lastChar);
		}

		public final String getBuffer() {
			return buffer;
		}

		public final void setBuffer(String buffer) {
			if(buffer == null) { return; }
			this.buffer = buffer;
		}
		
		public final void setFull() {
			firstChar = 0;
			lastChar = text.sb.length();
		}
	}
}