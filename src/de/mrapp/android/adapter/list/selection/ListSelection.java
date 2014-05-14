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
package de.mrapp.android.adapter.list.selection;

import java.util.List;

import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.ListSortingListener;
import de.mrapp.android.adapter.util.Entity;

/**
 * Defines the interface, all classes, which should be able to manage the
 * selection states of the items of a {@link ListAdapter}, must implement.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface ListSelection<DataType> extends SelectableList,
		ListAdapterListener<DataType>, ListSortingListener<DataType>,
		ObservableListSelection<DataType>, Entity {

	/**
	 * Returns the list, which contains the selection states, which correspond
	 * to the adapter's items.
	 * 
	 * @return The list, which contains the selection states, which correspond
	 *         to the adapter's items, as an instance of the type {@link List}.
	 *         The list may not be null
	 */
	List<Boolean> getSelections();

	@Override
	ListSelection<DataType> clone() throws CloneNotSupportedException;

}