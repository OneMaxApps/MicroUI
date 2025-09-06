package microui.core;

import static java.util.Objects.requireNonNull;

import microui.core.base.Component;
import microui.core.effect.Hover;
import microui.core.effect.Ripples;
import microui.core.style.Color;
import microui.core.style.Stroke;

public abstract class AbstractButton extends Component {
	private final Ripples ripples;
	private final Hover hover;
	private final Stroke stroke;
	
	public AbstractButton(float x, float y, float w, float h) {
		super(x, y, w, h);
		setVisible(true);
		setConstrainDimensionsEnabled(true);
		setEventListener(this);

		ripples = new Ripples(this);
		hover = new Hover(this);
		stroke = new Stroke();

		onClick(() -> {
			ripples.initAnim();
		});

	}

	@Override
	protected void update() {
		ctx.pushStyle();
		stroke.apply();
		getMutableColor().apply();
		ctx.rect(getContentX(), getContentY(), getContentWidth(), getContentHeight());
		hover.draw();
		ripples.draw();
		ctx.popStyle();
	}

	public Color getRipplesColor() {
		return ripples.getColor();
	}

	public void setRipplesColor(Color color) {
		ripples.setColor(requireNonNull(color,"color cannot be null"));
	}

	public boolean isRipplesEnabled() {
		return ripples.isEnabled();
	}

	public void setRipplesEnabled(boolean enable) {
		ripples.setEnabled(enable);
	}

	public boolean isHoverEnabled() {
		return hover.isEnabled();
	}

	public void setHoverEnabled(boolean enabled) {
		hover.setEnabled(enabled);
	}

	public int getStrokeWeight() {
		return stroke.getWeight();
	}

	public void setStrokeWeight(int weight) {
		stroke.setWeight(weight);
	}

	public Color getStrokeColor() {
		return new Color(stroke.getColor());
	}

	public void setStrokeColor(Color color) {
		stroke.setColor(requireNonNull(color,"color cannot be null"));
	}

	protected final Ripples getMutableRipples() {
		return ripples;
	}
	
	protected final Hover getMutableHover() {
		return hover;
	}
	
	protected final Stroke getMutableStroke() {
		return stroke;
	}
}