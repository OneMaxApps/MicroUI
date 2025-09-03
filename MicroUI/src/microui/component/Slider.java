package microui.component;

import static microui.constants.Orientation.HORIZONTAL;
import static processing.core.PApplet.map;

import microui.core.RangeControl;
import microui.core.base.Bounds;
import microui.core.style.Color;
import microui.core.style.Stroke;

public class Slider extends RangeControl {
	
	private final Rect level;
	
	public Slider(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		level = new Rect(x,y,w,h);
		level.color.set(234);
		
		value.set(0,100,0);
		
		setOrientation(HORIZONTAL);
		
	}
	
	public Slider() {
		this(cxt.width*.25f,cxt.height*.45f,cxt.width*.5f,cxt.height*.1f);
	}

	@Override
	protected void update() {
		super.update();
		level.draw();
		
		if(event.holding()) {
			switch(orientation) {
			case HORIZONTAL: value.set(map(cxt.mouseX,getX(),getX()+getWidth(),value.getMin(),value.getMax())); break;
			case VERTICAL: value.set(map(cxt.mouseY,getY(),getY()+getHeight(),value.getMax(),value.getMin())); break;
			}
			onChangeBounds();
			
			onStartChangeValue();
			onChangeValue();
			
		}
		
		
		
	}

	@Override
	protected void onChangeBounds() {
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


	public final class Rect extends Bounds {
	    public final Stroke stroke;
	    public final Color color;
	    
	    private Rect(float x, float y, float w, float h) {
	        super(x,y,w,h);
	        setVisible(true);
	        setNegativeDimensionsEnabled(true);
	        
	        stroke = new Stroke();
	        color = new Color(44);
	      }
	    
	    
	    public Rect() {
	      this(cxt.width*.3f,cxt.height*.45f,cxt.width*.4f,cxt.height*.1f);
	    }
	    
	    @Override
	    public void update() {
	      cxt.pushStyle();
	      stroke.apply();
		  color.apply();
		  cxt.rect(getX(),getY(),getWidth(),getHeight());
		  cxt.popStyle();
	 
	    }
	    
	    public void setStyle(final Rect otherRect) {
	    	stroke.set(otherRect.stroke);
	    	color.setHEX(otherRect.color.get());
	    }
		
	}
	
}