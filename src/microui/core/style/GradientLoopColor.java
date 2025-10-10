package microui.core.style;

public class GradientLoopColor extends AbstractGradientColor {
	private boolean isLoopEnabled;
	
	public GradientLoopColor(AbstractColor start, AbstractColor end) {
		super(start,end);
		setLoopEnabled(true);
		getAnimator().setSpeed(.01f);
	}

	public final boolean isLoopEnabled() {
		return getAnimator().isLoopEnabled();
	}

	public final void setLoopEnabled(boolean isLoopEnabled) {
		getAnimator().setLoopEnabled(isLoopEnabled);
	}
	
	@Override
	protected void preApply() {
		super.preApply();
		if(isLoopEnabled) {
			getAnimator().update();
		}
	}
}