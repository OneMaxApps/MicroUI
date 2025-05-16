package microUI.feedback;

import static processing.core.PApplet.constrain;
import static processing.core.PApplet.max;
import static processing.core.PConstants.CENTER;

import microUI.container.layout.Layout;
import microUI.core.Bounds;
import microUI.core.TextController;
import microUI.event.Event;
import microUI.util.Color;


public final class Tooltip extends Bounds {
	private final int SECONDS_FOR_SHOWING = 2;
	private final int TEXT_SIZE = (int) max(14,app.height*.01f);
	
	public final Color fill;
	public final Text text;
	
	private final Event event;
	private Layout layout;
	
	public Tooltip(Event event) {
		fill = new Color();
		fill.set(255,240,200,232);
		
		text = new Text();
		
		this.event = event;
	}
	
	@Override
	public void draw() {
		setVisible(event.inside(SECONDS_FOR_SHOWING) && !text.isEmpty());
		
		if(isVisible()) {
			setPosition(constrain(app.mouseX,0,app.width-getW()),constrain(app.mouseY+text.getHeight()/2,0,app.height-getH()));
			
			if(layout != null) {
				layout.setPosition(this);
				setSize(layout);
			} else {
				setSize(text.getWidth(),text.getHeight());
			}
			
			
			
		}
		
		super.draw();
		
		
	}

	@Override
	public void update() {
		fill.use();
		app.rect(x,y,w,h);
		
		if(layout != null) {
			layout.draw();
		} else {
			text.draw();
		}
		
	}
	
	public final Layout getLayout() {
		return layout;
	}

	public final void setLayout(Layout layout) {
		this.layout = layout;
	}

	public final class Text extends TextController {
		public final Color fill;
		private int width,height;
		
		private Text() {
			super();
			fill = new Color();
		}

		private final void draw() {
			app.pushStyle();
			fill.use();
			app.textSize(TEXT_SIZE);
			app.textAlign(CENTER,CENTER);
			app.text(sb.toString(), x, y, w, h);
			app.popStyle();
		}
		
		private final int getWidth() {
			if(width == 0) { calculateWidth(); }
			
			return width;
		}
		
		private final int getHeight() {
			if(height == 0) { calculateHeight(); }
			
			return height;
		}
		
		private final void calculateWidth() {
			final String[] LINES = sb.toString().split("\n");
			
			float maxWidth = 0;
		
			app.pushStyle();
			
			app.textSize(TEXT_SIZE);
			for(String line : LINES) {
				if(app.textWidth(line) > maxWidth) { maxWidth = app.textWidth(line); }
			}
			
			app.popStyle();
			
			width = (int) (maxWidth*1.1f);
			
		}
		
		private final void calculateHeight() {
			final int LINES = sb.toString().split("\n").length;
			
			app.pushStyle();
			app.textSize(TEXT_SIZE);
			
			height = (int) ((app.textAscent()+app.textDescent())*1.2f)*LINES;
			
			app.popStyle();
		}

		@Override
		protected void inInserting() {
			calculateWidth();
			calculateHeight();
		}
		
		
	}
}