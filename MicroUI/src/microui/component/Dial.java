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
		this(cxt.width*.4f,cxt.height*.4f,cxt.width*.2f,cxt.height*.2f);
	}

	@Override
	protected void update() {
		cxt.rect(getX(), getY(), getWidth(), getHeight());
	}
	
	@Override
	public void mouseWheel(MouseEvent e) {

	}
}