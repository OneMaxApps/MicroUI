package microui.feedback;

import static processing.core.PApplet.constrain;
import static processing.core.PConstants.CENTER;

import microui.core.TextController;
import microui.core.base.Bounds;
import microui.core.base.Container;
import microui.core.style.Color;
import microui.event.Callback;
import microui.event.EventType;
import microui.service.GlobalTooltip;


public final class Tooltip extends Bounds {
	private static final int PADDING_X = 10;
	
	public final Text text;
	
	private Color fill;
	private Container container;
	private boolean canBeVisible;
	private Boolean additionalCondition;
	
	public Tooltip(Callback event) {
		fill = GlobalTooltip.DEFAULT_COLOR;
		
		text = new Text();
		
		event.addListener(EventType.INSIDE_LONG, () -> {
			canBeVisible = additionalCondition && (!text.isEmpty() || container != null);
		});
		
		event.addListener(EventType.OUTSIDE, () -> {
			if(canBeVisible) {
				event.resetInsideTimer();
				canBeVisible = false;
			}
		});
		
		event.addListener(EventType.SHAKE, () -> {
			if(canBeVisible) {
				event.resetInsideTimer();
				canBeVisible = false;
			}
		});
		
		additionalCondition = true;
	}

	@Override
	public void update() {
		fill.use();
		app.rect(x,y,w,h);
		
		if(container != null) {
			container.draw();
		} else {
			text.draw();
		}
		
	}
	
	public final void init() {
		if(canBeVisible) {
			GlobalTooltip.onDraw(this);
			visible();
		} else { invisible(); }
		
		if(isVisible()) {
			setPosition(constrain(app.mouseX+PADDING_X,0,app.width-getWidth()),constrain(app.mouseY,0,app.height-getHeight()));
			
			if(container != null) {
				container.setPosition(this);
				setSize(container);
			} else {
				setSize(text.getWidth(),text.getHeight());
			}
		}
		
	}
	
	public final Container getContainer() {
		return container;
	}

	public final void setContainer(Container container) {
		if(container == null) { return; }
		this.container = container;
	}

	public final void setColor(final Color color) {
		if(color == null) { return; }
		this.fill = color;
	}
	
	/** For that cases when default conditions not enough inner.  
	 *  <p>
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
			if(color == null) { return; }
			this.fill = color;
		}
	}
}