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
package org.eclipse.papyrus.gef4.example.library.parsers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.papyrus.gef4.example.library.Book;
import org.eclipse.papyrus.gef4.example.library.Library;
import org.eclipse.papyrus.gef4.example.library.LibraryPackage;
import org.eclipse.papyrus.gef4.example.library.Person;
import org.eclipse.papyrus.gef4.example.library.util.LibrarySwitch;

public class LibraryParserProvider extends AbstractProvider implements IParserProvider {

	@Override
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			GetParserOperation parserOp = (GetParserOperation) operation;
			EObject semanticElement = parserOp.getHint().getAdapter(EObject.class);
			return semanticElement != null && semanticElement.eClass().getEPackage() == LibraryPackage.eINSTANCE;
		}
		return false;
	}

	@Override
	public IParser getParser(IAdaptable hint) {
		EObject semanticElement = hint.getAdapter(EObject.class);
		return new LibrarySwitch<LibraryParser>() {
			@Override
			public LibraryParser casePerson(Person object) {
				return new LibraryParser() {
					@Override
					public String getPrintString(IAdaptable element, int flags) {
						return "Person";
					}
				};
			}

			@Override
			public LibraryParser caseLibrary(Library object) {
				return new LibraryParser() {
					@Override
					public String getPrintString(IAdaptable element, int flags) {
						return "Library";
					}
				};
			}

			@Override
			public LibraryParser caseBook(Book object) {
				return new LibraryParser() {
					@Override
					public String getPrintString(IAdaptable element, int flags) {
						return "Book";
					}
				};
			}
		}.doSwitch(semanticElement);
	}

	private abstract static class LibraryParser implements IParser {

		@Override
		public String getEditString(IAdaptable element, int flags) {
			return null;
		}

		@Override
		public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
			return ParserEditStatus.UNEDITABLE_STATUS;
		}

		@Override
		public ICommand getParseCommand(IAdaptable element, String newString, int flags) {
			return null;
		}

		@Override
		public boolean isAffectingEvent(Object event, int flags) {
			return false;
		}

		@Override
		public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
			return null;
		}

	}

}
