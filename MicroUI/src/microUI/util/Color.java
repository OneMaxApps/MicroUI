package microUI.util;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Color {
	public static final int MIN_VALUE = 0, MAX_VALUE = 255;
	private float red,green,blue,alpha;
	
	public Color(float red, float green, float blue, float alpha) {
		super();
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
	
	public Color() {
		set(128,MAX_VALUE);
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
	}
	
	public void set(float gray, float alpha) {
		set(gray,gray,gray,alpha);
	}
	
	public void set(float gray) {
		red = green = blue = gray;
	}
	
	public void set(Color otherColor) {
		setHEX(otherColor.get());
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
		if(red < MIN_VALUE || red > MAX_VALUE) { return; }
		this.red = red;
	}

	public final float getGreen() {
		return green;
	}

	public final void setGreen(float green) {
		if(green < MIN_VALUE || blue > MAX_VALUE) { return; }
		this.green = green;
	}

	public final float getBlue() {
		return blue;
	}

	public final void setBlue(float blue) {
		if(blue < MIN_VALUE || blue > MAX_VALUE) { return; }
		this.blue = blue;
	}

	public final float getAlpha() {
		return alpha;
	}

	public final void setAlpha(float alpha) {
		if(alpha < MIN_VALUE || alpha > MAX_VALUE) { return; }
		this.alpha = alpha;
	}
	
	public void use(PApplet app) {
		app.fill(get());
	}
	
	public void use(PGraphics pg) {
		pg.fill(get());
	}

	public boolean isTransparent() { return (int) alpha == 0; }
}