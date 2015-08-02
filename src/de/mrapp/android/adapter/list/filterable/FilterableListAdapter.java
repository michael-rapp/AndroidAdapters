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

import de.mrapp.android.adapter.Filter;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a
 * filterable list of arbitrary items, must implement. Such an adapter's purpose
 * is to provide the underlying data for visualization using a
 * {@link AbsListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface FilterableListAdapter<DataType> {

	/**
	 * Filters the adapter's items by using a specific query, if no filter using
	 * the same query has been applied yet. If the adapter's underlying data
	 * does not implement the interface {@link Filterable} a
	 * {@link FilteringNotSupportedException} will be thrown. This method can be
	 * called multiple times without resetting the filtering, which causes the
	 * filtered item to be filtered once more.
	 * 
	 * @param query
	 *            The query, which should be used to filter the items, as a
	 *            {@link String}. The query may not be null
	 * @param flags
	 *            The flags, which should be used to filter the item, as an
	 *            {@link Integer} value, or 0, if no flags should be used
	 * @return True, if the filter has been applied, false otherwise
	 */
	boolean applyFilter(String query, int flags);

	/**
	 * Filters the adapter's items by using a specific query and a filter, which
	 * is used to apply the query on the single items, if no filter using the
	 * same query has been applied yet. This method can be called multiple times
	 * without resetting the filtering, which causes the filtered items to be
	 * filtered once more.
	 * 
	 * @param query
	 *            The query, which should be used to filter the items, as a
	 *            {@link String}. The query may not be null
	 * @param flags
	 *            The flags, which should be used to filter the items, as an
	 *            {@link Integer} value, or 0, if no flags should be used
	 * @param filter
	 *            The filter, which should be used to apply the given query on
	 *            the adapter's items, as an instance of the type {@link Filter}
	 *            . The filter may not be null
	 * @return True, if the filter has been applied, false otherwise
	 */
	boolean applyFilter(String query, int flags, Filter<DataType> filter);

	/**
	 * Resets the filter, which uses a specific query.
	 * 
	 * @param query
	 *            The query of the filter, which should be reseted, as a
	 *            {@link String}. The query may not be null
	 * @param flags
	 *            The flags of the filter, which should be reseted, as an
	 *            {@link Integer} value
	 * @return True, if the filter has been reseted, false otherwise
	 */
	boolean resetFilter(String query, int flags);

	/**
	 * Resets all applied filters.
	 */
	void resetAllFilters();

	/**
	 * Returns, whether at least one filter is currently applied on the adapter
	 * to filter its items, or not.
	 * 
	 * @return True, if at least one filter is currently applied on the adapter,
	 *         false otherwise.
	 */
	boolean isFiltered();

	/**
	 * Adds a new listener, which should be notified, when the adapter's
	 * underlying data has been filtered.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            class {@link ListFilterListener}. The listener may not be null
	 */
	void addFilterListener(ListFilterListener<DataType> listener);

	/**
	 * Removes a specific listener, which should not be notified, when the
	 * adapter's underlying data has been filtered, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            class {@link ListFilterListener}. The listener may not be null
	 */
	void removeFilterListener(ListFilterListener<DataType> listener);

}