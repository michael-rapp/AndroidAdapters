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
 * @since 0.1.0
 */
public interface FilterableExpandableListAdapter<GroupType, ChildType> {

	/**
	 * Returns, whether at least one filter is currently applied on the adapter
	 * to filter its group or child items, or not.
	 * 
	 * @return True, if at least one filter is currently applied on the adapter
	 *         to filter its group or child items, false otherwise.
	 */
	boolean isFiltered();

	/**
	 * Filters the adapter's group items by using a specific query, if no filter
	 * using the same query has been applied yet. If the underlying data of the
	 * adapter's group items does not implement the interface {@link Filterable}
	 * a {@link FilteringNotSupportedException} will be thrown. This method can
	 * be called multiple times without resetting the filtering, which causes
	 * the filtered group item to be filtered once more.
	 * 
	 * @param query
	 *            The query, which should be used to filter the group items, as
	 *            a {@link String}. The query may not be null
	 * @param flags
	 *            The flags, which should be used to filter the group items, as
	 *            an {@link Integer} value or 0, if no flags should be used
	 * @return True, if the filter has been applied, false otherwise
	 */
	boolean applyGroupFilter(String query, int flags);

	/**
	 * Filters the adapter's group items by using a specific query and a filter,
	 * which is used to apply the query on the single group items, if no filter
	 * using the same query has been applied yet. This method can be called
	 * multiple times without resetting the filtering, which causes the filtered
	 * group items to be filtered once more.
	 * 
	 * @param query
	 *            The query, which should be used to filter the group items, as
	 *            a {@link String}. The query may not be null
	 * @param flags
	 *            The flags, which should be used to filter the group items, as
	 *            an {@link Integer} value or 0, if no flags should be used
	 * @param filter
	 *            The filter, which should be used to apply the given query on
	 *            the adapter's group items, as an instance of the type
	 *            {@link Filter}. The filter may not be null
	 * @return True, if the filter has been applied, false otherwise
	 */
	boolean applyGroupFilter(String query, int flags, Filter<GroupType> filter);

	/**
	 * Resets the filter, which has been applied on the adapter to filter its
	 * group items, which uses a specific query.
	 * 
	 * @param query
	 *            The query of the filter, which should be reseted, as a
	 *            {@link String}. The query may not be null
	 * @param flags
	 *            The flags of the filter, which should be reseted, as an
	 *            {@link Integer} value
	 * @return True, if the filter has been reseted, false otherwise
	 */
	boolean resetGroupFilter(String query, int flags);

	/**
	 * Resets all applied filters, which have been applied on the adapter's
	 * group items.
	 */
	void resetAllGroupFilters();

	/**
	 * Returns, whether a filter, which uses a specific query, is currently
	 * applied on the adapter's group items.
	 * 
	 * @param query
	 *            The query of the filter, which should be checked, as a
	 *            {@link String}. The query may not be null
	 * @param flags
	 *            The flags of the filter, which should be checked, as an
	 *            {@link Integer} value
	 * @return True, if a filter, which uses the given query, is currently
	 *         applied on the adapter's group items, false otherwise
	 */
	boolean isGroupFilterApplied(String query, int flags);

	/**
	 * Returns, whether at least one filter is currently applied on the adapter
	 * to filter its group items, or not.
	 * 
	 * @return True, if at least one filter is currently applied on the adapter
	 *         to filter its group items, false otherwise.
	 */
	boolean areGroupsFiltered();

	/**
	 * Filters the adapter's child items, regardless of the group they belong
	 * to, by using a specific query, if no filter using the same query has been
	 * applied yet. If the underlying data of the adapter's child items does not
	 * implement the interface {@link Filterable} a
	 * {@link FilteringNotSupportedException} will be thrown. This method can be
	 * called multiple times without resetting the filtering, which causes the
	 * filtered child item to be filtered once more.
	 * 
	 * @param query
	 *            The query, which should be used to filter the child items, as
	 *            a {@link String}. The query may not be null
	 * @param flags
	 *            The flags, which should be used to filter the child items, as
	 *            an {@link Integer} value or 0, if no flags should be used
	 * @return True, if the filter has been applied on all groups, false
	 *         otherwise
	 */
	boolean applyChildFilter(String query, int flags);

	/**
	 * Filters the adapter's child items, regardless of the group they belong
	 * to, by using a specific query, if no filter using the same query has been
	 * applied yet. If the underlying data of the adapter's child items does not
	 * implement the interface {@link Filterable} a
	 * {@link FilteringNotSupportedException} will be thrown. This method can be
	 * called multiple times without resetting the filtering, which causes the
	 * filtered child item to be filtered once more.
	 * 
	 * @param filterEmptyGroups
	 *            True, if groups, which become empty by filtering their
	 *            children, should be filtered as well, false otherwise
	 * @param query
	 *            The query, which should be used to filter the child items, as
	 *            a {@link String}. The query may not be null
	 * @param flags
	 *            The flags, which should be used to filter the child items, as
	 *            an {@link Integer} value or 0, if no flags should be used
	 * @return True, if the filter has been applied on all groups, false
	 *         otherwise
	 */
	boolean applyChildFilter(boolean filterEmptyGroups, String query, int flags);

	/**
	 * Filters the child items of a specific group, by using a specific query,
	 * if no filter using the same query has been applied yet. If the underlying
	 * data of the adapter's child items does not implement the interface
	 * {@link Filterable} a {@link FilteringNotSupportedException} will be
	 * thrown. This method can be called multiple times without resetting the
	 * filtering, which causes the filtered child item to be filtered once more.
	 * 
	 * @param group
	 *            The group, the child items, which should be filtered, belong
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @param query
	 *            The query, which should be used to filter the child items, as
	 *            a {@link String}. The query may not be null
	 * @param flags
	 *            The flags, which should be used to filter the child items, as
	 *            an {@link Integer} value or 0, if no flags should be used
	 * @return True, if the filter has been applied, false otherwise
	 */
	boolean applyChildFilter(GroupType group, String query, int flags);

	/**
	 * Filters the child items of a specific group, by using a specific query,
	 * if no filter using the same query has been applied yet. If the underlying
	 * data of the adapter's child items does not implement the interface
	 * {@link Filterable} a {@link FilteringNotSupportedException} will be
	 * thrown. This method can be called multiple times without resetting the
	 * filtering, which causes the filtered child item to be filtered once more.
	 * 
	 * @param filterEmptyGroup
	 *            True, if the given group should be filtered as well, if it
	 *            becomes empty by filtering its children, false otherwise
	 * @param group
	 *            The group, the child items, which should be filtered, belong
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @param query
	 *            The query, which should be used to filter the child items, as
	 *            a {@link String}. The query may not be null
	 * @param flags
	 *            The flags, which should be used to filter the child items, as
	 *            an {@link Integer} value or 0, if no flags should be used
	 * @return True, if the filter has been applied, false otherwise
	 */
	boolean applyChildFilter(boolean filterEmptyGroup, GroupType group, String query, int flags);

	/**
	 * Filters the child items of the group, which belongs to a specific index,
	 * by using a specific query, if no filter using the same query has been
	 * applied yet. If the underlying data of the adapter's child items does not
	 * implement the interface {@link Filterable} a
	 * {@link FilteringNotSupportedException} will be thrown. This method can be
	 * called multiple times without resetting the filtering, which causes the
	 * filtered child item to be filtered once more.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items, which should be
	 *            filtered, belong to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param query
	 *            The query, which should be used to filter the child items, as
	 *            a {@link String}. The query may not be null
	 * @param flags
	 *            The flags, which should be used to filter the child items, as
	 *            an {@link Integer} value or 0, if no flags should be used
	 * @return True, if the filter has been applied, false otherwise
	 */
	boolean applyChildFilter(int groupIndex, String query, int flags);

	/**
	 * Filters the child items of the group, which belongs to a specific index,
	 * by using a specific query, if no filter using the same query has been
	 * applied yet. If the underlying data of the adapter's child items does not
	 * implement the interface {@link Filterable} a
	 * {@link FilteringNotSupportedException} will be thrown. This method can be
	 * called multiple times without resetting the filtering, which causes the
	 * filtered child item to be filtered once more.
	 * 
	 * @param filterEmptyGroup
	 *            True, if the given group should be filtered as well, if it
	 *            becomes empty by filtering its children, false otherwise
	 * @param groupIndex
	 *            The index of the group, the child items, which should be
	 *            filtered, belong to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param query
	 *            The query, which should be used to filter the child items, as
	 *            a {@link String}. The query may not be null
	 * @param flags
	 *            The flags, which should be used to filter the child items, as
	 *            an {@link Integer} value or 0, if no flags should be used
	 * @return True, if the filter has been applied, false otherwise
	 */
	boolean applyChildFilter(boolean filterEmptyGroup, int groupIndex, String query, int flags);

	/**
	 * Filters the adapter's child items, regardless of the group they belong
	 * to, by using a specific query and a filter, which is used to apply the
	 * query on the single child items, if no filter using the same query has
	 * been applied yet. This method can be called multiple times without
	 * resetting the filtering, which causes the filtered child items to be
	 * filtered once more.
	 * 
	 * @param query
	 *            The query, which should be used to filter the child items, as
	 *            a {@link String}. The query may not be null
	 * @param flags
	 *            The flags, which should be used to filter the child items, as
	 *            an {@link Integer} value or 0, if no flags should be used
	 * @param filter
	 *            The filter, which should be used to apply the given query on
	 *            the adapter's child items, as an instance of the type
	 *            {@link Filter}. The filter may not be null
	 * @return True, if the filter has been applied, false otherwise
	 */
	boolean applyChildFilter(String query, int flags, Filter<ChildType> filter);

	/**
	 * Filters the adapter's child items, regardless of the group they belong
	 * to, by using a specific query and a filter, which is used to apply the
	 * query on the single child items, if no filter using the same query has
	 * been applied yet. This method can be called multiple times without
	 * resetting the filtering, which causes the filtered child items to be
	 * filtered once more.
	 * 
	 * @param filterEmptyGroups
	 *            True, if groups, which become empty by filtering their
	 *            children, should be filtered as well, false otherwise
	 * @param query
	 *            The query, which should be used to filter the child items, as
	 *            a {@link String}. The query may not be null
	 * @param flags
	 *            The flags, which should be used to filter the child items, as
	 *            an {@link Integer} value or 0, if no flags should be used
	 * @param filter
	 *            The filter, which should be used to apply the given query on
	 *            the adapter's child items, as an instance of the type
	 *            {@link Filter}. The filter may not be null
	 * @return True, if the filter has been applied, false otherwise
	 */
	boolean applyChildFilter(boolean filterEmptyGroups, String query, int flags, Filter<ChildType> filter);

	/**
	 * Filters the child items of a specific group by using a specific query and
	 * a filter, which is used to apply the query on the single child items, if
	 * no filter using the same query has been applied yet. This method can be
	 * called multiple times without resetting the filtering, which causes the
	 * filtered child items to be filtered once more.
	 * 
	 * @param group
	 *            The group, the child items, which should be filtered, belong
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @param query
	 *            The query, which should be used to filter the child items, as
	 *            a {@link String}. The query may not be null
	 * @param flags
	 *            The flags, which should be used to filter the child items, as
	 *            an {@link Integer} value or 0, if no flags should be used
	 * @param filter
	 *            The filter, which should be used to apply the given query on
	 *            the group's child items, as an instance of the type
	 *            {@link Filter}. The filter may not be null
	 * @return True, if the filter has been applied, false otherwise
	 */
	boolean applyChildFilter(GroupType group, String query, int flags, Filter<ChildType> filter);

	/**
	 * Filters the child items of a specific group by using a specific query and
	 * a filter, which is used to apply the query on the single child items, if
	 * no filter using the same query has been applied yet. This method can be
	 * called multiple times without resetting the filtering, which causes the
	 * filtered child items to be filtered once more.
	 * 
	 * @param filterEmptyGroup
	 *            True, if the given group should be filtered as well, if it
	 *            becomes empty by filtering its children, false otherwise
	 * @param group
	 *            The group, the child items, which should be filtered, belong
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @param query
	 *            The query, which should be used to filter the child items, as
	 *            a {@link String}. The query may not be null
	 * @param flags
	 *            The flags, which should be used to filter the child items, as
	 *            an {@link Integer} value or 0, if no flags should be used
	 * @param filter
	 *            The filter, which should be used to apply the given query on
	 *            the group's child items, as an instance of the type
	 *            {@link Filter}. The filter may not be null
	 * @return True, if the filter has been applied, false otherwise
	 */
	boolean applyChildFilter(boolean filterEmptyGroup, GroupType group, String query, int flags,
			Filter<ChildType> filter);

	/**
	 * Filters the child items of the group, which belongs to a specific index,
	 * by using a specific query and a filter, which is used to apply the query
	 * on the single child items, if no filter using the same query has been
	 * applied yet. This method can be called multiple times without resetting
	 * the filtering, which causes the filtered child items to be filtered once
	 * more.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items, which should be
	 *            filtered, belong to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param query
	 *            The query, which should be used to filter the child items, as
	 *            a {@link String}. The query may not be null
	 * @param flags
	 *            The flags, which should be used to filter the child items, as
	 *            an {@link Integer} value or 0, if no flags should be used
	 * @param filter
	 *            The filter, which should be used to apply the given query on
	 *            the group's child items, as an instance of the type
	 *            {@link Filter}. The filter may not be null
	 * @return True, if the filter has been applied, false otherwise
	 */
	boolean applyChildFilter(int groupIndex, String query, int flags, Filter<ChildType> filter);

	/**
	 * Filters the child items of the group, which belongs to a specific index,
	 * by using a specific query and a filter, which is used to apply the query
	 * on the single child items, if no filter using the same query has been
	 * applied yet. This method can be called multiple times without resetting
	 * the filtering, which causes the filtered child items to be filtered once
	 * more.
	 * 
	 * @param filterEmptyGroup
	 *            True, if the given group should be filtered as well, if it
	 *            becomes empty by filtering its children, false otherwise
	 * @param groupIndex
	 *            The index of the group, the child items, which should be
	 *            filtered, belong to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param query
	 *            The query, which should be used to filter the child items, as
	 *            a {@link String}. The query may not be null
	 * @param flags
	 *            The flags, which should be used to filter the child items, as
	 *            an {@link Integer} value or 0, if no flags should be used
	 * @param filter
	 *            The filter, which should be used to apply the given query on
	 *            the group's child items, as an instance of the type
	 *            {@link Filter}. The filter may not be null
	 * @return True, if the filter has been applied, false otherwise
	 */
	boolean applyChildFilter(boolean filterEmptyGroup, int groupIndex, String query, int flags,
			Filter<ChildType> filter);

	/**
	 * Resets the filter, which has been applied on the adapter to filter its
	 * child items, regardless of the group they belong to, which uses a
	 * specific query.
	 * 
	 * @param query
	 *            The query of the filter, which should be reseted, as a
	 *            {@link String}. The query may not be null
	 * @param flags
	 *            The flags of the filter, which should be reseted, as an
	 *            {@link Integer} value
	 * @return True, if the filter has been reseted at all groups, false
	 *         otherwise
	 */
	boolean resetChildFilter(String query, int flags);

	/**
	 * Resets the filter, which has been applied on a group to filter its child
	 * items, which uses a specific query.
	 * 
	 * @param group
	 *            The group, the child items, which have been filtered, belong
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 * @param query
	 *            The query of the filter, which should be reseted, as a
	 *            {@link String}. The query may not be null
	 * @param flags
	 *            The flags of the filter, which should be reseted, as an
	 *            {@link Integer} value
	 * @return True, if the filter has been reseted, false otherwise
	 */
	boolean resetChildFilter(GroupType group, String query, int flags);

	/**
	 * Resets the filter, which has been applied on the group, which belongs to
	 * a specific index, to filter its child items, which uses a specific query.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items, which have been
	 *            filtered, belong to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param query
	 *            The query of the filter, which should be reseted, as a
	 *            {@link String}. The query may not be null
	 * @param flags
	 *            The flags of the filter, which should be reseted, as an
	 *            {@link Integer} value
	 * @return True, if the filter has been reseted, false otherwise
	 */
	boolean resetChildFilter(int groupIndex, String query, int flags);

	/**
	 * Resets all applied filters, which have been applied on the adapter's
	 * child items, regardless of the groups they belong to.
	 */
	void resetAllChildFilters();

	/**
	 * Resets all applied filters, which have been applied on the child items of
	 * a specific group.
	 * 
	 * @param group
	 *            The group, the child items, which have been filtered, belong
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null. If the group does not belong to the adapter,
	 *            a {@link NoSuchElementException} will be thrown
	 */
	void resetAllChildFilters(GroupType group);

	/**
	 * Resets all applied filters, which have been applied on the child items of
	 * the group, which belongs to a specific index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child items, which have been
	 *            filtered, belong to, as an {@link Integer} value. The value
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 */
	void resetAllChildFilters(int groupIndex);

	/**
	 * Returns, whether at least one filter, which uses a specific query, is
	 * currently applied on the adapter to filter any of its items, or not.
	 * 
	 * @param query
	 *            The query of the filter, which should be checked, as a
	 *            {@link String}. The query may not be null
	 * @param flags
	 *            The flags of the filter, which should be checked, as an
	 *            {@link Integer} value
	 * @return True, if a filter, which uses the given query, is currently
	 *         applied on the adapter to filter any of its child items, false
	 *         otherwise
	 */
	boolean isChildFilterApplied(String query, int flags);

	/**
	 * Returns, whether at least one filter is currently applied on the adapter
	 * to filter any of its child items, or not.
	 * 
	 * @return True, if at least one filter is currently applied on the adapter
	 *         to filter any of its child items, false otherwise.
	 */
	boolean areChildrenFiltered();

	/**
	 * Returns, whether a filter, which uses a specific query, is currently
	 * applied on a specific group's child items.
	 * 
	 * @param group
	 *            The group, the filter, which should be checked, has been
	 *            applied on, as instance of the generic type GroupType. The
	 *            group may not be null. If the group does not belong to the
	 *            adapter, a {@link NoSuchElementException} will be thrown
	 * @param query
	 *            The query of the filter, which should be checked, as a
	 *            {@link String}. The query may not be null
	 * @param flags
	 *            The flags of the filter, which should be checked, as an
	 *            {@link Integer} value
	 * @return True, if a filter, which uses the given query, is currently
	 *         applied on the given group's child items, false otherwise
	 */
	boolean isChildFilterApplied(GroupType group, String query, int flags);

	/**
	 * Returns, whether a filter, which uses a specific query, is currently
	 * applied on the child items of the group, which belongs to a specific
	 * index.
	 * 
	 * @param groupIndex
	 *            The index of the group, the filter, which should be checked,
	 *            has been applied on, as {@link Integer} value. The index must
	 *            be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param query
	 *            The query of the filter, which should be checked, as a
	 *            {@link String}. The query may not be null
	 * @param flags
	 *            The flags of the filter, which should be checked, as an
	 *            {@link Integer} value
	 * @return True, if a filter, which uses the given query, is currently
	 *         applied on the given group's child items, false otherwise
	 */
	boolean isChildFilterApplied(int groupIndex, String query, int flags);

	/**
	 * Returns, whether at least one filter is currently applied on a specific
	 * group to filter its child items, or not.
	 * 
	 * @param group
	 *            The group, whose filters should be checked, as an instance of
	 *            the generic type GroupType. The group may not be null. If the
	 *            group does not belong to the adapter, a
	 *            {@link NoSuchElementException} will be thrown
	 * @return True, if at least one filter is currently applied on the group to
	 *         filter its child items, false otherwise
	 */
	boolean areChildrenFiltered(GroupType group);

	/**
	 * Returns, whether at least one filter is currently applied on a specific
	 * group to filter its child items, or not.
	 * 
	 * @param groupIndex
	 *            The index of the group, whose filters should be checked, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getGroupCount():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return True, if at least one filter is currently applied on the group to
	 *         filter its child items, false otherwise
	 */
	boolean areChildrenFiltered(int groupIndex);

	/**
	 * Adds a new listener, which should be notified, when the adapter's
	 * underlying data has been filtered.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            class {@link ExpandableListFilterListener}. The listener may
	 *            not be null
	 */
	void addFilterListener(ExpandableListFilterListener<GroupType, ChildType> listener);

	/**
	 * Removes a specific listener, which should not be notified, when the
	 * adapter's underlying data has been filtered, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            class {@link ExpandableListFilterListener}. The listener may
	 *            not be null
	 */
	void removeFilterListener(ExpandableListFilterListener<GroupType, ChildType> listener);

}