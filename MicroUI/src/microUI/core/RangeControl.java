package microUI.core;

import microUI.core.inteface.Scrollable;
import microUI.event.Scrolling;
import microUI.util.Value;
import processing.event.MouseEvent;

public abstract class RangeControl extends Component implements Scrollable {
	public final Value value;
	public final Scrolling scrolling;
	protected int orientation;
	
	public RangeControl(float x, float y, float w, float h) {
		super(x, y, w, h);
		value = new Value(0,100,0) {
			@Override
			public void action() {
				inTransforms();
			}
		};
		scrolling = new Scrolling(event);
		orientation = Constants.HORIZONTAL;
		visible();
		
	}
	
	public RangeControl() {
		this(app.width*.25f,app.height*.45f,app.width*.5f,app.height*.1f);
	}

	@Override
	public void update() {
		event.listen(this);
		
		app.pushStyle();
		fill.use();
		app.rect(x, y, w, h);
		app.popStyle();
		
		if(scrolling.isScrolling()) { value.append(scrolling.get()); }
	}
	
	@Override
	public void mouseWheel(MouseEvent e) {
		scrolling.init(e);
		if(event.inside()) {
			value.append(scrolling.get());
		}
	}
	
	public final void setOrientation(final int orientation) {
		if(orientation != Constants.HORIZONTAL && orientation != Constants.VERTICAL) { return; }
		if(this.orientation == orientation) { return; }
		final float w = getW(), h = getH();
		this.orientation = orientation;
		setW(h);
		setH(w);
		inTransforms();
	}
	
	public final void autoScroll() {
		value.append(scrolling.get());
	}
	
}