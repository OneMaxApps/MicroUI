package microUI.utils;

import processing.event.MouseEvent;
 
public final class Scrolling {
	private Event event;
    private float speed;
    private boolean isScrolling;
    
    public Scrolling(Event event) {
    	this.event = event;
    }
    
    public void init(MouseEvent e, boolean action) {
      if(!action) { return; }
      
        if(e.getCount() > 0) {
          if(speed > 0) { speed = 0; }
            speed -= e.getCount()*.1f;
        } else {
          if(speed < 0) { speed = 0; }
            speed -= e.getCount()*.1f;
        }
    }
    
    public void init(MouseEvent e) {
    	init(e,event.inside());
    }
    
    public float get() {
      if(speed > .01f) { speed -= .01f; }
      if(speed < -.01f) { speed += .01f; }
      
      isScrolling = (speed < -.01f || speed > .01f);
      
      if(event.moved()) { speed = 0; }
      
      if(isScrolling) { return speed; }
      return 0f;
    }
    
    public boolean isScrolling() { return isScrolling; }
    
  }