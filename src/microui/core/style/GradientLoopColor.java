package microui.core.style;

import static processing.core.PApplet.map;

public class GradientLoopColor extends AbstractGradientColor {
	private final Animator animator;
	private boolean isLoopEnabled;
	
	public GradientLoopColor(AbstractColor start, AbstractColor end) {
		super(start,end);
		
		animator = new Animator();
		
		setLoopEnabled(true);
	}

	public final boolean isLoopEnabled() {
		return isLoopEnabled;
	}

	public final void setLoopEnabled(boolean isLoopEnabled) {
		this.isLoopEnabled = isLoopEnabled;
	}

	@Override
	public int getRed() {
		return animator.lerp(getStart().getRed(),getEnd().getRed());
	}

	@Override
	public int getGreen() {
		return animator.lerp(getStart().getGreen(),getEnd().getGreen());
	}

	@Override
	public int getBlue() {
		return animator.lerp(getStart().getBlue(),getEnd().getBlue());
	}

	@Override
	public int getAlpha() {
		return animator.lerp(getStart().getAlpha(),getEnd().getAlpha());
	}
	
	@Override
	protected void preApply() {
		super.preApply();
		if(isLoopEnabled) {
			animator.update();
		}
	}

	private final class Animator {
		private float speed = .01f;
		private float progress;
		
		public void update() {
			if(progress < START_PROGRESS || progress > END_PROGRESS) {
				speed = -speed;
				progress = constrain(progress, START_PROGRESS, END_PROGRESS);
			}
			
			
			progress += speed;
			
		}
		
		public int lerp(int start, int end) {
			return (int) map(progress,START_PROGRESS,END_PROGRESS,start,end);
		}
	}
}