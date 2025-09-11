package microui.core.effect;

import static java.util.Objects.requireNonNull;
import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;
import static processing.core.PConstants.CORNERS;
import static processing.core.PConstants.SQUARE;

import microui.core.base.SpatialView;
import microui.core.base.View;
import microui.core.style.Color;

public final class Shadow extends View {
	  private final Color color;
	  private final SpatialView bounds;
	  private int leftSize,rightSize,upSize,downSize;
	  private byte absoluteSize;
	  
	  public Shadow(SpatialView bounds) {
		super();
	    this.bounds = requireNonNull(bounds, "bounds cannot be null");
	    color = new Color(34);
	    leftSize = 10;
	    upSize = 10;
	    rightSize = 10;
	    downSize = 20;
	    absoluteSize = 10;
	    setVisible(true);
	  }
	  
	  @Override
	  public void update() {
	    if(bounds.isVisible()) {
	      ctx.pushStyle();
	      
	      for(int i = 0; i < absoluteSize; i++) {
	    	  ctx.strokeWeight(2);
	    	  ctx.strokeCap(SQUARE);
	    	  ctx.stroke(color.get(),constrain(164-i*20,0,255));
	    	  ctx.noFill();
	    	  ctx.rectMode(CORNERS);
	    	  /*
	    	  if(bounds instanceof Layout) {
    			  ctx.rect(bounds.getX()-map(i,0f,absoluteSize,0f,leftSize),
	    		  bounds.getY()-map(i,0f,absoluteSize,0f,upSize),
	    		  bounds.getX()+bounds.getWidth()+map(i,0f,absoluteSize,0f,rightSize),
	    		  bounds.getY()+bounds.getHeight()+map(i,0f,absoluteSize,0f,downSize));
    		  } else {*/
		    	  if(bounds instanceof SpatialView) {
		    		 ctx.rect(bounds.getX()-map(i,0f,absoluteSize,0f,leftSize),
		             bounds.getY()-map(i,0f,absoluteSize,0f,upSize),
		             bounds.getX()+bounds.getWidth()+map(i,0f,absoluteSize,0f,rightSize),
		             bounds.getY()+bounds.getHeight()+map(i,0f,absoluteSize,0f,downSize),leftSize,upSize,rightSize,downSize);
		    	  } 
	    		  
	    	  //}
	      }
	      ctx.popStyle();
	    }
	  }
	  
      public final Color getColor() {
	  	return color;
	  }

	  public void setStyle(Shadow shadow) {
		  requireNonNull(shadow, "shadow cannot be null");
		  color.set(shadow.color);  
		  leftSize = shadow.leftSize;
		  rightSize = shadow.rightSize;
		  upSize = shadow.upSize;
		  downSize = shadow.downSize;
		  absoluteSize = shadow.absoluteSize;
	  }
	  
	  public void set(float left, float up, float right, float down) {
	    leftSize = (int) constrain(left,0,20);
	    upSize = (int) constrain(up,0,20);
	    rightSize = (int) constrain(right,0,20);
	    downSize = (int) constrain(down,0,20);
	  }
	  
	  public void set(float angles) {
		if(angles < 0) { throw new IllegalArgumentException("angles cannot be less than zero"); }
	    leftSize = upSize = rightSize = downSize = (int) constrain(angles,0,20);
	  }
	  
	  public int[] get() { return new int[]{leftSize,upSize,rightSize,downSize}; }
	   
	}