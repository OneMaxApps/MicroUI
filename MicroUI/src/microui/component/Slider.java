package microui.component;

import static microui.constants.Orientation.HORIZONTAL;
import static processing.core.PApplet.map;

import microui.core.RangeControl;
import microui.core.style.Color;
import microui.core.style.Stroke;
import microui.util.UnsafeBounds;

public class Slider extends RangeControl {
	
	private final Rect level;
	
	public Slider(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		level = new Rect(x,y,w,h);
		level.color.set(234);
		level.allowNegativeDimensions(true);
		
		value.set(0,100,0);
		
		setOrientation(HORIZONTAL);
		
	}
	
	public Slider() {
		this(app.width*.25f,app.height*.45f,app.width*.5f,app.height*.1f);
	}

	@Override
	protected void update() {
		super.update();
		level.draw();
		
		if(event.holding()) {
			switch(orientation) {
			case HORIZONTAL: value.set(map(app.mouseX,getX(),getX()+getWidth(),value.getMin(),value.getMax())); break;
			case VERTICAL: value.set(map(app.mouseY,getY(),getY()+getHeight(),value.getMax(),value.getMin())); break;
			}
			onChangeBounds();
			
			onStartChangeValue();
			onChangeValue();
			
		}
		
		
		
	}

	@Override
	public void onChangeBounds() {
		if(level == null) { return; }
		
		level.setBounds(this);
		
		switch(orientation) {
		
			case HORIZONTAL:
				level.setWidth(map(value.get(),value.getMin(),value.getMax(),0,getWidth()));
				
			break;
			
			case VERTICAL:
				level.setY(getY()+getHeight());
				level.setWidth(getWidth());
				level.setHeight(map(value.get(),value.getMin(),value.getMax(),0,-getHeight()));
			break;
			
		}
		
	}
	
	@Override
	public void swapOrientation() {
		super.swapOrientation();
		onChangeBounds();
	}
	
	public final Rect getLevel() {
		return level;
	}


	public final class Rect extends UnsafeBounds {
	    public final Stroke stroke;
	    public final Color color;
	    
	    private Rect(float x, float y, float w, float h) {
	        super(x,y,w,h);
	        setVisible(true);
	  
	        stroke = new Stroke();
	        color = new Color(44);
	      }
	    
	    
	    public Rect() {
	      this(app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f);
	    }
	    
	    @Override
	    public void update() {
	      app.pushStyle();
	      stroke.apply();
		  color.apply();
		  app.rect(x,y,w,h);
		  app.popStyle();
	 
	    }
	    
	    public void setStyle(final Rect otherRect) {
	    	stroke.set(otherRect.stroke);
	    	color.setHEX(otherRect.color.get());
	    }
		
	}
	
}