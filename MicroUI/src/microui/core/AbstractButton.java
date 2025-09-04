package microui.core;

import static java.util.Objects.requireNonNull;

import microui.component.TextView;
import microui.core.base.Component;
import microui.core.effect.Hover;
import microui.core.effect.Ripples;
import microui.core.style.Color;
import microui.core.style.Stroke;
import processing.core.PFont;

public abstract class AbstractButton extends Component {
	protected final Ripples ripples;
	protected final Hover hover;
	protected final Stroke stroke;
	protected final TextView text;

	public AbstractButton(float x, float y, float w, float h) {
		super(x, y, w, h);
		setVisible(true);
		setConstrainDimensionsEnabled(true);
		setMinSize(30, 10);

		setEventListener(this);

		ripples = new Ripples(this);
		hover = new Hover(this);
		stroke = new Stroke();
		text = new TextView(x, y, w, h);

		onClick(() -> {
			ripples.initAnim();
		});

	}

	@Override
	protected void update() {
		ctx.pushStyle();
		stroke.apply();
		getMutableColor().apply();
		ctx.rect(getX(), getY(), getWidth(), getHeight());
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

	public final String getText() {
		return text.get();
	}

	public void setText(String text) {
		this.text.set(requireNonNull(text,"text cannot be null"));
	}

	public final PFont getFont() {
		return text.getFont();
	}

	public final void setFont(PFont font) {
		text.setFont(requireNonNull(font,"font cannot be null"));
	}

	public final Color getTextColor() {
		return text.getColor();
	}

	public final void setTextColor(Color color) {
		text.setColor(requireNonNull(color,"color cannot be null"));
	}

	public final boolean isTextVisible() {
		return text.isVisible();
	}

	public final void setTextVisible(boolean isVisible) {
		text.setVisible(isVisible);
	}

}