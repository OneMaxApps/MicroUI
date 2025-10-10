package microui.core.style;

import static processing.core.PApplet.map;

//Status: STABLE - Do not modify
//Last Reviewed: 11.10.2025
public abstract class AbstractGradientColor extends AbstractColor {
	private final AbstractColor start,end;
	private final Animator animator;
	
	public AbstractGradientColor(AbstractColor start, AbstractColor end) {
		super();
		
		if(start == null) {
			throw new NullPointerException("the start color for GradientColor cannot be null");
		}
		
		if(end == null) {
			throw new NullPointerException("the end color for GradientColor cannot be null");
		}
		
		this.start = start;
		this.end = end;
		
		animator = new Animator();
	}

	protected final AbstractColor getStart() {
		return start;
	}

	protected final AbstractColor getEnd() {
		return end;
	}
	
	@Override
	protected void preApply() {
		super.preApply();
		animator.update();
		
		if(getStart() instanceof AbstractGradientColor gradient) {
			gradient.preApply();
		}

		if(getEnd() instanceof AbstractGradientColor gradient) {
			gradient.preApply();
		}
		
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
	
	protected final Animator getAnimator() {
		return animator;
	}
	
	protected final int lerp(int start, int end) {
		return (int) map(animator.getProgress(),Animator.START_PROGRESS,Animator.END_PROGRESS,start,end);
	}

	protected final class Animator {
		public static final int START_PROGRESS = 0;
		public static final int END_PROGRESS = 1;
		private float progress,speed;
		private boolean isStartEnabled,isLoopEnabled;
		
		public Animator() {
			super();
			speed = .1f;
		}
		
		public void update() {
			if(isStartEnabled()) {
				if(progress < END_PROGRESS) {
					progress = Math.min(progress+speed, END_PROGRESS);
				} else {
					if(isLoopEnabled()) {
						setStartEnabled(false);
					}
					
				}
			} else {
				if(progress > START_PROGRESS) {
					progress = Math.max(progress-speed, START_PROGRESS);
				} else {
					if(isLoopEnabled()) {
						setStartEnabled(true);
					}
				}
			}
		}

		public final boolean isStartEnabled() {
			return isStartEnabled;
		}

		public final void setStartEnabled(boolean isStartEnabled) {
			this.isStartEnabled = isStartEnabled;
		}

		public final float getProgress() {
			return progress;
		}

		public final void setProgress(float progress) {
			this.progress = progress;
		}

		public final boolean isLoopEnabled() {
			return isLoopEnabled;
		}

		public final void setLoopEnabled(boolean isLoopEnabled) {
			this.isLoopEnabled = isLoopEnabled;
		}

		public final float getSpeed() {
			return speed;
		}

		public final void setSpeed(float speed) {
			if(speed < 0 || speed > 1) {
				throw new IllegalArgumentException("speed must be between 0 and 1");
			}
			this.speed = speed;
		}
		
	}
}