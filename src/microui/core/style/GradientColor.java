package microui.core.style;

import static processing.core.PApplet.map;

import java.util.function.BooleanSupplier;

import processing.core.PGraphics;

public final class GradientColor extends Color {
	private final Color start,end;
	private final BooleanSupplier condition;
	private float progressStart,progress,progressEnd,progressSpeed;
	
	public GradientColor(Color start, Color end, BooleanSupplier condition) {
		super();
		if(start == null) {
			throw new NullPointerException("the start color for GradientColor cannot be null");
		}
		
		if(end == null) {
			throw new NullPointerException("the end color for GradientColor cannot be null");
		}
		
		if(condition == null) {
			throw new NullPointerException("the condition for GradientColor cannot be null");
		}
		
		this.start = start;
		this.end = end;
		this.condition = condition;
		
		progressEnd = 1;
		setProgressSpeed(.1f);
	}

	public final float getProgressSpeed() {
		return progressSpeed;
	}
	
	public final void setProgressSpeed(float progressSpeed) {
		if(progressSpeed <= 0 || progressSpeed >= 1) {
			throw new IllegalArgumentException("progress speed must be between 0 and 1");
		}
		this.progressSpeed = progressSpeed;
	}

	@Override
	public int getRed() {
		super.setRed(lerp(start.getRed(),end.getRed()));
		return super.getRed();
	}

	@Override
	public int getGreen() {
		super.setGreen(lerp(start.getGreen(),end.getGreen()));
		return super.getGreen();
	}

	@Override
	public int getBlue() {
		super.setBlue(lerp(start.getBlue(),end.getBlue()));
		return super.getBlue();
	}

	@Override
	public int getAlpha() {
		super.setAlpha(lerp(start.getAlpha(),end.getAlpha()));
		return super.getAlpha();
	}
	
	@Override
	public void set(float red, float green, float blue, float alpha) {
		throw new UnsupportedOperationException("cannot change color in to GradientColor object");
	}

	@Override
	public void set(float red, float green, float blue) {
		throw new UnsupportedOperationException("cannot change color in to GradientColor object");
	}

	@Override
	public void set(float gray, float alpha) {
		throw new UnsupportedOperationException("cannot change color in to GradientColor object");
	}

	@Override
	public void set(float gray) {
		throw new UnsupportedOperationException("cannot change color in to GradientColor object");
	}

	@Override
	public void set(Color color) {
		throw new UnsupportedOperationException("cannot change color in to GradientColor object");
	}

	@Override
	public void setRed(float red) {
		throw new UnsupportedOperationException("cannot change color in to GradientColor object");
	}

	@Override
	public void setGreen(float green) {
		throw new UnsupportedOperationException("cannot change color in to GradientColor object");
	}

	@Override
	public void setBlue(float blue) {
		throw new UnsupportedOperationException("cannot change color in to GradientColor object");
	}

	@Override
	public void setAlpha(float alpha) {
		throw new UnsupportedOperationException("cannot change color in to GradientColor object");
	}

	@Override
	public void apply() {
		updateProgress();
		super.apply();
	}

	@Override
	public void apply(PGraphics pGraphics) {
		updateProgress();
		super.apply(pGraphics);
	}

	@Override
	public void applyStroke() {
		updateProgress();
		super.applyStroke();
	}

	@Override
	public void applyStroke(PGraphics pGraphics) {
		updateProgress();
		super.applyStroke(pGraphics);
	}

	@Override
	public void applyBackground() {
		updateProgress();
		super.applyBackground();
	}

	@Override
	public void applyBackground(PGraphics pGraphics) {
		updateProgress();
		super.applyBackground(pGraphics);
	}

	@Override
	public void applyTint() {
		updateProgress();
		super.applyTint();
	}

	@Override
	public void applyTint(PGraphics pGraphics) {
		updateProgress();
		super.applyTint(pGraphics);
	}

	private int lerp(int start, int end) {
		return (int) map(progress,progressStart,progressEnd,start,end);
	}
	
	private void updateProgress() {
		if(condition.getAsBoolean()) {
			if(progress < progressEnd) {
				progress += progressSpeed;
			}
		} else {
			if(progress > progressStart) {
				progress -= progressSpeed;
			}
		}
	}
}