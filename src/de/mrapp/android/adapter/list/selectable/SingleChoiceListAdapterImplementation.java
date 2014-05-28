package de.mrapp.android.adapter.list.selectable;

import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.View;
import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.SingleChoiceListAdapter;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;

public class SingleChoiceListAdapterImplementation<DataType> extends
		AbstractSelectableListAdapter<DataType> implements
		SingleChoiceListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	private ListAdapterListener<DataType> getAdapterListener() {
		return new ListAdapterListener<DataType>() {

			@Override
			public void onItemAdded(final DataType item, final int index) {
				if (isEmpty()) {
					select(index);
					notifyOnItemSelected(item, index);
				}
			}

			@Override
			public void onItemRemoved(final DataType item, final int index) {
				if (getNumberOfItems() >= index + 1) {
					select(index);
					notifyOnItemSelected(getItem(index), index);
				} else if (getNumberOfItems() >= index) {
					select(index - 1);
					notifyOnItemSelected(getItem(index - 1), index - 1);
				}
			}

		};
	}

	protected SingleChoiceListAdapterImplementation(final Context context,
			final int itemViewId, final View itemView,
			final List<Item<DataType>> items, final boolean allowDuplicates,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners,
			final Set<ListSortingListener<DataType>> sortingListeners,
			final Set<ListSelectionListener<DataType>> selectionListeners,
			final int numberOfItemStates,
			final SelectableListDecorator<DataType> decorator) {
		super(context, itemViewId, itemView, items, allowDuplicates,
				adapterListeners, enableStateListeners, sortingListeners,
				selectionListeners, numberOfItemStates, decorator);
		addAdapterListener(getAdapterListener());
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
		if (getItems().get(index).isEnabled()) {
			for (int i = 0; i < getNumberOfItems(); i++) {
				Item<DataType> item = getItems().get(i);

				if (i == index && !item.isSelected()) {
					item.setSelected(true);
					notifyOnItemSelected(item.getData(), index);
				} else if (i != index && item.isSelected()) {
					item.setSelected(false);
					notifyOnItemUnselected(item.getData(), index);
				}
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public final boolean select(final DataType item) {
		return select(indexOf(item));
	}

	@Override
	public final SingleChoiceListAdapterImplementation<DataType> clone()
			throws CloneNotSupportedException {
		return new SingleChoiceListAdapterImplementation<DataType>(
				getContext(), getItemViewId(), getItemView(), cloneItems(),
				areDuplicatesAllowed(), getAdapterListeners(),
				getEnableStateListeners(), getSortingListeners(),
				getSelectionListeners(), getNumberOfItemStates(),
				getDecorator());
	}
}