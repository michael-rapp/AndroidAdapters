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

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * An abstract base class for all classes, which should be able to manage the
 * selection states of the items of a {@link ListAdapter}.
 * 
 * @param <ItemType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractListSelection<ItemType> implements
		ListSelection<ItemType> {

	/**
	 * A list, which contains the selection states, which correspond to the
	 * adapter's items. The reference between the selection states and the items
	 * is established by their indices.
	 */
	private List<Boolean> selections;

	/**
	 * A set, which contains the listeners, which should be notified when the
	 * selection of an item of the adapter has been changed.
	 */
	private Set<ListSelectionListener<ItemType>> selectionListeners;

	/**
	 * Returns the list, which contains the selection states, which correspond
	 * to the adapter's items.
	 * 
	 * @return The list, which contains the selection states, which correspond
	 *         to the adapter's items, as an instance of the type {@link List}.
	 *         The list may not be null
	 */
	protected final List<Boolean> getSelections() {
		return selections;
	}

	/**
	 * Notifies all listeners, which have been registered to be notified when
	 * the selection of an item of the adapter has been changed, about an item,
	 * which has been selected.
	 * 
	 * @param index
	 *            The index of the item, which has been selected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>size():int</code> - 1
	 */
	protected final void notifyOnItemSelected(final int index) {
		for (ListSelectionListener<ItemType> listener : selectionListeners) {
			listener.onItemSelected(index);
		}
	}

	/**
	 * Notifies all listeners, which have been registered to be notified when
	 * the selection of an item of the adapter has been changed, about an item,
	 * which has been unselected.
	 * 
	 * @param index
	 *            The index of the item, which has been unselected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>size():int</code> - 1
	 */
	protected final void notifyOnItemUnselected(final int index) {
		for (ListSelectionListener<ItemType> listener : selectionListeners) {
			listener.onItemUnselected(index);
		}
	}

	/**
	 * Creates a new selection, which is able to manage the selection states of
	 * the items of a {@link ListAdapter}.
	 */
	public AbstractListSelection() {
		this.selections = new ArrayList<Boolean>();
		this.selectionListeners = new LinkedHashSet<ListSelectionListener<ItemType>>();
	}

	/**
	 * Adds a new listener, which should be notified when the selection of an
	 * item has been changed.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            type {@link ListSelectionListener}. The listener may not be
	 *            null
	 */
	public final void addSelectionListener(
			final ListSelectionListener<ItemType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		selectionListeners.add(listener);
	}

	/**
	 * Removes a specific listener, which should not be notified when the
	 * selection of an item has been changed, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            type {@link ListSelectionListener}. The listener may not be
	 *            null
	 */
	public final void removeSelectionListener(
			final ListSelectionListener<ItemType> listener) {
		selectionListeners.remove(listener);
	}

	@Override
	public final boolean isSelected(final int index) {
		return selections.get(index);
	}

}