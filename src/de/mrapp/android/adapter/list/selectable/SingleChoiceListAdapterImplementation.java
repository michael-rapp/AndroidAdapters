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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Pattern;

import android.content.Context;
import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.SingleChoiceListAdapter;
import de.mrapp.android.adapter.datastructure.AppliedFilter;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.filterable.Filter;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;

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
			public void onItemAdded(final DataType item, final int index) {
				if (getNumberOfItems() == 1) {
					select(index);
				}
			}

			@Override
			public void onItemRemoved(final DataType item, final int index) {
				if (getSelectedIndex() == -1) {
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
			public void onItemEnabled(final DataType item, final int index) {
				if (getNumberOfItems() == 1) {
					select(index);
				}
			}

			@Override
			public void onItemDisabled(final DataType item, final int index) {
				if (isSelected(index)) {
					getItems().get(index).setSelected(false);
					notifyOnItemUnselected(item, index);
					notifyDataSetChanged();
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
			public void onApplyFilter(final Pattern regularExpression,
					final Filter<DataType> filter,
					final Collection<DataType> filteredItems) {
				if (isFiltered() && getSelectedIndex() == -1 && !isEmpty()) {
					select(0);
				}
			}

			@Override
			public void onResetFilter(final Pattern regularExpression,
					final Collection<DataType> filteredItems) {
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
	 * Selects the item of the unfiltered data, which corresponds to a specific
	 * filtered index, if the adapter's data is currently filtered.
	 * 
	 * @param filteredIndex
	 *            The filtered index, which corresponds to the unfiltered item,
	 *            which should be selected
	 */
	private void selectUnfilteredItem(final int filteredIndex) {
		if (isFiltered()) {
			int unfilteredIndex = getUnfilteredIndex(filteredIndex);

			for (int i = 0; i < getUnfilteredItems().size(); i++) {
				Item<DataType> item = getUnfilteredItems().get(i);

				if (i == unfilteredIndex) {
					item.setSelected(true);
				} else {
					item.setSelected(false);
				}
			}
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
	 * @param filterListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been filtered or an
	 *            empty set, if no listeners should be notified
	 * @param appliedFilters
	 *            A list, which contains the filters, which should be used to
	 *            filter the adapter's underlying data or an empty list, if the
	 *            adapter's underlying data should not be filtered
	 * @param selectItemOnClick
	 *            True, if an item should be selected, when it is clicked by the
	 *            user, false otherwise
	 * @param selectionListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when an item's selection has been changed or an empty set, if
	 *            no listeners should be notified
	 */
	protected SingleChoiceListAdapterImplementation(final Context context,
			final Inflater inflater,
			final SelectableListDecorator<DataType> decorator,
			final List<Item<DataType>> items, final boolean allowDuplicates,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners,
			final int numberOfItemStates,
			final boolean triggerItemStateOnClick,
			final Set<ListItemStateListener<DataType>> itemStateListeners,
			final Set<ListSortingListener<DataType>> sortingListeners,
			final Set<ListFilterListener<DataType>> filterListeners,
			final Set<AppliedFilter<DataType>> appliedFilters,
			final boolean selectItemOnClick,
			final Set<ListSelectionListener<DataType>> selectionListeners) {
		super(context, inflater, decorator, items, allowDuplicates,
				adapterListeners, enableStateListeners, numberOfItemStates,
				triggerItemStateOnClick, itemStateListeners, sortingListeners,
				filterListeners, appliedFilters, selectItemOnClick,
				selectionListeners);
		addAdapterListener(createAdapterListener());
		addEnableStateListner(createEnableStateListener());
		addFilterListener(createFilterListener());
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
		this(context, inflater, decorator, new ArrayList<Item<DataType>>(),
				false, new LinkedHashSet<ListAdapterListener<DataType>>(),
				new LinkedHashSet<ListEnableStateListener<DataType>>(), 1,
				false, new LinkedHashSet<ListItemStateListener<DataType>>(),
				new LinkedHashSet<ListSortingListener<DataType>>(),
				new LinkedHashSet<ListFilterListener<DataType>>(),
				new LinkedHashSet<AppliedFilter<DataType>>(), true,
				new LinkedHashSet<ListSelectionListener<DataType>>());
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
		if (isEnabled(index) && !isSelected(index)) {
			for (int i = 0; i < getNumberOfItems(); i++) {
				Item<DataType> currentItem = getItems().get(i);

				if (i == index && !currentItem.isSelected()) {
					currentItem.setSelected(true);
					notifyOnItemSelected(currentItem.getData(), i);
				} else if (i != index && currentItem.isSelected()) {
					currentItem.setSelected(false);
					notifyOnItemUnselected(currentItem.getData(), i);
				}
			}

			selectUnfilteredItem(index);
			notifyDataSetChanged();
			return true;
		} else {
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
	public final String toString() {
		return "SingleChoiceListAdapter [sortingListeners="
				+ getSortingListeners() + ", itemStateListeners="
				+ getItemStateListeners() + ", numberOfItemStates="
				+ getNumberOfItemStates() + ", triggerItemStateOnClick="
				+ isItemStateTriggeredOnClick() + ", enableStateListeners="
				+ getEnableStateListeners() + ", items=" + getItems()
				+ ", adapterListeners=" + getAdapterListeners()
				+ ", allowDuplicates=" + areDuplicatesAllowed()
				+ ", filterListeners=" + getSelectionListeners()
				+ ", appliedFilters=" + getAppliedFilters()
				+ ", selectItemOnClick=" + isItemSelectedOnClick()
				+ ", selectionListeners=" + getSelectionListeners() + "]";
	}

	@Override
	public final SingleChoiceListAdapterImplementation<DataType> clone()
			throws CloneNotSupportedException {
		return new SingleChoiceListAdapterImplementation<DataType>(
				getContext(), getInflater(), getDecorator(), cloneItems(),
				areDuplicatesAllowed(), getAdapterListeners(),
				getEnableStateListeners(), getNumberOfItemStates(),
				isItemStateTriggeredOnClick(), getItemStateListeners(),
				getSortingListeners(), getFilterListeners(),
				cloneAppliedFilters(), isItemSelectedOnClick(),
				getSelectionListeners());
	}

}