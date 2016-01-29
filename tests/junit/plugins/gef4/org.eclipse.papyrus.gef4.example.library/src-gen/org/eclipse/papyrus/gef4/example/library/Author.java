/**
 */
package org.eclipse.papyrus.gef4.example.library;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Author</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gef4.example.library.Author#getWrittenBooks <em>Written Books</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gef4.example.library.LibraryPackage#getAuthor()
 * @model
 * @generated
 */
public interface Author extends Person {
	/**
	 * Returns the value of the '<em><b>Written Books</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.gef4.example.library.Book}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gef4.example.library.Book#getAuthors <em>Authors</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Written Books</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Written Books</em>' reference list.
	 * @see org.eclipse.papyrus.gef4.example.library.LibraryPackage#getAuthor_WrittenBooks()
	 * @see org.eclipse.papyrus.gef4.example.library.Book#getAuthors
	 * @model opposite="authors" ordered="false"
	 * @generated
	 */
	EList<Book> getWrittenBooks();

} // Author
