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
package de.mrapp.android.adapter.list.filterable;

import static de.mrapp.android.adapter.util.Condition.ensureAtLeast;
import static de.mrapp.android.adapter.util.Condition.ensureAtMaximum;
import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseIntArray;
import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.Order;
import de.mrapp.android.adapter.datastructure.AppliedFilter;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.datastructure.item.ItemComparator;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.ListAdapterItemClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.list.sortable.AbstractSortableListAdapter;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.adapter.logging.LogLevel;
import de.mrapp.android.adapter.util.VisibleForTesting;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a filterable list of arbitrary items. Such an adapter's purpose is to provide
 * the underlying data for visualization using a {@link AbsListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * @param <DecoratorType>
 *            The type of the decorator, which allows to customize the
 *            appearance of the views, which are used to visualize the items of
 *            the adapter
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractFilterableListAdapter<DataType, DecoratorType>
		extends AbstractSortableListAdapter<DataType, DecoratorType>implements FilterableListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The key, which is used to store the filters, which are used to filter the
	 * adapter's underlying data, within a bundle.
	 */
	@VisibleForTesting
	protected static final String APPLIED_FILTERS_BUNDLE_KEY = AbstractFilterableListAdapter.class.getSimpleName()
			+ "::AppliedFilters";

	/**
	 * A list, which contains the adapter's unfiltered data.
	 */
	private transient ArrayList<Item<DataType>> unfilteredItems;

	/**
	 * A sparse map, which maps the indices of the adapter's filtered items to
	 * their corresponding indices of the unfiltered data.
	 */
	private transient SparseIntArray indexMapping;

	/**
	 * A set, which contains the listeners, which should be notified, when the
	 * adapter's underlying data has been filtered.
	 */
	private transient Set<ListFilterListener<DataType>> filterListeners;

	/**
	 * A set, which contains the filters, which are used to filter the adapter's
	 * underlying data.
	 */
	private LinkedHashSet<AppliedFilter<DataType>> appliedFilters;

	/**
	 * Creates and returns a listener, which allows to adapt the unfiltered
	 * items, when an item has been removed from or added to the adapter.
	 * 
	 * @return The listener, which has been created, as an instance of the type
	 *         {@link ListAdapterListener}
	 */
	private ListAdapterListener<DataType> createAdapterListener() {
		return new ListAdapterListener<DataType>() {

			@Override
			public void onItemAdded(final ListAdapter<DataType> adapter, final DataType item, final int index) {
				if (isFiltered()) {
					Item<DataType> addedItem = getItems().get(index);
					unfilteredItems.add(index, addedItem);

					if (!matchAllFilters(addedItem)) {
						getItems().remove(index);
					}
				}
			}

			@Override
			public void onItemRemoved(final ListAdapter<DataType> adapter, final DataType item, final int index) {
				if (isFiltered()) {
					unfilteredItems.remove(getUnfilteredIndex(index));
				}
			}

		};
	}

	/**
	 * Creates and returns a listener, which allows to adapt the unfiltered
	 * items, when an item has been enabled or disabled.
	 * 
	 * @return The listener, which has been created, as an instance of the type
	 *         {@link ListEnableStateListener}
	 */
	private ListEnableStateListener<DataType> createEnableStateListener() {
		return new ListEnableStateListener<DataType>() {

			@Override
			public void onItemEnabled(final ListAdapter<DataType> adapter, final DataType item, final int index) {
				if (isFiltered()) {
					unfilteredItems.get(getUnfilteredIndex(index)).setEnabled(true);
				}
			}

			@Override
			public void onItemDisabled(final ListAdapter<DataType> adapter, final DataType item, final int index) {
				if (isFiltered()) {
					unfilteredItems.get(getUnfilteredIndex(index)).setEnabled(false);
				}
			}

		};
	}

	/**
	 * Creates and returns a listener, which allows to adapt the unfiltered
	 * items, when an item's state has been changed.
	 * 
	 * @return The listener, which has been created, as an instance of the type
	 *         {@link ListItemStateListener}
	 */
	private ListItemStateListener<DataType> createItemStateListener() {
		return new ListItemStateListener<DataType>() {

			@Override
			public void onItemStateChanged(final ListAdapter<DataType> adapter, final DataType item, final int index,
					final int state) {
				if (isFiltered()) {
					unfilteredItems.get(getUnfilteredIndex(index)).setState(state);
				}
			}

		};
	}

	/**
	 * Creates and returns a listener, which allows to adapter the unfiltered
	 * items, when the adapter's underlying data has been sorted.
	 * 
	 * @return The listener, which has been created as an instance of the type
	 *         {@link ListSortingListener}
	 */
	private ListSortingListener<DataType> createSortingListener() {
		return new ListSortingListener<DataType>() {

			@Override
			public void onSorted(final ListAdapter<DataType> adapter, final Collection<DataType> sortedItems,
					final Order order, final Comparator<DataType> comparator) {
				if (isFiltered()) {
					if (order == Order.ASCENDING) {
						if (comparator != null) {
							Collections.sort(unfilteredItems, new ItemComparator<DataType>(comparator));
						} else {
							Collections.sort(unfilteredItems);
						}
					} else {
						if (comparator != null) {
							Collections.sort(unfilteredItems,
									Collections.reverseOrder(new ItemComparator<DataType>(comparator)));
						} else {
							Collections.sort(unfilteredItems, Collections.reverseOrder());
						}
					}
				}
			}

		};
	}

	/**
	 * Applies all filters, which are currently applied on the adapter, to
	 * filter the adapter's underlying data.
	 */
	private void applyAllFilters() {
		for (AppliedFilter<DataType> filter : appliedFilters) {
			applyFilter(filter);
		}
	}

	/**
	 * Applies a specific filter to filter the adapter's underlying data.
	 * 
	 * @param filter
	 *            The filter, which should be applied, as an instance of the
	 *            class {@link AppliedFilter}. The filter may not be null
	 */
	private void applyFilter(final AppliedFilter<DataType> filter) {
		if (unfilteredItems == null) {
			unfilteredItems = new ArrayList<Item<DataType>>(getItems());
			indexMapping = new SparseIntArray();
		}

		Collection<Item<DataType>> itemsToRemove = new LinkedList<Item<DataType>>();
		int counter = 0;

		for (int i = 0; i < getNumberOfItems(); i++) {
			Item<DataType> item = getItems().get(i);

			if (!matchFilter(filter, item)) {
				itemsToRemove.add(item);
			} else {
				indexMapping.put(counter, i);
				counter++;
			}
		}

		getItems().removeAll(itemsToRemove);
	}

	/**
	 * Returns, whether a specific item matches all applied filters, or not.
	 * 
	 * @param item
	 *            The item, which should be matched, as an instance of the class
	 *            {@link Item}. The item may not be null
	 * @return True, if the given item matches all applied filters, false
	 *         otherwise
	 */
	private boolean matchAllFilters(final Item<DataType> item) {
		for (AppliedFilter<DataType> filter : appliedFilters) {
			if (!matchFilter(filter, item)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns, whether a specific item matches a filter, or not.
	 * 
	 * @param filter
	 *            The filter, which should be matched, as an instance of the
	 *            class {@link AppliedFilter}. The filter may not be null
	 * @param item
	 *            The item, which should be matched, as an instance of the class
	 *            {@link Item}. The item may not be null
	 * @return True, if the given item matches the filter, false otherwise
	 */
	private boolean matchFilter(final AppliedFilter<DataType> filter, final Item<DataType> item) {
		if (filter.getFilter() != null) {
			return filter.getFilter().match(item.getData(), filter.getQuery(), filter.getFlags());
		} else {
			return item.match(filter.getQuery(), filter.getFlags());
		}
	}

	/**
	 * Notifies all listeners, which have been registered to be notified, when
	 * the adapter's underlying data has been filtered, when a filter has been
	 * applied.
	 * 
	 * @param query
	 *            The query, which has been used to filter the adapter's
	 *            underlying data, as a {@link String}. The query may not be
	 *            null
	 * @param flags
	 *            The flags, which have been used to filter the adapter's
	 *            underlying data, as an {@link Integer} value, or 0, if no
	 *            flags have been used
	 * @param filter
	 *            The filter, which has been used to apply the query on the
	 *            single items, as an instance of the type {@link Filter} or
	 *            null, if the items' implementations of the interface
	 *            {@link Filterable} has been used instead
	 * @param filteredItems
	 *            A collection, which contains the adapter's filtered items, as
	 *            an instance of the type {@link Collection} or an empty
	 *            collection, if the adapter does not contain any items
	 */
	private void notifyOnApplyFilter(final String query, final int flags, final Filter<DataType> filter,
			final Collection<DataType> filteredItems) {
		for (ListFilterListener<DataType> listener : filterListeners) {
			listener.onApplyFilter(this, query, flags, filter, filteredItems);
		}
	}

	/**
	 * Notifies all listeners, which have been registered to be notified, when
	 * the adapter's underlying data has been filtered, when a filter has been
	 * reseted.
	 * 
	 * @param query
	 *            The query of the filter, which has been reseted, as a
	 *            {@link String}. The query may not be null
	 * @param flags
	 *            The flags of the filter, which has been reseted, as an
	 *            {@link Integer} value
	 * @param unfilteredItems
	 *            A collection, which contains the adapter's filtered items, as
	 *            an instance of the type {@link Collection} or an empty
	 *            collection, if the adapter does not contain any items
	 */
	private void notifyOnResetFilter(final String query, final int flags, final Collection<DataType> unfilteredItems) {
		for (ListFilterListener<DataType> listener : filterListeners) {
			listener.onResetFilter(this, query, flags, unfilteredItems);
		}
	}

	/**
	 * Returns a set, which contains the listeners, which should be notified,
	 * when the adapter's underlying data has been filtered.
	 * 
	 * @return A set, which contains the listeners, which should be notified,
	 *         when the adapter's underlying data has been filtered, as an
	 *         instance of the type {@link Set} or an empty set, if no listeners
	 *         should be notified
	 */
	protected final Set<ListFilterListener<DataType>> getFilterListeners() {
		return filterListeners;
	}

	/**
	 * Sets the set, which contains the listeners, which should be notified,
	 * when the adapter's underlying data has been filtered.
	 * 
	 * @param filterListeners
	 *            The set, which should be set, as an instance of the type
	 *            {@link Set} or an empty set, if no listeners should be
	 *            notified
	 */
	protected final void setFilterListeners(final Set<ListFilterListener<DataType>> filterListeners) {
		ensureNotNull(filterListeners, "The listeners may not be null");
		this.filterListeners = filterListeners;
	}

	/**
	 * Returns a set, which contains the filters, which are used to filter the
	 * adapter's underlying data.
	 * 
	 * @return A set, which contains the filters, which are used to filter the
	 *         adapter's underlying data, as an instance of the type
	 *         {@link LinkedHashSet} or an empty set, if the adapter's
	 *         underlying data is not filtered
	 */
	protected final LinkedHashSet<AppliedFilter<DataType>> getAppliedFilters() {
		return appliedFilters;
	}

	/**
	 * Sets the set, which contains the filters, which are used to filter the
	 * adapter's underlying data.
	 * 
	 * @param appliedFilters
	 *            The set, which should be set, as an instance of the type
	 *            {@link LinkedHashSet} or an empty set, if the adapter's
	 *            underlying data should not be filtered
	 */
	protected final void setAppliedFilters(final LinkedHashSet<AppliedFilter<DataType>> appliedFilters) {
		ensureNotNull(appliedFilters, "The applied filters may not be null");
		this.appliedFilters = appliedFilters;
		applyAllFilters();
	}

	/**
	 * Returns a list, which contains the adapter's unfiltered data.
	 * 
	 * @return A list, which contains the adapter's unfiltered data as an
	 *         instance of the type {@link ArrayList} or null, if no filters are
	 *         currently applied on the adapter
	 */
	protected final ArrayList<Item<DataType>> getUnfilteredItems() {
		return unfilteredItems;
	}

	/**
	 * Returns the unfiltered index, which corresponds to a specific filtered
	 * index.
	 * 
	 * @param filteredIndex
	 *            The index, whose corresponding unfiltered index should be
	 *            retrieved, as an {@link Integer} value
	 * @return The unfiltered index, which corresponds to the given filtered
	 *         index, as an {@link Integer} value
	 */
	protected final int getUnfilteredIndex(final int filteredIndex) {
		ensureAtLeast(filteredIndex, 0, "The index must be at least 0");
		ensureAtMaximum(filteredIndex, getNumberOfItems() - 1,
				"The index must be at maximum " + (getNumberOfItems() - 1));

		if (!isFiltered()) {
			return filteredIndex;
		} else {
			return indexMapping.get(filteredIndex);
		}
	}

	/**
	 * Creates and returns a deep copy of the set, which contains the filters,
	 * which are applied on the adapter.
	 * 
	 * @return A deep copy of the set, which contains the filters, which are
	 *         applied on the adapter, as an instance of the type
	 *         {@link LinkedHashSet} or an empty set, if no filters are applied
	 */
	protected final LinkedHashSet<AppliedFilter<DataType>> cloneAppliedFilters() {
		LinkedHashSet<AppliedFilter<DataType>> clonedAppliedFilters = new LinkedHashSet<AppliedFilter<DataType>>();

		for (AppliedFilter<DataType> filter : appliedFilters) {
			clonedAppliedFilters.add(filter.clone());
		}

		return clonedAppliedFilters;
	}

	@Override
	protected void onSaveInstanceState(final Bundle savedState) {
		super.onSaveInstanceState(savedState);
		savedState.putSerializable(APPLIED_FILTERS_BUNDLE_KEY, getAppliedFilters());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onRestoreInstanceState(final Bundle savedState) {
		super.onRestoreInstanceState(savedState);

		if (savedState.containsKey(APPLIED_FILTERS_BUNDLE_KEY)) {
			setAppliedFilters(
					(LinkedHashSet<AppliedFilter<DataType>>) savedState.getSerializable(APPLIED_FILTERS_BUNDLE_KEY));
		}
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a filterable
	 * list of arbitrary items.
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
	 */
	protected AbstractFilterableListAdapter(final Context context, final Inflater inflater,
			final DecoratorType decorator, final LogLevel logLevel, final ArrayList<Item<DataType>> items,
			final boolean allowDuplicates, final boolean notifyOnChange,
			final Set<ListAdapterItemClickListener<DataType>> itemClickListeners,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners, final int numberOfItemStates,
			final boolean triggerItemStateOnClick, final Set<ListItemStateListener<DataType>> itemStateListeners,
			final Set<ListSortingListener<DataType>> sortingListeners,
			final Set<ListFilterListener<DataType>> filterListeners,
			final LinkedHashSet<AppliedFilter<DataType>> appliedFilters) {
		super(context, inflater, decorator, logLevel, items, allowDuplicates, notifyOnChange, itemClickListeners,
				adapterListeners, enableStateListeners, numberOfItemStates, triggerItemStateOnClick, itemStateListeners,
				sortingListeners);
		setFilterListeners(filterListeners);
		setAppliedFilters(appliedFilters);
		addAdapterListener(createAdapterListener());
		addEnableStateListener(createEnableStateListener());
		addItemStateListener(createItemStateListener());
		addSortingListner(createSortingListener());
	}

	@Override
	public final boolean applyFilter(final String query, final int flags) {
		AppliedFilter<DataType> appliedFilter = new AppliedFilter<DataType>(query, flags);
		boolean added = appliedFilters.add(appliedFilter);

		if (added) {
			applyFilter(appliedFilter);
			notifyOnApplyFilter(query, flags, null, getAllItems());
			notifyOnDataSetChanged();
			String message = "Applied filter using the query \"" + query + "\" and flags \"" + flags + "\"";
			getLogger().logInfo(getClass(), message);
			return true;
		}

		String message = "Filter using the query \"" + query + "\" and flags \"" + flags
				+ "\" not applied, because a filter using the same "
				+ "query and flags is already applied on the adapter";
		getLogger().logDebug(getClass(), message);
		return false;
	}

	@Override
	public final boolean applyFilter(final String query, final int flags, final Filter<DataType> filter) {
		AppliedFilter<DataType> appliedFilter = new AppliedFilter<DataType>(query, flags, filter);
		boolean added = appliedFilters.add(appliedFilter);

		if (added) {
			applyFilter(appliedFilter);
			notifyOnApplyFilter(query, flags, filter, getAllItems());
			notifyOnDataSetChanged();
			String message = "Applied filter using the query \"" + query + "\", flags \"" + flags + "\" and filter \""
					+ filter + "\"";
			getLogger().logInfo(getClass(), message);
			return true;
		}

		String message = "Filter using the query \"" + query + "\" flags \"" + flags + "\" and filter \"" + filter
				+ "\" not applied, because a filter using the same query, flags and filter is already applied "
				+ "on the adapter";
		getLogger().logDebug(getClass(), message);
		return false;
	}

	@Override
	public final boolean resetFilter(final String query, final int flags) {
		AppliedFilter<DataType> appliedFilter = new AppliedFilter<DataType>(query, flags);
		boolean removed = appliedFilters.remove(appliedFilter);

		if (removed) {
			setItems(unfilteredItems);
			unfilteredItems = null;
			indexMapping = null;
			applyAllFilters();
			notifyOnResetFilter(query, flags, getAllItems());
			notifyOnDataSetChanged();
			String message = "Reseted filter with query \"" + query + "\" and flags \"" + flags + "\"";
			getLogger().logInfo(getClass(), message);
			return true;
		} else {
			String message = "Filter with query \"" + query + "\" and flags \"" + flags
					+ "\" not reseted, because no such filter is applied on the adapter";
			getLogger().logDebug(getClass(), message);
			return false;
		}
	}

	@Override
	public final void resetAllFilters() {
		for (AppliedFilter<DataType> appliedFilter : new LinkedHashSet<AppliedFilter<DataType>>(appliedFilters)) {
			resetFilter(appliedFilter.getQuery(), appliedFilter.getFlags());
		}

		String message = "Reseted all previously applied filters";
		getLogger().logInfo(getClass(), message);
	}

	@Override
	public final boolean isFiltered() {
		return !appliedFilters.isEmpty();
	}

	@Override
	public final void addFilterListener(final ListFilterListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		filterListeners.add(listener);
		String message = "Added filter listener \"" + listener + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final void removeFilterListener(final ListFilterListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		filterListeners.remove(listener);
		String message = "Removed filter listener \"" + listener + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + appliedFilters.hashCode();
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractFilterableListAdapter<?, ?> other = (AbstractFilterableListAdapter<?, ?>) obj;
		if (!appliedFilters.equals(other.appliedFilters))
			return false;
		return true;
	}

	@Override
	public abstract AbstractSortableListAdapter<DataType, DecoratorType> clone() throws CloneNotSupportedException;

}