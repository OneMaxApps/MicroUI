package microUI;

import microUI.utils.Rectangle;
import microUI.utils.Text;
import processing.core.PApplet;

public class Button extends Rectangle {
	  public Text text;
	  
	  public Button(PApplet app, String text, float x, float y, float w, float h) {
	    super(app,x,y,w,h);
	    
	    this.text = new Text(app,text,x,y,w,h);
	    this.text.setTextSize(h/2);
	    corners.set(10);
	  }
	  public Button(PApplet app, float x, float y, float w, float h) { this(app,"",x,y,w,h); }
	  public Button(PApplet app, String titie) { this(app,titie,0,0,app.width,app.height); }
	  public Button(PApplet app) { this(app,"Button",app.width*.3f,app.height*.4f,app.width*.4f,app.height*.2f); }
	  
	  public void draw() {
	    super.draw();
	    text.setTransforms(this);
	    text.draw();
	  }
	  
	}