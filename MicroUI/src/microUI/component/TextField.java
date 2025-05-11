package microUI.component;

import static java.awt.event.KeyEvent.VK_END;
import static java.awt.event.KeyEvent.VK_HOME;
import static processing.core.PApplet.map;
import static processing.core.PApplet.min;
import static processing.core.PApplet.max;
import static processing.core.PConstants.BACKSPACE;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.CORNER;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;

import microUI.core.Component;
import microUI.event.KeyPressable;
import microUI.util.Color;
import microUI.util.TextController;
import microUI.util.Value;
import processing.core.PFont;
import processing.core.PGraphics;


public final class TextField extends Component implements KeyPressable {
	public final Text text;
	public final Cursor cursor;
	private PGraphics pg;
	private final Value scroll;
	private final int LEFT_OFFSET = 10;
	private boolean focused;
	
	public TextField(float x, float y, float w, float h) {
		super(x, y, w, h);
		fill.set(255);
		visible();
		
		text = new Text();
		cursor = new Cursor();
		
		pg = app.createGraphics((int) w, (int) h, app.sketchRenderer());
		scroll = new Value(0,0,0);
		
	}
	
	public TextField() {
		this(app.width*.25f,app.height*.45f,app.width*.5f,app.height*.1f);
	}


	@Override
	public final void update() {
		event.listen(this);
		
		// logs();
		
		checkDimensions();
		
		if(event.pressed()) { cursor.blink.reset(); }
		
		app.pushStyle();
			app.fill(fill.get());
			app.rect(x,y,w,h);
			
			pg.beginDraw();
				pg.clear();
				text.draw(pg);
				if(focused) { cursor.draw(pg); }
			pg.endDraw();
			
			app.image(pg, x, y, w, h);
		app.popStyle();
		
		
		if(event.clicked()) { focused = true; }
		if(app.mousePressed && event.outside()) { focused = false; }
		
		if(event.pressed()) {
			updateScrollMax();
			cursor.row.set((int) map(app.mouseX-getX(),text.getX(),text.getX()+text.getWidth(),0,text.length()));
			if(app.mouseX-getX() < getW()*.1f) { scroll.append(-cursor.row.getCurrentCharWidth()); }
			if(app.mouseX-getX() > getW()*.9f) { scroll.append(cursor.row.getCurrentCharWidth()); }
			
		}
		
		
	}
	
	private final void logs() {
		// System.out.println("cursor row = "+cursor.row.get());
		// System.out.println("text length = "+text.length());
		// System.out.println(cursor.positionX);
	}
	
	private final void updateScrollMax() {
		if(text.isEmpty()) { scroll.setMax(0); return; }
		
		scroll.setMax((text.getWidth()-getW()*.8f));
	}
	
	@Override
	protected void inTransforms() {
		super.inTransforms();
		
		if(text != null) { text.updatePosition(); }
		
		if(scroll != null) { updateScrollMax(); }
		
		if(cursor != null) { cursor.updateTransforms(); }
		
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
		
		
		switch(app.keyCode) {
		case LEFT :
			cursor.row.back();
			if(cursor.isInStart()) { return; }
			scroll.append(-cursor.row.getBackCharWidth());
		break;
		
		case RIGHT :
			cursor.row.next();
			if(cursor.isInEnd()) { return; }
			scroll.append(cursor.row.getNextCharWidth());
		break;
		
		case BACKSPACE :
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
			text.insert(cursor.row.get(), app.key);
			scroll.append(cursor.row.getNextCharWidth());
		break;
		
		} 

		updateScrollMax();
		cursor.row.updatePositionX();
	}
	
	private final void checkDimensions() {
		if(app.mousePressed) { return; }
		if(pg == null) { return; }
		
		if((int) (getW()) != pg.width || (int) (getH()) != pg.height) {
			pg = app.createGraphics((int) max(1,getW()), (int) max(1,getH()), app.sketchRenderer());
			System.out.println("PGraphics object was created");
		}
		
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
		}

		private final float getX() { return x; }
		
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
		public final Row row;
		public final Blink blink;
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
			fill.use(pg);
			weight.use(pg);
			pg.line(positionX+LEFT_OFFSET, positionY, positionX+LEFT_OFFSET, height);
			}

		}
		
		private final void updateTransforms() {
			positionY = TextField.this.getH()*.1f;
			height = TextField.this.getH()*.9f;
		}
		
		private final boolean isInStart() { return row.get() == 0; }
		
		private final boolean isInEnd() { return row.get() == text.length(); }
		
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
				if(rate < 0 || rate > maxDuration/2) { return; }
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