package microUI;

import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;
import static processing.core.PApplet.map;
import static processing.core.PApplet.constrain;
import static processing.core.PApplet.min;
import static processing.core.PApplet.max;

import microUI.utils.BaseForm;
import microUI.utils.Color;
import microUI.utils.Event;
import processing.core.PApplet;

public final class EditText extends BaseForm {
	public Color fill;
	public Text text;
	public Cursor cursor;
	public Select select;
	public Event event;
	private String hint;
	private boolean focused;
	
	public EditText(PApplet app, float x, float y, float w, float h) {
		super(app, x, y, w, h);
		fill = new Color(app,255);
		text = new Text();
		cursor = new Cursor();
		select = new Select();
		event = new Event(app);
		hint = "";
	}

	@Override
	public void draw() {
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
	
	public final boolean isFocused() {
		return focused;
	}

	public final void setFocused(boolean focused) {
		this.focused = focused;
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
				if(!text.isFull()) {
					text.sb.insert(min(cursor.get(),text.sb.length()),select.getBuffer().charAt(i));
					cursor.right();
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
		boolean correctSymbol = Character.isLetter(app.key) || Character.isDigit(app.key) || Character.isWhitespace(app.key);
		
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
				break;
			case RIGHT:
				cursor.right();
				break;
			}
			
			if(!text.isFull()) {
				if(correctSymbol) {
					text.adaptiveSize();
					
					if(cursor.isOnEnd()) {
						text.sb.append(app.key);
						cursor.setOnEnd();
					} else {
						if(text.sb.length() == 0) {
							text.sb.append(app.key);
							cursor.right();
						} else {
							text.sb.insert(cursor.get(),app.key);
							cursor.right();
						}
					}
				}
			}
			
			
		}
	}

	public final class Text {
		public StringBuilder sb;
		public Color fill;
		private float size,targetSize;
		
		public Text() {
			super();
			sb = new StringBuilder();
			fill = new Color(app,0);
			size = targetSize = h/2;
		}
		
		public void draw() {
			size = constrain(size,1,targetSize);
			
			app.pushStyle();
			app.fill(fill.get());
			app.textSize(size);
			app.textAlign(LEFT,CENTER);
			app.text(sb.toString().isEmpty() ? hint : sb.toString(), x,y+h/2);
			app.popStyle();
			
			adaptiveSize();
			
		}
		
		private final float getHintWidth() {
			float width;
			app.pushStyle();
			app.textSize(size);
			width = app.textWidth(hint);
			app.popStyle();
			return width;
		}
		
		public final void adaptiveSize() {
			if(getWidth() > w || getHintWidth() > w) {
				size--;
			} else {
			if(size < targetSize) {
				size++;
			}
		  }
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
			app.pushStyle();
			app.textSize(size);
			width = app.textWidth(sb.toString().substring(0,index));
			app.popStyle();
			return width;
		}
		
		public final float getSubTextWidth(int indexStart, int indexEnd) {
			indexStart = constrain(indexStart,0,sb.length());
			indexEnd = constrain(indexEnd,0,sb.length());
			float width;
			app.pushStyle();
			app.textSize(size);
			width = app.textWidth(sb.toString().substring(indexStart,indexEnd));
			app.popStyle();
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
	}
	
	public final class Cursor {
		public Color fill;
		private int position,timer;
		private final int TIMER_MAX;
		
		public Cursor() {
			super();
			fill = new Color(app,0);
			TIMER_MAX = 60;
		}

		public final void draw() {
			if(focused) {
				if(timer < TIMER_MAX/2) {
					app.pushStyle();
					app.stroke(fill.get());
					app.strokeWeight((h*.02f)+1);
					app.line(x+text.getSubTextWidth(position), y+h*.05f, x+text.getSubTextWidth(position), y+h-h*.05f);
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
			position = (int) constrain(map(app.mouseX,x,x+text.getSubTextWidth(text.sb.length()),0,text.sb.length()),0,text.sb.length());
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
		private boolean selecting;
		private int firstChar,lastChar;
		private String buffer;
		private Event event;
		public Color fill;
		
		public Select() {
			fill = new Color(app,app.color(0,0,255,32));
			event = new Event(app);
		}
		
		public final void draw() {
			event.listen(x,y,w,h);
			
			if(!isEmpty()) {
				app.pushStyle();
				app.fill(fill.get());
				
				if(firstChar < cursor.get()) {
					app.rect(x+text.getSubTextWidth(firstChar),y+h*.1f,text.getSubTextWidth(min(firstChar,lastChar),max(firstChar,lastChar)),h*.8f);
				} else {
					app.rect(x+text.getSubTextWidth(firstChar),y+h*.1f,-text.getSubTextWidth(min(firstChar,lastChar),max(firstChar,lastChar)),h*.8f);
				}
				
				app.popStyle();
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
					firstChar = (int) (lastChar = 0);
				} else {
					firstChar = 0;
					lastChar = text.sb.length();
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