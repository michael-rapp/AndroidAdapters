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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import de.mrapp.android.adapter.Order;
import de.mrapp.android.adapter.util.Item;
import de.mrapp.android.adapter.util.ItemComparator;
import de.mrapp.android.adapter.util.SerializableWrapper;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a sortable list of arbitrary items. Such adapters are meant to provide the
 * underlying data for visualization using a {@link ListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractSortableListAdapter<DataType> extends
		AbstractEnableStateListAdapter<DataType> {

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
	 * A set, which contains the listeners, which should be notified when the
	 * adapter's underlying data has been sorted.
	 */
	private Set<ListSortingListener<DataType>> sortingListeners;

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
	 * Returns a set, which contains the listeners, which should be notified
	 * when the adapter's underlying data has been sorted.
	 * 
	 * @return A set, which contains the listeners, which should be notified
	 *         when the adapter's underlying data has been modified, as an
	 *         instance of the type {@link Set} or an empty set, if no listeners
	 *         should be notified
	 */
	protected final Set<ListSortingListener<DataType>> getSortingListeners() {
		return sortingListeners;
	}

	/**
	 * Sets the set, which contains the listeners, which should be notified when
	 * the adapter's underlying data has been sorted.
	 * 
	 * @param sortingListeners
	 *            The set, which should be set, as an instance of the type
	 *            {@link Set} or an empty set, if no listeners should be
	 *            notified
	 */
	protected final void setSortingListeners(
			final Set<ListSortingListener<DataType>> sortingListeners) {
		ensureNotNull(sortingListeners, "The sorting listeners may not be null");
		this.sortingListeners = sortingListeners;
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a sortable
	 * list of arbitrary items.
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
	protected AbstractSortableListAdapter(final Context context,
			final int itemViewId, final View itemView,
			final List<Item<DataType>> items,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListSortingListener<DataType>> sortingListeners) {
		super(context, itemViewId, itemView, items, adapterListeners);
		setSortingListeners(sortingListeners);
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
			Collections.sort(getItems());
		} else {
			Collections.sort(getItems(), Collections.reverseOrder());
		}

		notifyOnSorted(getAllItems(), order);
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
			Collections.sort(getItems(), itemComparator);
		} else {
			Collections.sort(getItems(),
					Collections.reverseOrder(itemComparator));
		}

		notifyOnSorted(getAllItems(), order);
		notifyDataSetChanged();
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

	@Override
	public final void onSaveInstanceState(final Bundle outState) {
		SerializableWrapper<List<Item<DataType>>> wrappedItems = new SerializableWrapper<List<Item<DataType>>>(
				getItems());
		outState.putSerializable(ITEMS_BUNDLE_KEY, wrappedItems);

		SerializableWrapper<Set<ListAdapterListener<DataType>>> wrappedAdapterListeners = new SerializableWrapper<Set<ListAdapterListener<DataType>>>(
				getAdapterListeners());
		outState.putSerializable(ADAPTER_LISTENERS_BUNDLE_KEY,
				wrappedAdapterListeners);

		SerializableWrapper<Set<ListSortingListener<DataType>>> wrappedSortingListeners = new SerializableWrapper<Set<ListSortingListener<DataType>>>(
				getSortingListeners());
		outState.putSerializable(SORTING_LISTENERS_BUNDLE_KEY,
				wrappedSortingListeners);
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void onRestoreInstanceState(final Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			SerializableWrapper<List<Item<DataType>>> wrappedItems = (SerializableWrapper<List<Item<DataType>>>) savedInstanceState
					.getSerializable(ITEMS_BUNDLE_KEY);
			setItems(wrappedItems.getWrappedInstance());

			SerializableWrapper<Set<ListAdapterListener<DataType>>> wrappedAdapterListeners = (SerializableWrapper<Set<ListAdapterListener<DataType>>>) savedInstanceState
					.getSerializable(ADAPTER_LISTENERS_BUNDLE_KEY);
			setAdapterListeners(wrappedAdapterListeners.getWrappedInstance());

			SerializableWrapper<Set<ListSortingListener<DataType>>> wrappedSortingListeners = (SerializableWrapper<Set<ListSortingListener<DataType>>>) savedInstanceState
					.getSerializable(SORTING_LISTENERS_BUNDLE_KEY);
			setSortingListeners(wrappedSortingListeners.getWrappedInstance());

			notifyDataSetChanged();
		}
	}

}