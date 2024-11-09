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
	    if(!pressed()) {
	        if(wasPressed != 0) { wasPressed = 0; }
	      }
	      
	    if(moving && !app.mousePressed) { moving = false; }
	  }
	  
	  public boolean inside() { return app.mouseX > x && app.mouseX < x+w && app.mouseY > y && app.mouseY < y+h; }
	  public boolean outside() { return !inside(); }
	  public boolean pressed() {  return inside() && app.mousePressed; }
	  public boolean moving() { if(pressed()) { return moving = true; } else { return moving; } }
	  public boolean clicked() {
	    if(pressed()) { wasPressed = 1; }
	    return inside() && !pressed() && wasPressed == 1;
	  }
	  
	}