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
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.ListDecorator;
import de.mrapp.android.adapter.list.selection.ListSelection;
import de.mrapp.android.adapter.list.selection.ListSelectionListener;
import de.mrapp.android.adapter.sorting.MergeSort;
import de.mrapp.android.adapter.sorting.Order;
import de.mrapp.android.adapter.sorting.SortingAlgorithm;
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
		implements ListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The key, which is used to store the adapter's selection within a bundle.
	 */
	private static final String SELECTION_BUNDLE_KEY = AbstractListAdapter.class
			.getSimpleName() + "::Selection";

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
	 * The decorator, which is used to customize the appearance of the widgets,
	 * which belong to the view, which is used to visualize the items of the
	 * adapter.
	 */
	private final transient ListDecorator<DataType> decorator;

	/**
	 * The selection, which is used to manage the selection states of the
	 * adapter's items.
	 */
	private ListSelection<DataType> selection;

	/**
	 * The id of the view, which is used to visualize each item of the adapter.
	 */
	private final int viewId;

	/**
	 * A list, which contains the the adapter's items.
	 */
	private List<DataType> items;

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
	 * True, if the selection of an item is triggered, when the item is clicked,
	 * false otherwise.
	 */
	private boolean triggerSelectionOnClick;

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
	 * Creates and returns a listener, which triggers the selection state of an
	 * item at a specific index.
	 * 
	 * @param index
	 *            The index of the item, whose selection state should be
	 *            triggered, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method <code>size():int</code>
	 *            - 1
	 * @return The listener, which has been created, as an instance of the type
	 *         {@link OnClickListener}
	 */
	private OnClickListener getItemOnClickListener(final int index) {
		return new OnClickListener() {

			@Override
			public void onClick(final View view) {
				if (isSelectionTriggeredOnClick()) {
					triggerSelection(index);
				}
			}

		};
	}

	/**
	 * Returns the id of the view, which is used to visualize each item of the
	 * adapter.
	 * 
	 * @return The id of the view, which should be used to visualize each item
	 *         of the adapter, as an {@link Integer} value. The id must specify
	 *         a valid view from within the \res folder
	 */
	protected final int getViewId() {
		return viewId;
	}

	/**
	 * Returns the decorator, which is used to customize the appearance of the
	 * widgets, which belong to the view, which is used to visualize the items
	 * of the adapter.
	 * 
	 * @return The decorator, which is used to customize the appearance of the
	 *         widgets, which belong to the view, which is used to visualize the
	 *         items of the adapter, as an instance of the type
	 *         {@link ListDecorator}. The decorator may not be null
	 */
	protected final ListDecorator<DataType> getDecorator() {
		return decorator;
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
	 * Creates and returns a deep copy of the list, which contains the adapter's
	 * items.
	 * 
	 * @return A deep copy of the list, which contains the adapter's items, as
	 *         an instance of the type {@link List}. The list may not be null
	 * @throws CloneNotSupportedException
	 *             The exception, which is thrown, if cloning is not supported
	 */
	@SuppressWarnings("unchecked")
	protected final List<DataType> cloneItems()
			throws CloneNotSupportedException {
		List<DataType> clonedItems = new ArrayList<DataType>();

		try {
			for (DataType item : items) {
				DataType clonedItem = (DataType) item.getClass()
						.getMethod("clone").invoke(item);
				clonedItems.add(clonedItem);
			}
		} catch (Exception e) {
			throw new CloneNotSupportedException();
		}

		return clonedItems;
	}

	/**
	 * Creates and returns a deep copy of the adapter's selection.
	 * 
	 * @return A deep copy of the adapter's selection, as an instance of the
	 *         type {@link List}. The list may not be null
	 * @throws CloneNotSupportedException
	 *             The exception, which is thrown, if cloning is not supported
	 */
	protected final ListSelection<DataType> cloneSelection()
			throws CloneNotSupportedException {
		return selection.clone();
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary items.
	 * 
	 * @param context
	 *            The context, the adapter should belong to, as an instance of
	 *            the class {@link Context}. The context may not be null
	 * @param viewId
	 *            The id of the view, which should be used to visualize each
	 *            item of the adapter, as an {@link Integer} value. The id must
	 *            specify a valid view from within the \res folder
	 * @param decorator
	 *            The decorator, which should be used to customize the
	 *            appearance of the widgets, which belong to the view, which is
	 *            used to visualize the items of the adapter, as an instance of
	 *            the type {@link ListDecorator}. The decorator may not be null
	 * @param selection
	 *            The selection, which should be used to manage the selection
	 *            states of the adapter's items, as an instance of the type
	 *            {@link ListSelection}. The selection may not be null
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
	 * @param triggerSelectionOnClick
	 *            True, if the selection of an item should be triggered, when
	 *            the item is clicked, false otherwise
	 */
	protected AbstractListAdapter(final Context context, final int viewId,
			final ListDecorator<DataType> decorator,
			final ListSelection<DataType> selection,
			final List<DataType> items,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListSortingListener<DataType>> sortingListeners,
			final boolean triggerSelectionOnClick) {
		ensureNotNull(context, "The context may not be null");
		ensureNotNull(selection, "The selection may not be null");
		ensureNotNull(decorator, "The decorator may not ben null");
		ensureNotNull(items, "The items may not be null");
		ensureNotNull(adapterListeners, "The adapter listeners may not be null");
		ensureNotNull(sortingListeners, "The sorting listeners may not be null");

		// TODO: To keep or not to keep!?
		context.getResources().getResourceName(viewId);

		this.adapterListeners = adapterListeners;
		this.sortingListeners = sortingListeners;
		this.items = items;
		this.context = context;
		this.viewId = viewId;
		this.decorator = decorator;
		this.selection = selection;
		addAdapterListener(selection);
		addSortingListner(selection);
		this.triggerSelectionOnClick = triggerSelectionOnClick;
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary items.
	 * 
	 * @param context
	 *            The context, the adapter should belong to, as an instance of
	 *            the class {@link Context}. The context may not be null
	 * @param viewId
	 *            The id of the view, which should be used to visualize each
	 *            item of the adapter, as an {@link Integer} value. The id must
	 *            specify a valid view from within the \res folder
	 * @param decorator
	 *            The decorator, which should be used to customize the
	 *            appearance of the widgets, which belong to the view, which is
	 *            used to visualize the items of the adapter, as an instance of
	 *            the type {@link ListDecorator}. The decorator may not be null
	 * @param selection
	 *            The selection, which should be used to manage the selection
	 *            states of the adapter's items, as an instance of the type
	 *            {@link ListSelection}. The selection may not be null
	 */
	public AbstractListAdapter(final Context context, final int viewId,
			final ListDecorator<DataType> decorator,
			final ListSelection<DataType> selection) {
		this(context, viewId, decorator, selection, new ArrayList<DataType>(),
				new LinkedHashSet<ListAdapterListener<DataType>>(),
				new LinkedHashSet<ListSortingListener<DataType>>(), true);
	}

	@Override
	public final Context getContext() {
		return context;
	}

	@Override
	public final void sort() {
		sort(Order.ASCENDING);
	}

	@Override
	public final void sort(final Order order) {
		SortingAlgorithm sortingAlgorithm = new MergeSort();
		Pair<List<DataType>, List<Boolean>> result = sortingAlgorithm.sort(
				items, selection.getSelections(), order);
		items = result.first;
		notifyOnSorted(result.first, result.second, order);
		notifyDataSetChanged();
	}

	@Override
	public final void sort(final Comparator<DataType> comparator) {
		sort(Order.ASCENDING, comparator);
	}

	@Override
	public final void sort(final Order order,
			final Comparator<DataType> comparator) {
		SortingAlgorithm sortingAlgorithm = new MergeSort();
		Pair<List<DataType>, List<Boolean>> result = sortingAlgorithm.sort(
				items, selection.getSelections(), order, comparator);
		items = result.first;
		notifyOnSorted(result.first, result.second, order);
		notifyDataSetChanged();
	}

	@Override
	public final void triggerSelectionOnClick(final boolean enable) {
		this.triggerSelectionOnClick = enable;
	}

	@Override
	public final boolean isSelectionTriggeredOnClick() {
		return triggerSelectionOnClick;
	}

	@Override
	public final int getSelectedIndex() {
		return selection.getSelectedIndex();
	}

	@Override
	public final List<Integer> getSelectedIndices() {
		return selection.getSelectedIndices();
	}

	@Override
	public final List<Integer> getUnselectedIndices() {
		return selection.getUnselectedIndices();
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
	public final void addSortingListner(
			final ListSortingListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		sortingListeners.add(listener);
	}

	@Override
	public final void removeSortingListener(
			final ListSortingListener<DataType> listener) {
		sortingListeners.remove(listener);
	}

	@Override
	public final void addSelectionListener(
			final ListSelectionListener<DataType> listener) {
		selection.addSelectionListener(listener);
	}

	@Override
	public final void removeSelectionListener(
			final ListSelectionListener<DataType> listener) {
		selection.removeSelectionListener(listener);
	}

	@Override
	public final boolean add(final DataType item) {
		boolean modified = items.add(item);
		notifyOnItemAdded(item, items.size() - 1);
		notifyDataSetChanged();
		return modified;
	}

	@Override
	public final void add(final int index, final DataType item) {
		items.add(index, item);
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
		DataType replacedItem = items.set(index, item);
		notifyOnItemRemoved(replacedItem, index);
		notifyOnItemAdded(item, index);
		notifyDataSetChanged();
		return replacedItem;
	}

	@Override
	public final DataType remove(final int index) {
		DataType item = items.remove(index);
		notifyOnItemRemoved(item, index);
		notifyDataSetChanged();
		return item;
	}

	@SuppressWarnings("unchecked")
	@Override
	public final boolean remove(final Object item) {
		int index = indexOf(item);
		boolean modified = items.remove(item);

		try {
			notifyOnItemRemoved((DataType) item, index);
			notifyDataSetChanged();
		} catch (ClassCastException e) {
			return modified;
		}

		return modified;
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
	public final boolean retainAll(final Collection<?> items) {
		boolean modified = false;

		for (DataType item : this.items) {
			if (!items.contains(item)) {
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
		return items.iterator();
	}

	@Override
	public final ListIterator<DataType> listIterator() {
		return items.listIterator();
	}

	@Override
	public final ListIterator<DataType> listIterator(final int index) {
		return items.listIterator(index);
	}

	@Override
	public final List<DataType> subList(final int start, final int end) {
		return items.subList(start, end);
	}

	@Override
	public final Object[] toArray() {
		return items.toArray();
	}

	@Override
	public final <T> T[] toArray(final T[] array) {
		return items.toArray(array);
	}

	@Override
	public final DataType get(final int index) {
		return items.get(index);
	}

	@Override
	public final int indexOf(final Object item) {
		return items.indexOf(item);
	}

	@Override
	public final int lastIndexOf(final Object item) {
		return items.lastIndexOf(item);
	}

	@Override
	public final boolean contains(final Object item) {
		return items.contains(item);
	}

	@Override
	public final boolean containsAll(final Collection<?> items) {
		return items.containsAll(items);
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
	public final void triggerSelection(final int index) {
		selection.triggerSelection(index);
		notifyDataSetInvalidated();
	}

	@Override
	public final boolean isSelected(final int index) {
		return selection.isSelected(index);
	}

	@Override
	public final int getCount() {
		return items.size();
	}

	@Override
	public final DataType getItem(final int index) {
		return items.get(index);
	}

	@Override
	public final long getItemId(final int index) {
		return index;
	}

	@Override
	public final View getView(final int index, final View convertView,
			final ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(viewId, parent, false);
		view.setOnClickListener(getItemOnClickListener(index));
		decorator.onCreateItem(context, view, getItem(index),
				selection.isSelected(index));
		return view;
	}

	@Override
	public final void onSaveInstanceState(final Bundle outState) {
		outState.putSerializable(SELECTION_BUNDLE_KEY, selection);

		SerializableWrapper<List<DataType>> wrappedItems = new SerializableWrapper<List<DataType>>(
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
			SerializableWrapper<List<DataType>> wrappedItems = (SerializableWrapper<List<DataType>>) savedInstanceState
					.getSerializable(ITEMS_BUNDLE_KEY);
			items = wrappedItems.getWrappedInstance();

			selection = (ListSelection<DataType>) savedInstanceState
					.getSerializable(SELECTION_BUNDLE_KEY);

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
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + adapterListeners.hashCode();
		result = prime * result + sortingListeners.hashCode();
		result = prime * result + items.hashCode();
		result = prime * result + selection.hashCode();
		result = prime * result + viewId;
		result = prime * result + (triggerSelectionOnClick ? 1231 : 1237);
		return result;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractListAdapter<?> other = (AbstractListAdapter<?>) obj;
		if (!adapterListeners.equals(other.adapterListeners))
			return false;
		if (!sortingListeners.equals(other.sortingListeners))
			return false;
		if (!items.equals(other.items))
			return false;
		if (!selection.equals(other.selection))
			return false;
		if (viewId != other.viewId)
			return false;
		if (triggerSelectionOnClick != other.triggerSelectionOnClick)
			return false;
		return true;
	}

	@Override
	public abstract AbstractListAdapter<DataType> clone()
			throws CloneNotSupportedException;

}