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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.Set;

import de.mrapp.android.adapter.ChoiceMode;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.SelectableExpandableListDecorator;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterItemClickListener;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterListener;
import de.mrapp.android.adapter.expandablelist.ExpansionListener;
import de.mrapp.android.adapter.expandablelist.enablestate.ExpandableListEnableStateListener;
import de.mrapp.android.adapter.expandablelist.filterable.AbstractFilterableExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.filterable.ExpandableListFilterListener;
import de.mrapp.android.adapter.expandablelist.itemstate.ExpandableListItemStateListener;
import de.mrapp.android.adapter.expandablelist.sortable.ExpandableListSortingListener;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.logging.LogLevel;
import de.mrapp.android.util.VisibleForTesting;

import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * An abstract base class for all adapters, whose underlying data is managed as a list of arbitrary
 * group and child items, of which one or multiple items can be selected. Such an adapter's purpose
 * is to provide the underlying data for visualization using an {@link ExpandableListView} widget.
 *
 * @param <GroupType>
 *         The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public abstract class AbstractSelectableExpandableListAdapter<GroupType, ChildType> extends
        AbstractFilterableExpandableListAdapter<GroupType, ChildType, SelectableExpandableListDecorator<GroupType, ChildType>>
        implements SelectableExpandableListAdapter<GroupType, ChildType> {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The key, which is used to store, whether a group item should be selected, when it is clicked
     * by the user, or not, within a bundle.
     */
    @VisibleForTesting
    protected static final String SELECT_GROUP_ON_CLICK_BUNDLE_KEY =
            AbstractSelectableExpandableListAdapter.class.getSimpleName() +
                    "::SelectGroupItemOnClick";

    /**
     * The key, which is used to store, whether a child item should be selected, when it is clicked
     * by the user, or not, within a bundle.
     */
    @VisibleForTesting
    protected static final String SELECT_CHILD_ON_CLICK_BUNDLE_KEY =
            AbstractSelectableExpandableListAdapter.class.getSimpleName() +
                    "::SelectChildItemOnClick";

    /**
     * The key, which is used to store, whether a group should be expanded, when it is selected, or
     * not, within a bundle.
     */
    @VisibleForTesting
    protected static final String EXPAND_GROUP_ON_SELECTION_BUNDLE_KEY =
            AbstractSelectableExpandableListAdapter.class.getSimpleName() +
                    "::ExpandGroupOnSelection";

    /**
     * The key, which is used to store, whether a group should be expanded, when one of its children
     * is selected, or not, within a bundle.
     */
    @VisibleForTesting
    protected static final String EXPAND_GROUP_ON_CHILD_SELECTION_BUNDLE_KEY =
            AbstractSelectableExpandableListAdapter.class.getSimpleName() +
                    "::ExpandGroupOnChildSelection";

    /**
     * The key, which is used to store the choice mode within a bundle.
     */
    @VisibleForTesting
    protected static final String CHOICE_MODE_BUNDLE_KEY =
            AbstractSelectableExpandableListAdapter.class.getSimpleName() + "::ChoiceMode";

    /**
     * A set, which contains the listeners, which should be notified, when an item has been selected
     * or unselected.
     */
    private transient Set<ExpandableListSelectionListener<GroupType, ChildType>> selectionListeners;

    /**
     * The choice mode, which is used by the adapter.
     */
    private ChoiceMode choiceMode;

    /**
     * True, if a group item should be selected, when it is clicked by the user, false otherwise.
     */
    private boolean selectGroupOnClick;

    /**
     * True, if a child item should be selected, when it is clicked by the user, false otherwise.
     */
    private boolean selectChildOnClick;

    /**
     * True, if a group should be expanded, when it is selected, false otherwise.
     */
    private boolean expandGroupOnSelection;

    /**
     * True, if a group should be expanded, when one of its children is selected, false otherwise.
     */
    private boolean expandGroupOnChildSelection;

    /**
     * Returns a set, which contains the listeners, which should be notified, when the selection of
     * an item of the adapter has been changed.
     *
     * @return A set, which contains the listeners, which should be notified when the selection of
     * an item of the adapter has been changed, as an instance of the type {@link Set} or an empty
     * set, if no listeners should be notified
     */
    protected final Set<ExpandableListSelectionListener<GroupType, ChildType>> getSelectionListeners() {
        return selectionListeners;
    }

    /**
     * Sets the set, which contains the listeners, which should be notified, when the selection of
     * an item of the adapter has been changed.
     *
     * @param selectionListeners
     *         The set, which should be set, as an instance of the type {@link Set} or an empty set,
     *         if no listeners should be notified
     */
    protected final void setSelectionListeners(
            @NonNull final Set<ExpandableListSelectionListener<GroupType, ChildType>> selectionListeners) {
        ensureNotNull(selectionListeners, "The selection listeners may not be null");
        this.selectionListeners = selectionListeners;
    }

    /**
     * Notifies all listeners, which have been registered to be notified when the selection of an
     * item of the adapter has been changed, about a group, which has been selected.
     *
     * @param group
     *         The group item, which has been selected, as an instance of the generic type
     *         GroupType. The group item may not be null
     * @param groupIndex
     *         The index of the group item, which has been selected, as an {@link Integer} value.
     *         The index must be between 0 and the value of the method <code>getGroupCount():int</code>
     *         - 1
     */
    protected final void notifyOnGroupSelected(@NonNull final GroupType group,
                                               final int groupIndex) {
        for (ExpandableListSelectionListener<GroupType, ChildType> listener : selectionListeners) {
            listener.onGroupSelected(this, group, groupIndex);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified when the selection of an
     * item of the adapter has been changed, about a group, which has been unselected.
     *
     * @param group
     *         The group item, which has been unselected, as an instance of the generic type
     *         GroupType. The group item may not be null
     * @param groupIndex
     *         The index of the group item, which has been unselected, as an {@link Integer} value.
     *         The index must be between 0 and the value of the method <code>getGroupCount():int</code>
     *         - 1
     */
    protected final void notifyOnGroupUnselected(@NonNull final GroupType group,
                                                 final int groupIndex) {
        for (ExpandableListSelectionListener<GroupType, ChildType> listener : selectionListeners) {
            listener.onGroupUnselected(this, group, groupIndex);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified when the selection of an
     * item of the adapter has been changed, about a child, which has been selected.
     *
     * @param group
     *         The group group, the child item, which has been selected, belongs to, as an instance
     *         of the generic type GroupType. The group item may not be null
     * @param groupIndex
     *         The index of the group, the child item, which has been selected, belongs to, as an
     *         {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1
     * @param child
     *         The child item, which has been selected, as an instance of the generic type
     *         ChildType. The child item may not be null
     * @param childIndex
     *         The index of the child item, which has been selected, as an {@link Integer} value.
     *         The index must be between 0 ad the value of the method <code>getChildCount(groupIndex):int</code>
     *         - 1
     */
    protected final void notifyOnChildSelected(@NonNull final GroupType group, final int groupIndex,
                                               @NonNull final ChildType child,
                                               final int childIndex) {
        for (ExpandableListSelectionListener<GroupType, ChildType> listener : selectionListeners) {
            listener.onChildSelected(this, child, childIndex, group, groupIndex);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified when the selection of an
     * item of the adapter has been changed, about a child, which has been unselected.
     *
     * @param group
     *         The group group, the child item, which has been unselected, belongs to, as an
     *         instance of the generic type GroupType. The group item may not be null
     * @param groupIndex
     *         The index of the group, the child item, which has been unselected, belongs to, as an
     *         {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1
     * @param child
     *         The child item, which has been unselected, as an instance of the generic type
     *         ChildType. The child item may not be null
     * @param childIndex
     *         The index of the child item, which has been unselected, as an {@link Integer} value.
     *         The index must be between 0 ad the value of the method <code>getChildCount(groupIndex):int</code>
     *         - 1
     */
    protected final void notifyOnChildUnselected(@NonNull final GroupType group,
                                                 final int groupIndex,
                                                 @NonNull final ChildType child,
                                                 final int childIndex) {
        for (ExpandableListSelectionListener<GroupType, ChildType> listener : selectionListeners) {
            listener.onChildUnselected(this, child, childIndex, group, groupIndex);
        }
    }

    /**
     * Creates a new adapter, whose underlying data is managed as a list of arbitrary group and
     * child items, of which one or multiple items can be selected.
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
    protected AbstractSelectableExpandableListAdapter(@NonNull final Context context,
                                                      @NonNull final Inflater groupInflater,
                                                      @NonNull final Inflater childInflater,
                                                      @NonNull final SelectableExpandableListDecorator<GroupType, ChildType> decorator,
                                                      @NonNull final LogLevel logLevel,
                                                      @NonNull final MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter,
                                                      final boolean allowDuplicateChildren,
                                                      final boolean notifyOnChange,
                                                      final boolean expandGroupOnClick,
                                                      @NonNull final Set<ExpandableListAdapterItemClickListener<GroupType, ChildType>> itemClickListeners,
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
        super(context, groupInflater, childInflater, decorator, logLevel, groupAdapter,
                allowDuplicateChildren, notifyOnChange, expandGroupOnClick, itemClickListeners,
                adapterListeners, expansionListeners, setChildEnableStatesImplicitly,
                enableStateListeners, numberOfGroupStates, numberOfChildStates,
                triggerGroupStateOnClick, triggerChildStateOnClick, setChildStatesImplicitly,
                itemStateListeners, sortingListeners, filterListeners);
        ensureNotNull(choiceMode, "The choice mode may not be null");
        this.choiceMode = choiceMode;
        selectGroupOnClick(selectGroupOnClick);
        selectChildOnClick(selectChildOnClick);
        expandGroupOnSelection(expandGroupOnSelection);
        expandGroupOnChildSelection(expandGroupOnChildSelection);
        setSelectionListeners(selectionListeners);
    }

    @Override
    protected final void applyDecoratorOnGroup(@NonNull final Context context,
                                               @NonNull final View view, final int index) {
        GroupType group = getGroup(index);
        boolean expanded = isGroupExpanded(index);
        boolean enabled = isGroupEnabled(index);
        int state = getGroupState(index);
        boolean filtered = areGroupsFiltered();
        boolean selected = isGroupSelected(index);
        getDecorator()
                .applyDecoratorOnGroup(context, this, view, group, index, expanded, enabled, state,
                        filtered, selected);
    }

    @Override
    protected final void applyDecoratorOnChild(@NonNull final Context context,
                                               @NonNull final View view, final int groupIndex,
                                               final int childIndex) {
        GroupType group = getGroup(groupIndex);
        ChildType child = getChild(groupIndex, childIndex);
        boolean enabled = isChildEnabled(groupIndex, childIndex);
        int state = getChildState(groupIndex, childIndex);
        boolean filtered = areChildrenFiltered();
        boolean selected = isChildSelected(groupIndex, childIndex);
        getDecorator()
                .applyDecoratorOnChild(context, this, view, child, childIndex, group, groupIndex,
                        enabled, state, filtered, selected);
    }

    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SELECT_GROUP_ON_CLICK_BUNDLE_KEY, isGroupSelectedOnClick());
        outState.putBoolean(SELECT_CHILD_ON_CLICK_BUNDLE_KEY, isChildSelectedOnClick());
        outState.putBoolean(EXPAND_GROUP_ON_SELECTION_BUNDLE_KEY, isGroupExpandedOnSelection());
        outState.putBoolean(EXPAND_GROUP_ON_CHILD_SELECTION_BUNDLE_KEY,
                isGroupExpandedOnChildSelection());
        outState.putSerializable(CHOICE_MODE_BUNDLE_KEY, getChoiceMode());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull final Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        selectGroupOnClick = savedState.getBoolean(SELECT_GROUP_ON_CLICK_BUNDLE_KEY, true);
        selectChildOnClick = savedState.getBoolean(SELECT_CHILD_ON_CLICK_BUNDLE_KEY, true);
        expandGroupOnSelection = savedState.getBoolean(EXPAND_GROUP_ON_SELECTION_BUNDLE_KEY);
        expandGroupOnChildSelection =
                savedState.getBoolean(EXPAND_GROUP_ON_CHILD_SELECTION_BUNDLE_KEY, true);
        choiceMode = (ChoiceMode) savedState.getSerializable(CHOICE_MODE_BUNDLE_KEY);
    }

    @Override
    public final void addSelectionListener(
            @NonNull final ExpandableListSelectionListener<GroupType, ChildType> listener) {
        ensureNotNull(listener, "The listener may not be null");
        selectionListeners.add(listener);
    }

    @Override
    public final void removeSelectionListener(
            @NonNull final ExpandableListSelectionListener<GroupType, ChildType> listener) {
        ensureNotNull(listener, "The listener may not be null");
        selectionListeners.remove(listener);
    }

    @Override
    public final boolean isGroupSelected(final int groupIndex) {
        return getGroupAdapter().isSelected(groupIndex);
    }

    @Override
    public final boolean isGroupSelected(@NonNull final GroupType group) {
        return getGroupAdapter().isSelected(indexOfGroupOrThrowException(group));
    }

    @Override
    public final int getSelectedGroupCount() {
        return getGroupAdapter().getSelectedItemCount();
    }

    @Override
    public final boolean isChildSelected(@NonNull final GroupType group, final int childIndex) {
        return isChildSelected(indexOfGroupOrThrowException(group), childIndex);
    }

    @Override
    public final boolean isChildSelected(@NonNull final GroupType group,
                                         @NonNull final ChildType child) {
        return isChildSelected(indexOfGroupOrThrowException(group), child);
    }

    @Override
    public final boolean isChildSelected(final int groupIndex, final int childIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().isSelected(childIndex);
    }

    @Override
    public final boolean isChildSelected(final int groupIndex, @NonNull final ChildType child) {
        return isChildSelected(groupIndex, indexOfChildOrThrowException(groupIndex, child));
    }

    @Override
    public final int getSelectedChildCount() {
        int result = 0;

        for (int i = 0; i < getGroupCount(); i++) {
            result += getSelectedChildCount(i);
        }

        return result;
    }

    @Override
    public final int getSelectedChildCount(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getSelectedItemCount();
    }

    @Override
    public final int getSelectedChildCount(@NonNull final GroupType group) {
        return getSelectedChildCount(indexOfGroupOrThrowException(group));
    }

    @Override
    public final boolean isGroupSelectedOnClick() {
        return selectGroupOnClick;
    }

    @Override
    public final void selectGroupOnClick(final boolean selectGroupOnClick) {
        this.selectGroupOnClick = selectGroupOnClick;
    }

    @Override
    public final boolean isChildSelectedOnClick() {
        return selectChildOnClick;
    }

    @Override
    public final void selectChildOnClick(final boolean selectChildOnClick) {
        this.selectChildOnClick = selectChildOnClick;
    }

    @Override
    public final boolean isGroupExpandedOnSelection() {
        return expandGroupOnSelection;
    }

    @Override
    public final void expandGroupOnSelection(final boolean expandGroupOnSelection) {
        this.expandGroupOnSelection = expandGroupOnSelection;

        if (expandGroupOnSelection && getChoiceMode() != ChoiceMode.CHILDREN_ONLY) {
            for (int i = 0; i < getGroupCount(); i++) {
                if (isGroupSelected(i)) {
                    expandGroup(i);
                }
            }
        }
    }

    @Override
    public final boolean isGroupExpandedOnChildSelection() {
        return expandGroupOnChildSelection;
    }

    @Override
    public final void expandGroupOnChildSelection(final boolean expandGroupOnChildSelection) {
        this.expandGroupOnChildSelection = expandGroupOnChildSelection;

        if (expandGroupOnChildSelection && getChoiceMode() != ChoiceMode.GROUPS_ONLY) {
            for (int i = 0; i < getGroupCount(); i++) {
                if (getSelectedChildCount(i) > 0) {
                    expandGroup(i);
                }
            }
        }
    }

    @Override
    public final ChoiceMode getChoiceMode() {
        return choiceMode;
    }

    @Override
    public final boolean isChildSelectable(final int groupIndex, final int childIndex) {
        return getChoiceMode() != ChoiceMode.GROUPS_ONLY && isChildEnabled(groupIndex, childIndex);
    }

    @Override
    public final int getChildTypeCount() {
        return getDecorator().getChildTypeCount();
    }

    @Override
    public final int getChildType(final int groupIndex, final int childIndex) {
        return getDecorator().getChildType(getChild(groupIndex, childIndex));
    }

    @Override
    public final int getGroupTypeCount() {
        return getDecorator().getGroupTypeCount();
    }

    @Override
    public final int getGroupType(final int groupIndex) {
        return getDecorator().getGroupType(getGroup(groupIndex));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + choiceMode.hashCode();
        result = prime * result + (selectChildOnClick ? 1231 : 1237);
        result = prime * result + (selectGroupOnClick ? 1231 : 1237);
        result = prime * result + (expandGroupOnSelection ? 1231 : 1237);
        result = prime * result + (expandGroupOnChildSelection ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractSelectableExpandableListAdapter<?, ?> other =
                (AbstractSelectableExpandableListAdapter<?, ?>) obj;
        if (choiceMode != other.choiceMode)
            return false;
        if (selectChildOnClick != other.selectChildOnClick)
            return false;
        if (selectGroupOnClick != other.selectGroupOnClick)
            return false;
        if (expandGroupOnSelection != other.expandGroupOnSelection)
            return false;
        if (expandGroupOnChildSelection != other.expandGroupOnChildSelection)
            return false;
        return true;
    }

    @Override
    public abstract AbstractSelectableExpandableListAdapter<GroupType, ChildType> clone()
            throws CloneNotSupportedException;

}