package de.mrapp.android.adapter.list.selectable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.View;
import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.adapter.util.Item;

public class MultipleChoiceListAdapterImplementation<DataType> extends
		AbstractSelectableListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	protected MultipleChoiceListAdapterImplementation(final Context context,
			final int itemViewId, final View itemView,
			final List<Item<DataType>> items,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners,
			final Set<ListSortingListener<DataType>> sortingListeners,
			final Set<ListSelectionListener<DataType>> selectionListeners,
			final int numberOfItemStates,
			final SelectableListDecorator<DataType> decorator) {
		super(context, itemViewId, itemView, items, adapterListeners,
				enableStateListeners, sortingListeners, selectionListeners,
				numberOfItemStates, decorator);
	}

	/**
	 * Returns, whether the item, which belongs to a specific index, is
	 * unselected, or not.
	 * 
	 * @param index
	 *            The index of the item, whose selection state should be
	 *            returned, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method
	 *            <code>getNumberOfItems():int</code> - 1
	 * @return True, if the item, which belongs to the given index, is
	 *         unselected, false otherwise
	 */
	public final boolean isUnselected(final int index) {
		return !isSelected(index);
	}

	/**
	 * Returns, whether a specific item is unselected, or not.
	 * 
	 * @param item
	 *            The item, whose selection state should be returned, as an
	 *            instance of the generic type DataType. The item may not be
	 *            null
	 * @return True, if the given item is unselected, false otherwise
	 */
	public final boolean isUnselected(final DataType item) {
		return !isSelected(item);
	}

	/**
	 * Return the index of the first selected item.
	 * 
	 * @return The index of the first selected item or -1, if no item is
	 *         selected. The index must be between 0 and the value of the method
	 *         <code>getNumberOfItems():int</code> - 1
	 */
	public final int getFirstSelectedIndex() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			if (getItems().get(i).isSelected()) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Returns the first selected item.
	 * 
	 * @return The first selected item, as an instance of the generic type
	 *         DataType or null, if no item is selected
	 */
	public final DataType getFirstSelectedItem() {
		for (Item<DataType> item : getItems()) {
			if (item.isSelected()) {
				return item.getData();
			}
		}

		return null;
	}

	/**
	 * Return the index of the last selected item.
	 * 
	 * @return The index of the last selected item or -1, if no item is
	 *         selected. The index must be between 0 and the value of the method
	 *         <code>getNumberOfItems():int</code> - 1
	 */
	public final int getLastSelectedIndex() {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			if (getItems().get(i).isSelected()) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Returns the last selected item.
	 * 
	 * @return The last selected item, as an instance of the generic type
	 *         DataType or null, if no item is selected
	 */
	public final DataType getLastSelectedItem() {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			Item<DataType> item = getItems().get(i);

			if (item.isSelected()) {
				return item.getData();
			}
		}

		return null;
	}

	/**
	 * Return the index of the first unselected item.
	 * 
	 * @return The index of the first unselected item or -1, if no item is
	 *         unselected. The index must be between 0 and the value of the
	 *         method <code>getNumberOfItems():int</code> - 1
	 */
	public final int getFirstUnselectedIndex() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			if (!getItems().get(i).isSelected()) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Returns the first unselected item.
	 * 
	 * @return The first unselected item, as an instance of the generic type
	 *         DataType or null, if no item is unselected
	 */
	public final DataType getFirstUnselectedItem() {
		for (Item<DataType> item : getItems()) {
			if (!item.isSelected()) {
				return item.getData();
			}
		}

		return null;
	}

	/**
	 * Return the index of the last unselected item.
	 * 
	 * @return The index of the last unselected item or -1, if no item is
	 *         unselected. The index must be between 0 and the value of the
	 *         method <code>getNumberOfItems():int</code> - 1
	 */
	public final int getLastUnselectedIndex() {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			if (!getItems().get(i).isSelected()) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Returns the last unselected item.
	 * 
	 * @return The last unselected item, as an instance of the generic type
	 *         DataType or null, if no item is unselected
	 */
	public final DataType getLastUnselectedItem() {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			Item<DataType> item = getItems().get(i);

			if (!item.isSelected()) {
				return item.getData();
			}
		}

		return null;
	}

	/**
	 * Returns a list, which contains the indices of all selected items.
	 * 
	 * @return A list, which contains the indices of all selected items, as an
	 *         instance of the type {@link List} or an empty list, if no item is
	 *         selected
	 */
	public final List<Integer> getSelectedIndices() {
		List<Integer> selectedIndices = new ArrayList<Integer>();

		for (int i = 0; i < getNumberOfItems(); i++) {
			if (getItems().get(i).isSelected()) {
				selectedIndices.add(i);
			}
		}

		return selectedIndices;
	}

	/**
	 * Returns a list, which contains all selected items.
	 * 
	 * @return A list, which contains all selected items, as an instance of the
	 *         type {@link List} or an empty list, if no item is selected
	 */
	public final List<DataType> getSelectedItems() {
		List<DataType> selectedItems = new ArrayList<DataType>();

		for (Item<DataType> item : getItems()) {
			if (item.isSelected()) {
				selectedItems.add(item.getData());
			}
		}

		return selectedItems;
	}

	/**
	 * Returns a list, which contains the indices of all unselected items.
	 * 
	 * @return A list, which contains the indices of all unselected items, as an
	 *         instance of the type {@link List} or an empty list, if no item is
	 *         selected
	 */
	public final List<Integer> getUnselectedIndices() {
		List<Integer> unselectedIndices = new ArrayList<Integer>();

		for (int i = 0; i < getNumberOfItems(); i++) {
			if (!getItems().get(i).isSelected()) {
				unselectedIndices.add(i);
			}
		}

		return unselectedIndices;
	}

	/**
	 * Returns a list, which contains all unselected items.
	 * 
	 * @return A list, which contains all unselected items, as an instance of
	 *         the type {@link List} or an empty list, if no item is selected
	 */
	public final List<DataType> getUnselectedItems() {
		List<DataType> unselectedItems = new ArrayList<DataType>();

		for (Item<DataType> item : getItems()) {
			if (!item.isSelected()) {
				unselectedItems.add(item.getData());
			}
		}

		return unselectedItems;
	}

	/**
	 * Returns the number of selected items.
	 * 
	 * @return The number of selected items, as an {@link Integer} value
	 */
	public final int getNumberOfSelectedItems() {
		return getSelectedItems().size();
	}

	/**
	 * Returns the number unselected items.
	 * 
	 * @return The number of unselected items, as an {@link Integer} value
	 */
	public final int getNumberOfUnselectedItems() {
		return getUnselectedItems().size();
	}

	/**
	 * Selects the item, which belongs to a specific index.
	 * 
	 * @param index
	 *            The index of the item, which should be selected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1
	 */
	public final void select(final int index) {
		Item<DataType> item = getItems().get(index);
		item.setSelected(true);
		notifyOnItemSelected(item.getData(), index);
	}

	/**
	 * Selects a specific item.
	 * 
	 * @param item
	 *            The item, which should be selected, as an instance of the
	 *            generic type DataType. The item may not be null
	 */
	public final void select(final DataType item) {
		select(indexOf(item));
	}

	/**
	 * Unselects the item, which belongs to a specific index.
	 * 
	 * @param index
	 *            The index of the item, which should be unselected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1
	 */
	public final void unselect(final int index) {
		Item<DataType> item = getItems().get(index);
		item.setSelected(false);
		notifyOnItemUnselected(item.getData(), index);
	}

	/**
	 * Unselects a specific item.
	 * 
	 * @param item
	 *            The item, which should be unselected, as an instance of the
	 *            generic type DataType. The item may not be null
	 */
	public final void unselect(final DataType item) {
		unselect(indexOf(item));
	}

	/**
	 * Triggers the selection of the item, which belongs to a specific index.
	 * This causes the item to become unselected, if it is selected and vice
	 * versa.
	 * 
	 * @param index
	 *            The index of the item, whose selection should be triggered, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1
	 */
	public final void triggerSelection(final int index) {
		Item<DataType> item = getItems().get(index);

		if (item.isSelected()) {
			item.setSelected(false);
			notifyOnItemUnselected(item.getData(), index);
		} else {
			item.setSelected(true);
			notifyOnItemSelected(item.getData(), index);
		}
	}

	/**
	 * Triggers the selection of a specific item. This causes the item to become
	 * unselected, if it is selected and vice versa.
	 * 
	 * @param item
	 *            The item, whose selection should be triggered, as an instance
	 *            of the generic type DataType. The item may not be null
	 */
	public final void triggerSelection(final DataType item) {
		triggerSelection(indexOf(item));
	}

	/**
	 * Selects all items.
	 */
	public final void selectAll() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			select(i);
		}
	}

	/**
	 * Unselects all items.
	 */
	public final void unselectAll() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			unselect(i);
		}
	}

	/**
	 * Triggers the selections of all items. This causes an item to become
	 * unselected, if it is selected and vice versa.
	 */
	public final void triggerAllSelections() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			triggerSelection(i);
		}
	}

	@Override
	public final MultipleChoiceListAdapterImplementation<DataType> clone()
			throws CloneNotSupportedException {
		return new MultipleChoiceListAdapterImplementation<DataType>(
				getContext(), getItemViewId(), getItemView(), cloneItems(),
				getAdapterListeners(), getEnableStateListeners(),
				getSortingListeners(), getSelectionListeners(),
				getNumberOfItemStates(), getDecorator());
	}

}