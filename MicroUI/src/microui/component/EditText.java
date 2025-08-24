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
import static microui.event.EventType.CLICKED;
import static microui.event.EventType.DOUBLE_CLICKED;
import static microui.event.EventType.INSIDE;
import static microui.event.EventType.OUTSIDE;
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
import java.util.Objects;

import microui.constants.Orientation;
import microui.core.base.Component;
import microui.core.interfaces.KeyPressable;
import microui.core.interfaces.Scrollable;
import microui.core.style.Color;
import microui.event.Event;
import microui.util.Clipboard;
import microui.util.Metrics;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.event.MouseEvent;

public class EditText extends Component implements Scrollable, KeyPressable {
	private static final int LEFT_OFFSET = 10;
	private static final String ALLOWED_CHARS = " ,.<>[]{}()+-*/\\\'\";:?!@#$%^&|_`~=";
	
	private float scrollsWeight;
	private boolean isFocused,isMouseOutsideFromScrollV;
	
	private final Scroll scrollV, scrollH;
	private final Items items;
	private final Selection selection;
	private final Cursor cursor;

	private PGraphics graphics;
	private PFont font;
	
	public EditText(float x, float y, float w, float h) {
		super(x, y, w, h);
		color.set(255);
		visible();
		
		scrollV = new Scroll();
		scrollH = new Scroll();
		initDefaultScrollsSettings();

		items = new Items();
		selection = new Selection();
		cursor = new Cursor();

		createGraphics();

		scrollsValuesUpdate();

		callback.addListener(DOUBLE_CLICKED, () -> {
			if (selection.isSelectedAllText()) {
				selection.unselect();
			} else {
				selection.setSelectedAllText(true);
				items.selectAllText();
			}
		});
		callback.addListener(CLICKED, () -> setFocus(true));
		

		scrollV.addListener(INSIDE, () -> isMouseOutsideFromScrollV = false);
		scrollV.addListener(OUTSIDE, () -> isMouseOutsideFromScrollV = true);
	}

	public EditText() {
		this(app.width * .1f, app.height * .1f, app.width * .8f, app.height * .8f);
	}

	@Override
	protected void update() {
		event.listen(this);

		tooltip.setAdditionalCondition(!isFocused);

		scrollH.setVisible(isFocused);
		scrollV.setVisible(isFocused);

		graphics.beginDraw();
		graphics.background(color.get());
		items.draw(graphics);
		cursor.draw(graphics);
		graphics.endDraw();

		app.image(graphics, x, y, w, h);
		scrollV.draw();
		scrollH.draw();

		mouseEvents();
		selection.update();

	}

	@Override
	public void onChangeBounds() {
		super.onChangeBounds();
		if (scrollH == null || scrollV == null) {
			return;
		}

		scrollsTransformsUpdate();

	}

	@Override
	public final void mouseWheel(MouseEvent e) {
		if (isFocused) {
			if (checkKey(CONTROL)) {
				items.setTextSize(items.getTextSize() + e.getCount());
				return;
			}

			if (isMouseOutsideFromScrollV) {
				scrollV.getScrolling().init(e, event.inside());
				scrollV.autoScroll();
			}

			scrollH.getScrolling().init(e);
			scrollH.autoScroll();

		}

	}

	@Override
	public final void keyPressed() {
		scrollsValuesUpdate();

		if (!isFocused) {
			return;
		}

		cursor.resetTimer();

		if (Event.checkKey(CONTROL)) {
			if (Event.checkKey(VK_C)) {
				Clipboard.set(selection.getText());
			}
			if (Event.checkKey(VK_V)) {
				ctrlV();
			}
			if (Event.checkKey(VK_X)) {
				ctrlX();
			}
			if (Event.checkKey(VK_A)) {
				items.selectAllText();
				selection.setSelectedAllText(true);
			}
			return;
		}

		switch (app.keyCode) {
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

		case BACKSPACE:
			keyBackspace();
			break;

		case VK_HOME:
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

			if (isAllowedChar(app.key)) {
				getCurrentItem().insert(String.valueOf(app.key));
				cursor.next();
				scrollH.getValue().setMax(items.getMaxTextWidthFromItems());
			}
			break;
		}

	}

	public final boolean isFocused() {
		return isFocused;
	}

	public final void setFocus(boolean isFocused) {
		this.isFocused = isFocused;
	}

	public final void loadText(String path) {
		items.clear();
		String[] lines = app.loadStrings(path);
		for (int i = 0; i < lines.length; i++) {
			items.add(lines[i]);
		}

		scrollV.getValue().setMinMax(h - items.getTotalHeight(), 0);
		scrollV.getValue().set(0);

		scrollH.getValue().setMax(items.getMaxTextWidthFromItems());
		scrollH.getValue().set(scrollH.getValue().getMin());
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

	public final String getSelectedText() {
		return selection.getText();
	}

	public final Color getItemsTextColor() {
		return items.color;
	}

	public final float getTextSize() {
		return items.textSize;
	}

	public final void setTextSize(float size) {
		items.setTextSize(size);
	}

	public final Color getCursorColor() {
		return cursor.color;
	}

	public final int getCurrentColumn() {
		return cursor.getCurrentColumn();
	}

	public final int getCurrentRow() {
		return cursor.getCurrentRow();
	}

	public final int getRows() {
		return cursor.getRows();
	}

	public final Scroll getScrollV() {
		return scrollV;
	}

	public final Scroll getScrollH() {
		return scrollH;
	}

	public final Color getSelectColor() {
		return items.selectColor;
	}

	private void calculateScrollsWeight() {
		final int defaultMinWeightForScrolls = 10;
		final float maxWeightForScrolls = (w+h) * .0125f;
		scrollsWeight = max(defaultMinWeightForScrolls, maxWeightForScrolls);
	}

	private Items.Item getCurrentItem() {
		return items.list.get(getCurrentRow());
	}

	private void mouseEvents() {
		if (!app.mousePressed) {
			checkDimensions();
		}

		if (app.mousePressed && event.outside() && !event.holding()) {
			isFocused = false;
		}
	}

	private void initDefaultScrollsSettings() {
		calculateScrollsWeight();
		
		scrollsPositionUpdate();

		scrollV.setOrientation(Orientation.VERTICAL);

		scrollsSizeUpdate();

		scrollH.getValue().setMin(-LEFT_OFFSET);
		scrollH.getValue().set(scrollH.getValue().getMin());

		scrollsValuesUpdate();

		scrollV.getScrolling().setVelocity(.1f);
		scrollH.getScrolling().setVelocity(.5f);
	}

	private void createGraphics() {
		graphics = app.createGraphics((int) getWidth(), (int) getHeight(), app.sketchRenderer());
		Metrics.register(graphics);
	}

	private void removeEmptyItem(int index) {
		if (items.count() <= 1) {
			return;
		}
		
		index = constrain(index,0,items.count()-1);
		
		if (!items.get(index).isEmpty()) {
			return;
		}

		items.list.remove(index);
		items.get(index - 1).setEditing(true);
		for (int i = index; i < items.count(); i++) {
			items.get(i).formUp();
		}
		items.appendTotalHeight(-items.getTextSize());
	}

	private void scrollsPositionUpdate() {
		if (scrollV != null && scrollH != null) {
			scrollV.setPosition(x + w - scrollsWeight, y);
			scrollH.setPosition(x, y + h - scrollsWeight);
		}
	}

	private void scrollsSizeUpdate() {
		if (scrollV != null && scrollH != null) {
			scrollV.setSize(scrollsWeight, h - scrollsWeight);
			scrollH.setSize(w, scrollsWeight);
		}
	}

	private void scrollsTransformsUpdate() {
		scrollsPositionUpdate();
		scrollsSizeUpdate();
	}

	private void scrollsValuesUpdate() {
		if (scrollV == null || scrollH == null || items == null) {
			return;
		}

		updateValueForScrollH();
		updateValueForScrollV();
	}

	private void updateValueForScrollH() {
		if (!items.isEmpty()) {
			scrollH.getValue().setMax(getCurrentItem().getTextWidth());
		} else {
			scrollH.getValue().set(scrollH.getValue().getMin());
		}
	}

	private void updateValueForScrollV() {
		if (items.getTotalHeight() > EditText.this.getHeight()) {
			scrollV.getValue().setMin(h - items.getTotalHeight());
		} else {
			scrollV.getValue().setMin(0);
		}
	}

	private void ctrlV() {
		if (isInClipboardSeveralLines()) {
			insertLines(Clipboard.getAsArray());
		} else {
			insertLine(Clipboard.get());
		}

		scrollsValuesUpdate();
	}

	private void ctrlX() {

		Clipboard.set(selection.getText());

		items.deleteAllSelectedText();

		deleteItemsFromSelectedArea();

		selection.unselect();
		selection.setSelectingState(false);

	}

	private boolean isInClipboardSeveralLines() {
		return Clipboard.get().contains(String.valueOf('\n'));
	}

	private void insertLine(String line) {
		getCurrentItem().insert(line);
		for (int i = 0; i < line.length(); i++) {
			cursor.next();
		}
	}

	private void insertLines(String[] lines) {
		for (int i = 0; i < lines.length; i++) {
			if (i == 0) {
				getCurrentItem().insert(lines[i]);
			} else {

				if (getCurrentItem().isEmpty()) {
					getCurrentItem().insert(lines[i]);
				} else {
					items.insert(lines[i]);
				}

				cursor.goInEnd();
				if (cursor.getPosY() > EditText.this.getHeight() * .9f) {
					scrollV.getValue().append(-items.getTextSize());
				}
			}
		}
	}

	private void keyEnter() {
		items.insert(cursor.getTextOfRightSide());

		scrollH.getValue().set(scrollH.getValue().getMin());
		cursor.goInStart();
	}

	private void keyPageUp() {
		getCurrentItem().setEditing(false);
		items.getFirst().setEditing(true);

		cursor.goInStart();

		scrollH.getValue().set(scrollH.getValue().getMin());
		scrollV.getValue().set(0);
	}

	private void keyPageDown() {
		getCurrentItem().setEditing(false);
		items.getLast().setEditing(true);

		cursor.goInEnd();

		scrollsValuesUpdate();

		scrollH.getValue().set(scrollH.getValue().getMin());
		scrollV.getValue().set(scrollV.getValue().getMin());
	}

	private void keyHome() {
		cursor.setCurrentColumn(0);
		scrollH.getValue().set(scrollH.getValue().getMin());
	}

	private void keyEnd() {
		cursor.setCurrentColumn(cursor.getMaxCharsInRow());

		if (getCurrentItem().getTextWidth() > EditText.this.getWidth()) {
			scrollH.getValue().set(scrollH.getValue().getMax() * .5f);
		}
	}

	private void keyTab() {
		getCurrentItem().insert("  ");
		cursor.next();
		cursor.next();
	}

	private void clearAllSelectedText() {
		selection.unselect();
		items.clear();
		while (items.getTotalHeight() < EditText.this.getHeight()) {
			items.add("");
		}
		cursor.setCurrentRow(0);
		cursor.setCurrentColumn(0);
		scrollV.getValue().set(0);
	}

	private void clearSingleSelectedTextLine() {
		if (!selection.isMultiLinesSelected()) {
			if (selection.isSelectedToRight()) {
				for (int i = 0; i < getCurrentItem().getSelectedText().length(); i++) {
					cursor.back();
				}
			}
		}

		items.deleteAllSelectedText();
		deleteItemsFromSelectedArea();
		selection.unselect();
	}

	private void clearAndJoinLines() {
		final String textFromRightSideToCursor = cursor.getTextOfRightSide();
		cursor.setCurrentRow(cursor.getCurrentRow() - 1);
		cursor.goInEnd();
		getCurrentItem().append(textFromRightSideToCursor);
		removeEmptyItem(cursor.getCurrentRow() + 1);
		scrollsValuesUpdate();
	}

	private void clearEmptyLine() {
		if (cursor.getCurrentRow() == 0) {
			return;
		}

		removeEmptyItem(cursor.getCurrentRow());
		cursor.goInEnd();
		scrollV.getValue().append(items.getFirst().getItemHeight());
		scrollsValuesUpdate();
	}

	private void keyBackspace() {
		if (selection.isSelectedAllText()) {
			clearAllSelectedText();
			return;
		}

		if (selection.isSelecting()) {
			clearSingleSelectedTextLine();
			return;
		}

		if (!getCurrentItem().isEmpty() && cursor.getCurrentColumn() == 0 && cursor.getCurrentRow() != 0) {
			clearAndJoinLines();
			return;
		}

		if (getCurrentItem().isEmpty()) {
			clearEmptyLine();
			return;
		}

		getCurrentItem().deleteChar();
		cursor.back();
	}

	private boolean isAllowedChar(char ch) {
		return ALLOWED_CHARS.contains(String.valueOf(ch)) || Character.isDigit(ch) || Character.isAlphabetic(ch);
	}

	private void deleteItemsFromSelectedArea() {
		for (int i = selection.getLastRow(); i > selection.getFirstRow(); i--) {
			removeEmptyItem(i);
		}
	}

	private void checkDimensions() {
		if (graphics.width != (int) w || graphics.height != (int) h) {
			createGraphics();
		}
	}

	private final class Items {
		private final Color color, selectColor;
		private final List<Item> list;
		private float totalHeight;
		private int textSize;

		private Items() {
			color = new Color(0);
			selectColor = new Color(0, 0, 255, 64);
			textSize = 32;
			list = new ArrayList<Item>();
			for (int i = 0; i < getHeight() / textSize; i++) {
				list.add(new Item(i * textSize, ""));
				totalHeight += textSize;
			}

			scrollV.getValue().setMinMax(h - totalHeight, 0);
			scrollV.getValue().set(0);
		}

		private void draw(final PGraphics pg) {
			color.use(pg);
			if (font != null) {
				pg.textFont(font);
			}
			pg.textSize(textSize);
			pg.textAlign(CORNER, CENTER);

			for (int i = list.size() - 1; i >= 0; i--) {
				Item item = list.get(i);
				if (itemInside(item)) {
					item.draw(pg);
					if (item.isEditing()) {
						cursor.setCurrentRow(i);
					}
				} else {
					item.setEditing(false);
				}
			}
		}

		private boolean isEmpty() {
			return list.isEmpty();
		}

		private boolean itemInside(Item item) {
			return item.getItemY() > getY() - item.getItemHeight() && item.getItemY() < getY() + getHeight();
		}

		private void deleteAllSelectedText() {
			for (Item item : list) {
				item.removeSelectedText();
			}
		}

		private void selectAllText() {
			for (Item item : list) {
				item.setFullSelect(true);
			}
		}

		private int count() {
			return list.size();
		}

		private void setTextSize(float textSize) {
			if (textSize <= 2) {
				return;
			}
			this.textSize = (int) textSize;
			totalHeight = 0;
			final float tmpScrollVerticalValue = scrollV.getValue().get();

			for (int i = 0; i < list.size(); i++) {
				Item item = list.get(i);
				item.setShiftY(i * textSize);
				totalHeight += textSize;
			}

			scrollsValuesUpdate();
			scrollV.getValue().set(tmpScrollVerticalValue + textSize);
		}

		private int getTextSize() {
			return textSize;
		}

		private float getTotalHeight() {
			return totalHeight;
		}

		private void appendTotalHeight(int value) {
			totalHeight += value;
		}

		private void add(String text) {
			list.add(new Item(list.size() * textSize, text));
			totalHeight += textSize;
		}

		private void clear() {
			list.clear();
			totalHeight = 0;
			scrollsValuesUpdate();
		}

		private float getMaxTextWidthFromItems() {
			float maxWidth = 0;
			for (int i = 0; i < list.size(); i++) {
				if (maxWidth < list.get(i).getTextWidth()) {
					maxWidth = list.get(i).getTextWidth();
				}
			}
			return maxWidth;
		}

		private void goUpToEditing() {
			int id = 0;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).isEditing()) {
					if (i == 0) {
						return;
					}
					id = i - 1;
					list.get(i).setEditing(false);
				}
			}

			list.get(id).setEditing(true);

			if (totalHeight > h && list.get(id).getInsideY() < h * .1f) {
				scrollV.getValue().append(textSize);
			}
		}

		private void goDownToEditing() {
			int id = 0;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).isEditing()) {
					if (i == list.size() - 1) {
						return;
					}
					id = i + 1;
					list.get(i).setEditing(false);
				}
			}

			list.get(id).setEditing(true);

			if (totalHeight > h && list.get(id).getInsideY() > h * .9f) {
				scrollV.getValue().append(-textSize);
			}
		}

		private void insert(String partOfText) {
			int id = 0;

			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).isEditing()) {
					id = i;
				}
			}

			list.add(id + 1, new Item(list.get(id).getShiftY(), partOfText));

			for (int i = id + 1; i < list.size(); i++) {
				list.get(i).formDown();
			}

			list.get(id).setEditing(false);
			list.get(id + 1).setEditing(true);

			totalHeight += textSize;

			if (items.getTotalHeight() > h) {
				float tempValueOfScrollV = scrollV.getValue().get();
				scrollV.getValue().setMinMax(h - totalHeight, 0);
				scrollV.getValue().set(tempValueOfScrollV);
			}

			if (cursor.getPosY() > getHeight() * .8f) {
				scrollV.getValue().append(-textSize);
			}
		}

		private Item get(int index) {
			return list.get(constrain(index, 0, list.size()-1));
		}

		private Item getFirst() {
			return list.get(0);
		}

		private Item getLast() {
			return list.get(list.size() - 1);
		}

		private final class Item {
			private final StringBuilder sb;
			private final Event event;
			private float shiftY, textWidth;
			private int firstSelectedColumn, lastSelectedColumn;
			private boolean isEditing, isFullSelected, isPartSelected;
			
			private Item(final float shiftY, String text) {
				sb = new StringBuilder(text);
				this.shiftY = shiftY;
				event = new Event();

			}

			private void draw(final PGraphics pg) {
				event.listen(EditText.this.getX(),getItemY(),EditText.this.getWidth(),getItemHeight());
				
				if (!Objects.isNull(pg)) {
					textOnDraw(pg);
					selectedTextAreaOnDraw(pg);
				}
				
				if (isCursorDraggable()) {
					isEditing = true;
					cursorStateUpdate();
				}

				if (app.mousePressed && event.outside()) {
					isEditing = false;
				}
				
			}
			
			private boolean isCursorDraggable() {
				return event.pressed() && !scrollH.getEvent().holding() && !scrollV.getEvent().holding();
			}
			
			private void cursorStateUpdate() {
				final float localMouseX = app.mouseX - x + scrollH.getValue().get();

				for (int i = 0; i < sb.length(); i++) {
					if (localMouseX > getTextWidth(cursor.getCurrentColumn())
							+ getCharWidth(cursor.getCurrentColumn()) / 2) {
						cursor.justNext();
					} else {
						cursor.justBack();
					}
				}

				if (localMouseX > getTextWidth(cursor.getCurrentColumn())
						+ getCharWidth(cursor.getCurrentColumn()) / 2) {
					cursor.justNext();
				}

				cursor.resetTimer();

				scrollsValuesUpdate();
			}
			
			private void textOnDraw(PGraphics pg) {
				pg.text(sb.toString(), -scrollH.getValue().get(), getInsideY() + textSize / 2);
			}
			
			private void selectedTextAreaOnDraw(PGraphics pg) {
				if (!isSelecting()) { return; }
				
				textWidth = pg.textWidth(sb.toString());
				
				pg.pushStyle();
				pg.noStroke();
				pg.fill(selectColor.get());
				if (isFullSelected) {
					pg.rect(-scrollH.getValue().get(), getInsideY(), getTextWidth(), textSize);
				}
				if (isPartSelected) {
					pg.rect(-scrollH.getValue().get() + getTextWidth(min(firstSelectedColumn, lastSelectedColumn)),
							getInsideY(), getTextWidth(firstSelectedColumn, lastSelectedColumn), textSize);
				}
				pg.popStyle();
				
			}

			private void setFullSelect(boolean isFullSelected) {
				this.isFullSelected = isFullSelected;

				if (isFullSelected) {
					firstSelectedColumn = 0;
					lastSelectedColumn = sb.length();
				}

				isPartSelected = false;
			}

			private void unselect() {
				isFullSelected = isPartSelected = false;
				firstSelectedColumn = lastSelectedColumn = 0;
			}

			private void setPartSelected(int firstRow, int lastRow) {
				isPartSelected = true;
				isFullSelected = false;
				firstSelectedColumn = firstRow;
				lastSelectedColumn = lastRow;
			}

			private void setRightSideSelected() {
				isPartSelected = true;
				isFullSelected = false;
				lastSelectedColumn = sb.length();
			}

			private void setRightSideSelected(int index) {
				isPartSelected = true;
				isFullSelected = false;
				firstSelectedColumn = index;
				lastSelectedColumn = sb.length();

			}

			private void setLeftSideSelected(int endRow) {
				isPartSelected = true;
				isFullSelected = false;
				firstSelectedColumn = 0;
				this.lastSelectedColumn = endRow;
			}

			private String getSelectedText() {
				return sb.toString().substring(min(firstSelectedColumn, lastSelectedColumn),
						max(firstSelectedColumn, lastSelectedColumn));
			}

			private void removeSelectedText() {
				if (sb.length() > 0) {
					sb.delete(min(firstSelectedColumn, lastSelectedColumn), max(firstSelectedColumn, lastSelectedColumn));
				}
				unselect();
			}

			private String getText() {
				return sb.toString();
			}

			private StringBuilder getStringBuilder() {
				return sb;
			}

			private void setEditing(boolean e) {
				isEditing = e;
			}

			public boolean isEditing() {
				return isEditing;
			}

			private float getItemY() {
				return shiftY + scrollV.getValue().get() + y;
			}

			private float getInsideY() {
				return shiftY + scrollV.getValue().get();
			}

			private float getItemHeight() {
				return textSize;
			}

			private void setShiftY(float shiftY) {
				this.shiftY = shiftY;
			}

			private float getShiftY() {
				return shiftY;
			}

			private void formDown() {
				shiftY += textSize;
			}

			private void formUp() {
				shiftY -= textSize;
			}

			private float getTextWidth() {
				return textWidth;
			}

			private float getTextWidth(int indexEnd) {
				return getTextWidth(0, indexEnd);
			}

			private float getTextWidth(int indexStart, int indexEnd) {
				float width = 0;

				if (indexStart < 0 || indexStart > sb.length()) {
					return width;
				}
				if (indexEnd < 0 || indexEnd > sb.length()) {
					return width;
				}

				width = graphics.textWidth(sb.toString().substring(min(indexStart, indexEnd), max(indexStart, indexEnd)));

				return width;
			}

			private float getCharWidth(int indexEnd) {
				float width = 0;

				if (indexEnd > sb.length()) {
					return width;
				}

				width = graphics.textWidth(sb.toString().substring(max(0, indexEnd - 1), indexEnd));

				return width;
			}

			private int getCharsCount() {
				return sb.length();
			}

			private void deleteChar() {
				if (sb.toString().isEmpty()) {
					return;
				}
				if (cursor.getCurrentColumn() - 1 >= 0) {
					sb.delete(cursor.getCurrentColumn() - 1, cursor.getCurrentColumn());
				}
			}

			private boolean isEmpty() {
				return sb.toString().isEmpty();
			}

			private void insert(String txt) {
				if (isEmpty()) {
					sb.append(txt);
				} else {
					sb.insert(cursor.getCurrentColumn(), txt);
				}

			}

			private void append(String txt) {
				sb.append(txt);
			}
			
			private boolean isSelecting() {
				return firstSelectedColumn != lastSelectedColumn;
			}
		}

	}

	private final class Cursor {
		private static final int MAX_DURATION = 60;
		private final Color color;
		private float posX, posY;
		private int column, row, duration;

		private Cursor() {
			color = new Color(0);
		}

		private void draw(final PGraphics pg) {
			if (!isFocused) {
				return;
			}

			posX = -scrollH.getValue().get()
					+ pg.textWidth(items.list.get(getCurrentRow()).sb.toString().substring(0, getCurrentColumn()));
			posY = items.list.get(getCurrentRow()).getInsideY();

			if (duration < MAX_DURATION) {
				duration++;
			} else {
				duration = 0;
			}

			if (duration < MAX_DURATION / 2) {
				float dynamicHeight = abs(
						map(duration, 0, MAX_DURATION, -items.getTextSize() / 2, items.getTextSize() / 2)) / 2;
				pg.pushStyle();
				pg.stroke(color.get());
				pg.strokeWeight(2);
				pg.line(posX, posY + dynamicHeight, posX, posY + items.getTextSize() - dynamicHeight);
				pg.popStyle();
			}

		}

		private float getPosX() {
			return posX;
		}

		private float getPosY() {
			return posY;
		}

		private int getCurrentColumn() {
			return constrain(column, 0, getMaxCharsInRow());
		}

		private void setCurrentColumn(int column) {
			this.column = column;
		}

		private int getCurrentRow() {
			return constrain(row,0,items.count()-1);
		}

		private void setCurrentRow(int row) {
			this.row = constrain(row,0,items.count()-1);
		}

		private int getRows() {
			return items.list.size();
		}

		private void back() {
			if (getCurrentColumn() > 0) {
				column--;
			}
			if (posX < getWidth() / 2) {
				scrollH.getValue().append(-items.getTextSize() / 2);
			}
		}

		private void next() {
			if (getCurrentColumn() < getMaxCharsInRow()) {
				column++;
			}
			if (posX > getWidth() * .8f) {
				scrollH.getValue().append(items.getTextSize());
			}
		}

		private void justNext() {
			if (getCurrentColumn() < getMaxCharsInRow()) {
				column++;
			}
		}

		private void justBack() {
			if (getCurrentColumn() > 0) {
				column--;
			}
		}

		private void goInStart() {
			column = 0;
		}

		private void goInEnd() {
			column = getMaxCharsInRow();
		}

		private void resetTimer() {
			duration = 0;
		}

		private int getMaxCharsInRow() {
			return getCurrentItem().getCharsCount();
		}

		private String getTextOfRightSide() {
			final Items.Item item = getCurrentItem();
			String txt = item.getText().substring(getCurrentColumn(), item.getText().length());
			item.getStringBuilder().delete(getCurrentColumn(), item.getText().length());

			return txt;
		}

	}

	private final class Selection {
		private int startColumn, endColumn, startRow, endRow;
		private boolean isSelecting, selectingWasStoped, isSelectedAllText;

		private Selection() {
			startColumn = endColumn = startRow = endRow = -1;
		}

		private void update() {

			if (isAllowedStateToStartSelecting()) {

				if (selectingWasStoped) {
					unselect();
					selectingWasStoped = false;
				}

				if (startRow == -1) {
					startRow = cursor.getCurrentRow();
				}
				endRow = cursor.getCurrentRow();
				if (startColumn == -1) {
					startColumn = cursor.getCurrentColumn();
				}
				endColumn = cursor.getCurrentColumn();

				if (items.getTotalHeight() > EditText.this.getHeight()) {

					if (cursor.getPosY() < getHeight() * .2f) {
						scrollV.getValue().append(getHeight() * .02f);
					}

					if (cursor.getPosY() > getHeight() * .8f) {
						scrollV.getValue().append(-getHeight() * .02f);
					}
				}

				if (cursor.getPosX() < getWidth() * .2f) {
					scrollH.getValue().append(-getWidth() * .02f);
				}

				if (cursor.getPosX() > getWidth() * .8f) {
					scrollH.getValue().append(getWidth() * .02f);
				}

			} else {
				if (!selectingWasStoped) {
					selectingWasStoped = true;
				}
			}

			isSelecting = (startRow != endRow) || (startColumn != endColumn);

			if (isSelecting && !scrollV.getThumb().getEvent().holding() && !scrollH.getThumb().getEvent().holding()) {

				for (int i = 0; i < items.count(); i++) {

					if (i == startRow) {
						if (isMultiLinesSelected()) {
							if (isDown()) {
								items.get(startRow).setRightSideSelected();
							}

							if (isUp()) {
								items.get(endRow).setRightSideSelected(endColumn);
							}

						} else {
							items.get(i).setPartSelected(startColumn, endColumn);
						}
					}

					if (i == endRow && isMultiLinesSelected()) {
						if (isDown()) {
							items.get(i).setLeftSideSelected(endColumn);
						}
						if (isUp()) {
							items.get(startRow).setLeftSideSelected(startColumn);
						}
					}

					if (i > min(startRow, endRow) && i < max(startRow, endRow)) {
						items.get(i).setFullSelect(true);
					} else {
						if (i < min(startRow, endRow) || i > max(startRow, endRow)) {
							items.get(i).setFullSelect(false);
						}
					}

				}

			}

		}

		private String getText() {
			final StringBuilder sb = new StringBuilder();

			for (Items.Item item : items.list) {
				if (!item.getSelectedText().isEmpty()) {
					sb.append(isMultiLinesSelected() || getCurrentItem().isFullSelected ? item.getSelectedText() + '\n'
							: item.getSelectedText());
				}
			}

			return sb.toString();
		}

		private boolean isAllowedStateToStartSelecting() {
			return event.holding() && !scrollV.getThumb().getEvent().holding()
					&& !scrollH.getThumb().getEvent().holding() && event.dragged();
		}

		private boolean isSelecting() {
			return isSelecting;
		}

		private void unselect() {
			startRow = endRow = startColumn = endColumn = -1;

			items.list.forEach(item -> item.unselect());

			isSelecting = false;
			selectingWasStoped = true;
			setSelectedAllText(false);
		}

		private boolean isSelectedAllText() {
			return isSelectedAllText;
		}

		private int getFirstRow() {
			return min(startRow, endRow);
		}

		private int getLastRow() {
			return max(startRow, endRow);
		}

		private void setSelectedAllText(boolean isSelectedAllText) {
			this.isSelectedAllText = isSelectedAllText;
			if (isSelectedAllText) {
				isSelecting = true;
			}
		}

		private boolean isMultiLinesSelected() {
			return startRow != endRow;
		}

		private boolean isUp() {
			return endRow < startRow;
		}

		private boolean isDown() {
			return endRow > startRow;
		}

		private void setSelectingState(boolean isSelecting) {
			this.isSelecting = isSelecting;
		}

		private boolean isSelectedToRight() {
			return startColumn < endColumn;
		}

	}

}