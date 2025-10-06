package microui.core.style;

import static java.util.Objects.requireNonNull;

//Status: STABLE - Do not modify
//Last Reviewed: 04.10.2025
public class Color extends AbstractColor {
	private final int red,green,blue,alpha;
	
	public Color(float red, float green, float blue, float alpha) {
		this.red   = constrain(red,MIN_VALUE,MAX_VALUE);
		this.green = constrain(green,MIN_VALUE,MAX_VALUE);
		this.blue  = constrain(blue,MIN_VALUE,MAX_VALUE);
		this.alpha = constrain(alpha,MIN_VALUE,MAX_VALUE);
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
	
	public Color(AbstractColor color) {
		this(requireNonNull(color, "color cannot be null").getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
	}
	
	public Color() {
		this(128);
	}
	
	@Override
	public int getRed() {
		return red;
	}
	
	@Override
	public int getGreen() {
		return green;
	}
	
	@Override
	public int getBlue() {
		return blue;
	}
	
	@Override
	public int getAlpha() {
		return alpha;
	}
	
	public final boolean isTransparent() { return alpha == 0; }
}