package de.mrapp.android.adapter.list.selectable;

import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.View;
import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.adapter.util.Item;

public class SingleChoiceListAdapter<DataType> extends
		AbstractSelectableListAdapter<DataType> {

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

	protected SingleChoiceListAdapter(final Context context,
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
		addAdapterListener(getAdapterListener());
	}

	public final int getSelectedIndex() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			if (getItems().get(i).isSelected()) {
				return i;
			}
		}

		return -1;
	}

	public final DataType getSelectedItem() {
		for (Item<DataType> item : getItems()) {
			if (item.isSelected()) {
				return item.getData();
			}
		}

		return null;
	}

	public final void select(final int index) {
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
	}

	public final void select(final DataType item) {
		select(indexOf(item));
	}

	@Override
	public final SingleChoiceListAdapter<DataType> clone()
			throws CloneNotSupportedException {
		return new SingleChoiceListAdapter<DataType>(getContext(),
				getItemViewId(), getItemView(), cloneItems(),
				getAdapterListeners(), getEnableStateListeners(),
				getSortingListeners(), getSelectionListeners(),
				getNumberOfItemStates(), getDecorator());
	}
}