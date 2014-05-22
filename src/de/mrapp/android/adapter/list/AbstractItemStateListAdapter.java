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
package de.mrapp.android.adapter.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.View;
import de.mrapp.android.adapter.util.Item;
import de.mrapp.android.adapter.util.Logger;
import static de.mrapp.android.adapter.util.Condition.ensureAtLeast;
import static de.mrapp.android.adapter.util.Condition.ensureAtMaximum;
import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a list of arbitrary items, which can have multiple states. Such adapters are
 * meant to provide the underlying data for visualization using a
 * {@link ListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractItemStateListAdapter<DataType> extends
		AbstractSortableListAdapter<DataType> implements
		ItemStateListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The number of states, the adapter's items can have.
	 */
	private int numberOfItemStates;

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
	 *            value of the adapter's <code>size():int</code> method - 1
	 * @param state
	 *            The new state of the item, whose state has been changed, as an
	 *            {@link Integer} value. The state must be at least 0 and less
	 *            than the value of the method
	 *            <code>getNumberOfStates():int</code> - 1
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

	protected AbstractItemStateListAdapter(final Context context,
			final Logger logger, final int itemViewId, final View itemView,
			final List<Item<DataType>> items,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners,
			final Set<ListSortingListener<DataType>> sortingListeners,
			final int numberOfItemStates) {
		super(context, logger, itemViewId, itemView, items, adapterListeners,
				enableStateListeners, sortingListeners);
		ensureAtLeast(numberOfItemStates, 1, "The number of items states "
				+ "must be at least 1");
		this.numberOfItemStates = numberOfItemStates;
	}

	@Override
	public final int getNumberOfItemStates() {
		return numberOfItemStates;
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
	public abstract AbstractItemStateListAdapter<DataType> clone()
			throws CloneNotSupportedException;

}