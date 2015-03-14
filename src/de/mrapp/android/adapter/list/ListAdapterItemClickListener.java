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

import de.mrapp.android.adapter.ListAdapter;

/**
 * Defines the interface, all listeners, which should be notified, when an item
 * of a {@link ListAdapter} has been clicked by the user, must implement.
 * 
 * @param <DataType>
 *            The type of the observed adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface ListAdapterItemClickListener<DataType> {

	/**
	 * The method, which is invoked, when an item of the adapter has been
	 * clicked by the user.
	 * 
	 * @param adapter
	 *            The observed adapter as an instance of the type
	 *            {@link ListAdapter}. The adapter may not be null
	 * @param item
	 *            The item, which has been clicked by the user, as an instance
	 *            of the generic type DataType. The item may not be null
	 * @param index
	 *            The index of the item, which has been clicked by the user, as
	 *            an {@link Integer} value
	 */
	void onItemClicked(ListAdapter<DataType> adapter, DataType item, int index);
	
}