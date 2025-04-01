package microUI;

import static processing.core.PApplet.constrain;
import static processing.core.PApplet.max;
import static processing.core.PConstants.BACKSPACE;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.CORNER;
import static processing.core.PConstants.DOWN;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;
import static processing.core.PConstants.UP;
import static processing.core.PConstants.TAB;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import microUI.util.Color;
import microUI.util.Component;
import microUI.util.Event;
import microUI.util.FX;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.event.MouseEvent;

public final class EditText extends Component {
	public final Items items;
	public final Cursor cursor;
	public Scroll scrollV,scrollH;
	
	private final float SCROLL_WEIGHT,SHIFT_0F_LEFT_SIDE;
	private boolean isFocused;
	private PGraphics pg;
	private PFont font;
	private final FX fx;
	private final Event event;

 	public EditText(PApplet app, float x, float y, float w, float h) {
		super(app, x, y, w, h);
		visible();
		fill.set(234);
		
		SCROLL_WEIGHT = max(10,w*.025f);
		SHIFT_0F_LEFT_SIDE = SCROLL_WEIGHT/2;
		initScrolls();
		items = new Items();
		cursor = new Cursor();
		
		pg = app.createGraphics((int) w, (int) h);
		fx = new FX(app);
		initFX();
		event = new Event(app);
		
		createFont("C:\\Windows\\Fonts\\consola.ttf",32);
	}

	public EditText(PApplet app) {
		this(app,app.width*.1f,app.height*.1f,app.width*.8f,app.height*.8f);
	}

	@Override
	public void update() {
		event.listen(this);
		fx.init();
		
		pg.beginDraw();
			pg.background(fill.get());
			items.draw(pg);
			if(isFocused) {
				cursor.draw(pg);
			}
		pg.endDraw();
		
		app.image(pg, x, y, w, h);

		scrollV.draw();
		scrollH.draw();
		
		
		if(!app.mousePressed) { autoCheckResize(); }
		
		if(event.clicked()) { isFocused = true; }
		if(app.mousePressed && event.outside()) { isFocused = false; }
		
		if(scrollH.getMax() == 0) {
			scrollH.setMax(items.getMaxTextWidthFromItems());
			scrollH.setValue(scrollH.getMin());
		}
		
	}
	
	private final void initFX() {
		fx.setIncludedTransforms(false);
		
		fx.scrolls.before.fill.set(1,0);
		fx.scrolls.before.stroke.fill.set(1,0);
		fx.scrolls.before.button.fill.set(32,0);
		fx.scrolls.before.button.stroke.fill.set(1,0);
		fx.scrolls.before.buttonMinus.fill.set(32,0);
		fx.scrolls.before.buttonMinus.stroke.fill.set(1,0);
		fx.scrolls.before.buttonPlus.fill.set(32,0);
		fx.scrolls.before.buttonPlus.stroke.fill.set(1,0);
		
		fx.scrolls.after.fill.set(1,0);
		fx.scrolls.after.stroke.fill.set(1,0);
		fx.scrolls.after.button.fill.set(32,94);
		fx.scrolls.after.button.stroke.fill.set(1,0);
		fx.scrolls.after.buttonMinus.fill.set(32,132);
		fx.scrolls.after.buttonMinus.stroke.fill.set(1,0);
		fx.scrolls.after.buttonPlus.fill.set(32,132);
		fx.scrolls.after.buttonPlus.stroke.fill.set(1,0);
		
		fx.add(scrollV,scrollH);
		fx.setBasicFX(false);
		fx.setEventType(Event.INSIDE);
	}
	
	private final void initScrolls() {
		scrollV = new Scroll(app);
		scrollV.setMinMax(-1,0);
		scrollV.setVerticalMode(true);
		scrollV.shadowDestroy();
		scrollV.setPosition(x+w-SCROLL_WEIGHT, y);
		scrollV.setSize(SCROLL_WEIGHT,h);
		scrollV.setValue(scrollV.getMax());
		scrollV.scrolling.setVelocity(.1f);
		
		scrollH = new Scroll(app);
		scrollH.setMinMax(-SHIFT_0F_LEFT_SIDE, 100);
		scrollH.shadowDestroy();
		scrollH.setPosition(x,y+h-SCROLL_WEIGHT);
		scrollH.setSize(w-SCROLL_WEIGHT,SCROLL_WEIGHT);
	}
		
	@Override
	public void setX(float x) {
		super.setX(x);
		if(scrollV != null && scrollH != null) {
			scrollV.setPosition(x+w-SCROLL_WEIGHT, y);
			scrollH.setPosition(x,y+h-SCROLL_WEIGHT);
		}
	}

	@Override
	public void setY(float y) {
		super.setY(y);
		if(scrollV != null && scrollH != null) {
			scrollV.setPosition(x+w-SCROLL_WEIGHT, y);
			scrollH.setPosition(x,y+h-SCROLL_WEIGHT);
		}
	}

	@Override
	public void setW(float w) {
		super.setW(w);
		if(scrollV != null && scrollH != null) {
			scrollV.setPosition(x+w-SCROLL_WEIGHT, y);
			scrollV.setSize(SCROLL_WEIGHT,h);
			scrollH.setPosition(x,y+h-SCROLL_WEIGHT);
			scrollH.setSize(w-SCROLL_WEIGHT,SCROLL_WEIGHT);
			scrollH.setMax(items.getMaxTextWidthFromItems());
			scrollH.setValue(scrollH.getMin());
		}
	}

	@Override
	public void setH(float h) {
		super.setH(h);
		if(scrollV != null && scrollH != null) {
			scrollV.setPosition(x+w-SCROLL_WEIGHT, y);
			scrollV.setSize(SCROLL_WEIGHT,h);
			scrollH.setPosition(x,y+h-SCROLL_WEIGHT);
			scrollH.setSize(w-SCROLL_WEIGHT,SCROLL_WEIGHT);
			scrollV.setMinMax(h-items.getTotalHeight(), 0);
			scrollV.setValue(0);
		}
	}

	public final void mouseWheel(MouseEvent e) {
		scrollV.scrolling.init(e,event.inside());
		scrollV.appendValue(scrollV.scrolling.get());
	}
	
	public final void keyPressed() {
		cursor.resetTimer();
		
		float tempValueOfScrollH = scrollH.getValue();
		scrollH.setMax(items.getMaxTextWidthFromItems());
		scrollH.setValue(tempValueOfScrollH);
		
		if(isFocused) {
			switch(app.keyCode) {
			
			case UP:
				items.goUpToEditing();
				break;
			
			case DOWN:
				items.goDownToEditing();
				break;
				
			case LEFT:
				cursor.back();
				break;
				
			case RIGHT:
				cursor.next();
				break;
				
			case TAB:
				items.list.get(cursor.getColumn()).getStringBuilder().append("  ");
				cursor.next();
				break;
				
			case BACKSPACE:	
					if(!items.list.get(cursor.getColumn()).isEmpty() && cursor.getColumn() > 0 && cursor.getRow() == 0) {
						String textFromRightSideToCursor = cursor.getTextOfRightSide();
						
						items.goUpToEditing();
						cursor.setRow(items.list.get(cursor.getColumn()-1).getCharsCount());
						items.list.get(cursor.getColumn()-1).getStringBuilder().append(textFromRightSideToCursor);
						
						
						if(cursor.getColumn() < items.list.size()-1) {
								items.list.remove(cursor.getColumn());
							
							for(int i = cursor.getColumn(); i < items.list.size(); i++) {
								items.list.get(i).formUp();
							}
							items.totalHeight -= items.getTextSize();
							
							if(items.getTotalHeight() > h) {
								float tempValueOfScrollV = scrollV.getValue();
								scrollV.setMinMax(h-items.totalHeight, 0);
								scrollV.setValue(constrain(tempValueOfScrollV+items.getTextSize(),scrollV.getMin(),scrollV.getMax()));
							}
						}
						
						
						return;
					}
				
					if(items.list.get(cursor.getColumn()).isEmpty() || cursor.getRow() == 0) {
						
						if(cursor.getColumn() > 0) {
							items.goUpToEditing();
							if(!items.list.get(cursor.getColumn()-1).isEmpty()) {
								cursor.setRow(items.list.get(cursor.getColumn()-1).getCharsCount());
							}
							if(cursor.getColumn() < items.list.size()-1) {
								items.list.remove(cursor.getColumn());
							
							for(int i = cursor.getColumn(); i < items.list.size(); i++) {
								items.list.get(i).formUp();
							}
							items.totalHeight -= items.getTextSize();
							
							if(items.getTotalHeight() > h) {
								float tempValueOfScrollV = scrollV.getValue();
								scrollV.setMinMax(h-items.totalHeight, 0);
								scrollV.setValue(constrain(tempValueOfScrollV+items.getTextSize(),scrollV.getMin(),scrollV.getMax()));
								}
							}
						}
						
						return;
					}
					
					
					
					items.list.get(cursor.getColumn()).deleteChar();
					cursor.back();
				break;
			
				
				default:
					if(app.key != '\n') {
						if(Character.isDigit(app.key) || Character.isAlphabetic(app.key) || Character.isWhitespace(app.key)) {
							if(cursor.getRow() <= items.list.get(cursor.getColumn()).sb.length()) {
								items.list.get(cursor.getColumn()).sb.insert(cursor.getRow(),app.key);
								cursor.next();
							}
							
						}
					}
				break;
			}
			
			switch(app.keyCode) {
				case KeyEvent.VK_HOME : 
					cursor.setRow(0);
					scrollH.setValue(scrollH.getMin());
					break;
					
				case KeyEvent.VK_END:
					cursor.setRow(cursor.getMaxCharsInRow());
					scrollH.setValue(scrollH.getMax()/4);
					
					break;
					
				case KeyEvent.VK_PAGE_UP:
					items.list.get(cursor.getColumn()).setEditing(false);
					items.list.get(0).setEditing(true);
					
					cursor.setColumn(0);
					cursor.setRow(0);
					scrollH.setValue(scrollH.getMin());
					scrollV.setValue(0);
					break;
					
				case KeyEvent.VK_PAGE_DOWN:
					items.list.get(cursor.getColumn()).setEditing(false);
					items.list.get(cursor.getColumnsCount()-1).setEditing(true);
					
					cursor.setColumn(cursor.getColumnsCount()-1);
					cursor.setRow(cursor.getMaxCharsInRow());
					scrollH.setValue(scrollH.getMin());
					scrollV.setValue(scrollV.getMin());
					break;
					
				case KeyEvent.VK_ENTER:
					items.insert(cursor.getTextOfRightSide());
					
					scrollH.setValue(scrollH.getMin());
					cursor.setRow(0);
					break;
			}
			
		}
	}

	public final void loadText(String path) {
		items.clear();
		String[] lines = app.loadStrings(path);
		for(int i = 0; i <  lines.length; i++) {
			items.add(lines[i]);
		}
		
		scrollV.setMinMax(h-items.getTotalHeight(), 0);
		scrollV.setValue(0);
		
		scrollH.setMax(items.getMaxTextWidthFromItems());
		scrollH.setValue(scrollH.getMin());
	}
	
	public final void setFont(PFont font) {
		this.font = font;
	}
	
	public final void createFont(String path) {
		this.font = app.createFont(path, items.textSize);
	}
	
	public final void createFont(String path, int textSize) {
		this.font = app.createFont(path, textSize);
	}
	
	public final void loadFont(String path) {
		this.font = app.loadFont(path);
	}
	
	public final void autoCheckResize() {
		if(pg.width != (int) w || pg.height != (int) h) {
			pg = app.createGraphics((int) w, (int) h);
			System.out.println("PGraphics was recreated");
		}
	}
	
	public final class Items {
		public final Color fill;
		private int textSize;
		private final List<Item> list;
		private float totalHeight;
		
		public Items() {
			fill = new Color(app,0);
			textSize = 32;
			list = new ArrayList<Item>();
			for(int i = 0; i < getH()/textSize; i++) {
				list.add(new Item(i*textSize,""));
				totalHeight += textSize;
			}
			
			scrollV.setMinMax(h-totalHeight, 0);
			scrollV.setValue(0);
		}
		
		private final void draw(PGraphics pg) {
			
			fill.use(pg);
			if(font != null) { pg.textFont(font); }
			pg.textSize(textSize);
			pg.textAlign(CORNER,CENTER);
			
			for(int i = list.size()-1; i >= 0; i--) {
				Item item = list.get(i);
				
				if(itemInside(item)) {
					item.draw(pg);
					
					if(item.isEditing()) {
						cursor.setColumn(i);
					}
				} else {
					item.setEditing(false);
				}
				
			}
			
			
		}
		
		private final boolean itemInside(Item item) {
			return item.getY() > getY()-item.getH() && item.getY() < getY()+getH();
		}
		
		public final int getItemsCount() {
			return list.size();
		}

		public final void setTextSize(float textSize) {
			if(textSize <= 2) { return; }
			this.textSize = (int) textSize;
			totalHeight = 0;
			for(int i = 0; i < list.size(); i++) {
				Item item = list.get(i);
				item.setShiftY(i*textSize);
				totalHeight += textSize;
			}
			scrollV.setMinMax(h-totalHeight, 0);
			scrollV.setValue(0);
			scrollH.setMax(items.getMaxTextWidthFromItems());
			scrollH.setValue(scrollH.getMin());
		}
		
		public final int getTextSize() {
			return textSize;
		}
		
		public final float getTotalHeight() { return totalHeight; }
		
		public final void add(String text) {
			list.add(new Item(list.size()*textSize,text));
			totalHeight += textSize;
		}
		
		public final void clear() {
			list.clear();
			totalHeight = 0;
		}
		
		private final float getMaxTextWidthFromItems() {
			float maxWidth = 0;
			for(int i = 0; i < list.size(); i++) {
				if(maxWidth < list.get(i).getTextWidth()) {
					maxWidth = list.get(i).getTextWidth();
				}
			}
			return maxWidth;
		}
		
		private final void goUpToEditing() {
			int id = 0;
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).isEditing()) {
					if(i == 0) { return; }
					id = i-1;
					list.get(i).setEditing(false);
				}
			}
			
			list.get(id).setEditing(true);
			
			if(totalHeight > h && list.get(id).getInsideY() < h*.1f) {
				scrollV.appendValue(textSize);
			}
		}
		
		private final void goDownToEditing() {
			int id = 0;
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).isEditing()) {
					if(i == list.size()-1) { return; }
					id = i+1;
					list.get(i).setEditing(false);
				}
			}
			
			list.get(id).setEditing(true);
			
			if(totalHeight > h && list.get(id).getInsideY() > h*.9f) {
				scrollV.appendValue(-textSize);
			}
		}
		
		private final void insert(String partOfText) {
			int id = 0;
			
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).isEditing()) { id = i; }
			}
			
			
			list.add(id+1, new Item(list.get(id).getShiftY(),partOfText));
			
			
			for(int i = id+1; i < list.size(); i++) {
				list.get(i).formDown();
			}
			
			list.get(id).setEditing(false);
			list.get(id+1).setEditing(true);
			
			totalHeight += textSize;
			
			if(items.getTotalHeight() > h) {
				float tempValueOfScrollV = scrollV.getValue();
				scrollV.setMinMax(h-totalHeight, 0);
				scrollV.setValue(tempValueOfScrollV);
			}
			
			if(cursor.getPosY() > getH()-textSize*2) {
				scrollV.appendValue(-textSize);
			}
		}
		
		
		private final class Item {
			private final StringBuilder sb;
			private final Event event;
			private float shiftY,textWidth;
			private boolean isEditing;
			
			public Item(float shiftY, String text) {
				sb = new StringBuilder(text);
				this.shiftY = shiftY;
				event = new Event(app);
				
			}
			
			public Item(float shiftY) {
				this(shiftY,"");
			}
			
			private final void draw(PGraphics pg) {
				event.listen(x,getY(),x+w,textSize);
				
				if(pg != null) {
					pg.text(sb.toString(),-scrollH.getValue(),getInsideY()+textSize/2);
					textWidth = pg.textWidth(sb.toString());
					
					if(isFocused && isEditing) {
						pg.pushStyle();
						pg.fill(0,12);
						pg.rect(0,getInsideY(),w,textSize);
						pg.popStyle();
					}
					
				}
				
				if(event.clicked()) {
					isEditing = true;

					for(int i = 0; i < sb.length(); i++) {
						if(app.mouseX-x+scrollH.getValue() > getTextWidth(cursor.getRow())+getCharWidth(cursor.getRow())/2) {
							cursor.setRow(cursor.getRow()+1);
						} else {
							cursor.setRow(cursor.getRow()-1);
						}
					}
					
					if(app.mouseX-x+scrollH.getValue() > getTextWidth(cursor.getRow())+getCharWidth(cursor.getRow())/2) {
						cursor.setRow(cursor.getRow()+1);
					}
					
					cursor.resetTimer();
				}
				
				if(app.mousePressed && event.outside()) {
					isEditing = false;
				}
			}
			
			private final String getText() {
				return sb.toString();
			}
			
			private final StringBuilder getStringBuilder() {
				return sb;
			}
			
			private final void setEditing(boolean e) {
				isEditing = e;
			}
			
			public final boolean isEditing() {
				return isEditing;
			}

			private final float getY() {
				return shiftY+scrollV.getValue()+y;
			}
			
			private final float getInsideY() {
				return shiftY+scrollV.getValue();
			}
			
			private final float getH() {
				return textSize;
			}
			
			private final void setShiftY(float shiftY) {
				this.shiftY = shiftY;
			}
			
			private final float getShiftY() {
				return shiftY;
			}
			
			private final void formDown() {
				shiftY += textSize;
			}
			
			private final void formUp() {
				shiftY -= textSize;
			}
			
			private final float getTextWidth() {
				return textWidth;
			}
			
			private final float getTextWidth(int indexEnd) {
				float width = 0;
				
				if(indexEnd > sb.length()) { return width; }
				
				width = pg.textWidth(sb.toString().substring(0,indexEnd));
				
				return width;
			}
			
			private final float getCharWidth(int indexEnd) {
				float width = 0;
				
				if(indexEnd > sb.length()) { return width; }
				
				width = pg.textWidth(sb.toString().substring(max(0,indexEnd-1),indexEnd));
				
				return width;
			}
			
			private final int getCharsCount() {
				return sb.length();
			}
			
			private final void deleteChar() {
				sb.delete(cursor.getRow()-1, cursor.getRow());
				
			}
			
			private final boolean isEmpty() {
				return sb.toString().isEmpty();
			}
			
		}
	
		
	}
	
	public final class Cursor {
		public final Color fill;
		
		private final int MAX_DURATION = 60;
		private float posX,posY;
		private int row,column;
		private int duration;
		
		
		public Cursor() {
			fill = new Color(app,0);
		}
		
		private final void draw(PGraphics pg) {
			posX = -scrollH.getValue()+pg.textWidth(items.list.get(getColumn()).sb.toString().substring(0, getRow()));
			posY = items.list.get(getColumn()).getInsideY();
			
			if(duration < MAX_DURATION) { duration++; } else { duration = 0; }
			
			if(duration < MAX_DURATION/2) {
				pg.pushStyle();
				pg.stroke(fill.get());
				pg.strokeWeight(2);
				pg.line(posX, posY, posX, posY+items.getTextSize());
				pg.popStyle();
			}
			
		}

		public final float getPosX() {
			return posX;
		}

		public final float getPosY() {
			return posY;
		}

		private final int getMaxCharsInRow() {
			if(getColumn() > items.list.size()-1) { column--; }
			
			return items.list.get(column).getCharsCount();
		}
		
		public final int getRow() {
			return constrain(row,0,getMaxCharsInRow());
		}

		public final void setRow(int row) {
			this.row = row;
		}

		public final int getColumn() {
			return column;
		}

		public final int getColumnsCount() {
			return items.list.size();
		}
	
		public final void setColumn(int column) {
			this.column = column;
		}
		
		public final void back() {
			if(getRow() > 0) { row--; }
			if(posX < getW()/2) {
				scrollH.appendValue(-items.getTextSize()/2);
			}
		}
		
		public final void next() {
			if(getRow() < getMaxCharsInRow()) { row++; }
			if(posX > getW()/2) {
				scrollH.appendValue(items.getTextSize()/2);
			}
		}
		
		private final void resetTimer() {
			duration = 0;
		}
		
		private final boolean isRightSideHasText() {
			return getMaxCharsInRow()-getRow() > 0;
		}
		
		private final String getTextOfRightSide() {
			String txt = items.list.get(getColumn()).getText().substring(getRow(), items.list.get(getColumn()).getText().length());
			items.list.get(getColumn()).getStringBuilder().delete(getRow(), items.list.get(getColumn()).getText().length());
			return txt;
		}
	}
		
}