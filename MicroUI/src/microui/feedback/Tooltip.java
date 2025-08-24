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

	private final Text text;
	private Color color;
	private Container container;
	private boolean canBeVisible,additionalCondition,isEnable;
	
	public Tooltip(Callback callback) {

		color = GlobalTooltip.DEFAULT_COLOR;
		
		text = new Text();
		
		callback.addListener(EventType.MOUSE_ENTER_LONG, () -> {
			canBeVisible = additionalCondition && (!text.isEmpty() || container != null);
		});
		
		callback.addListener(EventType.MOUSE_EXIT, () -> {
			if(canBeVisible) {
				callback.resetMouseEnterTimer();
				canBeVisible = false;
			}
		});
		
		callback.addListener(EventType.SHAKE, () -> {
			if(canBeVisible) {
				callback.resetMouseEnterTimer();
				canBeVisible = false;
			}
		});
		
		callback.addListener(EventType.PRESSED, () -> {
			if(canBeVisible) {
				callback.resetMouseEnterTimer();
				canBeVisible = false;
			}
		});
		
		additionalCondition = true;
		
		isEnable = true;
	}

	@Override
	public void update() {
		if(!isEnable) { return; }
		
		color.use();
		app.rect(x,y,w,h);
		
		if(container != null) {
			container.draw();
		} else {
			text.draw();
		}
		
	}
	
	public final boolean isEnabled() {
		return isEnable;
	}

	public final void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public final void init() {
		if(canBeVisible) {
			GlobalTooltip.onDraw(this);
			setVisible(true);
		} else { setVisible(false); }
		
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
		this.color = color;
	}
	
	public final Color getColor() {
		if(color == GlobalTooltip.DEFAULT_COLOR) {
			return color = new Color(GlobalTooltip.DEFAULT_COLOR);
		}
		return new Color(color);
	}
	
	public final Text getText() {
		return text;
	}
	
	/** For that cases when default conditions not enough inner.  
	 *  <p>
	 *  This method giving possibility for using your own additional condition 
	 */
	public void setAdditionalCondition(boolean additionalCondition) {
		this.additionalCondition = additionalCondition;
	}
	
	public final class Text extends TextController {
		private Color colorText;
		private int width,height;
		
		private Text() {
			super();
			colorText = new Color();
		}
		
		public final void setColor(final Color color) {
			if(color == null) { return; }
			this.colorText = color;
		}
		

		@Override
		protected void inInserting() {
			calculateWidth();
			calculateHeight();
		}
		
		private final void draw() {
			app.pushStyle();
			colorText.use();
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

		
		
		
	}
}