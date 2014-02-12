package com.code83.ui.gui.themes;

import com.code83.data.accessLayer.DataAccessObject;

/**
 * 
 *   Theme.java 760 2010-04-07 17:28:10Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: Theme.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
final public class Theme {
	
	private static Theme theme = null;
	private ThemeFactory themeFactory = new CoolNuvolaFactory();
	private DataAccessObject dao = new DataAccessObject();
	
	private Theme () {
		String themeName = dao.getSettingsDao().getTheme();
		for (Themes theme : Themes.values()) {
			if (themeName.equals(theme.toString())) {
				if (themeName.equals("COOL_NUVOLA")) {
					themeFactory = new CoolNuvolaFactory();
				}  else if (themeName.equals("COOL_TANGO")) {
					themeFactory = new CoolTangoFactory();
				} else if (themeName.equals("WARM_NUVOLA")) {
					themeFactory = new WarmNuvolaFactory();
				} else if (themeName.equals("WARM_TANGO")) {
					themeFactory = new WarmTangoFactory();
				}
				setThemeFactory(theme);
				break;
			}
		}
	}
	
	public static Theme instance () {
		if (theme==null) {
			theme = new Theme();
		}
		return theme;
	}
	
	public void setThemeFactory (Themes name) {
		dao.getSettingsDao().setTheme(name.toString());
	}
	
	public ThemeFactory getThemeFactory () {
		return themeFactory;
	}
	
	public enum Themes {COOL_NUVOLA, COOL_TANGO, WARM_NUVOLA, WARM_TANGO}
}
