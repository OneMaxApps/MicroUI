package microui.core.style;

import java.util.function.BooleanSupplier;

public final class GradientColor extends AbstractGradientColor {
	
	private final BooleanSupplier condition;

	public GradientColor(AbstractColor start, AbstractColor end, BooleanSupplier condition) {
		super(start,end);
		
		if(condition == null) {
			throw new NullPointerException("the condition for GradientColor cannot be null");
		}

		this.condition = condition;
	}

	public void resetAnimationProgress() {
		getAnimator().setProgress(0);
	}

	@Override
	protected void preApply() {
		super.preApply();
		getAnimator().setStartEnabled(condition.getAsBoolean());
	}
}