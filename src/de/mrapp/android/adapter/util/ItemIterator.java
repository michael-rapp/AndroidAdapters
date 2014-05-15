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
package de.mrapp.android.adapter.util;

import java.util.Iterator;
import java.util.List;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

/**
 * An iterator, which allows to iterate over the data of a list's items.
 * 
 * @param <DataType>
 *            The type of the item's data
 * 
 * @author Michael Rapp
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
	 * Creates a new iterator, which allows to iterate over the data of a list's
	 * items.
	 * 
	 * @param items
	 *            The list, which contains the items, whose data should be
	 *            iterated, as an instance of the type {@link List}. The list
	 *            may not be null
	 */
	public ItemIterator(final List<Item<DataType>> items) {
		ensureNotNull(items, "The items may not be null");
		this.items = items;
		this.currentIndex = 0;
	}

	@Override
	public final boolean hasNext() {
		return currentIndex < items.size() - 1;
	}

	@Override
	public final DataType next() {
		currentIndex++;
		return items.get(currentIndex).getData();
	}

	@Override
	public final void remove() {
		items.remove(currentIndex);
	}

}