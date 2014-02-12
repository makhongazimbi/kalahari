package com.code83.examples;

import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.ThemeFactory;
import com.code83.ui.gui.themes.Theme.Themes;

public class ThemeExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ThemeFactory theme = Theme.instance().getThemeFactory();
		String iconA = theme.getIcons().ALERT_ICON_16x16();
		System.out.println("iconA: " + iconA);
		
		Theme.instance().setThemeFactory(Themes.COOL_TANGO);
		ThemeFactory theme2 = Theme.instance().getThemeFactory();
		String iconB = theme2.getIcons().ALERT_ICON_16x16();
		System.out.println("iconB: " + iconB);
	}

}
