package microui;

import processing.core.PApplet;

// Status: STABLE - Do not modify
// Last Reviewed: 12.09.2025
public final class MicroUI {
	private static PApplet ctx;
	
	private static final int MAJOR = 2;
	private static final int MINOR = 0;
	private static final int PATCH = 0;
	
	private static final String VERSION = MAJOR+"."+MINOR+"."+PATCH;
	
	private static boolean isDebugModeEnabled;
	
	private MicroUI() {}

	public static final void setContext(PApplet context) {
		if(context != null && MicroUI.ctx == null) {
			MicroUI.ctx = context;
		}
	}
	
	public static final PApplet getContext() {
		return ctx;
	}
	
	public static final String getVersion() {
		return VERSION;
	}

	public static final boolean isDebugModeEnabled() {
		return isDebugModeEnabled;
	}

	public static final void setDebugModeEnabled(boolean isDebugModeEnabled) {	
		MicroUI.isDebugModeEnabled = isDebugModeEnabled;
	}
}