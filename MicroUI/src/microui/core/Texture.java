package microui.core;

import static java.util.Objects.requireNonNull;

import microui.core.base.Bounds;
import microui.core.style.Color;
import processing.core.PImage;

// Status: Stable - Do not modify
// Last Reviewed: 27.08.2025
public class Texture extends Bounds {
	protected final Color tint;
	protected PImage image;
	
	public Texture() {
		tint = new Color(255);
		
	}
	
	@Override
	public void draw() {
		if(isLoaded()) { super.draw(); }
	}
	
	
	@Override
	protected void update() {
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
	
	public final void set(final PImage image) {
		this.image = requireNonNull(image, "image cannot be null");
	}
	
	public final void load(final String path) {
		image = app.loadImage(requireNonNull(path, "path cannot be null"));
	}
	
	public final PImage get() {
		return image;
	}

	public final Color getTint() {
		return tint;
	}
	
}