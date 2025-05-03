package microUI.component;

import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.CORNER;

import microUI.core.Component;
import microUI.event.KeyPressable;
import microUI.util.Color;
import microUI.util.TextController;
import processing.core.PFont;
import processing.core.PGraphics;

public final class TextField extends Component implements KeyPressable {
	public final Text text;
	public final Cursor cursor;
	private PGraphics pg;
	private final int LEFT_OFFSET = 10;
	
	public TextField(float x, float y, float w, float h) {
		super(x, y, w, h);
		fill.set(255);
		visible();
		
		text = new Text();
		cursor = new Cursor();
		
		pg = app.createGraphics((int) w, (int) h, app.sketchRenderer());
		
	}
	
	public TextField() {
		this(app.width*.25f,app.height*.45f,app.width*.5f,app.height*.1f);
	}


	@Override
	public final void update() {
		event.listen(this);
		
		checkDimensions();
		
		if(event.pressed()) { cursor.blink.reset(); }
		
		app.pushStyle();
			app.fill(fill.get());
			app.rect(x,y,w,h);
			
			pg.beginDraw();
				pg.clear();
				text.draw(pg);
				cursor.draw(pg);
			pg.endDraw();
			
			app.image(pg, x, y, w, h);
		app.popStyle();
		
		
	}
	
	
	
	@Override
	protected void inTransforms() {
		super.inTransforms();
		
		if(text != null) {
			text.updatePosition();
		}
		
		if(cursor != null) {
			cursor.updateTransforms();
		}
	}

	@Override
	public final void keyPressed() {
		cursor.blink.reset();
		
		text.insert(0, app.key);
	}
	
	private final void checkDimensions() {
		if(app.mousePressed) { return; }
		if(pg == null) { return; }
		
		if((int) (getW()) != pg.width || (int) (getH()) != pg.height) {
			pg = app.createGraphics((int) getW(), (int) getH(), app.sketchRenderer());
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
		}
		
		private final void updatePosition() {
			x = LEFT_OFFSET;
			y = TextField.this.getH()*.5f;
		}
		
		
		public final String getHint() {
			return hint;
		}

		public final void setHint(String hint) {
			this.hint = hint;
		}

		// TODO Continue from here, think about that, how you can selected text width to the better way
		private final float getTextWidthBehind(final int index) {
			if(index < 0 || index > length()-1) { return 0; }
			return pg.textWidth(get().substring(index));
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
			}
			
			private final int get() { return row; }
			
			private final void goToStart() { row = 0; }
			
			private final void goToEnd() { row = text.length(); }
			
			private final void back() {
				if(row > 0) { row--; }
			}
			
			private final void next() {
				if(row < text.length()) { row++; }
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