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

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.Set;

import android.content.Context;
import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.datastructure.group.GroupFilter;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterItemClickListener;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterListener;
import de.mrapp.android.adapter.expandablelist.ExpansionListener;
import de.mrapp.android.adapter.expandablelist.enablestate.ExpandableListEnableStateListener;
import de.mrapp.android.adapter.expandablelist.itemstate.ExpandableListItemStateListener;
import de.mrapp.android.adapter.expandablelist.sortable.AbstractSortableExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.sortable.ExpandableListSortingListener;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.logging.LogLevel;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a filterable list of arbitrary group and child items. Such an adapter's
 * purpose is to provide the underlying data for visualization using a
 * {@link ExpandableListView} widget.
 * 
 * @param <GroupType>
 *            The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *            The type of the underlying data of the adapter's child items
 * @param <DecoratorType>
 *            The type of the decorator, which allows to customize the
 *            appearance of the views, which are used to visualize the group and
 *            child items of the adapter
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractFilterableExpandableListAdapter<GroupType, ChildType, DecoratorType>
		extends AbstractSortableExpandableListAdapter<GroupType, ChildType, DecoratorType>
		implements FilterableExpandableListAdapter<GroupType, ChildType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A set, which contains the listeners, which should be notified, when the
	 * adapter's underlying data has been filtered.
	 */
	private transient Set<ExpandableListFilterListener<GroupType, ChildType>> filterListeners;

	/**
	 * Returns a set, which contains the listeners, which should be notified,
	 * when the adapter's underlying data has been filtered.
	 * 
	 * @return A set, which contains the listeners, which should be notified,
	 *         when the adapter's underlying data has been filtered, as an
	 *         instance of the type {@link Set} or an empty set, if no listeners
	 *         should be notified
	 */
	protected final Set<ExpandableListFilterListener<GroupType, ChildType>> getFilterListeners() {
		return filterListeners;
	}

	/**
	 * Sets the set, which contains the listeners, which should be notified,
	 * when the adapter's underlying data has been filtered.
	 * 
	 * @param filterListeners
	 *            The set, which should be set, as an instance of the type
	 *            {@link Set} or an empty set, if no listeners should be
	 *            notified
	 */
	protected final void setFilterListeners(
			final Set<ExpandableListFilterListener<GroupType, ChildType>> filterListeners) {
		ensureNotNull(filterListeners, "The listeners may not be null");
		this.filterListeners = filterListeners;
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a filterable
	 * list of arbitrary group and child items.
	 * 
	 * @param context
	 *            The context, the adapter belongs to, as an instance of the
	 *            class {@link Context}. The context may not be null
	 * @param groupInflater
	 *            The inflater, which should be used to inflate the views, which
	 *            are used to visualize the adapter's group items, as an
	 *            instance of the type {@link Inflater}. The inflater may not be
	 *            null
	 * @param childInflater
	 *            The inflater, which should be used to inflate the views, which
	 *            are used to visualize the adapter's child items, as an
	 *            instance of the type {@link Inflater}. The inflater may not be
	 *            null
	 * @param decorator
	 *            The decorator, which should be used to customize the
	 *            appearance of the views, which are used to visualize the group
	 *            and child items of the adapter, as an instance of the generic
	 *            type DecoratorType. The decorator may not be null
	 * @param logLevel
	 *            The log level, which should be used for logging, as a value of
	 *            the enum {@link LogLevel}. The log level may not be null
	 * @param groupAdapter
	 *            The adapter, which should manage the adapter's group items, as
	 *            an instance of the type {@link MultipleChoiceListAdapter}. The
	 *            adapter may not be null
	 * @param allowDuplicateChildren
	 *            True, if duplicate group items, regardless from the group they
	 *            belong to, should be allowed, false otherwise
	 * @param notifyOnChange
	 *            True, if the method <code>notifyDataSetChanged():void</code>
	 *            should be automatically called when the adapter's underlying
	 *            data has been changed, false otherwise
	 * @param expandGroupOnClick
	 *            True, if a group should be expanded, when it is clicked by the
	 *            user, false otherwise
	 * @param itemClickListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when an item of the adapter has been clicked by the user, as
	 *            an instance of the type {@link Set} or an empty set, if no
	 *            listeners should be notified
	 * @param adapterListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been modified, as an
	 *            instance of the type {@link Set} or an empty set, if no
	 *            listeners should be notified
	 * @param expansionListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when a group item has been expanded or collapsed, as an
	 *            instance of the type {@link Set} or an empty set, if no
	 *            listeners should be notified
	 * @param enableStateListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when an item has been disabled or enabled, as an instance of
	 *            the type {@link Set} or an empty set, if no listeners should
	 *            be notified
	 * @param numberOfGroupStates
	 *            The number of states, the adapter's group items may have, as
	 *            an {@link Integer} value. The value must be at least 1
	 * @param numberOfChildStates
	 *            The number of states, the adapter's child items may have, as
	 *            an {@link Integer} value. The value must be at least 1
	 * @param triggerGroupStateOnClick
	 *            True, if the state of a group item should be triggered, when
	 *            it is clicked by the user, false otherwise
	 * @param triggerChildStateOnClick
	 *            True, if the state of a child item should be triggered, when
	 *            it is clicked by the user, false otherwise
	 * @param itemStateListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the state of an item has been changed or an empty set, if
	 *            no listeners should be notified
	 * @param sortingListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been sorted or an empty
	 *            set, if no listeners should be notified
	 * @param filterListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been filtered, or an
	 *            empty set, if no listeners should be notified
	 */
	protected AbstractFilterableExpandableListAdapter(final Context context, final Inflater groupInflater,
			final Inflater childInflater, final DecoratorType decorator, final LogLevel logLevel,
			final MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter,
			final boolean allowDuplicateChildren, final boolean notifyOnChange, final boolean expandGroupOnClick,
			final Set<ExpandableListAdapterItemClickListener<GroupType, ChildType>> itemClickListeners,
			final Set<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners,
			final Set<ExpansionListener<GroupType, ChildType>> expansionListeners,
			final Set<ExpandableListEnableStateListener<GroupType, ChildType>> enableStateListeners,
			final int numberOfGroupStates, final int numberOfChildStates, final boolean triggerGroupStateOnClick,
			final boolean triggerChildStateOnClick,
			final Set<ExpandableListItemStateListener<GroupType, ChildType>> itemStateListeners,
			final Set<ExpandableListSortingListener<GroupType, ChildType>> sortingListeners,
			final Set<ExpandableListFilterListener<GroupType, ChildType>> filterListeners) {
		super(context, groupInflater, childInflater, decorator, logLevel, groupAdapter, allowDuplicateChildren,
				notifyOnChange, expandGroupOnClick, itemClickListeners, adapterListeners, expansionListeners,
				enableStateListeners, numberOfGroupStates, numberOfChildStates, triggerGroupStateOnClick,
				triggerChildStateOnClick, itemStateListeners, sortingListeners);
		setFilterListeners(filterListeners);
	}

	@Override
	public final boolean applyGroupFilter(final String query, final int flags) {
		boolean result = getGroupAdapter().applyFilter(query, flags);

		if (result) {
			String message = "Applied group filter using the query \"" + query + "\" and flags \"" + flags + "\"";
			getLogger().logInfo(getClass(), message);
		} else {
			String message = "Group filter using the query \"" + query + "\" and flags \"" + flags
					+ "\" not applied, because a filter using the same query and flags is already "
					+ "applied on the adapter";
			getLogger().logDebug(getClass(), message);
		}

		return result;
	}

	@Override
	public final boolean applyGroupFilter(final String query, final int flags, final Filter<GroupType> filter) {
		boolean result = getGroupAdapter().applyFilter(query, flags, new GroupFilter<GroupType, ChildType>(filter));

		if (result) {
			String message = "Applied group filter using the query \"" + query + "\", flags \"" + flags
					+ "\" and filter \"" + filter + "\"";
			getLogger().logInfo(getClass(), message);
		} else {
			String message = "Group filter using the query \"" + query + "\" flags \"" + flags + "\" and filter \""
					+ filter + "\" not applied, because a filter using the same query, flags and filter is already "
					+ "applied on the adapter";
			getLogger().logDebug(getClass(), message);
		}

		return result;
	}

	@Override
	public final boolean resetGroupFilter(final String query, final int flags) {
		boolean result = getGroupAdapter().resetFilter(query, flags);

		if (result) {
			String message = "Reseted group filter with query \"" + query + "\" and flags \"" + flags + "\"";
			getLogger().logInfo(getClass(), message);
		} else {
			String message = "Group filter with query \"" + query + "\" and flags \"" + flags
					+ "\" not reseted, because no such filter is applied on the adapter";
			getLogger().logDebug(getClass(), message);
		}

		return result;
	}

	@Override
	public final void resetAllGroupFilters() {
		getGroupAdapter().resetAllFilters();
		String message = "Reseted all previously applied group filters";
		getLogger().logInfo(getClass(), message);
	}

	@Override
	public final boolean areGroupsFiltered() {
		return getGroupAdapter().isFiltered();
	}

	@Override
	public final boolean applyChildFilter(final String query, final int flags) {
		boolean result = true;

		for (int i = 0; i < getNumberOfGroups(); i++) {
			result &= applyChildFilter(i, query, flags);
		}

		return result;
	}

	@Override
	public final boolean applyChildFilter(final GroupType group, final String query, final int flags) {
		return applyChildFilter(indexOfGroupOrThrowException(group), query, flags);
	}

	@Override
	public final boolean applyChildFilter(final int groupIndex, final String query, final int flags) {
		Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
		boolean result = group.getChildAdapter().applyFilter(query, flags);

		if (result) {
			String message = "Applied child filter using the query \"" + query + "\" and flags \"" + flags
					+ "\" on the group \"" + group.getData() + "\" at index " + groupIndex;
			getLogger().logInfo(getClass(), message);
		} else {
			String message = "Child filter using the query \"" + query + "\" and flags \"" + flags
					+ "\" not applied on group \"" + group.getData() + "\" at index " + groupIndex
					+ ", because a filter using the same query and flags is already applied on the group";
			getLogger().logDebug(getClass(), message);
		}

		return result;
	}

	@Override
	public final boolean applyChildFilter(final String query, final int flags, final Filter<ChildType> filter) {
		boolean result = true;

		for (int i = 0; i < getNumberOfGroups(); i++) {
			result &= applyChildFilter(i, query, flags, filter);
		}

		return result;
	}

	@Override
	public final boolean applyChildFilter(final GroupType group, final String query, final int flags,
			final Filter<ChildType> filter) {
		return applyChildFilter(indexOfGroupOrThrowException(group), query, flags, filter);
	}

	@Override
	public final boolean applyChildFilter(final int groupIndex, final String query, final int flags,
			final Filter<ChildType> filter) {
		Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
		boolean result = group.getChildAdapter().applyFilter(query, flags, filter);

		if (result) {
			String message = "Applied child filter using the query \"" + query + "\", flags \"" + flags
					+ "\" and filter \"" + filter + "\" on the group \"" + group.getData() + "\" at index "
					+ groupIndex;
			getLogger().logInfo(getClass(), message);
		} else {
			String message = "Child filter using the query \"" + query + "\", flags \"" + flags + "\" and filter \""
					+ filter + "\" not applied on group \"" + group.getData() + "\" at index " + groupIndex
					+ ", because a filter using the same query, flags and filter is already applied on the group";
			getLogger().logDebug(getClass(), message);
		}

		return result;
	}

	@Override
	public final boolean resetChildFilter(final String query, final int flags) {
		boolean result = true;

		for (int i = 0; i < getNumberOfGroups(); i++) {
			result &= resetChildFilter(i, query, flags);
		}

		return result;
	}

	@Override
	public final boolean resetChildFilter(final GroupType group, final String query, final int flags) {
		return resetChildFilter(indexOfGroupOrThrowException(group), query, flags);
	}

	@Override
	public final boolean resetChildFilter(final int groupIndex, final String query, final int flags) {
		Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
		boolean result = group.getChildAdapter().resetFilter(query, flags);

		if (result) {
			String message = "Reseted child filter of group \"" + group.getData() + "\" at index " + groupIndex
					+ " with query \"" + query + "\" and flags \"" + flags + "\"";
			getLogger().logInfo(getClass(), message);
		} else {
			String message = "Child filter of group \"" + group.getData() + "\" at index " + groupIndex
					+ " with query \"" + query + "\" and flags \"" + flags
					+ "\" not reseted, because no such filter is applied on the adapter";
			getLogger().logDebug(getClass(), message);
		}

		return result;
	}

	@Override
	public final void resetAllChildFilters() {
		for (int i = 0; i < getNumberOfGroups(); i++) {
			resetAllChildFilters(i);
		}
	}

	@Override
	public final void resetAllChildFilters(final GroupType group) {
		resetAllChildFilters(indexOfGroupOrThrowException(group));
	}

	@Override
	public final void resetAllChildFilters(final int groupIndex) {
		Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
		group.getChildAdapter().resetAllFilters();
		String message = "Reseted all previously applied child filters of group \"" + group.getData() + "\" at index "
				+ groupIndex;
		getLogger().logInfo(getClass(), message);
	}

	@Override
	public final void addFilterListener(final ExpandableListFilterListener<GroupType, ChildType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		filterListeners.add(listener);
	}

	@Override
	public final void removeFilterListener(final ExpandableListFilterListener<GroupType, ChildType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		filterListeners.remove(listener);
	}

	@Override
	public final boolean areChildrenFiltered() {
		for (int i = 0; i < getNumberOfGroups(); i++) {
			if (areChildrenFiltered(i)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public final boolean areChildrenFiltered(final GroupType group) {
		return areChildrenFiltered(indexOfGroupOrThrowException(group));
	}

	@Override
	public final boolean areChildrenFiltered(final int groupIndex) {
		return getGroupAdapter().getItem(groupIndex).getChildAdapter().isFiltered();
	}

	@Override
	public abstract AbstractFilterableExpandableListAdapter<GroupType, ChildType, DecoratorType> clone()
			throws CloneNotSupportedException;

}