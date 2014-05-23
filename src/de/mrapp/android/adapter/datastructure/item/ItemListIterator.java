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

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A list iterator, which allows to iterate over the data of a list's items.
 * 
 * @param <DataType>
 *            The type of the item's data
 * 
 * @author Michael Rapp
 */
public class ItemListIterator<DataType> implements ListIterator<DataType> {

	/**
	 * A list, which contains the items, whose data should be iterated.
	 */
	private List<Item<DataType>> items;

	/**
	 * The current index of the iterator.
	 */
	private int currentIndex;

	/**
	 * Creates a new list iterator, which allows to iterate over the data of a
	 * list's items.
	 * 
	 * @param items
	 *            The list, which contains the items, whose data should be
	 *            iterated, as an instance of the type {@link List}. The list
	 *            may not be null
	 * @param index
	 *            The index, the iterator should start at, as an {@link Integer}
	 *            value. The index must be greater than 0 and less than the size
	 *            of the given list - 1
	 */
	public ItemListIterator(final List<Item<DataType>> items, final int index) {
		if (index < 0 || index >= items.size()) {
			throw new IndexOutOfBoundsException();
		}

		this.items = items;
		this.currentIndex = index;
	}

	/**
	 * Creates a new list iterator, which allows to iterate over the data of a
	 * list's items.
	 * 
	 * @param items
	 *            The list, which contains the items, whose data should be
	 *            iterated, as an instance of the type {@link List}. The list
	 *            may not be null
	 */
	public ItemListIterator(final List<Item<DataType>> items) {
		this(items, 0);
	}

	@Override
	public final void add(final DataType item) {
		items.add(currentIndex, new Item<DataType>(item));
	}

	@Override
	public final boolean hasNext() {
		return currentIndex < items.size() - 1;
	}

	@Override
	public final boolean hasPrevious() {
		return currentIndex > 0;
	}

	@Override
	public final DataType next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}

		currentIndex++;
		return items.get(currentIndex).getData();
	}

	@Override
	public final int nextIndex() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}

		return currentIndex + 1;
	}

	@Override
	public final DataType previous() {
		if (!hasPrevious()) {
			throw new NoSuchElementException();
		}

		return items.get(currentIndex - 1).getData();
	}

	@Override
	public final int previousIndex() {
		if (!hasPrevious()) {
			throw new NoSuchElementException();
		}

		return currentIndex - 1;
	}

	@Override
	public final void remove() {
		items.remove(currentIndex);
	}

	@Override
	public final void set(final DataType item) {
		items.set(currentIndex, new Item<DataType>(item));
	}

}