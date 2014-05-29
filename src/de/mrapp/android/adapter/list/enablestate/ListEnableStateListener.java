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
package de.mrapp.android.adapter.list.enablestate;

/**
 * Defines the interface, all listeners, which should be notified, when an item
 * of a {@link ListAdapter} has been enabled or disabled, must implement.
 * 
 * @param <DataType>
 *            The type of the observed adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface ListEnableStateListener<DataType> {

	/**
	 * The method, which is invoked, when an item has been enabled.
	 * 
	 * @param item
	 *            The item, which has been enabled, as an instance of the
	 *            generic type DataType. The item may not be null
	 * @param index
	 *            The index of the item, which has been enabled, as an
	 *            {@link Integer} value
	 */
	void onItemEnabled(DataType item, int index);

	/**
	 * The method, which is invoked, when an item has been disabled.
	 * 
	 * @param item
	 *            The item, which has been enabled, as an instance of the
	 *            generic type DataType. The item may not be null
	 * @param index
	 *            The index of the item, which has been unselected, as an
	 *            {@link Integer} value
	 */
	void onItemDisabled(DataType item, int index);

}