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

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An iterator, which allows to iterate the data of items, which are contained
 * by a list.
 * 
 * @param <DataType>
 *            The type of the items' data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public class ItemIterator<DataType> implements Iterator<DataType> {

	/**
	 * A list, which contains the items, whose data should be iterated.
	 */
	private List<Item<DataType>> items;

	/**
	 * The current index of the iterator.
	 */
	private int currentIndex;

	/**
	 * The item, whose data has been returned the last time when the next-method has
	 * been called.
	 */
	private Item<DataType> lastReturnedData;

	/**
	 * Creates a new iterator, which allows to iterate the data of items, which
	 * are contained by a list.
	 * 
	 * @param items
	 *            The list, which contains the items, whose data should be
	 *            iterated, as an instance of the type {@link List}. The list
	 *            may not be null
	 */
	public ItemIterator(final List<Item<DataType>> items) {
		ensureNotNull(items, "The items may not be null");
		this.items = items;
		this.currentIndex = -1;
		this.lastReturnedData = null;
	}

	@Override
	public final boolean hasNext() {
		return currentIndex < items.size() - 1;
	}

	@Override
	public final DataType next() {
		if (!hasNext()) {
			lastReturnedData = null;
			throw new NoSuchElementException();
		} else {
			currentIndex++;
			lastReturnedData = items.get(currentIndex);
			return lastReturnedData.getData();
		}

	}

	@Override
	public final void remove() {
		if (lastReturnedData == null) {
			throw new IllegalStateException();
		} else {
			items.remove(lastReturnedData);
			currentIndex--;
			lastReturnedData = null;
		}
	}

}