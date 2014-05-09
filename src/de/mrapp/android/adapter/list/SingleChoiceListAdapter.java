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

import java.util.List;
import java.util.Set;

import android.content.Context;
import de.mrapp.android.adapter.ListDecorator;
import de.mrapp.android.adapter.list.selection.ListSelection;
import de.mrapp.android.adapter.list.selection.SingleChoiceSelection;

/**
 * An adapter, whose underlying data is managed as a list of arbitrary items, of
 * whom only one item can be selected at once. Such an adapter is meant to
 * provide the underlying data for visualization using a @link ListView} widget.
 * 
 * @param <ItemType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public class SingleChoiceListAdapter<ItemType> extends
		AbstractListAdapter<ItemType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary items, of whom only one item can be selected at once.
	 * 
	 * @param context
	 *            The context, the adapter should belong to, as an instance of
	 *            the class {@link Context}. The context may not be null
	 * @param viewId
	 *            The id of the view, which should be used to visualize each
	 *            item of the adapter, as an {@link Integer} value. The id must
	 *            specify a valid view from within the \res folder
	 * @param decorator
	 *            The decorator, which should be used to customize the
	 *            appearance of the widgets, which belong to the view, which is
	 *            used to visualize the items of the adapter, as an instance of
	 *            the type {@link ListDecorator}. The decorator may not be null
	 * @param selection
	 *            The selection, which should be used to manage the selection
	 *            states of the adapter's items, as an instance of the type
	 *            {@link ListSelection}. The selection may not be null
	 * @param items
	 *            A list, which contains the the adapter's items, or an empty
	 *            list, if the adapter should not contain any items
	 * @param adapterListeners
	 *            A set, which contains the listeners, which should be notified
	 *            when the adapter's underlying data has been modified or an
	 *            empty set, if no listeners should be notified
	 * @param triggerSelectionOnClick
	 *            True, if the selection of an item should be triggered, when
	 *            the item is clicked, false otherwise
	 */
	protected SingleChoiceListAdapter(final Context context, final int viewId,
			final ListDecorator<ItemType> decorator,
			final ListSelection<ItemType> selection,
			final List<ItemType> items,
			final Set<ListAdapterListener<ItemType>> adapterListeners,
			final boolean triggerSelectionOnClick) {
		super(context, viewId, decorator, selection, items, adapterListeners,
				triggerSelectionOnClick);
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary items, of whom only one item can be selected at once.
	 * 
	 * @param context
	 *            The context, the adapter should belong to, as an instance of
	 *            the class {@link Context}. The context may not be null
	 * @param viewId
	 *            The id of the view, which should be used to visualize each
	 *            item of the adapter, as an {@link Integer} value. The id must
	 *            specify a valid view from within the \res folder
	 * @param decorator
	 *            The decorator, which should be used to customize the
	 *            appearance of the widgets, which belong to the view, which is
	 *            used to visualize the items of the adapter, as an instance of
	 *            the type {@link ListDecorator}. The decorator may not be null
	 */
	public SingleChoiceListAdapter(final Context context, final int viewId,
			final ListDecorator<ItemType> decorator) {
		super(context, viewId, decorator, new SingleChoiceSelection<ItemType>());
	}

	@Override
	public final SingleChoiceListAdapter<ItemType> clone()
			throws CloneNotSupportedException {
		return new SingleChoiceListAdapter<ItemType>(getContext(), getViewId(),
				getDecorator(), cloneSelection(), cloneItems(),
				getAdapterListeners(), isSelectionTriggeredOnClick());
	}
}