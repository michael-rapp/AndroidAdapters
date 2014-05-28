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

/**
 * Defines the interface, a class, which should allow to customize the
 * appearance of the view, which is used to visualize the items of a
 * {@link ListAdapter}, must implement.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface ListDecorator<DataType> {

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
	 */
	void onShowItem(Context context, View view, DataType item, int index,
			boolean enabled, int state);

}