package microui.core.style;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static processing.core.PApplet.map;

import java.util.function.BooleanSupplier;

import processing.core.PGraphics;

public final class GradientColor extends Color {
	private final Color start,end;
	private final BooleanSupplier condition;
	private float progressStart,progress,progressEnd,progressSpeed;
	private boolean isRevertModeEnabled;
	
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
		setProgressSpeed(.05f);
	}

	public void resetAnimationProgress() {
		progress = 0;
	}
	
	public boolean isRevertModeEnabled() {
		return isRevertModeEnabled;
	}

	public void setRevertModeEnabled(boolean isRevertModeEnabled) {
		this.isRevertModeEnabled = isRevertModeEnabled;
	}

	public float getProgressSpeed() {
		return progressSpeed;
	}
	
	public void setProgressSpeed(float progressSpeed) {
		if(progressSpeed <= 0 || progressSpeed >= 1) {
			throw new IllegalArgumentException("progress speed must be between 0 and 1");
		}
		this.progressSpeed = progressSpeed;
	}

	@Override
	public int getRed() {
		return lerp(start.getRed(),end.getRed());
	}

	@Override
	public int getGreen() {
		return lerp(start.getGreen(),end.getGreen());
	}

	@Override
	public int getBlue() {
		return lerp(start.getBlue(),end.getBlue());
	}

	@Override
	public int getAlpha() {
		return lerp(start.getAlpha(),end.getAlpha());
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
				progress = min(progress+progressSpeed,progressEnd);
			}
		} else {
			if(isRevertModeEnabled()) {
				progress = progressStart;
			} else {
				if(progress > progressStart) {
					progress = max(progress-progressSpeed, progressStart);
				}
			}
			
			
		}
	}
}