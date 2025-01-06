package microUI.utils;

import static processing.core.PApplet.abs;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.CORNER;

import processing.core.PApplet;
import processing.core.PFont;

public class Text extends BaseView {
	  private PFont font;
	  public Color fill;
	  private StringBuilder text;
	  private int textSize;
	  private boolean center;
	  
	  public Text(PApplet app, String text, float x, float y, float w, float h) {
	    super(app,x,y,w,h);
	    this.text = new StringBuilder(text);
	    fill = new Color(app,255);
	    textSize = (int) (h/3 > 0 ? h/3 : h/2);
	    center = true;
	  }
	  
	  public Text(PApplet app, String text) {
		this(app,text,app.width*.2f,app.height*.4f,app.width*.6f,app.height*.2f);
	  }
	  
	  public void draw() {
		  app.pushStyle();
		  app.fill(fill.get());
		  if(font != null) {
			  app.textFont(font,textSize);
		  }
		  app.textSize((textSize <= 0) ? ( (h/3 > 0) ? h/3 : 1 ) : textSize);
		  if(center) {
		  app.textAlign(CENTER,CENTER);
		  } else {
			  app.textAlign(CORNER,CENTER);
		  }
		  app.text(text.toString() != null ? text.toString() : "",x,y,abs(w <= 0 ? 1 : w),abs(h <= 0 ? 1 : h));
		  app.popStyle();
	  }
	  
	  
	  
	  
	public final PFont getFont() {
		return font;
	}

	public final void loadFont(String path) {
		font = app.loadFont(path);
	}
	
	public final void createFont(String path, int textSize) {
		font = app.createFont(path,textSize);
	}
	
	public final void createFont(String path) {
		createFont(path,textSize);
	}
	
	public void setFont(PFont font) {
		this.font = font;
	}
	
	public void set(String text) {
	    clear();
	    this.text.append(text);
	  }
	  
	  public void set(int digits) { set(String.valueOf(digits)); }
	  
	  public void set(float digits) { set(String.valueOf(digits)); }
	  
	  public String get() { return text.toString(); }
	  
	  public void clear() { text.delete(0,text.length()); }
	  
	  public void delete(int index) {
		  if(text.length() == 0 || index == 0) { return; }
		  text.delete(index-1,index);
	  }
	  
	  public void append(String text) { this.text.append(text); }
	  
	  public void insert(int index, String text) { this.text.insert(index, text); }
	  
	  public void setTextSize(float textSize) { if(textSize >= 1) { this.textSize = (int) textSize; } }
	  
	  public int getTextSize() { return textSize; }
	
	  public final void setInCenter(boolean center) {
		this.center = center;
	  }
	  
	  
	}