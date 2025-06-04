package microui.core;

import microui.core.base.Component;
import microui.core.effect.Hover;
import microui.core.effect.Ripples;
import microui.core.style.Stroke;

public abstract class AbstractButton extends Component {
	public final Ripples ripples;
	public final Hover hover;
	public final Stroke stroke;
	
	public AbstractButton(float x, float y, float w, float h) {
		super(x, y, w, h);
		visible();
		ripples = new Ripples(this);
		hover = new Hover(this);
		stroke = new Stroke();
		
		eventCallback.addOnClickListener(() -> ripples.initAnim());
	}

	@Override
	public void update() {
		event.listen(this);
		
		app.pushStyle();
			stroke.get();
			fill.use();
			app.rect(x, y, w, h);
			hover.draw();
			ripples.draw();
		app.popStyle();
	}
	
}