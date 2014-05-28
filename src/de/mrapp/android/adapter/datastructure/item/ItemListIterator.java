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

import static de.mrapp.android.adapter.util.Condition.ensureAtLeast;
import static de.mrapp.android.adapter.util.Condition.ensureAtMaximum;
import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A list iterator, which allows to iterate the data the items, which are
 * contained by a list.
 * 
 * @param <DataType>
 *            The type of the items' data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
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
	 * The item, whose data has been returned the last time when the next- or
	 * previous-method has been called.
	 */
	private Item<DataType> lastReturnedItem;

	/**
	 * Creates a new list iterator, which allows to iterate the data of the
	 * item's which are contained by a list, starting at a specific index.
	 * 
	 * @param items
	 *            The list, which contains the items, whose data should be
	 *            iterated, as an instance of the type {@link List}. The list
	 *            may not be null
	 * @param index
	 *            The index, the iterator should start at, as an {@link Integer}
	 *            value. The index must be at least 0 and at maximum the size of
	 *            the given list - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 */
	public ItemListIterator(final List<Item<DataType>> items, final int index) {
		ensureNotNull(items, "The items may not be null");
		ensureAtLeast(index, -1, "The index must be at least -1");
		ensureAtMaximum(index, items.isEmpty() ? 0 : items.size(),
				"The index must be at maximum " + items.size());

		this.items = items;
		this.currentIndex = index;
		this.lastReturnedItem = null;
	}

	/**
	 * Creates a new list iterator, which allows to iterate the data of the
	 * item's which are contained by a list, starting at the beginning of the
	 * list.
	 * 
	 * @param items
	 *            The list, which contains the items, whose data should be
	 *            iterated, as an instance of the type {@link List}. The list
	 *            may not be null
	 */
	public ItemListIterator(final List<Item<DataType>> items) {
		this(items, -1);
	}

	@Override
	public final void add(final DataType item) {
		ensureNotNull(item, "The item may not be null");
		currentIndex++;
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
		} else {
			currentIndex++;
			lastReturnedItem = items.get(currentIndex);
			return lastReturnedItem.getData();
		}
	}

	@Override
	public final int nextIndex() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		} else {
			return currentIndex + 1;
		}
	}

	@Override
	public final DataType previous() {
		if (!hasPrevious()) {
			throw new NoSuchElementException();
		} else {
			currentIndex--;
			lastReturnedItem = items.get(currentIndex);
			return lastReturnedItem.getData();
		}
	}

	@Override
	public final int previousIndex() {
		if (!hasPrevious()) {
			throw new NoSuchElementException();
		} else {
			return currentIndex - 1;
		}
	}

	@Override
	public final void remove() {
		if (lastReturnedItem == null) {
			throw new IllegalStateException();
		} else {
			items.remove(lastReturnedItem);
			currentIndex--;
			lastReturnedItem = null;
		}
	}

	@Override
	public final void set(final DataType item) {
		ensureNotNull(item, "The item may not be null");

		if (lastReturnedItem == null) {
			throw new IllegalStateException();
		} else {
			int index = items.indexOf(lastReturnedItem);
			items.set(index, new Item<DataType>(item));
			lastReturnedItem = null;
		}
	}

}