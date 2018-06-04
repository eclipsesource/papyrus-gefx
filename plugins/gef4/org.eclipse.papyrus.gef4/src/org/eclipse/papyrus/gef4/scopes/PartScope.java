/*****************************************************************************
 * Copyright (c) 2018 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (EclipseSource) cletavernier@eclipsesource.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gef4.scopes;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.inject.Singleton;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;

@Singleton
public class PartScope implements Scope {

	final Map<Object, Map<Key<?>, Object>> scopedValues = new HashMap<>();
	Stack<Object> scopes = new Stack<>();

	public void enter(Object scope) {
		scopedValues.put(scope, new HashMap<>());
		scopes.push(scope);
	}

	public void exit(Object scope) {
		scopedValues.remove(scope);
		scopes.pop();
	}

	protected void register(Key<?> key, Object value) {
		if (scopes.isEmpty()) {
			throw new IllegalStateException("Part scope is inactive");
		}
		register(scopes.peek(), key, value);
	}

	protected void register(Object scope, Key<?> key, Object value) {
		if (!scopedValues.containsKey(scope)) {
			throw new IllegalStateException("Part scope is inactive. Requested scope: " + scope);
		}
		scopedValues.get(scope).put(key, value);
	}

	@Override
	public <T> Provider<T> scope(Key<T> key, Provider<T> unscoped) {
		return () -> {
			if (this.scopes.isEmpty()) {
				return unscoped.get();
			}

			Object currentScope = scopes.peek();
			Map<Key<?>, Object> scopeValues = scopedValues.get(currentScope);
			if (!scopeValues.containsKey(key)) {
				scopeValues.put(key, unscoped.get());
			}
			return (T) scopeValues.get(key);
		};

	}

}
