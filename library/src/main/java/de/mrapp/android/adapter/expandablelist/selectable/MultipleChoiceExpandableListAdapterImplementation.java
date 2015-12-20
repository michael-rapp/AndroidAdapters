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
package de.mrapp.android.adapter.expandablelist.selectable;

import android.content.Context;
import android.widget.ExpandableListView;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import de.mrapp.android.adapter.ExpandableListAdapter;
import de.mrapp.android.adapter.MultipleChoiceExpandableListAdapter;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.SelectableExpandableListDecorator;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.datastructure.group.UnmodifiableGroupList;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterItemClickListener;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterListener;
import de.mrapp.android.adapter.expandablelist.ExpansionListener;
import de.mrapp.android.adapter.expandablelist.NullObjectDecorator;
import de.mrapp.android.adapter.expandablelist.enablestate.ExpandableListEnableStateListener;
import de.mrapp.android.adapter.expandablelist.filterable.ExpandableListFilterListener;
import de.mrapp.android.adapter.expandablelist.itemstate.ExpandableListItemStateListener;
import de.mrapp.android.adapter.expandablelist.sortable.ExpandableListSortingListener;
import de.mrapp.android.adapter.inflater.Inflater;
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
     * Creates and returns a listener, which allows to select an item, when it is clicked by the
     * user.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * ExpandableListAdapterItemClickListener}
     */
    private ExpandableListAdapterItemClickListener<GroupType, ChildType> createItemClickListener() {
        return new ExpandableListAdapterItemClickListener<GroupType, ChildType>() {

            @Override
            public void onGroupClicked(final ExpandableListAdapter<GroupType, ChildType> adapter,
                                       final GroupType group, final int index) {
                if (isGroupSelectedOnClick() && getChoiceMode() != ChoiceMode.CHILDREN_ONLY) {
                    getLogger().logVerbose(getClass(), "Triggering group selection on click...");
                    triggerGroupSelection(index);
                }
            }

            @Override
            public void onChildClicked(final ExpandableListAdapter<GroupType, ChildType> adapter,
                                       final ChildType child, final int childIndex,
                                       final GroupType group, final int groupIndex) {
                if (isChildSelectedOnClick() && getChoiceMode() != ChoiceMode.GROUPS_ONLY) {
                    getLogger().logVerbose(getClass(), "Triggering child selection on click...");
                    triggerChildSelection(groupIndex, childIndex);
                }
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
    protected MultipleChoiceExpandableListAdapterImplementation(final Context context,
                                                                final Inflater groupInflater,
                                                                final Inflater childInflater,
                                                                final SelectableExpandableListDecorator<GroupType, ChildType> decorator,
                                                                final LogLevel logLevel,
                                                                final MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter,
                                                                final boolean allowDuplicateChildren,
                                                                final boolean notifyOnChange,
                                                                final boolean expandGroupOnClick,
                                                                final Set<ExpandableListAdapterItemClickListener<GroupType, ChildType>> itemClickListeners,
                                                                final Set<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners,
                                                                final Set<ExpansionListener<GroupType, ChildType>> expansionListeners,
                                                                final boolean setChildEnableStatesImplicitly,
                                                                final Set<ExpandableListEnableStateListener<GroupType, ChildType>> enableStateListeners,
                                                                final int numberOfGroupStates,
                                                                final int numberOfChildStates,
                                                                final boolean triggerGroupStateOnClick,
                                                                final boolean triggerChildStateOnClick,
                                                                final boolean setChildStatesImplicitly,
                                                                final Set<ExpandableListItemStateListener<GroupType, ChildType>> itemStateListeners,
                                                                final Set<ExpandableListSortingListener<GroupType, ChildType>> sortingListeners,
                                                                final Set<ExpandableListFilterListener<GroupType, ChildType>> filterListeners,
                                                                final boolean selectGroupOnClick,
                                                                final boolean selectChildOnClick,
                                                                final boolean expandGroupOnSelection,
                                                                final boolean expandGroupOnChildSelection,
                                                                final Set<ExpandableListSelectionListener<GroupType, ChildType>> selectionListeners,
                                                                final ChoiceMode choiceMode) {
        super(context, groupInflater, childInflater, decorator, logLevel, groupAdapter,
                allowDuplicateChildren, notifyOnChange, expandGroupOnClick, itemClickListeners,
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
     * @param choiceMode
     *         The choice mode of the adapter as a value of the enum {@link ChoiceMode} they belong
     *         to, is selected, false otherwise
     */
    public MultipleChoiceExpandableListAdapterImplementation(final Context context,
                                                             final Inflater groupInflater,
                                                             final Inflater childInflater,
                                                             final SelectableExpandableListDecorator<GroupType, ChildType> decorator,
                                                             final ChoiceMode choiceMode) {
        this(context, groupInflater, childInflater, decorator, LogLevel.ALL,
                new MultipleChoiceListAdapterImplementation<>(context, groupInflater,
                        new NullObjectDecorator<Group<GroupType, ChildType>>()), false, true, true,
                new LinkedHashSet<ExpandableListAdapterItemClickListener<GroupType, ChildType>>(),
                new LinkedHashSet<ExpandableListAdapterListener<GroupType, ChildType>>(),
                new LinkedHashSet<ExpansionListener<GroupType, ChildType>>(), true,
                new LinkedHashSet<ExpandableListEnableStateListener<GroupType, ChildType>>(), 1, 1,
                false, false, false,
                new LinkedHashSet<ExpandableListItemStateListener<GroupType, ChildType>>(),
                new LinkedHashSet<ExpandableListSortingListener<GroupType, ChildType>>(),
                new LinkedHashSet<ExpandableListFilterListener<GroupType, ChildType>>(), true, true,
                false, true,
                new LinkedHashSet<ExpandableListSelectionListener<GroupType, ChildType>>(),
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

                notifyOnDataSetChanged();
                String message = selected ? "Selected" :
                        "Unselected" + "group \"" + group.getData() + "\" at index " + index;
                getLogger().logInfo(getClass(), message);

                if (isGroupExpandedOnSelection() && selected) {
                    expandGroup(index);
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
    public final boolean setGroupSelected(final GroupType group, final boolean selected) {
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
    public final boolean triggerGroupSelection(final GroupType group) {
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
    public final int getFirstSelectedChildIndex(final GroupType group) {
        return getFirstSelectedChildIndex(indexOfGroupOrThrowException(group));
    }

    @Override
    public final int getFirstSelectedChildIndex(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getFirstSelectedIndex();
    }

    @Override
    public final ChildType getFirstSelectedChild(final GroupType group) {
        return getFirstSelectedChild(indexOfGroupOrThrowException(group));
    }

    @Override
    public final ChildType getFirstSelectedChild(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getFirstSelectedItem();
    }

    @Override
    public final int getLastSelectedChildIndex(final GroupType group) {
        return getLastSelectedChildIndex(indexOfGroupOrThrowException(group));
    }

    @Override
    public final int getLastSelectedChildIndex(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getLastSelectedIndex();
    }

    @Override
    public final ChildType getLastSelectedChild(final GroupType group) {
        return getLastSelectedChild(indexOfGroupOrThrowException(group));
    }

    @Override
    public final ChildType getLastSelectedChild(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getLastSelectedItem();
    }

    @Override
    public final int getFirstUnselectedChildIndex(final GroupType group) {
        return getFirstUnselectedChildIndex(indexOfGroupOrThrowException(group));
    }

    @Override
    public final int getFirstUnselectedChildIndex(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getFirstUnselectedIndex();
    }

    @Override
    public final ChildType getFirstUnselectedChild(final GroupType group) {
        return getFirstUnselectedChild(indexOfGroupOrThrowException(group));
    }

    @Override
    public final ChildType getFirstUnselectedChild(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getFirstUnselectedItem();
    }

    @Override
    public final int getLastUnselectedChildIndex(final GroupType group) {
        return getLastUnselectedChildIndex(indexOfGroupOrThrowException(group));
    }

    @Override
    public final int getLastUnselectedChildIndex(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getLastUnselectedIndex();
    }

    @Override
    public final ChildType getLastUnselectedChild(final GroupType group) {
        return getLastUnselectedChild(indexOfGroupOrThrowException(group));
    }

    @Override
    public final ChildType getLastUnselectedChild(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getLastUnselectedItem();
    }

    @Override
    public final List<Integer> getSelectedChildIndices(final GroupType group) {
        return getSelectedChildIndices(indexOfGroupOrThrowException(group));
    }

    @Override
    public final List<Integer> getSelectedChildIndices(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getSelectedIndices();
    }

    @Override
    public final List<ChildType> getSelectedChildren(final GroupType group) {
        return getSelectedChildren(indexOfGroupOrThrowException(group));
    }

    @Override
    public final List<ChildType> getSelectedChildren(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getSelectedItems();
    }

    @Override
    public final List<Integer> getUnselectedChildIndices(final GroupType group) {
        return getUnselectedChildIndices(indexOfGroupOrThrowException(group));
    }

    @Override
    public final List<Integer> getUnselectedChildIndices(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getUnselectedIndices();
    }

    @Override
    public final List<ChildType> getUnselectedChildren(final GroupType group) {
        return getUnselectedChildren(indexOfGroupOrThrowException(group));
    }

    @Override
    public final List<ChildType> getUnselectedChildren(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getUnselectedItems();
    }

    @Override
    public final boolean setChildSelected(final GroupType group, final int childIndex,
                                          final boolean selected) {
        return setChildSelected(indexOfGroupOrThrowException(group), childIndex, selected);
    }

    @Override
    public final boolean setChildSelected(final GroupType group, final ChildType child,
                                          final boolean selected) {
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

                notifyOnDataSetChanged();
                String message = selected ? "Selected" :
                        "Unselected" + " child \"" + childAdapter.getItemId(childIndex) +
                                "\" at index " + childIndex + " of group \"" + group.getData() +
                                "\" at index " + groupIndex;
                getLogger().logInfo(getClass(), message);

                if (isGroupExpandedOnChildSelection() && selected) {
                    expandGroup(groupIndex);
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
    public final boolean setChildSelected(final int groupIndex, final ChildType child,
                                          final boolean selected) {
        return setChildSelected(groupIndex, indexOfChildOrThrowException(groupIndex, child),
                selected);
    }

    @Override
    public final boolean triggerChildSelection(final GroupType group, final int childIndex) {
        return triggerChildSelection(indexOfGroupOrThrowException(group), childIndex);
    }

    @Override
    public final boolean triggerChildSelection(final GroupType group, final ChildType child) {
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
    public final boolean triggerChildSelection(final int groupIndex, final ChildType child) {
        return triggerChildSelection(groupIndex, indexOfChildOrThrowException(groupIndex, child));
    }

    @Override
    public final boolean setAllChildrenSelected(final GroupType group, final boolean selected) {
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
    public final boolean triggerAllChildSelections(final GroupType group) {
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
        return new MultipleChoiceExpandableListAdapterImplementation<>(getContext(),
                getGroupInflater(), getChildInflater(), getDecorator(), getLogLevel(),
                getGroupAdapter(), areDuplicateChildrenAllowed(), isNotifiedOnChange(),
                isGroupExpandedOnClick(), getItemClickListeners(), getAdapterListeners(),
                getExpansionListeners(), areChildEnableStatesSetImplicitly(),
                getEnableStateListeners(), getNumberOfGroupStates(), getNumberOfChildStates(),
                isGroupStateTriggeredOnClick(), isChildStateTriggeredOnClick(),
                areChildStatesSetImplicitly(), getItemStateListeners(), getSortingListeners(),
                getFilterListeners(), isGroupSelectedOnClick(), isChildSelectedOnClick(),
                isGroupExpandedOnSelection(), isGroupExpandedOnChildSelection(),
                getSelectionListeners(), getChoiceMode());
    }

}