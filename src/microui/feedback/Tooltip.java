package microui.feedback;

import static processing.core.PApplet.constrain;
import static processing.core.PConstants.CENTER;

import microui.core.TextController;
import microui.core.base.Container;
import microui.core.base.SpatialView;
import microui.core.style.Color;
import microui.event.EventType;
import microui.event.InteractionHandler;
import microui.service.GlobalTooltip;


public final class Tooltip extends SpatialView {
	private static final int PADDING_X = 10;

	private final Text text;
	private Color color;
	private Container container;
	private boolean canBeVisible,additionalCondition,isEnabled;
	
	public Tooltip(InteractionHandler interactionHandle) {

		color = GlobalTooltip.DEFAULT_COLOR;
		
		text = new Text();
		
		interactionHandle.addListener(EventType.ENTER_LONG, () -> {
			canBeVisible = additionalCondition && (!text.isEmpty() || container != null);
		});
		
		interactionHandle.addListener(EventType.LEAVE, () -> {
			if(canBeVisible) {
//				callback.resetInsideTimer();
				canBeVisible = false;
			}
		});
		
//		interactionHandle.addListener(EventTypeOld.SHAKE, () -> {
//			if(canBeVisible) {
//				callback.resetInsideTimer();
//				canBeVisible = false;
//			}
//		});
		
		interactionHandle.addListener(EventType.PRESS, () -> {
			if(canBeVisible) {
//				callback.resetInsideTimer();
				canBeVisible = false;
			}
		});
		
		additionalCondition = true;
		
		isEnabled = true;
	}

	@Override
	public void render() {
		if(!isEnabled) { return; }
		
		color.apply();
		ctx.rect(getX(), getY(), getWidth(), getHeight());
		
		if(container != null) {
			container.draw();
		} else {
			text.draw();
		}
		
	}
	
	public final boolean isEnabled() {
		return isEnabled;
	}

	public final void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public final void setCallbacksFor(InteractionHandler interactionHandle) {
		if(interactionHandle == null) { return; }
		
		interactionHandle.addListener(EventType.ENTER_LONG, () -> {
			canBeVisible = additionalCondition && (!text.isEmpty() || container != null);
		});
		
		interactionHandle.addListener(EventType.LEAVE, () -> {
			if(canBeVisible) {
//				callback.resetInsideTimer();
				canBeVisible = false;
			}
		});
		
//		interactionHandle.addListener(EventTypeOld.SHAKE, () -> {
//			if(canBeVisible) {
//				callback.resetInsideTimer();
//				canBeVisible = false;
//			}
//		});
		
		interactionHandle.addListener(EventType.PRESS, () -> {
			if(canBeVisible) {
//				callback.resetInsideTimer();
				canBeVisible = false;
			}
		});
	}

	public final void listen() {
		if(canBeVisible) {
			GlobalTooltip.onDraw(this);
			setVisible(true);
		} else { setVisible(false); }
		
		if(isVisible()) {
			setPosition(constrain(ctx.mouseX+PADDING_X,0,ctx.width-getWidth()),constrain(ctx.mouseY,0,ctx.height-getHeight()));
			
			if(container != null) {
				container.setPositionFrom(this);
				setSizeFrom(container);
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
		
		public final Color getColor() {
			return new Color(colorText);
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
			ctx.pushStyle();
			colorText.apply();
			ctx.textSize(GlobalTooltip.DEFAULT_TEXT_SIZE);
			ctx.textAlign(CENTER,CENTER);
			ctx.text(sb.toString(), getX(), getY(), getWidth(), getHeight());
			ctx.popStyle();
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
		
			ctx.pushStyle();
			
			ctx.textSize(GlobalTooltip.DEFAULT_TEXT_SIZE);
			for(String line : LINES) {
				if(ctx.textWidth(line) > maxWidth) { maxWidth = ctx.textWidth(line); }
			}
			
			ctx.popStyle();
			final float SCALE_WIDTH = 1.1f;
			width = (int) (maxWidth*SCALE_WIDTH);
			
		}
		
		private final void calculateHeight() {
			final int LINES = sb.toString().split("\n").length;
			
			ctx.pushStyle();
			ctx.textSize(GlobalTooltip.DEFAULT_TEXT_SIZE);
			
			final float SCALE_HEIGHT = 1.2f;
			height = (int) ((ctx.textAscent()+ctx.textDescent())*SCALE_HEIGHT)*LINES;
			
			ctx.popStyle();
		}

		
		
		
	}
}