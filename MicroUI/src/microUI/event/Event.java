package microUI.event;

import microUI.MicroUI;
import microUI.core.AbstractRectangle;
import processing.core.PApplet;

public class Event extends MicroUI {

	  public static final int PRESSED = 1, CLICKED = 2, MOVED = 3, INSIDE = 4, OUTSIDE = 5;
   	  
	  private float x,y,w,h;
	  private byte wasPressed,longPressed,clickCounter;
	  private boolean moving,dragging,enable;
	  private static final boolean[] keys = new boolean[Character.MAX_VALUE];

	  public Event() {
		  enable = true;
	  }
	  
	  public void listen(AbstractRectangle form) {
		if(!enable) { return; }
		
	    x = form.getX();
	    y = form.getY();
	    w = form.getW();
	    h = form.getH();
	      
	    if(!app.mousePressed) {
	    	moving = false;
	    	dragging = false;
	    }
	    
	    if(pressed()) {
	    	wasPressed = 1;
	    	if(app.frameCount%60 == 0) { longPressed++; }
		     
	    } else {
	    	longPressed = 0;
	     }
	    
	    if(outside()) { wasPressed = 0; }
	    
	  }
	  
	  public void listen(float x, float y, float w, float h) {
		  	if(!enable) { return; }
			
		    this.x = x;
		    this.y = y;
		    this.w = w;
		    this.h = h;
		    
		    if(!app.mousePressed) {
		    	moving = false;
		    	dragging = false;
		    }
		    
		    if(pressed()) {
		    	wasPressed = 1;
		    	if(app.frameCount%60 == 0) { longPressed++; }
			     
		    } else {
		    	longPressed = 0;
		     }
		    
		    if(outside()) { wasPressed = 0; }
		    
	  }
	  
	  public final void setEnable(boolean enable) { this.enable = enable; }
	  public final boolean isEnable() { return enable; }
	  
	  public boolean inside() { return app.mouseX > x && app.mouseX < x+w && app.mouseY > y && app.mouseY < y+h; }
	  public boolean outside() { return !inside(); }
	  public boolean pressed() {  return inside() && app.mousePressed; }
	  public boolean longPressed(int seconds) {
		  if(pressed() && longPressed >= seconds) { return true; }
		  return false;
	  }

	  public boolean moved() {
		  if(pressed()) { return moving = true;
		  } else {
			  	return moving;
			}
	  }
	  
	  public boolean dragged() {
		  if(pressed() && (app.mouseX != app.pmouseX || app.mouseY != app.pmouseY)) { return dragging = true;
		  } else {
			  	return dragging;
			}
	  }
	  
	  public boolean clicked() {
	    if(inside() && !pressed() && wasPressed == 1) {
	    	wasPressed = 0;
	    	action();
	    	return true;
	    }
	    return false;
	  }
	  
	  public boolean clicked(int count) {
		  if(clickCounter == count) {clickCounter = 0;} else {
			 if(clicked()) { clickCounter++; } else {
				 if(clickCounter != 0 && app.frameCount%30 == 0) { clickCounter--; }
			 }
		  }
		  return clickCounter == count;
	  }
	  
	  public static final void keyPressed(PApplet app) {
		  keys[app.key] = true;
		  keys[app.keyCode] = true;
	  }
	  
	  public static final boolean checkKey(int ch) {
		  return keys[ch];
	  }
	  
	  public static final void keyReleased() {
		  for(int i = 0; i < keys.length; i++) {
				  keys[i] = false;
		  }
		  
	  }
	  
	  public void action() {}
	  
	  public final void resetState() {
		  keyReleased();
		  longPressed = wasPressed = clickCounter = 0;
		  moving = dragging = false;
	  }
	}