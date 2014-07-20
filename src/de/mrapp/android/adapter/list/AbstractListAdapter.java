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
import static de.mrapp.android.adapter.util.Condition.ensureAtLeast;
import static de.mrapp.android.adapter.util.Condition.ensureAtMaximum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.widget.BaseAdapter;
import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.datastructure.SerializableWrapper;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.datastructure.item.ItemIterator;
import de.mrapp.android.adapter.datastructure.item.ItemListIterator;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.util.VisibleForTesting;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a list of arbitrary items. Such an adapter's purpose is to provide the
 * underlying data for visualization using a {@link ListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractListAdapter<DataType> extends BaseAdapter
		implements ListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The key, which is used to store the adapter's underlying data within a
	 * bundle.
	 */
	@VisibleForTesting
	protected static final String ITEMS_BUNDLE_KEY = AbstractListAdapter.class
			.getSimpleName() + "::Items";

	/**
	 * The key, which is used to store the listeners, which should be notified
	 * when the adapter's underlying data has been modified, within a bundle.
	 */
	@VisibleForTesting
	protected static final String ADAPTER_LISTENERS_BUNDLE_KEY = AbstractListAdapter.class
			.getSimpleName() + "::AdapterListeners";

	/**
	 * The context, the adapter belongs to.
	 */
	private final transient Context context;

	/**
	 * The inflater, which is used to inflate the views, which are used to
	 * visualize the adapter's items.
	 */
	private final transient Inflater inflater;

	/**
	 * True, if duplicate items are allowed, false otherwise.
	 */
	private boolean allowDuplicates;

	/**
	 * A list, which contains the the adapter's underlying data.
	 */
	private List<Item<DataType>> items;

	/**
	 * A set, which contains the listeners, which should be notified, when the
	 * adapter's underlying data has been modified.
	 */
	private Set<ListAdapterListener<DataType>> adapterListeners;

	/**
	 * Notifies all listeners, which have been registered to be notified, when
	 * the adapter's underlying data has been modified, about an item, which has
	 * been added to the adapter.
	 * 
	 * @param item
	 *            The item, which has been added to the adapter, as an instance
	 *            of the generic type DataType. The item may not be null
	 * @param index
	 *            The index of the item, which has been added to the adapter, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1
	 */
	private void notifyOnItemAdded(final DataType item, final int index) {
		for (ListAdapterListener<DataType> listener : adapterListeners) {
			listener.onItemAdded(item, index);
		}
	}

	/**
	 * Notifies all listeners, which have been registered to be notified, when
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
	 *            between 0 and the value of the method
	 *            <code>getNumberOfItems():int</code>
	 */
	private void notifyOnItemRemoved(final DataType item, final int index) {
		for (ListAdapterListener<DataType> listener : adapterListeners) {
			listener.onItemRemoved(item, index);
		}
	}

	/**
	 * Returns, the context, the adapter belongs to.
	 * 
	 * @return The context, the adapter belongs to, as an instance of the class
	 *         {@link Context}. The context may not be null
	 */
	protected final Context getContext() {
		return context;
	}

	/**
	 * Returns the inflater, which is used to inflate the views, which are used
	 * to visualize the adapter's items.
	 * 
	 * @return The inflater, which is used to inflate views, which are used to
	 *         visualize the adapter's items, as an instance of the type
	 *         {@link Inflater}. The inflater may not be null
	 */
	protected final Inflater getInflater() {
		return inflater;
	}

	/**
	 * Returns a list, which contains the adapter's underlying data.
	 * 
	 * @return A list, which contains the adapters underlying data, as an
	 *         instance of the type {@link List} or an empty list, if the
	 *         adapter does not contain any data
	 */
	protected final List<Item<DataType>> getItems() {
		return items;
	}

	/**
	 * Creates and returns a deep copy of the list, which contains the adapter's
	 * underlying data.
	 * 
	 * @return A deep copy of the list, which contains the adapter's underlying
	 *         data, as an instance of the type {@link List}. The list may not
	 *         be null
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
	 * Returns a set, which contains the listeners, which should be notified,
	 * when the adapter's underlying data has been modified.
	 * 
	 * @return A set, which contains the listeners, which should be notified,
	 *         when the adapter's underlying data has been modified, as an
	 *         instance of the type {@link Set} or an empty set, if no listeners
	 *         should be notified
	 */
	protected final Set<ListAdapterListener<DataType>> getAdapterListeners() {
		return adapterListeners;
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary items.
	 * 
	 * @param context
	 *            The context, the adapter belongs to, as an instance of the
	 *            class {@link Context}. The context may not be null
	 * @param inflater
	 *            The inflater, which should be used to inflate the views, which
	 *            are used to visualize the adapter's items, as an instance of
	 *            the type {@link Inflater}. The inflater may not be null
	 * @param items
	 *            A list, which contains the the adapter's underlying data, as
	 *            an instance of the type {@link List} or an empty list, if the
	 *            adapter should not contain any data
	 * @param allowDuplicates
	 *            True, if duplicate items should be allowed, false otherwise
	 * @param adapterListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been modified, as an
	 *            instance of the type {@link Set} or an empty set, if no
	 *            listeners should be notified
	 */
	protected AbstractListAdapter(final Context context,
			final Inflater inflater, final List<Item<DataType>> items,
			final boolean allowDuplicates,
			final Set<ListAdapterListener<DataType>> adapterListeners) {
		ensureNotNull(context, "The context may not be null");
		ensureNotNull(inflater, "The inflater may not be null");
		ensureNotNull(items, "The items may not be null");
		ensureNotNull(adapterListeners, "The adapter listeners may not be null");
		this.context = context;
		this.inflater = inflater;
		this.items = items;
		this.allowDuplicates = allowDuplicates;
		this.adapterListeners = adapterListeners;
	}

	@Override
	public final boolean areDuplicatesAllowed() {
		return allowDuplicates;
	}

	@Override
	public final void allowDuplicates(final boolean allowDuplicates) {
		this.allowDuplicates = allowDuplicates;
	}

	@Override
	public final void addAdapterListener(
			final ListAdapterListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		adapterListeners.add(listener);
	}

	@Override
	public final void removeAdapterListener(
			final ListAdapterListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		adapterListeners.remove(listener);
	}

	@Override
	public final boolean addItem(final DataType item) {
		return addItem(getNumberOfItems(), item);
	}

	@Override
	public final boolean addItem(final int index, final DataType item) {
		ensureNotNull(item, "The item may not be null");

		if (!areDuplicatesAllowed() && containsItem(item)) {
			return false;
		}

		items.add(index, new Item<DataType>(item));
		notifyOnItemAdded(item, index);
		notifyDataSetChanged();
		return true;
	}

	@Override
	public final boolean addAllItems(final Collection<DataType> items) {
		ensureNotNull(items, "The collection may not be null");
		return addAllItems(getNumberOfItems(), items);
	}

	@Override
	public final boolean addAllItems(final int index,
			final Collection<DataType> items) {
		ensureNotNull(items, "The collection may not be null");
		boolean result = true;
		int currentIndex = index;

		for (DataType item : items) {
			result &= addItem(currentIndex, item);
			currentIndex++;
		}

		return result;
	}

	@Override
	public final boolean addAllItems(final DataType... items) {
		ensureNotNull(items, "The collection may not be null");
		return addAllItems(getNumberOfItems(), items);
	}

	@Override
	public final boolean addAllItems(final int index, final DataType... items) {
		ensureNotNull(items, "The array may not be null");
		return addAllItems(index, Arrays.asList(items));
	}

	@Override
	public final DataType replaceItem(final int index, final DataType item) {
		ensureNotNull(item, "The item may not be null");
		DataType replacedItem = items.set(index, new Item<DataType>(item))
				.getData();
		notifyOnItemRemoved(replacedItem, index);
		notifyOnItemAdded(item, index);
		notifyDataSetChanged();
		return replacedItem;
	}

	@Override
	public final DataType removeItem(final int index) {
		DataType removedItem = items.remove(index).getData();
		notifyOnItemRemoved(removedItem, index);
		notifyDataSetChanged();
		return removedItem;
	}

	@Override
	public final boolean removeItem(final DataType item) {
		ensureNotNull(item, "The item may not be null");
		int index = indexOf(item);

		if (index != -1) {
			items.remove(index);
			notifyOnItemRemoved(item, index);
			notifyDataSetChanged();
			return true;
		}

		return false;
	}

	@Override
	public final boolean removeAllItems(final Collection<DataType> items) {
		ensureNotNull(items, "The collection may not be null");
		int numberOfRemovedItems = 0;

		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			if (items.contains(getItem(i))) {
				removeItem(i);
				numberOfRemovedItems++;
			}
		}

		return numberOfRemovedItems == items.size();
	}

	@Override
	public final boolean removeAllItems(final DataType... items) {
		ensureNotNull(items, "The array may not be null");
		return removeAllItems(Arrays.asList(items));
	}

	@Override
	public final void retainAllItems(final Collection<DataType> items) {
		ensureNotNull(items, "The collection may not be null");

		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			if (!items.contains(getItem(i))) {
				removeItem(i);
			}
		}
	}

	@Override
	public final void retainAllItems(final DataType... items) {
		ensureNotNull(items, "The array may not be null");
		retainAllItems(Arrays.asList(items));
	}

	@Override
	public final void clearItems() {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			removeItem(i);
		}
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
	public final Collection<DataType> subList(final int start, final int end) {
		Collection<DataType> subList = new ArrayList<DataType>();

		for (int i = start; i < end; i++) {
			subList.add(getItem(i));
		}

		return subList;
	}

	@Override
	public final Object[] toArray() {
		return getAllItems().toArray();
	}

	@Override
	public final <T> T[] toArray(final T[] array) {
		return getAllItems().toArray(array);
	}

	@Override
	public final int indexOf(final DataType item) {
		ensureNotNull(item, "The item may not be null");
		return getAllItems().indexOf(item);
	}

	@Override
	public final int lastIndexOf(final DataType item) {
		ensureNotNull(item, "The item may not be null");
		return getAllItems().lastIndexOf(item);
	}

	@Override
	public final boolean containsItem(final DataType item) {
		ensureNotNull(item, "The item may not be null");
		return getAllItems().contains(item);
	}

	@Override
	public final boolean containsAllItems(final Collection<DataType> items) {
		ensureNotNull(items, "The collection may not be null");
		return getAllItems().containsAll(items);
	}

	@Override
	public final boolean containsAllItems(final DataType... items) {
		ensureNotNull(items, "The array may not be null");
		return containsAllItems(Arrays.asList(items));
	}

	@Override
	public final int getNumberOfItems() {
		return items.size();
	}

	@Override
	public final DataType getItem(final int index) {
		return items.get(index).getData();
	}

	@Override
	public final List<DataType> getAllItems() {
		List<DataType> result = new ArrayList<DataType>();

		for (Item<DataType> item : items) {
			result.add(item.getData());
		}

		return result;
	}

	@Override
	public final boolean isEmpty() {
		return items.isEmpty();
	}

	@Override
	public final int getCount() {
		return getNumberOfItems();
	}

	@Override
	public final long getItemId(final int index) {
		ensureAtLeast(index, 0, "The index must be at least 0");
		ensureAtMaximum(index, items.size() - 1,
				isEmpty() ? "The index must be at maximum "
						+ (items.size() - 1) : "The adapter is empty");
		return index;
	}

	@Override
	public void onSaveInstanceState(final Bundle outState) {
		SerializableWrapper<List<Item<DataType>>> wrappedItems = new SerializableWrapper<List<Item<DataType>>>(
				getItems());
		outState.putSerializable(ITEMS_BUNDLE_KEY, wrappedItems);

		SerializableWrapper<Set<ListAdapterListener<DataType>>> wrappedAdapterListeners = new SerializableWrapper<Set<ListAdapterListener<DataType>>>(
				getAdapterListeners());
		outState.putSerializable(ADAPTER_LISTENERS_BUNDLE_KEY,
				wrappedAdapterListeners);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onRestoreInstanceState(final Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			SerializableWrapper<List<Item<DataType>>> wrappedItems = (SerializableWrapper<List<Item<DataType>>>) savedInstanceState
					.getSerializable(ITEMS_BUNDLE_KEY);
			items = wrappedItems.getWrappedInstance();

			SerializableWrapper<Set<ListAdapterListener<DataType>>> wrappedAdapterListeners = (SerializableWrapper<Set<ListAdapterListener<DataType>>>) savedInstanceState
					.getSerializable(ADAPTER_LISTENERS_BUNDLE_KEY);
			adapterListeners = wrappedAdapterListeners.getWrappedInstance();

			notifyDataSetChanged();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + adapterListeners.hashCode();
		result = prime * result + (allowDuplicates ? 1231 : 1237);
		result = prime * result + items.hashCode();
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractListAdapter<?> other = (AbstractListAdapter<?>) obj;
		if (!adapterListeners.equals(other.adapterListeners))
			return false;
		if (allowDuplicates != other.allowDuplicates)
			return false;
		if (!items.equals(other.items))
			return false;
		return true;
	}

	@Override
	public abstract AbstractListAdapter<DataType> clone()
			throws CloneNotSupportedException;

}