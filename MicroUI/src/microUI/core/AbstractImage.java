package microUI.core;

import microUI.core.base.Bounds;
import microUI.core.style.Color;
import processing.core.PImage;

public abstract class AbstractImage extends Bounds {
	public final Color tint;
	protected PImage image;
	
	public AbstractImage() {
		tint = new Color(255);
		
	}
	
	@Override
	public void draw() {
		if(isLoaded()) { super.draw(); }
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