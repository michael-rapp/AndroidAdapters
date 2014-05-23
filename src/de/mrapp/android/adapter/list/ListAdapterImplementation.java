package de.mrapp.android.adapter.list;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import de.mrapp.android.adapter.ListDecorator;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.itemstate.AbstractItemStateListAdapter;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;

public class ListAdapterImplementation<DataType> extends
		AbstractItemStateListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The decorator, which is used to customize the appearance of the widgets,
	 * which belong to the view, which is used to visualize the items of the
	 * adapter.
	 */
	private final transient ListDecorator<DataType> decorator;

	protected ListAdapterImplementation(final Context context,
			final int itemViewId, final View itemView,
			final List<Item<DataType>> items,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners,
			final Set<ListSortingListener<DataType>> sortingListeners,
			final int numberOfItemStates,
			final ListDecorator<DataType> decorator) {
		super(context, itemViewId, itemView, items, adapterListeners,
				enableStateListeners, sortingListeners, numberOfItemStates);
		ensureNotNull(decorator, "The decorator may not be null");
		this.decorator = decorator;
	}

	public ListAdapterImplementation(final Context context,
			final int numberOfItemStates,
			final ListDecorator<DataType> decorator, final int itemViewId) {
		this(context, itemViewId, null, new ArrayList<Item<DataType>>(),
				new LinkedHashSet<ListAdapterListener<DataType>>(),
				new LinkedHashSet<ListEnableStateListener<DataType>>(),
				new LinkedHashSet<ListSortingListener<DataType>>(),
				numberOfItemStates, decorator);
	}

	public ListAdapterImplementation(final Context context,
			final int numberOfItemStates,
			final ListDecorator<DataType> decorator, final View itemView) {
		this(context, -1, itemView, new ArrayList<Item<DataType>>(),
				new LinkedHashSet<ListAdapterListener<DataType>>(),
				new LinkedHashSet<ListEnableStateListener<DataType>>(),
				new LinkedHashSet<ListSortingListener<DataType>>(),
				numberOfItemStates, decorator);
	}

	@Override
	public final View getView(final int index, final View convertView,
			final ViewGroup parent) {
		View view = inflateOrReturnItemView(parent);
		decorator.onCreateItem(getContext(), view, getItem(index),
				isEnabled(index), getItemState(index));
		return view;
	}

	@Override
	public final ListAdapterImplementation<DataType> clone()
			throws CloneNotSupportedException {
		return new ListAdapterImplementation<DataType>(getContext(),
				getItemViewId(), getItemView(), cloneItems(),
				getAdapterListeners(), getEnableStateListeners(),
				getSortingListeners(), getNumberOfItemStates(), decorator);
	}

}