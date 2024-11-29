package microUI.utils;

import microUI.MicroUI;

public class Event {

	  public static final int PRESSED = 1;
	  public static final int CLICKED = 2;
	  public static final int MOVED = 3;
	  public static final int INSIDE = 4;
	  public static final int OUTSIDE = 5;
   	  
	  private float x,y,w,h;
	  private byte wasPressed,longPressed;
	  private boolean moving;

	  public Event() { }
	  
	  public void listen(Form form) {
	    x = form.getX();
	    y = form.getY();
	    w = form.getW();
	    h = form.getH();
	      
	    if(moving && !MicroUI.app.mousePressed) { moving = false; }
	    if(pressed()) {
	    	wasPressed = 1;
	    	if(MicroUI.app.frameCount%60 == 0) { longPressed++; }
		     
	    } else {
	    	longPressed = 0;
	     }
	    
	    if(outside()) { wasPressed = 0; }
	    

	    	
	    
	  }
	  
	  public boolean inside() { return MicroUI.app.mouseX > x && MicroUI.app.mouseX < x+w && MicroUI.app.mouseY > y && MicroUI.app.mouseY < y+h; }
	  public boolean outside() { return !inside(); }
	  public boolean pressed() {  return inside() && MicroUI.app.mousePressed; }
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