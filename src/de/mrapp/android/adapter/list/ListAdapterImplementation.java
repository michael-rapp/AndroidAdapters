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
import de.mrapp.android.adapter.util.Item;
import de.mrapp.android.adapter.util.Logger;

public class ListAdapterImplementation<DataType> extends
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

	protected ListAdapterImplementation(final Context context, final Logger logger,
			final int itemViewId, final View itemView,
			final List<Item<DataType>> items,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners,
			final Set<ListSortingListener<DataType>> sortingListeners,
			final ListDecorator<DataType> decorator) {
		super(context, logger, itemViewId, itemView, items, adapterListeners,
				enableStateListeners, sortingListeners);
		ensureNotNull(decorator, "The decorator may not be null");
		this.decorator = decorator;
	}

	public ListAdapterImplementation(final Context context, final Logger logger,
			final ListDecorator<DataType> decorator, final int itemViewId) {
		this(context, logger, itemViewId, null,
				new ArrayList<Item<DataType>>(),
				new LinkedHashSet<ListAdapterListener<DataType>>(),
				new LinkedHashSet<ListEnableStateListener<DataType>>(),
				new LinkedHashSet<ListSortingListener<DataType>>(), decorator);
	}

	public ListAdapterImplementation(final Context context, final Logger logger,
			final ListDecorator<DataType> decorator, final View itemView) {
		this(context, logger, -1, itemView, new ArrayList<Item<DataType>>(),
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
	public final ListAdapterImplementation<DataType> clone()
			throws CloneNotSupportedException {
		return new ListAdapterImplementation<DataType>(getContext(), getLogger().clone(),
				getItemViewId(), getItemView(), cloneItems(),
				getAdapterListeners(), getEnableStateListeners(),
				getSortingListeners(), decorator);
	}

}