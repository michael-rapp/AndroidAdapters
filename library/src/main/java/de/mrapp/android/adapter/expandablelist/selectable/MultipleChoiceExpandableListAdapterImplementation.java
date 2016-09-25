/*
 * Copyright 2014 - 2016 Michael Rapp
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
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import de.mrapp.android.adapter.ChoiceMode;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapter;
import de.mrapp.android.adapter.MultipleChoiceExpandableListAdapter;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.SelectableExpandableListDecorator;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.datastructure.group.UnmodifiableGroupList;
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
 * which multiple items can be selected at once. Such an adapter's purpose is to provide the
 * underlying data for visualization using a {@link ExpandableListView} widget.
 *
 * @param <GroupType>
 *         The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public class MultipleChoiceExpandableListAdapterImplementation<GroupType, ChildType>
        extends AbstractSelectableExpandableListAdapter<GroupType, ChildType>
        implements MultipleChoiceExpandableListAdapter<GroupType, ChildType> {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates and returns a listener, which allows to triggerSelection an item, when it is clicked by the
     * user.
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
                    getLogger().logVerbose(getClass(), "Triggering group selection on click...");
                    triggerGroupSelection(index);
                }
            }

            @Override
            public void onChildClicked(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final ChildType child, final int childIndex,
                    @NonNull final GroupType group, final int groupIndex) {
                if (isChildSelectedOnClick() && getChoiceMode() != ChoiceMode.GROUPS_ONLY) {
                    getLogger().logVerbose(getClass(), "Triggering child selection on click...");
                    triggerChildSelection(groupIndex, childIndex);
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
     * Creates a new adapter, whose underlying data is managed as a list of arbitrary group and
     * child items, of which multiple items can be selected at once.
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
     *         false otherwiseo
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
     */
    protected MultipleChoiceExpandableListAdapterImplementation(@NonNull final Context context,
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
                                                                @NonNull final ChoiceMode choiceMode) {
        super(context, decorator, logLevel, groupAdapter, allowDuplicateChildren, notifyOnChange,
                triggerGroupExpansionOnClick, itemClickListeners, itemLongClickListeners,
                adapterListeners, expansionListeners, setChildEnableStatesImplicitly,
                enableStateListeners, numberOfGroupStates, numberOfChildStates,
                triggerGroupStateOnClick, triggerChildStateOnClick, setChildStatesImplicitly,
                itemStateListeners, sortingListeners, filterListeners, selectGroupOnClick,
                selectChildOnClick, expandGroupOnSelection, expandGroupOnChildSelection,
                selectionListeners, choiceMode);
        addItemClickListener(createItemClickListener());
    }

    /**
     * Creates a new adapter, whose underlying data is managed as a list of arbitrary group and
     * child items, of which multiple items can be selected at once.
     *
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param decorator
     *         The decorator, which should be used to customize the appearance of the views, which
     *         are used to visualize the group and child items of the adapter, as an instance of the
     *         generic type DecoratorType. The decorator may not be null
     * @param choiceMode
     *         The choice mode of the adapter as a value of the enum {@link ChoiceMode} they belong
     *         to, is selected, false otherwise
     */
    public MultipleChoiceExpandableListAdapterImplementation(@NonNull final Context context,
                                                             @NonNull final SelectableExpandableListDecorator<GroupType, ChildType> decorator,
                                                             @NonNull final ChoiceMode choiceMode) {
        this(context, decorator, LogLevel.ALL,
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
                choiceMode);
    }

    @Override
    public final int getFirstSelectedGroupIndex() {
        return getGroupAdapter().getFirstSelectedIndex();
    }

    @Override
    public final GroupType getFirstSelectedGroup() {
        return getGroupAdapter().getFirstSelectedItem().getData();
    }

    @Override
    public final int getLastSelectedGroupIndex() {
        return getGroupAdapter().getLastSelectedIndex();
    }

    @Override
    public final GroupType getLastSelectedGroup() {
        return getGroupAdapter().getLastSelectedItem().getData();
    }

    @Override
    public final int getFirstUnselectedGroupIndex() {
        return getGroupAdapter().getFirstUnselectedIndex();
    }

    @Override
    public final GroupType getFirstUnselectedGroup() {
        return getGroupAdapter().getFirstUnselectedItem().getData();
    }

    @Override
    public final int getLastUnselectedGroupIndex() {
        return getGroupAdapter().getLastUnselectedIndex();
    }

    @Override
    public final GroupType getLastUnselectedGroup() {
        return getGroupAdapter().getLastUnselectedItem().getData();
    }

    @Override
    public final List<Integer> getSelectedGroupIndices() {
        return getGroupAdapter().getSelectedIndices();
    }

    @Override
    public final List<GroupType> getSelectedGroups() {
        return new UnmodifiableGroupList<>(getGroupAdapter().getSelectedItems());
    }

    @Override
    public final List<Integer> getUnselectedGroupIndices() {
        return getGroupAdapter().getUnselectedIndices();
    }

    @Override
    public final List<GroupType> getUnselectedGroups() {
        return new UnmodifiableGroupList<>(getGroupAdapter().getUnselectedItems());
    }

    @Override
    public final boolean setGroupSelected(final int index, final boolean selected) {
        if (getChoiceMode() == ChoiceMode.CHILDREN_ONLY) {
            throw new IllegalStateException(
                    "Groups are not allowed to be selected when using the choice mode " +
                            getChoiceMode());
        }

        MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter = getGroupAdapter();
        Group<GroupType, ChildType> group = groupAdapter.getItem(index);

        if (groupAdapter.isEnabled(index)) {
            if (groupAdapter.isSelected(index) != selected) {
                getGroupAdapter().setSelected(index, selected);

                if (selected) {
                    notifyOnGroupSelected(group.getData(), index);
                } else {
                    notifyOnGroupUnselected(group.getData(), index);
                }

                notifyObserversOnDataSetChanged();
                String message = selected ? "Selected" :
                        "Unselected" + "group \"" + group.getData() + "\" at index " + index;
                getLogger().logInfo(getClass(), message);

                if (isGroupExpandedOnSelection() && selected) {
                    setGroupExpanded(index, true);
                }

                return true;
            } else {
                String message =
                        "The selection of group \"" + group.getData() + " at index " + index +
                                " has not been changed, because it is already " +
                                (selected ? "selected" : "unselected");
                getLogger().logDebug(getClass(), message);
                return false;
            }
        } else {
            String message = "Group \"" + group.getData() + " at index " + index +
                    " not selected, because it is disabled";
            getLogger().logDebug(getClass(), message);
            return false;
        }
    }

    @Override
    public final boolean setGroupSelected(@NonNull final GroupType group, final boolean selected) {
        return setGroupSelected(indexOfGroupOrThrowException(group), selected);
    }

    @Override
    public final boolean triggerGroupSelection(final int index) {
        if (isGroupSelected(index)) {
            return setGroupSelected(index, false);
        } else {
            return setGroupSelected(index, true);
        }
    }

    @Override
    public final boolean triggerGroupSelection(@NonNull final GroupType group) {
        return triggerGroupSelection(indexOfGroupOrThrowException(group));
    }

    @Override
    public final boolean setAllGroupsSelected(final boolean selected) {
        boolean result = true;

        for (int i = 0; i < getGroupCount(); i++) {
            result &= setGroupSelected(i, selected);
        }

        return result;
    }

    @Override
    public final boolean triggerAllGroupSelections() {
        boolean result = true;

        for (int i = 0; i < getGroupCount(); i++) {
            result &= triggerGroupSelection(i);
        }

        return result;
    }

    @Override
    public final int getFirstSelectedChildIndex(@NonNull final GroupType group) {
        return getFirstSelectedChildIndex(indexOfGroupOrThrowException(group));
    }

    @Override
    public final int getFirstSelectedChildIndex(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getFirstSelectedIndex();
    }

    @Override
    public final ChildType getFirstSelectedChild(@NonNull final GroupType group) {
        return getFirstSelectedChild(indexOfGroupOrThrowException(group));
    }

    @Override
    public final ChildType getFirstSelectedChild(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getFirstSelectedItem();
    }

    @Override
    public final int getLastSelectedChildIndex(@NonNull final GroupType group) {
        return getLastSelectedChildIndex(indexOfGroupOrThrowException(group));
    }

    @Override
    public final int getLastSelectedChildIndex(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getLastSelectedIndex();
    }

    @Override
    public final ChildType getLastSelectedChild(@NonNull final GroupType group) {
        return getLastSelectedChild(indexOfGroupOrThrowException(group));
    }

    @Override
    public final ChildType getLastSelectedChild(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getLastSelectedItem();
    }

    @Override
    public final int getFirstUnselectedChildIndex(@NonNull final GroupType group) {
        return getFirstUnselectedChildIndex(indexOfGroupOrThrowException(group));
    }

    @Override
    public final int getFirstUnselectedChildIndex(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getFirstUnselectedIndex();
    }

    @Override
    public final ChildType getFirstUnselectedChild(@NonNull final GroupType group) {
        return getFirstUnselectedChild(indexOfGroupOrThrowException(group));
    }

    @Override
    public final ChildType getFirstUnselectedChild(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getFirstUnselectedItem();
    }

    @Override
    public final int getLastUnselectedChildIndex(@NonNull final GroupType group) {
        return getLastUnselectedChildIndex(indexOfGroupOrThrowException(group));
    }

    @Override
    public final int getLastUnselectedChildIndex(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getLastUnselectedIndex();
    }

    @Override
    public final ChildType getLastUnselectedChild(@NonNull final GroupType group) {
        return getLastUnselectedChild(indexOfGroupOrThrowException(group));
    }

    @Override
    public final ChildType getLastUnselectedChild(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getLastUnselectedItem();
    }

    @Override
    public final List<Integer> getSelectedChildIndices(@NonNull final GroupType group) {
        return getSelectedChildIndices(indexOfGroupOrThrowException(group));
    }

    @Override
    public final List<Integer> getSelectedChildIndices(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getSelectedIndices();
    }

    @Override
    public final List<ChildType> getSelectedChildren(@NonNull final GroupType group) {
        return getSelectedChildren(indexOfGroupOrThrowException(group));
    }

    @Override
    public final List<ChildType> getSelectedChildren(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getSelectedItems();
    }

    @Override
    public final List<Integer> getUnselectedChildIndices(@NonNull final GroupType group) {
        return getUnselectedChildIndices(indexOfGroupOrThrowException(group));
    }

    @Override
    public final List<Integer> getUnselectedChildIndices(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getUnselectedIndices();
    }

    @Override
    public final List<ChildType> getUnselectedChildren(@NonNull final GroupType group) {
        return getUnselectedChildren(indexOfGroupOrThrowException(group));
    }

    @Override
    public final List<ChildType> getUnselectedChildren(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getUnselectedItems();
    }

    @Override
    public final boolean setChildSelected(@NonNull final GroupType group, final int childIndex,
                                          final boolean selected) {
        return setChildSelected(indexOfGroupOrThrowException(group), childIndex, selected);
    }

    @Override
    public final boolean setChildSelected(@NonNull final GroupType group,
                                          @NonNull final ChildType child, final boolean selected) {
        return setChildSelected(indexOfGroupOrThrowException(group), child, selected);
    }

    @Override
    public final boolean setChildSelected(final int groupIndex, final int childIndex,
                                          final boolean selected) {
        if (getChoiceMode() == ChoiceMode.GROUPS_ONLY) {
            throw new IllegalStateException(
                    "Children are not allowed to be selected when using the choice mode " +
                            getChoiceMode());
        }

        Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
        MultipleChoiceListAdapter<ChildType> childAdapter = group.getChildAdapter();

        if (childAdapter.isEnabled(childIndex)) {
            if (childAdapter.isSelected(childIndex) != selected) {
                childAdapter.setSelected(childIndex, selected);

                if (selected) {
                    notifyOnChildSelected(group.getData(), groupIndex,
                            childAdapter.getItem(childIndex), childIndex);
                } else {
                    notifyOnChildUnselected(group.getData(), groupIndex,
                            childAdapter.getItem(childIndex), childIndex);
                }

                notifyObserversOnDataSetChanged();
                String message = selected ? "Selected" :
                        "Unselected" + " child \"" + childAdapter.getItemId(childIndex) +
                                "\" at index " + childIndex + " of group \"" + group.getData() +
                                "\" at index " + groupIndex;
                getLogger().logInfo(getClass(), message);

                if (isGroupExpandedOnChildSelection() && selected) {
                    setGroupExpanded(groupIndex, true);
                }

                return true;
            } else {
                String message = "The selection of child \"" + childAdapter.getItemId(childIndex) +
                        " at index " + childIndex + " of group \"" + group.getData() +
                        "\" at index " + groupIndex + " not selected, because it is disabled";
                getLogger().logDebug(getClass(), message);
                return false;
            }
        } else {
            String message =
                    "Child \"" + childAdapter.getItem(childIndex) + " at index " + childIndex +
                            " of group \"" + group.getData() + "\" at index " + groupIndex +
                            " not selected, because it is disabled";
            getLogger().logDebug(getClass(), message);
            return false;
        }
    }

    @Override
    public final boolean setChildSelected(final int groupIndex, @NonNull final ChildType child,
                                          final boolean selected) {
        return setChildSelected(groupIndex, indexOfChildOrThrowException(groupIndex, child),
                selected);
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
        if (isChildSelected(groupIndex, childIndex)) {
            return setChildSelected(groupIndex, childIndex, false);
        } else {
            return setChildSelected(groupIndex, childIndex, true);
        }
    }

    @Override
    public final boolean triggerChildSelection(final int groupIndex,
                                               @NonNull final ChildType child) {
        return triggerChildSelection(groupIndex, indexOfChildOrThrowException(groupIndex, child));
    }

    @Override
    public final boolean setAllChildrenSelected(final boolean selected) {
        boolean result = true;

        for (int i = 0; i < getGroupCount(); i++) {
            result &= setAllChildrenSelected(i, selected);
        }

        return result;
    }

    @Override
    public final boolean setAllChildrenSelected(@NonNull final GroupType group,
                                                final boolean selected) {
        return setAllChildrenSelected(indexOfGroupOrThrowException(group), selected);
    }

    @Override
    public final boolean setAllChildrenSelected(final int groupIndex, final boolean selected) {
        boolean result = true;

        for (int i = 0; i < getChildCount(groupIndex); i++) {
            result &= setChildSelected(groupIndex, i, selected);
        }

        return result;
    }

    @Override
    public final boolean triggerAllChildSelections(@NonNull final GroupType group) {
        return triggerAllChildSelections(indexOfGroupOrThrowException(group));
    }

    @Override
    public final boolean triggerAllChildSelections(final int groupIndex) {
        boolean result = true;

        for (int i = 0; i < getChildCount(groupIndex); i++) {
            result &= triggerChildSelection(groupIndex, i);
        }

        return result;
    }

    @Override
    public final String toString() {
        return "MultipleChoiceExpandableListAdapter (" + getGroupCount() + " groups, " +
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
                isGroupExpandedOnChildSelection() + "]";
    }

    @Override
    public final MultipleChoiceExpandableListAdapterImplementation<GroupType, ChildType> clone()
            throws CloneNotSupportedException {
        return new MultipleChoiceExpandableListAdapterImplementation<>(getContext(), getDecorator(),
                getLogLevel(), getGroupAdapter(), areDuplicateChildrenAllowed(),
                isNotifiedOnChange(), isGroupExpansionTriggeredOnClick(), getItemClickListeners(),
                getItemLongClickListeners(), getAdapterListeners(), getExpansionListeners(),
                areChildEnableStatesSetImplicitly(), getEnableStateListeners(),
                getNumberOfGroupStates(), getNumberOfChildStates(), isGroupStateTriggeredOnClick(),
                isChildStateTriggeredOnClick(), areChildStatesSetImplicitly(),
                getItemStateListeners(), getSortingListeners(), getFilterListeners(),
                isGroupSelectedOnClick(), isChildSelectedOnClick(), isGroupExpandedOnSelection(),
                isGroupExpandedOnChildSelection(), getSelectionListeners(), getChoiceMode());
    }

}