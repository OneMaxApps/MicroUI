package microui;

import processing.core.PApplet;

// Status: STABLE - Do not modify
// Last Reviewed: 01.06.2025
public class MicroUI {
	protected static PApplet app;
	
	private static final int MAJOR = 2;
	private static final int MINOR = 0;
	private static final int PATCH = 0;
	
	private static final String VERSION = MAJOR+"."+MINOR+"."+PATCH;
	
	protected MicroUI() {}

	public static final void setContext(PApplet applet) {
		if(applet != null && MicroUI.app == null) {
			MicroUI.app = applet;
		}
	}
	
	public static final PApplet getContext() {
		return app;
	}
	
	public static final String getVersion() {
		return VERSION;
	}
}