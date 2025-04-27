package microUI.util;

import static processing.core.PApplet.abs;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.CORNER;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;

public final class Text extends Component {
	  public final Color fill;
	  public final Shadow shadow;
	  private PFont font;
	  private final StringBuilder text;
	  private int textSize;
	  private boolean center,upperCaseStyle;
	  
	  
	  public Text(PApplet app, String text, float x, float y, float w, float h) {
	    super(app,x,y,w,h);
	    this.text = new StringBuilder(text);
	    fill = new Color(255);
	    textSize = (int) (h/3 > 0 ? h/3 : h/2);
	    center = true;
	    shadow = new Shadow();
	    visible = true;
	  }
	  
	  public Text(PApplet app, float x, float y, float w, float h) {
		this(app,"",x,y,w,h);
	  }
	  
	  public Text(PApplet app, String text) {
			this(app,text,app.width*.2f,app.height*.4f,app.width*.6f,app.height*.2f);
	  }
	  
	  @Override
	  public void update() {
		  shadow.draw();
		  
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
	  
	  public void draw(PGraphics pg) {
		  pg.pushStyle();
		  pg.fill(fill.get());
		  if(font != null) {
			  pg.textFont(font,textSize);
		  }
		  pg.textSize((textSize <= 0) ? ( (h/3 > 0) ? h/3 : 1 ) : textSize);
		  if(center) {
			  pg.textAlign(CENTER,CENTER);
		  } else {
			  pg.textAlign(CORNER,CENTER);
		  }
		  pg.text(text.toString() != null ? text.toString() : "",x,y+h/2);
		  pg.popStyle();
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
	    append(text); 
	    
	  }
	  
	  public void set(int digits) { set(String.valueOf(digits)); }
	  
	  public void set(float digits) { set(String.valueOf(digits)); }
	  
	  public String get() { return text.toString(); }
	  
	  public void clear() { text.delete(0,text.length()); }
	  
	  public void delete(int index) {
		  if(text.length() == 0 || index == 0) { return; }
		  text.delete(index-1,index);
	  }
	  
	  public void deleteLastChar() {
		  if(text.length() == 0) { return; }
		  text.delete(text.length()-1,text.length());
	  }
	  
	  public void append(String text) {
		  if(upperCaseStyle) {
			  this.text.append(text.toUpperCase());
		  } else {
			  this.text.append(text);
		  }  
	  }
	  
	  public void append(char ch) { this.text.append(ch); }
	  
	  public void insert(int index, String text) {
		  if(upperCaseStyle) {
			  this.text.insert(index, text.toUpperCase());
		  } else {
			  this.text.insert(index, text);
		  }
	  }
	  
	  public void setTextSize(float textSize) {
		  if(textSize < 1) { return; }
		  app.textSize(textSize);
		  float textHeight = app.textAscent()+app.textDescent();
		  if(textHeight > getH()) { return; }
		  if(textSize >= 1) { this.textSize = (int) textSize; }
	  }
	  
	  public final float getTextHeight() {
		  return app.textAscent()+app.textDescent(); 
	  }
	  
	  public int getTextSize() { return textSize; }
	
	  public final void setInCenter(boolean center) {
		this.center = center;
	  }
	  
	  public boolean isEmpty() {
		  return text.toString().isEmpty();
	  }
	  
	  public final boolean isUpperCaseStyle() {
		return upperCaseStyle;
	  }

	  public final void setUpperCaseStyle(boolean upperCaseStyle) {
		this.upperCaseStyle = upperCaseStyle;
	  }




	public final class Shadow {
		  public Color fill;
		  private float extraSize,shiftX,shiftY;
		  private boolean isVisible;
		  
		  public Shadow() {
			  fill = new Color(0);
			  extraSize = h*.025f;
			  isVisible = false;
		  }
		  
		  public final void draw() {
			  if(isVisible) {
				  app.pushStyle();
				  app.fill(fill.get());
				  if(font != null) {
					  app.textFont(font,textSize);
				  }
				  app.textSize((textSize <= 0) ? ( (h/3 > 0) ? h/3+extraSize : 1 ) : textSize+extraSize);
				  if(center) {
				  app.textAlign(CENTER,CENTER);
				  } else {
					  app.textAlign(CORNER,CENTER);
				  }
				  app.text(text.toString() != null ? text.toString() : "",x+shiftX*w*.05f,y+shiftY*h*.05f,abs(w <= 0 ? 1 : w),abs(h <= 0 ? 1 : h));
				  app.popStyle();
			  }
		 }

		public final float getShiftX() {
			return shiftX;
		}

		public final void setShiftX(float shiftX) {
			if(shiftX < -1 || shiftX > 1) { return; }
			this.shiftX = shiftX;
		}

		public final float getShiftY() {
			return shiftY;
		}

		public final void setShiftY(float shiftY) {
			if(shiftY < -1 || shiftY > 1) { return; }
			this.shiftY = shiftY;
		}
		 
		public final void setSize(float size) {
			extraSize = size;
		}

		public final boolean isVisible() {
			return isVisible;
		}

		public final void setVisible(boolean isVisible) {
			this.isVisible = isVisible;
		}
		
		
	  } 
	}