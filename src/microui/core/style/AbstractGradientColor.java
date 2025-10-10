package microui.core.style;

public abstract class AbstractGradientColor extends AbstractColor {
	public static final int START_PROGRESS = 0;
	public static final int END_PROGRESS = 1;
	
	private final AbstractColor start,end;

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
		
		if(getStart() instanceof GradientLoopColor g) {
			g.preApply();
		}

		if(getStart() instanceof GradientColor g) {
			g.preApply();
		}
		
		if(getEnd() instanceof GradientLoopColor g) {
			g.preApply();
		}
		
		if(getEnd() instanceof GradientColor g) {
			g.preApply();
		}
	}
	
}