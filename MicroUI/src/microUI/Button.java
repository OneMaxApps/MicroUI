package microUI;

import microUI.utils.Rectangle;
import microUI.utils.Text;

public class Button extends Rectangle {
	  public Text text;
	  
	  public Button(String text, float x, float y, float w, float h) {
	    super(MicroUI.app,x,y,w,h);
	    
	    this.text = new Text(app,text,x,y,w,h);
	    this.text.setTextSize(h/2);
	    corners.set(10);
	  }
	  public Button(float x, float y, float w, float h) { this("",x,y,w,h); }
	  public Button(String titie) { this(titie,MicroUI.app.width*.3f,MicroUI.app.height*.45f,MicroUI.app.width*.4f,MicroUI.app.height*.1f); }
	  
	  public Button() {
		  this("Button");
	  }
	  
	  public void draw() {
		if(isVisible()) {
		    super.draw();
		    text.setTransforms(this);
		    text.draw();
		}
	  }
	  
	}