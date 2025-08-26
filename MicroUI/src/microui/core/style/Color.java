package microui.core.style;

import static processing.core.PApplet.constrain;

import microui.MicroUI;
import microui.util.Metrics;
import processing.core.PGraphics;

public class Color {
	public static final int MIN_VALUE = 0, MAX_VALUE = 255;
	private float red,green,blue,alpha;
	
	public Color(float red, float green, float blue, float alpha) {
		super();
		Metrics.register("Color");
		
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
		
	}
	
	public Color(float red, float green, float blue) {
		this(red,green,blue,MAX_VALUE);
	}
	
	public Color(float gray, float alpha) {
		this(gray,gray,gray,alpha);
	}
	
	public Color(float gray) {
		this(gray,gray,gray,MAX_VALUE);
	}
	
	public Color(final Color color) {
		this(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
	}
	
	public Color() {
		this(128,MAX_VALUE);
	}
	
	public void set(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public void set(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		alpha = 255;
	}
	
	public void set(float gray, float alpha) {
		set(gray,gray,gray,alpha);
	}
	
	public void set(float gray) {
		set(gray,255);
	}
	
	public void set(Color color) {
		setHEX(color.get());
	}
	
	public void setHEX(int hex) {
		setAlpha(hex >> 24 & 0xFF);
		setRed(hex >> 16 & 0xFF);
		setGreen(hex >> 8 & 0xFF);
		setBlue(hex & 0xFF);	
	}
	
	public int get() {
		return hexFromRGBA(red,green,blue,alpha);
	}
	
	public static int hexFromRGBA(float red, float green, float blue, float alpha) {
		return (int) alpha << 24 | (int) red << 16 | (int) green << 8 | (int) blue;
	}

	public final float getRed() {
		return red;
	}

	public final void setRed(float red) {
		this.red = constrain(red,MIN_VALUE,MAX_VALUE);
	}

	public final float getGreen() {
		return green;
	}

	public final void setGreen(float green) {
		this.green = constrain(green,MIN_VALUE,MAX_VALUE);
	}

	public final float getBlue() {
		return blue;
	}

	public final void setBlue(float blue) {
		this.blue = constrain(blue,MIN_VALUE,MAX_VALUE);
	}

	public final float getAlpha() {
		return alpha;
	}

	public final void setAlpha(float alpha) {
		this.alpha = constrain(alpha,MIN_VALUE,MAX_VALUE);
	}
	
	public void apply() {
		MicroUI.getContext().fill(get());
	}
	
	public void apply(PGraphics pg) {
		pg.fill(get());
	}

	public boolean isTransparent() { return (int) alpha == 0; }
}