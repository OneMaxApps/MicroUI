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
		
		callback.addListener(CLICK,() -> {
			if(ripples != null) {
				ripples.initAnim();
			}
		});
	}

	@Override
	protected void update() {
		event.listen(this);
		
		cxt.pushStyle();
		stroke.apply();
		color.apply();
		cxt.rect(getX(), getY(), getWidth(), getHeight());
		hover.draw();
		ripples.draw();
		cxt.popStyle();
	}

	public Color getRipplesColor() {
		return ripples.getColor();
	}
	
	public void setRipplesColor(Color color) {
		ripples.setColor(color);
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