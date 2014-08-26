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

import java.util.regex.Pattern;

import de.mrapp.android.adapter.Filter;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a
 * filterable list of arbitrary group and child items, must implement. Such an
 * adapter's purpose is to provide the underlying data for visualization using a
 * {@link ExpandableListView} widget.
 * 
 * @param <GroupType>
 *            The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *            The type of the underlying data of the adapter's child items
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface FilterableExpandableListAdapter<GroupType, ChildType> {

	/**
	 * Filters the adapter's group items by using a specific regular expression,
	 * if no filter using the same regular expression has been applied yet. If
	 * the underlying data of the adapter's group items does not implement the
	 * interface {@link Filterable} a {@link FilteringNotSupportedException}
	 * will be thrown. This method can be called multiple times without
	 * resetting the filtering, which causes the filtered group item to be
	 * filtered once more.
	 * 
	 * @param regularExpression
	 *            The regular expression, which should be used to filter the
	 *            group items, as an instance of the class {@link Pattern}. The
	 *            regular expression may not be null
	 * @return True, if the filter has been applied, false otherwise
	 */
	boolean applyFilterOnGroups(Pattern regularExpression);

	/**
	 * Filters the adapter's group items by using a specific regular expression
	 * and a filter, which is used to apply the regular expression on the single
	 * group items, if no filter using the same regular expression has been
	 * applied yet. This method can be called multiple times without resetting
	 * the filtering, which causes the filtered group items to be filtered once
	 * more.
	 * 
	 * @param regularExpression
	 *            The regular expression, which should be used to filter the
	 *            group items, as an instance of the class {@link Pattern}. The
	 *            regular expression may not be null
	 * @param filter
	 *            The filter, which should be used to apply the given regular
	 *            expression on the adapter's group items, as an instance of the
	 *            type {@link Filter}. The filter may not be null
	 * @return True, if the filter has been applied, false otherwise
	 */
	boolean applyFilterOnGroups(Pattern regularExpression,
			Filter<GroupType> filter);

	/**
	 * Resets the filter, which has been applied on the adapter to filter its
	 * group items, which uses a specific regular expression.
	 * 
	 * @param regularExpression
	 *            The regular expression of the filter, which should be reseted,
	 *            as an instance of the class {@link Pattern}. The regular
	 *            expression may not be null
	 * @return True, if the filter has been reseted, false otherwise
	 */
	boolean resetGroupFilter(Pattern regularExpression);

	/**
	 * Resets all applied filters, which have been applied on the adapter's
	 * group items.
	 */
	void resetAllGroupFilters();

	/**
	 * Returns, whether at least one filter is currently applied on the adapter
	 * to filter its group items, or not.
	 * 
	 * @return True, if at least one filter is currently applied on the adapter
	 *         to filter its group items, false otherwise.
	 */
	boolean areGroupsFiltered();

	/**
	 * Returns, whether a filter, which uses a specific regular expression, is
	 * currently applied on the adapter to filter its group items, or not.
	 * 
	 * @param regularExpression
	 *            The regular expression of the filter, which should be checked,
	 *            as an instance of the class {@link Pattern}. The regular
	 *            expression may not be null
	 * @return True, if a filter, which uses the given regular expression, is
	 *         currently applied on the adapter to filter its group items, false
	 *         otherwise
	 */
	boolean isFilterAppliedOnGroups(Pattern regularExpression);

	/**
	 * Returns the number of filters, which are currently applied on the adapter
	 * to filter its group items.
	 * 
	 * @return The number of filters, which are currently applied on the adapter
	 *         to filter its group items, as an {@link Integer} value
	 */
	int getNumberOfAppliedGroupFilters();

	/**
	 * Filters the adapter's child items by using a specific regular expression,
	 * if no filter using the same regular expression has been applied yet. If
	 * the underlying data of the adapter's child items does not implement the
	 * interface {@link Filterable} a {@link FilteringNotSupportedException}
	 * will be thrown. This method can be called multiple times without
	 * resetting the filtering, which causes the filtered child item to be
	 * filtered once more.
	 * 
	 * @param regularExpression
	 *            The regular expression, which should be used to filter the
	 *            child items, as an instance of the class {@link Pattern}. The
	 *            regular expression may not be null
	 * @return True, if the filter has been applied, false otherwise
	 */
	boolean applyFilterOnChildren(Pattern regularExpression);

	/**
	 * Filters the adapter's child items by using a specific regular expression
	 * and a filter, which is used to apply the regular expression on the single
	 * child items, if no filter using the same regular expression has been
	 * applied yet. This method can be called multiple times without resetting
	 * the filtering, which causes the filtered child items to be filtered once
	 * more.
	 * 
	 * @param regularExpression
	 *            The regular expression, which should be used to filter the
	 *            child items, as an instance of the class {@link Pattern}. The
	 *            regular expression may not be null
	 * @param filter
	 *            The filter, which should be used to apply the given regular
	 *            expression on the adapter's child items, as an instance of the
	 *            type {@link Filter}. The filter may not be null
	 * @return True, if the filter has been applied, false otherwise
	 */
	boolean applyFilterOnChildren(Pattern regularExpression,
			Filter<ChildType> filter);

	/**
	 * Resets the filter, which has been applied on the adapter to filter its
	 * child items, which uses a specific regular expression.
	 * 
	 * @param regularExpression
	 *            The regular expression of the filter, which should be reseted,
	 *            as an instance of the class {@link Pattern}. The regular
	 *            expression may not be null
	 * @return True, if the filter has been reseted, false otherwise
	 */
	boolean resetChildFilter(Pattern regularExpression);

	/**
	 * Resets all applied filters, which have been applied on the adapter's
	 * child items.
	 */
	void resetAllChildFilters();

	/**
	 * Returns, whether at least one filter is currently applied on the adapter
	 * to filter its child items, or not.
	 * 
	 * @return True, if at least one filter is currently applied on the adapter
	 *         to filter its child items, false otherwise.
	 */
	boolean areChildrenFiltered();

	/**
	 * Returns, whether a filter, which uses a specific regular expression, is
	 * currently applied on the adapter to filter its child items, or not.
	 * 
	 * @param regularExpression
	 *            The regular expression of the filter, which should be checked,
	 *            as an instance of the class {@link Pattern}. The regular
	 *            expression may not be null
	 * @return True, if a filter, which uses the given regular expression, is
	 *         currently applied on the adapter to filter its child items, false
	 *         otherwise
	 */
	boolean isFilterAppliedOnChildren(Pattern regularExpression);

	/**
	 * Returns the number of filters, which are currently applied on the adapter
	 * to filter its child items.
	 * 
	 * @return The number of filters, which are currently applied on the adapter
	 *         to filter its child items, as an {@link Integer} value
	 */
	int getNumberOfAppliedChildFilters();

	/**
	 * Adds a new listener, which should be notified, when the adapter's
	 * underlying data has been filtered.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            class {@link ExpandableListFilterListener}. The listener may
	 *            not be null
	 */
	void addFilterListener(
			ExpandableListFilterListener<GroupType, ChildType> listener);

	/**
	 * Removes a specific listener, which should not be notified, when the
	 * adapter's underlying data has been filtered, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            class {@link ExpandableListFilterListener}. The listener may
	 *            not be null
	 */
	void removeFilterListener(
			ExpandableListFilterListener<GroupType, ChildType> listener);

}