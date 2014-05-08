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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.list.selection.ListSelection;
import de.mrapp.android.adapter.list.selection.ListSelectionListener;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a list of arbitrary items. Such adapters are meant to provide the underlying
 * data for visualization using a {@link ListView} widget.
 * 
 * @param <ItemType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractListAdapter<ItemType> extends BaseAdapter
		implements ListAdapter<ItemType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The context, the adapter belongs to.
	 */
	private final transient Context context;

	/**
	 * The selection, which is used to manage the selection states of the
	 * adapter's items.
	 */
	private final ListSelection<ItemType> selection;

	/**
	 * The id of the view, which is used to visualize each item of the adapter.
	 */
	private final int viewId;

	/**
	 * A list, which contains the the adapter's items.
	 */
	private List<ItemType> items;

	/**
	 * A set, which contains the listeners, which should be notified when the
	 * adapter's underlying data has been modified.
	 */
	private Set<ListAdapterListener<ItemType>> adapterListeners;

	/**
	 * Notifies all listeners, which have been registered to be notified when
	 * the adapter's underlying data has been modified, about an item, which has
	 * been added to the adapter.
	 * 
	 * @param item
	 *            The item, which has been added to the adapter, as an instance
	 *            of the generic type ItemType. The item may not be null
	 * @param index
	 *            The index of the item, which has been added to the adapter, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>size():int</code> - 1
	 */
	private void notifyOnItemAdded(final ItemType item, final int index) {
		for (ListAdapterListener<ItemType> listener : adapterListeners) {
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
	 *            instance of the generic type ItemType. The item may not be
	 *            null
	 * @param index
	 *            The index of the item, which has been removed from the
	 *            adapter, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method <code>size():int</code>
	 *            - 2.
	 */
	private void notifyOnItemRemoved(final ItemType item, final int index) {
		for (ListAdapterListener<ItemType> listener : adapterListeners) {
			listener.onItemRemoved(item, index);
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
				triggerSelection(index);
			}

		};
	}

	/**
	 * The method which is invoked, when an item should be visualized,
	 * respectively when its visualization should be refreshed. This method is
	 * meant to be used to customize the appearance of the widgets, which belong
	 * to the view, which is used to visualize the item.
	 * 
	 * @param view
	 *            The view, which is used to visualize the item, as an instance
	 *            of the class {@link View}. The view may not be null
	 * @param item
	 *            The item, which is should be visualized, as an instance of the
	 *            generic type ItemType. The item may not be null
	 * @param selected
	 *            True, if the item, which should be visualized, is currently
	 *            selected, false otherwise
	 */
	protected abstract void onCreateItem(View view, ItemType item,
			boolean selected);

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
	 * @param selection
	 *            The selection, which should be used to manage the selection
	 *            states of the adapter's items, as an instance of the type
	 *            {@link ListSelection}. The selection may not be null
	 */
	public AbstractListAdapter(final Context context, final int viewId,
			final ListSelection<ItemType> selection) {
		ensureNotNull(context, "The context may not be null");
		ensureNotNull(selection, "The selection may not be null");

		// TODO: To keep or not to keep!?
		context.getResources().getResourceName(viewId);

		this.adapterListeners = new LinkedHashSet<ListAdapterListener<ItemType>>();
		this.items = new ArrayList<ItemType>();
		this.context = context;
		this.viewId = viewId;
		this.selection = selection;
		addAdapterListener(selection);
	}

	@Override
	public final Context getContext() {
		return context;
	}

	@Override
	public final void addAdapterListener(
			final ListAdapterListener<ItemType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		adapterListeners.add(listener);
	}

	@Override
	public final void removeAdapterListener(
			final ListAdapterListener<ItemType> listener) {
		adapterListeners.remove(listener);
	}

	@Override
	public final void addSelectionListener(
			final ListSelectionListener<ItemType> listener) {
		selection.addSelectionListener(listener);
	}

	@Override
	public final void removeSelectionListener(
			final ListSelectionListener<ItemType> listener) {
		selection.removeSelectionListener(listener);
	}

	@Override
	public final boolean add(final ItemType item) {
		boolean modified = items.add(item);
		notifyOnItemAdded(item, items.size() - 1);
		notifyDataSetChanged();
		return modified;
	}

	@Override
	public final void add(final int index, final ItemType item) {
		items.add(index, item);
		notifyOnItemAdded(item, index);
		notifyDataSetChanged();
	}

	@Override
	public final boolean addAll(final Collection<? extends ItemType> items) {
		boolean modified = false;

		for (ItemType item : items) {
			modified |= add(item);
		}

		return modified;
	}

	@Override
	public final boolean addAll(final int index,
			final Collection<? extends ItemType> items) {
		int currentIndex = index;

		for (ItemType item : items) {
			add(currentIndex, item);
			currentIndex++;
		}

		return true;
	}

	@Override
	public final ItemType set(final int index, final ItemType item) {
		ItemType replacedItem = items.set(index, item);
		notifyOnItemRemoved(replacedItem, index);
		notifyOnItemAdded(item, index);
		notifyDataSetChanged();
		return replacedItem;
	}

	@Override
	public final ItemType remove(final int index) {
		ItemType item = items.remove(index);
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
			notifyOnItemRemoved((ItemType) item, index);
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

		for (ItemType item : this.items) {
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
	public final Iterator<ItemType> iterator() {
		return items.iterator();
	}

	@Override
	public final ListIterator<ItemType> listIterator() {
		return items.listIterator();
	}

	@Override
	public final ListIterator<ItemType> listIterator(final int index) {
		return items.listIterator(index);
	}

	@Override
	public final List<ItemType> subList(final int start, final int end) {
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
	public final ItemType get(final int index) {
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
	public final ItemType getItem(final int index) {
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
		onCreateItem(view, getItem(index), selection.isSelected(index));
		return view;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + adapterListeners.hashCode();
		result = prime * result + items.hashCode();
		result = prime * result + selection.hashCode();
		result = prime * result + viewId;
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
		if (!items.equals(other.items))
			return false;
		if (!selection.equals(other.selection))
			return false;
		if (viewId != other.viewId)
			return false;
		return true;
	}

	@Override
	public abstract AbstractListAdapter<ItemType> clone();

}