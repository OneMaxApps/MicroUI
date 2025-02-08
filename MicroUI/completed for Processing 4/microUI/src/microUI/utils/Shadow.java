package microUI.utils;

import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;
import static processing.core.PConstants.CORNERS;
import static processing.core.PConstants.SQUARE;
import microUI.layouts.Layout;
import processing.core.PApplet;

public final class Shadow extends View {
	  public Color fill;
	  private BaseForm form;
	  private int leftSize,rightSize,upSize,downSize;
	  private byte absoluteSize;
	  
	  public Shadow(PApplet app, BaseForm form) {
		super(app);
	    this.form = form;
	    this.app = app;
	    fill = new Color(app,34);
	    leftSize = 10;
	    upSize = 10;
	    rightSize = 10;
	    downSize = 20;
	    absoluteSize = 10;
	    isVisible = true;
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
		    	  if(form instanceof BaseForm) {
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
		  fill = shadow.fill;
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