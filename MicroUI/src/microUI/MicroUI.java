package microUI;

import processing.core.PApplet;

public class MicroUI {

	protected static PApplet app;
	
	public final static void setContext(final PApplet app) {
		MicroUI.app = app;
	}
	
	public final static PApplet getContext() {
		return app;
	}
	
}