package de.mrapp.android.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.View;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.selectable.AbstractSelectableListAdapter;
import de.mrapp.android.adapter.list.selectable.ListSelectionListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.adapter.util.Item;

public class MultipleChoiceListAdapter<DataType> extends
		AbstractSelectableListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	protected MultipleChoiceListAdapter(final Context context,
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

	public final boolean isUnselected(final int index) {
		return !isSelected(index);
	}

	public final boolean isUnselected(final DataType item) {
		return !isSelected(item);
	}

	public final int getFirstSelectedIndex() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			if (getItems().get(i).isSelected()) {
				return i;
			}
		}

		return -1;
	}

	public final DataType getFirstSelectedItem() {
		for (Item<DataType> item : getItems()) {
			if (item.isSelected()) {
				return item.getData();
			}
		}

		return null;
	}

	public final int getLastSelectedIndex() {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			if (getItems().get(i).isSelected()) {
				return i;
			}
		}

		return -1;
	}

	public final DataType getLastSelectedItem() {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			Item<DataType> item = getItems().get(i);

			if (item.isSelected()) {
				return item.getData();
			}
		}

		return null;
	}

	public final int getFirstUnselectedIndex() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			if (!getItems().get(i).isSelected()) {
				return i;
			}
		}

		return -1;
	}

	public final DataType getFirstUnselectedItem() {
		for (Item<DataType> item : getItems()) {
			if (!item.isSelected()) {
				return item.getData();
			}
		}

		return null;
	}

	public final int getLastUnselectedIndex() {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			if (!getItems().get(i).isSelected()) {
				return i;
			}
		}

		return -1;
	}

	public final DataType getLastUnselectedItem() {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			Item<DataType> item = getItems().get(i);

			if (!item.isSelected()) {
				return item.getData();
			}
		}

		return null;
	}

	public final List<Integer> getSelectedIndices() {
		List<Integer> selectedIndices = new ArrayList<Integer>();

		for (int i = 0; i < getNumberOfItems(); i++) {
			if (getItems().get(i).isSelected()) {
				selectedIndices.add(i);
			}
		}

		return selectedIndices;
	}

	public final List<DataType> getSelectedItems() {
		List<DataType> selectedItems = new ArrayList<DataType>();

		for (Item<DataType> item : getItems()) {
			if (item.isSelected()) {
				selectedItems.add(item.getData());
			}
		}

		return selectedItems;
	}

	public final List<Integer> getUnselectedIndices() {
		List<Integer> unselectedIndices = new ArrayList<Integer>();

		for (int i = 0; i < getNumberOfItems(); i++) {
			if (!getItems().get(i).isSelected()) {
				unselectedIndices.add(i);
			}
		}

		return unselectedIndices;
	}

	public final List<DataType> getUnselectedItems() {
		List<DataType> unselectedItems = new ArrayList<DataType>();

		for (Item<DataType> item : getItems()) {
			if (!item.isSelected()) {
				unselectedItems.add(item.getData());
			}
		}

		return unselectedItems;
	}

	public final int getNumberOfSelectedItems() {
		return getSelectedItems().size();
	}

	public final int getNumberOfUnselectedItems() {
		return getUnselectedItems().size();
	}

	public final void select(final int index) {
		Item<DataType> item = getItems().get(index);
		item.setSelected(true);
		notifyOnItemSelected(item.getData(), index);
	}

	public final void select(final DataType item) {
		select(indexOf(item));
	}

	public final void unselect(final int index) {
		Item<DataType> item = getItems().get(index);
		item.setSelected(false);
		notifyOnItemUnselected(item.getData(), index);
	}

	public final void unselect(final DataType item) {
		unselect(indexOf(item));
	}

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

	public final void triggerSelection(final DataType item) {
		triggerSelection(indexOf(item));
	}

	public final void selectAll() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			select(i);
		}
	}

	public final void unselectAll() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			unselect(i);
		}
	}

	public final void triggerAllSelections() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			triggerSelection(i);
		}
	}

	@Override
	public final MultipleChoiceListAdapter<DataType> clone()
			throws CloneNotSupportedException {
		return new MultipleChoiceListAdapter<DataType>(getContext(),
				getItemViewId(), getItemView(), cloneItems(),
				getAdapterListeners(), getEnableStateListeners(),
				getSortingListeners(), getSelectionListeners(),
				getNumberOfItemStates(), getDecorator());
	}

}