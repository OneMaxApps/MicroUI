package microUI.utils;

import microUI.MicroUI;

public class Color {
	private int hex;

	public Color() {
		MicroUI.app.color(128);
	}

	public Color(int hex) {
		this.hex = hex;
	}

	public Color(Color c) {
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
		hex = MicroUI.app.color(gray);
	}
	
	public void set(float gray, float alpha) {
		hex = MicroUI.app.color(gray, alpha);
	}
	
	public void set(float red, float green, float blue) {
		hex = MicroUI.app.color(red,green,blue);
	}
	
	public void set(float red, float green, float blue, float alpha) {
		hex = MicroUI.app.color(red,green,blue,alpha);
	}

	public int getRed() {
		return (int) MicroUI.app.red(hex);
	}

	public int getGreen() {
		return (int) MicroUI.app.green(hex);
	}

	public int getBlue() {
		return (int) MicroUI.app.blue(hex);
	}

	public int getAlpha() {
		return (int) MicroUI.app.alpha(hex);
	}

}