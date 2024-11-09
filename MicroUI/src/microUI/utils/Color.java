package microUI.utils;

import processing.core.PApplet;

public class Color {
	protected PApplet app;

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
		set(c.get());
	}

	public int get() {
		return hex;
	}

	public void set(int hex) {
		this.hex = hex;
	}

	public int r() {
		return (int) app.red(hex);
	}

	public int g() {
		return (int) app.green(hex);
	}

	public int b() {
		return (int) app.blue(hex);
	}

	public int a() {
		return (int) app.alpha(hex);
	}

}