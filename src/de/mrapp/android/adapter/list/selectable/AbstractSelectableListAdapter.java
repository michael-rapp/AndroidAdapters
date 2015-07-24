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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.datastructure.AppliedFilter;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.ListAdapterItemClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.filterable.AbstractFilterableListAdapter;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.adapter.logging.LogLevel;
import de.mrapp.android.adapter.util.VisibleForTesting;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a list of arbitrary items, of which one or multiple items can be selected.
 * Such an adapter's purpose is to provide the underlying data for visualization
 * using a {@link AbsListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractSelectableListAdapter<DataType>
		extends AbstractFilterableListAdapter<DataType, SelectableListDecorator<DataType>>
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
	protected static final String SELECT_ITEM_ON_CLICK_BUNDLE_KEY = AbstractSelectableListAdapter.class.getSimpleName()
			+ "::SelectItemOnClick";

	/**
	 * A set, which contains the listeners, which should be notified, when an
	 * item has been selected or unselected.
	 */
	private transient Set<ListSelectionListener<DataType>> selectionListeners;

	/**
	 * True, if the an item should be selected, when it is clicked by the user,
	 * false otherwise.
	 */
	private boolean selectItemOnClick;

	/**
	 * Creates and returns a listener, which allows to adapt the unfiltered
	 * items, when an item has been selected or unselected.
	 * 
	 * @return The listener, which has been created, as an instance of the type
	 *         {@link ListSelectionListener}
	 */
	private ListSelectionListener<DataType> createSelectionListener() {
		return new ListSelectionListener<DataType>() {

			@Override
			public void onItemSelected(final SelectableListAdapter<DataType> adapter, final DataType item,
					final int index) {
				if (isFiltered()) {
					getUnfilteredItems().get(getUnfilteredIndex(index)).setSelected(true);
				}
			}

			@Override
			public void onItemUnselected(final SelectableListAdapter<DataType> adapter, final DataType item,
					final int index) {
				if (isFiltered()) {
					getUnfilteredItems().get(getUnfilteredIndex(index)).setSelected(false);
				}
			}

		};
	}

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
	protected final void notifyOnItemSelected(final DataType item, final int index) {
		for (ListSelectionListener<DataType> listener : selectionListeners) {
			listener.onItemSelected(this, item, index);
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
	protected final void notifyOnItemUnselected(final DataType item, final int index) {
		for (ListSelectionListener<DataType> listener : selectionListeners) {
			listener.onItemUnselected(this, item, index);
		}
	}

	/**
	 * Returns a set, which contains the listeners, which should be notified,
	 * when the selection of an item of the adapter has been changed.
	 * 
	 * @return A set, which contains the listeners, which should be notified
	 *         when the selection of an item of the adapter has been changed, as
	 *         an instance of the type {@link Set} or an empty set, if no
	 *         listeners should be notified
	 */
	protected final Set<ListSelectionListener<DataType>> getSelectionListeners() {
		return selectionListeners;
	}

	/**
	 * Sets the set, which contains the listeners, which should be notified,
	 * when the selection of an item of the adapter has been changed.
	 * 
	 * @param selectionListeners
	 *            The set, which should be set, as an instance of the type
	 *            {@link Set} or an empty set, if no listeners should be
	 *            notified
	 */
	protected final void setSelectionListeners(final Set<ListSelectionListener<DataType>> selectionListeners) {
		ensureNotNull(selectionListeners, "The selection listeners may not be null");
		this.selectionListeners = selectionListeners;
	}

	@Override
	protected final void applyDecorator(final Context context, final View view, final int index) {
		DataType item = getItem(index);
		boolean enabled = isEnabled(index);
		int itemState = getItemState(index);
		boolean filtered = isFiltered();
		boolean selected = isSelected(index);
		getDecorator().applyDecorator(context, this, view, item, index, enabled, itemState, filtered, selected);
		String message = "Applied decorator \"" + getDecorator() + "\" using arguments: Item=[" + item + ", index="
				+ index + ", enabled=" + enabled + ", itemState=" + itemState + ", filtered=" + filtered + "]";
		getLogger().logVerbose(getClass(), message);
	}

	@Override
	protected void onSaveInstanceState(final Bundle savedState) {
		super.onSaveInstanceState(savedState);
		savedState.putBoolean(SELECT_ITEM_ON_CLICK_BUNDLE_KEY, isItemSelectedOnClick());
	}

	@Override
	protected void onRestoreInstanceState(final Bundle savedState) {
		super.onRestoreInstanceState(savedState);
		selectItemOnClick = savedState.getBoolean(SELECT_ITEM_ON_CLICK_BUNDLE_KEY, true);
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
	 * @param logLevel
	 *            The log level, which should be used for logging, as a value of
	 *            the enum {@link LogLevel}. The log level may not be null
	 * @param items
	 *            A list, which contains the the adapter's items, or an empty
	 *            list, if the adapter should not contain any items
	 * @param allowDuplicates
	 *            True, if duplicate items should be allowed, false otherwise
	 * @param notifyOnChange
	 *            True, if the method <code>notifyDataSetChanged():void</code>
	 *            should be automatically called when the adapter's underlying
	 *            data has been changed, false otherwise
	 * @param itemClickListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when an item of the adapter has been clicked by the user, as
	 *            an instance of the type {@link Set} or an empty set, if no
	 *            listeners should be notified
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
	 * @param filterListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been filtered or an
	 *            empty set, if no listeners should be notified
	 * @param appliedFilters
	 *            A set, which contains the filters, which should be used to
	 *            filter the adapter's underlying data or an empty set, if the
	 *            adapter's underlying data should not be filtered
	 * @param selectItemOnClick
	 *            True, if an item should be selected, when it is clicked by the
	 *            user, false otherwise
	 * @param selectionListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when an item's selection has been changed or an empty set, if
	 *            no listeners should be notified
	 */
	protected AbstractSelectableListAdapter(final Context context, final Inflater inflater,
			final SelectableListDecorator<DataType> decorator, final LogLevel logLevel,
			final ArrayList<Item<DataType>> items, final boolean allowDuplicates, final boolean notifyOnChange,
			final Set<ListAdapterItemClickListener<DataType>> itemClickListeners,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners, final int numberOfItemStates,
			final boolean triggerItemStateOnClick, final Set<ListItemStateListener<DataType>> itemStateListeners,
			final Set<ListSortingListener<DataType>> sortingListeners,
			final Set<ListFilterListener<DataType>> filterListeners,
			final LinkedHashSet<AppliedFilter<DataType>> appliedFilters, final boolean selectItemOnClick,
			final Set<ListSelectionListener<DataType>> selectionListeners) {
		super(context, inflater, decorator, logLevel, items, allowDuplicates, notifyOnChange, itemClickListeners,
				adapterListeners, enableStateListeners, numberOfItemStates, triggerItemStateOnClick, itemStateListeners,
				sortingListeners, filterListeners, appliedFilters);
		selectItemOnClick(selectItemOnClick);
		setSelectionListeners(selectionListeners);
		addSelectionListener(createSelectionListener());
	}

	@Override
	public final void addSelectionListener(final ListSelectionListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		selectionListeners.add(listener);
		String message = "Added selection listener \"" + listener + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final void removeSelectionListener(final ListSelectionListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		selectionListeners.remove(listener);
		String message = "Removed selection listener \"" + listener + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final boolean isSelected(final int index) {
		return getItems().get(index).isSelected();
	}

	@Override
	public final boolean isSelected(final DataType item) {
		int index = indexOf(item);

		if (index != -1) {
			return isSelected(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final int getNumberOfSelectedItems() {
		int result = 0;

		for (Item<DataType> item : getItems()) {
			if (item.isSelected()) {
				result++;
			}
		}

		return result;
	}

	@Override
	public final int getNumberOfUnselectedItems() {
		int result = 0;

		for (Item<DataType> item : getItems()) {
			if (!item.isSelected()) {
				result++;
			}
		}

		return result;
	}

	@Override
	public final boolean isItemSelectedOnClick() {
		return selectItemOnClick;
	}

	@Override
	public final void selectItemOnClick(final boolean selectItemOnClick) {
		this.selectItemOnClick = selectItemOnClick;
		String message = "Items are now " + (selectItemOnClick ? "" : "not ") + "selected on click";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (selectItemOnClick ? 1231 : 1237);
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
		return true;
	}

	@Override
	public abstract AbstractSelectableListAdapter<DataType> clone() throws CloneNotSupportedException;

}