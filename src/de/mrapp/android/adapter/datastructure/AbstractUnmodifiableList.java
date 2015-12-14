/*
 * AndroidAdapters Copyright 2014 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>. 
 */
package de.mrapp.android.adapter.datastructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

/**
 * An abstract base class for all implementations of the type {@link List},
 * which should throw exceptions of the type
 * {@link UnsupportedOperationException} when attempted to be modified. Such a
 * list is intended to encapsulate an {@link ArrayList} parameterized with a
 * subtype of {@link AbstractAdapterItem}.
 *
 * @param <DataType>
 *            The underlying type of the items of the encapsulated list
 * 
 * @author Michael Rapp
 * 
 * @since 0.1.0
 */
public abstract class AbstractUnmodifiableList<DataType> implements List<DataType> {

	/**
	 * The array list, which is encapsulated by the list.
	 */
	private final ArrayList<? extends AbstractAdapterItem<DataType>> encapsulatedList;

	/**
	 * Returns the array list, which is encapsulated by the list.
	 * 
	 * @return The array list, which is encapsulated by the list, as an instance
	 *         of the class {@link ArrayList}
	 */
	protected final ArrayList<? extends AbstractAdapterItem<DataType>> getEncapsulatedList() {
		return encapsulatedList;
	}

	/**
	 * Creates a new implementation of the type {@link List}, which throws
	 * exceptions of the type {@link UnsupportedOperationException} when
	 * attempted to be modified.
	 * 
	 * @param encapsulatedList
	 *            The array list, which should be encapsulated by the list, as
	 *            an instance of the class {@link ArrayList}. The array list may
	 *            not be null
	 */
	public AbstractUnmodifiableList(final ArrayList<? extends AbstractAdapterItem<DataType>> encapsulatedList) {
		ensureNotNull(encapsulatedList, "The encapsulated list may not be null");
		this.encapsulatedList = encapsulatedList;
	}

	@Override
	public final void add(final int location, final DataType object) {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

	@Override
	public final boolean add(final DataType object) {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

	@Override
	public final boolean addAll(final int location, final Collection<? extends DataType> collection) {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

	@Override
	public final boolean addAll(final Collection<? extends DataType> collection) {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

	@Override
	public final void clear() {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

	@Override
	public final DataType remove(final int location) {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

	@Override
	public final boolean remove(final Object object) {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

	@Override
	public final boolean removeAll(final Collection<?> collection) {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

	@Override
	public final boolean retainAll(final Collection<?> collection) {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

	@Override
	public final DataType set(final int location, final DataType object) {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

}