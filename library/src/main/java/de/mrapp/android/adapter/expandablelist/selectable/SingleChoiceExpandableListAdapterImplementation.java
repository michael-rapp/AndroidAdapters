/*
 * Copyright 2014 - 2017 Michael Rapp
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
package de.mrapp.android.adapter.expandablelist.selectable;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import de.mrapp.android.adapter.ChoiceMode;
import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.SelectableExpandableListDecorator;
import de.mrapp.android.adapter.SingleChoiceExpandableListAdapter;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterItemClickListener;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterItemLongClickListener;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterListener;
import de.mrapp.android.adapter.expandablelist.ExpansionListener;
import de.mrapp.android.adapter.expandablelist.NullObjectDecorator;
import de.mrapp.android.adapter.expandablelist.enablestate.ExpandableListEnableStateListener;
import de.mrapp.android.adapter.expandablelist.filterable.ExpandableListFilterListener;
import de.mrapp.android.adapter.expandablelist.itemstate.ExpandableListItemStateListener;
import de.mrapp.android.adapter.expandablelist.sortable.ExpandableListSortingListener;
import de.mrapp.android.adapter.list.selectable.MultipleChoiceListAdapterImplementation;
import de.mrapp.android.adapter.logging.LogLevel;

/**
 * An adapter, whose underlying data is managed as a list of arbitrary group and child items, of
 * which only one item can be selected at once. Such an adapter's purpose is to provide the
 * underlying data for visualization using a {@link ExpandableListView} widget.
 *
 * @param <GroupType>
 *         The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public class SingleChoiceExpandableListAdapterImplementation<GroupType, ChildType>
        extends AbstractSelectableExpandableListAdapter<GroupType, ChildType>
        implements SingleChoiceExpandableListAdapter<GroupType, ChildType> {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The key, which is used to store, whether the adapter's selection is adapted automatically, or
     * not, within a bundle.
     */
    @VisibleForTesting
    protected static final String ADAPT_SELECTION_AUTOMATICALLY_BUNDLE_KEY =
            SingleChoiceExpandableListAdapterImplementation.class.getSimpleName() +
                    "::AdaptSelectionAutomatically";

    /**
     * True, if the adapter's selection is adapted automatically, false otherwise.
     */
    private boolean adaptSelectionAutomatically;

    /**
     * Creates and returns a listener, which allows to triggerSelection an item, when it is clicked
     * by the user.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * ExpandableListAdapterItemClickListener}
     */
    private ExpandableListAdapterItemClickListener<GroupType, ChildType> createItemClickListener() {
        return new ExpandableListAdapterItemClickListener<GroupType, ChildType>() {

            @Override
            public void onGroupClicked(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final GroupType group, final int index) {
                if (isGroupSelectedOnClick() && getChoiceMode() != ChoiceMode.CHILDREN_ONLY) {
                    getLogger().logVerbose(getClass(), "Selecting group on click...");
                    selectGroupItem(index);
                }
            }

            @Override
            public void onChildClicked(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final ChildType child, final int childIndex,
                    @NonNull final GroupType group, final int groupIndex) {
                if (isChildSelectedOnClick() && getChoiceMode() != ChoiceMode.GROUPS_ONLY) {
                    getLogger().logVerbose(getClass(), "Selecting child on click...");
                    selectChildItem(groupIndex, childIndex);
                }
            }

            @Override
            public void onHeaderClicked(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final View view, final int index) {

            }

            @Override
            public void onFooterClicked(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final View view, final int index) {

            }

        };
    }

    /**
     * Unselects a specific group and all of its children.
     *
     * @param groupIndex
     *         The index of the group, which should be unselected, as an {@link Integer} value
     * @param group
     *         The group, which should be unselected, as an instance of the class {@link Group}
     */
    private void unselectGroupAndChildren(final int groupIndex,
                                          @NonNull final Group<GroupType, ChildType> group) {
        if (getGroupAdapter().isSelected(groupIndex)) {
            getGroupAdapter().setSelected(groupIndex, false);
            notifyOnGroupUnselected(group.getData(), groupIndex);
            String message = "Unselected group \"" + group.getData() + "\" at index " + groupIndex;
            getLogger().logInfo(getClass(), message);
        }

        unselectChildren(groupIndex, group);
    }

    /**
     * Unselects all children of a specific group.
     *
     * @param groupIndex
     *         The index of the group, whose children should be unselected, as an {@link Integer}
     *         value
     * @param group
     *         The group, whose children should be unselected, as an instance of the class {@link
     *         Group}
     */
    private void unselectChildren(final int groupIndex,
                                  @NonNull final Group<GroupType, ChildType> group) {
        if (getChoiceMode() != ChoiceMode.GROUPS_ONLY) {
            MultipleChoiceListAdapter<ChildType> childAdapter = group.getChildAdapter();

            for (int i = 0; i < childAdapter.getCount(); i++) {
                if (childAdapter.isSelected(i)) {
                    childAdapter.setSelected(i, false);
                    notifyOnChildUnselected(group.getData(), groupIndex, childAdapter.getItem(i),
                            i);
                    String message =
                            "Unselected child \"" + childAdapter.getItem(i) + "\" at index " + i +
                                    " of group " + group.getData() + " at index " + groupIndex;
                    getLogger().logInfo(getClass(), message);
                }
            }
        }
    }

    /**
     * Selects the nearest enabled group or child item, starting at a specific group index and
     * optionally child index. If a child index is given, the item is searched within the group,
     * which belongs to the given index, at first. Otherwise, an enabled group item is searched at
     * first.If neither a child, nor a group item could be selected in this initial step, the search
     * is continued amongst the children, which have not been considered yet.
     *
     * @param groupIndex
     *         The index of the group, the search for the nearest enabled item should be started at,
     *         as an {@link Integer} value
     * @param childIndex
     *         The index of the child, the search for the nearest enabled item should be started at,
     *         as an {@link Integer} value or -1, if no child index is available
     */
    private void selectNearestEnabledItem(final int groupIndex, final int childIndex) {
        boolean selected;

        if (childIndex != -1) {
            selected = selectNearestEnabledChildItem(groupIndex, childIndex);

            if (!selected) {
                selected = selectNearestEnabledGroupItem(groupIndex);
            }
        } else {
            selected = selectNearestEnabledGroupItem(groupIndex);
        }

        if (!selected) {
            int i = 0;

            while (i < getGroupCount() && !selected) {
                if (i != childIndex) {
                    selected = selectNearestEnabledChildItem(i, 0);
                }

                i++;
            }
        }
    }

    /**
     * Selects the nearest enabled group item, starting at a specific group index. The group item is
     * searched alternately by ascending and descending indices. If no enabled group item is
     * available, no item will be selected.
     *
     * @param groupIndex
     *         The index of the group, the search for the nearest enabled group item should be
     *         started at, as an {@link Integer} value
     * @return True, if a group item has been selected, false otherwise
     */
    private boolean selectNearestEnabledGroupItem(final int groupIndex) {
        if (getChoiceMode() != ChoiceMode.CHILDREN_ONLY) {
            int ascendingIndex = groupIndex;
            int descendingIndex = groupIndex - 1;

            while (ascendingIndex < getGroupCount() || descendingIndex >= 0) {
                if (ascendingIndex < getGroupCount() && isGroupEnabled(ascendingIndex)) {
                    selectGroupItem(ascendingIndex);
                    return true;
                } else if (descendingIndex >= 0 && isGroupEnabled(descendingIndex)) {
                    selectGroupItem(descendingIndex);
                    return true;
                }

                ascendingIndex++;
                descendingIndex--;
            }
        }

        return false;
    }

    /**
     * Selects the nearest enabled child item of a group, starting at a specific child index. The
     * child item is searched alternately by ascending and descending indices. If no enabled child
     * item is available, no item will be selected.
     *
     * @param groupIndex
     *         The index of the group, the child item, which should be selected, belongs to, as an
     *         {@link Integer} value
     * @param childIndex
     *         The index of the child, the search for the nearest enabled child item should be
     *         started at, as an {@link Integer} value
     * @return True, if a child item has been selected, false otherwise
     */
    private boolean selectNearestEnabledChildItem(final int groupIndex, final int childIndex) {
        if (getChoiceMode() != ChoiceMode.GROUPS_ONLY) {
            int ascendingIndex = childIndex;
            int descendingIndex = childIndex - 1;

            while (ascendingIndex < getChildCount(groupIndex) || descendingIndex >= 0) {
                if (ascendingIndex < getChildCount(groupIndex) &&
                        isChildEnabled(groupIndex, ascendingIndex)) {
                    selectChildItem(groupIndex, ascendingIndex);
                    return true;
                } else if (descendingIndex >= 0 && isChildEnabled(groupIndex, descendingIndex)) {
                    selectChildItem(groupIndex, descendingIndex);
                    return true;
                }

                ascendingIndex++;
                descendingIndex--;
            }
        }

        return false;
    }

    /**
     * Selects the group item, which corresponds to a specific index. This causes all other items to
     * become unselected.
     *
     * @param groupIndex
     *         The index of the group item, which should be selected, as an {@link Integer} value
     */
    private void selectGroupItem(final int groupIndex) {
        if (isGroupEnabled(groupIndex)) {
            if (!isGroupSelected(groupIndex)) {
                for (int i = 0; i < getGroupCount(); i++) {
                    Group<GroupType, ChildType> currentGroup = getGroupAdapter().getItem(i);

                    if (i == groupIndex) {
                        getGroupAdapter().setSelected(i, true);
                        notifyOnGroupSelected(currentGroup.getData(), i);
                        String message =
                                "Selected group \"" + currentGroup.getData() + "\" at index " + i;
                        getLogger().logInfo(getClass(), message);
                        unselectChildren(groupIndex, currentGroup);
                    } else {
                        unselectGroupAndChildren(i, currentGroup);
                    }
                }

                if (isGroupExpandedOnSelection()) {
                    setGroupExpanded(groupIndex, true);
                }

                notifyObserversOnDataSetChanged();
            }
        }
    }

    /**
     * Selects the child item, which corresponds to a specific index of a specific group. This
     * causes all other items to become unselected.
     *
     * @param groupIndex
     *         The index of the group, the child item, which should be selected, belongs to, as an
     *         {@link Integer} value
     * @param childIndex
     *         The index of the child item, which should be selected, as an {@link Integer} value
     */
    private void selectChildItem(final int groupIndex, final int childIndex) {
        MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter = getGroupAdapter();
        Group<GroupType, ChildType> group = groupAdapter.getItem(groupIndex);
        MultipleChoiceListAdapter<ChildType> childAdapter = group.getChildAdapter();

        if (childAdapter.isEnabled(childIndex)) {
            if (!childAdapter.isSelected(childIndex)) {
                groupAdapter.setSelected(groupIndex, false);

                for (int i = 0; i < getGroupCount(); i++) {
                    if (i != groupIndex) {
                        unselectGroupAndChildren(i, groupAdapter.getItem(i));
                    }
                }

                for (int i = 0; i < childAdapter.getCount(); i++) {
                    if (i == childIndex) {
                        childAdapter.setSelected(i, true);
                        notifyOnChildSelected(group.getData(), groupIndex, childAdapter.getItem(i),
                                i);
                        String message =
                                "Selected child \"" + childAdapter.getItemId(i) + "\" at index " +
                                        i + " of group \"" + group.getData() + "\" at index " +
                                        groupIndex;
                        getLogger().logInfo(getClass(), message);
                    } else if (childAdapter.isSelected(i)) {
                        childAdapter.setSelected(i, false);
                        notifyOnChildUnselected(group.getData(), groupIndex,
                                childAdapter.getItem(i), i);
                        String message =
                                "Unselected child \"" + childAdapter.getItem(i) + "\" at index " +
                                        i + " of group \"" + group.getData() + " at index " +
                                        groupIndex;
                        getLogger().logInfo(getClass(), message);
                    }
                }

                if (isGroupExpandedOnChildSelection()) {
                    setGroupExpanded(groupIndex, true);
                }

                notifyObserversOnDataSetChanged();
            }
        }
    }

    /**
     * Creates and returns a listener, which allows to adapt the selections of the adapter's items,
     * when an item has been removed from or added to the adapter.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * ExpandableListAdapterListener}
     */
    private ExpandableListAdapterListener<GroupType, ChildType> createAdapterListener() {
        return new ExpandableListAdapterListener<GroupType, ChildType>() {

            @Override
            public void onGroupAdded(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final GroupType group, final int index) {
                if (isSelectionAdaptedAutomatically() && getGroupCount() == 1 &&
                        getChoiceMode() != ChoiceMode.CHILDREN_ONLY) {
                    selectGroupItem(0);
                }
            }

            @Override
            public void onGroupRemoved(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final GroupType group, final int index) {
                if (isSelectionAdaptedAutomatically() && !isEmpty() &&
                        getSelectedGroupIndex() == -1) {
                    selectNearestEnabledItem(index, -1);
                }
            }

            @Override
            public void onChildAdded(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final ChildType child, final int childIndex,
                    @NonNull final GroupType group, final int groupIndex) {
                if (isSelectionAdaptedAutomatically() &&
                        getChoiceMode() != ChoiceMode.GROUPS_ONLY &&
                        getSelectedGroupIndex() == -1) {
                    selectNearestEnabledItem(groupIndex, childIndex);
                }
            }

            @Override
            public void onChildRemoved(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final ChildType child, final int childIndex,
                    @NonNull final GroupType group, final int groupIndex) {
                if (isSelectionAdaptedAutomatically() && !isEmpty() &&
                        getSelectedGroupIndex() == -1) {
                    selectNearestEnabledItem(groupIndex, childIndex);
                }
            }

        };
    }

    /**
     * Creates and returns a listener, which allows to adapt the selections of the adapter's items,
     * when an item has been enabled or disabled.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * ExpandableListEnableStateListener}
     */
    private ExpandableListEnableStateListener<GroupType, ChildType> createEnableStateListener() {
        return new ExpandableListEnableStateListener<GroupType, ChildType>() {

            @Override
            public void onGroupEnabled(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final GroupType group, final int index) {
                if (isSelectionAdaptedAutomatically() &&
                        getChoiceMode() != ChoiceMode.CHILDREN_ONLY &&
                        getSelectedGroupIndex() == -1) {
                    selectGroupItem(index);
                }
            }

            @Override
            public void onGroupDisabled(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final GroupType group, final int index) {
                if (isSelectionAdaptedAutomatically() && getSelectedGroupIndex() == -1) {
                    selectNearestEnabledItem(index, -1);
                }
            }

            @Override
            public void onChildEnabled(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final ChildType child, final int childIndex,
                    @NonNull final GroupType group, final int groupIndex) {
                if (isSelectionAdaptedAutomatically() &&
                        getChoiceMode() != ChoiceMode.GROUPS_ONLY &&
                        getSelectedGroupIndex() == -1) {
                    selectChildItem(groupIndex, childIndex);
                }
            }

            @Override
            public void onChildDisabled(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final ChildType child, final int childIndex,
                    @NonNull final GroupType group, final int groupIndex) {
                if (isSelectionAdaptedAutomatically() && getSelectedGroupIndex() == -1) {
                    selectNearestEnabledItem(groupIndex, childIndex);
                }
            }

        };
    }

    /**
     * Creates and returns a listener, which allows to adapt the selections of the adapter's items,
     * when an the adapter's underlying data has been filtered.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * ExpandableListFilterListener}
     */
    private ExpandableListFilterListener<GroupType, ChildType> createFilterListener() {
        return new ExpandableListFilterListener<GroupType, ChildType>() {

            @Override
            public void onApplyGroupFilter(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final String query, final int flags,
                    @Nullable final Filter<GroupType> filter,
                    @NonNull final List<GroupType> filteredItems,
                    @NonNull final List<GroupType> unfilteredGroups) {
                if (isSelectionAdaptedAutomatically() && !isEmpty() &&
                        getSelectedGroupIndex() == -1) {
                    selectNearestEnabledItem(0, -1);
                }
            }

            @Override
            public void onResetGroupFilter(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final String query, final int flags,
                    @NonNull final List<GroupType> unfilteredGroups) {
                if (isSelectionAdaptedAutomatically() && !isEmpty() &&
                        getSelectedGroupIndex() == -1) {
                    selectNearestEnabledItem(0, -1);
                }
            }

            @Override
            public void onApplyChildFilter(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final String query, final int flags,
                    @Nullable final Filter<ChildType> filter, @NonNull final GroupType group,
                    final int groupIndex, @NonNull final List<ChildType> filteredChildren,
                    @NonNull final List<ChildType> unfilteredChildren) {
                if (isSelectionAdaptedAutomatically() && getSelectedGroupIndex() == -1) {
                    selectNearestEnabledItem(groupIndex, 0);
                }
            }

            @Override
            public void onResetChildFilter(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final String query, final int flags, @NonNull final GroupType group,
                    final int groupIndex, @NonNull final List<ChildType> filteredChildren) {
                if (isSelectionAdaptedAutomatically() && !isEmpty() &&
                        getSelectedGroupIndex() == -1) {
                    selectNearestEnabledItem(groupIndex, 0);
                }
            }

        };
    }

    /**
     * Creates a new adapter, whose underlying data is managed as a list of arbitrary group and
     * child items, of which only one item can be selected at once.
     *
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
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
     * @param triggerGroupExpansionOnClick
     *         True, if a group's expansion should be triggered, when it is clicked by the user,
     *         false otherwise
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
     * @param selectGroupOnClick
     *         True, if a group item should be selected, when it is clicked by the user, false
     *         otherwise
     * @param selectChildOnClick
     *         True, if a group item should be selected, when it is clicked by the user, false
     *         otherwise
     * @param expandGroupOnSelection
     *         True, if a group should be expanded automatically, when it is selected, false
     *         otherwise
     * @param expandGroupOnChildSelection
     *         True, if a group should be expanded automatically, when one of its children is
     *         selected, false otherwise
     * @param selectionListeners
     *         A set, which contains the listener, which should be notified, when the selection of
     *         an item has changed, or an empty set, if no listeners should be notified
     * @param choiceMode
     *         The choice mode of the adapter as a value of the enum {@link ChoiceMode}
     * @param adaptSelectionAutomatically
     *         True, if the adapter's selection should be automatically adapted, false otherwise
     */
    protected SingleChoiceExpandableListAdapterImplementation(@NonNull final Context context,
                                                              @NonNull final SelectableExpandableListDecorator<GroupType, ChildType> decorator,
                                                              @NonNull final LogLevel logLevel,
                                                              @NonNull final MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter,
                                                              final boolean allowDuplicateChildren,
                                                              final boolean notifyOnChange,
                                                              final boolean triggerGroupExpansionOnClick,
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
                                                              @NonNull final Set<ExpandableListFilterListener<GroupType, ChildType>> filterListeners,
                                                              final boolean selectGroupOnClick,
                                                              final boolean selectChildOnClick,
                                                              final boolean expandGroupOnSelection,
                                                              final boolean expandGroupOnChildSelection,
                                                              @NonNull final Set<ExpandableListSelectionListener<GroupType, ChildType>> selectionListeners,
                                                              @NonNull final ChoiceMode choiceMode,
                                                              final boolean adaptSelectionAutomatically) {
        super(context, decorator, logLevel, groupAdapter, allowDuplicateChildren, notifyOnChange,
                triggerGroupExpansionOnClick, itemClickListeners, itemLongClickListeners,
                adapterListeners, expansionListeners, setChildEnableStatesImplicitly,
                enableStateListeners, numberOfGroupStates, numberOfChildStates,
                triggerGroupStateOnClick, triggerChildStateOnClick, setChildStatesImplicitly,
                itemStateListeners, sortingListeners, filterListeners, selectGroupOnClick,
                selectChildOnClick, expandGroupOnSelection, expandGroupOnChildSelection,
                selectionListeners, choiceMode);
        addItemClickListener(createItemClickListener());
        addAdapterListener(createAdapterListener());
        addEnableStateListener(createEnableStateListener());
        addFilterListener(createFilterListener());
        adaptSelectionAutomatically(adaptSelectionAutomatically);
    }

    /**
     * Creates a new adapter, whose underlying data is managed as a list of arbitrary group and
     * child items, of which only one item can be selected at once.
     *
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param decorator
     *         The decorator, which should be used to customize the appearance of the views, which
     *         are used to visualize the group and child items of the adapter, as an instance of the
     *         generic type DecoratorType. The decorator may not be null
     * @param choiceMode
     *         The choice mode of the adapter as a value of the enum {@link ChoiceMode}
     */
    public SingleChoiceExpandableListAdapterImplementation(@NonNull final Context context,
                                                           @NonNull final SelectableExpandableListDecorator<GroupType, ChildType> decorator,
                                                           @NonNull final ChoiceMode choiceMode) {
        this(context, decorator, LogLevel.INFO,
                new MultipleChoiceListAdapterImplementation<>(context,
                        new NullObjectDecorator<Group<GroupType, ChildType>>()), false, true, true,
                new CopyOnWriteArraySet<ExpandableListAdapterItemClickListener<GroupType, ChildType>>(),
                new CopyOnWriteArraySet<ExpandableListAdapterItemLongClickListener<GroupType, ChildType>>(),
                new CopyOnWriteArraySet<ExpandableListAdapterListener<GroupType, ChildType>>(),
                new CopyOnWriteArraySet<ExpansionListener<GroupType, ChildType>>(), true,
                new CopyOnWriteArraySet<ExpandableListEnableStateListener<GroupType, ChildType>>(),
                1, 1, false, false, false,
                new CopyOnWriteArraySet<ExpandableListItemStateListener<GroupType, ChildType>>(),
                new CopyOnWriteArraySet<ExpandableListSortingListener<GroupType, ChildType>>(),
                new CopyOnWriteArraySet<ExpandableListFilterListener<GroupType, ChildType>>(), true,
                true, false, true,
                new CopyOnWriteArraySet<ExpandableListSelectionListener<GroupType, ChildType>>(),
                choiceMode, true);
    }

    @Override
    protected final void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ADAPT_SELECTION_AUTOMATICALLY_BUNDLE_KEY,
                isSelectionAdaptedAutomatically());
    }

    @Override
    protected final void onRestoreInstanceState(@NonNull final Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        adaptSelectionAutomatically =
                savedState.getBoolean(ADAPT_SELECTION_AUTOMATICALLY_BUNDLE_KEY, true);
    }

    @Override
    public final boolean isGroupSelected() {
        return getGroupAdapter().getSelectedItemCount() > 0;
    }

    @Override
    public final boolean isChildSelected() {
        return getSelectedChildIndex() != -1;
    }

    @Override
    public final int getSelectedGroupIndex() {
        int groupIndex = getGroupAdapter().getFirstSelectedIndex();

        if (groupIndex == -1) {
            for (int i = 0; i < getGroupCount(); i++) {
                int childIndex =
                        getGroupAdapter().getItem(i).getChildAdapter().getFirstSelectedIndex();

                if (childIndex != -1) {
                    return i;
                }
            }

            return -1;
        }

        return groupIndex;
    }

    @Override
    public final int getSelectedChildIndex() {
        for (int i = 0; i < getGroupCount(); i++) {
            int childIndex = getGroupAdapter().getItem(i).getChildAdapter().getFirstSelectedIndex();

            if (childIndex != -1) {
                return childIndex;
            }
        }

        return -1;
    }

    @Override
    public final GroupType getSelectedGroup() {
        int groupIndex = getGroupAdapter().getFirstSelectedIndex();

        if (groupIndex != -1) {
            return getGroup(groupIndex);
        }

        return null;
    }

    @Override
    public final ChildType getSelectedChild() {
        for (int i = 0; i < getGroupCount(); i++) {
            ChildType child = getGroupAdapter().getItem(i).getChildAdapter().getFirstSelectedItem();

            if (child != null) {
                return child;
            }
        }

        return null;
    }

    @Override
    public final boolean triggerGroupSelection(final int groupIndex) {
        if (getChoiceMode() == ChoiceMode.CHILDREN_ONLY) {
            throw new IllegalStateException(
                    "Groups are not allowed to be selected when using the choice mode " +
                            getChoiceMode());
        }

        MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter = getGroupAdapter();
        Group<GroupType, ChildType> group = groupAdapter.getItem(groupIndex);

        if (groupAdapter.isEnabled(groupIndex)) {
            if (!groupAdapter.isSelected(groupIndex)) {
                selectGroupItem(groupIndex);
                return true;
            } else {
                groupAdapter.setSelected(groupIndex, false);
                notifyOnGroupUnselected(group.getData(), groupIndex);
                String message = "Unselected group \"" + group.getData() + "\" at index " +
                        groupIndex;
                getLogger().logDebug(getClass(), message);
                notifyObserversOnDataSetChanged();
                return true;
            }
        } else {
            String message =
                    "The selection of group \"" + group.getData() + "\" at index " + groupIndex +
                            " has not been changed, because it is disabled";
            getLogger().logDebug(getClass(), message);
            return false;
        }
    }

    @Override
    public final boolean triggerGroupSelection(@NonNull final GroupType group) {
        return triggerGroupSelection(indexOfGroupOrThrowException(group));
    }

    @Override
    public final boolean triggerChildSelection(@NonNull final GroupType group,
                                               final int childIndex) {
        return triggerChildSelection(indexOfGroupOrThrowException(group), childIndex);
    }

    @Override
    public final boolean triggerChildSelection(@NonNull final GroupType group,
                                               @NonNull final ChildType child) {
        return triggerChildSelection(indexOfGroupOrThrowException(group), child);
    }

    @Override
    public final boolean triggerChildSelection(final int groupIndex, final int childIndex) {
        if (getChoiceMode() == ChoiceMode.GROUPS_ONLY) {
            throw new IllegalStateException(
                    "Children are not allowed to be selected when using the choice mode " +
                            getChoiceMode());
        }

        MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter = getGroupAdapter();
        Group<GroupType, ChildType> group = groupAdapter.getItem(groupIndex);
        MultipleChoiceListAdapter<ChildType> childAdapter = group.getChildAdapter();

        if (childAdapter.isEnabled(childIndex)) {
            if (!childAdapter.isSelected(childIndex)) {
                selectChildItem(groupIndex, childIndex);
                return true;
            } else {
                String message = "The selection of child \"" + childAdapter.getItem(childIndex) +
                        "\" at index " + childIndex + " of group \"" + group.getData() +
                        " at index " + groupIndex +
                        " has not been changed, because it is already selected";
                getLogger().logDebug(getClass(), message);
                return false;
            }
        } else {
            childAdapter.setSelected(childIndex, false);
            ChildType child = childAdapter.getItem(childIndex);
            notifyOnChildUnselected(group.getData(), groupIndex, child, childIndex);
            String message = "Unselected child \"" + child +
                    "\" at index " + childIndex + " of group \"" + group.getData() + " at index " +
                    groupIndex;
            getLogger().logDebug(getClass(), message);
            notifyObserversOnDataSetChanged();
            return false;
        }
    }

    @Override
    public final boolean triggerChildSelection(final int groupIndex,
                                               @NonNull final ChildType child) {
        return triggerChildSelection(groupIndex, indexOfChildOrThrowException(groupIndex, child));
    }

    @Override
    public final void adaptSelectionAutomatically(final boolean adaptSelectionAutomatically) {
        this.adaptSelectionAutomatically = adaptSelectionAutomatically;

        if (adaptSelectionAutomatically && !isEmpty() && getSelectedGroupIndex() == -1) {
            selectNearestEnabledItem(0, -1);
        }
    }

    @Override
    public final boolean isSelectionAdaptedAutomatically() {
        return adaptSelectionAutomatically;
    }

    @Override
    public final String toString() {
        return "SingleChoiceExpandableListAdapter (" + getGroupCount() + " groups, " +
                getChildCount() + " children) [logLevel=" + getLogLevel() + ", parameters=" +
                getParameters() + ", notifyOnChange=" + isNotifiedOnChange() +
                ", allowDuplicateGroups=" + areDuplicateGroupsAllowed() +
                ", allowDuplicateChildren=" + areDuplicateChildrenAllowed() +
                ", numberOfGroupStates=" + getNumberOfGroupStates() + ", numberOfChildStates=" +
                getNumberOfChildStates() + ", triggerGroupStateOnClick=" +
                isGroupStateTriggeredOnClick() + ", triggerChildStateOnClick=" +
                isChildStateTriggeredOnClick() + ", filtered=" + isFiltered() + ", choiceMode=" +
                getChoiceMode() + ", selectGroupOnClick=" + isGroupSelectedOnClick() +
                ", selectChildOnClick=" + isChildSelectedOnClick() + ", expandGroupOnSelection=" +
                isGroupExpandedOnSelection() + ", expandGroupOnChildSelection=" +
                isGroupExpandedOnChildSelection() + ", adaptSelectionAutomatically=" +
                isSelectionAdaptedAutomatically() + "]";
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (adaptSelectionAutomatically ? 1231 : 1237);
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        SingleChoiceExpandableListAdapterImplementation<?, ?> other =
                (SingleChoiceExpandableListAdapterImplementation<?, ?>) obj;
        if (adaptSelectionAutomatically != other.adaptSelectionAutomatically)
            return false;
        return true;
    }

    @Override
    public final SingleChoiceExpandableListAdapterImplementation<GroupType, ChildType> clone()
            throws CloneNotSupportedException {
        return new SingleChoiceExpandableListAdapterImplementation<>(getContext(), getDecorator(),
                getLogLevel(), cloneGroupAdapter(), areDuplicateChildrenAllowed(),
                isNotifiedOnChange(), isGroupExpansionTriggeredOnClick(), getItemClickListeners(),
                getItemLongClickListeners(), getAdapterListeners(), getExpansionListeners(),
                areChildEnableStatesSetImplicitly(), getEnableStateListeners(),
                getNumberOfGroupStates(), getNumberOfChildStates(), isGroupStateTriggeredOnClick(),
                isChildStateTriggeredOnClick(), areChildStatesSetImplicitly(),
                getItemStateListeners(), getSortingListeners(), getFilterListeners(),
                isGroupSelectedOnClick(), isChildSelectedOnClick(), isGroupExpandedOnSelection(),
                isGroupExpandedOnChildSelection(), getSelectionListeners(), getChoiceMode(),
                isSelectionAdaptedAutomatically());
    }

}