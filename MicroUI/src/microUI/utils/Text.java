package microUI.utils;

import static processing.core.PApplet.abs;
import static processing.core.PConstants.CENTER;

import microUI.MicroUI;

public class Text extends Form {
	  private StringBuilder text;
	  public Color fill;
	  private int textSize;
	  
	  public Text(String text, float x, float y, float w, float h) {
	    super(x,y,w,h);
	    this.text = new StringBuilder(text);
	    fill = new Color(255);
	    textSize = (int) (h/3 > 0 ? h/3 : h/2);
	  }
	  
	  public void draw() {
		  MicroUI.app.pushStyle();
		  MicroUI.app.fill(fill.get());
		  MicroUI.app.textSize((textSize <= 0) ? ( (h/3 > 0) ? h/3 : 1 ) : textSize);
		  MicroUI.app.textAlign(CENTER,CENTER);
		  MicroUI.app.text(text.toString() != null ? text.toString() : "",x,y,abs(w <= 0 ? 1 : w),abs(h <= 0 ? 1 : h));
		  MicroUI.app.popStyle();
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