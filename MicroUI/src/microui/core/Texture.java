package microui.core;

import static java.util.Objects.requireNonNull;

import microui.core.base.Bounds;
import microui.core.style.Color;
import processing.core.PImage;

// Status: Stable - Do not modify
// Last Reviewed: 10.09.2025
public class Texture extends Bounds {
	private Color color;
	private PImage image;
	
	public Texture() {
		setVisible(true);
		color = new Color(255);
		
	}
	
	@Override
	public void draw() {
		if(isLoaded()) { super.draw(); }
	}
	
	@Override
	protected void update() {
		if(isLoaded()) {
		  ctx.pushStyle();
		  ctx.tint(color.get());
		  ctx.image(image,getX(), getY(), getWidth(), getHeight());
		  ctx.popStyle();
		}
	}

	public final boolean isLoaded() {
		return image != null;
	}
	
	public final void set(final PImage image) {
		this.image = requireNonNull(image, "image cannot be null");
		this.image.resize(ctx.width,ctx.height);
	}
	
	public final void load(final String path) {
		image = ctx.loadImage(requireNonNull(path, "path cannot be null"));
		image.resize(ctx.width,ctx.height);
	}
	
	public final PImage get() {
		return image;
	}

	public final Color getColor() {
		return new Color(color);
	}

	public final void setColor(Color color) {
		this.color.set(requireNonNull(color,"color cannot be null"));
	}

	@Override
	protected void onChangeDimensions() {
		super.onChangeBounds();
		if(image == null) { return; }
		image.resize(ctx.width,ctx.height);
		
	}
	
	

}