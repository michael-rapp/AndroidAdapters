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

import de.mrapp.android.adapter.list.ListAdapterImplementation;
import android.content.Context;
import android.view.View;

/**
 * Defines the interface, all classes must implement, which should allow to
 * customize the appearance of the widgets, which belong to the view, which is
 * used to visualize an item of a {@link ListAdapterImplementation}.
 * 
 * @author Michael Rapp
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 */
public interface ListDecorator<DataType> {

	/**
	 * The method which is invoked, when an item should be visualized,
	 * respectively when its visualization should be refreshed. This method is
	 * meant to be used to customize the appearance of the widgets, which belong
	 * to the view, which is used to visualize the item.
	 * 
	 * @param context
	 *            The context, the adapter belongs to, as an instance of the
	 *            class {@link Context}. The context may not be null
	 * @param view
	 *            The view, which is used to visualize the item, as an instance
	 *            of the class {@link View}. The view may not be null
	 * @param item
	 *            The item, which is should be visualized, as an instance of the
	 *            generic type DataType. The item may not be null
	 * @param enabled
	 *            True, if the item, which should be visualized, is enabled, or
	 *            not
	 */
	void onCreateItem(Context context, View view, DataType item, boolean enabled);

}