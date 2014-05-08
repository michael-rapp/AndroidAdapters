/*
 * AndroidAdapters Copyright 2014 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>. 
 */
package de.mrapp.android.adapter.list.selection;

import de.mrapp.android.adapter.list.ListAdapterListener;

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
public interface ListSelection<ItemType> extends ListAdapterListener<ItemType> {

	/**
	 * Triggers the selection of the item, which belongs to a specific index.
	 * The impact of this method on the selection state of the item and on the
	 * selection states of all other items depends on the way the selection is
	 * meant to work.
	 * 
	 * @param index
	 *            The index of the item, whose selection should be triggered, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the adapter's <code>size():int</code> method - 1
	 */
	void triggerSelection(int index);

	/**
	 * Returns, whether a the item, which belongs to a specific index, is
	 * currently selected, or not.
	 * 
	 * @param index
	 *            The index of the item, whose selection state should be
	 *            retrieved, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the adapter's
	 *            <code>size():int</code> method - 1
	 * @return True, if the item is currently selected, false otherwise
	 */
	boolean isSelected(int index);

	/**
	 * Adds a new listener, which should be notified when the selection of an
	 * item has been changed.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            type {@link ListSelectionListener}. The listener may not be
	 *            null
	 */
	void addSelectionListener(ListSelectionListener<ItemType> listener);

	/**
	 * Removes a specific listener, which should not be notified when the
	 * selection of an item has been changed, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            type {@link ListSelectionListener}. The listener may not be
	 *            null
	 */
	void removeSelectionListener(ListSelectionListener<ItemType> listener);

}