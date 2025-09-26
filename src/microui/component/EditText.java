package microui.component;

import microui.core.base.Component;
import microui.core.base.SpatialView;
import microui.core.interfaces.KeyPressable;
import microui.core.interfaces.Scrollable;
import microui.core.style.Color;
import processing.core.PGraphics;
import processing.event.MouseEvent;

public final class EditText extends Component implements Scrollable, KeyPressable {
	private static final float SCROLL_WEIGHT = 16;
	private final BufferRenderer bufferRenderer;
	private final Scroll scrollH,scrollV;
	
	public EditText() {
		super();
		bufferRenderer = new BufferRenderer();
		
		scrollH = new Scroll();
		
		scrollV = new Scroll();
		
		initDefaultScrolls();
	}

	@Override
	public void keyPressed() {

	}

	@Override
	public void mouseWheel(MouseEvent mouseEvent) {
		if(scrollH.isMouseInside()) {
			scrollH.mouseWheel(mouseEvent,isMouseInside());
		} else {
			scrollV.mouseWheel(mouseEvent,isMouseInside());
		}
	}

	@Override
	protected void render() {
		bufferRenderer.draw();
		
		scrollH.draw();
		scrollV.draw();
		
		
	}
	
	@Override
	protected void onChangeDimensions() {
		super.onChangeDimensions();
		bufferRenderer.setBoundsFrom(this);
		recalculateScrollsBounds();
	}
	
	private void initDefaultScrolls() {
		initDefaultScrollH();
		initDefaultScrollV();
	}
	
	private void initDefaultScrollH() {
		scrollH.setConstrainDimensionsEnabled(false);
		recalculateScrollHBounds();
		scrollH.setBackgroundColor(new Color(0,24));
		scrollH.setStrokeColor(new Color(0,0));
	}
	
	private void initDefaultScrollV() {
		scrollV.setConstrainDimensionsEnabled(false);
		scrollV.swapOrientation();
		recalculateScrollVBounds();
		scrollV.setBackgroundColor(new Color(0,32));
		scrollV.setStrokeColor(new Color(0,0));
	}
	
	private void recalculateScrollHBounds() {
		scrollH.setSize(getWidth()-SCROLL_WEIGHT,SCROLL_WEIGHT);
		scrollH.setPosition(getX(), getY()+getHeight()-SCROLL_WEIGHT);
	}
	
	private void recalculateScrollVBounds() {
		scrollV.setSize(SCROLL_WEIGHT,getHeight()-SCROLL_WEIGHT);
		scrollV.setPosition(getX()+getWidth()-SCROLL_WEIGHT, getY());
	}
	
	private void recalculateScrollsBounds() {
		recalculateScrollHBounds();
		recalculateScrollVBounds();
	}
	
	private final class BufferRenderer extends SpatialView {
		PGraphics graphics;

		BufferRenderer() {
			super();
			setVisible(true);
			
			createGraphics();
		}

		@Override
		protected void render() {
			if(graphics == null) { return; }
			
			graphics.beginDraw();
			//graphics.clear();
			graphics.background(255);
			
			graphics.endDraw();
			
			ctx.pushStyle();
			ctx.image(graphics, (int) getX() , (int) getY() , (int) getWidth() , (int)  getHeight());
			ctx.popStyle();
		}

		@Override
		protected void onChangeDimensions() {
			super.onChangeDimensions();

			if(isDimensionsDirty()) {
				createGraphics();
			}
		}
		
		boolean isDimensionsDirty() {
			return (int) getWidth() != graphics.width || (int) getHeight() != graphics.height;
		}
		
		void createGraphics() {
			graphics = ctx.createGraphics((int) getWidth(), (int) getHeight(), ctx.sketchRenderer());
		}
	}
}