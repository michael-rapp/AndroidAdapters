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
package de.mrapp.android.adapter.expandablelist.itemstate;

import static de.mrapp.android.adapter.util.Condition.ensureAtLeast;
import static de.mrapp.android.adapter.util.Condition.ensureAtMaximum;
import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import de.mrapp.android.adapter.ExpandableListAdapter;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterItemClickListener;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterListener;
import de.mrapp.android.adapter.expandablelist.ExpansionListener;
import de.mrapp.android.adapter.expandablelist.enablestate.AbstractEnableStateExpadableListAdapter;
import de.mrapp.android.adapter.expandablelist.enablestate.ExpandableListEnableStateListener;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.logging.LogLevel;
import de.mrapp.android.adapter.util.VisibleForTesting;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a list of arbitrary group and child items, which may have multiple states.
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
public abstract class AbstractItemStateExpandableListAdapter<GroupType, ChildType, DecoratorType>
		extends AbstractEnableStateExpadableListAdapter<GroupType, ChildType, DecoratorType>
		implements ItemStateExpandableListAdapter<GroupType, ChildType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The key, which is used to store the number of states, the adapter's child
	 * items may have, within a bundle.
	 */
	@VisibleForTesting
	protected static final String NUMBER_OF_CHILD_STATES_BUNDLE_KEY = AbstractItemStateExpandableListAdapter.class
			.getSimpleName() + "::NumberOfChildStates";

	/**
	 * The key, which is used to store, whether the state of a child item should
	 * be triggered, when it is clicked by the user, or not, within a bundle.
	 */
	@VisibleForTesting
	protected static final String TRIGGER_CHILD_STATE_ON_CLICK_BUNDLE_KEY = AbstractItemStateExpandableListAdapter.class
			.getSimpleName() + "::TriggerChildStateOnClick";

	/**
	 * The key, which is used to store, whether the states of children are also
	 * set, when the state of the group, they belong to, is set, within a
	 * bundle.
	 */
	@VisibleForTesting
	protected static final String SET_CHILD_STATES_IMPLICITLY_BUNDLE_KEY = AbstractItemStateExpandableListAdapter.class
			.getSimpleName() + "::SetChildStatesImplicitly";

	/**
	 * A set, which contains the listeners, which should be notified, when the
	 * state of an item of the adapter has been changed.
	 */
	private transient Set<ExpandableListItemStateListener<GroupType, ChildType>> itemStateListeners;

	/**
	 * The number of states, the adapter's child items can have.
	 */
	private int numberOfChildStates;

	/**
	 * True, if the state of a child item should be triggered, when it is
	 * clicked by the user, false otherwise.
	 */
	private boolean triggerChildStateOnClick;

	/**
	 * True, if the states of children is also set, when the state of the group,
	 * they belong to, is set, false otherwise.
	 */
	private boolean setChildStatesImplicitly;

	/**
	 * Creates and returns a listener, which allows to trigger the state on an
	 * item, when it is clicked by the user.
	 * 
	 * @return The listener, which has been created, as an instance of the type
	 *         {@link ExpandableListAdapterItemClickListener}
	 */
	private ExpandableListAdapterItemClickListener<GroupType, ChildType> createItemClickListener() {
		return new ExpandableListAdapterItemClickListener<GroupType, ChildType>() {

			@Override
			public void onGroupClicked(final ExpandableListAdapter<GroupType, ChildType> adapter, final GroupType group,
					final int index) {
				if (isGroupStateTriggeredOnClick()) {
					getLogger().logVerbose(getClass(), "Triggering group state on click...");
					triggerGroupState(index);
				}
			}

			@Override
			public void onChildClicked(final ExpandableListAdapter<GroupType, ChildType> adapter, final ChildType child,
					final int childIndex, final GroupType group, final int groupIndex) {
				if (isChildStateTriggeredOnClick()) {
					getLogger().logVerbose(getClass(), "Triggering child state on click...");
					triggerChildState(groupIndex, childIndex);
				}
			}

		};
	}

	/**
	 * Notifies all listeners, which have registered to be notified, when the
	 * state of an item of the adapter has been changed, about a group item
	 * whose state has been changed.
	 * 
	 * @param group
	 *            The group item, whose state has been changed, as an instance
	 *            of the generic type GroupType. The group item may not be null
	 * @param index
	 *            The index of the group item, whose state has been changed, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getGroupCount():int</code> - 1
	 * @param state
	 *            The new state of the group item, whose state has been changed,
	 *            as an {@link Integer} value. The state must be between 0 and
	 *            the value of the method
	 *            <code>getNumberOfGroupStates():int</code> - 1
	 */
	private void notifyOnGroupStateChanged(final GroupType group, final int index, final int state) {
		for (ExpandableListItemStateListener<GroupType, ChildType> listener : itemStateListeners) {
			listener.onGroupStateChanged(this, group, index, state);
		}
	}

	/**
	 * Notifies all listeners, which have registered to be notified, when the
	 * state of an item of the adapter has been changed, about a child item
	 * whose state has been changed.
	 * 
	 * @param child
	 *            The child item, whose state has been changed, as an instance
	 *            of the generic type ChildType. The child item may not be null
	 * @param childIndex
	 *            The index of the child item, whose state has been changed, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getChildCount(groupIndex):int</code>
	 *            - 1
	 * @param group
	 *            The group item, the child, whose state has been changed,
	 *            belongs to, as an instance of the generic type GroupType. The
	 *            group item may not be null
	 * @param groupIndex
	 *            The index of the group item, the child, whose state has been
	 *            changed, belongs to, as an {@link Integer} value. The index
	 *            must be between 0 and the value of the method
	 *            <code>getGroupCount():int</code> - 1
	 * @param state
	 *            The new state of the child item, whose state has been changed,
	 *            as an {@link Integer} value. The state must be between 0 and
	 *            the value of the method
	 *            <code>getNumberOfChildStates():int</code> - 1
	 */
	private void notifyOnChildStateChanged(final ChildType child, final int childIndex, final GroupType group,
			final int groupIndex, final int state) {
		for (ExpandableListItemStateListener<GroupType, ChildType> listener : itemStateListeners) {
			listener.onChildStateChanged(this, child, childIndex, group, groupIndex, state);
		}
	}

	/**
	 * Returns the set, which contains the listeners, which should be notified,
	 * when the state of an item of the adapter has been changed.
	 * 
	 * @return The set, which contains the listeners, which should be notified,
	 *         when the state of an item of the adapter has been changed, as an
	 *         instance of the type {@link Set} or an empty set, if no listeners
	 *         should be notified
	 */
	protected final Set<ExpandableListItemStateListener<GroupType, ChildType>> getItemStateListeners() {
		return itemStateListeners;
	}

	/**
	 * Sets the set, which contains the listeners, which should be notified,
	 * when the state of an item of the adapter has been changed.
	 * 
	 * @param itemStateListeners
	 *            The set, which should be set, as an instance of the type
	 *            {@link Set} or an empty set, if no listeners should be
	 *            notified
	 */
	protected final void setItemStateListeners(
			final Set<ExpandableListItemStateListener<GroupType, ChildType>> itemStateListeners) {
		ensureNotNull(itemStateListeners, "The item state listeners may not be null");
		this.itemStateListeners = itemStateListeners;
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary group and child items, which may have multiple states.
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
	 * @param setChildStatesImplicitly
	 *            True, if the states of children should be also set, when the
	 *            state of the group, they belong to, is set, false otherwise
	 * @param itemStateListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the state of an item has been changed, or an empty set,
	 *            if no listeners should be notified
	 */
	protected AbstractItemStateExpandableListAdapter(final Context context, final Inflater groupInflater,
			final Inflater childInflater, final DecoratorType decorator, final LogLevel logLevel,
			final MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter,
			final boolean allowDuplicateChildren, final boolean notifyOnChange, final boolean expandGroupOnClick,
			final Set<ExpandableListAdapterItemClickListener<GroupType, ChildType>> itemClickListeners,
			final Set<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners,
			final Set<ExpansionListener<GroupType, ChildType>> expansionListeners,
			final boolean setChildEnableStatesImplicitly,
			final Set<ExpandableListEnableStateListener<GroupType, ChildType>> enableStateListeners,
			final int numberOfGroupStates, final int numberOfChildStates, final boolean triggerGroupStateOnClick,
			final boolean triggerChildStateOnClick, final boolean setChildStatesImplicitly,
			final Set<ExpandableListItemStateListener<GroupType, ChildType>> itemStateListeners) {
		super(context, groupInflater, childInflater, decorator, logLevel, groupAdapter, allowDuplicateChildren,
				notifyOnChange, expandGroupOnClick, itemClickListeners, adapterListeners, expansionListeners,
				setChildEnableStatesImplicitly, enableStateListeners);
		setNumberOfGroupStates(numberOfGroupStates);
		setNumberOfChildStates(numberOfChildStates);
		setChildStatesImplicitly(setChildEnableStatesImplicitly);
		triggerGroupStateOnClick(triggerGroupStateOnClick);
		triggerChildStateOnClick(triggerChildStateOnClick);
		setItemStateListeners(itemStateListeners);
		addItemClickListener(createItemClickListener());
	}

	@Override
	protected void onSaveInstanceState(final Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(NUMBER_OF_CHILD_STATES_BUNDLE_KEY, getNumberOfChildStates());
		outState.putBoolean(TRIGGER_CHILD_STATE_ON_CLICK_BUNDLE_KEY, isChildStateTriggeredOnClick());
		outState.putBoolean(SET_CHILD_STATES_IMPLICITLY_BUNDLE_KEY, areChildStatesSetImplicitly());
	}

	@Override
	protected void onRestoreInstanceState(final Bundle savedState) {
		super.onRestoreInstanceState(savedState);
		numberOfChildStates = savedState.getInt(NUMBER_OF_CHILD_STATES_BUNDLE_KEY);
		triggerChildStateOnClick = savedState.getBoolean(TRIGGER_CHILD_STATE_ON_CLICK_BUNDLE_KEY);
		setChildStatesImplicitly = savedState.getBoolean(SET_CHILD_STATES_IMPLICITLY_BUNDLE_KEY);
	}

	@Override
	public final int getNumberOfGroupStates() {
		return getGroupAdapter().getNumberOfItemStates();
	}

	@Override
	public final void setNumberOfGroupStates(final int numberOfGroupStates) {
		ensureAtLeast(numberOfGroupStates, 1, "The number of group states " + "must be at least 1",
				IllegalArgumentException.class);
		getGroupAdapter().setNumberOfItemStates(numberOfGroupStates);
		String message = "Set number of group states to " + numberOfGroupStates;
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final int minGroupState() {
		return getGroupAdapter().minItemState();
	}

	@Override
	public final int maxGroupState() {
		return getGroupAdapter().maxItemState();
	}

	@Override
	public final int getGroupState(final int groupIndex) {
		return getGroupAdapter().getItemState(groupIndex);
	}

	@Override
	public final int getGroupState(final GroupType group) {
		return getGroupState(indexOfGroupOrThrowException(group));
	}

	@Override
	public final int setGroupState(final int groupIndex, final int state) {
		ensureAtLeast(state, minGroupState(), "The group state must be at minimum " + minGroupState(),
				IllegalArgumentException.class);
		ensureAtMaximum(state, maxGroupState(), "The group state must be at maximum " + maxGroupState(),
				IllegalArgumentException.class);
		Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);

		if (areChildStatesSetImplicitly()) {
			setAllChildStates(groupIndex, state);
		}

		if (group.isEnabled()) {
			int previousState = group.getState();

			if (previousState != state) {
				group.setState(state);
				notifyOnGroupStateChanged(group.getData(), groupIndex, state);
				notifyOnDataSetChanged();
				String message = "Changed state of group \"" + group.getData() + "\" at index " + groupIndex + " from "
						+ previousState + " to " + state;
				getLogger().logInfo(getClass(), message);
				return previousState;
			} else {
				String message = "The state of group \"" + group.getData() + "\" at index " + groupIndex
						+ " has not been changed, because state " + state + " is already set";
				getLogger().logDebug(getClass(), message);
				return previousState;
			}
		} else {
			String message = "The state of group \"" + group.getData() + "\" at index " + groupIndex
					+ " has not been changed, because the group is disabled";
			getLogger().logDebug(getClass(), message);
			return -1;
		}
	}

	@Override
	public final int setGroupState(final GroupType group, final int state) {
		return setGroupState(group, state);
	}

	@Override
	public final boolean setAllGroupStates(final int state) {
		boolean result = true;

		for (int i = 0; i < getGroupCount(); i++) {
			result &= (setGroupState(i, state) != -1);
		}

		return result;
	}

	@Override
	public final int triggerGroupState(final int groupIndex) {
		return triggerGroupState(false, groupIndex);
	}

	@Override
	public final int triggerGroupState(final boolean triggerChildStates, final int groupIndex) {
		if (triggerChildStates) {
			triggerAllChildStates(groupIndex);
		}

		if (isGroupEnabled(groupIndex)) {
			int previousState = getGroupState(groupIndex);

			if (previousState == maxGroupState()) {
				setGroupState(groupIndex, 0);
			} else {
				setGroupState(groupIndex, previousState + 1);
			}

			return previousState;
		} else {
			return -1;
		}
	}

	@Override
	public final int triggerGroupState(final GroupType group) {
		return triggerGroupState(false, group);
	}

	@Override
	public final int triggerGroupState(final boolean triggerChildStates, final GroupType group) {
		return triggerGroupState(triggerChildStates, indexOfGroupOrThrowException(group));
	}

	@Override
	public final boolean triggerAllGroupStates() {
		return triggerAllGroupStates(false);
	}

	@Override
	public final boolean triggerAllGroupStates(final boolean triggerChildStates) {
		boolean result = true;

		for (int i = 0; i < getGroupCount(); i++) {
			result &= (triggerGroupState(triggerChildStates, i) != -1);
		}

		return result;
	}

	@Override
	public final int getFirstGroupIndexWithSpecificState(final int state) {
		return getGroupAdapter().getFirstIndexWithSpecificState(state);
	}

	@Override
	public final GroupType getFirstGroupWithSpecificState(final int state) {
		return getGroupAdapter().getFirstItemWithSpecificState(state).getData();
	}

	@Override
	public final int getLastGroupIndexWithSpecificState(final int state) {
		return getGroupAdapter().getLastIndexWithSpecificState(state);
	}

	@Override
	public final GroupType getLastGroupWithSpecificState(final int state) {
		return getGroupAdapter().getLastItemWithSpecificState(state).getData();
	}

	@Override
	public final Collection<Integer> getGroupIndicesWithSpecificState(final int state) {
		return getGroupAdapter().getIndicesWithSpecificState(state);
	}

	@Override
	public final Collection<GroupType> getGroupsWithSpecificState(final int state) {
		List<GroupType> groups = new ArrayList<>();

		for (int i = 0; i < getGroupCount(); i++) {
			Group<GroupType, ChildType> group = getGroupAdapter().getItem(i);

			if (group.getState() == state) {
				groups.add(group.getData());
			}
		}

		return groups;
	}

	@Override
	public final int getGroupStateCount(final int state) {
		return getGroupAdapter().getItemStateCount(state);
	}

	@Override
	public final boolean isGroupStateTriggeredOnClick() {
		return getGroupAdapter().isItemStateTriggeredOnClick();
	}

	@Override
	public final void triggerGroupStateOnClick(final boolean triggerGroupStateOnClick) {
		getGroupAdapter().triggerItemStateOnClick(triggerGroupStateOnClick);
		String message = "Group states are now " + (triggerGroupStateOnClick ? "" : "not ") + "triggered on click";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final int getNumberOfChildStates() {
		return numberOfChildStates;
	}

	@Override
	public final void setNumberOfChildStates(final int numberOfChildStates) {
		ensureAtLeast(numberOfChildStates, 1, "The number of child states must be at least 1",
				IllegalArgumentException.class);
		this.numberOfChildStates = numberOfChildStates;
		String message = "Set number of child states to " + numberOfChildStates;
		getLogger().logDebug(getClass(), message);

		for (int i = 0; i < getGroupCount(); i++) {
			Group<GroupType, ChildType> group = getGroupAdapter().getItem(i);
			group.getChildAdapter().setNumberOfItemStates(numberOfChildStates);
		}
	}

	@Override
	public final int minChildState() {
		return 0;
	}

	@Override
	public final int maxChildState() {
		return getNumberOfChildStates() - 1;
	}

	@Override
	public final int getChildState(final GroupType group, final int childIndex) {
		return getChildState(indexOfGroupOrThrowException(group), childIndex);
	}

	@Override
	public final int getChildState(final GroupType group, final ChildType child) {
		return getChildState(indexOfGroupOrThrowException(group), child);
	}

	@Override
	public final int getChildState(final int groupIndex, final int childIndex) {
		return getGroupAdapter().getItem(groupIndex).getChildAdapter().getItemState(childIndex);
	}

	@Override
	public final int getChildState(final int groupIndex, final ChildType child) {
		return getChildState(groupIndex, indexOfChildOrThrowException(groupIndex, child));
	}

	@Override
	public final int setChildState(final GroupType group, final int childIndex, final int state) {
		return setChildState(indexOfGroupOrThrowException(group), childIndex, state);
	}

	@Override
	public final int setChildState(final GroupType group, final ChildType child, final int state) {
		return setChildState(indexOfGroupOrThrowException(group), child, state);
	}

	@Override
	public final int setChildState(final int groupIndex, final int childIndex, final int state) {
		ensureAtLeast(state, minChildState(), "The child state must be at minimum " + minChildState(),
				IllegalArgumentException.class);
		ensureAtMaximum(state, maxChildState(), "The child state must be at maximum " + maxChildState(),
				IllegalArgumentException.class);
		Group<GroupType, ChildType> group = getGroupAdapter().getItem(groupIndex);
		MultipleChoiceListAdapter<ChildType> childAdapter = group.getChildAdapter();

		if (childAdapter.isEnabled(childIndex)) {
			int previousState = childAdapter.getItemState(childIndex);

			if (previousState != state) {
				childAdapter.setItemState(childIndex, state);
				notifyOnChildStateChanged(childAdapter.getItem(childIndex), childIndex, group.getData(), groupIndex,
						state);
				notifyOnDataSetChanged();
				String message = "Changed state of child \"" + childAdapter.getItem(childIndex) + "\" at index "
						+ childIndex + " of group \"" + group.getData() + "\" at index " + groupIndex + " from "
						+ previousState + " to " + state;
				getLogger().logInfo(getClass(), message);
				return previousState;
			} else {
				String message = "The state of child \"" + childAdapter.getItem(childIndex) + "\" at index "
						+ childIndex + " of group \"" + group.getData() + "\" at index " + groupIndex
						+ " has not been changed, because state " + state + " is already set";
				getLogger().logDebug(getClass(), message);
				return previousState;
			}
		} else {
			String message = "The state of child \"" + childAdapter.getItem(childIndex) + "\" at index " + childIndex
					+ " of group \"" + group.getData() + "\" at index " + groupIndex
					+ " has not been changed, because the child is disabled";
			getLogger().logDebug(getClass(), message);
			return -1;
		}
	}

	@Override
	public final int setChildState(final int groupIndex, final ChildType child, final int state) {
		return setChildState(groupIndex, indexOfChildOrThrowException(groupIndex, child), state);
	}

	@Override
	public final boolean setAllChildStates(final int state) {
		boolean result = true;

		for (int i = 0; i < getGroupCount(); i++) {
			result &= setAllChildStates(i, state);
		}

		return result;
	}

	@Override
	public final boolean setAllChildStates(final GroupType group, final int state) {
		return setAllChildStates(indexOfGroupOrThrowException(group), state);
	}

	@Override
	public final boolean setAllChildStates(final int groupIndex, final int state) {
		boolean result = true;

		for (int i = 0; i < getChildCount(groupIndex); i++) {
			result &= (setChildState(groupIndex, i, state) != -1);
		}

		return result;
	}

	@Override
	public final int triggerChildState(final GroupType group, final int childIndex) {
		return triggerChildState(indexOfGroupOrThrowException(group), childIndex);
	}

	@Override
	public final int triggerChildState(final GroupType group, final ChildType child) {
		return triggerChildState(indexOfGroupOrThrowException(group), child);
	}

	@Override
	public final int triggerChildState(final int groupIndex, final int childIndex) {
		if (isChildEnabled(groupIndex, childIndex)) {
			int previousState = getChildState(groupIndex, childIndex);

			if (previousState == maxChildState()) {
				setChildState(groupIndex, childIndex, 0);
			} else {
				setChildState(groupIndex, childIndex, previousState + 1);
			}

			return previousState;
		} else {
			return -1;
		}
	}

	@Override
	public final int triggerChildState(final int groupIndex, final ChildType child) {
		return triggerChildState(groupIndex, indexOfChildOrThrowException(groupIndex, child));
	}

	@Override
	public final boolean triggerAllChildStates() {
		boolean result = false;

		for (int i = 0; i < getGroupCount(); i++) {
			result &= triggerAllChildStates(i);
		}

		return result;
	}

	@Override
	public final boolean triggerAllChildStates(final int groupIndex) {
		boolean result = true;

		for (int i = 0; i < getChildCount(groupIndex); i++) {
			result &= (triggerChildState(groupIndex, i) != -1);
		}

		return result;
	}

	@Override
	public final boolean triggerAllChildStates(final GroupType group) {
		return triggerAllChildStates(indexOfGroupOrThrowException(group));
	}

	@Override
	public final int getFirstChildIndexWithSpecificState(final GroupType group, final int state) {
		return getFirstChildIndexWithSpecificState(indexOfGroupOrThrowException(group), state);
	}

	@Override
	public final int getFirstChildIndexWithSpecificState(final int groupIndex, final int state) {
		return getGroupAdapter().getItem(groupIndex).getChildAdapter().getFirstIndexWithSpecificState(state);
	}

	@Override
	public final ChildType getFirstChildWithSpecificState(final GroupType group, final int state) {
		return getFirstChildWithSpecificState(group, state);
	}

	@Override
	public final ChildType getFirstChildWithSpecificState(final int groupIndex, final int state) {
		return getGroupAdapter().getItem(groupIndex).getChildAdapter().getFirstItemWithSpecificState(state);
	}

	@Override
	public final int getLastChildIndexWithSpecificState(final GroupType group, final int state) {
		return getLastChildIndexWithSpecificState(indexOfGroupOrThrowException(group), state);
	}

	@Override
	public final int getLastChildIndexWithSpecificState(final int groupIndex, final int state) {
		return getGroupAdapter().getItem(groupIndex).getChildAdapter().getLastIndexWithSpecificState(state);
	}

	@Override
	public final ChildType getLastChildWithSpecificState(final GroupType group, final int state) {
		return getLastChildWithSpecificState(indexOfGroupOrThrowException(group), state);
	}

	@Override
	public final ChildType getLastChildWithSpecificState(final int groupIndex, final int state) {
		return getGroupAdapter().getItem(groupIndex).getChildAdapter().getLastItemWithSpecificState(state);
	}

	@Override
	public final Collection<Integer> getChildIndicesWithSpecificState(final GroupType group, final int state) {
		return getChildIndicesWithSpecificState(indexOfGroupOrThrowException(group), state);
	}

	@Override
	public final Collection<Integer> getChildIndicesWithSpecificState(final int groupIndex, final int state) {
		return getGroupAdapter().getItem(groupIndex).getChildAdapter().getIndicesWithSpecificState(state);
	}

	@Override
	public final Collection<ChildType> getChildrenWithSpecificState(final int state) {
		List<ChildType> children = new ArrayList<>();

		for (int i = 0; i < getGroupCount(); i++) {
			children.addAll(getChildrenWithSpecificState(i, state));
		}

		return children;
	}

	@Override
	public final Collection<ChildType> getChildrenWithSpecificState(final GroupType group, final int state) {
		return getChildrenWithSpecificState(indexOfGroupOrThrowException(group), state);
	}

	@Override
	public final Collection<ChildType> getChildrenWithSpecificState(final int groupIndex, final int state) {
		return getGroupAdapter().getItem(groupIndex).getChildAdapter().getItemsWithSpecificState(state);
	}

	@Override
	public final int getChildStateCount(final int state) {
		return getChildrenWithSpecificState(state).size();
	}

	@Override
	public final int getChildStateCount(final GroupType group, final int state) {
		return getChildrenWithSpecificState(group, state).size();
	}

	@Override
	public final int getChildStateCount(final int groupIndex, final int state) {
		return getChildrenWithSpecificState(groupIndex, state).size();
	}

	@Override
	public final boolean isChildStateTriggeredOnClick() {
		return triggerChildStateOnClick;
	}

	@Override
	public final void triggerChildStateOnClick(final boolean triggerChildStateOnClick) {
		this.triggerChildStateOnClick = triggerChildStateOnClick;
		String message = "Child states are now " + (triggerChildStateOnClick ? "" : "not ") + "triggered on click";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final boolean areChildStatesSetImplicitly() {
		return setChildStatesImplicitly;
	}

	@Override
	public final void setChildStatesImplicitly(final boolean setChildStatesImplicitly) {
		this.setChildStatesImplicitly = setChildStatesImplicitly;

		if (setChildStatesImplicitly) {
			for (int i = 0; i < getGroupCount(); i++) {
				for (int j = 0; j < getChildCount(i); j++) {
					setChildState(i, j, getGroupState(i));
				}
			}
		}
	}

	@Override
	public final void addItemStateListener(final ExpandableListItemStateListener<GroupType, ChildType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		itemStateListeners.add(listener);
		String message = "Added item state listener \"" + listener + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final void removeItemStateListener(final ExpandableListItemStateListener<GroupType, ChildType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		itemStateListeners.remove(listener);
		String message = "Removed item state listener \"" + listener + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + numberOfChildStates;
		result = prime * result + (setChildStatesImplicitly ? 1231 : 1237);
		result = prime * result + (triggerChildStateOnClick ? 1231 : 1237);
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
		AbstractItemStateExpandableListAdapter<?, ?, ?> other = (AbstractItemStateExpandableListAdapter<?, ?, ?>) obj;
		if (numberOfChildStates != other.numberOfChildStates)
			return false;
		if (setChildStatesImplicitly != other.setChildStatesImplicitly)
			return false;
		if (triggerChildStateOnClick != other.triggerChildStateOnClick)
			return false;
		return true;
	}

	@Override
	public abstract AbstractItemStateExpandableListAdapter<GroupType, ChildType, DecoratorType> clone()
			throws CloneNotSupportedException;

}