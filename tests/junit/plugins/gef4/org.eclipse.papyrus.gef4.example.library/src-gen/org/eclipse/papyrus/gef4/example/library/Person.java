/**
 */
package org.eclipse.papyrus.gef4.example.library;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Person</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gef4.example.library.Person#getBorrows <em>Borrows</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gef4.example.library.LibraryPackage#getPerson()
 * @model
 * @generated
 */
public interface Person extends EObject {

	/**
	 * Returns the value of the '<em><b>Borrows</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.gef4.example.library.Book}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Borrows</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Borrows</em>' reference list.
	 * @see org.eclipse.papyrus.gef4.example.library.LibraryPackage#getPerson_Borrows()
	 * @model ordered="false"
	 * @generated
	 */
	EList<Book> getBorrows();
} // Person
