package microUI.component;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_END;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_HOME;
import static java.awt.event.KeyEvent.VK_PAGE_DOWN;
import static java.awt.event.KeyEvent.VK_PAGE_UP;
import static java.awt.event.KeyEvent.VK_V;
import static java.awt.event.KeyEvent.VK_X;
import static processing.core.PApplet.abs;
import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;
import static processing.core.PApplet.max;
import static processing.core.PApplet.min;
import static processing.core.PConstants.BACKSPACE;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.CONTROL;
import static processing.core.PConstants.CORNER;
import static processing.core.PConstants.DOWN;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;
import static processing.core.PConstants.TAB;
import static processing.core.PConstants.UP;

import java.util.ArrayList;
import java.util.List;

import microUI.Constants;
import microUI.core.Component;
//import microUI.effect.Animation;
import microUI.event.Event;
import microUI.event.KeyPressable;
import microUI.event.Scrollable;
import microUI.util.Clipboard;
import microUI.util.Color;
import microUI.util.Metrics;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.event.MouseEvent;

public class EditText extends Component implements Scrollable, KeyPressable {
	protected final float SCROLL_WEIGHT,SHIFT_LEFT_SIDE;
	
	public Scroll scrollV,scrollH;
	public final Items items;
	public final Cursor cursor;
	public final Selection selection;
	
	
	protected boolean focused;
	protected PGraphics pg;
	protected PFont font;
	protected final Event event;

 	public EditText(float x, float y, float w, float h) {
		super(x, y, w, h);
		Metrics.Component.registerEditText();
		
		visible();
		fill.set(255,255,232);
		
		final int DEFAULT_MIN_WEIGHT_FOR_SCROLLS = 10;
		final float MAX_WEIGHT_FOR_SCROLLS = w*.025f;
		SCROLL_WEIGHT = max(DEFAULT_MIN_WEIGHT_FOR_SCROLLS,MAX_WEIGHT_FOR_SCROLLS);
		SHIFT_LEFT_SIDE = SCROLL_WEIGHT/2;
		
		initScrolls();
		items = new Items();
		cursor = new Cursor();
		selection = new Selection();
		
		pg = app.createGraphics((int) w, (int) h, app.sketchRenderer());
		event = new Event();
		
		scrollsValuesUpdate();
		
	}

	public EditText() {
		this(app.width*.1f,app.height*.1f,app.width*.8f,app.height*.8f);
	}
	
	private final void initScrolls() {
		
		scrollV = new Scroll();
		scrollV.value.setMinMax(-1,0);
		scrollV.setOrientation(Constants.VERTICAL);
		scrollV.setPosition(x+w-SCROLL_WEIGHT, y);
		scrollV.setSize(SCROLL_WEIGHT,h-SCROLL_WEIGHT);
		scrollV.value.set(scrollV.value.getMax());
		scrollV.scrolling.setVelocity(.1f);
		
		scrollH = new Scroll();
		scrollH.value.setMinMax(-SHIFT_LEFT_SIDE, 100);
		scrollH.setPosition(x,y+h-SCROLL_WEIGHT);
		scrollH.setSize(w,SCROLL_WEIGHT);
		
		scrollH.inTransforms();
		scrollV.inTransforms();
	}
	
	@Override
	public void update() {
		event.listen(this);
	
		pg.beginDraw();
			pg.background(fill.get());
			items.draw(pg);
			if(focused) { cursor.draw(pg); }
			selection.draw();
		pg.endDraw();
		
		app.image(pg, x, y, w, h);

		
		scrollV.draw();
		scrollH.draw();
		
		
		if(!app.mousePressed) { autoCheckResize(); }
		
		if(event.clicked()) { focused = true; }
		if(app.mousePressed && event.outside() && !event.holding()) { focused = false; }
		
		
		if(scrollH.value.getMax() == 0) {
			scrollH.value.setMax(items.getMaxTextWidthFromItems());
			scrollH.value.set(scrollH.value.getMin());
		}
		
	}

	/*
	private final void debug() {
		System.out.println("\nfps:"+ (int) app.frameRate+"\nselected items:"+selection.getSelectedLines()+"\ntotal height:"+items.getTotalHeight()+"\nitems count:"+items.size());
		System.out.println("\nis Selecting:"+selection.isSelecting()+
							"\nis FullTextSelected:"+selection.isSelectedAllText()+
							"\nfirst Column:"+selection.getFirstColumn()+
							"\nlast Column:"+selection.getLastColumn()+
							"\nfirst row:"+selection.getFirstRow()+
							"\nlast row:"+selection.getLastRow());
	}*/
	
	@Override
	public void setX(float x) {
		super.setX(x);
		scrollsPositionUpdate();
	}

	@Override
	public void setY(float y) {
		super.setY(y);
		scrollsPositionUpdate();
	}

	@Override
	public void setW(float w) {
		super.setW(w);
		scrollsTransformsUpdate();
		scrollsValuesUpdate();
	}

	@Override
	public void setH(float h) {
		super.setH(h);
		scrollsTransformsUpdate();
		scrollsValuesUpdate();
	}
	
	

	@Override
	public void inTransforms() {
		super.inTransforms();
		if(scrollH == null || scrollV == null) { return; }
		
		scrollH.inTransforms();
		scrollV.inTransforms();
		
	}

	@Override
	public final void mouseWheel(MouseEvent e) {
		
		if(focused) {
			scrollV.scrolling.init(e,event.inside());
			scrollV.autoScroll();
		}
		
	}
	
	@Override
	public final void keyPressed() {
		scrollsValuesUpdate();
		
		if(!focused) { return; }
			
		cursor.resetTimer();
		
		if(Event.checkKey(CONTROL)) {
			if(Event.checkKey('+')) { items.setTextSize(items.getTextSize()+1); }
			if(Event.checkKey('-')) { items.setTextSize(items.getTextSize()-1); }
			if(Event.checkKey(VK_C)) { Clipboard.set(selection.getText()); }
			if(Event.checkKey(VK_V)) { CTRLV(); }
			if(Event.checkKey(VK_X)) { CTRLX(); }
			if(Event.checkKey(VK_A)) {
				items.selectAllText();
				
				selection.setSelectedAllText(true);
				
			}
			return;
		}
			
		switch(app.keyCode) {
			case UP:    items.goUpToEditing(); 		break;
			case DOWN:  items.goDownToEditing(); 	break;	
			case LEFT:  cursor.back();				break;	
			case RIGHT: cursor.next();				break;
			case TAB:   keyTab();					break;
			case BACKSPACE:	 keyBackspace(); 		break;
			case VK_HOME : keyHome();				break;	
			case VK_END: keyEnd();					break;	
			case VK_PAGE_UP: keyPageUp(); 			break;	
			case VK_PAGE_DOWN: keyPageDown(); 		break;
			case VK_ENTER: keyEnter();				break;
			default:
				
				items.deleteAllSelectedText();
				selection.unselect();
				
				if(isAllowedChar(app.key)) {
					items.getCurrent().insert(String.valueOf(app.key));
					cursor.next();
				}
				break;
		}

	}
	
	public final boolean isFocused() { return focused; }
	
	public final void setFocused(boolean isFocused) { this.focused = isFocused; }

	private final void removeEmptyItem(final int index) {
		if(items.size() <= 1) { return; }
		if(index < 0 || index > items.size()-1) { return; }
		if(!items.get(index).isEmpty()) { return; }
		
		items.list.remove(index);
		items.get(index-1).setEditing(true);
		for(int i = index; i < items.size(); i++) {
			items.get(i).formUp();
		}
		items.appendTotalHeight(-items.getTextSize());
	}

	private final void scrollsPositionUpdate() {
		
		if(scrollV != null && scrollH != null) {
			scrollV.setPosition(x+w-SCROLL_WEIGHT, y);
			scrollH.setPosition(x,y+h-SCROLL_WEIGHT);
		}
	}	
	private final void scrollsSizeUpdate() {
		
		if(scrollV != null && scrollH != null) {
			scrollV.setSize(SCROLL_WEIGHT,h-SCROLL_WEIGHT);
			scrollH.setSize(w,SCROLL_WEIGHT);
		}
	}
	
	private final void scrollsTransformsUpdate() {
		scrollsPositionUpdate();
		scrollsSizeUpdate();
	}

	private final void scrollsValuesUpdate() {
		
		if(scrollV != null && scrollH != null) {
			float tempValueOfScrollH = scrollH.value.get(),
				  tempValueOfScrollV = scrollV.value.get();
			if(!items.list.isEmpty()) {
				scrollH.value.setMax(items.getCurrent().getTextWidth());
				scrollH.value.set(tempValueOfScrollH);
			}
			if(items.getTotalHeight() > EditText.this.getH()) {
				scrollV.value.setMinMax(h-items.getTotalHeight(), 0);
				scrollV.value.set(max(tempValueOfScrollV,scrollV.value.getMin()));
			} else {
				scrollV.value.setMinMax(-items.getTotalHeight()-items.getTextSize(), 0);
				scrollV.value.set(scrollV.value.getMax());
			}
		}
		
	}

	private final void CTRLV() {
		if(isInClipboardSeveralLines()) {
			insertLines(Clipboard.getAsArray());
		} else {
			insertLine(Clipboard.get());
		}
		
		scrollsValuesUpdate();
	}
	
	private final boolean isInClipboardSeveralLines() {
		return Clipboard.get().contains(String.valueOf('\n'));
	}
	
	private final void insertLine(String line) {
		items.getCurrent().insert(line);
		for(int i = 0; i < line.length(); i++) { cursor.next(); }
	}
	
	private final void insertLines(String[] lines) {
		for(int i = 0; i < lines.length; i++) {
			if(i == 0) {
				items.getCurrent().insert(lines[i]);
			} else {
				
				if(items.getCurrent().isEmpty()) {
					items.getCurrent().insert(lines[i]);
				} else {
					items.insert(lines[i]);
				}
				
				
				
				cursor.goInEnd();
				if(cursor.getPosY() > EditText.this.getH()*.9f) {
					scrollV.value.append(-items.getTextSize());
				}
			}
		}
	}
	
	private final void CTRLX() {
		Clipboard.set(selection.getText());
		
		items.deleteAllSelectedText();
		
		deleteItemsFromSelectedArea();
		
		selection.unselect();
		selection.setSelecting(false);
		
	}
	
	private final void keyEnter() {
		items.insert(cursor.getTextOfRightSide());
		
		scrollH.value.set(scrollH.value.getMin());
		cursor.goInStart();
	}
	
	private final void keyPageUp() {
		items.getCurrent().setEditing(false);
		items.getFirst().setEditing(true);
		
		cursor.goInStart();
		
		scrollH.value.set(scrollH.value.getMin());
		scrollV.value.set(0);
	}
	
	private final void keyPageDown() {
		items.getCurrent().setEditing(false);
		items.getLast().setEditing(true);
		
		cursor.goInEnd();
		
		scrollsValuesUpdate();
		
		scrollH.value.set(scrollH.value.getMin());
		scrollV.value.set(scrollV.value.getMin());
	}
	
	private final void keyHome() {
		cursor.setRow(0);
		scrollH.value.set(scrollH.value.getMin());
	}
	
	private final void keyEnd() {
		cursor.setRow(cursor.getMaxCharsInRow());
		scrollH.value.set(scrollH.value.getMax()*.9f);
	}
	
	private final void keyTab() {
		items.getCurrent().insert("  ");
		cursor.next();
		cursor.next();
	}
	
	private final void keyBackspace() {
		
		if(selection.isSelectedAllText()) {
			items.deleteAllText();
			selection.unselect();
			items.clear();
			while(items.getTotalHeight() < EditText.this.getH()) { items.add(""); }
			cursor.setColumn(0);
			cursor.setRow(0);
			return;
		}
		
		if(selection.isSelecting()) {
			items.deleteAllSelectedText();
			deleteItemsFromSelectedArea();
			selection.unselect();
			return;
		}
		
		if(!items.getCurrent().isEmpty() && cursor.getRow() == 0) {
			final String textFromRightSideToCursor = cursor.getTextOfRightSide();
			cursor.setColumn(cursor.getColumn()-1);
			cursor.goInEnd();
			items.getCurrent().append(textFromRightSideToCursor);
			removeEmptyItem(cursor.getColumn()+1);
			scrollsValuesUpdate();
			return;
		}
	
		if(items.getCurrent().isEmpty()) {
			removeEmptyItem(cursor.getColumn());
			cursor.goInEnd();
			scrollsValuesUpdate();
			return;
		}
		
		items.getCurrent().deleteChar();
		cursor.back();
	}
	
	private final boolean isAllowedChar(char ch) {
		String s = "\'\"!@#$%^&*()_+|\\|/=-[]{};:/?,.<>=-";
		return s.contains(String.valueOf(ch)) || Character.isDigit(ch) || Character.isAlphabetic(ch) || Character.isWhitespace(ch);
	}

	private final void deleteItemsFromSelectedArea() {
		for(int i = selection.getLastColumn(); i > selection.getFirstColumn(); i--) {
			removeEmptyItem(i);
		}
	}

	public final void loadText(String path) {
		items.clear();
		String[] lines = app.loadStrings(path);
		for(int i = 0; i <  lines.length; i++) {
			items.add(lines[i]);
		}
		
		scrollV.value.setMinMax(h-items.getTotalHeight(), 0);
		scrollV.value.set(0);
		
		scrollH.value.setMax(items.getMaxTextWidthFromItems());
		scrollH.value.set(scrollH.value.getMin());
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
	
	private final void autoCheckResize() {
		if(pg.width != (int) w || pg.height != (int) h) {
			pg = app.createGraphics((int) w, (int) h,app.sketchRenderer());
			System.out.println("PGraphics was recreated");
		}
	}
	
	public final class Items {
		public final Color fill;
		private int textSize;
		private final List<Item> list;
		private float totalHeight;
		
		public Items() {
			fill = new Color(0);
			textSize = 32;
			list = new ArrayList<Item>();
			for(int i = 0; i < getH()/textSize; i++) {
				list.add(new Item(i*textSize,""));
				totalHeight += textSize;
			}
			
			scrollV.value.setMinMax(h-totalHeight, 0);
			scrollV.value.set(0);
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
					if(item.isEditing()) { cursor.setColumn(i); }
				} else {
					item.setEditing(false);
					}
			}
		}
		
		private final boolean itemInside(Item item) {
			return item.getY() > getY()-item.getH() && item.getY() < getY()+getH();
		}
		
		private final void deleteAllSelectedText() {
			for(Item item : list) { item.removeSelectedText(); }
		}
		
		private final void deleteAllText() {
			for(Item item : list) { item.removeAllText(); }
		}

		private final void selectAllText() {
			for(Item item : list) { item.setFullSelect(true); }
		}
		
		public final int size() {
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
			
			scrollsValuesUpdate();
		}
		
		public final int getTextSize() { return textSize; }
		
		protected final float getTotalHeight() { return totalHeight; }
		
		private final void appendTotalHeight(int value) {
			totalHeight += value;
		}

		protected final void add(String text) {
			list.add(new Item(list.size()*textSize,text));
			totalHeight += textSize;
		}
		
		private final void clear() {
			list.clear();
			totalHeight = 0;
			scrollsValuesUpdate();
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
				scrollV.value.append(textSize);
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
				scrollV.value.append(-textSize);
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
				float tempValueOfScrollV = scrollV.value.get();
				scrollV.value.setMinMax(h-totalHeight, 0);
				scrollV.value.set(tempValueOfScrollV);
			}
			
			if(cursor.getPosY() > getH()-textSize*2) {
				scrollV.value.append(-textSize);
			}
		}
		
		private final Item getCurrent() {
			return list.get(cursor.getColumn());
		}

		private final Item get(int index) {
			 return list.get(constrain(index,0,list.size()));
		}
		
		private final Item getFirst() {
			return list.get(0);
		}
		
		private final Item getLast() {
			return list.get(list.size()-1);
		}
		
		private final class Item {
			private final StringBuilder sb;
			private final Event event;
			private float shiftY,textWidth;
			private boolean isEditing, isFullSelected,isPartSelected;
			private int firstSelectedRow,lastSelectedRow;
			
			public Item(float shiftY, String text) {
				sb = new StringBuilder(text);
				this.shiftY = shiftY;
				event = new Event();
				
			}

			
			private final void draw(PGraphics pg) {
				event.listen(x,getY(),x+w,getH());
				
				if(!isFullSelected && !isPartSelected) {
					firstSelectedRow = lastSelectedRow = 0;
				}
				
				if(pg != null) {
					pg.text(sb.toString(),-scrollH.value.get(),getInsideY()+textSize/2);
					
					textWidth = pg.textWidth(sb.toString());
					
					if(focused && isEditing) {
						pg.pushStyle();
						pg.fill(0,12);
						pg.rect(0,getInsideY(),w,textSize);
						pg.popStyle();
					}
					
						pg.pushStyle();
						pg.fill(0,0,255,64);
						if(isFullSelected) {
							pg.rect(-scrollH.value.get(),getInsideY(),getTextWidth(),textSize);
						}
						if(isPartSelected) {
							pg.rect(-scrollH.value.get()+getTextWidth(min(firstSelectedRow,lastSelectedRow)),getInsideY(),getTextWidth(firstSelectedRow,lastSelectedRow),textSize);
						}
						pg.popStyle();
					}
				
				if(event.pressed()) {
					isEditing = true;
					final float LOCAL_MOUSE_X = app.mouseX-x+scrollH.value.get();
					
					for(int i = 0; i < sb.length(); i++) {
						if(LOCAL_MOUSE_X > getTextWidth(cursor.getRow())+getCharWidth(cursor.getRow())/2) {
							cursor.justNext();
						} else {
							cursor.justBack();
						}
					}
					
					if(LOCAL_MOUSE_X > getTextWidth(cursor.getRow())+getCharWidth(cursor.getRow())/2) {
						cursor.justNext();
					}
					
					cursor.resetTimer();
				}
				
				if(app.mousePressed && event.outside()) {
					isEditing = false;
				}
			}
			
			private final void setFullSelect(boolean isFullSelected) {
				this.isFullSelected = isFullSelected;
				
				if(isFullSelected) {
					firstSelectedRow = 0;
					lastSelectedRow = sb.length();
				}
				
				isPartSelected = false;
			}
			
			private final void unselect() {
				isFullSelected = isPartSelected = false;
				firstSelectedRow = lastSelectedRow = 0;
			}
			
			private final void setPartSelected(int firstRow, int lastRow) {
				isPartSelected = true;
				isFullSelected = false;
				firstSelectedRow = firstRow;
				lastSelectedRow = lastRow;
			}
			
			private final void setRightSideSelected() {
				isPartSelected = true;
				isFullSelected = false;
				lastSelectedRow = sb.length();
			}
			
			private final void setRightSideSelected(int index) {
				isPartSelected = true;
				isFullSelected = false;
				firstSelectedRow = index;
				lastSelectedRow = sb.length();
				
			}
			
			
			private final void setLeftSideSelected(int endRow) {
				isPartSelected = true;
				isFullSelected = false;
				firstSelectedRow = 0;
				this.lastSelectedRow = endRow;
			}
			
			private final String getSelectedText() {
				return sb.toString().substring(min(firstSelectedRow,lastSelectedRow),max(firstSelectedRow,lastSelectedRow));
			}
			
			private final void removeSelectedText() {
				if(sb.length() > 0) {
					sb.delete(min(firstSelectedRow,lastSelectedRow), max(firstSelectedRow,lastSelectedRow));
				}
				unselect();
			}
			
			private final void removeAllText() {
				sb.delete(0, sb.length());
				unselect();
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
				return shiftY+scrollV.value.get()+y;
			}
			
			private final float getInsideY() {
				return shiftY+scrollV.value.get();
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
				return getTextWidth(0,indexEnd);
			}
			
			private final float getTextWidth(int indexStart, int indexEnd) {
				float width = 0;
				
				if(indexStart < 0 || indexStart > sb.length()) { return width; }
				if(indexEnd < 0 || indexEnd > sb.length()) { return width; }
				
				width = pg.textWidth(sb.toString().substring(min(indexStart,indexEnd),max(indexStart,indexEnd)));
				
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
				if(sb.toString().isEmpty()) { return; }
				if(cursor.getRow()-1 >= 0) {
					sb.delete(cursor.getRow()-1, cursor.getRow());
				}
			}
			
			private final boolean isEmpty() {
				return sb.toString().isEmpty();
			}
			
			private final void insert(String txt) {
				if(isEmpty()) {
					sb.append(txt);
				} else {
					sb.insert(cursor.getRow(),txt);
				}
			}
			
			private final void append(String txt) {
				sb.append(txt);
			}
		}
	
		
	}
	
	public final class Cursor {
		public final Color fill;
		private final int MAX_DURATION = 60;
		private float posX,posY;
		private int row,column,
					duration;
		
		public Cursor() {
			fill = new Color(0);
		}
		
		private final void draw(PGraphics pg) {
			posX = -scrollH.value.get()+pg.textWidth(items.list.get(getColumn()).sb.toString().substring(0, getRow()));
			posY = items.list.get(getColumn()).getInsideY();
			
			if(duration < MAX_DURATION) { duration++; } else { duration = 0; }
			
			if(duration < MAX_DURATION/2) {
				float dynamicHeight = abs(map(duration,0,MAX_DURATION,-items.getTextSize()/2,items.getTextSize()/2))/2;
				pg.pushStyle();
				pg.stroke(fill.get());
				pg.strokeWeight(2);
				pg.line(posX, posY+dynamicHeight, posX, posY+items.getTextSize()-dynamicHeight);
				pg.popStyle();
			}
			
		}

		protected final float getPosX() {
			return posX;
		}

		protected final float getPosY() {
			return posY;
		}

		protected final int getRow() {
			return constrain(row,0,getMaxCharsInRow());
		}

		protected final void setRow(int row) {
			this.row = row;
		}

		protected final int getColumn() {
			return column;
		}

		protected final int getColumnsCount() {
			return items.list.size();
		}
	
		protected final void setColumn(int column) {
			this.column = column;
		}
		
		protected final void back() {
			if(getRow() > 0) { row--; }
			if(posX < getW()/2) {
				scrollH.value.append(-items.getTextSize()/2);
			}
		}
		
		protected final void next() {
			if(getRow() < getMaxCharsInRow()) { row++; }
			if(posX > getW()*.8f) {
				scrollH.value.append(items.getTextSize());
			}
		}
		
		protected final void justNext() {
			if(getRow() < getMaxCharsInRow()) { row++; }
		}
		
		protected final void justBack() {
			if(getRow() > 0) { row--; }
		}

		protected final void goInStart() {
			row = 0;
		}
		
		protected final void goInEnd() {
			row = getMaxCharsInRow();
		}
		
		protected final void resetTimer() {
			duration = 0;
		}
		
		protected final int getMaxCharsInRow() {
			if(getColumn() > items.list.size()-1) { column--; }
			
			return items.list.get(column).getCharsCount();
		}
		
		protected final String getTextOfRightSide() {
			String txt = items.list.get(getColumn()).getText().substring(getRow(), items.list.get(getColumn()).getText().length());
			items.list.get(getColumn()).getStringBuilder().delete(getRow(), items.list.get(getColumn()).getText().length());
			return txt;
		}
	}
		
	public final class Selection {
		private int startColumn,endColumn,startRow,endRow;
		private final Event eventIn;
		private boolean isSelecting,selectingWasStoped,isSelectedAllText;
		
		public Selection() {
			eventIn = new Event();
			startColumn = endColumn = startRow = endRow = -1;
		}
		
		private final void draw() {
			eventIn.listen(EditText.this);
			
			if(isAllowedStateToStartSelecting()) {
				
				if(selectingWasStoped) {
					unselect();
					selectingWasStoped = false;
				}
				
				if(startColumn == -1) { startColumn = cursor.getColumn(); }
				endColumn = cursor.getColumn();
				if(startRow == -1) { startRow = cursor.getRow(); }
				endRow = cursor.getRow();
				
				if(items.getTotalHeight() > EditText.this.getH()) {
				
					if(cursor.getPosY() < getH()*.2f) {
						scrollV.value.append(getH()*.01f);
					}
					
					if(cursor.getPosY() > getH()*.8f) {
						scrollV.value.append(-getH()*.01f);
					}
					
					if(cursor.getPosX() < getW()*.2f) {
						scrollH.value.append(-getW()*.01f);
					}
					
					if(cursor.getPosX() > getW()*.8f) {
						scrollH.value.append(getW()*.01f);
					}
				
				}
				
				
			} else {
				if(!selectingWasStoped) {
					selectingWasStoped = true;
				}
			}
			
			isSelecting = (startRow != endRow) || (startColumn != endColumn);
			
			if(isSelecting && !scrollV.thumb.event.holding() && !scrollH.thumb.event.holding()) {
				
				for(int i = 0; i < items.size(); i++) {
					
					if(i == startColumn) {
						if(isMultiLinesSelected()) {
							if(isDown()) {
								items.get(startColumn).setRightSideSelected();
							}
							
							if(isUp()) {
								items.get(endColumn).setRightSideSelected(endRow);
							}
							
						} else {
							items.get(i).setPartSelected(startRow, endRow);
						}
					}
					
					if(i == endColumn && isMultiLinesSelected()) {
						if(isDown()) {
							items.get(i).setLeftSideSelected(endRow);
						}
						if(isUp()) {
							items.get(startColumn).setLeftSideSelected(startRow);
						}
					}
					
					if(i > min(startColumn,endColumn) && i < max(startColumn,endColumn)) {
						items.get(i).setFullSelect(true);
					} else {
						if(i < min(startColumn,endColumn) || i > max(startColumn,endColumn)) {
							items.get(i).setFullSelect(false);
						}
					}
					
				}
			
			}
		
			if(eventIn.clicked(2)) {
				unselect();
			}
		}
		
		private final boolean isAllowedStateToStartSelecting() {
			return event.pressed() &&
				   !scrollV.thumb.event.holding() &&
				   !scrollH.thumb.event.holding() &&
				   !scrollV.event.inside() &&
				   !scrollH.event.inside();
		}
		
		private final boolean isSelecting() { return isSelecting; }
		
		private final void unselect() {
			startColumn = endColumn = startRow = endRow = -1;
			for(int i = 0; i < items.size(); i++) {
				items.get(i).unselect();
			}
			isSelecting = false;
			selectingWasStoped = true;
			setSelectedAllText(false);
		}
		
		private final boolean isSelectedAllText() {
			return isSelectedAllText;
		}
		
		private final int getFirstColumn() {
			return min(startColumn,endColumn);
		}
		
		private final int getLastColumn() {
			return max(startColumn,endColumn);
		}


		private final void setSelectedAllText(boolean isSelectedAllText) {
			this.isSelectedAllText = isSelectedAllText;
			if(isSelectedAllText) { isSelecting = true; }
		}

		private final boolean isMultiLinesSelected() {
			return startColumn != endColumn;
		}
		
		private final boolean isUp() {
			return endColumn < startColumn;
		}
		
		private final boolean isDown() {
			return endColumn > startColumn;
		}
		
		private final void setSelecting(boolean isSelecting) {
			this.isSelecting = isSelecting;
		}
		
		public final String getText() {
			StringBuilder sb = new StringBuilder();
			int countOfLines = getSelectedLines();
			
			
			
			if(countOfLines > 1) {
				for(Items.Item item : items.list) { 
					if(!item.getSelectedText().equals("")) {
						sb.append(item.getSelectedText()+'\n');
					}
				}
			} else {
				for(Items.Item item : items.list) { 
					if(!item.getSelectedText().equals("")) {
						sb.append(item.getSelectedText());
					}
				}
			}
			 
			
			
			return sb.toString(); 
		}
		
		private final int getSelectedLines() {
			int countOfLines = 0;
			
			for(Items.Item item : items.list) {
				 
				if(!item.getSelectedText().equals("")) {
					countOfLines++;
				}
				
			}
			
			return countOfLines;
		}
		
	}
	
}