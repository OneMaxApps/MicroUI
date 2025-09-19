package microui.core.style;

public final class Theme {
	private static AbstractTheme currentTheme = new ThemeWhite();
	
	public static void setTheme(AbstractTheme theme) {
		if(theme == null) {
			throw new NullPointerException("theme cannot be null");
		}
		currentTheme = theme;
	}
	
	public static AbstractTheme getTheme() {
		return currentTheme;
	}
}