//package microui.component;
//
//import static processing.core.PApplet.abs;
//import static processing.core.PApplet.max;
//import static processing.core.PApplet.min;
//import static processing.core.PConstants.CENTER;
//import static processing.core.PConstants.CORNER;
//
//import microui.constants.AutoResizeMode;
//import microui.core.base.Component;
//import microui.core.base.View;
//import microui.core.style.Color;
//import processing.core.PFont;
//import processing.core.PGraphics;
//
//public class TextVieww extends Component {
//	  private final Shadow shadow;
//	  private final StringBuilder text;
//	  private PFont font;
//	  private int textSize;
//	  private boolean center,upperCaseStyle,lowerCaseStyle,isAutoResizeEnabled;
//	  private AutoResizeMode autoResizeMode;
//	  
//	  public TextVieww(String text, float x, float y, float w, float h) {
//	    super(x,y,w,h);
//	    setConstrainDimensionsEnabled(false);
//	    this.text = new StringBuilder(text);
//	    textSize = (int) (h/3 > 0 ? h/3 : h/2);
//	    autoResizeMode = AutoResizeMode.SMALL;
//	    center = true;
//	    shadow = new Shadow();
//	    setVisible(true);
//	    getMutableColor().set(255);
//	    
//	    //callback.setEnabled(false);
//	  }
//	  
//	  public TextVieww(float x, float y, float w, float h) {
//		this("",x,y,w,h);
//	  }
//	  
//	  public TextVieww(String text) {
//			this(text,ctx.width*.2f,ctx.height*.4f,ctx.width*.6f,ctx.height*.2f);
//	  }
//	  
//	  public TextVieww() {
//			this("",ctx.width*.2f,ctx.height*.4f,ctx.width*.6f,ctx.height*.2f);
//	  }
//	  
//	  @Override
//	  protected void update() {
//		  
//		  if(text.isEmpty()) { return; }
//		  
//		  shadow.draw();
//		  ctx.pushStyle();
//		  getMutableColor().apply();
//		  if(font != null) { ctx.textFont(font,textSize); }
//		  
//		  if(isAutoResizeEnabled()) {
//			  ctx.textSize(max(1,min(getContentWidth(),getContentHeight())/autoResizeMode.getValue()));
//		  } else {
//			  ctx.textSize((textSize <= 0) ? ( (getContentHeight()/3 > 0) ? getContentHeight()/3 : 1 ) : textSize);
//		  }
//		  
//		  ctx.textAlign(center ? CENTER : CORNER,CENTER);
//		  ctx.text(text.toString(), getContentX(), getContentY(), getContentWidth() ,getContentHeight());
//		  ctx.popStyle();
//		  
//		  
//	  }
//	  
//	  public void draw(PGraphics pg) {
//		  pg.pushStyle();
//		  pg.fill(getMutableColor().get());
//		  if(font != null) {
//			  pg.textFont(font,textSize);
//		  }
//		  
//		  if(isAutoResizeEnabled()) {
//			  ctx.textSize(max(1,min(getContentWidth(),getContentHeight())/autoResizeMode.getValue()));
//		  } else {
//			  ctx.textSize((textSize <= 0) ? ( (getContentHeight()/3 > 0) ? getContentHeight()/3 : 1 ) : textSize);
//		  }
//		  
//		  if(center) {
//			  pg.textAlign(CENTER,CENTER);
//		  } else {
//			  pg.textAlign(CORNER,CENTER);
//		  }
//		  pg.text(text.toString() != null ? text.toString() : "",getContentX(),getContentY()+getContentHeight()/2);
//		  pg.popStyle();
//	  }
//
//	  public final PFont getFont() {
//			return font;
//	  }
//	
//	  public final void loadFont(String path) {
//		font = ctx.loadFont(path);
//	  }
//		
//	  public final void createFont(String path, int textSize) {
//		font = ctx.createFont(path,textSize);
//	  }
//	
//	  public final void createFont(String path) {
//		createFont(path,textSize);
//	  }
//	
//	  public void setFont(PFont font) {
//		this.font = font;
//	  }
//	
//	  public void set(String text) {
//	    clear();
//	    append(text);
//	  }
//	  
//	  public final void set(TextVieww otherText) {
//		  set(otherText.get());
//	  }
//	  
//	  public void set(int digits) { set(String.valueOf(digits)); }
//	  
//	  public void set(float digits) { set(String.valueOf(digits)); }
//	  
//	  public String get() { return text.toString(); }
//	  
//	  public void clear() { text.delete(0,text.length()); }
//	  
//	  public void delete(int index) {
//		  if(text.length() == 0 || index == 0) { return; }
//		  text.delete(index-1,index);
//	  }
//	  
//	  public void deleteLastChar() {
//		  if(text.length() == 0) { return; }
//		  text.delete(text.length()-1,text.length());
//	  }
//	  
//	  public void append(String text) {
//		  this.text.append(upperCaseStyle ? text.toUpperCase() : lowerCaseStyle ? text.toLowerCase() : text);
//	  }
//	  
//	  public void append(char ch) {
//		  this.text.append(ch);
//	  }
//	  
//	  public void insert(int index, String text) {
//		  if(upperCaseStyle) {
//			  this.text.insert(index, text.toUpperCase());
//		  } else {
//			  this.text.insert(index, text);
//		  }
//	  }
//	  
//	  public void setTextSize(float textSize) {
//		  if(textSize < 1) { return; }
//		  ctx.textSize(textSize);
//		  float textHeight = ctx.textAscent()+ctx.textDescent();
//		  if(textHeight > getContentHeight()) { return; }
//		  if(textSize >= 1) { this.textSize = (int) textSize; }
//	  }
//	  
//	  public final float getTextHeight() {
//		  return ctx.textAscent()+ctx.textDescent(); 
//	  }
//	  
//	  public int getTextSize() { return textSize; }
//	
//	  public final void setInCenter(boolean center) {
//		this.center = center;
//	  }
//	  
//	  public boolean isEmpty() {
//		  return text.toString().isEmpty();
//	  }
//	  
//	  public final boolean isUpperCaseStyle() {
//		return upperCaseStyle;
//	  }
//
//	  public final void setUpperCaseStyle(boolean upperCaseStyle) {
//		if(this.upperCaseStyle) { return; }
//		
//		this.upperCaseStyle = upperCaseStyle;
//		lowerCaseStyle = false;
//		
//		set(text.toString().toUpperCase());
//		
//	  }
//	  
//	  public final boolean isLowerCaseStyle() {
//		return lowerCaseStyle;
//	  }
//
//	  public final void setLowerCaseStyle(boolean lowerCaseStyle) {
//		if(this.lowerCaseStyle) { return; }
//		
//		this.lowerCaseStyle = lowerCaseStyle;
//		upperCaseStyle = false;
//		set(text.toString().toLowerCase());
//		
//      }
//
//	  public final boolean isAutoResizeEnabled() {
//		return isAutoResizeEnabled;
//	  }
//
//	  public final void setAutoResizeEnabled(boolean autoResizeEnabled) {
//		this.isAutoResizeEnabled = autoResizeEnabled;
//	  }
//
//	  public final AutoResizeMode getAutoResizeMode() { return autoResizeMode; }
//	  
//	  public final void setAutoResizeMode(AutoResizeMode resizeMode) {
//		this.autoResizeMode = resizeMode;
//	  }
//	  
//	public final Shadow getShadow() {
//		return shadow;
//	}
//	
//	public final class Shadow extends View {
//		  public final Color color;
//		  private float extraSize,shiftX,shiftY;
//		  
//		  private Shadow() {
//			  color = new Color(0);
//			  extraSize = getContentHeight()*.01f;
//			  setVisible(false);
//		  }
//		  
//		  @Override
//		  public final void update() {
//			  ctx.pushStyle();
//			  ctx.fill(color.get());
//			  if(font != null) {
//				  ctx.textFont(font,textSize);
//			  }
//			  
//			  if(isAutoResizeEnabled()) {
//				  ctx.textSize(max(1,min(getContentWidth(),getContentHeight())/autoResizeMode.getValue())+extraSize);
//			  } else {
//				  ctx.textSize((textSize <= 0) ? ( (getContentHeight()/3 > 0) ? getContentHeight()/3 : 1 ) : textSize+extraSize);
//			  }
//			  
//			  if(center) {
//			  ctx.textAlign(CENTER,CENTER);
//			  } else {
//				  ctx.textAlign(CORNER,CENTER);
//			  }
//			  ctx.text(text.toString() != null ? text.toString() : "",getContentX()+shiftX*getContentWidth()*.05f,getContentY()+shiftY*getContentHeight()*.05f,abs(getContentWidth() <= 0 ? 1 : getContentWidth()),abs(getContentHeight() <= 0 ? 1 : getContentHeight()));
//			  ctx.popStyle();
//		}
//
//		public final float getShiftX() {
//			return shiftX;
//		}
//
//		public final void setShiftX(float shiftX) {
//			if(shiftX < -1 || shiftX > 1) { throw new IllegalArgumentException(); }
//			this.shiftX = shiftX;
//		}
//
//		public final float getShiftY() {
//			return shiftY;
//		}
//
//		public final void setShiftY(float shiftY) {
//			if(shiftY < -1 || shiftY > 1) { throw new IllegalArgumentException(); }
//			this.shiftY = shiftY;
//		}
//		 
//		public final void setSize(float size) {
//			extraSize = size;
//		}
//
//	  }
//	
//	  
//	}