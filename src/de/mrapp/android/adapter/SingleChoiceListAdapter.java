package de.mrapp.android.adapter;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import de.mrapp.android.adapter.list.AbstractListAdapter;
import de.mrapp.android.adapter.list.AbstractSingleStateListAdapter;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.ListSelectionListener;
import de.mrapp.android.adapter.list.ListSortingListener;
import de.mrapp.android.adapter.util.Item;

public class SingleChoiceListAdapter<DataType> extends
		AbstractSingleStateListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	private SingleChoiceListDecorator<DataType> decorator;

	private ListAdapterListener<DataType> getAdapterListener() {
		return new ListAdapterListener<DataType>() {

			@Override
			public void onItemAdded(final DataType item, final int index) {
				if (isEmpty()) {
					select(index);
					notifyOnItemSelected(index);
				}
			}

			@Override
			public void onItemRemoved(final DataType item, final int index) {
				if (size() >= index + 1) {
					select(index);
					notifyOnItemSelected(index);
				} else if (size() >= index) {
					select(index - 1);
					notifyOnItemSelected(index - 1);
				}
			}

		};
	}

	protected SingleChoiceListAdapter(final Context context,
			final int itemViewId, final View itemView,
			final List<Item<DataType>> items,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListSortingListener<DataType>> sortingListeners,
			final Set<ListSelectionListener<DataType>> selectionListeners,
			final SingleChoiceListDecorator<DataType> decorator) {
		super(context, itemViewId, itemView, items, adapterListeners,
				sortingListeners, selectionListeners);
		ensureNotNull(decorator, "The decorator may not be null");
		this.decorator = decorator;
		addAdapterListener(getAdapterListener());
	}

	public final int getSelectedIndex() {
		for (int i = 0; i < size(); i++) {
			if (getItems().get(i).getSelectionState() == 1) {
				return i;
			}
		}

		return -1;
	}

	public final DataType getSelectedItem() {
		for (Item<DataType> item : getItems()) {
			if (item.getSelectionState() == 1) {
				return item.getData();
			}
		}

		return null;
	}

	public final void select(final int index) {
		for (int i = 0; i < size(); i++) {
			Item<DataType> item = getItems().get(i);

			if (i == index && item.getSelectionState() == 0) {
				item.setSelectionState(1);
				notifyOnItemSelected(index);
			} else if (i != index && item.getSelectionState() == 1) {
				item.setSelectionState(0);
				notifyOnItemUnselected(index);
			}
		}
	}

	public final void select(final DataType item) {
		select(indexOf(item));
	}

	@Override
	public final View getView(final int index, final View convertView,
			final ViewGroup parent) {
		View view = inflateOrReturnItemView(parent);
		decorator.onCreateItem(getContext(), view, get(index),
				isEnabled(index), isSelected(index));
		return view;
	}

	@Override
	public final AbstractListAdapter<DataType> clone()
			throws CloneNotSupportedException {
		return new SingleChoiceListAdapter<DataType>(getContext(),
				getItemViewId(), getItemView(), cloneItems(),
				getAdapterListeners(), getSortingListeners(),
				getSelectionListeners(), decorator);
	}

}