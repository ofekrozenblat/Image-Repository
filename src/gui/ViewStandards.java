package gui;

import java.awt.Font;

public class ViewStandards {
	
	private static final String DEFAULT_FONT_NAME = "Calibri";
	
	public static Font labelSmallBoldFont() {
		return new Font(DEFAULT_FONT_NAME, Font.BOLD, 18);
	}
	
	public static Font labelSmallFont() {
		return new Font(DEFAULT_FONT_NAME, Font.PLAIN, 18);
	}
	
	public static Font labelMediumFont() {
		return new Font(DEFAULT_FONT_NAME, Font.PLAIN, 20);
	}
	
	public static Font labelLargeFont() {
		return new Font(DEFAULT_FONT_NAME, Font.BOLD, 25);
	}
	
}
