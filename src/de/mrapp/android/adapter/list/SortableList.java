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
package de.mrapp.android.adapter.list;

import java.util.Comparator;

import de.mrapp.android.adapter.sorting.Order;

/**
 * Defines the interface, which must be implemented by all classes, which should
 * allow to sort the items of an adapter.
 * 
 * @author Michael Rapp
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 */
public interface SortableList<DataType> {

	/**
	 * Sorts the entries of the adapter in an ascending order.
	 */
	void sort();

	/**
	 * Sorts the entries of the adapter in a specific order.
	 * 
	 * @param order
	 *            The order, which should be used to sort the entries, as a
	 *            value of the enum {@link Order}. The order may either be
	 *            <code>ASCENDING</code> order <code>DESCENDING</code>
	 */
	void sort(Order order);

	/**
	 * Sorts the entries of the adapter by using a comparator in an ascending
	 * order.
	 * 
	 * @param comparator
	 *            The comparable, which should be used to sort the entries, as
	 *            an instance of the type {@link Comparator}. The comparator may
	 *            not be null
	 */
	void sort(Comparator<DataType> comparator);

	/**
	 * Sorts the entries of the adapter by using a comparator in a specific
	 * order.
	 * 
	 * @param comparator
	 *            The comparable, which should be used to sort the entries, as
	 *            an instance of the type {@link Comparator}. The comparator may
	 *            not be null
	 * @param order
	 *            The order, which should be used to sort the entries, as a
	 *            value of the enum {@link Order}. The order may either be
	 *            <code>ASCENDING</code> order <code>DESCENDING</code>
	 */
	void sort(Order order, Comparator<DataType> comparator);

}