/**
 */
package org.eclipse.papyrus.gef4.example.library;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Library</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gef4.example.library.Library#getBooks <em>Books</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gef4.example.library.LibraryPackage#getLibrary()
 * @model
 * @generated
 */
public interface Library extends EObject {
	/**
	 * Returns the value of the '<em><b>Books</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.gef4.example.library.Book}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Books</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Books</em>' containment reference list.
	 * @see org.eclipse.papyrus.gef4.example.library.LibraryPackage#getLibrary_Books()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Book> getBooks();

} // Library
