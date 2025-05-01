package microUI.core;

import microUI.effect.Hover;
import microUI.effect.Ripples;

public abstract class AbstractButton extends Component {
	public final Ripples ripples;
	public final Hover hover;
	
	public AbstractButton(float x, float y, float w, float h) {
		super(x, y, w, h);
		ripples = new Ripples(this);
		hover = new Hover(this);
		visible();
	}

	@Override
	public void update() {
		event.listen(this);
		app.pushStyle();
		fill.use(app);
		app.rect(x, y, w, h);
		hover.draw();
		ripples.draw();
		app.popStyle();
	
	}
	
}