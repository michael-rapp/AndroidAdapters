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
package de.mrapp.android.adapter;

import android.content.Context;
import android.view.View;
import de.mrapp.android.adapter.decorator.AbstractDecorator;
import de.mrapp.android.adapter.list.selectable.SelectableListAdapter;

/**
 * An abstract base class for all decorators, which should allow to customize
 * the appearance of the view, which is used to visualize the items of a
 * {@link SelectableListAdapter}.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 0.1.0
 */
public abstract class SelectableListDecorator<DataType> extends AbstractDecorator {

	/**
	 * The method, which is invoked by an adapter to apply the decorator. It
	 * initializes the view holder pattern, which is provided by the decorator
	 * and then delegates the method call to the decorator's custom
	 * implementation of the method <code>onShowItem(...):void</code>.
	 * 
	 * @param context
	 *            The context, the adapter belongs to, as an instance of the
	 *            class {@link Context}. The context may not be null
	 * @param adapter
	 *            The adapter, whose items are visualized by the decorator, as
	 *            an instance of the type {@link SelectableListAdapter}. The
	 *            adapter may not be null
	 * @param view
	 *            The view, which is used to visualize the item, as an instance
	 *            of the class {@link View}. The view may not be null
	 * @param item
	 *            The item, which should be visualized, as an instance of the
	 *            generic type DataType. The item may not be null
	 * @param index
	 *            The index of the item, which should be visualized, as an
	 *            {@link Integer} value
	 * @param enabled
	 *            True, if the item, which should be visualized, is currently
	 *            enabled, false otherwise
	 * @param state
	 *            The current state of the item, which should be visualized, as
	 *            an {@link Integer} value
	 * @param filtered
	 *            True, if at least one filter is currently applied on the
	 *            adapter, false otherwise
	 * @param selected
	 *            True, if the item, which should be visualized, is currently
	 *            selected, false otherwise
	 */
	public final void applyDecorator(final Context context, final SelectableListAdapter<DataType> adapter,
			final View view, final DataType item, final int index, final boolean enabled, final int state,
			final boolean filtered, final boolean selected) {
		setCurrentParentView(view);
		int viewType = getViewType(adapter, item, index, enabled, state, filtered, selected);
		setCurrentViewType(viewType);
		adaptViewState(view, enabled, selected);
		onShowItem(context, adapter, view, item, viewType, index, enabled, state, filtered, selected);
	}

	/**
	 * The method which is invoked in order to retrieve the view type of a
	 * specific item, which is about to be visualized. This method has to be
	 * overridden by custom decorators which should be able to visualize some
	 * items optically divergent from others, returning a different integer
	 * constant for each type.
	 * 
	 * @param adapter
	 *            The adapter, whose items are visualized by the decorator, as
	 *            an instance of the type {@link ListAdapter}. The adapter may
	 *            not be null
	 * @param item
	 *            The item, which should be visualized, as an instance of the
	 *            generic type DataType. The item may not be null
	 * @param index
	 *            The index of the item, which should be visualized, as an
	 *            {@link Integer} value
	 * @param enabled
	 *            True, if the item, which should be visualized, is currently
	 *            enabled, false otherwise
	 * @param state
	 *            The current state of the item, which should be visualized, as
	 *            an {@link Integer} value
	 * @param filtered
	 *            True, if at least one filter is currently applied on the
	 *            adapter, false otherwise
	 * @return The view type of the item, which is about to be visualized, as an
	 *         {@link Integer} value
	 * @param selected
	 *            True, if the item, which should be visualized, is currently
	 *            selected, false otherwise
	 */
	public int getViewType(final ListAdapter<DataType> adapter, final DataType item, final int index,
			final boolean enabled, final int state, final boolean filtered, final boolean selected) {
		return 0;
	}

	/**
	 * Returns the number of view types, which are used by the decorator in
	 * order to visualize items. This method has to be overridden by custom
	 * decorators in order to return a value, which is consistent with the
	 * implementation of the <code>getViewType</code>-method.
	 * 
	 * @return The number of view types, which are used by the decorator in
	 *         order to visualize items, as an {@link Integer} value
	 */
	public int getViewTypeCount() {
		return 1;
	}

	/**
	 * The method which is invoked, when the view, which is used to visualize an
	 * item, should be shown, respectively when it should be refreshed. The
	 * purpose of this method is to customize the appearance of the view, which
	 * is used to visualize the appropriate item, depending on its state and
	 * whether it is currently enabled or not.
	 * 
	 * @param context
	 *            The context, the adapter belongs to, as an instance of the
	 *            class {@link Context}. The context may not be null
	 * @param adapter
	 *            The adapter, whose items are visualized by the decorator, as
	 *            an instance of the type {@link SelectableListAdapter}. The
	 *            adapter may not be null
	 * @param view
	 *            The view, which is used to visualize the item, as an instance
	 *            of the class {@link View}. The view may not be null
	 * @param item
	 *            The item, which should be visualized, as an instance of the
	 *            generic type DataType. The item may not be null
	 * @param viewType
	 *            The view type of the item, which should be visualized, as an
	 *            {@link Integer} value
	 * @param index
	 *            The index of the item, which should be visualized, as an
	 *            {@link Integer} value
	 * @param enabled
	 *            True, if the item, which should be visualized, is currently
	 *            enabled, false otherwise
	 * @param state
	 *            The current state of the item, which should be visualized, as
	 *            an {@link Integer} value
	 * @param filtered
	 *            True, if at least one filter is currently applied on the
	 *            adapter, false otherwise
	 * @param selected
	 *            True, if the item, which should be visualized, is currently
	 *            selected, false otherwise
	 */
	protected abstract void onShowItem(Context context, SelectableListAdapter<DataType> adapter, View view,
			DataType item, int viewType, int index, boolean enabled, int state, boolean filtered, boolean selected);

}