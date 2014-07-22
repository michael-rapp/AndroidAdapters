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
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import de.mrapp.android.adapter.datastructure.SerializableWrapper;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.AbstractEnableStateListAdapter;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.util.VisibleForTesting;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a list of arbitrary items, which may have multiple states. Such an adapter's
 * purpose is to provide the underlying data for visualization using a
 * {@link ListView} widget.
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
		extends AbstractEnableStateListAdapter<DataType, DecoratorType>
		implements ItemStateListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The key, which is used to store the number of states, the adapter's items
	 * may have, within a bundle.
	 */
	@VisibleForTesting
	protected static final String NUMBER_OF_ITEM_STATES_BUNDLE_KEY = AbstractItemStateListAdapter.class
			.getSimpleName() + "::NumberOfItemStates";

	/**
	 * The key, which is used to store, whether the state of an item should be
	 * triggered, when it is clicked by the user, or not, within a bundle.
	 */
	@VisibleForTesting
	protected static final String TRIGGER_ITEM_STATE_ON_CLICK_BUNDLE_KEY = AbstractItemStateListAdapter.class
			.getSimpleName() + "::TriggerItemStateOnClick";

	/**
	 * The key, which is used to store the listeners, which should be notified,
	 * when the state of an item has been changed, within a bundle.
	 */
	@VisibleForTesting
	protected static final String ITEM_STATE_LISTENERS_BUNDLE_KEY = AbstractItemStateListAdapter.class
			.getSimpleName() + "::ItemStateListeners";

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
	 * A set, which contains the listeners, which should be notified, when the
	 * state of an item of the adapter has been changed.
	 */
	private Set<ListItemStateListener<DataType>> itemStateListeners;

	/**
	 * Notifies all listeners, which have registered to be notified, when the
	 * state of an item of the adapter has been changed.
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
	private void notifyOnItemStateChanged(final DataType item, final int index,
			final int state) {
		for (ListItemStateListener<DataType> listener : itemStateListeners) {
			listener.onItemStateChanged(item, index, state);
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
	protected final void setItemStateListeners(
			final Set<ListItemStateListener<DataType>> itemStateListeners) {
		ensureNotNull(itemStateListeners,
				"The item state listeners may not be null");
		this.itemStateListeners = itemStateListeners;
	}
	
	@Override
	protected void onItemClicked(final int index) {
		super.onItemClicked(index);
		
		if (isItemStateTriggeredOnClick()) {
			triggerItemState(index);
		}
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
	 * @param items
	 *            A list, which contains the adapter's items, or an empty list,
	 *            if the adapter should not contain any items
	 * @param allowDuplicates
	 *            True, if duplicate items should be allowed, false otherwise
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
	protected AbstractItemStateListAdapter(final Context context,
			final Inflater inflater, final DecoratorType decorator,
			final List<Item<DataType>> items, final boolean allowDuplicates,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners,
			final int numberOfItemStates,
			final boolean triggerItemStateOnClick,
			final Set<ListItemStateListener<DataType>> itemStateListeners) {
		super(context, inflater, decorator, items, allowDuplicates,
				adapterListeners, enableStateListeners);
		setNumberOfItemStates(numberOfItemStates);
		triggerItemStateOnClick(triggerItemStateOnClick);
		setItemStateListeners(itemStateListeners);
	}

	@Override
	public final int getNumberOfItemStates() {
		return numberOfItemStates;
	}

	@Override
	public final void setNumberOfItemStates(final int numberOfItemStates) {
		ensureAtLeast(numberOfItemStates, 1, "The number of items states "
				+ "must be at least 1");
		this.numberOfItemStates = numberOfItemStates;

		for (Item<DataType> item : getItems()) {
			item.setState(Math.min(item.getState(), numberOfItemStates));
		}
	}

	@Override
	public final int getItemState(final int index) {
		return getItems().get(index).getState();
	}

	@Override
	public final int getItemState(final DataType item) {
		return getItemState(indexOf(item));
	}

	@Override
	public final int setItemState(final int index, final int state) {
		ensureAtMaximum(state, numberOfItemStates - 1,
				"The state may be at maximum " + (numberOfItemStates - 1));
		Item<DataType> item = getItems().get(index);
		int previousState = item.getState();
		item.setState(state);
		notifyOnItemStateChanged(item.getData(), index, state);
		notifyDataSetInvalidated();
		return previousState;
	}

	@Override
	public final void setAllItemStates(final int state) {
		for (int i = 0; i < getNumberOfItems(); i++) {
			setItemState(i, state);
		}
	}

	@Override
	public final int triggerItemState(final int index) {
		Item<DataType> item = getItems().get(index);
		int previousState = item.getState();

		if (previousState == numberOfItemStates - 1) {
			item.setState(0);
		} else {
			item.setState(previousState + 1);
		}

		notifyOnItemStateChanged(item.getData(), index, item.getState());
		notifyDataSetInvalidated();
		return previousState;
	}

	@Override
	public final int triggerItemState(final DataType item) {
		return triggerItemState(indexOf(item));
	}

	@Override
	public final void triggerAllItemStates() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			triggerItemState(i);
		}
	}

	@Override
	public final int setItemState(final DataType item, final int state) {
		return setItemState(indexOf(item), state);
	}

	@Override
	public final int getFirstIndexWithSpecificState(final int state) {
		for (int i = 0; i < getNumberOfItems(); i++) {
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
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			if (getItems().get(i).getState() == state) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final DataType getLastItemWithSpecificState(final int state) {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			Item<DataType> item = getItems().get(i);

			if (item.getState() == state) {
				return item.getData();
			}
		}

		return null;
	}

	@Override
	public final List<Integer> getIndicesWithSpecificState(final int state) {
		List<Integer> indices = new ArrayList<Integer>();

		for (int i = 0; i < getNumberOfItems(); i++) {
			if (getItems().get(i).getState() == state) {
				indices.add(i);
			}
		}

		return indices;
	}

	@Override
	public final List<DataType> getItemsWithSpecificState(final int state) {
		List<DataType> items = new ArrayList<DataType>();

		for (Item<DataType> item : getItems()) {
			if (item.getState() == state) {
				items.add(item.getData());
			}
		}

		return items;
	}

	@Override
	public final int getNumberOfItemsWithSpecificState(final int state) {
		return getItemsWithSpecificState(state).size();
	}

	@Override
	public final boolean isItemStateTriggeredOnClick() {
		return triggerItemStateOnClick;
	}

	@Override
	public final void triggerItemStateOnClick(
			final boolean triggerItemStateOnClick) {
		this.triggerItemStateOnClick = triggerItemStateOnClick;
	}

	@Override
	public final void addItemStateListner(
			final ListItemStateListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		itemStateListeners.add(listener);
	}

	@Override
	public final void removeItemStateListener(
			final ListItemStateListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		itemStateListeners.remove(listener);
	}

	@Override
	public void onSaveInstanceState(final Bundle outState) {
		outState.putInt(NUMBER_OF_ITEM_STATES_BUNDLE_KEY,
				getNumberOfItemStates());

		outState.putBoolean(TRIGGER_ITEM_STATE_ON_CLICK_BUNDLE_KEY,
				isItemStateTriggeredOnClick());

		SerializableWrapper<Set<ListItemStateListener<DataType>>> wrappedItemStateListeners = new SerializableWrapper<Set<ListItemStateListener<DataType>>>(
				getItemStateListeners());
		outState.putSerializable(ITEM_STATE_LISTENERS_BUNDLE_KEY,
				wrappedItemStateListeners);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onRestoreInstanceState(final Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			numberOfItemStates = savedInstanceState
					.getInt(NUMBER_OF_ITEM_STATES_BUNDLE_KEY);

			triggerItemStateOnClick = savedInstanceState
					.getBoolean(TRIGGER_ITEM_STATE_ON_CLICK_BUNDLE_KEY);

			SerializableWrapper<Set<ListItemStateListener<DataType>>> wrappedItemStateListeners = (SerializableWrapper<Set<ListItemStateListener<DataType>>>) savedInstanceState
					.getSerializable(ITEM_STATE_LISTENERS_BUNDLE_KEY);
			itemStateListeners = wrappedItemStateListeners.getWrappedInstance();

			notifyDataSetChanged();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + itemStateListeners.hashCode();
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
		if (!itemStateListeners.equals(other.itemStateListeners))
			return false;
		if (numberOfItemStates != other.numberOfItemStates)
			return false;
		if (triggerItemStateOnClick != other.triggerItemStateOnClick)
			return false;
		return true;
	}

	@Override
	public abstract AbstractItemStateListAdapter<DataType, DecoratorType> clone()
			throws CloneNotSupportedException;

}