package microUI.utils;

import processing.core.PApplet;

public class Color {
	private PApplet app;
	private int hex;

	public Color(PApplet app) {
		this.app = app;
		app.color(128);
	}

	public Color(PApplet app, int hex) {
		this.app = app;
		this.hex = hex;
	}

	public Color(PApplet app, Color c) {
		this.app = app;
		set(c);
	}

	public int get() {
		return hex;
	}
	
	public void set(Color c) {
		this.hex = c.get();
	}
	
	public void setHEX(int hex) {
		this.hex = hex;
	}
	
	public void set(float gray) {
		hex = app.color(gray);
	}
	
	public void set(float gray, float alpha) {
		hex = app.color(gray, alpha);
	}
	
	public void set(float red, float green, float blue) {
		hex = app.color(red,green,blue);
	}
	
	public void set(float red, float green, float blue, float alpha) {
		hex = app.color(red,green,blue,alpha);
	}

	public int getRed() {
		return (int) app.red(hex);
	}

	public int getGreen() {
		return (int) app.green(hex);
	}

	public int getBlue() {
		return (int) app.blue(hex);
	}

	public int getAlpha() {
		return (int) app.alpha(hex);
	}

}