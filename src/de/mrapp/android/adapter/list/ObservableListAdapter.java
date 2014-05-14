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

/**
 * Defines the interface, all classes, which should allow to register listeners,
 * which are notified when the underlying data of a {@link ListAdapter} has been
 * modified, must implement.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface ObservableListAdapter<DataType> {

	/**
	 * Adds a new listener, which should be notified when the adapter's
	 * underlying data has been modified.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            class {@link ListAdapterListener}. The listener may not be
	 *            null
	 */
	void addAdapterListener(ListAdapterListener<DataType> listener);

	/**
	 * Removes a specific listener, which should not be notified when the
	 * adapter's underlying data has been modified, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            class {@link ListAdapterListener}. The listener may not be
	 *            null
	 */
	void removeAdapterListener(ListAdapterListener<DataType> listener);

	/**
	 * Adds a new listener, which should be notified when the adapter's
	 * underlying data has been sorted.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            class {@link ListSortingListener}. The listener may not be
	 *            null
	 */
	void addSortingListner(ListSortingListener<DataType> listener);

	/**
	 * Removes a specific listener, which should not be notified when the
	 * adapter's underlying data has been modified, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            class {@link ListSortingListener}. The listener may not be
	 *            null
	 */
	void removeSortingListener(ListSortingListener<DataType> listener);

}