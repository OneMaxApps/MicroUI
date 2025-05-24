package microUI.feedback;

import static processing.core.PApplet.constrain;
import static processing.core.PConstants.CENTER;

import microUI.core.TextController;
import microUI.core.base.Bounds;
import microUI.core.base.Layout;
import microUI.core.style.Color;
import microUI.event.Event;
import microUI.service.GlobalTooltip;


public final class Tooltip extends Bounds {
	private final int SECONDS_FOR_SHOWING = 2;
	
	public final Text text;
	
	private Color fill;
	private final Event event;
	private Layout layout;
	private boolean additionalCondition;
	
	public Tooltip(Event event) {
		fill = GlobalTooltip.DEFAULT_COLOR;
		
		text = new Text();
		
		this.event = event;
		
		additionalCondition = true;
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
	
	public final void init() {
		if(canBeVisible()) {
			GlobalTooltip.onDraw(this);
			visible();
		} else { invisible(); }
		
		if(isVisible()) {
			setPosition(constrain(app.mouseX,0,app.width-getW()),constrain(app.mouseY,0,app.height-getH()));
			
			if(layout != null) {
				layout.setPosition(this);
				setSize(layout);
			} else {
				setSize(text.getWidth(),text.getHeight());
			}
		}
		
	}
	
	
	
	private final boolean canBeVisible() {
		return additionalCondition && event.inside(SECONDS_FOR_SHOWING) && (!text.isEmpty() || layout != null);
	}
	
	public final Layout getLayout() {
		return layout;
	}

	public final void setLayout(Layout layout) {
		this.layout = layout;
	}

	public final void setColor(final Color color) {
		this.fill = color;
	}
	
	/** For that cases when default conditions not enough inner.  
	 *  
	 *  This method giving possibility for using your own additional condition 
	 */
	public void setAdditionalCondition(boolean additionalCondition) {
		this.additionalCondition = additionalCondition;
	}
	
	public final class Text extends TextController {
		private Color fill;
		private int width,height;
		
		private Text() {
			super();
			fill = new Color();
		}

		private final void draw() {
			app.pushStyle();
			fill.use();
			app.textSize(GlobalTooltip.DEFAULT_TEXT_SIZE);
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
			
			app.textSize(GlobalTooltip.DEFAULT_TEXT_SIZE);
			for(String line : LINES) {
				if(app.textWidth(line) > maxWidth) { maxWidth = app.textWidth(line); }
			}
			
			app.popStyle();
			final float SCALE_WIDTH = 1.1f;
			width = (int) (maxWidth*SCALE_WIDTH);
			
		}
		
		private final void calculateHeight() {
			final int LINES = sb.toString().split("\n").length;
			
			app.pushStyle();
			app.textSize(GlobalTooltip.DEFAULT_TEXT_SIZE);
			
			final float SCALE_HEIGHT = 1.2f;
			height = (int) ((app.textAscent()+app.textDescent())*SCALE_HEIGHT)*LINES;
			
			app.popStyle();
		}

		@Override
		protected void inInserting() {
			calculateWidth();
			calculateHeight();
		}
		
		public final void setColor(final Color color) {
			this.fill = color;
		}
	}
}