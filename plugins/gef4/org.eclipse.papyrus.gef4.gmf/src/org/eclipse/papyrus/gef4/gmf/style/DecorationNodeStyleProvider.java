package org.eclipse.papyrus.gef4.gmf.style;

import org.eclipse.gmf.runtime.notation.DrawerStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.TitleStyle;
import org.eclipse.papyrus.gef4.services.style.CompartmentStyleService;

public class DecorationNodeStyleProvider extends AbstractNotationStyleService implements CompartmentStyleService {

	/**
	 * Whether the title of the compartment should be displayed
	 *
	 * Defaults to false
	 *
	 * @see {@link #getTitle()}
	 *
	 * @return
	 */
	@Override
	public boolean isShowTitle() {
		TitleStyle titleStyle = (TitleStyle) getView().getStyle(NotationPackage.eINSTANCE.getTitleStyle());
		if (titleStyle == null) {
			return false;
		}

		return titleStyle.isShowTitle();
	}

	@Override
	public boolean isCollapsed() {
		final DrawerStyle style = (DrawerStyle) getView().getStyle(NotationPackage.eINSTANCE.getDrawerStyle());
		return style == null ? false : style.isCollapsed();
	}

}
