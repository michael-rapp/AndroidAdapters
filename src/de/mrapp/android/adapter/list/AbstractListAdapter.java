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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.Order;
import de.mrapp.android.adapter.util.Item;
import de.mrapp.android.adapter.util.ItemIterator;
import de.mrapp.android.adapter.util.ItemListIterator;
import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

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
	 * The id of the view, which is used to visualize each item of the adapter.
	 */
	private final int itemViewId;

	/**
	 * The view, which is used to visualize each item of the adapter.
	 */
	private final View itemView;

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
	 * @param sortedSelections
	 *            A list, which contains the selection states, which correspond
	 *            to the adapter's sorted items, as an instance of the type
	 *            {@link List}. The list may not be null
	 * @param order
	 *            The order, which has been used to sort the list, as a value of
	 *            the enum {@link Order}. The order may either be
	 *            <code>ASCENDING</code> or <code>DESCENDING</code>
	 */
	private void notifyOnSorted(final List<DataType> sortedList,
			final List<Boolean> sortedSelections, final Order order) {
		for (ListSortingListener<DataType> listener : sortingListeners) {
			listener.onSorted(sortedList, sortedSelections, order);
		}
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
	public final List<DataType> getItems() {
		List<DataType> result = new ArrayList<DataType>();

		for (Item<DataType> item : items) {
			result.add(item.getData());
		}

		return result;
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
	public abstract AbstractListAdapter<DataType> clone()
			throws CloneNotSupportedException;

}