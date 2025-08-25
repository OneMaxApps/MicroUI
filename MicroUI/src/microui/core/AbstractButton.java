package microui.core;

import static microui.event.EventType.CLICK;

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
		
		ripples = new Ripples(this);
		hover = new Hover(this);
		stroke = new Stroke();
		text = new TextView(x, y, w, h);
		
		callback.addListener(CLICK,() -> ripples.initAnim());
	}

	@Override
	protected void update() {
		event.listen(this);
		
		app.pushStyle();
			stroke.apply();
			color.apply();
			app.rect(x, y, w, h);
			hover.draw();
			ripples.draw();
		app.popStyle();
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
	
	public final void setRipplesEnabled(boolean enable) {
		ripples.setEnable(enable);
	}
	
	public final boolean isHoverEnabled() {
		return hover.isEnabled();
	}
	
	public final void setHoverEnabled(boolean enabled) {
		hover.setEnabled(enabled);
	}
	
	public final int getStrokeWeight() {
		return stroke.getWeight();
	}
	
	public final void setStrokeWeight(int weight) {
		stroke.setWeight(weight);
	}
	
	public final Color getStrokeColor() {
		return new Color(stroke.getColor());
	}
	
	public final void setStrokeColor(Color color) {
		stroke.setColor(color);
	}
	
	
	public final String getText() {
		return text.get();
	}
	
	public void setText(String text) {
		this.text.set(text);
	}
	
	public final PFont getFont() {
		return text.getFont();
	}
	
	public final void setFont(PFont font) {
		text.setFont(font);
	}
	
	public final Color getTextColor() {
		return text.getColor();
	}
	
	public final void setTextColor(Color color) {
		text.setColor(color);
	}
	
	public final boolean isTextVisible() {
		return text.isVisible();
	}
	
	public final void setTextVisible(boolean isVisible) {
		text.setVisible(isVisible);
	}
	
}