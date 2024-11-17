package microUI.utils;

import processing.core.PApplet;
import processing.core.PImage;

public class View extends BaseForm {
protected PApplet applet;
private PImage image;

public View(PApplet applet) {
	super(0,0,0,0);
	this.applet = applet;
	
}

public View(PApplet applet, PImage image) {
	super(0,0,0,0);
	this.applet = applet;
	this.image = image;
}

public View(PApplet applet, int x, int y, int w, int h) {
	super(x,y,w,h);
	this.applet = applet;
	
}

public View(PApplet applet, PImage image, float x, float y, float w, float h) {
	super(x, y, w, h);
	this.applet = applet;
	this.image = image;
}

public void draw() {
	if(image != null) {
		applet.pushStyle();
		applet.image(image, x,y,w,h);
		applet.popStyle();
	}
}

public PImage getImage() {
	return image;
}

public void setImage(PImage image) {
	this.image = image;
}

}