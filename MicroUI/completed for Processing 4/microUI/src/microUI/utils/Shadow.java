package microUI.utils;

import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;
import static processing.core.PConstants.CORNERS;
import static processing.core.PConstants.SQUARE;

import processing.core.PApplet;

public class Shadow {
	  protected PApplet app;
	  public Color fill;
	  private Rectangle form;
	  private int leftSize,rightSize,upSize,downSize;
	  private byte absoluteSize;
	  private boolean isVisible;
	  
	  public Shadow(PApplet app, Rectangle form) {
		this.app = app;
	    this.form = form;
	    fill = new Color(app,34);
	    leftSize = 10;
	    upSize = 10;
	    rightSize = 10;
	    downSize = 20;
	    absoluteSize = 10;
	    isVisible = true;
	  }
	  
	  public void draw() {
	    if(form.isVisible() && isVisible) {
	      app.pushStyle();
	      
	      for(int i = 0; i < absoluteSize; i++) {
	    	  app.strokeWeight(2);
	    	  app.strokeCap(SQUARE);
	    	  app.stroke(fill.get(),constrain(164-i*20,0,255));
	    	  app.noFill();
	    	  app.rectMode(CORNERS);
	    	  app.rect(form.getX()-map(i,0f,absoluteSize,0f,leftSize),
	             form.getY()-map(i,0f,absoluteSize,0f,upSize),
	             form.getX()+form.getW()+map(i,0f,absoluteSize,0f,rightSize),
	             form.getY()+form.getH()+map(i,0f,absoluteSize,0f,downSize),
	             form.corners.get()[0],
	             form.corners.get()[1],
	             form.corners.get()[2],
	             form.corners.get()[3]);
	      }
	      app.popStyle();
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