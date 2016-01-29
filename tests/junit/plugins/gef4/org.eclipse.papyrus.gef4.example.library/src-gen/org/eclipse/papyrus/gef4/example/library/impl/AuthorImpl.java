/**
 */
package org.eclipse.papyrus.gef4.example.library.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.papyrus.gef4.example.library.Author;
import org.eclipse.papyrus.gef4.example.library.Book;
import org.eclipse.papyrus.gef4.example.library.LibraryPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Author</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gef4.example.library.impl.AuthorImpl#getWrittenBooks <em>Written Books</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AuthorImpl extends PersonImpl implements Author {
	/**
	 * The cached value of the '{@link #getWrittenBooks() <em>Written Books</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWrittenBooks()
	 * @generated
	 * @ordered
	 */
	protected EList<Book> writtenBooks;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AuthorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryPackage.Literals.AUTHOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Book> getWrittenBooks() {
		if (writtenBooks == null) {
			writtenBooks = new EObjectWithInverseResolvingEList.ManyInverse<Book>(Book.class, this, LibraryPackage.AUTHOR__WRITTEN_BOOKS, LibraryPackage.BOOK__AUTHORS);
		}
		return writtenBooks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryPackage.AUTHOR__WRITTEN_BOOKS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getWrittenBooks()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryPackage.AUTHOR__WRITTEN_BOOKS:
				return ((InternalEList<?>)getWrittenBooks()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryPackage.AUTHOR__WRITTEN_BOOKS:
				return getWrittenBooks();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LibraryPackage.AUTHOR__WRITTEN_BOOKS:
				getWrittenBooks().clear();
				getWrittenBooks().addAll((Collection<? extends Book>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case LibraryPackage.AUTHOR__WRITTEN_BOOKS:
				getWrittenBooks().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case LibraryPackage.AUTHOR__WRITTEN_BOOKS:
				return writtenBooks != null && !writtenBooks.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AuthorImpl
