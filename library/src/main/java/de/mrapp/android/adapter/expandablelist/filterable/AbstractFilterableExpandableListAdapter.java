/*
 * AndroidAdapters Copyright 2014 - 2015 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package de.mrapp.android.adapter.expandablelist.filterable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ExpandableListView;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.FilterQuery;
import de.mrapp.android.adapter.Filterable;
import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.datastructure.group.GroupFilter;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterItemClickListener;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterItemLongClickListener;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterListener;
import de.mrapp.android.adapter.expandablelist.ExpansionListener;
import de.mrapp.android.adapter.expandablelist.enablestate.ExpandableListEnableStateListener;
import de.mrapp.android.adapter.expandablelist.itemstate.ExpandableListItemStateListener;
import de.mrapp.android.adapter.expandablelist.sortable.AbstractSortableExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.sortable.ExpandableListSortingListener;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.logging.LogLevel;

import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * An abstract base class for all adapters, whose underlying data is managed as a filterable list of
 * arbitrary group and child items. Such an adapter's purpose is to provide the underlying data for
 * visualization using a {@link ExpandableListView} widget.
 *
 * @param <GroupType>
 *         The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the adapter's child items
 * @param <DecoratorType>
 *         The type of the decorator, which allows to customize the appearance of the views, which
 *         are used to visualize the group and child items of the adapter
 * @author Michael Rapp
 * @since 0.1.0
 */
public abstract class AbstractFilterableExpandableListAdapter<GroupType, ChildType, DecoratorType>
        extends AbstractSortableExpandableListAdapter<GroupType, ChildType, DecoratorType>
        implements FilterableExpandableListAdapter<GroupType, ChildType> {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * A set, which contains the listeners, which should be notified, when the adapter's underlying
     * data has been filtered.
     */
    private transient Set<ExpandableListFilterListener<GroupType, ChildType>> filterListeners;

    /**
     * Creates and returns a listener, which allows to notify all listeners, which have been
     * registered to be notified when the adapter's underlying data is filtered, when a filter,
     * which has been used to filter the adapter's group items, has been reseted.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * ListFilterListener}
     */
    private ListFilterListener<Group<GroupType, ChildType>> createGroupFilterListener() {
        return new ListFilterListener<Group<GroupType, ChildType>>() {

            @Override
            public void onApplyFilter(
                    @NonNull final ListAdapter<Group<GroupType, ChildType>> adapter,
                    @NonNull final String query, final int flags,
                    @Nullable final Filter<Group<GroupType, ChildType>> filter,
                    @NonNull final List<Group<GroupType, ChildType>> filteredItems) {

            }

            @Override
            public void onResetFilter(
                    @NonNull final ListAdapter<Group<GroupType, ChildType>> adapter,
                    @NonNull final String query, final int flags,
                    @NonNull final List<Group<GroupType, ChildType>> unfilteredItems) {
                notifyOnResetGroupFilter(query, flags, getAllGroups());
            }

        };
    }

    /**
     * Creates and returns a listener, which allows to notify all listeners, which have been
     * registered to be notified when the adapter's underlying data is filtered, when a filter,
     * which has been used to filter the adapter's child items, has been reseted.
     *
     * @param groupIndex
     *         The index of the group to whose child adapter the filter should be attached as an
     *         {@link Integer} value
     * @return The listener, which has been created, as an instance of the type {@link
     * ListFilterListener}
     */
    private ListFilterListener<ChildType> createChildFilterListener(final int groupIndex) {
        return new ListFilterListener<ChildType>() {

            @Override
            public void onApplyFilter(@NonNull final ListAdapter<ChildType> adapter,
                                      @NonNull final String query, final int flags,
                                      @Nullable final Filter<ChildType> filter,
                                      @NonNull final List<ChildType> filteredItems) {

            }

            @Override
            public void onResetFilter(@NonNull final ListAdapter<ChildType> adapter,
                                      @NonNull final String query, final int flags,
                                      @NonNull final List<ChildType> unfilteredItems) {
                notifyOnResetChildFilter(query, flags, getGroup(groupIndex), groupIndex,
                        getAllChildren(groupIndex));
            }

        };
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when the adapter's
     * underlying data has been filtered, when a filter has been applied on the adapter's group
     * items.
     *
     * @param query
     *         The query, which has been used to filter the adapter's group items, as a {@link
     *         String}. The query may not be null
     * @param flags
     *         The flags, which have been used to filter the adapter's group items, as an {@link
     *         Integer} value, or 0, if no flags have been used
     * @param filter
     *         The filter, which has been used to apply the query on the single items, as an
     *         instance of the type {@link Filter} or null, if the items' implementations of the
     *         interface {@link Filterable} has been used instead
     * @param filteredGroups
     *         A collection, which contains the adapter's filtered group items, as an instance of
     *         the type {@link List} or an empty collection, if the adapter does not contain any
     *         group items
     */
    private void notifyOnApplyGroupFilter(@NonNull final String query, final int flags,
                                          @Nullable final Filter<GroupType> filter,
                                          @NonNull final List<GroupType> filteredGroups) {
        for (ExpandableListFilterListener<GroupType, ChildType> listener : filterListeners) {
            listener.onApplyGroupFilter(this, query, flags, filter, filteredGroups);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when the adapter's
     * underlying data has been filtered, when a filter, which has been used to filter the adapter's
     * group items, has been reseted.
     *
     * @param query
     *         The query of the filter, which has been reseted, as a {@link String}. The query may
     *         not be null
     * @param flags
     *         The flags of the filter, which has been reseted, as an {@link Integer} value
     * @param unfilteredGroups
     *         A collection, which contains the adapter's unfiltered group items, as an instance of
     *         the type {@link List} or an empty collection, if the adapter does not contain any
     *         group items
     */
    private void notifyOnResetGroupFilter(@NonNull final String query, final int flags,
                                          @NonNull final List<GroupType> unfilteredGroups) {
        for (ExpandableListFilterListener<GroupType, ChildType> listener : filterListeners) {
            listener.onResetGroupFilter(this, query, flags, unfilteredGroups);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when the adapter's
     * underlying data has been filtered, when a filter has been applied on the adapter's child
     * items.
     *
     * @param query
     *         The query, which has been used to filter the adapter's child items, as a {@link
     *         String}. The query may not be null
     * @param flags
     *         The flags, which have been used to filter the adapter's child items, as an {@link
     *         Integer} value, or 0, if no flags have been used
     * @param filter
     *         The filter, which has been used to apply the query on the single items, as an
     *         instance of the type {@link Filter} or null, if the items' implementations of the
     *         interface {@link Filterable} has been used instead
     * @param group
     *         The group, whose child items have been filtered, as an instance of the generic type
     *         GroupType. The group may not be null
     * @param groupIndex
     *         The index of the group, whose child items have been filtered, as an {@link Integer}
     *         value
     * @param filteredChildren
     *         A collection, which contains the adapter's filtered child items, as an instance of
     *         the type {@link List} or an empty collection, if the adapter does not contain any
     *         child items
     */
    private void notifyOnApplyChildFilter(@NonNull final String query, final int flags,
                                          @Nullable final Filter<ChildType> filter,
                                          @NonNull final GroupType group, final int groupIndex,
                                          @NonNull final List<ChildType> filteredChildren) {
        for (ExpandableListFilterListener<GroupType, ChildType> listener : filterListeners) {
            listener.onApplyChildFilter(this, query, flags, filter, group, groupIndex,
                    filteredChildren);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when the adapter's
     * underlying data has been filtered, when a filter, which has been used to filter the adapter's
     * child items, has been reseted.
     *
     * @param query
     *         The query of the filter, which has been reseted, as a {@link String}. The query may
     *         not be null
     * @param flags
     *         The flags of the filter, which has been reseted, as an {@link Integer} value
     * @param group
     *         The group, whose child items have been filtered, as an instance of the generic type
     *         GroupType. The group may not be null
     * @param groupIndex
     *         The index of the group, whose child items have been filtered, as an {@link Integer}
     *         value
     * @param unfilteredChildren
     *         A collection, which contains the adapter's unfiltered child items, as an instance of
     *         the type {@link List} or an empty collection, if the adapter does not contain any
     *         child items
     */
    private void notifyOnResetChildFilter(@NonNull final String query, final int flags,
                                          @NonNull final GroupType group, final int groupIndex,
                                          @NonNull final List<ChildType> unfilteredChildren) {
        for (ExpandableListFilterListener<GroupType, ChildType> listener : filterListeners) {
            listener.onResetChildFilter(this, query, flags, group, groupIndex, unfilteredChildren);
        }
    }

    /**
     * Returns a set, which contains the listeners, which should be notified, when the adapter's
     * underlying data has been filtered.
     *
     * @return A set, which contains the listeners, which should be notified, when the adapter's
     * underlying data has been filtered, as an instance of the type {@link Set} or an empty set, if
     * no listeners should be notified
     */
    protected final Set<ExpandableListFilterListener<GroupType, ChildType>> getFilterListeners() {
        return filterListeners;
    }

    /**
     * Sets the set, which contains the listeners, which should be notified, when the adapter's
     * underlying data has been filtered.
     *
     * @param filterListeners
     *         The set, which should be set, as an instance of the type {@link Set} or an empty set,
     *         if no listeners should be notified
     */
    protected final void setFilterListeners(
            @NonNull final Set<ExpandableListFilterListener<GroupType, ChildType>> filterListeners) {
        ensureNotNull(filterListeners, "The listeners may not be null");
        this.filterListeners = filterListeners;
    }

    /**
     * Creates a new adapter, whose underlying data is managed as a filterable list of arbitrary
     * group and child items.
     *
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param groupInflater
     *         The inflater, which should be used to inflate the views, which are used to visualize
     *         the adapter's group items, as an instance of the type {@link Inflater}. The inflater
     *         may not be null
     * @param childInflater
     *         The inflater, which should be used to inflate the views, which are used to visualize
     *         the adapter's child items, as an instance of the type {@link Inflater}. The inflater
     *         may not be null
     * @param decorator
     *         The decorator, which should be used to customize the appearance of the views, which
     *         are used to visualize the group and child items of the adapter, as an instance of the
     *         generic type DecoratorType. The decorator may not be null
     * @param logLevel
     *         The log level, which should be used for logging, as a value of the enum {@link
     *         LogLevel}. The log level may not be null
     * @param groupAdapter
     *         The adapter, which should manage the adapter's group items, as an instance of the
     *         type {@link MultipleChoiceListAdapter}. The adapter may not be null
     * @param allowDuplicateChildren
     *         True, if duplicate child items, regardless from the group they belong to, should be
     *         allowed, false otherwise
     * @param notifyOnChange
     *         True, if the method <code>notifyDataSetChanged():void</code> should be automatically
     *         called when the adapter's underlying data has been changed, false otherwise
     * @param expandGroupOnClick
     *         True, if a group should be expanded, when it is clicked by the user, false otherwise
     * @param itemClickListeners
     *         A set, which contains the listeners, which should be notified, when an item of the
     *         adapter has been clicked by the user, as an instance of the type {@link Set}, or an
     *         empty set, if no listeners should be notified
     * @param itemLongClickListeners
     *         A set, which contains the listeners, which should be notified, when an item of the
     *         adapter has been long-clicked by the user, as an instance of the type {@link Set}, or
     *         an empty set, if no listeners should be notified
     * @param adapterListeners
     *         A set, which contains the listeners, which should be notified, when the adapter's
     *         underlying data has been modified, as an instance of the type {@link Set}, or an
     *         empty set, if no listeners should be notified
     * @param expansionListeners
     *         A set, which contains the listeners, which should be notified, when a group item has
     *         been expanded or collapsed, as an instance of the type {@link Set}, or an empty set,
     *         if no listeners should be notified
     * @param setChildEnableStatesImplicitly
     *         True, if the enable states of children should be also set, when the enable state of
     *         the group, they belong to, is set
     * @param enableStateListeners
     *         A set, which contains the listeners, which should be notified, when an item has been
     *         disabled or enabled, as an instance of the type {@link Set}, or an empty set, if no
     *         listeners should be notified
     * @param numberOfGroupStates
     *         The number of states, the adapter's group items may have, as an {@link Integer}
     *         value. The value must be at least 1
     * @param numberOfChildStates
     *         The number of states, the adapter's child items may have, as an {@link Integer}
     *         value. The value must be at least 1
     * @param triggerGroupStateOnClick
     *         True, if the state of a group item should be triggered, when it is clicked by the
     *         user, false otherwise
     * @param triggerChildStateOnClick
     *         True, if the state of a child item should be triggered, when it is clicked by the
     *         user, false otherwise
     * @param setChildStatesImplicitly
     *         True, if the states of children should be also set, when the state of the group, they
     *         belong to, is set, false otherwise
     * @param itemStateListeners
     *         A set, which contains the listeners, which should be notified, when the state of an
     *         item has been changed, or an empty set, if no listeners should be notified
     * @param sortingListeners
     *         A set, which contains the listeners, which should be notified, when the adapter's
     *         underlying data has been sorted, or an empty set, if no listeners should be notified
     * @param filterListeners
     *         A set, which contains the listeners, which should be notified, when the adapter's
     *         underlying data has been filtered, or an empty set, if no listeners should be
     *         notified
     */
    protected AbstractFilterableExpandableListAdapter(@NonNull final Context context,
                                                      @NonNull final Inflater groupInflater,
                                                      @NonNull final Inflater childInflater,
                                                      @NonNull final DecoratorType decorator,
                                                      @NonNull final LogLevel logLevel,
                                                      @NonNull final MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter,
                                                      final boolean allowDuplicateChildren,
                                                      final boolean notifyOnChange,
                                                      final boolean expandGroupOnClick,
                                                      @NonNull final Set<ExpandableListAdapterItemClickListener<GroupType, ChildType>> itemClickListeners,
                                                      @NonNull final Set<ExpandableListAdapterItemLongClickListener<GroupType, ChildType>> itemLongClickListeners,
                                                      @NonNull final Set<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners,
                                                      @NonNull final Set<ExpansionListener<GroupType, ChildType>> expansionListeners,
                                                      final boolean setChildEnableStatesImplicitly,
                                                      @NonNull final Set<ExpandableListEnableStateListener<GroupType, ChildType>> enableStateListeners,
                                                      final int numberOfGroupStates,
                                                      final int numberOfChildStates,
                                                      final boolean triggerGroupStateOnClick,
                                                      final boolean triggerChildStateOnClick,
                                                      final boolean setChildStatesImplicitly,
                                                      @NonNull final Set<ExpandableListItemStateListener<GroupType, ChildType>> itemStateListeners,
                                                      @NonNull final Set<ExpandableListSortingListener<GroupType, ChildType>> sortingListeners,
                                                      @NonNull final Set<ExpandableListFilterListener<GroupType, ChildType>> filterListeners) {
        super(context, groupInflater, childInflater, decorator, logLevel, groupAdapter,
                allowDuplicateChildren, notifyOnChange, expandGroupOnClick, itemClickListeners,
                itemLongClickListeners, adapterListeners, expansionListeners,
                setChildEnableStatesImplicitly, enableStateListeners, numberOfGroupStates,
                numberOfChildStates, triggerGroupStateOnClick, triggerChildStateOnClick,
                setChildEnableStatesImplicitly, itemStateListeners, sortingListeners);
        setFilterListeners(filterListeners);
        getGroupAdapter().addFilterListener(createGroupFilterListener());
    }

    @Override
    protected final Group<GroupType, ChildType> createGroup(final int groupIndex,
                                                            @NonNull final GroupType group) {
        Group<GroupType, ChildType> groupItem = super.createGroup(groupIndex, group);
        groupItem.getChildAdapter().addFilterListener(createChildFilterListener(groupIndex));
        return groupItem;
    }

    @Override
    public final boolean isFiltered() {
        return areGroupsFiltered() || areChildrenFiltered();
    }

    @Override
    public final boolean applyGroupFilter(@NonNull final String query, final int flags) {
        boolean result = getGroupAdapter().applyFilter(query, flags);

        if (result) {
            notifyOnApplyGroupFilter(query, flags, null, getAllGroups());
            notifyOnDataSetChanged();
            String message =
                    "Applied group filter using the query \"" + query + "\" and flags \"" + flags +
                            "\"";
            getLogger().logInfo(getClass(), message);
        } else {
            String message = "Group filter using the query \"" + query + "\" and flags \"" + flags +
                    "\" not applied, because a filter using the same query and flags is already " +
                    "applied on the adapter";
            getLogger().logDebug(getClass(), message);
        }

        return result;
    }

    @Override
    public final boolean applyGroupFilter(@NonNull final String query, final int flags,
                                          @NonNull final Filter<GroupType> filter) {
        boolean result = getGroupAdapter()
                .applyFilter(query, flags, new GroupFilter<GroupType, ChildType>(filter));

        if (result) {
            notifyOnApplyGroupFilter(query, flags, filter, getAllGroups());
            notifyOnDataSetChanged();
            String message =
                    "Applied group filter using the query \"" + query + "\", flags \"" + flags +
                            "\" and filter \"" + filter + "\"";
            getLogger().logInfo(getClass(), message);
        } else {
            String message = "Group filter using the query \"" + query + "\" flags \"" + flags +
                    "\" and filter \"" + filter +
                    "\" not applied, because a filter using the same query, flags and filter is already " +
                    "applied on the adapter";
            getLogger().logDebug(getClass(), message);
        }

        return result;
    }

    @Override
    public final boolean resetGroupFilter(@NonNull final String query, final int flags) {
        boolean result = getGroupAdapter().resetFilter(query, flags);

        if (result) {
            notifyOnDataSetChanged();
            String message =
                    "Reseted group filter with query \"" + query + "\" and flags \"" + flags + "\"";
            getLogger().logInfo(getClass(), message);
        } else {
            String message = "Group filter with query \"" + query + "\" and flags \"" + flags +
                    "\" not reseted, because no such filter is applied on the adapter";
            getLogger().logDebug(getClass(), message);
        }

        return result;
    }

    @Override
    public final void resetAllGroupFilters() {
        getGroupAdapter().resetAllFilters();
        notifyOnDataSetChanged();
        String message = "Reseted all previously applied group filters";
        getLogger().logInfo(getClass(), message);
    }

    @Override
    public final boolean isGroupFilterApplied(@NonNull final String query, final int flags) {
        return getGroupAdapter().isFilterApplied(query, flags);
    }

    @Override
    public final boolean areGroupsFiltered() {
        return getGroupAdapter().isFiltered();
    }

    @Override
    public final Set<? extends FilterQuery> getGroupFilterQueries() {
        return getGroupAdapter().getFilterQueries();
    }

    @Override
    public final boolean applyChildFilter(@NonNull final String query, final int flags) {
        return applyChildFilter(false, query, flags);
    }

    @Override
    public final boolean applyChildFilter(final boolean filterEmptyGroups,
                                          @NonNull final String query, final int flags) {
        boolean result = true;

        for (int i = 0; i < getGroupCount(); i++) {
            result &= applyChildFilter(i, query, flags);
        }

        if (filterEmptyGroups) {
            applyGroupFilter("", Group.FLAG_FILTER_EMPTY_GROUPS);
        }

        return result;
    }

    @Override
    public final boolean applyChildFilter(@NonNull final GroupType group,
                                          @NonNull final String query, final int flags) {
        return applyChildFilter(false, group, query, flags);
    }

    @Override
    public final boolean applyChildFilter(final boolean filterEmptyGroup,
                                          @NonNull final GroupType group,
                                          @NonNull final String query, final int flags) {
        return applyChildFilter(filterEmptyGroup, indexOfGroupOrThrowException(group), query,
                flags);
    }

    @Override
    public final boolean applyChildFilter(final int groupIndex, @NonNull final String query,
                                          final int flags) {
        return applyChildFilter(false, groupIndex, query, flags);
    }

    @Override
    public final boolean applyChildFilter(final boolean filterEmptyGroup, final int groupIndex,
                                          @NonNull final String query, final int flags) {
        Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
        boolean result = group.getChildAdapter().applyFilter(query, flags);

        if (result) {
            notifyOnApplyChildFilter(query, flags, null, group.getData(), groupIndex,
                    getAllChildren(groupIndex));
            notifyOnDataSetChanged();
            String message =
                    "Applied child filter using the query \"" + query + "\" and flags \"" + flags +
                            "\" on the group \"" + group.getData() + "\" at index " + groupIndex;
            getLogger().logInfo(getClass(), message);

            if (filterEmptyGroup) {
                applyGroupFilter("", Group.FLAG_FILTER_EMPTY_GROUPS);
            }
        } else {
            String message = "Child filter using the query \"" + query + "\" and flags \"" + flags +
                    "\" not applied on group \"" + group.getData() + "\" at index " + groupIndex +
                    ", because a filter using the same query and flags is already applied on the group";
            getLogger().logDebug(getClass(), message);
        }

        return result;
    }

    @Override
    public final boolean applyChildFilter(@NonNull final String query, final int flags,
                                          @NonNull final Filter<ChildType> filter) {
        return applyChildFilter(false, query, flags, filter);
    }

    @Override
    public final boolean applyChildFilter(final boolean filterEmptyGroups,
                                          @NonNull final String query, final int flags,
                                          @NonNull final Filter<ChildType> filter) {
        boolean result = true;

        for (int i = 0; i < getGroupCount(); i++) {
            result &= applyChildFilter(i, query, flags, filter);
        }

        if (filterEmptyGroups) {
            applyGroupFilter("", Group.FLAG_FILTER_EMPTY_GROUPS);
        }

        return result;
    }

    @Override
    public final boolean applyChildFilter(@NonNull final GroupType group,
                                          @NonNull final String query, final int flags,
                                          @NonNull final Filter<ChildType> filter) {
        return applyChildFilter(false, group, query, flags, filter);
    }

    @Override
    public final boolean applyChildFilter(final boolean filterEmptyGroup,
                                          @NonNull final GroupType group,
                                          @NonNull final String query, final int flags,
                                          @NonNull final Filter<ChildType> filter) {
        return applyChildFilter(filterEmptyGroup, indexOfGroupOrThrowException(group), query, flags,
                filter);
    }

    @Override
    public final boolean applyChildFilter(final int groupIndex, @NonNull final String query,
                                          final int flags,
                                          @NonNull final Filter<ChildType> filter) {
        return applyChildFilter(false, groupIndex, query, flags, filter);
    }

    @Override
    public final boolean applyChildFilter(final boolean filterEmptyGroup, final int groupIndex,
                                          @NonNull final String query, final int flags,
                                          @NonNull final Filter<ChildType> filter) {
        Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
        boolean result = group.getChildAdapter().applyFilter(query, flags, filter);

        if (result) {
            notifyOnApplyChildFilter(query, flags, filter, group.getData(), groupIndex,
                    getAllChildren(groupIndex));
            notifyOnDataSetChanged();
            String message =
                    "Applied child filter using the query \"" + query + "\", flags \"" + flags +
                            "\" and filter \"" + filter + "\" on the group \"" + group.getData() +
                            "\" at index " + groupIndex;
            getLogger().logInfo(getClass(), message);

            if (filterEmptyGroup) {
                applyGroupFilter("", Group.FLAG_FILTER_EMPTY_GROUPS);
            }
        } else {
            String message = "Child filter using the query \"" + query + "\", flags \"" + flags +
                    "\" and filter \"" + filter + "\" not applied on group \"" + group.getData() +
                    "\" at index " + groupIndex +
                    ", because a filter using the same query, flags and filter is already applied on the group";
            getLogger().logDebug(getClass(), message);
        }

        return result;
    }

    @Override
    public final boolean resetChildFilter(@NonNull final String query, final int flags) {
        boolean result = true;

        for (int i = 0; i < getGroupCount(); i++) {
            result &= resetChildFilter(i, query, flags);
        }

        return result;
    }

    @Override
    public final boolean resetChildFilter(@NonNull final GroupType group,
                                          @NonNull final String query, final int flags) {
        return resetChildFilter(indexOfGroupOrThrowException(group), query, flags);
    }

    @Override
    public final boolean resetChildFilter(final int groupIndex, @NonNull final String query,
                                          final int flags) {
        Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
        boolean result = group.getChildAdapter().resetFilter(query, flags);

        if (result) {
            notifyOnDataSetChanged();
            String message = "Reseted child filter of group \"" + group.getData() + "\" at index " +
                    groupIndex + " with query \"" + query + "\" and flags \"" + flags + "\"";
            getLogger().logInfo(getClass(), message);

            if (isGroupFilterApplied("", Group.FLAG_FILTER_EMPTY_GROUPS)) {
                resetGroupFilter("", Group.FLAG_FILTER_EMPTY_GROUPS);
            }
        } else {
            String message =
                    "Child filter of group \"" + group.getData() + "\" at index " + groupIndex +
                            " with query \"" + query + "\" and flags \"" + flags +
                            "\" not reseted, because no such filter is applied on the adapter";
            getLogger().logDebug(getClass(), message);
        }

        return result;
    }

    @Override
    public final void resetAllChildFilters() {
        resetGroupFilter("", Group.FLAG_FILTER_EMPTY_GROUPS);

        for (int i = 0; i < getGroupCount(); i++) {
            resetAllChildFilters(i);
        }
    }

    @Override
    public final void resetAllChildFilters(@NonNull final GroupType group) {
        resetAllChildFilters(indexOfGroupOrThrowException(group));
    }

    @Override
    public final void resetAllChildFilters(final int groupIndex) {
        resetGroupFilter("", Group.FLAG_FILTER_EMPTY_GROUPS);
        Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
        group.getChildAdapter().resetAllFilters();
        notifyOnDataSetChanged();
        String message =
                "Reseted all previously applied child filters of group \"" + group.getData() +
                        "\" at index " + groupIndex;
        getLogger().logInfo(getClass(), message);
    }

    @Override
    public final void addFilterListener(
            @NonNull final ExpandableListFilterListener<GroupType, ChildType> listener) {
        ensureNotNull(listener, "The listener may not be null");
        filterListeners.add(listener);
        String message = "Added filter listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void removeFilterListener(
            @NonNull final ExpandableListFilterListener<GroupType, ChildType> listener) {
        ensureNotNull(listener, "The listener may not be null");
        filterListeners.remove(listener);
        String message = "Removed filter listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final boolean isChildFilterApplied(@NonNull final String query, final int flags) {
        for (int i = 0; i < getGroupCount(); i++) {
            if (isChildFilterApplied(i, query, flags)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public final boolean areChildrenFiltered() {
        for (int i = 0; i < getGroupCount(); i++) {
            if (areChildrenFiltered(i)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public final boolean isChildFilterApplied(@NonNull final GroupType group,
                                              @NonNull final String query, final int flags) {
        return isChildFilterApplied(indexOfGroupOrThrowException(group), query, flags);
    }

    @Override
    public final boolean isChildFilterApplied(final int groupIndex, @NonNull final String query,
                                              final int flags) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter()
                .isFilterApplied(query, flags);
    }

    @Override
    public final boolean areChildrenFiltered(@NonNull final GroupType group) {
        return areChildrenFiltered(indexOfGroupOrThrowException(group));
    }

    @Override
    public final boolean areChildrenFiltered(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().isFiltered();
    }

    @Override
    public final Set<? extends FilterQuery> getChildFilterQueries() {
        Set<FilterQuery> queries = new LinkedHashSet<>();

        for (int i = 0; i < getGroupCount(); i++) {
            queries.addAll(getChildFilterQueries(i));
        }

        return queries;
    }

    @Override
    public final Set<? extends FilterQuery> getChildFilterQueries(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getFilterQueries();
    }

    @Override
    public final Set<? extends FilterQuery> getChildFilterQueries(final GroupType group) {
        return getChildFilterQueries(indexOfGroupOrThrowException(group));
    }

    @Override
    public abstract AbstractFilterableExpandableListAdapter<GroupType, ChildType, DecoratorType> clone()
            throws CloneNotSupportedException;

}