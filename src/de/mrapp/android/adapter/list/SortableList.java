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

import de.mrapp.android.adapter.SortingNotSupported;

/**
 * Defines the interface, which must be implemented by all classes, which should
 * allow to sort the items of a {@link ListAdapter} in an ascending or
 * descending order.
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface SortableList {

	/**
	 * Sorts the items of the adapter in an ascending order.
	 * 
	 * @throws SortingNotSupported
	 *             The exception, which is thrown, if the adapter's underlying
	 *             data does not implement the interface {@link Comparable}
	 */
	void sortAscending() throws SortingNotSupported;

	/**
	 * Sorts the items of the adapter in a descending order.
	 * 
	 * @throws SortingNotSupported
	 *             The exception, which is thrown, if the adapter's underlying
	 *             data does not implement the interface {@link Comparable}
	 */
	void sortDescending() throws SortingNotSupported;

}