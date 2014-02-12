package com.code83.ui.gui.commands;

import com.code83.ui.gui.themes.Theme;
import com.code83.ui.gui.themes.Theme.Themes;

/**
 * 
 *   SetWarmNuvola.java 760 2010-04-07 17:28:10Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: SetWarmNuvola.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class SetWarmNuvola implements Command {

	public void execute () {
		Theme theme = Theme.instance();
		theme.setThemeFactory(Themes.WARM_NUVOLA);
	}

}
