/*
 * AndroidAdapters Copyright 2014 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>. 
 */
package de.mrapp.android.adapter.list;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import de.mrapp.android.adapter.ListDecorator;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.sortable.AbstractSortableListAdapter;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;

/**
 * An adapter, whose underlying data is managed as a list of arbitrary items.
 * Such an adapter's purpose is to provide the underlying data for visualization
 * using a {@link ListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
// TODO: Add toString-method
public class ListAdapterImplementation<DataType> extends
		AbstractSortableListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The decorator, which is used to customize the appearance of the views,
	 * which are used to visualize the items of the adapter.
	 */
	private final transient ListDecorator<DataType> decorator;

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary items.
	 * 
	 * @param context
	 *            The context, the adapter belongs to, as an instance of the
	 *            class {@link Context}. The context may not be null
	 * @param inflater
	 *            The inflater, which should be used to inflate the views, which
	 *            are used to visualize the adapter's items, as an instance of
	 *            the type {@link Inflater}. The inflater may not be null
	 * @param items
	 *            A list, which contains the the adapter's underlying data, as
	 *            an instance of the type {@link List} or an empty list, if the
	 *            adapter should not contain any data
	 * @param allowDuplicates
	 *            True, if duplicate items should be allowed, false otherwise
	 * @param adapterListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been modified, as an
	 *            instance of the type {@link Set} or an empty set, if no
	 *            listeners should be notified
	 * @param decorator
	 *            The decorator, which should be used to customize the
	 *            appearance of the views, which are used to visualize the items
	 *            of the adapter, as an instance of the type
	 *            {@link ListDecorator}. The decorator may not be null
	 */
	protected ListAdapterImplementation(final Context context,
			final Inflater inflater, final List<Item<DataType>> items,
			final boolean allowDuplicates,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners,
			final int numberOfItemStates,
			final boolean triggerItemStateOnClick,
			final Set<ListSortingListener<DataType>> sortingListeners,
			final ListDecorator<DataType> decorator) {
		super(context, inflater, items, allowDuplicates, adapterListeners,
				enableStateListeners, numberOfItemStates,
				triggerItemStateOnClick, sortingListeners);
		ensureNotNull(decorator, "The decorator may not be null");
		this.decorator = decorator;
	}

	@Override
	public final View getView(final int index, final View convertView,
			final ViewGroup parent) {
		View view = getInflater().inflate(getContext(), parent);
		decorator.onShowItem(getContext(), view, getItem(index), index,
				isEnabled(index), getItemState(index));
		return view;
	}

	@Override
	public final ListAdapterImplementation<DataType> clone()
			throws CloneNotSupportedException {
		return new ListAdapterImplementation<DataType>(getContext(),
				getInflater(), cloneItems(), areDuplicatesAllowed(),
				getAdapterListeners(), getEnableStateListeners(),
				getNumberOfItemStates(), isItemStateTriggeredOnClick(),
				getSortingListeners(), decorator);
	}

}