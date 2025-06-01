package microui.core;

import microui.core.base.Bounds;
import microui.core.style.Color;
import processing.core.PImage;

// Status: Stable - Do not modify
// Last Reviewed: 01.06.2025
public class Texture extends Bounds {
	public final Color tint;
	protected PImage image;
	
	public Texture() {
		tint = new Color(255);
		
	}
	
	@Override
	public void draw() {
		if(isLoaded()) { super.draw(); }
	}
	
	
	@Override
	public void update() {
		if(isLoaded()) {
			  app.pushStyle();
			  app.tint(tint.get());
			  app.image(image,x,y,w,h);
			  app.popStyle();
		  }
	}

	public final boolean isLoaded() {
		return image != null;
	}
	
	public final void set(final PImage img) {
		if(img == null) { return; }
		image = img;
	}
	
	public final void load(final String path) {
		if(path == null) { return; }
		image = app.loadImage(path);
	}
	
	public final PImage get() {
		return image;
	}
}