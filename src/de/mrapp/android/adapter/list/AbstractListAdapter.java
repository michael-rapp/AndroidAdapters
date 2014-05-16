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

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import de.mrapp.android.adapter.Adapter;
import de.mrapp.android.adapter.Order;
import de.mrapp.android.adapter.util.Item;
import de.mrapp.android.adapter.util.ItemComparator;
import de.mrapp.android.adapter.util.ItemIterator;
import de.mrapp.android.adapter.util.ItemListIterator;
import de.mrapp.android.adapter.util.SerializableWrapper;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a list of arbitrary items. Such adapters are meant to provide the underlying
 * data for visualization using a {@link ListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractListAdapter<DataType> extends BaseAdapter
		implements Adapter, List<DataType>, ListAdapter {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The key, which is used to store the adapter's items within a bundle.
	 */
	private static final String ITEMS_BUNDLE_KEY = AbstractListAdapter.class
			.getSimpleName() + "::Items";

	/**
	 * The key, which is used to store the listeners, which should be notified
	 * when the adapter's underlying data has been modified, within a bundle.
	 */
	private static final String ADAPTER_LISTENERS_BUNDLE_KEY = AbstractListAdapter.class
			.getSimpleName() + "::AdapterListeners";

	/**
	 * The key, which is used to store the listeners, which should be notified
	 * when the adapter's underlying data has been sorted, within a bundle.
	 */
	private static final String SORTING_LISTENERS_BUNDLE_KEY = AbstractListAdapter.class
			.getSimpleName() + "::SortingListners";

	/**
	 * The context, the adapter belongs to.
	 */
	private final transient Context context;

	/**
	 * The id of the view, which is used to visualize each item of the adapter.
	 */
	private final transient int itemViewId;

	/**
	 * The view, which is used to visualize each item of the adapter.
	 */
	private final transient View itemView;

	/**
	 * A list, which contains the the adapter's items.
	 */
	private List<Item<DataType>> items;

	/**
	 * A set, which contains the listeners, which should be notified when the
	 * adapter's underlying data has been modified.
	 */
	private Set<ListAdapterListener<DataType>> adapterListeners;

	/**
	 * A set, which contains the listeners, which should be notified when the
	 * adapter's underlying data has been sorted.
	 */
	private Set<ListSortingListener<DataType>> sortingListeners;

	/**
	 * Notifies all listeners, which have been registered to be notified when
	 * the adapter's underlying data has been modified, about an item, which has
	 * been added to the adapter.
	 * 
	 * @param item
	 *            The item, which has been added to the adapter, as an instance
	 *            of the generic type DataType. The item may not be null
	 * @param index
	 *            The index of the item, which has been added to the adapter, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>size():int</code> - 1
	 */
	private void notifyOnItemAdded(final DataType item, final int index) {
		for (ListAdapterListener<DataType> listener : adapterListeners) {
			listener.onItemAdded(item, index);
		}
	}

	/**
	 * Notifies all listeners, which have been registered to be notified when
	 * the adapter's underlying data has been modified, about an item, which has
	 * been removed from the adapter.
	 * 
	 * @param item
	 *            The item, which has been removed from the adapter, as an
	 *            instance of the generic type DataType. The item may not be
	 *            null
	 * @param index
	 *            The index of the item, which has been removed from the
	 *            adapter, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method <code>size():int</code>
	 *            - 2.
	 */
	private void notifyOnItemRemoved(final DataType item, final int index) {
		for (ListAdapterListener<DataType> listener : adapterListeners) {
			listener.onItemRemoved(item, index);
		}
	}

	/**
	 * Notifies all listeners, which have been registered to be notified when
	 * the adapter's underlying data has been sorted.
	 * 
	 * @param sortedList
	 *            A list, which contains the adapter's sorted items, as an
	 *            instance of the type {@link List}. The list may not be null
	 * @param order
	 *            The order, which has been used to sort the list, as a value of
	 *            the enum {@link Order}. The order may either be
	 *            <code>ASCENDING</code> or <code>DESCENDING</code>
	 */
	private void notifyOnSorted(final List<DataType> sortedList,
			final Order order) {
		for (ListSortingListener<DataType> listener : sortingListeners) {
			listener.onSorted(sortedList, order);
		}
	}

	/**
	 * Returns the view, which is used to visualize each item of the adapter. If
	 * a view has been passed to the adapter, the view is returned, otherwise
	 * the view with the given id is inflated.
	 * 
	 * @param parent
	 *            The parent, that this view will eventually be attached to, as
	 *            an instance of the class {@link ViewGroup}. The parent may not
	 *            be null
	 * @return The view, which is used to visualize each item of the adapter, as
	 *         an instance of the class {@link View}. The view may not be null
	 */
	protected final View inflateOrReturnItemView(final ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (itemView != null) {
			return itemView;
		} else {
			return inflater.inflate(itemViewId, parent, false);
		}
	}

	/**
	 * Creates and returns a deep copy of the list, which contains the adapter's
	 * items.
	 * 
	 * @return A deep copy of the list, which contains the adapter's items, as
	 *         an instance of the type {@link List}. The list may not be null
	 * @throws CloneNotSupportedException
	 *             The exception, which is thrown, if cloning is not supported
	 *             by the adapter's underlying data
	 */
	protected final List<Item<DataType>> cloneItems()
			throws CloneNotSupportedException {
		List<Item<DataType>> clonedItems = new ArrayList<Item<DataType>>();

		for (Item<DataType> item : items) {
			clonedItems.add(item.clone());
		}

		return clonedItems;
	}

	/**
	 * Returns the id of the view, which is used to visualize each item of the
	 * adapter.
	 * 
	 * @return The id of the view, which should be used to visualize each item
	 *         of the adapter, as an {@link Integer} value. The id must specify
	 *         a valid view from within the \res folder
	 */
	protected final int getItemViewId() {
		return itemViewId;
	}

	/**
	 * Returns the view, which is used to visualize each item of the adapter.
	 * 
	 * @return The view, which should be used to visualize each item of the
	 *         adapter, as an instance of the class {@link View}. The view may
	 *         be null
	 */
	protected final View getItemView() {
		return itemView;
	}

	/**
	 * Returns a set, which contains the listeners, which should be notified
	 * when the adapter's underlying data has been modified.
	 * 
	 * @return A set, which contains the listeners, which should be notified
	 *         when the adapter's underlying data has been modified, as an
	 *         instance of the type {@link Set}. The set may not be null
	 */
	protected final Set<ListAdapterListener<DataType>> getAdapterListeners() {
		return adapterListeners;
	}

	/**
	 * Returns a set, which contains the listeners, which should be notified
	 * when the adapter's underlying data has been sorted.
	 * 
	 * @return A set, which contains the listeners, which should be notified
	 *         when the adapter's underlying data has been modified, as an
	 *         instance of the type {@link Set}. The set may not be null
	 */
	protected final Set<ListSortingListener<DataType>> getSortingListeners() {
		return sortingListeners;
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary items.
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
	 * @param sortingListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been sorted or an empty
	 *            set, if no listeners should be notified
	 */
	protected AbstractListAdapter(final Context context, final int itemViewId,
			final View itemView, final List<Item<DataType>> items,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListSortingListener<DataType>> sortingListeners) {
		ensureNotNull(context, "The context may not be null");
		ensureNotNull(items, "The items may not be null");
		ensureNotNull(adapterListeners, "The adapter listeners may not be null");
		ensureNotNull(sortingListeners, "The sorting listeners may not be null");

		this.adapterListeners = adapterListeners;
		this.sortingListeners = sortingListeners;
		this.items = items;
		this.context = context;
		this.itemViewId = itemViewId;
		this.itemView = itemView;
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
		return items.get(index).isEnabled();
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
		return items.get(indexOf(item)).isEnabled();
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
			if (items.get(i).isEnabled()) {
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
		for (Item<DataType> item : items) {
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
			if (items.get(i).isEnabled()) {
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
			Item<DataType> item = items.get(i);

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
			if (!items.get(i).isEnabled()) {
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
		for (Item<DataType> item : items) {
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
			if (!items.get(i).isEnabled()) {
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
			Item<DataType> item = items.get(i);

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
			if (items.get(i).isEnabled()) {
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

		for (Item<DataType> item : items) {
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
			if (!items.get(i).isEnabled()) {
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

		for (Item<DataType> item : items) {
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
		items.get(index).setEnabled(true);
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
		items.get(index).setEnabled(false);
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
		Item<DataType> item = items.get(index);
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
		for (Item<DataType> item : items) {
			item.setEnabled(true);
		}
	}

	/**
	 * Disables all items.
	 */
	public final void disableAll() {
		for (Item<DataType> item : items) {
			item.setEnabled(false);
		}
	}

	/**
	 * Triggers the enable states of all items. That means, that the items will
	 * be disabled, if they are enabled and vice versa.
	 */
	public final void triggerAllEnableStates() {
		for (Item<DataType> item : items) {
			item.setEnabled(!item.isEnabled());
		}
	}

	/**
	 * Sorts the items of the adapter in an ascending order.
	 */
	public final void sort() {
		sort(Order.ASCENDING);

	}

	/**
	 * Sorts the items of the adapter in a specific order.
	 * 
	 * @param order
	 *            The order, which should be used to sort the items, as a value
	 *            of the enum {@link Order}. The order may either be
	 *            <code>ASCENDING</code> order <code>DESCENDING</code>
	 */
	public final void sort(final Order order) {
		if (order == Order.ASCENDING) {
			Collections.sort(items);
		} else {
			Collections.sort(items, Collections.reverseOrder());
		}

		notifyOnSorted(getItems(), order);
		notifyDataSetChanged();
	}

	/**
	 * Sorts the items of the adapter by using a comparator in an ascending
	 * order.
	 * 
	 * @param comparator
	 *            The comparable, which should be used to sort the items, as an
	 *            instance of the type {@link Comparator}. The comparator may
	 *            not be null
	 */
	public final void sort(final Comparator<DataType> comparator) {
		sort(Order.ASCENDING, comparator);
	}

	/**
	 * Sorts the entries of the adapter by using a comparator in a specific
	 * order.
	 * 
	 * @param comparator
	 *            The comparable, which should be used to sort the entries, as
	 *            an instance of the type {@link Comparator}. The comparator may
	 *            not be null
	 * @param order
	 *            The order, which should be used to sort the entries, as a
	 *            value of the enum {@link Order}. The order may either be
	 *            <code>ASCENDING</code> order <code>DESCENDING</code>
	 */
	public final void sort(final Order order,
			final Comparator<DataType> comparator) {
		Comparator<Item<DataType>> itemComparator = new ItemComparator<DataType>(
				comparator);

		if (order == Order.ASCENDING) {
			Collections.sort(items, itemComparator);
		} else {
			Collections.sort(items, Collections.reverseOrder(itemComparator));
		}

		notifyOnSorted(getItems(), order);
		notifyDataSetChanged();
	}

	/**
	 * Adds a new listener, which should be notified when the adapter's
	 * underlying data has been modified.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            class {@link ListAdapterListener}. The listener may not be
	 *            null
	 */
	public final void addAdapterListener(
			final ListAdapterListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		adapterListeners.add(listener);
	}

	/**
	 * Removes a specific listener, which should not be notified when the
	 * adapter's underlying data has been modified, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            class {@link ListAdapterListener}. The listener may not be
	 *            null
	 */
	public final void removeAdapterListener(
			final ListAdapterListener<DataType> listener) {
		adapterListeners.remove(listener);
	}

	/**
	 * Adds a new listener, which should be notified when the adapter's
	 * underlying data has been sorted.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            class {@link ListSortingListener}. The listener may not be
	 *            null
	 */
	public final void addSortingListner(
			final ListSortingListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		sortingListeners.add(listener);
	}

	/**
	 * Removes a specific listener, which should not be notified when the
	 * adapter's underlying data has been modified, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            class {@link ListSortingListener}. The listener may not be
	 *            null
	 */
	public final void removeSortingListener(
			final ListSortingListener<DataType> listener) {
		sortingListeners.remove(listener);
	}

	/**
	 * Returns a list, which contains the adapter's items.
	 * 
	 * @return A list, which contains the adapter's items, as an instance of the
	 *         type {@link List} or an empty list, if the adapter does not
	 *         contain any items
	 */
	public final List<DataType> getItems() {
		List<DataType> result = new ArrayList<DataType>();

		for (Item<DataType> item : items) {
			result.add(item.getData());
		}

		return result;
	}

	@Override
	public final Context getContext() {
		return context;
	}

	@Override
	public final boolean add(final DataType item) {
		boolean modified = items.add(new Item<DataType>(item));
		notifyOnItemAdded(item, items.size() - 1);
		notifyDataSetChanged();
		return modified;
	}

	@Override
	public final void add(final int index, final DataType item) {
		items.add(index, new Item<DataType>(item));
		notifyOnItemAdded(item, index);
		notifyDataSetChanged();
	}

	@Override
	public final boolean addAll(final Collection<? extends DataType> items) {
		boolean modified = false;

		for (DataType item : items) {
			modified |= add(item);
		}

		return modified;
	}

	@Override
	public final boolean addAll(final int index,
			final Collection<? extends DataType> items) {
		int currentIndex = index;

		for (DataType item : items) {
			add(currentIndex, item);
			currentIndex++;
		}

		return true;
	}

	@Override
	public final DataType set(final int index, final DataType item) {
		DataType replacedItem = items.set(index, new Item<DataType>(item))
				.getData();
		notifyOnItemRemoved(replacedItem, index);
		notifyOnItemAdded(item, index);
		notifyDataSetChanged();
		return replacedItem;
	}

	@Override
	public final DataType remove(final int index) {
		DataType removedItem = items.remove(index).getData();
		notifyOnItemRemoved(removedItem, index);
		notifyDataSetChanged();
		return removedItem;
	}

	@SuppressWarnings("unchecked")
	@Override
	public final boolean remove(final Object item) {
		int index = indexOf(item);
		items.remove(index);
		notifyOnItemRemoved((DataType) item, index);
		notifyDataSetChanged();
		return true;
	}

	@Override
	public final boolean removeAll(final Collection<?> items) {
		boolean modified = false;

		for (Object item : items) {
			modified |= remove(item);
		}
		return modified;
	}

	@Override
	public final boolean retainAll(final Collection<?> arg0) {
		boolean modified = false;

		for (Item<DataType> item : this.items) {
			if (!items.contains(item.getData())) {
				modified |= remove(item);
			}
		}
		return modified;
	}

	@Override
	public final void clear() {
		items.clear();
	}

	@Override
	public final Iterator<DataType> iterator() {
		return new ItemIterator<DataType>(items);
	}

	@Override
	public final ListIterator<DataType> listIterator() {
		return new ItemListIterator<DataType>(items);
	}

	@Override
	public final ListIterator<DataType> listIterator(final int index) {
		return new ItemListIterator<DataType>(items, index);
	}

	@Override
	public final List<DataType> subList(final int start, final int end) {
		List<DataType> subList = new ArrayList<DataType>();

		for (int i = start; i < end; i++) {
			subList.add(items.get(i).getData());
		}

		return subList;
	}

	@Override
	public final Object[] toArray() {
		Object[] array = new Object[items.size()];

		for (int i = 0; i < items.size(); i++) {
			array[i] = items.get(i).getData();
		}

		return array;
	}

	@SuppressWarnings("unchecked")
	@Override
	public final <T> T[] toArray(final T[] array) {
		T[] result;

		if (array.length >= items.size()) {
			result = array;
		} else {
			result = (T[]) Array.newInstance(array.getClass()
					.getComponentType(), items.size());
		}

		for (int i = 0; i < result.length; i++) {
			if (i < items.size() - 1) {
				result[i] = (T) items.get(i).getData();
			} else {
				result[i] = null;
			}
		}

		return result;
	}

	@Override
	public final DataType get(final int index) {
		return items.get(index).getData();
	}

	@Override
	public final int indexOf(final Object item) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getData() == item) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final int lastIndexOf(final Object item) {
		for (int i = items.size() - 1; i >= 0; i--) {
			if (items.get(i).getData() == item) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final boolean contains(final Object item) {
		return indexOf(item) != -1;
	}

	@Override
	public final boolean containsAll(final Collection<?> items) {
		for (Object item : items) {
			if (!contains(item)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public final boolean isEmpty() {
		return items.isEmpty();
	}

	@Override
	public final int size() {
		return items.size();
	}

	@Override
	public final int getCount() {
		return items.size();
	}

	@Override
	public final DataType getItem(final int index) {
		return items.get(index).getData();
	}

	@Override
	public final long getItemId(final int index) {
		return index;
	}

	@Override
	public final void onSaveInstanceState(final Bundle outState) {
		SerializableWrapper<List<Item<DataType>>> wrappedItems = new SerializableWrapper<List<Item<DataType>>>(
				items);
		outState.putSerializable(ITEMS_BUNDLE_KEY, wrappedItems);

		SerializableWrapper<Set<ListAdapterListener<DataType>>> wrappedAdapterListeners = new SerializableWrapper<Set<ListAdapterListener<DataType>>>(
				adapterListeners);
		outState.putSerializable(ADAPTER_LISTENERS_BUNDLE_KEY,
				wrappedAdapterListeners);

		SerializableWrapper<Set<ListSortingListener<DataType>>> wrappedSortingListeners = new SerializableWrapper<Set<ListSortingListener<DataType>>>(
				sortingListeners);
		outState.putSerializable(SORTING_LISTENERS_BUNDLE_KEY,
				wrappedSortingListeners);
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void onRestoreInstanceState(final Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			SerializableWrapper<List<Item<DataType>>> wrappedItems = (SerializableWrapper<List<Item<DataType>>>) savedInstanceState
					.getSerializable(ITEMS_BUNDLE_KEY);
			items = wrappedItems.getWrappedInstance();

			SerializableWrapper<Set<ListAdapterListener<DataType>>> wrappedAdapterListeners = (SerializableWrapper<Set<ListAdapterListener<DataType>>>) savedInstanceState
					.getSerializable(ADAPTER_LISTENERS_BUNDLE_KEY);
			adapterListeners = wrappedAdapterListeners.getWrappedInstance();

			SerializableWrapper<Set<ListSortingListener<DataType>>> wrappedSortingListeners = (SerializableWrapper<Set<ListSortingListener<DataType>>>) savedInstanceState
					.getSerializable(SORTING_LISTENERS_BUNDLE_KEY);
			sortingListeners = wrappedSortingListeners.getWrappedInstance();

			notifyDataSetChanged();
		}
	}

	@Override
	public abstract AbstractListAdapter<DataType> clone()
			throws CloneNotSupportedException;

}