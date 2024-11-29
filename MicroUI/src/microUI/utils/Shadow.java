package microUI.utils;

import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;
import static processing.core.PConstants.CORNERS;
import static processing.core.PConstants.SQUARE;

import microUI.MicroUI;
import microUI.layouts.Layout;
import processing.core.PApplet;

public class Shadow {
	  public Color fill;
	  private BaseForm form;
	  private int leftSize,rightSize,upSize,downSize;
	  private byte absoluteSize;
	  private boolean isVisible;
	  
	  public Shadow(BaseForm form) {
	    this.form = form;
	    fill = new Color(34);
	    leftSize = 10;
	    upSize = 10;
	    rightSize = 10;
	    downSize = 20;
	    absoluteSize = 10;
	    isVisible = true;
	  }
	  
	  public void draw() {
	    if(form.isVisible() && isVisible) {
	      MicroUI.app.pushStyle();
	      
	      for(int i = 0; i < absoluteSize; i++) {
	    	  MicroUI.app.strokeWeight(2);
	    	  MicroUI.app.strokeCap(SQUARE);
	    	  MicroUI.app.stroke(fill.get(),constrain(164-i*20,0,255));
	    	  MicroUI.app.noFill();
	    	  MicroUI.app.rectMode(CORNERS);
	    	  if(form instanceof Layout) {
    			  MicroUI.app.rect(form.getX()-map(i,0f,absoluteSize,0f,leftSize),
	    		  form.getY()-map(i,0f,absoluteSize,0f,upSize),
	    		  form.getX()+form.getW()+map(i,0f,absoluteSize,0f,rightSize),
	    		  form.getY()+form.getH()+map(i,0f,absoluteSize,0f,downSize));
    		  } else {
		    	  if(form instanceof BaseForm) {
		    		 MicroUI.app.rect(form.getX()-map(i,0f,absoluteSize,0f,leftSize),
		             form.getY()-map(i,0f,absoluteSize,0f,upSize),
		             form.getX()+form.getW()+map(i,0f,absoluteSize,0f,rightSize),
		             form.getY()+form.getH()+map(i,0f,absoluteSize,0f,downSize),leftSize,upSize,rightSize,downSize);
		    	  } 
	    		  
	    	  }
	      }
	      MicroUI.app.popStyle();
	    }
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
	  
	  public void setVisible(boolean isVisible) { this.isVisible = isVisible; }
	  public boolean isVisible() { return isVisible; }
	  
	}