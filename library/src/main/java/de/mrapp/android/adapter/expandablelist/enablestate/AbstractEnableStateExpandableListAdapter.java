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
package de.mrapp.android.adapter.expandablelist.enablestate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.datastructure.UnmodifiableList;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.datastructure.group.UnmodifiableGroupList;
import de.mrapp.android.adapter.decorator.AbstractExpandableListDecorator;
import de.mrapp.android.adapter.expandablelist.AbstractExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterItemClickListener;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterItemLongClickListener;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterListener;
import de.mrapp.android.adapter.expandablelist.ExpansionListener;
import de.mrapp.android.adapter.logging.LogLevel;

import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * An abstract base class for all adapters, whose underlying data is managed as a list of arbitrary
 * group and child items, which may be disabled or enabled. Such an adapter's purpose is to provide
 * the underlying data for visualization using a {@link ExpandableListView} widget.
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
public abstract class AbstractEnableStateExpandableListAdapter<GroupType, ChildType, DecoratorType extends AbstractExpandableListDecorator<GroupType, ChildType>>
        extends AbstractExpandableListAdapter<GroupType, ChildType, DecoratorType>
        implements EnableStateExpandableListAdapter<GroupType, ChildType> {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The key, which is used to store, whether the enable states of children are also set, when the
     * enable state of the group, they belong to, is set, within a bundle.
     */
    @VisibleForTesting
    protected static final String SET_CHILD_ENABLE_STATES_IMPLICITLY_BUNDLE_KEY =
            AbstractEnableStateExpandableListAdapter.class.getSimpleName() +
                    "::SetChildEnableStatesImplicitly";

    /**
     * A set, which contains the listeners, which should be notified, when an item has been disabled
     * or enabled.
     */
    private transient Set<ExpandableListEnableStateListener<GroupType, ChildType>>
            enableStateListeners;

    /**
     * True, if the enable states of children are also set, when the enable state of the group, they
     * belong to, is set, false otherwise.
     */
    private boolean setChildEnableStatesImplicitly;

    /**
     * Notifies all listeners, which have been registered to be notified, when an item has been
     * enabled or disabled, about a group item, which has been enabled.
     *
     * @param group
     *         The group item, which has been enabled, as an instance of the generic type GroupType.
     *         The group item may not be null
     * @param index
     *         The index of the group item, which has been enabled, as an {@link Integer} value. The
     *         index must be between 0 and the value of the method <code>getGroupCount():int</code>
     *         - 1
     */
    private void notifyOnGroupEnabled(@NonNull final GroupType group, final int index) {
        for (ExpandableListEnableStateListener<GroupType, ChildType> listener : enableStateListeners) {
            listener.onGroupEnabled(this, group, index);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when an item has been
     * enabled or disabled, about a group item, which has been disabled.
     *
     * @param group
     *         The group item, which has been disabled, as an instance of the generic type
     *         GroupType. The group item may not be null
     * @param index
     *         The index of the group item, which has been disabled, as an {@link Integer} value.
     *         The index must be between 0 and the value of the method <code>getGroupCount():int</code>
     *         - 1
     */
    private void notifyOnGroupDisabled(@NonNull final GroupType group, final int index) {
        for (ExpandableListEnableStateListener<GroupType, ChildType> listener : enableStateListeners) {
            listener.onGroupDisabled(this, group, index);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when an item has been
     * enabled or disabled, about a child item, which has been enabled.
     *
     * @param child
     *         The child item, which has been enabled, as an instance of the generic type ChildType.
     *         The group item may not be null
     * @param childIndex
     *         The index of the child item, which has been enabled, as an {@link Integer} value. The
     *         index must be between 0 and the value of the method <code>getChildCount(groupIndex):int</code>
     *         - 1
     * @param group
     *         The group, the child item, which has been enabled, belongs to, as an instance of the
     *         generic type GroupType. The group may not be null
     * @param groupIndex
     *         The index of the group, the child item, which has been enabled, belongs to, as an
     *         {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1
     */
    private void notifyOnChildEnabled(@NonNull final ChildType child, final int childIndex,
                                      @NonNull final GroupType group, final int groupIndex) {
        for (ExpandableListEnableStateListener<GroupType, ChildType> listener : enableStateListeners) {
            listener.onChildEnabled(this, child, childIndex, group, groupIndex);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when an item has been
     * disabled or disabled, about a child item, which has been disabled.
     *
     * @param child
     *         The child item, which has been disabled, as an instance of the generic type
     *         ChildType. The group item may not be null
     * @param childIndex
     *         The index of the child item, which has been disabled, as an {@link Integer} value.
     *         The index must be between 0 and the value of the method <code>getChildCount(groupIndex):int</code>
     *         - 1
     * @param group
     *         The group, the child item, which has been disabled, belongs to, as an instance of the
     *         generic type GroupType. The group may not be null
     * @param groupIndex
     *         The index of the group, the child item, which has been disabled, belongs to, as an
     *         {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1
     */
    private void notifyOnChildDisabled(@NonNull final ChildType child, final int childIndex,
                                       @NonNull final GroupType group, final int groupIndex) {
        for (ExpandableListEnableStateListener<GroupType, ChildType> listener : enableStateListeners) {
            listener.onChildDisabled(this, child, childIndex, group, groupIndex);
        }
    }

    /**
     * Returns a set, which contains the listeners, which should be notified, when an item has been
     * disabled or enabled.
     *
     * @return A set, which contains the listeners, which should be notified, when an item has been
     * disabled or enabled, as an instance of the type {@link Set} or an empty setr, if no listeners
     * should be notified
     */
    protected final Set<ExpandableListEnableStateListener<GroupType, ChildType>> getEnableStateListeners() {
        return enableStateListeners;
    }

    /**
     * Sets the set, which contains the listeners, which should be notified, when an item has been
     * disabled or enabled.
     *
     * @param enableStateListeners
     *         The set, which should be set, as an instance of the type {@link Set} or an empty set,
     *         if no listeners should be notified
     */
    protected final void setEnableStateListeners(
            @NonNull final Set<ExpandableListEnableStateListener<GroupType, ChildType>> enableStateListeners) {
        ensureNotNull(enableStateListeners, "The enable state listeners may not be null");
        this.enableStateListeners = enableStateListeners;
    }

    /**
     * Creates a new adapter, whose underlying data is managed as a list of arbitrary group and
     * child items, which may be disabled or enabled.
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
     */
    protected AbstractEnableStateExpandableListAdapter(@NonNull final Context context,
                                                       @NonNull final DecoratorType decorator,
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
                                                       @NonNull final Set<ExpandableListEnableStateListener<GroupType, ChildType>> enableStateListeners) {
        super(context, decorator, logLevel, groupAdapter, allowDuplicateChildren, notifyOnChange,
                triggerGroupExpansionOnClick, itemClickListeners, itemLongClickListeners,
                adapterListeners, expansionListeners);
        setEnableStateListeners(enableStateListeners);
        setChildEnableStatesImplicitly(setChildEnableStatesImplicitly);
    }

    @Override
    public final boolean areAllItemsEnabled() {
        return getEnabledGroupCount() == getGroupCount() &&
                getEnabledChildCount() == getChildCount();
    }

    @CallSuper
    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        outState.putBoolean(SET_CHILD_ENABLE_STATES_IMPLICITLY_BUNDLE_KEY,
                areChildEnableStatesSetImplicitly());
    }

    @CallSuper
    @Override
    protected void onRestoreInstanceState(@NonNull final Bundle savedState) {
        setChildEnableStatesImplicitly =
                savedState.getBoolean(SET_CHILD_ENABLE_STATES_IMPLICITLY_BUNDLE_KEY, true);
    }

    @Override
    public final boolean isGroupEnabled(final int groupIndex) {
        return getGroupAdapter().isEnabled(groupIndex);
    }

    @Override
    public final boolean isGroupEnabled(@NonNull final GroupType group) {
        return isGroupEnabled(indexOfGroupOrThrowException(group));
    }

    @Override
    public final int getFirstEnabledGroupIndex() {
        return getGroupAdapter().getFirstEnabledIndex();
    }

    @Override
    public final GroupType getFirstEnabledGroup() {
        return getGroupAdapter().getFirstEnabledItem().getData();
    }

    @Override
    public final int getLastEnabledGroupIndex() {
        return getGroupAdapter().getLastEnabledIndex();
    }

    @Override
    public final GroupType getLastEnabledGroup() {
        return getGroupAdapter().getLastEnabledItem().getData();
    }

    @Override
    public final int getFirstDisabledGroupIndex() {
        return getGroupAdapter().getFirstDisabledIndex();
    }

    @Override
    public final GroupType getFirstDisabledGroup() {
        return getGroupAdapter().getFirstDisabledItem().getData();
    }

    @Override
    public final int getLastDisabledGroupIndex() {
        return getGroupAdapter().getLastDisabledIndex();
    }

    @Override
    public final GroupType getLastDisabledGroup() {
        return getGroupAdapter().getLastDisabledItem().getData();
    }

    @Override
    public final List<Integer> getEnabledGroupIndices() {
        return getGroupAdapter().getEnabledIndices();
    }

    @Override
    public final List<GroupType> getEnabledGroups() {
        return new UnmodifiableGroupList<>(getGroupAdapter().getEnabledItems());
    }

    @Override
    public final List<Integer> getDisabledGroupIndices() {
        return getGroupAdapter().getDisabledIndices();
    }

    @Override
    public final List<GroupType> getDisabledGroups() {
        return new UnmodifiableGroupList<>(getGroupAdapter().getDisabledItems());
    }

    @Override
    public final int getEnabledGroupCount() {
        return getGroupAdapter().getEnabledItemCount();
    }

    @Override
    public final void setGroupEnabled(final int groupIndex, final boolean enabled) {
        MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter = getGroupAdapter();
        Group<GroupType, ChildType> group = groupAdapter.getItem(groupIndex);

        if (groupAdapter.isEnabled(groupIndex) != enabled) {
            groupAdapter.setEnabled(groupIndex, enabled);

            if (enabled) {
                notifyOnGroupEnabled(group.getData(), groupIndex);
            } else {
                notifyOnGroupDisabled(group.getData(), groupIndex);
            }

            notifyObserversOnDataSetChanged();
            String message = enabled ? "Enabled" :
                    "Disabled" + " group \"" + group.getData() + "\" at index " + groupIndex;
            getLogger().logInfo(getClass(), message);
        } else {
            String message =
                    "The enable state of group \"" + group.getData() + "\" at index " + groupIndex +
                            " has not been changed, because it is already " +
                            (enabled ? "enabled" : "disabled");
            getLogger().logDebug(getClass(), message);
        }

        if (areChildEnableStatesSetImplicitly()) {
            setAllChildrenEnabled(groupIndex, enabled);
        }
    }

    @Override
    public final void setGroupEnabled(@NonNull final GroupType group, final boolean enabled) {
        setGroupEnabled(indexOfGroupOrThrowException(group), enabled);
    }

    @Override
    public final boolean triggerGroupEnableState(final int groupIndex) {
        return triggerGroupEnableState(false, groupIndex);
    }

    @Override
    public final boolean triggerGroupEnableState(final boolean triggerChildStates,
                                                 final int groupIndex) {
        boolean enabled = !isGroupEnabled(groupIndex);
        setGroupEnabled(groupIndex, enabled);

        if (triggerChildStates) {
            triggerAllChildEnableStates(groupIndex);
        }

        return enabled;
    }

    @Override
    public final boolean triggerGroupEnableState(@NonNull final GroupType group) {
        return triggerGroupEnableState(false, group);
    }

    @Override
    public final boolean triggerGroupEnableState(final boolean triggerChildStates,
                                                 @NonNull final GroupType group) {
        return triggerGroupEnableState(triggerChildStates, indexOfGroupOrThrowException(group));
    }

    @Override
    public final void setAllGroupsEnabled(final boolean enabled) {
        for (int i = 0; i < getGroupCount(); i++) {
            setGroupEnabled(i, enabled);
        }
    }

    @Override
    public final void triggerAllGroupEnableStates() {
        triggerAllGroupEnableStates(false);
    }

    @Override
    public final void triggerAllGroupEnableStates(final boolean triggerChildStates) {
        for (int i = 0; i < getGroupCount(); i++) {
            triggerGroupEnableState(triggerChildStates, i);
        }
    }

    @Override
    public final boolean isChildEnabled(@NonNull final GroupType group, final int childIndex) {
        return isChildEnabled(indexOfGroupOrThrowException(group), childIndex);
    }

    @Override
    public final boolean isChildEnabled(@NonNull final GroupType group,
                                        @NonNull final ChildType child) {
        return isChildEnabled(indexOfGroupOrThrowException(group), child);
    }

    @Override
    public final boolean isChildEnabled(final int groupIndex, final int childIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().isEnabled(childIndex);
    }

    @Override
    public final boolean isChildEnabled(final int groupIndex, @NonNull final ChildType child) {
        return isChildEnabled(groupIndex, indexOfChildOrThrowException(groupIndex, child));
    }

    @Override
    public final int getFirstEnabledChildIndex(@NonNull final GroupType group) {
        return getFirstEnabledChildIndex(indexOfGroupOrThrowException(group));
    }

    @Override
    public final ChildType getFirstEnabledChild(@NonNull final GroupType group) {
        return getFirstEnabledChild(indexOfGroupOrThrowException(group));
    }

    @Override
    public final int getFirstEnabledChildIndex(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getFirstEnabledIndex();
    }

    @Override
    public final ChildType getFirstEnabledChild(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getFirstEnabledItem();
    }

    @Override
    public final int getLastEnabledChildIndex(@NonNull final GroupType group) {
        return getLastEnabledChildIndex(indexOfGroupOrThrowException(group));
    }

    @Override
    public final ChildType getLastEnabledChild(@NonNull final GroupType group) {
        return getLastEnabledChild(indexOfGroupOrThrowException(group));
    }

    @Override
    public final int getLastEnabledChildIndex(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getLastEnabledIndex();
    }

    @Override
    public final ChildType getLastEnabledChild(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getLastEnabledItem();
    }

    @Override
    public final int getFirstDisabledChildIndex(@NonNull final GroupType group) {
        return getFirstDisabledChildIndex(indexOfGroupOrThrowException(group));
    }

    @Override
    public final ChildType getFirstDisabledChild(@NonNull final GroupType group) {
        return getFirstDisabledChild(indexOfGroupOrThrowException(group));
    }

    @Override
    public final int getFirstDisabledChildIndex(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getFirstDisabledIndex();
    }

    @Override
    public final ChildType getFirstDisabledChild(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getFirstDisabledItem();
    }

    @Override
    public final int getLastDisabledChildIndex(@NonNull final GroupType group) {
        return getLastDisabledChildIndex(indexOfGroupOrThrowException(group));
    }

    @Override
    public final ChildType getLastDisabledChild(@NonNull final GroupType group) {
        return getLastDisabledChild(indexOfGroupOrThrowException(group));
    }

    @Override
    public final int getLastDisabledChildIndex(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getLastDisabledIndex();
    }

    @Override
    public final ChildType getLastDisabledChild(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getLastDisabledItem();
    }

    @Override
    public final List<ChildType> getEnabledChildren() {
        List<ChildType> enabledChildren = new ArrayList<>();

        for (int i = 0; i < getGroupCount(); i++) {
            enabledChildren.addAll(getEnabledChildren(i));
        }

        return new UnmodifiableList<>(enabledChildren);
    }

    @Override
    public final List<Integer> getEnabledChildIndices(@NonNull final GroupType group) {
        return getEnabledChildIndices(indexOfGroupOrThrowException(group));
    }

    @Override
    public final List<ChildType> getEnabledChildren(@NonNull final GroupType group) {
        return getEnabledChildren(indexOfGroupOrThrowException(group));
    }

    @Override
    public final List<Integer> getEnabledChildIndices(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getEnabledIndices();
    }

    @Override
    public final List<ChildType> getEnabledChildren(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getEnabledItems();
    }

    @Override
    public final List<ChildType> getDisabledChildren() {
        List<ChildType> disabledChildren = new ArrayList<>();

        for (int i = 0; i < getGroupCount(); i++) {
            disabledChildren.addAll(getDisabledChildren(i));
        }

        return new UnmodifiableList<>(disabledChildren);
    }

    @Override
    public final List<Integer> getDisabledChildIndices(@NonNull final GroupType group) {
        return getDisabledChildIndices(indexOfGroupOrThrowException(group));
    }

    @Override
    public final List<ChildType> getDisabledChildren(@NonNull final GroupType group) {
        return getDisabledChildren(indexOfGroupOrThrowException(group));
    }

    @Override
    public final List<Integer> getDisabledChildIndices(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getDisabledIndices();
    }

    @Override
    public final List<ChildType> getDisabledChildren(final int groupIndex) {
        return getGroupAdapter().getItem(groupIndex).getChildAdapter().getDisabledItems();
    }

    @Override
    public final int getEnabledChildCount() {
        return getEnabledChildren().size();
    }

    @Override
    public final int getEnabledChildCount(@NonNull final GroupType group) {
        return getEnabledChildren(group).size();
    }

    @Override
    public final int getEnabledChildCount(final int groupIndex) {
        return getEnabledChildren(groupIndex).size();
    }

    @Override
    public final void setChildEnabled(@NonNull final GroupType group, final int childIndex,
                                      final boolean enabled) {
        setChildEnabled(indexOfGroupOrThrowException(group), childIndex, enabled);
    }

    @Override
    public final void setChildEnabled(@NonNull final GroupType group,
                                      @NonNull final ChildType child, final boolean enabled) {
        setChildEnabled(indexOfGroupOrThrowException(group), child, enabled);
    }

    @Override
    public final void setChildEnabled(final int groupIndex, final int childIndex,
                                      final boolean enabled) {
        Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
        MultipleChoiceListAdapter<ChildType> childAdapter = group.getChildAdapter();

        if (childAdapter.isEnabled(childIndex) != enabled) {
            childAdapter.setEnabled(childIndex, enabled);

            if (enabled) {
                notifyOnChildEnabled(childAdapter.getItem(childIndex), childIndex, group.getData(),
                        groupIndex);
            } else {
                notifyOnChildDisabled(childAdapter.getItem(childIndex), childIndex, group.getData(),
                        groupIndex);
            }

            notifyObserversOnDataSetChanged();
            String message = enabled ? "Enabled" :
                    "Disabled" + " child \"" + childAdapter.getItem(childIndex) + "\" at index " +
                            childIndex + " of group \"" + group.getData() + "\" at index " +
                            groupIndex;
            getLogger().logInfo(getClass(), message);
        } else {
            String message = "The enable state of child \"" + childAdapter.getItem(childIndex) +
                    "\" at index " + childIndex + " of group \"" + group.getData() +
                    "\" at index " + groupIndex + " has not been changed, because it is already " +
                    (enabled ? "enabled" : "disabled");
            getLogger().logDebug(getClass(), message);
        }
    }

    @Override
    public final void setChildEnabled(final int groupIndex, @NonNull final ChildType child,
                                      final boolean enabled) {
        setChildEnabled(groupIndex, indexOfChildOrThrowException(groupIndex, child), enabled);
    }

    @Override
    public final boolean triggerChildEnableState(@NonNull final GroupType group,
                                                 final int childIndex) {
        return triggerChildEnableState(indexOfGroupOrThrowException(group), childIndex);
    }

    @Override
    public final boolean triggerChildEnableState(@NonNull final GroupType group,
                                                 @NonNull final ChildType child) {
        return triggerChildEnableState(indexOfGroupOrThrowException(group), child);
    }

    @Override
    public final boolean triggerChildEnableState(final int groupIndex, final int childIndex) {
        boolean enabled = !isChildEnabled(groupIndex, childIndex);
        setChildEnabled(groupIndex, childIndex, enabled);
        return enabled;
    }

    @Override
    public final boolean triggerChildEnableState(final int groupIndex,
                                                 @NonNull final ChildType child) {
        return triggerChildEnableState(groupIndex, indexOfChildOrThrowException(groupIndex, child));
    }

    @Override
    public final void setAllChildrenEnabled(final boolean enabled) {
        for (int i = 0; i < getGroupCount(); i++) {
            setAllChildrenEnabled(i, enabled);
        }
    }

    @Override
    public final void setAllChildrenEnabled(@NonNull final GroupType group, final boolean enabled) {
        setAllChildrenEnabled(indexOfGroupOrThrowException(group), enabled);
    }

    @Override
    public final void setAllChildrenEnabled(final int groupIndex, final boolean enabled) {
        for (int i = 0; i < getChildCount(groupIndex); i++) {
            setChildEnabled(groupIndex, i, enabled);
        }
    }

    @Override
    public final void triggerAllChildEnableStates() {
        for (int i = 0; i < getGroupCount(); i++) {
            triggerAllChildEnableStates(i);
        }
    }

    @Override
    public final void triggerAllChildEnableStates(@NonNull final GroupType group) {
        triggerAllChildEnableStates(indexOfGroupOrThrowException(group));
    }

    @Override
    public final void triggerAllChildEnableStates(final int groupIndex) {
        for (int i = 0; i < getChildCount(groupIndex); i++) {
            triggerChildEnableState(groupIndex, i);
        }
    }

    @Override
    public final boolean areChildEnableStatesSetImplicitly() {
        return setChildEnableStatesImplicitly;
    }

    @Override
    public final void setChildEnableStatesImplicitly(final boolean setChildEnableStatesImplicitly) {
        this.setChildEnableStatesImplicitly = setChildEnableStatesImplicitly;

        if (setChildEnableStatesImplicitly) {
            for (int i = 0; i < getGroupCount(); i++) {
                for (int j = 0; j < getChildCount(i); j++) {
                    setChildEnabled(i, j, isGroupEnabled(i));
                }
            }
        }
    }

    @Override
    public final void addEnableStateListener(
            @NonNull final ExpandableListEnableStateListener<GroupType, ChildType> listener) {
        ensureNotNull(listener, "The listener may not be null");
        enableStateListeners.add(listener);
        String message = "Added enable state listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void removeEnableStateListener(
            @NonNull final ExpandableListEnableStateListener<GroupType, ChildType> listener) {
        ensureNotNull(listener, "The listener may not be null");
        enableStateListeners.remove(listener);
        String message = "Removed enable state listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @CallSuper
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (setChildEnableStatesImplicitly ? 1231 : 1237);
        return result;
    }

    @CallSuper
    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractEnableStateExpandableListAdapter<?, ?, ?> other =
                (AbstractEnableStateExpandableListAdapter<?, ?, ?>) obj;
        if (setChildEnableStatesImplicitly != other.setChildEnableStatesImplicitly)
            return false;
        return true;
    }

    @Override
    public abstract AbstractEnableStateExpandableListAdapter<GroupType, ChildType, DecoratorType> clone()
            throws CloneNotSupportedException;

}