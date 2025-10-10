package microui.core.style;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static processing.core.PApplet.map;

import java.util.function.BooleanSupplier;

public final class GradientColor extends AbstractGradientColor {
	
	private final BooleanSupplier condition;
	private float progressCurrent,progressSpeed;

	public GradientColor(AbstractColor start, AbstractColor end, BooleanSupplier condition) {
		super(start,end);
		
		if(condition == null) {
			throw new NullPointerException("the condition for GradientColor cannot be null");
		}

		this.condition = condition;
		
		setProgressSpeed(.05f);
	}

	public void resetAnimationProgress() {
		progressCurrent = 0;
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
		return lerp(getStart().getRed(),getEnd().getRed());
	}

	@Override
	public int getGreen() {
		return lerp(getStart().getGreen(),getEnd().getGreen());
	}

	@Override
	public int getBlue() {
		return lerp(getStart().getBlue(),getEnd().getBlue());
	}

	@Override
	public int getAlpha() {
		return lerp(getStart().getAlpha(),getEnd().getAlpha());
	}

	@Override
	protected void preApply() {
		super.preApply();
		updateProgress();
	}

	private int lerp(int start, int end) {
		return (int) map(progressCurrent,START_PROGRESS,END_PROGRESS,start,end);
	}
	
	private void updateProgress() {
		if(condition.getAsBoolean()) {
			if(progressCurrent < END_PROGRESS) {
				progressCurrent = min(progressCurrent+progressSpeed,END_PROGRESS);
			}
		} else {
			if(progressCurrent > START_PROGRESS) {
				progressCurrent = max(progressCurrent-progressSpeed, START_PROGRESS);
			}
		}
	}
}