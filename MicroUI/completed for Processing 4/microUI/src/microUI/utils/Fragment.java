package microUI.utils;

import processing.core.PApplet;
import processing.core.PImage;

public class Fragment extends BaseForm {
private PImage image;

public Fragment(PApplet app) {
	super(app,0,0,0,0);
	
}

public Fragment(PApplet app, PImage image) {
	super(app,0,0,0,0);
	this.image = image;
}

public Fragment(PApplet app, float x, float y, float w, float h) {
	super(app,x,y,w,h);
}

public Fragment(PApplet app, PImage image, float x, float y, float w, float h) {
	super(app, x, y, w, h);
	this.image = image;
}

public void draw() {
	if(image != null) {
		app.pushStyle();
		app.image(image, x,y,w,h);
		app.popStyle();
	}
}

public PImage getImage() {
	return image;
}

public void setImage(PImage image) {
	this.image = image;
}

}