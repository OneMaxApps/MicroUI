package microui.component;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_END;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_HOME;
import static java.awt.event.KeyEvent.VK_PAGE_DOWN;
import static java.awt.event.KeyEvent.VK_PAGE_UP;
import static java.awt.event.KeyEvent.VK_V;
import static java.awt.event.KeyEvent.VK_X;
import static microui.event.Event.checkKey;
import static microui.util.Constants.VERTICAL;
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

import microui.core.base.Component;
import microui.core.interfaces.KeyPressable;
import microui.core.interfaces.Scrollable;
import microui.core.style.Color;
import microui.event.Event;
import microui.event.EventType;
import microui.util.Clipboard;
import microui.util.Metrics;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.event.MouseEvent;

public class EditText extends Component implements Scrollable, KeyPressable {
	protected final float LEFT_OFFSET = 10, SCROLLS_WEIGHT;
	private static final String ALLOWED_CHARS = " ,.<>[]{}()+-*/\\\'\";:?!@#$%^&|_`~=";
	
	public final Items items;
	public final Cursor cursor;
	public final Selection selection;
	public final Scroll scrollV,scrollH;
	
	protected PGraphics pg;
	protected PFont font;
	
	protected boolean isFocused;
	
 	public EditText(float x, float y, float w, float h) {
		super(x, y, w, h);
		fill.set(255);
		visible();
		
		final int defaultMinWeightForScrolls = 10;
		final float maxWeightForScrolls = w*.025f;
		SCROLLS_WEIGHT = max(defaultMinWeightForScrolls,maxWeightForScrolls);
		
		scrollV = new Scroll();
		scrollH = new Scroll();
		initDefaultScrollsSettings();
		
		items = new Items();
		cursor = new Cursor();
		selection = new Selection();
		
		createGraphics(w,h);
		
		scrollsValuesUpdate();
		
		callback.addListener(EventType.DOUBLE_CLICKED, () -> selection.unselect());;
	}
 	
	public EditText() {
		this(app.width*.1f,app.height*.1f,app.width*.8f,app.height*.8f);
	}
	
	@Override
	protected void update() {
		event.listen(this);
		
		tooltip.setAdditionalCondition(!isFocused);
		
		scrollH.setVisible(isFocused);
		scrollV.setVisible(isFocused);
		
		pg.beginDraw();
			pg.background(fill.get());
			items.draw(pg);
			cursor.draw(pg);
		pg.endDraw();
		
		app.image(pg, x, y, w, h);
		scrollV.draw();
		scrollH.draw();
		
		events();
		selection.update();
	}
	
	@Override
	public void onChangeBounds() {
		super.onChangeBounds();
		if(scrollH == null || scrollV == null) { return; }
		
		scrollsTransformsUpdate();
		
		scrollH.onChangeBounds();
		scrollV.onChangeBounds();
		
	}
	@Override
	public final void mouseWheel(MouseEvent e) {
		if(isFocused) {
			if(checkKey(CONTROL)) {
				items.setTextSize(items.getTextSize()+e.getCount());
				return;
			}
			
			if(!scrollH.event.inside()) {
				scrollV.scrolling.init(e,event.inside());
				scrollV.autoScroll();
			}
			
			scrollH.scrolling.init(e);
			scrollH.autoScroll();

		}
		
	}
	
	@Override
	public final void keyPressed() {
		scrollsValuesUpdate();
		
		if(!isFocused) { return; }
			
		cursor.resetTimer();
		
		if(Event.checkKey(CONTROL)) {
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
			case UP:
				items.goUpToEditing();
				selection.unselect();
			break;
			
			case DOWN:
				items.goDownToEditing();
				selection.unselect();
			break;
			
			case LEFT:
				cursor.back();
				selection.unselect();
			break;
			
			case RIGHT:
				cursor.next();
				selection.unselect();
			break;
			
			case TAB:
				keyTab();
				selection.unselect();
			break;
			
			case BACKSPACE:	 keyBackspace(); break;
			
			case VK_HOME :
				keyHome();
				selection.unselect();
			break;
			
			case VK_END:
				keyEnd();
				selection.unselect();
			break;
			
			case VK_PAGE_UP:
				keyPageUp();
				selection.unselect();
			break;
			
			case VK_PAGE_DOWN:
				keyPageDown(); 
				selection.unselect();
			break;
			
			case VK_ENTER:
				keyEnter();
				items.deleteAllSelectedText();
				selection.unselect();
			break;
			
			default:
				items.deleteAllSelectedText();
				selection.unselect();
				
				if(isAllowedChar(app.key)) {
					items.getCurrent().insert(String.valueOf(app.key));
					cursor.next();
					scrollH.value.setMax(items.getMaxTextWidthFromItems());
				}
				break;
		}
		
	}
	
	public final boolean isFocused() { return isFocused; }
	
	public final void setFocused(boolean isFocused) { this.isFocused = isFocused; }
	
	private final void events() {
		if(!app.mousePressed) { checkDimensions(); }
		
		if(event.clicked()) { isFocused = true; }
		
		if(app.mousePressed && event.outside() && !event.holding()) { isFocused = false; }
	}
	
	private final void initDefaultScrollsSettings() {
		scrollsPositionUpdate();
		
		scrollV.setOrientation(VERTICAL);
		
		scrollsSizeUpdate();
		
		scrollH.value.setMin(-LEFT_OFFSET);
		scrollH.value.set(scrollH.value.getMin());
		
		scrollsValuesUpdate();
		
		scrollV.scrolling.setVelocity(.1f);
		scrollH.scrolling.setVelocity(.5f);
	}
	
	private final void createGraphics(final float w, final float h) {
		pg = app.createGraphics((int) w, (int) h, app.sketchRenderer());
		Metrics.register(pg);
	}
	
	private final void removeEmptyItem(final int index) {
		if(items.count() <= 1) { return; }
		if(index < 0 || index > items.count()-1) { return; }
		if(!items.get(index).isEmpty()) { return; }
		
		items.list.remove(index);
		items.get(index-1).setEditing(true);
		for(int i = index; i < items.count(); i++) {
			items.get(i).formUp();
		}
		items.appendTotalHeight(-items.getTextSize());
	}
	
	private final void scrollsPositionUpdate() {
		if(scrollV != null && scrollH != null) {
			scrollV.setPosition(x+w-SCROLLS_WEIGHT, y);
			scrollH.setPosition(x,y+h-SCROLLS_WEIGHT);
		}
	}	

	private final void scrollsSizeUpdate() {
		if(scrollV != null && scrollH != null) {
			scrollV.setSize(SCROLLS_WEIGHT,h-SCROLLS_WEIGHT);
			scrollH.setSize(w,SCROLLS_WEIGHT);
		}
	}
	
	private final void scrollsTransformsUpdate() {
		scrollsPositionUpdate();
		scrollsSizeUpdate();
	}
	
	private final void scrollsValuesUpdate() {
		if(scrollV == null || scrollH == null || items == null) { return; }
			
			if(!items.isEmpty()) {
				scrollH.value.setMax(items.getCurrent().getTextWidth());
				
			} else {
				scrollH.value.set(scrollH.value.getMin());
			}
			
			if(items.getTotalHeight() > EditText.this.getHeight()) {
				scrollV.value.setMin(h-items.getTotalHeight());
			} else {
				scrollV.value.setMin(0);
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
				if(cursor.getPosY() > EditText.this.getHeight()*.9f) {
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
		selection.setSelectingState(false);
		
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
		cursor.setCurrentColumn(0);
		scrollH.value.set(scrollH.value.getMin());
	}
	
	private final void keyEnd() {
		cursor.setCurrentColumn(cursor.getMaxCharsInRow());
		
		if(items.getCurrent().getTextWidth() > EditText.this.getWidth()) {
			scrollH.value.set(scrollH.value.getMax()*.5f);
		}
	}
	
	private final void keyTab() {
		items.getCurrent().insert("  ");
		cursor.next();
		cursor.next();
	}
	
	private final void clearAllSelectedText() {
		selection.unselect();
		items.clear();
		while(items.getTotalHeight() < EditText.this.getHeight()) { items.add(""); }
		cursor.setCurrentRow(0);
		cursor.setCurrentColumn(0);
		scrollV.value.set(0);
	}
	
	private final void clearSingleSelectedTextLine() {
		if(!selection.isMultiLinesSelected()) {
			if(selection.isSelectedToRight()) {
				for(int i = 0; i < cursor.getCurrentItem().getSelectedText().length(); i++) {
					cursor.back();
				}
			}
		}
		
		items.deleteAllSelectedText();
		deleteItemsFromSelectedArea();
		selection.unselect();
	}
	
	private final void clearAndJoinLines() {
		final String textFromRightSideToCursor = cursor.getTextOfRightSide();
		cursor.setCurrentRow(cursor.getCurrentRow()-1);
		cursor.goInEnd();
		items.getCurrent().append(textFromRightSideToCursor);
		removeEmptyItem(cursor.getCurrentRow()+1);
		scrollsValuesUpdate();
	}
	
	private final void clearEmptyLine() {
		if(cursor.getCurrentRow() == 0) { return; }
		
		removeEmptyItem(cursor.getCurrentRow());
		cursor.goInEnd();
		scrollV.value.append(items.getFirst().getH());
		scrollsValuesUpdate();
	}
	
	private final void keyBackspace() {
		if(selection.isSelectedAllText()) {
			clearAllSelectedText();
			return;
		}
		
		if(selection.isSelecting()) {
			clearSingleSelectedTextLine();
			return;
		}
		
		
		if(!items.getCurrent().isEmpty() && cursor.getCurrentColumn() == 0 && cursor.getCurrentRow() != 0) {
			clearAndJoinLines();
			return;
		}
		
		
		if(items.getCurrent().isEmpty()) {
			clearEmptyLine();
			return;
		}
		
		items.getCurrent().deleteChar();
		cursor.back();
	}
	
	private final boolean isAllowedChar(char ch) {
		return ALLOWED_CHARS.contains(String.valueOf(ch)) || Character.isDigit(ch) || Character.isAlphabetic(ch);
	}
	
	private final void deleteItemsFromSelectedArea() {
		for(int i = selection.getLastRow(); i > selection.getFirstRow(); i--) {
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
	
	private final void checkDimensions() {
		if(pg.width != (int) w || pg.height != (int) h) {
			createGraphics(w,h);
		}
	}
	
	public final class Items {
		public final Color fill;
		private int textSize;
		private final List<Item> list;
		private float totalHeight;
		
		protected Items() {
			fill = new Color(0);
			textSize = 32;
			list = new ArrayList<Item>();
			for(int i = 0; i < getHeight()/textSize; i++) {
				list.add(new Item(i*textSize,""));
				totalHeight += textSize;
			}
			
			scrollV.value.setMinMax(h-totalHeight, 0);
			scrollV.value.set(0);
		}
		
		private final void draw(final PGraphics pg) {
			fill.use(pg);
			if(font != null) { pg.textFont(font); }
			pg.textSize(textSize);
			pg.textAlign(CORNER,CENTER);
			
			for(int i = list.size()-1; i >= 0; i--) {
				Item item = list.get(i);
				if(itemInside(item)) {
					item.draw(pg);
					if(item.isEditing()) { cursor.setCurrentRow(i); }	
				} else {
					item.setEditing(false);
					}
			}
		}
		
		protected boolean isEmpty() {
			return list.isEmpty();
		}
		
		private final boolean itemInside(Item item) {
			return item.getY() > getY()-item.getH() && item.getY() < getY()+getHeight();
		}
		
		private final void deleteAllSelectedText() {
			for(Item item : list) { item.removeSelectedText(); }
		}
		
		private final void selectAllText() {
			for(Item item : list) {
				item.setFullSelect(true);
			}
		}
		
		private final int count() {
			return list.size();
		}
		
		public final void setTextSize(float textSize) {
			if(textSize <= 2) { return; }
			this.textSize = (int) textSize;
			totalHeight = 0;
			final float TEMP_SCROLL_V_VALUE = scrollV.value.get();
			
			for(int i = 0; i < list.size(); i++) {
				Item item = list.get(i);
				item.setShiftY(i*textSize);
				totalHeight += textSize;
			}
			
			scrollsValuesUpdate();
			scrollV.value.set(TEMP_SCROLL_V_VALUE+textSize);
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
			
			if(cursor.getPosY() > getHeight()*.8f) {
				scrollV.value.append(-textSize);
			}
		}
		
		private final Item getCurrent() {
			return cursor.getCurrentItem();
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
			
			private Item(final float shiftY, String text) {
				sb = new StringBuilder(text);
				this.shiftY = shiftY;
				event = new Event();
				
			}
			
			private final void draw(final PGraphics pg) {
				event.listen(x,getY(),x+w,getH());
				
				if(!isFullSelected && !isPartSelected) {
					firstSelectedRow = lastSelectedRow = 0;
				}
				
				if(pg != null) {
					pg.text(sb.toString(),-scrollH.value.get(),getInsideY()+textSize/2);
					
					textWidth = pg.textWidth(sb.toString());
					
					if(isFocused && isEditing) {
						pg.pushStyle();
						pg.fill(0,12);
						pg.rect(0,getInsideY(),w,textSize);
						pg.popStyle();
					}
					
						if(firstSelectedRow != lastSelectedRow) {
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
					}
				
				if(event.pressed() && !scrollH.event.holding() && !scrollV.event.holding()) {
					isEditing = true;
					final float LOCAL_MOUSE_X = app.mouseX-x+scrollH.value.get();
					
					for(int i = 0; i < sb.length(); i++) {
						if(LOCAL_MOUSE_X > getTextWidth(cursor.getCurrentColumn())+getCharWidth(cursor.getCurrentColumn())/2) {
							cursor.justNext();
						} else {
							cursor.justBack();
						}
					}
					
					if(LOCAL_MOUSE_X > getTextWidth(cursor.getCurrentColumn())+getCharWidth(cursor.getCurrentColumn())/2) {
						cursor.justNext();
					}
					
					cursor.resetTimer();
					
					scrollsValuesUpdate();
				}
				
				if(app.mousePressed && event.outside()) { isEditing = false; }
				
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
				if(cursor.getCurrentColumn()-1 >= 0) {
					sb.delete(cursor.getCurrentColumn()-1, cursor.getCurrentColumn());
				}
			}
			
			private final boolean isEmpty() {
				return sb.toString().isEmpty();
			}
			
			private final void insert(String txt) {
				if(isEmpty()) {
					sb.append(txt);
				} else {
					sb.insert(cursor.getCurrentColumn(),txt);
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
		private int column,row,
					duration;
		
		private Cursor() {
			fill = new Color(0);
		}
		
		private final void draw(final PGraphics pg) {
			if(!isFocused) { return; }
			
			posX = -scrollH.value.get()+pg.textWidth(items.list.get(getCurrentRow()).sb.toString().substring(0, getCurrentColumn()));
			posY = items.list.get(getCurrentRow()).getInsideY();
			
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
		
		public final int getCurrentColumn() {
			return constrain(column,0,getMaxCharsInRow());
		}
		protected final void setCurrentColumn(int column) {
			this.column = column;
		}
		
		public final int getCurrentRow() {
			return row;
		}
		
		protected final void setCurrentRow(int row) {
			this.row = row;
		}
		
		public final int getRows() {
			return items.list.size();
		}
	
		protected final void back() {
			if(getCurrentColumn() > 0) { column--; }
			if(posX < getWidth()/2) {
				scrollH.value.append(-items.getTextSize()/2);
			}
		}
		
		protected final void next() {
			if(getCurrentColumn() < getMaxCharsInRow()) { column++; }
			if(posX > getWidth()*.8f) {
				scrollH.value.append(items.getTextSize());
			}
		}
		
		protected final void justNext() {
			if(getCurrentColumn() < getMaxCharsInRow()) { column++; }
		}
		
		protected final void justBack() {
			if(getCurrentColumn() > 0) { column--; }
		}
		
		protected final void goInStart() {
			column = 0;
		}
		
		protected final void goInEnd() {
			column = getMaxCharsInRow();
		}
		
		protected final void resetTimer() {
			duration = 0;
		}
		
		protected final int getMaxCharsInRow() {
			return getCurrentItem().getCharsCount();
		}
		
		protected final String getTextOfRightSide() {
			String txt = items.getCurrent().getText().substring(getCurrentColumn(), items.getCurrent().getText().length());
			items.getCurrent().getStringBuilder().delete(getCurrentColumn(), items.getCurrent().getText().length());
			
			return txt;
		}
		
		private final Items.Item getCurrentItem() {
			if(getCurrentRow() < 0) { row = 0; }
			if(getCurrentRow() > items.list.size()-1) { row = items.list.size()-1; }
			return items.list.get(getCurrentRow());
		}
	}
		
	public final class Selection {
		private int startColumn,endColumn,startRow,endRow;
		private boolean isSelecting,selectingWasStoped,isSelectedAllText;
		
		private Selection() {
			startColumn = endColumn = startRow = endRow = -1;
		}
		
		private final void update() {
			
			if(isAllowedStateToStartSelecting()) {
				
				if(selectingWasStoped) {
					unselect();
					selectingWasStoped = false;
				}
				
				if(startRow == -1) { startRow = cursor.getCurrentRow(); }
				endRow = cursor.getCurrentRow();
				if(startColumn == -1) { startColumn = cursor.getCurrentColumn(); }
				endColumn = cursor.getCurrentColumn();
				
				if(items.getTotalHeight() > EditText.this.getHeight()) {
				
					if(cursor.getPosY() < getHeight()*.2f) {
						scrollV.value.append(getHeight()*.02f);
					}
					
					if(cursor.getPosY() > getHeight()*.8f) {
						scrollV.value.append(-getHeight()*.02f);
					}
					
					if(cursor.getPosX() < getWidth()*.2f) {
						scrollH.value.append(-getWidth()*.02f);
					}
					
					if(cursor.getPosX() > getWidth()*.8f) {
						scrollH.value.append(getWidth()*.02f);
					}
				
				}
				
				
			} else {
				if(!selectingWasStoped) {
					selectingWasStoped = true;
				}
			}
			
			isSelecting = (startRow != endRow) || (startColumn != endColumn);
			
			if(isSelecting && !scrollV.thumb.event.holding() && !scrollH.thumb.event.holding()) {
				
				for(int i = 0; i < items.count(); i++) {
					
					if(i == startRow) {
						if(isMultiLinesSelected()) {
							if(isDown()) {
								items.get(startRow).setRightSideSelected();
							}
							
							if(isUp()) {
								items.get(endRow).setRightSideSelected(endColumn);
							}
							
						} else {
							items.get(i).setPartSelected(startColumn, endColumn);
						}
					}
					
					if(i == endRow && isMultiLinesSelected()) {
						if(isDown()) {
							items.get(i).setLeftSideSelected(endColumn);
						}
						if(isUp()) {
							items.get(startRow).setLeftSideSelected(startColumn);
						}
					}
					
					if(i > min(startRow,endRow) && i < max(startRow,endRow)) {
						items.get(i).setFullSelect(true);
					} else {
						if(i < min(startRow,endRow) || i > max(startRow,endRow)) {
							items.get(i).setFullSelect(false);
						}
					}
					
				}
			
			}

		}

		public final String getText() {
			final StringBuilder sb = new StringBuilder();
			
			for(Items.Item item : items.list) { 
				if(!item.getSelectedText().isEmpty()) {
					sb.append(isMultiLinesSelected() || items.getCurrent().isFullSelected ? item.getSelectedText()+'\n' : item.getSelectedText());
				}
			}
			
			return sb.toString(); 
		}
		
		private final boolean isAllowedStateToStartSelecting() {
			return event.holding() &&
				   !scrollV.thumb.event.holding() &&
				   !scrollH.thumb.event.holding() &&
				   event.dragged();
		}
		
		private final boolean isSelecting() { return isSelecting; }
		
		private final void unselect() {
			startRow = endRow = startColumn = endColumn = -1;
			
			items.list.forEach(item -> item.unselect());
			
			isSelecting = false;
			selectingWasStoped = true;
			setSelectedAllText(false);
		}
		
		private final boolean isSelectedAllText() {
			return isSelectedAllText;
		}
		
		private final int getFirstRow() {
			return min(startRow,endRow);
		}
		
		private final int getLastRow() {
			return max(startRow,endRow);
		}
		
		private final void setSelectedAllText(boolean isSelectedAllText) {
			this.isSelectedAllText = isSelectedAllText;
			if(isSelectedAllText) { isSelecting = true; }
		}
		
		private final boolean isMultiLinesSelected() {
			return startRow != endRow;
		}
		
		private final boolean isUp() {
			return endRow < startRow;
		}
		
		private final boolean isDown() {
			return endRow > startRow;
		}
		
		private final void setSelectingState(boolean isSelecting) {
			this.isSelecting = isSelecting;
		}
		
		private final boolean isSelectedToRight() {
			return startColumn < endColumn;
		}
		
	}
	
}