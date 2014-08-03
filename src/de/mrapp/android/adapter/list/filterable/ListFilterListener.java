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
import java.util.regex.Pattern;

/**
 * Defines the interface, all listeners, which should be notified when the items
 * of a {@link ListAdapter} have been filtered, must implement.
 * 
 * @param <DataType>
 *            The type of the observed adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface ListFilterListener<DataType> {

	/**
	 * The method, which is invoked, when the adapter's items have been filtered
	 * by using a regular expression.
	 * 
	 * @param regularExpression
	 *            The regular expression, which has been used, as an instance of
	 *            the class {@link Pattern}. The regular expression may not be
	 *            null
	 * @param filteredItems
	 *            A collection, which contains the adapter's filtered items, as
	 *            an instance of the type {@link Collection} or an empty
	 *            collection, if the adapter does not contain any items
	 */
	void onApplyFilter(Pattern regularExpression,
			Collection<DataType> filteredItems);

	/**
	 * The method, which is invoked, when all applied filters have been reseted
	 * to restore the adapter's original items.
	 * 
	 * @param unfilteredItems
	 *            A collection, which contains the adapter's unfiltered items,
	 *            as an instance of the type {@link Collection} or an empty
	 *            collection, if the adapter does not contain any items
	 */
	void onResetFilter(Collection<DataType> unfilteredItems);

}