/*
 * Copyright 2014 - 2018 Michael Rapp
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package de.mrapp.android.adapter.expandablelist.filterable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.FilterQuery;
import de.mrapp.android.adapter.Filterable;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.datastructure.UnmodifiableList;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.datastructure.group.GroupFilter;
import de.mrapp.android.adapter.datastructure.group.UnmodifiableGroupList;
import de.mrapp.android.adapter.decorator.AbstractExpandableListDecorator;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterItemClickListener;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterItemLongClickListener;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterListener;
import de.mrapp.android.adapter.expandablelist.ExpansionListener;
import de.mrapp.android.adapter.expandablelist.enablestate.ExpandableListEnableStateListener;
import de.mrapp.android.adapter.expandablelist.itemstate.ExpandableListItemStateListener;
import de.mrapp.android.adapter.expandablelist.sortable.AbstractSortableExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.sortable.ExpandableListSortingListener;
import de.mrapp.android.adapter.list.ListAdapter;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.util.datastructure.ListenerList;
import de.mrapp.android.util.logging.LogLevel;

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
public abstract class AbstractFilterableExpandableListAdapter<GroupType, ChildType, DecoratorType extends AbstractExpandableListDecorator<GroupType, ChildType>>
        extends AbstractSortableExpandableListAdapter<GroupType, ChildType, DecoratorType>
        implements FilterableExpandableListAdapter<GroupType, ChildType> {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * A list, which contains the listeners, which should be notified, when the adapter's underlying
     * data has been filtered.
     */
    private transient ListenerList<ExpandableListFilterListener<GroupType, ChildType>>
            filterListeners;

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
     *         A collection, which contains the group items, which have been filtered, as an
     *         instance of the type {@link List} or an empty collection, if no group items have been
     *         filtered
     * @param unfilteredGroups
     *         A collection, which contains the adapter's unfiltered group items, as an instance of
     *         the type {@link List} or an empty collection, if the adapter does not contain any
     *         group items
     */
    private void notifyOnApplyGroupFilter(@NonNull final String query, final int flags,
                                          @Nullable final Filter<GroupType> filter,
                                          @NonNull final List<GroupType> filteredGroups,
                                          @NonNull final List<GroupType> unfilteredGroups) {
        for (ExpandableListFilterListener<GroupType, ChildType> listener : filterListeners) {
            listener.onApplyGroupFilter(this, query, flags, filter, filteredGroups,
                    unfilteredGroups);
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
     *         A collection, which contains the child items, which have been filtered, as an
     *         instance of the type {@link List} or an empty collection, if no child items have been
     *         filtered
     * @param unfilteredChildren
     *         A collection, which contains the adapter's unfiltered child items, as an instance of
     *         the type {@link List} or an empty collection, if the adapter does not contain any
     *         child items
     */
    private void notifyOnApplyChildFilter(@NonNull final String query, final int flags,
                                          @Nullable final Filter<ChildType> filter,
                                          @NonNull final GroupType group, final int groupIndex,
                                          @NonNull final List<ChildType> filteredChildren,
                                          @NonNull final List<ChildType> unfilteredChildren) {
        for (ExpandableListFilterListener<GroupType, ChildType> listener : filterListeners) {
            listener.onApplyChildFilter(this, query, flags, filter, group, groupIndex,
                    filteredChildren, unfilteredChildren);
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
     * Creates and returns a listener, which allows to unselect all child items of a group, which
     * has been filtered.
     *
     * @return The listener, which has been creates, as an instance of the type {@link
     * ListFilterListener}
     */
    private ListFilterListener<Group<GroupType, ChildType>> createGroupFilterListener() {
        return new ListFilterListener<Group<GroupType, ChildType>>() {

            @Override
            public void onApplyFilter(
                    @NonNull final ListAdapter<Group<GroupType, ChildType>> adapter,
                    @NonNull final String query, final int flags,
                    @Nullable final Filter<Group<GroupType, ChildType>> filter,
                    @NonNull final List<Group<GroupType, ChildType>> filteredItems,
                    @NonNull final List<Group<GroupType, ChildType>> unfilteredItems) {
                for (Group<GroupType, ChildType> filteredGroup : filteredItems) {
                    filteredGroup.getChildAdapter().setAllSelected(false);
                }
            }

            @Override
            public void onResetFilter(
                    @NonNull final ListAdapter<Group<GroupType, ChildType>> adapter,
                    @NonNull final String query, final int flags,
                    @NonNull final List<Group<GroupType, ChildType>> unfilteredItems) {

            }

        };
    }

    /**
     * Resets the filter, which has been applied on the group, which belongs to a specific index, to
     * filter its child items, which uses a specific query.
     *
     * @param groupIndex
     *         The index of the group, the child items, which have been filtered, belong to, as an
     *         {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param query
     *         The query of the filter, which should be reseted, as a {@link String}. The query may
     *         not be null
     * @param flags
     *         The flags of the filter, which should be reseted, as an {@link Integer} value
     * @param filterEmptyGroups
     *         True, if the filter, which filters empty groups, should be applied on the adapter's
     *         groups after the filter has been reseted
     * @return True, if the filter has been reseted, false otherwise
     */
    private boolean resetChildFilter(final int groupIndex, @NonNull final String query,
                                     final int flags, boolean filterEmptyGroups) {
        resetGroupFilter("", Group.FLAG_FILTER_EMPTY_GROUPS);
        Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
        boolean result = group.getChildAdapter().resetFilter(query, flags);

        if (result) {
            notifyOnResetChildFilter(query, flags, group.getData(), groupIndex,
                    getAllChildren(groupIndex));
            notifyObserversOnDataSetChanged();
            String message = "Reseted child filter of group \"" + group.getData() + "\" at index " +
                    groupIndex + " with query \"" + query + "\" and flags \"" + flags + "\"";
            getLogger().logInfo(getClass(), message);
        } else {
            String message =
                    "Child filter of group \"" + group.getData() + "\" at index " + groupIndex +
                            " with query \"" + query + "\" and flags \"" + flags +
                            "\" not reseted, because no such filter is applied on the adapter";
            getLogger().logDebug(getClass(), message);
        }

        if (filterEmptyGroups && areChildrenFiltered()) {
            applyGroupFilter("", Group.FLAG_FILTER_EMPTY_GROUPS);
        }

        return result;
    }

    /**
     * Returns a list, which contains the listeners, which should be notified, when the adapter's
     * underlying data has been filtered.
     *
     * @return A list, which contains the listeners, which should be notified, when the adapter's
     * underlying data has been filtered, as an instance of the class ListenerList or an empty list,
     * if no listeners should be notified
     */
    protected final ListenerList<ExpandableListFilterListener<GroupType, ChildType>> getFilterListeners() {
        return filterListeners;
    }

    /**
     * Sets the list, which contains the listeners, which should be notified, when the adapter's
     * underlying data has been filtered.
     *
     * @param filterListeners
     *         The list, which should be set, as an instance of the class ListenerList or an empty
     *         list, if no listeners should be notified
     */
    protected final void setFilterListeners(
            @NonNull final ListenerList<ExpandableListFilterListener<GroupType, ChildType>> filterListeners) {
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
     * @param decorator
     *         The decorator, which should be used to customize the appearance of the views, which
     *         are used to visualize the group and child items of the adapter, as an instance of the
     *         generic type DecoratorType. The decorator may not be null
     * @param logLevel
     *         The log level, which should be used for logging, as a value of the enum LogLevel. The
     *         log level may not be null
     * @param groupAdapter
     *         The adapter, which should manage the adapter's group items, as an instance of the
     *         type {@link MultipleChoiceListAdapter}. The adapter may not be null
     * @param allowDuplicateChildren
     *         True, if duplicate child items, regardless from the group they belong to, should be
     *         allowed, false otherwise
     * @param notifyOnChange
     *         True, if the method <code>notifyDataSetChanged():void</code> should be automatically
     *         called when the adapter's underlying data has been changed, false otherwise
     * @param triggerGroupExpansionOnClick
     *         True, if a group's expansion should be triggered, when it is clicked by the user,
     *         false otherwise
     * @param itemClickListeners
     *         A list, which contains the listeners, which should be notified, when an item of the
     *         adapter has been clicked by the user, as an instance of the class ListenerList, or an
     *         empty list, if no listeners should be notified
     * @param itemLongClickListeners
     *         A list, which contains the listeners, which should be notified, when an item of the
     *         adapter has been long-clicked by the user, as an instance of the class ListenerList,
     *         or an empty list, if no listeners should be notified
     * @param adapterListeners
     *         A list, which contains the listeners, which should be notified, when the adapter's
     *         underlying data has been modified, as an instance of the class ListenerList, or an
     *         empty list, if no listeners should be notified
     * @param expansionListeners
     *         A list, which contains the listeners, which should be notified, when a group item has
     *         been expanded or collapsed, as an instance of the class ListenerList, or an empty
     *         list, if no listeners should be notified
     * @param setChildEnableStatesImplicitly
     *         True, if the enable states of children should be also set, when the enable state of
     *         the group, they belong to, is set
     * @param enableStateListeners
     *         A list, which contains the listeners, which should be notified, when an item has been
     *         disabled or enabled, as an instance of the class ListenerList, or an empty list, if
     *         no listeners should be notified
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
     *         A list, which contains the listeners, which should be notified, when the state of an
     *         item has been changed, as an instance of the class ListenerList or an empty list, if
     *         no listeners should be notified
     * @param sortingListeners
     *         A list, which contains the listeners, which should be notified, when the adapter's
     *         underlying data has been sorted, as an instance of the class ListenerList or an empty
     *         list, if no listeners should be notified
     * @param filterListeners
     *         A list, which contains the listeners, which should be notified, when the adapter's
     *         underlying data has been filtered, as an instance of the class ListenerList or an
     *         empty list, if no listeners should be notified
     */
    protected AbstractFilterableExpandableListAdapter(@NonNull final Context context,
                                                      @NonNull final DecoratorType decorator,
                                                      @NonNull final LogLevel logLevel,
                                                      @NonNull final MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter,
                                                      final boolean allowDuplicateChildren,
                                                      final boolean notifyOnChange,
                                                      final boolean triggerGroupExpansionOnClick,
                                                      @NonNull final ListenerList<ExpandableListAdapterItemClickListener<GroupType, ChildType>> itemClickListeners,
                                                      @NonNull final ListenerList<ExpandableListAdapterItemLongClickListener<GroupType, ChildType>> itemLongClickListeners,
                                                      @NonNull final ListenerList<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners,
                                                      @NonNull final ListenerList<ExpansionListener<GroupType, ChildType>> expansionListeners,
                                                      final boolean setChildEnableStatesImplicitly,
                                                      @NonNull final ListenerList<ExpandableListEnableStateListener<GroupType, ChildType>> enableStateListeners,
                                                      final int numberOfGroupStates,
                                                      final int numberOfChildStates,
                                                      final boolean triggerGroupStateOnClick,
                                                      final boolean triggerChildStateOnClick,
                                                      final boolean setChildStatesImplicitly,
                                                      @NonNull final ListenerList<ExpandableListItemStateListener<GroupType, ChildType>> itemStateListeners,
                                                      @NonNull final ListenerList<ExpandableListSortingListener<GroupType, ChildType>> sortingListeners,
                                                      @NonNull final ListenerList<ExpandableListFilterListener<GroupType, ChildType>> filterListeners) {
        super(context, decorator, logLevel, groupAdapter, allowDuplicateChildren, notifyOnChange,
                triggerGroupExpansionOnClick, itemClickListeners, itemLongClickListeners,
                adapterListeners, expansionListeners, setChildEnableStatesImplicitly,
                enableStateListeners, numberOfGroupStates, numberOfChildStates,
                triggerGroupStateOnClick, triggerChildStateOnClick, setChildStatesImplicitly,
                itemStateListeners, sortingListeners);
        setFilterListeners(filterListeners);
        getGroupAdapter().addFilterListener(createGroupFilterListener());
    }

    @Override
    public final boolean isFiltered() {
        return areGroupsFiltered() || areChildrenFiltered();
    }

    @Nullable
    @Override
    public final List<GroupType> applyGroupFilter(@NonNull final String query, final int flags) {
        List<Group<GroupType, ChildType>> filteredGroups =
                getGroupAdapter().applyFilter(query, flags);

        if (filteredGroups != null) {
            notifyOnApplyGroupFilter(query, flags, null,
                    new UnmodifiableGroupList<>(filteredGroups), getAllGroups());
            notifyObserversOnDataSetChanged();
            String message =
                    "Applied group filter using the query \"" + query + "\" and flags \"" + flags +
                            "\"";
            getLogger().logInfo(getClass(), message);
            return new UnmodifiableGroupList<>(filteredGroups);
        } else {
            String message = "Group filter using the query \"" + query + "\" and flags \"" + flags +
                    "\" not applied, because a filter using the same query and flags is already " +
                    "applied on the adapter";
            getLogger().logDebug(getClass(), message);
            return null;
        }
    }

    @Nullable
    @Override
    public final List<GroupType> applyGroupFilter(@NonNull final String query, final int flags,
                                                  @NonNull final Filter<GroupType> filter) {
        List<Group<GroupType, ChildType>> filteredGroups = getGroupAdapter()
                .applyFilter(query, flags, new GroupFilter<GroupType, ChildType>(filter));

        if (filteredGroups != null) {
            notifyOnApplyGroupFilter(query, flags, filter,
                    new UnmodifiableGroupList<>(filteredGroups), getAllGroups());
            notifyObserversOnDataSetChanged();
            String message =
                    "Applied group filter using the query \"" + query + "\", flags \"" + flags +
                            "\" and filter \"" + filter + "\"";
            getLogger().logInfo(getClass(), message);
            return new UnmodifiableGroupList<>(filteredGroups);
        } else {
            String message = "Group filter using the query \"" + query + "\" flags \"" + flags +
                    "\" and filter \"" + filter +
                    "\" not applied, because a filter using the same query, flags and filter is already " +
                    "applied on the adapter";
            getLogger().logDebug(getClass(), message);
            return null;
        }
    }

    @Override
    public final boolean resetGroupFilter(@NonNull final String query, final int flags) {
        boolean result = getGroupAdapter().resetFilter(query, flags);

        if (result) {
            taintAdapterView();
            notifyObserversOnDataSetChanged();
            notifyOnResetGroupFilter(query, flags, getAllGroups());
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
        Set<? extends FilterQuery> filterQueries = getGroupFilterQueries();

        for (FilterQuery filterQuery : filterQueries) {
            resetGroupFilter(filterQuery.getQuery(), filterQuery.getFlags());
        }

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
    public final List<ChildType> applyChildFilter(@NonNull final String query, final int flags) {
        return applyChildFilter(false, query, flags);
    }

    @Nullable
    @Override
    public final List<ChildType> applyChildFilter(final boolean filterEmptyGroups,
                                                  @NonNull final String query, final int flags) {
        List<ChildType> result = null;

        for (int i = 0; i < getGroupCount(); i++) {
            List<ChildType> filteredChildren = applyChildFilter(i, query, flags);

            if (filteredChildren != null) {
                if (result == null) {
                    result = new ArrayList<>();
                }

                result.addAll(filteredChildren);
            }
        }

        if (filterEmptyGroups) {
            applyGroupFilter("", Group.FLAG_FILTER_EMPTY_GROUPS);
        }

        return result != null ? new UnmodifiableList<>(result) : null;
    }

    @Nullable
    @Override
    public final List<ChildType> applyChildFilter(@NonNull final GroupType group,
                                                  @NonNull final String query, final int flags) {
        return applyChildFilter(false, group, query, flags);
    }

    @Nullable
    @Override
    public final List<ChildType> applyChildFilter(final boolean filterEmptyGroup,
                                                  @NonNull final GroupType group,
                                                  @NonNull final String query, final int flags) {
        return applyChildFilter(filterEmptyGroup, indexOfGroupOrThrowException(group), query,
                flags);
    }

    @Nullable
    @Override
    public final List<ChildType> applyChildFilter(final int groupIndex, @NonNull final String query,
                                                  final int flags) {
        return applyChildFilter(false, groupIndex, query, flags);
    }

    @Nullable
    @Override
    public final List<ChildType> applyChildFilter(final boolean filterEmptyGroup,
                                                  final int groupIndex, @NonNull final String query,
                                                  final int flags) {
        Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
        List<ChildType> filteredChildren = group.getChildAdapter().applyFilter(query, flags);

        if (filteredChildren != null) {
            notifyOnApplyChildFilter(query, flags, null, group.getData(), groupIndex,
                    filteredChildren, getAllChildren(groupIndex));
            notifyObserversOnDataSetChanged();
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

        return filteredChildren;
    }

    @Nullable
    @Override
    public final List<ChildType> applyChildFilter(@NonNull final String query, final int flags,
                                                  @NonNull final Filter<ChildType> filter) {
        return applyChildFilter(false, query, flags, filter);
    }

    @Nullable
    @Override
    public final List<ChildType> applyChildFilter(final boolean filterEmptyGroups,
                                                  @NonNull final String query, final int flags,
                                                  @NonNull final Filter<ChildType> filter) {
        List<ChildType> result = null;

        for (int i = 0; i < getGroupCount(); i++) {
            List<ChildType> filteredChildren = applyChildFilter(i, query, flags, filter);

            if (filteredChildren != null) {
                if (result == null) {
                    result = new ArrayList<>();
                }

                result.addAll(filteredChildren);
            }
        }

        if (filterEmptyGroups) {
            applyGroupFilter("", Group.FLAG_FILTER_EMPTY_GROUPS);
        }

        return result != null ? new UnmodifiableList<>(result) : null;
    }

    @Nullable
    @Override
    public final List<ChildType> applyChildFilter(@NonNull final GroupType group,
                                                  @NonNull final String query, final int flags,
                                                  @NonNull final Filter<ChildType> filter) {
        return applyChildFilter(false, group, query, flags, filter);
    }

    @Nullable
    @Override
    public final List<ChildType> applyChildFilter(final boolean filterEmptyGroup,
                                                  @NonNull final GroupType group,
                                                  @NonNull final String query, final int flags,
                                                  @NonNull final Filter<ChildType> filter) {
        return applyChildFilter(filterEmptyGroup, indexOfGroupOrThrowException(group), query, flags,
                filter);
    }

    @Nullable
    @Override
    public final List<ChildType> applyChildFilter(final int groupIndex, @NonNull final String query,
                                                  final int flags,
                                                  @NonNull final Filter<ChildType> filter) {
        return applyChildFilter(false, groupIndex, query, flags, filter);
    }

    @Nullable
    @Override
    public final List<ChildType> applyChildFilter(final boolean filterEmptyGroup,
                                                  final int groupIndex, @NonNull final String query,
                                                  final int flags,
                                                  @NonNull final Filter<ChildType> filter) {
        Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
        List<ChildType> filteredChildren =
                group.getChildAdapter().applyFilter(query, flags, filter);

        if (filteredChildren != null) {
            notifyOnApplyChildFilter(query, flags, filter, group.getData(), groupIndex,
                    filteredChildren, getAllChildren(groupIndex));
            notifyObserversOnDataSetChanged();
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

        return filteredChildren;
    }

    @Override
    public final boolean resetChildFilter(@NonNull final String query, final int flags) {
        boolean result = true;
        boolean emptyGroupFilterApplied = isGroupFilterApplied("", Group.FLAG_FILTER_EMPTY_GROUPS);
        resetGroupFilter("", Group.FLAG_FILTER_EMPTY_GROUPS);

        for (int i = 0; i < getGroupCount(); i++) {
            result &= resetChildFilter(i, query, flags,
                    emptyGroupFilterApplied && i == getGroupCount() - 1);
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
        return resetChildFilter(groupIndex, query, flags,
                isGroupFilterApplied("", Group.FLAG_FILTER_EMPTY_GROUPS));
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
        Set<? extends FilterQuery> filterQueries = getChildFilterQueries();

        for (FilterQuery filterQuery : filterQueries) {
            resetChildFilter(groupIndex, filterQuery.getQuery(), filterQuery.getFlags());
        }

        String message =
                "Reseted all previously applied child filters of group \"" + getGroup(groupIndex) +
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