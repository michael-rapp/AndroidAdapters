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

import java.util.regex.Pattern;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a
 * filterable list of arbitrary items, must implement. Such an adapter's purpose
 * is to provide the underlying data for visualization using a {@link ListView}
 * widget.
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
	 * Filters the adapter's items by using a specific regular expression. If
	 * the adapter's underlying data does not implement the interface
	 * {@link Filterable} a {@link FilteringNotSupportedException} will be
	 * thrown. This method can be called multiple times without resetting the
	 * filtering, which causes the filtered item to be filtered once more.
	 * 
	 * @param regularExpression
	 *            The regular expression, which should be used to filter the
	 *            items, as an instance of the class {@link Pattern}. The
	 *            regular expression may not be null
	 */
	void applyFilter(Pattern regularExpression);

	/**
	 * Filters the adapter's items by using a specific regular expression and a
	 * filter, which is used to apply the regular expression on the single
	 * items. This method can be called multiple times without resetting the
	 * filtering, which causes the filtered item to be filtered once more.
	 * 
	 * @param regularExpression
	 *            The regular expression, which should be used to filter items,
	 *            as an instance of the class {@link Pattern}. The regular
	 *            expression may not be null
	 * @param filter
	 *            The filter, which should be used to apply the given regular
	 *            expression on the adapter's items, as an instance of the type
	 *            {@link Filter}. The filter may not be null
	 */
	void applyFilter(Pattern regularExpression, Filter<DataType> filter);

	/**
	 * Resets all applied filters to restore the original items of the adapter.
	 */
	void resetFilter();

	/**
	 * Returns, whether at least one filter is applied on the adapter to filter
	 * its items, or not.
	 * 
	 * @return True, if at least one filter is applied on the adapter, false
	 *         otherwise.
	 */
	boolean isFilterApplied();

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