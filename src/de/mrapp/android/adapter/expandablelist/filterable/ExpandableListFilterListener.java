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
package de.mrapp.android.adapter.expandablelist.filterable;

import java.util.Collection;
import java.util.regex.Pattern;

import de.mrapp.android.adapter.Filter;

/**
 * Defines the interface, all listeners, which should be notified when the
 * underlying data of an {@link ExpandableListAdapter} has been filtered, must
 * implement.
 * 
 * @param <GroupType>
 *            The type of the underlying data of the observed adapter's group
 *            items
 * @param <ChildType>
 *            The type of the underlying data of the observed adapter's child
 *            items
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface ExpandableListFilterListener<GroupType, ChildType> {

	/**
	 * The method, which is invoked, when the adapter's group items have been
	 * filtered by using a regular expression.
	 * 
	 * @param regularExpression
	 *            The regular expression, which has been used, as an instance of
	 *            the class {@link Pattern}. The regular expression may not be
	 *            null
	 * @param filter
	 *            The filter, which has been used to apply the regular
	 *            expression on the single group items, as an instance of the
	 *            type {@link Filter} or null, if the group items'
	 *            implementations of the interface {@link Filterable} have been
	 *            used instead
	 * @param filteredGroups
	 *            A collection, which contains the adapter's filtered group
	 *            items, as an instance of the type {@link Collection} or an
	 *            empty collection, if the adapter does not contain any group
	 *            items
	 */
	void onApplyGroupFilter(Pattern regularExpression,
			Filter<GroupType> filter, Collection<GroupType> filteredGroups);

	/**
	 * The method, which is invoked, when a filter, which has been used to
	 * filter the adapter's group items, has been reseted.
	 * 
	 * @param regularExpression
	 *            The regular expression used by the filter, which has been
	 *            reseted, as an instance of the class {@link Pattern}. The
	 *            regular expression may not be null
	 * @param filteredGroups
	 *            A collection, which contains the adapter's filtered group
	 *            items, as an instance of the type {@link Collection} or an
	 *            empty collection, if the adapter does not contain any group
	 *            items
	 */
	void onResetGroupFilter(Pattern regularExpression,
			Collection<GroupType> filteredGroups);

	/**
	 * The method, which is invoked, when the adapter's child items have been
	 * filtered by using a regular expression.
	 * 
	 * @param regularExpression
	 *            The regular expression, which has been used, as an instance of
	 *            the class {@link Pattern}. The regular expression may not be
	 *            null
	 * @param filter
	 *            The filter, which has been used to apply the regular
	 *            expression on the single child items, as an instance of the
	 *            type {@link Filter} or null, if the child items'
	 *            implementations of the interface {@link Filterable} have been
	 *            used instead
	 * @param filteredGroups
	 *            A collection, which contains the adapter's filtered child
	 *            items, as an instance of the type {@link Collection} or an
	 *            empty collection, if the adapter does not contain any child
	 *            items
	 */
	void onApplyChildFilter(Pattern regularExpression,
			Filter<GroupType> filter, Collection<GroupType> filteredGroups);

	/**
	 * The method, which is invoked, when a filter, which has been used to
	 * filter the adapter's child items, has been reseted.
	 * 
	 * @param regularExpression
	 *            The regular expression used by the filter, which has been
	 *            reseted, as an instance of the class {@link Pattern}. The
	 *            regular expression may not be null
	 * @param filteredGroups
	 *            A collection, which contains the adapter's filtered child
	 *            items, as an instance of the type {@link Collection} or an
	 *            empty collection, if the adapter does not contain any group
	 *            items
	 */
	void onResetChildFilter(Pattern regularExpression,
			Collection<GroupType> filteredGroups);

}