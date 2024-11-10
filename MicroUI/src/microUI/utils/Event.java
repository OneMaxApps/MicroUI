package microUI.utils;

import processing.core.PApplet;

public class Event {
   	  protected PApplet app;
	  private float x,y,w,h;
	  private byte wasPressed;
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
	    if(pressed()) { wasPressed = 1; }
	  }
	  
	  public boolean inside() { return app.mouseX > x && app.mouseX < x+w && app.mouseY > y && app.mouseY < y+h; }
	  public boolean outside() { return !inside(); }
	  public boolean pressed() {  return inside() && app.mousePressed; }
	  public boolean moving() { if(pressed()) { return moving = true; } else { return moving; } }
	  public boolean clicked() {
	    if(inside() & !pressed() & wasPressed == 1) {
	    	wasPressed = 0;
	    	return true;
	    }
	    return false;
	  }
	  
	}