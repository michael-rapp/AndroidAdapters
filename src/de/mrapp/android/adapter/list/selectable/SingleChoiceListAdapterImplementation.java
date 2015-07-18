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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import android.content.Context;
import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.SingleChoiceListAdapter;
import de.mrapp.android.adapter.datastructure.AppliedFilter;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.ListAdapterItemClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.adapter.logging.LogLevel;

/**
 * An adapter, whose underlying data is managed as a list of arbitrary items, of
 * which only item can be selected at once. Such an adapter's purpose is to
 * provide the underlying data for visualization using a {@link ListView}
 * widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public class SingleChoiceListAdapterImplementation<DataType> extends
		AbstractSelectableListAdapter<DataType> implements
		SingleChoiceListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * True, if the adapter's selection is adapted automatically, false
	 * otherwise.
	 */
	private boolean adaptSelectionAutomatically;

	/**
	 * Creates and returns a listener, which allows to select an item, when it
	 * is clicked by the user.
	 * 
	 * @return The listener, which has been created, as an instance of the type
	 *         {@link ListAdapterItemClickListener}
	 */
	private ListAdapterItemClickListener<DataType> createItemClickListener() {
		return new ListAdapterItemClickListener<DataType>() {

			@Override
			public void onItemClicked(final ListAdapter<DataType> adapter,
					final DataType item, final int index) {
				if (isItemSelectedOnClick()) {
					getLogger().logVerbose(getClass(),
							"Selecting item on click...");
					select(index);
				}
			}

		};
	}

	/**
	 * Creates and returns a listener, which allows to adapt the selections of
	 * the adapter's items, when an item has been removed from or added to the
	 * adapter.
	 * 
	 * @return The listener, which has been created, as an instance of the type
	 *         {@link ListAdapterListener}
	 */
	private ListAdapterListener<DataType> createAdapterListener() {
		return new ListAdapterListener<DataType>() {

			@Override
			public void onItemAdded(final ListAdapter<DataType> adapter,
					final DataType item, final int index) {
				if (isSelectionAdaptedAutomatically()
						&& getNumberOfItems() == 1) {
					select(index);
				}
			}

			@Override
			public void onItemRemoved(final ListAdapter<DataType> adapter,
					final DataType item, final int index) {
				if (isSelectionAdaptedAutomatically()
						&& getSelectedIndex() == -1) {
					selectNearestEnabledItem(index);
				}
			}

		};
	}

	/**
	 * Creates and returns a listener, which allows to adapt the selections of
	 * the adapter's items, when an item has been enabled or disabled.
	 * 
	 * @return The listener, which has been created, as an instance of the type
	 *         {@link ListEnableStateListener}
	 */
	private ListEnableStateListener<DataType> createEnableStateListener() {
		return new ListEnableStateListener<DataType>() {

			@Override
			public void onItemEnabled(final ListAdapter<DataType> adapter,
					final DataType item, final int index) {
				if (isSelectionAdaptedAutomatically()
						&& getNumberOfEnabledItems() == 1) {
					select(index);
				}
			}

			@Override
			public void onItemDisabled(final ListAdapter<DataType> adapter,
					final DataType item, final int index) {
				if (isSelectionAdaptedAutomatically() && isSelected(index)) {
					getItems().get(index).setSelected(false);
					notifyOnItemUnselected(item, index);
					notifyOnDataSetChanged();
					selectNearestEnabledItem(index);
				}
			}

		};
	}

	/**
	 * Creates and returns a listener, which allows to adapt the selections of
	 * the adapter's items, when an the adapter's underlying data has been
	 * filtered.
	 * 
	 * @return The listener, which has been created, as an instance of the type
	 *         {@link ListFilterListener}
	 */
	private ListFilterListener<DataType> createFilterListener() {
		return new ListFilterListener<DataType>() {

			@Override
			public void onApplyFilter(final ListAdapter<DataType> adapter,
					final String query, final int flags,
					final Filter<DataType> filter,
					final Collection<DataType> filteredItems) {
				if (isSelectionAdaptedAutomatically() && isFiltered()
						&& getSelectedIndex() == -1 && !isEmpty()) {
					select(0);
				}
			}

			@Override
			public void onResetFilter(final ListAdapter<DataType> adapter,
					final String query, final int flags,
					final Collection<DataType> filteredItems) {
				return;
			}
		};
	}

	/**
	 * Creates and returns a listener, which allows to adapt the unfiltered
	 * items, when an item has been selected.
	 * 
	 * @return The listener, which has been created, as an instance of the type
	 *         {@link ListSelectionListener}
	 */
	private ListSelectionListener<DataType> createSelectionListener() {
		return new ListSelectionListener<DataType>() {

			@Override
			public void onItemSelected(
					final SelectableListAdapter<DataType> adapter,
					final DataType item, final int index) {
				if (isFiltered()) {
					for (int i = 0; i < getUnfilteredItems().size(); i++) {
						if (i != getUnfilteredIndex(index)) {
							getUnfilteredItems().get(i).setSelected(false);
						}
					}
				}
			}

			@Override
			public void onItemUnselected(
					final SelectableListAdapter<DataType> adapter,
					final DataType item, final int index) {
				return;
			}

		};
	}

	/**
	 * Selects the nearest enabled item, starting from a specific index. The
	 * item is searched alternately by ascending and descending indices. If no
	 * enabled item is available, no item will be selected.
	 * 
	 * @param index
	 *            The index, the search for the nearest enabled item should be
	 *            started at, as an {@link Integer} value
	 */
	private void selectNearestEnabledItem(final int index) {
		int ascendingIndex = index;
		int descendingIndex = index - 1;

		while (ascendingIndex < getNumberOfItems() || descendingIndex >= 0) {
			if (ascendingIndex < getNumberOfItems()
					&& isEnabled(ascendingIndex)) {
				select(ascendingIndex);
				return;
			} else if (descendingIndex >= 0 && isEnabled(descendingIndex)) {
				select(descendingIndex);
				return;
			}

			ascendingIndex++;
			descendingIndex--;
		}
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary items, of which only one item can be selected at once.
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
	 * @param adaptSelectionAutomatically
	 *            True, if the adapter's selection should be adapted
	 *            automatically, false otherwise
	 */
	protected SingleChoiceListAdapterImplementation(
			final Context context,
			final Inflater inflater,
			final SelectableListDecorator<DataType> decorator,
			final LogLevel logLevel,
			final ArrayList<Item<DataType>> items,
			final boolean allowDuplicates,
			final boolean notifyOnChange,
			final Set<ListAdapterItemClickListener<DataType>> itemClickListeners,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners,
			final int numberOfItemStates,
			final boolean triggerItemStateOnClick,
			final Set<ListItemStateListener<DataType>> itemStateListeners,
			final Set<ListSortingListener<DataType>> sortingListeners,
			final Set<ListFilterListener<DataType>> filterListeners,
			final LinkedHashSet<AppliedFilter<DataType>> appliedFilters,
			final boolean selectItemOnClick,
			final Set<ListSelectionListener<DataType>> selectionListeners,
			final boolean adaptSelectionAutomatically) {
		super(context, inflater, decorator, logLevel, items, allowDuplicates,
				notifyOnChange, itemClickListeners, adapterListeners,
				enableStateListeners, numberOfItemStates,
				triggerItemStateOnClick, itemStateListeners, sortingListeners,
				filterListeners, appliedFilters, selectItemOnClick,
				selectionListeners);
		addItemClickListener(createItemClickListener());
		addAdapterListener(createAdapterListener());
		addEnableStateListner(createEnableStateListener());
		addFilterListener(createFilterListener());
		adaptSelectionAutomatically(adaptSelectionAutomatically);
		addSelectionListener(createSelectionListener());
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary items, of which only one item can be selected at once.
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
	 */
	public SingleChoiceListAdapterImplementation(final Context context,
			final Inflater inflater,
			final SelectableListDecorator<DataType> decorator) {
		this(context, inflater, decorator, LogLevel.ALL,
				new ArrayList<Item<DataType>>(), false, true,
				new LinkedHashSet<ListAdapterItemClickListener<DataType>>(),
				new LinkedHashSet<ListAdapterListener<DataType>>(),
				new LinkedHashSet<ListEnableStateListener<DataType>>(), 1,
				false, new LinkedHashSet<ListItemStateListener<DataType>>(),
				new LinkedHashSet<ListSortingListener<DataType>>(),
				new LinkedHashSet<ListFilterListener<DataType>>(),
				new LinkedHashSet<AppliedFilter<DataType>>(), true,
				new LinkedHashSet<ListSelectionListener<DataType>>(), true);
	}

	@Override
	public final int getSelectedIndex() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			if (getItems().get(i).isSelected()) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final DataType getSelectedItem() {
		for (Item<DataType> item : getItems()) {
			if (item.isSelected()) {
				return item.getData();
			}
		}

		return null;
	}

	@Override
	public final boolean select(final int index) {
		Item<DataType> item = getItems().get(index);

		if (item.isEnabled()) {
			if (!item.isSelected()) {
				for (int i = 0; i < getNumberOfItems(); i++) {
					Item<DataType> currentItem = getItems().get(i);

					if (i == index && !currentItem.isSelected()) {
						currentItem.setSelected(true);
						notifyOnItemSelected(currentItem.getData(), i);
						String message = "Selected item \"" + currentItem
								+ "\" at index " + i;
						getLogger().logInfo(getClass(), message);
					} else if (i != index && currentItem.isSelected()) {
						currentItem.setSelected(false);
						notifyOnItemUnselected(currentItem.getData(), i);
						String message = "Unselected item \"" + currentItem
								+ "\" at index " + i;
						getLogger().logInfo(getClass(), message);
					}
				}

				notifyOnDataSetChanged();
				return true;
			} else {
				String message = "Item \"" + item.getData() + "\" at index "
						+ index
						+ " not selected, because it is already selected";
				getLogger().logDebug(getClass(), message);
				return false;
			}
		} else {
			String message = "Item \"" + item.getData() + "\" at index "
					+ index + " not selected, because it is disabled";
			getLogger().logDebug(getClass(), message);
			return false;
		}
	}

	@Override
	public final boolean select(final DataType item) {
		int index = indexOf(item);

		if (index != -1) {
			return select(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final void adaptSelectionAutomatically(
			final boolean adaptSelectionAutomatically) {
		this.adaptSelectionAutomatically = adaptSelectionAutomatically;

		if (!isEmpty() && getSelectedIndex() == -1) {
			select(0);
		}
	}

	@Override
	public final boolean isSelectionAdaptedAutomatically() {
		return adaptSelectionAutomatically;
	}

	@Override
	public final String toString() {
		return "SingleChoiceListAdapter [logLevel=" + getLogLevel()
				+ ", parameters=" + getParameters() + ", sortingListeners="
				+ getSortingListeners() + ", itemStateListeners="
				+ getItemStateListeners() + ", numberOfItemStates="
				+ getNumberOfItemStates() + ", triggerItemStateOnClick="
				+ isItemStateTriggeredOnClick() + ", enableStateListeners="
				+ getEnableStateListeners() + ", items=" + getItems()
				+ ", itemClickListeners=" + getItemClickListeners()
				+ ", adapterListeners=" + getAdapterListeners()
				+ ", allowDuplicates=" + areDuplicatesAllowed()
				+ ", notifyOnChange=" + isNotifiedOnChange()
				+ ", filterListeners=" + getSelectionListeners()
				+ ", appliedFilters=" + getAppliedFilters()
				+ ", selectItemOnClick=" + isItemSelectedOnClick()
				+ ", selectionListeners=" + getSelectionListeners()
				+ ", adaptSelectionAutomatically="
				+ isSelectionAdaptedAutomatically() + "]";
	}

	@Override
	public final SingleChoiceListAdapterImplementation<DataType> clone()
			throws CloneNotSupportedException {
		return new SingleChoiceListAdapterImplementation<DataType>(
				getContext(), getInflater(), getDecorator(), getLogLevel(),
				cloneItems(), areDuplicatesAllowed(), isNotifiedOnChange(),
				getItemClickListeners(), getAdapterListeners(),
				getEnableStateListeners(), getNumberOfItemStates(),
				isItemStateTriggeredOnClick(), getItemStateListeners(),
				getSortingListeners(), getFilterListeners(),
				cloneAppliedFilters(), isItemSelectedOnClick(),
				getSelectionListeners(), isSelectionAdaptedAutomatically());
	}

}