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
package de.mrapp.android.adapter.datastructure.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import de.mrapp.android.adapter.datastructure.AbstractUnmodifiableList;

/**
 * An implementation of the type {@link List}, which throws exceptions of the
 * type {@link UnsupportedOperationException} when attempted to be modified.
 * Such a list encapsulates an other list, which contains items of the type
 * {@link Item}.
 * 
 * @param <DataType>
 *            The type of the underlying data of the list's items
 * 
 * @author Michael Rapp
 *
 * @since 0.1.0
 */
public class UnmodifiableItemList<DataType> extends AbstractUnmodifiableList<DataType, Item<DataType>> {

	/**
	 * Creates a new implementation of the type {@link List}, which throws
	 * exceptions of the type {@link UnsupportedOperationException} when
	 * attempted to be modified.
	 * 
	 * @param encapsulatedList
	 *            The list, which should be encapsulated by the list, as an
	 *            instance of the type {@link List}. The list may not be null
	 */
	public UnmodifiableItemList(final ArrayList<Item<DataType>> encapsulatedList) {
		super(encapsulatedList);
	}

	@Override
	public final Iterator<DataType> iterator() {
		return new ItemIterator<DataType>(getEncapsulatedList(), null);
	}

	@Override
	public final ListIterator<DataType> listIterator() {
		return new ItemListIterator<>(getEncapsulatedList(), null);
	}

	@Override
	public final ListIterator<DataType> listIterator(final int location) {
		return new ItemListIterator<>(getEncapsulatedList(), null, location);
	}

	@Override
	public final DataType get(final int location) {
		return getEncapsulatedList().get(location).getData();
	}

}