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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import android.content.Context;
import android.widget.BaseAdapter;
import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.datastructure.item.ItemIterator;
import de.mrapp.android.adapter.datastructure.item.ItemListIterator;
import de.mrapp.android.adapter.inflater.Inflater;

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
		implements ListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

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
	 * Sets the list, which contains the adapter's underlying data.
	 * 
	 * @param items
	 *            The list, which should be set, as an instance of the type
	 *            {@link List} or an empty list, if the adapter should not
	 *            contain any data
	 */
	// TODO: Remove
	protected final void setItems(final List<Item<DataType>> items) {
		ensureNotNull(items, "The items may not be null");
		this.items = items;
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
	 * Sets the set, which contains the listeners, which should be notified,
	 * when the adapter's underlying data has been modified.
	 * 
	 * @param adapterListeners
	 *            The set, which should be set, as an instance of the type
	 *            {@link Set} or an empty set, if no listeners should be
	 *            notified
	 */
	// TODO: Remove
	protected final void setAdapterListeners(
			final Set<ListAdapterListener<DataType>> adapterListeners) {
		ensureNotNull(adapterListeners, "The adapter listeners may not be null");
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
		adapterListeners.remove(listener);
	}

	@Override
	public final boolean addItem(final DataType item) {
		if (!areDuplicatesAllowed() && containsItem(item)) {
			return false;
		} else {
			items.add(new Item<DataType>(item));
			notifyOnItemAdded(item, items.size() - 1);
			notifyDataSetChanged();
			return true;
		}
	}

	@Override
	public final boolean addItem(final int index, final DataType item) {
		if (!areDuplicatesAllowed() && containsItem(item)) {
			return false;
		} else {
			items.add(index, new Item<DataType>(item));
			notifyOnItemAdded(item, index);
			notifyDataSetChanged();
			return true;
		}
	}

	@Override
	public final boolean addAllItems(final Collection<DataType> items) {
		boolean result = true;

		for (DataType item : items) {
			result &= addItem(item);
		}

		return result;
	}

	@Override
	public final void addAllItems(final int index,
			final Collection<DataType> items) {
		int currentIndex = index;

		for (DataType item : items) {
			addItem(currentIndex, item);
			currentIndex++;
		}
	}

	@Override
	public final DataType replaceItem(final int index, final DataType item) {
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
		int index = indexOf(item);

		if (index != -1) {
			items.remove(index);
			notifyOnItemRemoved((DataType) item, index);
			notifyDataSetChanged();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public final boolean removeAllItems(final Collection<DataType> items) {
		boolean result = true;

		for (DataType item : items) {
			result &= removeItem(item);
		}

		return result;
	}

	@Override
	public final void retainAllItems(final Collection<DataType> items) {

		for (Item<DataType> item : this.items) {
			if (!items.contains(item.getData())) {
				removeItem(item.getData());
			}
		}
	}

	@Override
	public final void clearItems() {
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

	@Override
	public final int indexOf(final DataType item) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getData() == item) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final int lastIndexOf(final DataType item) {
		for (int i = items.size() - 1; i >= 0; i--) {
			if (items.get(i).getData() == item) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final boolean containsItem(final DataType item) {
		return indexOf(item) != -1;
	}

	@Override
	public final boolean containsAllItems(final Collection<DataType> items) {
		for (DataType item : items) {
			if (!containsItem(item)) {
				return false;
			}
		}

		return true;
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
		return index;
	}

	@Override
	public abstract AbstractListAdapter<DataType> clone()
			throws CloneNotSupportedException;

}