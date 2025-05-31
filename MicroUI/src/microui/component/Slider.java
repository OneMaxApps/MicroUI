package microui.component;

import static processing.core.PApplet.map;

import microui.core.RangeControl;
import microui.core.style.Color;
import microui.core.style.Stroke;
import microui.util.Constants;
import microui.util.UnsafeBounds;

public final class Slider extends RangeControl {
	
	public final Rect level;
	
	public Slider(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		level = new Rect(x,y,w,h);
		level.fill.set(234);
		level.allowNegativeDimensions(true);
		
		value.set(0,100,0);
		
		setOrientation(Constants.HORIZONTAL);
		
	}
	
	public Slider() {
		this(app.width*.25f,app.height*.45f,app.width*.5f,app.height*.1f);
	}

	@Override
	public void update() {
		super.update();
		level.draw();
		
		if(event.holding()) {
			switch(orientation) {
			case Constants.HORIZONTAL: value.set(map(app.mouseX,getX(),getX()+getWidth(),value.getMin(),value.getMax())); break;
			case Constants.VERTICAL: value.set(map(app.mouseY,getY(),getY()+getHeight(),value.getMin(),value.getMax())); break;
			}
			onChangeBounds();
		}
		
		
		
	}

	@Override
	public void onChangeBounds() {
		if(level == null) { return; }
		
		level.setBounds(this);
		
		switch(orientation) {
		
			case Constants.HORIZONTAL:
				level.setWidth(map(value.get(),value.getMin(),value.getMax(),0,getWidth()));
				
			break;
			
			case Constants.VERTICAL:
				level.setY(getY()+getHeight());
				level.setWidth(getWidth());
				level.setHeight(map(value.get(),value.getMin(),value.getMax(),0,-getHeight()));
			break;
			
		}
		
	}
	
	
	public final class Rect extends UnsafeBounds {
	    public final Stroke stroke;
	    public final Color fill;
	    
	    private Rect(float x, float y, float w, float h) {
	        super(x,y,w,h);
	        visible();
	  
	        stroke = new Stroke();
	        fill = new Color(44);
	      }
	    
	    
	    public Rect() {
	      this(app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f);
	    }
	    
	    @Override
	    public void update() {
	      app.pushStyle();
	      stroke.get();
		  fill.use();
		  app.rect(x,y,w,h);
		  app.popStyle();
	 
	    }
	    
	    public void setStyle(final Rect otherRect) {
	    	stroke.set(otherRect.stroke);
	    	fill.setHEX(otherRect.fill.get());
	    }
		
	}
	
}