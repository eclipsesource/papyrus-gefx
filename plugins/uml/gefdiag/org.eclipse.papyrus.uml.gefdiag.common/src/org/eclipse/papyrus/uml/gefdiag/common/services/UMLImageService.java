package org.eclipse.papyrus.uml.gefdiag.common.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.common.adapt.IAdaptable;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.utils.NotationUtil;
import org.eclipse.papyrus.gef4.parts.LabelContentPart;
import org.eclipse.papyrus.gef4.services.ImageService;

public class UMLImageService extends IAdaptable.Bound.Impl<LabelContentPart<View>> implements ImageService {

	private static final String imagePath = "platform:/plugin/org.eclipse.uml2.uml.edit/icons/full/obj16";

	/**
	 * @see org.eclipse.papyrus.gef4.services.ImageService#getImagePath()
	 *
	 * @return
	 */
	@Override
	public String getImagePath() {
		final EObject semanticElement = getContent().getElement();
		if (semanticElement == null) {
			return null;
		}

		if (NotationUtil.showElementIcon(getContent())) {
			return imagePath + "/" + semanticElement.eClass().getName() + ".gif"; //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			return null;
		}
	}

	protected View getContent() {
		return getAdaptable().getContent();
	}

}
