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
package de.mrapp.android.adapter.list.itemstate;

import static de.mrapp.android.adapter.util.Condition.ensureAtLeast;
import static de.mrapp.android.adapter.util.Condition.ensureAtMaximum;
import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.ListAdapterItemClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.AbstractEnableStateListAdapter;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.logging.LogLevel;
import de.mrapp.android.adapter.util.VisibleForTesting;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a list of arbitrary items, which may have multiple states. Such an adapter's
 * purpose is to provide the underlying data for visualization using a
 * {@link AbsListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * @param <DecoratorType>
 *            The type of the decorator, which allows to customize the
 *            appearance of the views, which are used to visualize the items of
 *            the adapter
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractItemStateListAdapter<DataType, DecoratorType>
		extends AbstractEnableStateListAdapter<DataType, DecoratorType> implements ItemStateListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The key, which is used to store the number of states, the adapter's items
	 * may have, within a bundle.
	 */
	@VisibleForTesting
	protected static final String NUMBER_OF_ITEM_STATES_BUNDLE_KEY = AbstractItemStateListAdapter.class.getSimpleName()
			+ "::NumberOfItemStates";

	/**
	 * The key, which is used to store, whether the state of an item should be
	 * triggered, when it is clicked by the user, or not, within a bundle.
	 */
	@VisibleForTesting
	protected static final String TRIGGER_ITEM_STATE_ON_CLICK_BUNDLE_KEY = AbstractItemStateListAdapter.class
			.getSimpleName() + "::TriggerItemStateOnClick";

	/**
	 * A set, which contains the listeners, which should be notified, when the
	 * state of an item of the adapter has been changed.
	 */
	private transient Set<ListItemStateListener<DataType>> itemStateListeners;

	/**
	 * The number of states, the adapter's items can have.
	 */
	private int numberOfItemStates;

	/**
	 * True, if the state of an item should be triggered, when it is clicked by
	 * the user, false otherwise.
	 */
	private boolean triggerItemStateOnClick;

	/**
	 * Creates and returns a listener, which allows to trigger the state of an
	 * item, when it is clicked by the user.
	 * 
	 * @return The listener, which has been created, as an instance of the type
	 *         {@link ListAdapterItemClickListener}
	 */
	private ListAdapterItemClickListener<DataType> createItemClickListener() {
		return new ListAdapterItemClickListener<DataType>() {

			@Override
			public void onItemClicked(final ListAdapter<DataType> adapter, final DataType item, final int index) {
				if (isItemStateTriggeredOnClick()) {
					getLogger().logVerbose(getClass(), "Triggering item state on click...");
					triggerItemState(index);
				}
			}

		};
	}

	/**
	 * Notifies all listeners, which have registered to be notified, when the
	 * state of an item of the adapter has been changed, about an item whose
	 * state has been changed.
	 * 
	 * @param item
	 *            The item, whose state has been changed, as an instance of the
	 *            generic type DataType. The item may not be null
	 * @param index
	 *            The index of the item, whose state has been changed, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1
	 * @param state
	 *            The new state of the item, whose state has been changed, as an
	 *            {@link Integer} value. The state must be between 0 and the
	 *            value of the method <code>getNumberOfStates():int</code> - 1
	 */
	private void notifyOnItemStateChanged(final DataType item, final int index, final int state) {
		for (ListItemStateListener<DataType> listener : itemStateListeners) {
			listener.onItemStateChanged(this, item, index, state);
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
	protected final Set<ListItemStateListener<DataType>> getItemStateListeners() {
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
	protected final void setItemStateListeners(final Set<ListItemStateListener<DataType>> itemStateListeners) {
		ensureNotNull(itemStateListeners, "The item state listeners may not be null");
		this.itemStateListeners = itemStateListeners;
	}

	@Override
	protected void onSaveInstanceState(final Bundle savedState) {
		savedState.putInt(NUMBER_OF_ITEM_STATES_BUNDLE_KEY, getNumberOfItemStates());
		savedState.putBoolean(TRIGGER_ITEM_STATE_ON_CLICK_BUNDLE_KEY, isItemStateTriggeredOnClick());
	}

	@Override
	protected void onRestoreInstanceState(final Bundle savedState) {
		numberOfItemStates = savedState.getInt(NUMBER_OF_ITEM_STATES_BUNDLE_KEY, 1);
		triggerItemStateOnClick = savedState.getBoolean(TRIGGER_ITEM_STATE_ON_CLICK_BUNDLE_KEY, false);
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary items, which may have multiple states.
	 * 
	 * @param context
	 *            The context, the adapter should belong to, as an instance of
	 *            the class {@link Context}. The context may not be null
	 * @param inflater
	 *            The inflater, which should be used to inflate the views, which
	 *            are used to visualize the adapter's items, as an instance of
	 *            the type {@link Inflater}. The inflater may not be null
	 * @param decorator
	 *            The decorator, which should be used to customize the
	 *            appearance of the views, which are used to visualize the items
	 *            of the adapter, as an instance of the generic type
	 *            DecoratorType. The decorator may not be null
	 * @param logLevel
	 *            The log level, which should be used for logging, as a value of
	 *            the enum {@link LogLevel}. The log level may not be null
	 * @param items
	 *            A list, which contains the adapter's items, or an empty list,
	 *            if the adapter should not contain any items
	 * @param allowDuplicates
	 *            True, if duplicate items should be allowed, false otherwise
	 * @param notifyOnChange
	 *            True, if the method <code>notifyDataSetChanged():void</code>
	 *            should be automatically called when the adapter's underlying
	 *            data has been changed, false otherwise
	 * @param itemClickListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when an item of the adapter has been clicked by the user, as
	 *            an instance of the type {@link Set} or an empty set, if no
	 *            listeners should be notified
	 * @param adapterListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been modified or an
	 *            empty set, if no listeners should be notified
	 * @param enableStateListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when an item has been disabled or enabled or an empty set, if
	 *            no listeners should be notified
	 * @param numberOfItemStates
	 *            The number of states, the adapter's items may have, as an
	 *            {@link Integer} value. The value must be at least 1
	 * @param triggerItemStateOnClick
	 *            True, if the state of an item should be triggered, when it is
	 *            clicked by the user, false otherwise
	 * @param itemStateListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the state of an item has been changed or an empty set, if
	 *            no listeners should be notified
	 */
	protected AbstractItemStateListAdapter(final Context context, final Inflater inflater,
			final DecoratorType decorator, final LogLevel logLevel, final ArrayList<Item<DataType>> items,
			final boolean allowDuplicates, final boolean notifyOnChange,
			final Set<ListAdapterItemClickListener<DataType>> itemClickListeners,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners, final int numberOfItemStates,
			final boolean triggerItemStateOnClick, final Set<ListItemStateListener<DataType>> itemStateListeners) {
		super(context, inflater, decorator, logLevel, items, allowDuplicates, notifyOnChange, itemClickListeners,
				adapterListeners, enableStateListeners);
		setNumberOfItemStates(numberOfItemStates);
		triggerItemStateOnClick(triggerItemStateOnClick);
		setItemStateListeners(itemStateListeners);
		addItemClickListener(createItemClickListener());
	}

	@Override
	public final int getNumberOfItemStates() {
		return numberOfItemStates;
	}

	@Override
	public final void setNumberOfItemStates(final int numberOfItemStates) {
		ensureAtLeast(numberOfItemStates, 1, "The number of items states must be at least 1",
				IllegalArgumentException.class);
		this.numberOfItemStates = numberOfItemStates;
		String message = "Set number of item states to " + numberOfItemStates;
		getLogger().logDebug(getClass(), message);

		for (int i = 0; i < getCount(); i++) {
			if (getItemState(i) > maxItemState()) {
				setItemState(i, maxItemState());
			}
		}
	}

	@Override
	public final int minItemState() {
		return 0;
	}

	@Override
	public final int maxItemState() {
		return getNumberOfItemStates() - 1;
	}

	@Override
	public final int getItemState(final int index) {
		return getItems().get(index).getState();
	}

	@Override
	public final int getItemState(final DataType item) {
		return getItemState(indexOfOrThrowException(item));
	}

	@Override
	public final int setItemState(final int index, final int state) {
		ensureAtLeast(state, minItemState(), "The state must be at minimum " + minItemState(),
				IllegalArgumentException.class);
		ensureAtMaximum(state, maxItemState(), "The state must be at maximum " + maxItemState(),
				IllegalArgumentException.class);
		Item<DataType> item = getItems().get(index);

		if (item.isEnabled()) {
			int previousState = item.getState();

			if (previousState != state) {
				item.setState(state);
				notifyOnItemStateChanged(item.getData(), index, state);
				notifyOnDataSetChanged();
				String message = "Changed state of item \"" + item.getData() + "\" at index " + index + " from "
						+ previousState + " to " + state;
				getLogger().logInfo(getClass(), message);
				return previousState;
			} else {
				String message = "The state of item \"" + item.getData() + "\" at index " + index
						+ " has not been changed, because state " + state + " is already set";
				getLogger().logDebug(getClass(), message);
				return previousState;
			}
		} else {
			String message = "The state of item \"" + item.getData() + "\" at index " + index
					+ " has not been changed, because the item is disabled";
			getLogger().logDebug(getClass(), message);
			return -1;
		}
	}

	@Override
	public final int setItemState(final DataType item, final int state) {
		return setItemState(indexOfOrThrowException(item), state);
	}

	@Override
	public final boolean setAllItemStates(final int state) {
		ensureAtLeast(state, minItemState(), "The state must be at least " + minItemState(),
				IllegalArgumentException.class);
		ensureAtMaximum(state, maxItemState(), "The state must be at maximum " + maxItemState(),
				IllegalArgumentException.class);
		boolean result = true;

		for (int i = 0; i < getCount(); i++) {
			result &= (setItemState(i, state) != -1);
		}

		return result;
	}

	@Override
	public final int triggerItemState(final int index) {
		if (isEnabled(index)) {
			int previousState = getItemState(index);

			if (previousState == maxItemState()) {
				setItemState(index, 0);
			} else {
				setItemState(index, previousState + 1);
			}

			return previousState;
		} else {
			return -1;
		}
	}

	@Override
	public final int triggerItemState(final DataType item) {
		return triggerItemState(indexOfOrThrowException(item));
	}

	@Override
	public final boolean triggerAllItemStates() {
		boolean result = true;

		for (int i = 0; i < getCount(); i++) {
			result &= (triggerItemState(i) != -1);
		}

		return result;
	}

	@Override
	public final int getFirstIndexWithSpecificState(final int state) {
		for (int i = 0; i < getCount(); i++) {
			if (getItems().get(i).getState() == state) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final DataType getFirstItemWithSpecificState(final int state) {
		for (Item<DataType> item : getItems()) {
			if (item.getState() == state) {
				return item.getData();
			}
		}

		return null;
	}

	@Override
	public final int getLastIndexWithSpecificState(final int state) {
		for (int i = getCount() - 1; i >= 0; i--) {
			if (getItems().get(i).getState() == state) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final DataType getLastItemWithSpecificState(final int state) {
		for (int i = getCount() - 1; i >= 0; i--) {
			Item<DataType> item = getItems().get(i);

			if (item.getState() == state) {
				return item.getData();
			}
		}

		return null;
	}

	@Override
	public final Collection<Integer> getIndicesWithSpecificState(final int state) {
		List<Integer> indices = new ArrayList<Integer>();

		for (int i = 0; i < getCount(); i++) {
			if (getItems().get(i).getState() == state) {
				indices.add(i);
			}
		}

		return indices;
	}

	@Override
	public final Collection<DataType> getItemsWithSpecificState(final int state) {
		List<DataType> items = new ArrayList<DataType>();

		for (Item<DataType> item : getItems()) {
			if (item.getState() == state) {
				items.add(item.getData());
			}
		}

		return items;
	}

	@Override
	public final int getItemStateCount(final int state) {
		return getItemsWithSpecificState(state).size();
	}

	@Override
	public final boolean isItemStateTriggeredOnClick() {
		return triggerItemStateOnClick;
	}

	@Override
	public final void triggerItemStateOnClick(final boolean triggerItemStateOnClick) {
		this.triggerItemStateOnClick = triggerItemStateOnClick;
		String message = "Item states are now " + (triggerItemStateOnClick ? "" : "not ") + "triggered on click";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final void addItemStateListener(final ListItemStateListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		itemStateListeners.add(listener);
		String message = "Added item state listener \"" + listener + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final void removeItemStateListener(final ListItemStateListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		itemStateListeners.remove(listener);
		String message = "Removed item state listener \"" + listener + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + numberOfItemStates;
		result = prime * result + (triggerItemStateOnClick ? 1231 : 1237);
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
		AbstractItemStateListAdapter<?, ?> other = (AbstractItemStateListAdapter<?, ?>) obj;
		if (numberOfItemStates != other.numberOfItemStates)
			return false;
		if (triggerItemStateOnClick != other.triggerItemStateOnClick)
			return false;
		return true;
	}

	@Override
	public abstract AbstractItemStateListAdapter<DataType, DecoratorType> clone() throws CloneNotSupportedException;

}