package microUI;

import static processing.core.PApplet.min;

import microUI.utils.Rectangle;
import microUI.utils.Text;
import processing.core.PApplet;

public class Button extends Rectangle {
	  public Text text;
	  
	  public Button(PApplet app, String text, float x, float y, float w, float h) {
	    super(app,x,y,w,h);
	    
	    this.text = new Text(app,text.toUpperCase(),x,y,w,h);
	    this.text.setTextSize(h/2);
	  }
	  public Button(PApplet app, float x, float y, float w, float h) { this(app,"",x,y,w,h); }
	  public Button(PApplet app, String titie) { this(app,titie,app.width*.3f,app.height*.45f,app.width*.4f,app.height*.1f); } 
	  public Button(PApplet app) {
		  this(app,"BUTTON");
	  }
	  
	  @Override
	  public void update() {
		 super.update();
		 text.setTransforms(this);
		 text.draw();
		 
	  }
	  
	  public void setStyle(Button button) {
	    	super.setStyle(button);
	    	text = button.text;
	  }
	  
	  @Override
	  public void setW(float w) {
	    super.setW(w);
	    if(text == null) { return; }
	    text.setTextSize(min(w,h)/2);
	  }
	  
	  @Override
	  public void setH(float h) {
		super.setH(h);
		if(text == null) { return; }
		text.setTextSize(min(w,h)/2);
	  }
	  
	  
	}