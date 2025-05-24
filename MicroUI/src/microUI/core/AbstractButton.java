package microUI.core;

import microUI.core.base.Component;
import microUI.core.effect.Hover;
import microUI.core.effect.Ripples;
import microUI.core.style.Stroke;

public abstract class AbstractButton extends Component {
	public final Ripples ripples;
	public final Hover hover;
	public final Stroke stroke;
	
	public AbstractButton(float x, float y, float w, float h) {
		super(x, y, w, h);
		ripples = new Ripples(this);
		hover = new Hover(this);
		stroke = new Stroke();
		visible();
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