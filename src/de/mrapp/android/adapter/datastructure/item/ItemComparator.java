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

import java.util.Comparator;

/**
 * A comparator, which allows to compare two items by comparing their data.
 * 
 * @param <DataType>
 *            The type of the items' data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public class ItemComparator<DataType> implements Comparator<Item<DataType>> {

	/**
	 * The comparator, which is used to compare the items' data.
	 */
	private final Comparator<DataType> comparator;

	/**
	 * Creates a new comparator, which allows to compare two items by comparing
	 * their data.
	 * 
	 * @param comparator
	 *            The comparator, which should be used to compare the items'
	 *            data, as an instance of the type {@link Comparator}. The
	 *            comparator may not be null
	 */
	public ItemComparator(final Comparator<DataType> comparator) {
		ensureNotNull(comparator, "The comparator may not be null");
		this.comparator = comparator;
	}

	@Override
	public final int compare(final Item<DataType> lhs, final Item<DataType> rhs) {
		return comparator.compare(lhs.getData(), rhs.getData());
	}

}