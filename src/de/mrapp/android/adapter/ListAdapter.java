package de.mrapp.android.adapter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import de.mrapp.android.adapter.list.AbstractListAdapter;
import de.mrapp.android.adapter.list.AbstractSortableListAdapter;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.ListEnableStateListener;
import de.mrapp.android.adapter.list.ListSortingListener;
import de.mrapp.android.adapter.util.Item;
import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

public class ListAdapter<DataType> extends
		AbstractSortableListAdapter<DataType> {

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

	protected ListAdapter(final Context context, final int itemViewId,
			final View itemView, final List<Item<DataType>> items,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners,
			final Set<ListSortingListener<DataType>> sortingListeners,
			final ListDecorator<DataType> decorator) {
		super(context, itemViewId, itemView, items, adapterListeners,
				enableStateListeners, sortingListeners);
		ensureNotNull(decorator, "The decorator may not be null");
		this.decorator = decorator;
	}

	public ListAdapter(final Context context,
			final ListDecorator<DataType> decorator, final int itemViewId) {
		this(context, itemViewId, null, new ArrayList<Item<DataType>>(),
				new LinkedHashSet<ListAdapterListener<DataType>>(),
				new LinkedHashSet<ListEnableStateListener<DataType>>(),
				new LinkedHashSet<ListSortingListener<DataType>>(), decorator);
	}

	public ListAdapter(final Context context,
			final ListDecorator<DataType> decorator, final View itemView) {
		this(context, -1, itemView, new ArrayList<Item<DataType>>(),
				new LinkedHashSet<ListAdapterListener<DataType>>(),
				new LinkedHashSet<ListEnableStateListener<DataType>>(),
				new LinkedHashSet<ListSortingListener<DataType>>(), decorator);
	}

	@Override
	public final View getView(final int index, final View convertView,
			final ViewGroup parent) {
		View view = inflateOrReturnItemView(parent);
		decorator.onCreateItem(getContext(), view, getItem(index),
				isEnabled(index));
		return view;
	}

	@Override
	public final AbstractListAdapter<DataType> clone()
			throws CloneNotSupportedException {
		return new ListAdapter<DataType>(getContext(), getItemViewId(),
				getItemView(), cloneItems(), getAdapterListeners(),
				getEnableStateListeners(), getSortingListeners(), decorator);
	}

}