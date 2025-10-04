package microui.core.style;

import static java.util.Objects.requireNonNull;
import static microui.MicroUI.getContext;

import microui.util.Metrics;
import processing.core.PGraphics;

//Status: STABLE - Do not modify
//Last Reviewed: 04.10.2025
public class Color {
	public static final int MIN_VALUE = 0, MAX_VALUE = 255;
	private int red,green,blue,alpha;
	
	public Color(float red, float green, float blue, float alpha) {
		setInternal(red, green, blue, alpha);
		Metrics.register(this);
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
	
	public Color(Color color) {
		this(requireNonNull(color, "color cannot be null").getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
	}
	
	public Color() {
		this(128);
	}
	
	public void set(float red, float green, float blue, float alpha) {
		this.red   = constrain(red,MIN_VALUE,MAX_VALUE);
		this.green = constrain(green,MIN_VALUE,MAX_VALUE);
		this.blue  = constrain(blue,MIN_VALUE,MAX_VALUE);
		this.alpha = constrain(alpha,MIN_VALUE,MAX_VALUE);
	}
	
	public void set(float red, float green, float blue) {
		set(getRed(),getGreen(),getBlue(),MAX_VALUE);
	}
	
	public void set(float gray, float alpha) {
		set(gray,gray,gray,alpha);
	}
	
	public void set(float gray) {
		set(gray,MAX_VALUE);
	}
	
	public void set(Color color) {
		if(color == null) {
			throw new NullPointerException("color cannot be null");
		}
		set(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
	}

	public int getRed() {
		return red;
	}

	public void setRed(float red) {
		this.red = constrain(red,MIN_VALUE,MAX_VALUE);
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(float green) {
		this.green = constrain(green,MIN_VALUE,MAX_VALUE);
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(float blue) {
		this.blue = constrain(blue,MIN_VALUE,MAX_VALUE);
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = constrain(alpha,MIN_VALUE,MAX_VALUE);
	}
	
	public void apply() {
		getContext().fill(getRed(),getGreen(),getBlue(),getAlpha());
	}
	
	public void apply(PGraphics pGraphics) {
		if(pGraphics == null) {
			throw new NullPointerException("pGraphics cannot be null");
		}
		pGraphics.fill(getRed(),getGreen(),getBlue(),getAlpha());
	}
	
	public void applyStroke() {
		getContext().stroke(getRed(),getGreen(),getBlue(),getAlpha());
	}
	
	public void applyStroke(PGraphics pGraphics) {
		if(pGraphics == null) {
			throw new NullPointerException("pGraphics cannot be null");
		}
		pGraphics.stroke(getRed(),getGreen(),getBlue(),getAlpha());
	}
	
	public void applyBackground() {
		getContext().background(getRed(),getGreen(),getBlue(),getAlpha());
	}
	
	public void applyBackground(PGraphics pGraphics) {
		if(pGraphics == null) {
			throw new NullPointerException("pGraphics cannot be null");
		}
		pGraphics.background(getRed(),getGreen(),getBlue(),getAlpha());
	}
	
	public void applyTint() {
		getContext().tint(getRed(),getGreen(),getBlue(),getAlpha());
	}
	
	public void applyTint(PGraphics pGraphics) {
		if(pGraphics == null) {
			throw new NullPointerException("pGraphics cannot be null");
		}
		pGraphics.tint(getRed(),getGreen(),getBlue(),getAlpha());
	}

	public final boolean isTransparent() { return alpha == 0; }
	
	protected static int constrain(float value, float min, float max) {
		return (int) (value < min ? min : value > max ? max : value);
	}
	
	private void setInternal(float red, float green, float blue, float alpha) {
		this.red   = constrain(red,MIN_VALUE,MAX_VALUE);
		this.green = constrain(green,MIN_VALUE,MAX_VALUE);
		this.blue  = constrain(blue,MIN_VALUE,MAX_VALUE);
		this.alpha = constrain(alpha,MIN_VALUE,MAX_VALUE);
	}
}