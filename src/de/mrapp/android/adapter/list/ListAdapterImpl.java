package de.mrapp.android.adapter.list;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import de.mrapp.android.adapter.ListDecorator;
import de.mrapp.android.adapter.util.Item;

public class ListAdapterImpl<DataType> extends AbstractListAdapter<DataType> {

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

	protected ListAdapterImpl(final Context context, final int itemViewId,
			final View itemView, final List<Item<DataType>> items,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListSortingListener<DataType>> sortingListeners,
			final ListDecorator<DataType> decorator) {
		super(context, itemViewId, itemView, items, adapterListeners,
				sortingListeners);
		this.decorator = decorator;
	}

	public ListAdapterImpl(final Context context,
			final ListDecorator<DataType> decorator, final int itemViewId) {
		this(context, itemViewId, null, new ArrayList<Item<DataType>>(),
				new LinkedHashSet<ListAdapterListener<DataType>>(),
				new LinkedHashSet<ListSortingListener<DataType>>(), decorator);
	}

	public ListAdapterImpl(final Context context,
			final ListDecorator<DataType> decorator, final View itemView) {
		this(context, -1, itemView, new ArrayList<Item<DataType>>(),
				new LinkedHashSet<ListAdapterListener<DataType>>(),
				new LinkedHashSet<ListSortingListener<DataType>>(), decorator);
	}

	@Override
	public final View getView(final int index, final View convertView,
			final ViewGroup parent) {
		View view = inflateOrReturnItemView(parent);
		decorator.onCreateItem(getContext(), view, getItem(index));
		return view;
	}

	@Override
	public final AbstractListAdapter<DataType> clone()
			throws CloneNotSupportedException {
		return new ListAdapterImpl<DataType>(getContext(), getItemViewId(),
				getItemView(), cloneItems(), getAdapterListeners(),
				getSortingListeners(), decorator);
	}

}