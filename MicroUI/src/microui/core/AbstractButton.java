package microui.core;

import static microui.event.EventType.CLICKED;

import microui.core.base.Component;
import microui.core.effect.Hover;
import microui.core.effect.Ripples;
import microui.core.style.Color;
import microui.core.style.Stroke;

public abstract class AbstractButton extends Component {
	protected final Ripples ripples;
	protected final Hover hover;
	protected final Stroke stroke;
	
	public AbstractButton(float x, float y, float w, float h) {
		super(x, y, w, h);
		visible();
		ripples = new Ripples(this);
		hover = new Hover(this);
		stroke = new Stroke();
		
		callback.addListener(CLICKED,() -> ripples.initAnim());
	}

	@Override
	protected void update() {
		event.listen(this);
		
		app.pushStyle();
			stroke.get();
			color.use();
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
	
	public final void setHoverEnabled(boolean enable) {
		hover.setEnable(enable);
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
	
}