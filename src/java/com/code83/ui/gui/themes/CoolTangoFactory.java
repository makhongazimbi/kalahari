package com.code83.ui.gui.themes;

/**
 * 
 *   CoolTangoFactory.java 692 2009-09-15 21:54:36Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com> <makho.ngazimbi@gmail.com>
 * @version $Id: CoolTangoFactory.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class CoolTangoFactory implements ThemeFactory {

	public Colors getColors() {
		return new CoolColors();
	}

	public Icons getIcons() {
		return new TangoIcons();
	}

}
