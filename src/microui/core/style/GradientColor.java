package microui.core.style;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static processing.core.PApplet.map;

import java.util.function.BooleanSupplier;

public final class GradientColor extends AbstractColor {
	private static final int PROGRESS_START = 0;
	private static final int PROGRESS_END = 1;
	
	private final Color start,end;
	private final BooleanSupplier condition;
	private float progressCurrent,progressSpeed;

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
	protected void preApply() {
		super.preApply();
		updateProgress();
	}

	private int lerp(int start, int end) {
		return (int) map(progressCurrent,PROGRESS_START,PROGRESS_END,start,end);
	}
	
	private void updateProgress() {
		if(condition.getAsBoolean()) {
			if(progressCurrent < PROGRESS_END) {
				progressCurrent = min(progressCurrent+progressSpeed,PROGRESS_END);
			}
		} else {
			if(progressCurrent > PROGRESS_START) {
				progressCurrent = max(progressCurrent-progressSpeed, PROGRESS_END);
			}
		}
	}
}