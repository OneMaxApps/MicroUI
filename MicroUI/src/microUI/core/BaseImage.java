package microUI.core;

import microUI.util.Color;
import processing.core.PApplet;
import processing.core.PImage;

public abstract class BaseImage extends BaseForm {
	public final Color tint;
	protected PImage image;
	
	public BaseImage(PApplet app) {
		super(app);
		tint = new Color(255);
	}
	
	public final boolean isLoaded() {
		return image != null;
	}
	
	public final void set(final PImage img) {
		if(img == null) { return; }
		image = img;
	}
	
	public final void set(final String path) {
		if(path == null) { return; }
		image = app.loadImage(path);
	}
	
	public final PImage get() {
		return image;
	}
}