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

	/**
	 * Returns the index of the first selected item or -1, if no item is
	 * currently selected.
	 * 
	 * @return The index of the first selected item, as an {@link Integer} value
	 *         or -1, if no item is currently selected. The index must be
	 *         between 0 and the value of the adapter's <code>size():int</code>
	 *         method - 1
	 */
	int getSelectedIndex();

	/**
	 * Returns a list, which contains the indices of all items, which are
	 * currently selected.
	 * 
	 * @return A list, which contains the indices of all items, which are
	 *         currently selected, as an instance of the type {@link List} or an
	 *         empty list, if no item is currently selected
	 */
	List<Integer> getSelectedIndices();

	/**
	 * Returns a list, which contains the indices of all items, which are
	 * currently not selected.
	 * 
	 * @return A list, which contains the indices of all items, which are
	 *         currently not selected, as an instance of the type {@link List}
	 *         or an empty list, if all items are currently selected
	 */
	List<Integer> getUnselectedIndices();

}