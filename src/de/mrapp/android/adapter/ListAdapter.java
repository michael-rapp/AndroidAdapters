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

import java.util.List;

import android.content.Context;
import de.mrapp.android.adapter.list.ObservableListAdapter;
import de.mrapp.android.adapter.list.selection.ObservableListSelection;
import de.mrapp.android.adapter.list.selection.SelectableList;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list
 * of arbitrary items, must implement. Such an adapter is meant to provide the
 * underlying data for visualization using a {@link ListView} widget.
 * 
 * @param <ItemType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface ListAdapter<ItemType> extends Adapter<ItemType>,
		List<ItemType>, SelectableList, ObservableListSelection<ItemType>,
		ObservableListAdapter<ItemType>, android.widget.ListAdapter {

	/**
	 * Returns the context, the adapter belongs to.
	 * 
	 * @return The context, the adapter belongs to, as an instance of the class
	 *         {@link Context}
	 */
	Context getContext();

	@Override
	ListAdapter<ItemType> clone() throws CloneNotSupportedException;

}