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
		this(app.width*.4f,app.height*.4f,app.width*.2f,app.height*.2f);
	}

	@Override
	protected void update() {
		app.rect(x, y, w, h);
	}
	
	@Override
	public void mouseWheel(MouseEvent e) {

	}
}