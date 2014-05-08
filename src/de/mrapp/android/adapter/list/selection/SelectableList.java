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

/**
 * Defines the interface, which must be implemented by all classes, which should
 * allow to select items of a list, respectively retrieve the selection states
 * of the items.
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface SelectableList {

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

}