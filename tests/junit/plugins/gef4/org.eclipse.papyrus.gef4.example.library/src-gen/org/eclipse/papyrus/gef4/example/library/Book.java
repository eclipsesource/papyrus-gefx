/**
 */
package org.eclipse.papyrus.gef4.example.library;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Book</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gef4.example.library.Book#getAuthors <em>Authors</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gef4.example.library.LibraryPackage#getBook()
 * @model
 * @generated
 */
public interface Book extends EObject {
	/**
	 * Returns the value of the '<em><b>Authors</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.gef4.example.library.Author}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gef4.example.library.Author#getWrittenBooks <em>Written Books</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authors</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authors</em>' reference list.
	 * @see org.eclipse.papyrus.gef4.example.library.LibraryPackage#getBook_Authors()
	 * @see org.eclipse.papyrus.gef4.example.library.Author#getWrittenBooks
	 * @model opposite="writtenBooks" required="true" ordered="false"
	 * @generated
	 */
	EList<Author> getAuthors();

} // Book
