package de.mrapp.android.adapter.list.selectable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.View;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;

public class MultipleChoiceListAdapterImplementation<DataType> extends
		AbstractSelectableListAdapter<DataType> implements
		MultipleChoiceListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	private ListEnableStateListener<DataType> getEnableStateListener() {
		return new ListEnableStateListener<DataType>() {

			@Override
			public void onItemEnabled(final DataType item, final int index) {
				return;
			}

			@Override
			public void onItemDisabled(final DataType item, final int index) {
				unselect(index);
			}

		};
	}

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
		addEnableStateListner(getEnableStateListener());
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
	public final List<Integer> getSelectedIndices() {
		List<Integer> selectedIndices = new ArrayList<Integer>();

		for (int i = 0; i < getNumberOfItems(); i++) {
			if (getItems().get(i).isSelected()) {
				selectedIndices.add(i);
			}
		}

		return selectedIndices;
	}

	@Override
	public final List<DataType> getSelectedItems() {
		List<DataType> selectedItems = new ArrayList<DataType>();

		for (Item<DataType> item : getItems()) {
			if (item.isSelected()) {
				selectedItems.add(item.getData());
			}
		}

		return selectedItems;
	}

	@Override
	public final List<Integer> getUnselectedIndices() {
		List<Integer> unselectedIndices = new ArrayList<Integer>();

		for (int i = 0; i < getNumberOfItems(); i++) {
			if (!getItems().get(i).isSelected()) {
				unselectedIndices.add(i);
			}
		}

		return unselectedIndices;
	}

	@Override
	public final List<DataType> getUnselectedItems() {
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
	public final void select(final int index) {
		Item<DataType> item = getItems().get(index);
		item.setSelected(true);
		notifyOnItemSelected(item.getData(), index);
		notifyDataSetInvalidated();
	}

	@Override
	public final void select(final DataType item) {
		select(indexOf(item));
	}

	@Override
	public final void unselect(final int index) {
		Item<DataType> item = getItems().get(index);
		item.setSelected(false);
		notifyOnItemUnselected(item.getData(), index);
		notifyDataSetInvalidated();
	}

	@Override
	public final void unselect(final DataType item) {
		unselect(indexOf(item));
	}

	@Override
	public final void triggerSelection(final int index) {
		Item<DataType> item = getItems().get(index);

		if (item.isSelected()) {
			item.setSelected(false);
			notifyOnItemUnselected(item.getData(), index);
			notifyDataSetInvalidated();
		} else {
			item.setSelected(true);
			notifyOnItemSelected(item.getData(), index);
			notifyDataSetInvalidated();
		}
	}

	@Override
	public final void triggerSelection(final DataType item) {
		triggerSelection(indexOf(item));
	}

	@Override
	public final void selectAll() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			select(i);
		}
	}

	@Override
	public final void unselectAll() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			unselect(i);
		}
	}

	@Override
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