package microUI.component;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_END;
import static java.awt.event.KeyEvent.VK_HOME;
import static java.awt.event.KeyEvent.VK_V;
import static java.awt.event.KeyEvent.VK_X;
import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;
import static processing.core.PApplet.max;
import static processing.core.PApplet.min;
import static processing.core.PConstants.BACKSPACE;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.CONTROL;
import static processing.core.PConstants.CORNER;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;

import microUI.core.Component;
import microUI.core.TextController;
import microUI.event.Event;
import microUI.event.KeyPressable;
import microUI.util.Clipboard;
import microUI.util.Color;
import microUI.util.Value;
import processing.core.PFont;
import processing.core.PGraphics;

// TASKS:
/*
 1. TextField it's simple line text input component and nothing else; DONE
 2. Hint is working - DONE
 3. Can moving - DONE
 4. Can resize - DONE
 5. Can change focus state outside without real actions for it - DONE
 6. Can set ANY type of fonts for using inside the TextField - DONE
 7. Can set style from other text field - DONE
 8. Can change color of background - DONE
 9. Can change color of text - DONE
 10. Can change color of cursor - DONE
 11. Can change color of selection - DONE
 12. Can Use HotKeys like CTRL + V,C,X,A and HOME with END keys - DONE
 */

public final class TextField extends Component implements KeyPressable {
	private static final int LEFT_OFFSET = 10;
	
	public final Text text;
	public final Cursor cursor;
	public final Selection selection;
	
	private final Value scroll;
	private PGraphics pg;
	
	private boolean focused,componentSizeChanged;
	
	
	public TextField(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		fill.set(255);
		visible();
		
		text = new Text();
		cursor = new Cursor();
		selection = new Selection();
		
		scroll = new Value(0);
		
		createPGraphics();
	}
	
	public TextField() {
		this(app.width*.25f,app.height*.45f,app.width*.5f,app.height*.1f);
	}

	@Override
	public final void update() {
		event.listen(this);
		
		checkDimensions();
		
		app.pushStyle();
			fill.use();
			app.rect(x,y,w,h);
			
			pg.beginDraw();
				pg.clear();
				text.draw(pg);
				if(focused) {
					cursor.draw(pg);
					selection.draw(pg);
				}
			pg.endDraw();
			
			app.image(pg, x, y, w, h);
			
			if(!focused) {
				app.fill(0,32);
				app.rect(x, y, w, h);
			}
		app.popStyle();
		
		events();
	}
	
	private final void events() {
		
		if(event.pressed()) {
			if(!focused) { focused = true; }
			cursor.blink.reset();
		}
		
		if(mustNotHaveFocus()) {
			if(focused) { focused = false; }
			selection.reset();
		}
		
		if(event.holding()) {
			if(text.isEmpty()) { return; }
			
			cursor.row.set((int) map(app.mouseX-getX(),text.getX(),text.getX()+text.getWidth(),0,text.length()));
			
			if(app.frameCount%3 == 0) {
				if(cursor.isCloseToLeftSide()) { scroll.append(-cursor.row.getCurrentCharWidth()); }
				if(cursor.isCloseToRightSide()) { scroll.append(cursor.row.getCurrentCharWidth()); }
			}
			
			if(!selection.isStarted()) {
				selection.setStartRow(cursor.row.get());
				selection.setStarted(true);
			} else {
				selection.setEndRow(cursor.row.get());
			}
			
			updateScrollMax();
			cursor.row.updatePositionX();
			
		} else {
			selection.setStarted(false);
		}
		
		if(event.clicked(2)) {
			if(selection.isSelected()) {
				selection.reset();
			} else {
				selection.selectAll();
			}
		}
	}
	
	private final boolean mustNotHaveFocus() {
		return app.mousePressed && event.outside() && !event.holding();
	}
	
	private final void updateScrollMax() {
		if(text.isEmpty() || text.getWidth() < getW()*.8f) {
			scroll.setMax(0);
			return;
		}
		
		scroll.setMax((text.getWidth()-getW()*.8f));
	}
	
	private final void checkDimensions() {
		if(app.mousePressed) { return; }
		if(componentSizeChanged) { createPGraphics(); }
	}
	
	private final void createPGraphics() {
		pg = app.createGraphics((int) max(1,getW()), (int) max(1,getH()), app.sketchRenderer());
		System.out.println("PGraphics object was created");
		componentSizeChanged = false;
	}

	
	@Override
	protected void inTransforms() {
		super.inTransforms();
		
		if(text != null) { text.updatePosition(); }
		
		if(scroll != null) { updateScrollMax(); }
		
		if(cursor != null) { cursor.updateTransforms(); }
		
		if(selection != null) { selection.updateTransforms(); }
		
		componentSizeChanged = true;
	}
	
	public final boolean isFocused() {
		return focused;
	}

	public final void setFocused(boolean focused) {
		this.focused = focused;
	}

	@Override
	public final void keyPressed() {
		if(!focused) { return; }
		
		cursor.blink.reset();
		
		if(Event.checkKey(CONTROL)) {
			if(Event.checkKey(VK_C)) { Clipboard.set(selection.getText()); }
			if(Event.checkKey(VK_V)) {
				if(Clipboard.isEmpty()) { return; }
				
				for(char ch : Clipboard.get().toCharArray()) {
					text.insert(cursor.row.get(), ch);
					updateScrollMax();
					cursor.row.updatePositionX();
				}
			}
			
			if(Event.checkKey(VK_X)) {
				if(selection.isSelectedAll()) {
				Clipboard.set(selection.getText());
				text.clear();
				scroll.setMax(0);
				cursor.row.set(0);
				selection.reset();
				}
				
				if(selection.isSelected()) {
					text.deleteSelectedArea();
					selection.reset();
				}
			}
			
			if(Event.checkKey(VK_A)) { selection.selectAll(); }
			return;
		}
		
		switch(app.keyCode) {
		case LEFT :
			cursor.row.back();
			if(cursor.isInStart()) { return; }
			if(cursor.isCloseToLeftSide()) {
				scroll.append(-cursor.row.getBackCharWidth());
			}
			
		break;
		
		case RIGHT :
			cursor.row.next();
			if(cursor.isInEnd()) { return; }
			if(cursor.isCloseToRightSide()) {
				scroll.append(cursor.row.getNextCharWidth());
			}
		break;
		
		case BACKSPACE :
			if(selection.isSelectedAll()) {
				text.clear();
				scroll.setMax(0);
				cursor.row.set(0);
				selection.reset();
				break;
			}
			if(selection.isSelected()) {
				text.deleteSelectedArea();
				selection.reset();
				break;
			}
			
			if(cursor.isInStart()) { break; }
			text.removeCharAt(cursor.row.get()-1);
			scroll.append(-cursor.row.getNextCharWidth());
			cursor.row.back();
			
		break;
		case VK_HOME :
			cursor.row.goToStart();
			scroll.set(scroll.getMin());
		break;
		case VK_END :
			cursor.row.goToEnd();
			scroll.set(scroll.getMax());
		break;
		
		default :

			if(selection.isSelectedAll()) {
				text.clear();
				scroll.setMax(0);
				cursor.row.set(0);
				selection.reset();
			}
			
			text.insert(cursor.row.get(), app.key);
			
		break;
		
		} 

		updateScrollMax();
		cursor.row.updatePositionX();
	}
	
	public void setStyle(final TextField otherTextField) {
		super.setStyle(otherTextField);
		text.fill.set(otherTextField.text.fill);
		cursor.fill.set(otherTextField.cursor.fill);
		selection.fill.set(otherTextField.selection.fill);
		
		text.font.set(otherTextField.text.font.get());
		text.size.set(otherTextField.text.size.get());
		
	}

	public final class Text extends TextController {
		public final Color fill;
		public final Size size;
		public final Font font;
		private float x,y;
		private String hint;
		
		private Text() {
			super();
			fill = new Color(0);
			size = new Size();
			font = new Font();
			
			updatePosition();
			size.set(h/2);
		}
		
		private final void draw(final PGraphics pg) {
			pg.pushStyle();
			
			fill.use(pg);
			font.use(pg);
			size.use(pg);
			pg.textAlign(CORNER,CENTER);
			if(isEmpty() && hint != null) {
			pg.text(hint,x,y);
			} else {
			pg.text(get(),x,y);
			}
			pg.popStyle();
			
			updatePositionX();
		}
		
		private final void updatePosition() {
			updatePositionX();
			y = TextField.this.getH()*.5f;
		}
		
		private final void updatePositionX() {
			if(scroll == null) { x = LEFT_OFFSET; } else { x = LEFT_OFFSET-scroll.get();}
		}
		
		
		public final String getHint() {
			return hint;
		}

		public final void setHint(String hint) {
			this.hint = hint;
		}
		
		@Override
		protected void inInserting() {
			cursor.row.next();
			updatePositionX();
			scroll.append(cursor.row.getBackCharWidth());
		}

		private final float getX() { return x; }
		
		private final void deleteSelectedArea() {
			for(int i = selection.getStartRow(); i < selection.getEndRow(); i++) {
				scroll.append(-cursor.row.getNextCharWidth());
				if(selection.getRealStartRow() < selection.getRealEndRow()) {
					cursor.row.back();
				}
			}
			
			sb.delete(selection.getStartRow(), selection.getEndRow());
		}
		
		private final float getWidth() {
			return pg.textWidth(get());
		}

		public final class Size extends AbstractSize {

			private Size() {}
			
			@Override
			public final void set(final float size) {
				if(size < 1 || size > TextField.this.getH()) { return; }
				this.size = size;
			}

			@Override
			protected final void use(PGraphics pg) {
				if(pg == null) { return; }
				pg.textSize(size);
			}
		}
		
		public final class Font {
			private PFont font;
			
			private Font() {}
			
			public final void set(PFont font) {
				if(font == null) { return; }
				this.font = font;
			}
			
			public final PFont get() { return font; }
			
			private final void use(PGraphics pg) {
				if(pg == null || font == null) { return; }
				pg.textFont(font);
			}
		}
	}
	
	public final class Cursor {
		public final Color fill;
		public final Size weight;
		public final Blink blink;
		private final Row row;
		private float positionX,positionY,height;
		
		private Cursor() {
			fill = new Color(0);
			weight = new Size(2);
			row = new Row();
			blink = new Blink();
			updateTransforms();
		}
		
		private final void draw(PGraphics pg) {
			if(pg == null) { return; }
			
			blink.updateState();
			
			if(blink.isBlinking()) {
			pg.pushStyle();
			pg.stroke(fill.get());
			weight.use(pg);
			pg.line(positionX+LEFT_OFFSET, positionY, positionX+LEFT_OFFSET, height);
			pg.popStyle();
			}

		}
		
		private final void updateTransforms() {
			positionY = TextField.this.getH()*.1f;
			height = TextField.this.getH()*.9f;
		}
		
		private final boolean isInStart() { return row.get() == 0; }
		
		private final boolean isInEnd() { return row.get() == text.length(); }
		
		private final boolean isCloseToLeftSide() {
			return positionX < getW()*.1f;
		}
		
		private final boolean isCloseToRightSide() {
			return positionX > getW()*.9f;
		}
		
		public final class Blink {
			private int duration,maxDuration;
			private float rate;
			
			private Blink() {
				maxDuration = 60;
				rate = 1;
			}
			
			private final boolean isBlinking() {
				return duration < maxDuration/2;
			}
			
			private final void updateState() {
				if(duration < maxDuration) { duration += rate; } else { duration = 0; }
			}
			
			private final void reset() { duration = 0; }
			
			public final void setRate(final float rate) {
				if(rate <= 0 || rate >= maxDuration/2) { return; }
				this.rate = rate;
			}
		}
		
		public final class Row {
			private int row;
			
			private Row() {}
			
			private final void set(final int row) {
				if(row < 0 || row > text.length()) { return; }
				this.row = row;
				updatePositionX();
			}
			
			private final int get() { return row; }
			
			private final void goToStart() { set(0); }
			
			private final void goToEnd() { set(text.length()); }
			
			private final void back() {
				if(row > 0) {
					row--;
					updatePositionX();
				}
				
			}
			
			private final void next() {
				if(row < text.length()) {
					row++;
					updatePositionX();
				}
			}
			
			private final void updatePositionX() {
				if(text.isEmpty()) { positionX = 0; return; }
				
				positionX = pg.textWidth(text.get().substring(0,row))-scroll.get();
			}
			
			private final float getCurrentCharWidth() {
				if(text.isEmpty()) { return 0; }
				return pg.textWidth(text.get().charAt(min(row,text.length()-1)));
			}
			
			private final float getNextCharWidth() {
				if(text.isEmpty()) { return 0; }
				return pg.textWidth(text.get().charAt(min(row+1,text.length()-1)));
			}
			
			private final float getBackCharWidth() {
				if(text.isEmpty()) { return 0; }
				return pg.textWidth(text.get().charAt(max(0,min(row-1,text.length()-1))));
			}
		}
		
		public final class Size extends AbstractSize {

			private Size(final float size) {
				set(size);
			}
			
			@Override
			public void set(float size) {
				if(size < 1 || size > 10) { return; }
				this.size = size;
			}

			@Override
			protected void use(PGraphics pg) {
				if(pg == null) { return; }
				pg.strokeWeight(size);
			}
			
		}
	}
	
	public final class Selection {
		public final Color fill;
		private float x,y,w,h;
		private int startRow,endRow;
		private boolean isStarted;
		
		private Selection() {
			fill = new Color(0,164,255,64);
			y = getH()*.1f;
			h = getH()*.8f;
		}
		
		private final void draw(final PGraphics pg) {
			pg.pushStyle();
			pg.noStroke();
			fill.use(pg);
			pg.rect(x+LEFT_OFFSET-scroll.get(),y,startRow < endRow ? w : -w,h);
			pg.popStyle();
		}
		
		private final void updateTransforms() {
			
			
			y = getH()*.1f;
			h = getH()*.8f;
			
			if(pg == null) { return; }
			if(text.isEmpty()) { x = w = 0; return; }
			x = pg.textWidth(text.get().substring(0,getStartRow()));
			w = pg.textWidth(text.get().substring(getStartRow(),getEndRow()));
		}

		private final String getText() {
			if(text.isEmpty()) { return ""; }
			return text.get().substring(getStartRow(),getEndRow());
		}
		
		private final int getStartRow() {
			return min(startRow,endRow);
		}

		private final void setStartRow(int startRow) {
			startRow = constrain(startRow,0,text.length());
			if(pg == null) { return; }
			
			x = pg.textWidth(text.get().substring(0,startRow));
			
			this.startRow = startRow;
		}

		private final int getEndRow() {
			return max(startRow,endRow);
		}
		
		private final void setEndRow(int endRow) {
			endRow = constrain(endRow,0,text.length());
			if(pg == null) { return; }
			
			w = pg.textWidth(text.get().substring(getStartRow(),getEndRow()));

			this.endRow = endRow;
		}
		
		private final int getRealStartRow() { return startRow; }
		
		private final int getRealEndRow() { return endRow; }
		
		private final void reset() {
			startRow = endRow = 0;
			isStarted = false;
			
			updateTransforms();
		}
		
		private final boolean isSelected() {
			return startRow != endRow;
		}
		
		private final void selectAll() {
			startRow = 0;
			endRow = text.length();
			updateTransforms();
		}
		
		private final boolean isSelectedAll() {
			return getStartRow() == 0 && getEndRow() == text.length();
		}

		private final boolean isStarted() {
			return isStarted;
		}

		private final void setStarted(boolean isStarted) {
			this.isStarted = isStarted;
		}
		
	}
	
	private abstract class AbstractSize {
		protected float size;
		
		private AbstractSize() {}
		
		public abstract void set(final float size);
		
		public final float get() {
			return size;
		}
		
		protected abstract void use(PGraphics pg);
		
	}
}