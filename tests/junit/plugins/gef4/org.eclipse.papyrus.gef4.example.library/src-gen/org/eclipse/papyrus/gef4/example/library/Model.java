/**
 */
package org.eclipse.papyrus.gef4.example.library;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gef4.example.library.Model#getLibrary <em>Library</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gef4.example.library.Model#getPersons <em>Persons</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gef4.example.library.LibraryPackage#getModel()
 * @model
 * @generated
 */
public interface Model extends EObject {
	/**
	 * Returns the value of the '<em><b>Library</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.gef4.example.library.Library}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Library</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Library</em>' containment reference list.
	 * @see org.eclipse.papyrus.gef4.example.library.LibraryPackage#getModel_Library()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Library> getLibrary();

	/**
	 * Returns the value of the '<em><b>Persons</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.gef4.example.library.Person}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persons</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persons</em>' containment reference list.
	 * @see org.eclipse.papyrus.gef4.example.library.LibraryPackage#getModel_Persons()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Person> getPersons();

} // Model
