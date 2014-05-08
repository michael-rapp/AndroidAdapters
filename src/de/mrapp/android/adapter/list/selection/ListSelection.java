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

import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.util.Entity;

/**
 * Defines the interface, all classes, which should be able to manage the
 * selection states of the items of a {@link ListAdapter}, must implement.
 * 
 * @param <ItemType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface ListSelection<ItemType> extends SelectableList,
		ListAdapterListener<ItemType>, ObservableListSelection<ItemType>,
		Entity {

	@Override
	ListSelection<ItemType> clone() throws CloneNotSupportedException;

}