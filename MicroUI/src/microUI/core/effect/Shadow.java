package microUI.core.effect;

import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;
import static processing.core.PConstants.CORNERS;
import static processing.core.PConstants.SQUARE;

import microUI.core.base.Bounds;
import microUI.core.base.Layout;
import microUI.core.base.View;
import microUI.core.style.Color;

public final class Shadow extends View {
	  public final Color fill;
	  private final Bounds form;
	  private int leftSize,rightSize,upSize,downSize;
	  private byte absoluteSize;
	  
	  public Shadow(Bounds form) {
		super();
	    this.form = form;
	    fill = new Color(34);
	    leftSize = 10;
	    upSize = 10;
	    rightSize = 10;
	    downSize = 20;
	    absoluteSize = 10;
	    visible = true;
	    
	  }
	  
	  @Override
	  public void update() {
	    if(form.isVisible()) {
	      app.pushStyle();
	      
	      for(int i = 0; i < absoluteSize; i++) {
	    	  app.strokeWeight(2);
	    	  app.strokeCap(SQUARE);
	    	  app.stroke(fill.get(),constrain(164-i*20,0,255));
	    	  app.noFill();
	    	  app.rectMode(CORNERS);
	    	  if(form instanceof Layout) {
    			  app.rect(form.getX()-map(i,0f,absoluteSize,0f,leftSize),
	    		  form.getY()-map(i,0f,absoluteSize,0f,upSize),
	    		  form.getX()+form.getW()+map(i,0f,absoluteSize,0f,rightSize),
	    		  form.getY()+form.getH()+map(i,0f,absoluteSize,0f,downSize));
    		  } else {
		    	  if(form instanceof Bounds) {
		    		 app.rect(form.getX()-map(i,0f,absoluteSize,0f,leftSize),
		             form.getY()-map(i,0f,absoluteSize,0f,upSize),
		             form.getX()+form.getW()+map(i,0f,absoluteSize,0f,rightSize),
		             form.getY()+form.getH()+map(i,0f,absoluteSize,0f,downSize),leftSize,upSize,rightSize,downSize);
		    	  } 
	    		  
	    	  }
	      }
	      app.popStyle();
	    }
	  }
	  
	  public void setStyle(Shadow shadow) {
		  fill.set(shadow.fill);  
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
	    leftSize = upSize = rightSize = downSize = (int) constrain(angles,0,20);
	  }
	  
	  public int[] get() { return new int[]{leftSize,upSize,rightSize,downSize}; }
	   
	}