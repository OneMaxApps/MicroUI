package microUI.utils;

import static processing.core.PApplet.map;

import microUI.Button;
import microUI.Scroll;
import microUI.Slider;
import processing.core.PApplet;

public class FX {
	  protected PApplet app;
	  public Buttons button;
	  public Scrolls scroll;
	  public Sliders slider;
	  private int duration,pos;
	  
	  public FX (PApplet app, Button b) {
		this.app = app;
	    button = new Buttons(app);
	    button.before = b;
	    
	    setDuration(10);
	  }
	  
	  public FX (PApplet app, Scroll sc) {
		this.app = app;
	    scroll = new Scrolls(app);
	    scroll.before = sc;
	    
	    setDuration(10);
	  }
	  
	  public FX (PApplet app, Slider sl) {
	    slider = new Sliders(app);
	    slider.before = sl;
	    setDuration(10);
	  }
	  
	  public void init(Button b, boolean event) {
	    if(b == null || b.fill == null  || b.text == null || b.event == null || b.text.fill == null || button == null) { return; }
	    if(button == null || button.before == null || button.after == null) { return; }
	    
	    if(event) {
	        if(pos != duration) { pos++; }
	      } else {
	        if(pos != 0) { pos--; }
	      }


	    b.fill.set(app.color((int) map(pos,0,duration,button.before.fill.r(),button.after.fill.r()),
	                     (int) map(pos,0,duration,button.before.fill.g(),button.after.fill.g()),
	                     (int) map(pos,0,duration,button.before.fill.b(),button.after.fill.b()),
	                     (int) map(pos,0,duration,button.before.fill.a(),button.after.fill.a())));
	                     
	    b.text.fill.set(app.color((int) map(pos,0,duration,button.before.text.fill.r(),button.after.text.fill.r()),
	                          (int) map(pos,0,duration,button.before.text.fill.g(),button.after.text.fill.g()),
	                          (int) map(pos,0,duration,button.before.text.fill.b(),button.after.text.fill.b()),
	                          (int) map(pos,0,duration,button.before.text.fill.a(),button.after.text.fill.a())));
	                          
	    b.text.setTextSize((int) map(pos,0,duration,button.before.text.getTextSize(),button.after.text.getTextSize()));
	    
	    if(b.shadow != null) {
	      b.shadow.fill.set(app.color((int) map(pos,0,duration,button.before.shadow.fill.r(),button.after.shadow.fill.r()),
	                       (int) map(pos,0,duration,button.before.shadow.fill.g(),button.after.shadow.fill.g()),
	                       (int) map(pos,0,duration,button.before.shadow.fill.b(),button.after.shadow.fill.b()),
	                       (int) map(pos,0,duration,button.before.shadow.fill.a(),button.after.shadow.fill.a())));
	    }
	      
	    }
	    
	    public void init(Scroll sc, boolean event) {
	    if(sc == null || sc.fill == null  || sc.event == null) { return; }
	    if(scroll == null || scroll.before == null || scroll.after == null) { return; }
	    
	    if(event) {
	        if(pos != duration) { pos++; }
	      } else {
	        if(pos != 0) { pos--; }
	      }
	     
	    sc.fill.set(app.color((int) map(pos,0,duration,scroll.before.fill.r(),scroll.after.fill.r()),
	                     (int) map(pos,0,duration,scroll.before.fill.g(),scroll.after.fill.g()),
	                     (int) map(pos,0,duration,scroll.before.fill.b(),scroll.after.fill.b()),
	                     (int) map(pos,0,duration,scroll.before.fill.a(),scroll.after.fill.a())));
	                     
	    if(sc.shadow != null) {
	      sc.shadow.fill.set(app.color((int) map(pos,0,duration,scroll.before.shadow.fill.r(),scroll.after.shadow.fill.r()),
	                       (int) map(pos,0,duration,scroll.before.shadow.fill.g(),scroll.after.shadow.fill.g()),
	                       (int) map(pos,0,duration,scroll.before.shadow.fill.b(),scroll.after.shadow.fill.b()),
	                       (int) map(pos,0,duration,scroll.before.shadow.fill.a(),scroll.after.shadow.fill.a())));
	    }
	    
	    init(sc.button,sc.button.event.pressed());
	    init(sc.buttonUp,sc.buttonUp.event.pressed());
	    init(sc.buttonDown,sc.buttonDown.event.pressed());
	      
	    }
	    
	    public void init(Slider sl, boolean event) {
	    
	    if(slider == null || slider.before == null || slider.after == null) { return; }
	    
	    if(event) {
	        if(pos != duration) { pos++; }
	      } else {
	        if(pos != 0) { pos--; }
	      }
	    
	    
	    sl.fill.set(app.color((int) map(pos,0,duration,slider.before.fill.r(),slider.after.fill.r()),
	                     (int) map(pos,0,duration,slider.before.fill.g(),slider.after.fill.g()),
	                     (int) map(pos,0,duration,slider.before.fill.b(),slider.after.fill.b()),
	                     (int) map(pos,0,duration,slider.before.fill.a(),slider.after.fill.a())));
	                     
	    if(sl.shadow != null) {
	      sl.shadow.fill.set(app.color((int) map(pos,0,duration,slider.before.shadow.fill.r(),slider.after.shadow.fill.r()),
	                               (int) map(pos,0,duration,slider.before.shadow.fill.g(),slider.after.shadow.fill.g()),
	                               (int) map(pos,0,duration,slider.before.shadow.fill.b(),slider.after.shadow.fill.b()),
	                               (int) map(pos,0,duration,slider.before.shadow.fill.a(),slider.after.shadow.fill.a())));
	    }
	    
	    init(sl.button,sl.button.event.pressed());
	      
	    }
	  
	  
	  
	  private class Buttons {
	    public Button before,after;
	    
	    public Buttons(PApplet app) {
	      before = new Button(app,0,0,0,0);
	      after = new Button(app,0,0,0,0);
	      after.fill.set(app.color(128));
	      before.text.fill.set(app.color(234));
	      after.text.fill.set(app.color(34));
	    }
	  }
	  
	  private class Scrolls {
	    public Scroll before,after;
	    
	    public Scrolls(PApplet app) {
	      before = new Scroll(app,0,0,0,0);
	      after = new Scroll(app,0,0,0,0);
	      before.fill.set(app.color(34));
	      after.fill.set(app.color(64));
	    }
	  }
	  
	  private class Sliders {
	    public Slider before,after;
	    
	    public Sliders(PApplet app) {
	      before = new Slider(app,0,0,0,0);
	      after = new Slider(app,0,0,0,0);
	      before.fill.set(app.color(34));
	      after.fill.set(app.color(64));
	    }
	  }
	  
	  public void setDuration(int duration) { this.duration = duration; }
	  public int getDuration() { return duration; }
	  
	}