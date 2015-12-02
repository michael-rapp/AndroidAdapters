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
package de.mrapp.android.adapter.expandablelist.enablestate;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.expandablelist.AbstractExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterItemClickListener;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterListener;
import de.mrapp.android.adapter.expandablelist.ExpansionListener;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.logging.LogLevel;
import de.mrapp.android.adapter.util.VisibleForTesting;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a list of arbitrary group and child items, which may be disabled or enabled.
 * Such an adapter's purpose is to provide the underlying data for visualization
 * using a {@link ExpandableListView} widget.
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
 * @since 0.1.0
 */
public abstract class AbstractEnableStateExpadableListAdapter<GroupType, ChildType, DecoratorType>
		extends AbstractExpandableListAdapter<GroupType, ChildType, DecoratorType>
		implements EnableStateExpandableListAdapter<GroupType, ChildType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The key, which is used to store, whether the enable states of children
	 * are also set, when the enable state of the group, they belong to, is set,
	 * within a bundle.
	 */
	@VisibleForTesting
	protected static final String SET_CHILD_ENABLE_STATES_IMPLICITLY_BUNDLE_KEY = AbstractEnableStateExpadableListAdapter.class
			.getSimpleName() + "::SetChildEnableStatesImplicitly";

	/**
	 * A set, which contains the listeners, which should be notified, when an
	 * item has been disabled or enabled.
	 */
	private transient Set<ExpandableListEnableStateListener<GroupType, ChildType>> enableStateListeners;

	/**
	 * True, if the enable states of children are also set, when the enable
	 * state of the group, they belong to, is set, false otherwise.
	 */
	private boolean setChildEnableStatesImplicitly;

	/**
	 * Notifies all listeners, which have been registered to be notified, when
	 * an item has been enabled or disabled, about a group item, which has been
	 * enabled.
	 * 
	 * @param group
	 *            The group item, which has been enabled, as an instance of the
	 *            generic type GroupType. The group item may not be null
	 * @param index
	 *            The index of the group item, which has been enabled, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getGroupCount():int</code> - 1
	 */
	private void notifyOnGroupEnabled(final GroupType group, final int index) {
		for (ExpandableListEnableStateListener<GroupType, ChildType> listener : enableStateListeners) {
			listener.onGroupEnabled(this, group, index);
		}
	}

	/**
	 * Notifies all listeners, which have been registered to be notified, when
	 * an item has been enabled or disabled, about a group item, which has been
	 * disabled.
	 * 
	 * @param group
	 *            The group item, which has been disabled, as an instance of the
	 *            generic type GroupType. The group item may not be null
	 * @param index
	 *            The index of the group item, which has been disabled, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getGroupCount():int</code> - 1
	 */
	private void notifyOnGroupDisabled(final GroupType group, final int index) {
		for (ExpandableListEnableStateListener<GroupType, ChildType> listener : enableStateListeners) {
			listener.onGroupDisabled(this, group, index);
		}
	}

	/**
	 * Notifies all listeners, which have been registered to be notified, when
	 * an item has been enabled or disabled, about a child item, which has been
	 * enabled.
	 * 
	 * @param child
	 *            The child item, which has been enabled, as an instance of the
	 *            generic type ChildType. The group item may not be null
	 * @param childIndex
	 *            The index of the child item, which has been enabled, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getChildCount(groupIndex):int</code>
	 *            - 1
	 * @param group
	 *            The group, the child item, which has been enabled, belongs to,
	 *            as an instance of the generic type GroupType. The group may
	 *            not be null
	 * @param groupIndex
	 *            The index of the group, the child item, which has been
	 *            enabled, belongs to, as an {@link Integer} value. The index
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1
	 */
	private void notifyOnChildEnabled(final ChildType child, final int childIndex, final GroupType group,
			final int groupIndex) {
		for (ExpandableListEnableStateListener<GroupType, ChildType> listener : enableStateListeners) {
			listener.onChildEnabled(this, child, childIndex, group, groupIndex);
		}
	}

	/**
	 * Notifies all listeners, which have been registered to be notified, when
	 * an item has been disabled or disabled, about a child item, which has been
	 * disabled.
	 * 
	 * @param child
	 *            The child item, which has been disabled, as an instance of the
	 *            generic type ChildType. The group item may not be null
	 * @param childIndex
	 *            The index of the child item, which has been disabled, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getChildCount(groupIndex):int</code>
	 *            - 1
	 * @param group
	 *            The group, the child item, which has been disabled, belongs
	 *            to, as an instance of the generic type GroupType. The group
	 *            may not be null
	 * @param groupIndex
	 *            The index of the group, the child item, which has been
	 *            disabled, belongs to, as an {@link Integer} value. The index
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1
	 */
	private void notifyOnChildDisabled(final ChildType child, final int childIndex, final GroupType group,
			final int groupIndex) {
		for (ExpandableListEnableStateListener<GroupType, ChildType> listener : enableStateListeners) {
			listener.onChildDisabled(this, child, childIndex, group, groupIndex);
		}
	}

	/**
	 * Returns a set, which contains the listeners, which should be notified,
	 * when an item has been disabled or enabled.
	 * 
	 * @return A set, which contains the listeners, which should be notified,
	 *         when an item has been disabled or enabled, as an instance of the
	 *         type {@link Set} or an empty setr, if no listeners should be
	 *         notified
	 */
	protected final Set<ExpandableListEnableStateListener<GroupType, ChildType>> getEnableStateListeners() {
		return enableStateListeners;
	}

	/**
	 * Sets the set, which contains the listeners, which should be notified,
	 * when an item has been disabled or enabled.
	 * 
	 * @param enableStateListeners
	 *            The set, which should be set, as an instance of the type
	 *            {@link Set} or an empty set, if no listeners should be
	 *            notified
	 */
	protected final void setEnableStateListeners(
			final Set<ExpandableListEnableStateListener<GroupType, ChildType>> enableStateListeners) {
		ensureNotNull(enableStateListeners, "The enable state listeners may not be null");
		this.enableStateListeners = enableStateListeners;
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary group and child items, which may be disabled or enabled.
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
	 *            True, if duplicate child items, regardless from the group they
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
	 *            an instance of the type {@link Set}, or an empty set, if no
	 *            listeners should be notified
	 * @param adapterListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been modified, as an
	 *            instance of the type {@link Set}, or an empty set, if no
	 *            listeners should be notified
	 * @param expansionListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when a group item has been expanded or collapsed, as an
	 *            instance of the type {@link Set}, or an empty set, if no
	 *            listeners should be notified
	 * @param setChildEnableStatesImplicitly
	 *            True, if the enable states of children should be also set,
	 *            when the enable state of the group, they belong to, is set
	 * @param enableStateListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when an item has been disabled or enabled, as an instance of
	 *            the type {@link Set}, or an empty set, if no listeners should
	 *            be notified
	 */
	protected AbstractEnableStateExpadableListAdapter(final Context context, final Inflater groupInflater,
			final Inflater childInflater, final DecoratorType decorator, final LogLevel logLevel,
			final MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter,
			final boolean allowDuplicateChildren, final boolean notifyOnChange, final boolean expandGroupOnClick,
			final Set<ExpandableListAdapterItemClickListener<GroupType, ChildType>> itemClickListeners,
			final Set<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners,
			final Set<ExpansionListener<GroupType, ChildType>> expansionListeners,
			final boolean setChildEnableStatesImplicitly,
			final Set<ExpandableListEnableStateListener<GroupType, ChildType>> enableStateListeners) {
		super(context, groupInflater, childInflater, decorator, logLevel, groupAdapter, allowDuplicateChildren,
				notifyOnChange, expandGroupOnClick, itemClickListeners, adapterListeners, expansionListeners);
		setEnableStateListeners(enableStateListeners);
		setChildEnableStatesImplicitly(setChildEnableStatesImplicitly);
	}

	@Override
	protected void onSaveInstanceState(final Bundle outState) {
		outState.putBoolean(SET_CHILD_ENABLE_STATES_IMPLICITLY_BUNDLE_KEY, areChildEnableStatesSetImplicitly());
	}

	@Override
	protected void onRestoreInstanceState(final Bundle savedState) {
		setChildEnableStatesImplicitly = savedState.getBoolean(SET_CHILD_ENABLE_STATES_IMPLICITLY_BUNDLE_KEY, true);
	}

	@Override
	public final boolean isGroupEnabled(final int groupIndex) {
		return getGroupAdapter().isEnabled(groupIndex);
	}

	@Override
	public final boolean isGroupEnabled(final GroupType group) {
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
	public final Collection<Integer> getEnabledGroupIndices() {
		return getGroupAdapter().getEnabledIndices();
	}

	@Override
	public final Collection<GroupType> getEnabledGroups() {
		List<GroupType> enabledGroups = new ArrayList<>();

		for (int i = 0; i < getGroupCount(); i++) {
			Group<GroupType, ChildType> group = getGroupAdapter().getItem(i);

			if (group.isEnabled()) {
				enabledGroups.add(group.getData());
			}
		}

		return enabledGroups;
	}

	@Override
	public final Collection<Integer> getDisabledGroupIndices() {
		return getGroupAdapter().getDisabledIndices();
	}

	@Override
	public final Collection<GroupType> getDisabledGroups() {
		List<GroupType> disabledGroups = new ArrayList<>();

		for (int i = 0; i < getGroupCount(); i++) {
			Group<GroupType, ChildType> group = getGroupAdapter().getItem(i);

			if (!group.isEnabled()) {
				disabledGroups.add(group.getData());
			}
		}

		return disabledGroups;
	}

	@Override
	public final int getEnabledGroupCount() {
		return getGroupAdapter().getEnabledItemCount();
	}

	@Override
	public final void setGroupEnabled(final int groupIndex, final boolean enabled) {
		Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);

		if (group.isEnabled() != enabled) {
			group.setEnabled(enabled);

			if (enabled) {
				notifyOnGroupEnabled(group.getData(), groupIndex);
			} else {
				notifyOnGroupDisabled(group.getData(), groupIndex);
			}

			notifyOnDataSetChanged();
			String message = enabled ? "Enabled"
					: "Disabled" + " group \"" + group.getData() + "\" at index " + groupIndex;
			getLogger().logInfo(getClass(), message);
		} else {
			String message = "The enable state of group \"" + group.getData() + "\" at index " + groupIndex
					+ " has not been changed, because it is already " + (enabled ? "enabled" : "disabled");
			getLogger().logDebug(getClass(), message);
		}

		if (areChildEnableStatesSetImplicitly()) {
			setAllChildrenEnabled(groupIndex, enabled);
		}
	}

	@Override
	public final void setGroupEnabled(final GroupType group, final boolean enabled) {
		setGroupEnabled(group, enabled);
	}

	@Override
	public final boolean triggerGroupEnableState(final int groupIndex) {
		return triggerGroupEnableState(false, groupIndex);
	}

	@Override
	public final boolean triggerGroupEnableState(final boolean triggerChildStates, final int groupIndex) {
		boolean enabled = !isGroupEnabled(groupIndex);
		setGroupEnabled(groupIndex, enabled);

		if (triggerChildStates) {
			triggerAllChildEnableStates(groupIndex);
		}

		return enabled;
	}

	@Override
	public final boolean triggerGroupEnableState(final GroupType group) {
		return triggerGroupEnableState(false, group);
	}

	@Override
	public final boolean triggerGroupEnableState(final boolean triggerChildStates, final GroupType group) {
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
	public final boolean isChildEnabled(final GroupType group, final int childIndex) {
		return isChildEnabled(indexOfGroupOrThrowException(group), childIndex);
	}

	@Override
	public final boolean isChildEnabled(final GroupType group, final ChildType child) {
		return isChildEnabled(indexOfGroupOrThrowException(group), child);
	}

	@Override
	public final boolean isChildEnabled(final int groupIndex, final int childIndex) {
		return getGroupAdapter().getItem(groupIndex).getChildAdapter().isEnabled(childIndex);
	}

	@Override
	public final boolean isChildEnabled(final int groupIndex, final ChildType child) {
		return isChildEnabled(groupIndex, indexOfChildOrThrowException(groupIndex, child));
	}

	@Override
	public final int getFirstEnabledChildIndex(final GroupType group) {
		return getFirstEnabledChildIndex(indexOfGroupOrThrowException(group));
	}

	@Override
	public final ChildType getFirstEnabledChild(final GroupType group) {
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
	public final int getLastEnabledChildIndex(final GroupType group) {
		return getLastEnabledChildIndex(indexOfGroupOrThrowException(group));
	}

	@Override
	public final ChildType getLastEnabledChild(final GroupType group) {
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
	public final int getFirstDisabledChildIndex(final GroupType group) {
		return getFirstDisabledChildIndex(indexOfGroupOrThrowException(group));
	}

	@Override
	public final ChildType getFirstDisabledChild(final GroupType group) {
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
	public final int getLastDisabledChildIndex(final GroupType group) {
		return getLastDisabledChildIndex(indexOfGroupOrThrowException(group));
	}

	@Override
	public final ChildType getLastDisabledChild(final GroupType group) {
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
	public final Collection<ChildType> getEnabledChildren() {
		List<ChildType> enabledChildren = new ArrayList<>();

		for (int i = 0; i < getGroupCount(); i++) {
			enabledChildren.addAll(getEnabledChildren(i));
		}

		return enabledChildren;
	}

	@Override
	public final Collection<Integer> getEnabledChildIndices(final GroupType group) {
		return getEnabledChildIndices(indexOfGroupOrThrowException(group));
	}

	@Override
	public final Collection<ChildType> getEnabledChildren(final GroupType group) {
		return getEnabledChildren(indexOfGroupOrThrowException(group));
	}

	@Override
	public final Collection<Integer> getEnabledChildIndices(final int groupIndex) {
		return getGroupAdapter().getItem(groupIndex).getChildAdapter().getEnabledIndices();
	}

	@Override
	public final Collection<ChildType> getEnabledChildren(final int groupIndex) {
		return getGroupAdapter().getItem(groupIndex).getChildAdapter().getEnabledItems();
	}

	@Override
	public final Collection<ChildType> getDisabledChildren() {
		List<ChildType> disabledChildren = new ArrayList<>();

		for (int i = 0; i < getGroupCount(); i++) {
			disabledChildren.addAll(getDisabledChildren(i));
		}

		return disabledChildren;
	}

	@Override
	public final Collection<Integer> getDisabledChildIndices(final GroupType group) {
		return getDisabledChildIndices(indexOfGroupOrThrowException(group));
	}

	@Override
	public final Collection<ChildType> getDisabledChildren(final GroupType group) {
		return getDisabledChildren(indexOfGroupOrThrowException(group));
	}

	@Override
	public final Collection<Integer> getDisabledChildIndices(final int groupIndex) {
		return getGroupAdapter().getItem(groupIndex).getChildAdapter().getDisabledIndices();
	}

	@Override
	public final Collection<ChildType> getDisabledChildren(final int groupIndex) {
		return getGroupAdapter().getItem(groupIndex).getChildAdapter().getDisabledItems();
	}

	@Override
	public final int getEnabledChildCount() {
		return getEnabledChildren().size();
	}

	@Override
	public final int getEnabledChildCount(final GroupType group) {
		return getEnabledChildren(group).size();
	}

	@Override
	public final int getEnabledChildCount(final int groupIndex) {
		return getEnabledChildren(groupIndex).size();
	}

	@Override
	public final void setChildEnabled(final GroupType group, final int childIndex, final boolean enabled) {
		setChildEnabled(indexOfGroupOrThrowException(group), childIndex, enabled);
	}

	@Override
	public final void setChildEnabled(final GroupType group, final ChildType child, final boolean enabled) {
		setChildEnabled(indexOfGroupOrThrowException(group), child, enabled);
	}

	@Override
	public final void setChildEnabled(final int groupIndex, final int childIndex, final boolean enabled) {
		Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
		MultipleChoiceListAdapter<ChildType> childAdapter = group.getChildAdapter();

		if (childAdapter.isEnabled(childIndex) != enabled) {
			childAdapter.setEnabled(childIndex, enabled);

			if (enabled) {
				notifyOnChildEnabled(childAdapter.getItem(childIndex), childIndex, group.getData(), groupIndex);
			} else {
				notifyOnChildDisabled(childAdapter.getItem(childIndex), childIndex, group.getData(), groupIndex);
			}

			notifyOnDataSetChanged();
			String message = enabled ? "Enabled"
					: "Disabled" + " child \"" + childAdapter.getItem(childIndex) + "\" at index " + childIndex
							+ " of group \"" + group.getData() + "\" at index " + groupIndex;
			getLogger().logInfo(getClass(), message);
		} else {
			String message = "The enable state of child \"" + childAdapter.getItem(childIndex) + "\" at index "
					+ childIndex + " of group \"" + group.getData() + "\" at index " + groupIndex
					+ " has not been changed, because it is already " + (enabled ? "enabled" : "disabled");
			getLogger().logDebug(getClass(), message);
		}
	}

	@Override
	public final void setChildEnabled(final int groupIndex, final ChildType child, final boolean enabled) {
		setChildEnabled(groupIndex, indexOfChildOrThrowException(groupIndex, child), enabled);
	}

	@Override
	public final boolean triggerChildEnableState(final GroupType group, final int childIndex) {
		return triggerChildEnableState(indexOfGroupOrThrowException(group), childIndex);
	}

	@Override
	public final boolean triggerChildEnableState(final GroupType group, final ChildType child) {
		return triggerChildEnableState(indexOfGroupOrThrowException(group), child);
	}

	@Override
	public final boolean triggerChildEnableState(final int groupIndex, final int childIndex) {
		boolean enabled = !isChildEnabled(groupIndex, childIndex);
		setChildEnabled(groupIndex, childIndex, enabled);
		return enabled;
	}

	@Override
	public final boolean triggerChildEnableState(final int groupIndex, final ChildType child) {
		return triggerChildEnableState(groupIndex, indexOfChildOrThrowException(groupIndex, child));
	}

	@Override
	public final void setAllChildrenEnabled(final boolean enabled) {
		for (int i = 0; i < getGroupCount(); i++) {
			setAllChildrenEnabled(i, enabled);
		}
	}

	@Override
	public final void setAllChildrenEnabled(final GroupType group, final boolean enabled) {
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
	public final void triggerAllChildEnableStates(final GroupType group) {
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
	public final void addEnableStateListener(final ExpandableListEnableStateListener<GroupType, ChildType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		enableStateListeners.add(listener);
		String message = "Added enable state listener \"" + listener + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final void removeEnableStateListener(
			final ExpandableListEnableStateListener<GroupType, ChildType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		enableStateListeners.remove(listener);
		String message = "Removed enable state listener \"" + listener + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public abstract AbstractEnableStateExpadableListAdapter<GroupType, ChildType, DecoratorType> clone()
			throws CloneNotSupportedException;

}