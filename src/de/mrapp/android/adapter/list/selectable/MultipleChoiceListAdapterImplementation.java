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

import android.content.Context;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.datastructure.AppliedFilter;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.adapter.logging.LogLevel;

/**
 * An adapter, whose underlying data is managed as a list of arbitrary items, of
 * which multiple items can be selected at once. Such an adapter's purpose is to
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
public class MultipleChoiceListAdapterImplementation<DataType> extends
		AbstractSelectableListAdapter<DataType> implements
		MultipleChoiceListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

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
				return;
			}

			@Override
			public void onItemDisabled(final DataType item, final int index) {
				getItems().get(index).setSelected(false);
				notifyOnItemUnselected(item, index);
				notifyDataSetChanged();
			}

		};
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary items, of which multiple items can be selected at once.
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
	protected MultipleChoiceListAdapterImplementation(final Context context,
			final Inflater inflater,
			final SelectableListDecorator<DataType> decorator,
			final LogLevel logLevel, final List<Item<DataType>> items,
			final boolean allowDuplicates,
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
		super(context, inflater, decorator, logLevel, items, allowDuplicates,
				adapterListeners, enableStateListeners, numberOfItemStates,
				triggerItemStateOnClick, itemStateListeners, sortingListeners,
				filterListeners, appliedFilters, selectItemOnClick,
				selectionListeners);
		addEnableStateListner(createEnableStateListener());
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary items, of which multiple items can be selected at once.
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
	public MultipleChoiceListAdapterImplementation(final Context context,
			final Inflater inflater,
			final SelectableListDecorator<DataType> decorator) {
		this(context, inflater, decorator, LogLevel.ALL,
				new ArrayList<Item<DataType>>(), false,
				new LinkedHashSet<ListAdapterListener<DataType>>(),
				new LinkedHashSet<ListEnableStateListener<DataType>>(), 1,
				false, new LinkedHashSet<ListItemStateListener<DataType>>(),
				new LinkedHashSet<ListSortingListener<DataType>>(),
				new LinkedHashSet<ListFilterListener<DataType>>(),
				new LinkedHashSet<AppliedFilter<DataType>>(), true,
				new LinkedHashSet<ListSelectionListener<DataType>>());
	}

	@Override
	public final boolean isUnselected(final int index) {
		return !isSelected(index);
	}

	@Override
	public final boolean isUnselected(final DataType item) {
		return !isSelected(item);
	}

	@Override
	public final int getFirstSelectedIndex() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			if (getItems().get(i).isSelected()) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final DataType getFirstSelectedItem() {
		for (Item<DataType> item : getItems()) {
			if (item.isSelected()) {
				return item.getData();
			}
		}

		return null;
	}

	@Override
	public final int getLastSelectedIndex() {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			if (getItems().get(i).isSelected()) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final DataType getLastSelectedItem() {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			Item<DataType> item = getItems().get(i);

			if (item.isSelected()) {
				return item.getData();
			}
		}

		return null;
	}

	@Override
	public final int getFirstUnselectedIndex() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			if (!getItems().get(i).isSelected()) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final DataType getFirstUnselectedItem() {
		for (Item<DataType> item : getItems()) {
			if (!item.isSelected()) {
				return item.getData();
			}
		}

		return null;
	}

	@Override
	public final int getLastUnselectedIndex() {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			if (!getItems().get(i).isSelected()) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final DataType getLastUnselectedItem() {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			Item<DataType> item = getItems().get(i);

			if (!item.isSelected()) {
				return item.getData();
			}
		}

		return null;
	}

	@Override
	public final Collection<Integer> getSelectedIndices() {
		List<Integer> selectedIndices = new ArrayList<Integer>();

		for (int i = 0; i < getNumberOfItems(); i++) {
			if (getItems().get(i).isSelected()) {
				selectedIndices.add(i);
			}
		}

		return selectedIndices;
	}

	@Override
	public final Collection<DataType> getSelectedItems() {
		List<DataType> selectedItems = new ArrayList<DataType>();

		for (Item<DataType> item : getItems()) {
			if (item.isSelected()) {
				selectedItems.add(item.getData());
			}
		}

		return selectedItems;
	}

	@Override
	public final Collection<Integer> getUnselectedIndices() {
		List<Integer> unselectedIndices = new ArrayList<Integer>();

		for (int i = 0; i < getNumberOfItems(); i++) {
			if (!getItems().get(i).isSelected()) {
				unselectedIndices.add(i);
			}
		}

		return unselectedIndices;
	}

	@Override
	public final Collection<DataType> getUnselectedItems() {
		List<DataType> unselectedItems = new ArrayList<DataType>();

		for (Item<DataType> item : getItems()) {
			if (!item.isSelected()) {
				unselectedItems.add(item.getData());
			}
		}

		return unselectedItems;
	}

	@Override
	public final int getNumberOfSelectedItems() {
		return getSelectedItems().size();
	}

	@Override
	public final int getNumberOfUnselectedItems() {
		return getUnselectedItems().size();
	}

	@Override
	public final boolean select(final int index) {
		Item<DataType> item = getItems().get(index);

		if (item.isEnabled()) {
			if (!item.isSelected()) {
				item.setSelected(true);
				notifyOnItemSelected(item.getData(), index);
				notifyDataSetChanged();
				String message = "Selected item \"" + item + "\" at index "
						+ index;
				getLogger().logInfo(getClass(), message);
				return true;
			} else {
				String message = "Item \"" + item.getData() + " at index "
						+ index
						+ " not selected, because it is already selected";
				getLogger().logDebug(getClass(), message);
				return false;
			}
		} else {
			String message = "Item \"" + item.getData() + " at index " + index
					+ " not selected, because it is disabled";
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
	public final boolean unselect(final int index) {
		Item<DataType> item = getItems().get(index);

		if (item.isEnabled()) {
			if (item.isSelected()) {
				item.setSelected(false);
				notifyOnItemUnselected(item.getData(), index);
				notifyDataSetChanged();
				String message = "Unselected item \"" + item + "\" at index "
						+ index;
				getLogger().logInfo(getClass(), message);
				return true;
			} else {
				String message = "Item \"" + item + " at index " + index
						+ " not unselected, because it is already unselected";
				getLogger().logDebug(getClass(), message);
				return false;
			}
		} else {
			String message = "Item \"" + item + " at index " + index
					+ " not unselected, because it is disabled";
			getLogger().logDebug(getClass(), message);
			return false;
		}
	}

	@Override
	public final boolean unselect(final DataType item) {
		return unselect(indexOf(item));
	}

	@Override
	public final boolean triggerSelection(final int index) {
		if (isSelected(index)) {
			return unselect(index);
		} else {
			return select(index);
		}
	}

	@Override
	public final boolean triggerSelection(final DataType item) {
		int index = indexOf(item);

		if (index != -1) {
			return triggerSelection(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean selectAll() {
		boolean result = true;

		for (int i = 0; i < getNumberOfItems(); i++) {
			result &= select(i);
		}

		return result;
	}

	@Override
	public final boolean unselectAll() {
		boolean result = true;

		for (int i = 0; i < getNumberOfItems(); i++) {
			result &= unselect(i);
		}

		return result;
	}

	@Override
	public final boolean triggerAllSelections() {
		boolean result = true;

		for (int i = 0; i < getNumberOfItems(); i++) {
			result &= triggerSelection(i);
		}

		return result;
	}

	@Override
	public final String toString() {
		return "MultipleChoiceListAdapter [logLevel=" + getLogLevel()
				+ ", sortingListeners=" + getSortingListeners()
				+ ", itemStateListeners=" + getItemStateListeners()
				+ ", numberOfItemStates=" + getNumberOfItemStates()
				+ ", triggerItemStateOnClick=" + isItemStateTriggeredOnClick()
				+ ", enableStateListeners=" + getEnableStateListeners()
				+ ", items=" + getItems() + ", adapterListeners="
				+ getAdapterListeners() + ", allowDuplicates="
				+ areDuplicatesAllowed() + ", filterListeners="
				+ getFilterListeners() + ", appliedFilters="
				+ getAppliedFilters() + ", selectItemOnClick="
				+ isItemSelectedOnClick() + ", selectionListeners="
				+ getSelectionListeners() + "]";
	}

	@Override
	public final MultipleChoiceListAdapterImplementation<DataType> clone()
			throws CloneNotSupportedException {
		return new MultipleChoiceListAdapterImplementation<DataType>(
				getContext(), getInflater(), getDecorator(), getLogLevel(),
				cloneItems(), areDuplicatesAllowed(), getAdapterListeners(),
				getEnableStateListeners(), getNumberOfItemStates(),
				isItemStateTriggeredOnClick(), getItemStateListeners(),
				getSortingListeners(), getFilterListeners(),
				cloneAppliedFilters(), isItemSelectedOnClick(),
				getSelectionListeners());
	}

}