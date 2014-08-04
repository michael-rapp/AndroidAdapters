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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import de.mrapp.android.adapter.ListDecorator;
import de.mrapp.android.adapter.datastructure.AppliedFilter;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.filterable.AbstractFilterableListAdapter;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
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
public class ListAdapterImplementation<DataType> extends
		AbstractFilterableListAdapter<DataType, ListDecorator<DataType>> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected final void applyDecorator(final Context context, final View view,
			final int index) {
		getDecorator().onShowItem(context, view, getItem(index), index,
				isEnabled(index), getItemState(index));
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary items.
	 * 
	 * @param context
	 *            The context, the adapter should belong to, as an instance of
	 *            the class {@link Context}. The context may not be null
	 * @param inflater
	 *            The inflater, which should be used to inflate the views, which
	 *            are used to visualize the adapter's items, as an instance of
	 *            the type {@link Inflater}. The inflater may not be null
	 * @param decorator
	 *            The decorator, which should be used to customize the
	 *            appearance of the views, which are used to visualize the items
	 *            of the adapter, as an instance of the generic type
	 *            DecoratorType. The decorator may not be null
	 * @param items
	 *            A list, which contains the the adapter's items, or an empty
	 *            list, if the adapter should not contain any items
	 * @param allowDuplicates
	 *            True, if duplicate items should be allowed, false otherwise
	 * @param adapterListeners
	 *            A set, which contains the listeners, which should be notified
	 *            when the adapter's underlying data has been modified or an
	 *            empty set, if no listeners should be notified
	 * @param enableStateListeners
	 *            A set, which contains the listeners, which should be notified
	 *            when an item has been disabled or enabled or an empty set, if
	 *            no listeners should be notified
	 * @param numberOfItemStates
	 *            The number of states, the adapter's items may have, as an
	 *            {@link Integer} value. The value must be at least 1
	 * @param triggerItemStateOnClick
	 *            True, if the state of an item should be triggered, when it is
	 *            clicked by the user, false otherwise
	 * @param itemStateListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the state of an item has been changed or an empty set, if
	 *            no listeners should be notified
	 * @param sortingListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been sorted or an empty
	 *            set, if no listeners should be notified
	 * @param filterListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been filtered or an
	 *            empty set, if no listeners should be notified
	 * @param appliedFilters
	 *            A list, which contains the filters, which should be used to
	 *            filter the adapter's underlying data or an empty list, if the
	 *            adapter's underlying data should not be filtered
	 */
	protected ListAdapterImplementation(final Context context,
			final Inflater inflater, final ListDecorator<DataType> decorator,
			final List<Item<DataType>> items, final boolean allowDuplicates,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners,
			final int numberOfItemStates,
			final boolean triggerItemStateOnClick,
			final Set<ListItemStateListener<DataType>> itemStateListeners,
			final Set<ListSortingListener<DataType>> sortingListeners,
			final Set<ListFilterListener<DataType>> filterListeners,
			final Set<AppliedFilter<DataType>> appliedFilters) {
		super(context, inflater, decorator, items, allowDuplicates,
				adapterListeners, enableStateListeners, numberOfItemStates,
				triggerItemStateOnClick, itemStateListeners, sortingListeners,
				filterListeners, appliedFilters);
	}

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
	 * @param decorator
	 *            The decorator, which should be used to customize the
	 *            appearance of the views, which are used to visualize the items
	 *            of the adapter, as an instance of the type
	 *            {@link ListDecorator}. The decorator may not be null
	 */
	public ListAdapterImplementation(final Context context,
			final Inflater inflater, final ListDecorator<DataType> decorator) {
		this(context, inflater, decorator, new ArrayList<Item<DataType>>(),
				false, new LinkedHashSet<ListAdapterListener<DataType>>(),
				new LinkedHashSet<ListEnableStateListener<DataType>>(), 1,
				false, new LinkedHashSet<ListItemStateListener<DataType>>(),
				new LinkedHashSet<ListSortingListener<DataType>>(),
				new LinkedHashSet<ListFilterListener<DataType>>(),
				new LinkedHashSet<AppliedFilter<DataType>>());
	}

	@Override
	public final String toString() {
		return "ListAdapter [sortingListeners=" + getSortingListeners()
				+ ", itemStateListeners=" + getItemStateListeners()
				+ ", numberOfItemStates=" + getNumberOfItemStates()
				+ ", triggerItemStateOnClick=" + isItemStateTriggeredOnClick()
				+ ", enableStateListeners=" + getEnableStateListeners()
				+ ", items=" + getItems() + ", adapterListeners="
				+ getAdapterListeners() + ", allowDuplicates="
				+ areDuplicatesAllowed() + ", filterListeners="
				+ getFilterListeners() + ", appliedFilters="
				+ getAppliedFilters() + "]";
	}

	@Override
	public final ListAdapterImplementation<DataType> clone()
			throws CloneNotSupportedException {
		return new ListAdapterImplementation<DataType>(getContext(),
				getInflater(), getDecorator(), cloneItems(),
				areDuplicatesAllowed(), getAdapterListeners(),
				getEnableStateListeners(), getNumberOfItemStates(),
				isItemStateTriggeredOnClick(), getItemStateListeners(),
				getSortingListeners(), getFilterListeners(),
				cloneAppliedFilters());
	}

}