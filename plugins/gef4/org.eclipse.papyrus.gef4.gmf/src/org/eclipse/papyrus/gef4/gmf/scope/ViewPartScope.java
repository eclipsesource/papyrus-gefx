package org.eclipse.papyrus.gef4.gmf.scope;

import javax.inject.Singleton;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.scopes.PartScope;

import com.google.inject.Key;

@Singleton
public class ViewPartScope extends PartScope {

	@Override
	public void enter(Object scope) {
		super.enter(scope);
		if (scope instanceof View) {
			register(scope, Key.get(View.class), scope);
		}
	}

}
