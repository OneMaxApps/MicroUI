package microui.core;

import static microui.core.style.theme.ThemeManager.getTheme;

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
		setBackgroundColor(getTheme().getBackgroundColor());
		ripples = new Ripples(this);
		hover = new Hover(this);
		stroke = new Stroke();

	}

	@Override
	protected void render() {
		ctx.pushStyle();
		stroke.apply();
		getBackgroundColor().apply();
		ctx.rect(getPadX(), getPadY(), getPadWidth(), getPadHeight());
		ripples.draw();
		hover.draw();
		ctx.popStyle();
	}

	public final Color getRipplesColor() {
		return ripples.getColor();
	}

	public final void setRipplesColor(Color color) {
		ripples.setColor(color);
	}

	public final boolean isRipplesEnabled() {
		return ripples.isEnabled();
	}

	public final void setRipplesEnabled(boolean isEnabled) {
		ripples.setEnabled(isEnabled);
	}

	public final boolean isHoverEnabled() {
		return hover.isEnabled();
	}

	public final void setHoverEnabled(boolean isEnabled) {
		hover.setEnabled(isEnabled);
	}
	
	public Color getHoverColor() {
		return hover.getColor();
	}
	
	public void setHoverColor(Color color) {
		hover.setColor(color);
	}
	
	public final float getHoverSpeed() {
		return hover.getSpeed();
	}

	public final void setHoverSpeed(float speed) {
		hover.setSpeed(speed);
	}

	public final float getStrokeWeight() {
		return stroke.getWeight();
	}

	public final void setStrokeWeight(int weight) {
		stroke.setWeight(weight);
	}

	public final Color getStrokeColor() {
		return stroke.getColor();
	}

	public final void setStrokeColor(Color color) {
		stroke.setColor(color);
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