package microui.component;

import microui.core.base.Component;
import microui.core.interfaces.Scrollable;
import processing.event.MouseEvent;

public class Dial extends Component implements Scrollable {

	public Dial(float x, float y, float w, float h) {
		super(x, y, w, h);
		setVisible(true);
		
	}
	
	public Dial() {
		this(ctx.width*.4f,ctx.height*.4f,ctx.width*.2f,ctx.height*.2f);
	}

	@Override
	protected void update() {
		ctx.rect(getX(), getY(), getWidth(), getHeight());
	}
	
	@Override
	public void mouseWheel(MouseEvent e) {

	}
}