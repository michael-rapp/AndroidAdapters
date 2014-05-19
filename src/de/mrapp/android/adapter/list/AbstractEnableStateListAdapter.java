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
import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a list of arbitrary items, which can be disabled or enabled. Such adapters
 * are meant to provide the underlying data for visualization using a
 * {@link ListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractEnableStateListAdapter<DataType> extends
		AbstractListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A set, which contains the listeners, which should be notified when an
	 * item has been disabled or enabled.
	 */
	private Set<ListEnableStateListener<DataType>> enableStateListeners;

	/**
	 * Returns a set, which contains the listeners, which should be notified
	 * when an item has been disabled or enabled.
	 * 
	 * @return A set, which contains the listeners, which should be notified
	 *         when an item has been disabled or enabled, as an instance of the
	 *         type {@link Set} or an empty set, if no listeners should be
	 *         notified
	 */
	protected final Set<ListEnableStateListener<DataType>> getEnableStateListeners() {
		return enableStateListeners;
	}

	/**
	 * Sets the set, which contains the listeners, which should be notified when
	 * an item has been disabled or enabled.
	 * 
	 * @param enableStateListeners
	 *            The set, which should be set, as an instance of the type
	 *            {@link Set} or an empty set, if no listeners should be
	 *            notified
	 */
	protected final void setEnableStateListeners(
			final Set<ListEnableStateListener<DataType>> enableStateListeners) {
		ensureNotNull(enableStateListeners,
				"The enable state listeners may not be null");
		this.enableStateListeners = enableStateListeners;
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary items, which can be disabled or enabled.
	 * 
	 * @param context
	 *            The context, the adapter should belong to, as an instance of
	 *            the class {@link Context}. The context may not be null
	 * @param itemViewId
	 *            The id of the view, which should be used to visualize each
	 *            item of the adapter, as an {@link Integer} value. The id must
	 *            specify a valid view from within the \res folder
	 * @param itemView
	 *            The view, which should be used to visualize each item of the
	 *            adapter, as an instance of the class {@link View}. The view
	 *            may be null
	 * @param items
	 *            A list, which contains the the adapter's items, or an empty
	 *            list, if the adapter should not contain any items
	 * @param adapterListeners
	 *            A set, which contains the listeners, which should be notified
	 *            when the adapter's underlying data has been modified or an
	 *            empty set, if no listeners should be notified
	 * @param enableStateListeners
	 *            A set, which contains the listeners, which should be notified
	 *            when an item has been disabled or enabled or an empty set, if
	 *            no listeners should be notified
	 */
	protected AbstractEnableStateListAdapter(final Context context,
			final int itemViewId, final View itemView,
			final List<Item<DataType>> items,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners) {
		super(context, itemViewId, itemView, items, adapterListeners);
		setEnableStateListeners(enableStateListeners);
	}

	/**
	 * Returns, whether the item, which belongs to a specific index, is enabled,
	 * or not.
	 * 
	 * @param index
	 *            The index of the item, whose enable state should be checked,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>size():int</code> - 1
	 * @return True, if the item, which belongs to the given index, is enabled,
	 *         false otherwise
	 */
	public final boolean isEnabled(final int index) {
		return getItems().get(index).isEnabled();
	}

	/**
	 * Returns, whether a specific item is enabled, or not.
	 * 
	 * @param item
	 *            The item, whose enable state should be checked, as an instance
	 *            of the generic type DataType. The item may not be null
	 * @return True, if the given item is enabled, false otherwise
	 */
	public final boolean isEnabled(final DataType item) {
		return getItems().get(indexOf(item)).isEnabled();
	}

	/**
	 * Returns, whether the item, which belongs to a specific index, is
	 * disabled, or not.
	 * 
	 * @param index
	 *            The index of the item, whose enable state should be checked,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>size():int</code> - 1
	 * @return True, if the item, which belongs to the given index, is disabled,
	 *         false otherwise
	 */
	public final boolean isDisabled(final int index) {
		return !isEnabled(index);
	}

	/**
	 * Returns, whether a specific item is disabled, or not.
	 * 
	 * @param item
	 *            The item, whose enable state should be checked, as an instance
	 *            of the generic type DataType. The item may not be null
	 * @return True, if the given item is disabled, false otherwise
	 */
	public final boolean isDisabled(final DataType item) {
		return !isEnabled(item);
	}

	/**
	 * Returns the index of the first enabled item.
	 * 
	 * @return The index of the first enabled item, as an {@link Integer} value
	 *         or -1, if no item is enabled. The index must be between 0 and the
	 *         value of the method <code>size():int</code> - 1
	 */
	public final int getFirstEnabledIndex() {
		for (int i = 0; i < size(); i++) {
			if (getItems().get(i).isEnabled()) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Returns the first enabled item.
	 * 
	 * @return The first enabled item, as an instance of the generic type
	 *         DataType or null, if no item is enabled
	 */
	public final DataType getFirstEnabledItem() {
		for (Item<DataType> item : getItems()) {
			if (item.isEnabled()) {
				return item.getData();
			}
		}

		return null;
	}

	/**
	 * Returns the index of the last enabled item.
	 * 
	 * @return The index of the last enabled item, as an {@link Integer} value
	 *         or -1, if no item is enabled. The index must be between 0 and the
	 *         value of the method <code>size():int</code> - 1
	 */
	public final int getLastEnabledIndex() {
		for (int i = size() - 1; i >= 0; i--) {
			if (getItems().get(i).isEnabled()) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Returns the last enabled item.
	 * 
	 * @return The last enabled item, as an instance of the generic type
	 *         DataType or null, if no item is enabled
	 */
	public final DataType getLastEnabledItem() {
		for (int i = size() - 1; i >= 0; i--) {
			Item<DataType> item = getItems().get(i);

			if (item.isEnabled()) {
				return item.getData();
			}
		}

		return null;
	}

	/**
	 * Returns the index of the first disabled item.
	 * 
	 * @return The index of the first disabled item, as an {@link Integer} value
	 *         or -1, if no item is disabled. The index must be between 0 and
	 *         the value of the method <code>size():int</code> - 1
	 */
	public final int getFirstDisabledIndex() {
		for (int i = 0; i < size(); i++) {
			if (!getItems().get(i).isEnabled()) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Returns the first disabled item.
	 * 
	 * @return The first disabled item, as an instance of the generic type
	 *         DataType or null, if no item is disabled
	 */
	public final DataType getFirstDisabledItem() {
		for (Item<DataType> item : getItems()) {
			if (!item.isEnabled()) {
				return item.getData();
			}
		}

		return null;
	}

	/**
	 * Returns the index of the last disabled item.
	 * 
	 * @return The index of the last disabled item, as an {@link Integer} value
	 *         or -1, if no item is disabled. The index must be between 0 and
	 *         the value of the method <code>size():int</code> - 1
	 */
	public final int getLastDisabledIndex() {
		for (int i = size() - 1; i >= 0; i--) {
			if (!getItems().get(i).isEnabled()) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Returns the index of the last disabled item.
	 * 
	 * @return The index of the last disabled item, as an {@link Integer} value
	 *         or -1, if no item is disabled. The index must be between 0 and
	 *         the value of the method <code>size():int</code> - 1
	 */
	public final DataType getLastDisabledItem() {
		for (int i = size() - 1; i >= 0; i--) {
			Item<DataType> item = getItems().get(i);

			if (!item.isEnabled()) {
				return item.getData();
			}
		}

		return null;
	}

	/**
	 * Returns a list, which contains the indices of all enabled items.
	 * 
	 * @return A list, which contains the indices of all enabled items, as an
	 *         instance of the type {@link List} or an empty list, if no item is
	 *         enabled
	 */
	public final List<Integer> getEnabledIndices() {
		List<Integer> enabledIndices = new ArrayList<Integer>();

		for (int i = 0; i < size(); i++) {
			if (getItems().get(i).isEnabled()) {
				enabledIndices.add(i);
			}
		}

		return enabledIndices;
	}

	/**
	 * Returns a list, which contains all enabled items.
	 * 
	 * @return A list, which contains all enabled items, as an instance of the
	 *         type {@link List} or an empty list, if no item is enabled
	 */
	public final List<DataType> getEnabledItems() {
		List<DataType> enabledItems = new ArrayList<DataType>();

		for (Item<DataType> item : getItems()) {
			if (item.isEnabled()) {
				enabledItems.add(item.getData());
			}
		}

		return enabledItems;
	}

	/**
	 * Returns a list, which contains the indices of all disabled items.
	 * 
	 * @return A list, which contains the indices of all disabled items, as an
	 *         instance of the type {@link List} or an empty list, if no item is
	 *         disabled
	 */
	public final List<Integer> getDisabledIndices() {
		List<Integer> disabledIndices = new ArrayList<Integer>();

		for (int i = 0; i < size(); i++) {
			if (!getItems().get(i).isEnabled()) {
				disabledIndices.add(i);
			}
		}

		return disabledIndices;
	}

	/**
	 * Returns a list, which contains all disabled items.
	 * 
	 * @return A list, which contains all disabled items, as an instance of the
	 *         type {@link List} or an empty list, if no item is disabled
	 */
	public final List<DataType> getDisabledItems() {
		List<DataType> disabledItems = new ArrayList<DataType>();

		for (Item<DataType> item : getItems()) {
			if (!item.isEnabled()) {
				disabledItems.add(item.getData());
			}
		}

		return disabledItems;
	}

	/**
	 * Returns the number of enabled items.
	 * 
	 * @return The number of enabled items, as an {@link Integer} value
	 */
	public final int getNumberOfEnabledItems() {
		return getEnabledItems().size();
	}

	/**
	 * Returns the number of disabled items.
	 * 
	 * @return The number of disabled items, as an {@link Integer} value.
	 */
	public final int getNumberOfDisabledItems() {
		return getDisabledItems().size();
	}

	/**
	 * Enables the item, which belongs to a specific index.
	 * 
	 * @param index
	 *            The index of the item, which should be enabled, as a
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>size():int</code> - 1
	 */
	public final void enable(final int index) {
		getItems().get(index).setEnabled(true);
	}

	/**
	 * Enables a specific item.
	 * 
	 * @param item
	 *            The item, which should be enabled, as an instance of the
	 *            generic type DataType. The item may not be null
	 */
	public final void enable(final DataType item) {
		enable(indexOf(item));
	}

	/**
	 * Disables the item, which belongs to a specific index.
	 * 
	 * @param index
	 *            The index of the item, which should be disabled, as a
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>size():int</code> - 1
	 */
	public final void disable(final int index) {
		getItems().get(index).setEnabled(false);
	}

	/**
	 * Disables a specific item.
	 * 
	 * @param item
	 *            The item, which should be disabled, as an instance of the
	 *            generic type DataType. The item may not be null
	 */
	public final void disable(final DataType item) {
		disable(indexOf(item));
	}

	/**
	 * Triggers the enable state of the item, which belongs to a specific index.
	 * That means, that the item will be disabled, if it is enabled and vice
	 * versa.
	 * 
	 * @param index
	 *            The index of the item, whose enable state should be triggered,
	 *            as an {@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>size():int</code> - 1
	 * @return True, if the item has been enabled, false otherwise
	 */
	public final boolean triggerEnableState(final int index) {
		Item<DataType> item = getItems().get(index);
		item.setEnabled(!item.isEnabled());
		return item.isEnabled();
	}

	/**
	 * Triggers the enable state of a specific item. That means, that the item
	 * will be disabled, if it is enabled and vice versa.
	 * 
	 * @param item
	 *            The item, whose enable state should be triggered, as an
	 *            instance of the generic type DataType. The item may not be
	 *            null
	 * @return True, if the item has been enabled, false otherwise
	 */
	public final boolean triggerEnableState(final DataType item) {
		return triggerEnableState(indexOf(item));
	}

	/**
	 * Enables all items.
	 */
	public final void enableAll() {
		for (Item<DataType> item : getItems()) {
			item.setEnabled(true);
		}
	}

	/**
	 * Disables all items.
	 */
	public final void disableAll() {
		for (Item<DataType> item : getItems()) {
			item.setEnabled(false);
		}
	}

	/**
	 * Triggers the enable states of all items. That means, that the items will
	 * be disabled, if they are enabled and vice versa.
	 */
	public final void triggerAllEnableStates() {
		for (Item<DataType> item : getItems()) {
			item.setEnabled(!item.isEnabled());
		}
	}

	/**
	 * Adds a new listener, which should be notified when an item has been
	 * disable or enabled.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            class {@link ListEnableStateListener}. The listener may not be
	 *            null
	 */
	public final void addEnableStateListner(
			final ListEnableStateListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		enableStateListeners.add(listener);
	}

	/**
	 * Removes a specific listener, which should not be notified when an item
	 * has been disable or enabled, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            class {@link ListEnableStateListener}. The listener may not be
	 *            null
	 */
	public final void removeSortingListener(
			final ListEnableStateListener<DataType> listener) {
		enableStateListeners.remove(listener);
	}

	@Override
	public abstract AbstractEnableStateListAdapter<DataType> clone()
			throws CloneNotSupportedException;

}