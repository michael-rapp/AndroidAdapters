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
package de.mrapp.android.adapter.list.filterable;

import java.util.Collection;

import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.ListAdapter;

/**
 * Defines the interface, all listeners, which should be notified when the
 * underlying data of a {@link ListAdapter} has been filtered, must implement.
 * 
 * @param <DataType>
 *            The type of the observed adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 0.1.0
 */
public interface ListFilterListener<DataType> {

	/**
	 * The method, which is invoked, when the adapter's items have been filtered
	 * by using a query.
	 * 
	 * @param adapter
	 *            The observed adapters as an instance of the type
	 *            {@link ListAdapter}. The adapter may not be null
	 * @param query
	 *            The query, which has been used, as a {@link String}. The query
	 *            may not be null
	 * @param flags
	 *            The flags, which have been used, as an {@link Integer} value,
	 *            or 0, if no flags have been used
	 * @param filter
	 *            The filter, which has been used to apply the query on the
	 *            single items, as an instance of the type {@link Filter} or
	 *            null, if the items' implementations of the interface
	 *            {@link Filterable} have been used instead
	 * @param filteredItems
	 *            A collection, which contains the adapter's filtered items, as
	 *            an instance of the type {@link Collection} or an empty
	 *            collection, if the adapter does not contain any items
	 */
	void onApplyFilter(ListAdapter<DataType> adapter, String query, int flags,
			Filter<DataType> filter, Collection<DataType> filteredItems);

	/**
	 * The method, which is invoked, when a filter has been reseted.
	 * 
	 * @param adapter
	 *            The observed adapters as an instance of the type
	 *            {@link ListAdapter}. The adapter may not be null
	 * @param query
	 *            The query used by the filter, which has been reseted, as a
	 *            {@link String}. The query may not be null
	 * @param flags
	 *            The flags used by the filter, which has been reseted, as an
	 *            {@link Integer} value or 0, if no flags have been used by the
	 *            filter
	 * @param filteredItems
	 *            A collection, which contains the adapter's filtered items, as
	 *            an instance of the type {@link Collection} or an empty
	 *            collection, if the adapter does not contain any items
	 */
	void onResetFilter(ListAdapter<DataType> adapter, String query, int flags,
			Collection<DataType> filteredItems);

}