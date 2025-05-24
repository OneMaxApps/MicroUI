package microUI.abstracts;

import microUI.core.Component;
import microUI.graphics.Stroke;
import microUI.graphics.effect.Hover;
import microUI.graphics.effect.Ripples;

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