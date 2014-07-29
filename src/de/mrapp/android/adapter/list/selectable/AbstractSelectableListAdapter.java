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
package de.mrapp.android.adapter.list.selectable;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.List;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.datastructure.SerializableWrapper;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.list.sortable.AbstractSortableListAdapter;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.adapter.util.VisibleForTesting;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a list of arbitrary items, of which one or multiple items can be selected.
 * Such an adapter's purpose is to provide the underlying data for visualization
 * using a {@link ListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractSelectableListAdapter<DataType>
		extends
		AbstractSortableListAdapter<DataType, SelectableListDecorator<DataType>>
		implements SelectableListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The key, which is used to store, whether an item should be selected, when
	 * it is clicked by the user, or not, within a bundle.
	 */
	@VisibleForTesting
	protected static final String SELECT_ITEM_ON_CLICK_BUNDLE_KEY = AbstractSelectableListAdapter.class
			.getSimpleName() + "::SelectItemOnClick";

	/**
	 * The key, which is used to store the listeners, which should be notified,
	 * when an item has been selected or unselected, within a bundle.
	 */
	@VisibleForTesting
	protected static final String SELECTION_LISTENERS_BUNDLE_KEY = AbstractSelectableListAdapter.class
			.getSimpleName() + "::SelectionListeners";

	/**
	 * True, if the an item should be selected, when it is clicked by the user,
	 * false otherwise.
	 */
	private boolean selectItemOnClick;

	/**
	 * A set, which contains the listeners, which should be notified, when an
	 * item has been selected or unselected.
	 */
	private Set<ListSelectionListener<DataType>> selectionListeners;

	/**
	 * Notifies all listeners, which have been registered to be notified when
	 * the selection of an item of the adapter has been changed, about an item,
	 * which has been selected.
	 * 
	 * @param item
	 *            The item, which has been selected, as an instance of the
	 *            generic type DataType. The item may not be null
	 * @param index
	 *            The index of the item, which has been selected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>size():int</code> - 1
	 */
	protected final void notifyOnItemSelected(final DataType item,
			final int index) {
		for (ListSelectionListener<DataType> listener : selectionListeners) {
			listener.onItemSelected(item, index);
		}
	}

	/**
	 * Notifies all listeners, which have been registered to be notified when
	 * the selection of an item of the adapter has been changed, about an item,
	 * which has been unselected.
	 * 
	 * @param item
	 *            The item, which has been unselected, as an instance of the
	 *            generic type DataType. The item may not be null
	 * @param index
	 *            The index of the item, which has been unselected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>size():int</code> - 1
	 */
	protected final void notifyOnItemUnselected(final DataType item,
			final int index) {
		for (ListSelectionListener<DataType> listener : selectionListeners) {
			listener.onItemUnselected(item, index);
		}
	}

	/**
	 * Returns the set, which contains the listeners, which should be notified
	 * when the selection of an item of the adapter has been changed.
	 * 
	 * @return The set, which contains the listeners, which should be notified
	 *         when the selection of an item of the adapter has been changed, as
	 *         an instance of the type {@link Set}. The set may not be null
	 */
	protected final Set<ListSelectionListener<DataType>> getSelectionListeners() {
		return selectionListeners;
	}

	@Override
	protected final void applyDecorator(final Context context, final View view,
			final int index) {
		getDecorator().onShowItem(context, view, getItem(index), index,
				isEnabled(index), getItemState(index), isSelected(index));
	}

	@Override
	protected final void onItemClicked(final int index) {
		super.onItemClicked(index);

		if (isItemSelectedOnClick()) {
			select(index);
		}
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary items, of which one or multiple items can be selected.
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
	 *            A list, which contains the the adapter's items, or an empty
	 *            list, if the adapter should not contain any items
	 * @param allowDuplicates
	 *            True, if duplicate items should be allowed, false otherwise
	 * @param adapterListeners
	 *            A set, which contains the listeners, which should be notified
	 *            when the adapter's underlying data has been modified or an
	 *            empty set, if no listeners should be notified
	 * @param enableStateListeners
	 *            A set, which contains the listeners, which should be notified
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
	 * @param sortingListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been sorted or an empty
	 *            set, if no listeners should be notified
	 * @param selectItemOnClick
	 *            True, if an item should be selected, when it is clicked by the
	 *            user, false otherwise
	 * @param selectionListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when an item's selection has been changed or an empty set, if
	 *            no listeners should be notified
	 */
	protected AbstractSelectableListAdapter(final Context context,
			final Inflater inflater,
			final SelectableListDecorator<DataType> decorator,
			final List<Item<DataType>> items, final boolean allowDuplicates,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners,
			final int numberOfItemStates,
			final boolean triggerItemStateOnClick,
			final Set<ListItemStateListener<DataType>> itemStateListeners,
			final Set<ListSortingListener<DataType>> sortingListeners,
			final boolean selectItemOnClick,
			final Set<ListSelectionListener<DataType>> selectionListeners) {
		super(context, inflater, decorator, items, allowDuplicates,
				adapterListeners, enableStateListeners, numberOfItemStates,
				triggerItemStateOnClick, itemStateListeners, sortingListeners);
		selectItemOnClick(selectItemOnClick);
	}

	@Override
	public final void addSelectionListener(
			final ListSelectionListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		selectionListeners.add(listener);
	}

	@Override
	public final void removeSelectionListener(
			final ListSelectionListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		selectionListeners.remove(listener);
	}

	@Override
	public final boolean isSelected(final int index) {
		return getItems().get(index).isSelected();
	}

	@Override
	public final boolean isSelected(final DataType item) {
		return isSelected(indexOf(item));
	}

	@Override
	public final boolean isItemSelectedOnClick() {
		return selectItemOnClick;
	}

	@Override
	public final void selectItemOnClick(final boolean selectItemOnClick) {
		this.selectItemOnClick = selectItemOnClick;
	}

	@Override
	public final void onSaveInstanceState(final Bundle outState) {
		outState.putBoolean(SELECT_ITEM_ON_CLICK_BUNDLE_KEY,
				isItemSelectedOnClick());

		SerializableWrapper<Set<ListSelectionListener<DataType>>> wrappedSelectionListeners = new SerializableWrapper<Set<ListSelectionListener<DataType>>>(
				getSelectionListeners());
		outState.putSerializable(SELECTION_LISTENERS_BUNDLE_KEY,
				wrappedSelectionListeners);
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void onRestoreInstanceState(final Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			selectItemOnClick = savedInstanceState
					.getBoolean(SELECT_ITEM_ON_CLICK_BUNDLE_KEY);

			SerializableWrapper<Set<ListSelectionListener<DataType>>> wrappedSelectionListeners = (SerializableWrapper<Set<ListSelectionListener<DataType>>>) savedInstanceState
					.getSerializable(SELECTION_LISTENERS_BUNDLE_KEY);
			selectionListeners = wrappedSelectionListeners.getWrappedInstance();

			notifyDataSetChanged();
		}
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (selectItemOnClick ? 1231 : 1237);
		result = prime * result + selectionListeners.hashCode();
		return result;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractSelectableListAdapter<?> other = (AbstractSelectableListAdapter<?>) obj;
		if (selectItemOnClick != other.selectItemOnClick)
			return false;
		if (!selectionListeners.equals(other.selectionListeners))
			return false;
		return true;
	}

	@Override
	public abstract AbstractSelectableListAdapter<DataType> clone()
			throws CloneNotSupportedException;

}