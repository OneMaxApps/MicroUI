package microUI.utils;

import processing.core.PApplet;

public class Event {
   	  protected PApplet app;

	  public static final int PRESSED = 1;
	  public static final int CLICKED = 2;
	  public static final int MOVED = 3;
	  public static final int INSIDE = 4;
	  public static final int OUTSIDE = 5;
   	  
	  private float x,y,w,h;
	  private byte wasPressed,longPressed;
	  private boolean moving;

	  public Event(PApplet app) {
		  this.app = app;
	  }
	  
	  public void listen(Form form) {
	    x = form.getX();
	    y = form.getY();
	    w = form.getW();
	    h = form.getH();
	      
	    if(moving && !app.mousePressed) { moving = false; }
	    if(pressed()) {
	    	wasPressed = 1;
	    	if(app.frameCount%60 == 0) { longPressed++; }
		     
	    } else {
	    	longPressed = 0;
	     }
	    
	    if(outside()) { wasPressed = 0; }
	    

	    	
	    
	  }
	  
	  public boolean inside() { return app.mouseX > x && app.mouseX < x+w && app.mouseY > y && app.mouseY < y+h; }
	  public boolean outside() { return !inside(); }
	  public boolean pressed() {  return inside() && app.mousePressed; }
	  public boolean longPressed(int seconds) {
		  if(pressed() && longPressed >= seconds) { return true; }
		  return false;
	  }
	  public boolean moved() { if(pressed()) { return moving = true; } else { return moving; } }
	  public boolean clicked() {
	    if(inside() && !pressed() && wasPressed == 1) {
	    	wasPressed = 0;
	    	action();
	    	return true;
	    }
	    return false;
	  }
	  
	  public void action() {}
	}