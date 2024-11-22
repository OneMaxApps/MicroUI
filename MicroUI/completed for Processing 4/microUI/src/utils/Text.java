package microUI.utils;

import static processing.core.PApplet.abs;
import static processing.core.PConstants.CENTER;

import processing.core.PApplet;

public class Text extends Form {
	  private StringBuilder text;
	  public Color fill;
	  private int textSize;
	  
	  public Text(PApplet app, String text, float x, float y, float w, float h) {
	    super(app,x,y,w,h);
	    this.text = new StringBuilder(text);
	    fill = new Color(app,255);
	    textSize = (int) (h/3 > 0 ? h/3 : h/2);
	  }
	  
	  public void draw() {
		  app.pushStyle();
		  app.fill(fill.get());
		  app.textSize((textSize <= 0) ? ( (h/3 > 0) ? h/3 : 1 ) : textSize);
		  app.textAlign(CENTER,CENTER);
		  app.text(text.toString() != null ? text.toString() : "",x,y,abs(w <= 0 ? 1 : w),abs(h <= 0 ? 1 : h));
		  app.popStyle();
	  }
	  
	  public void set(String text) {
	    clear();
	    this.text.append(text);
	  }
	  
	  public void set(int digits) { set(String.valueOf(digits)); }
	  
	  public void set(float digits) { set(String.valueOf(digits)); }
	  
	  public String get() { return text.toString(); }
	  
	  public void clear() { text.delete(0,text.length()); }
	  
	  public void append(String text) { this.text.append(text); }
	  
	  public void setTextSize(float textSize) { if(textSize >= 1) { this.textSize = (int) textSize; } }
	  
	  public int getTextSize() { return textSize; }
	}