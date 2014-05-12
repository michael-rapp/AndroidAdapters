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

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import de.mrapp.android.adapter.sorting.Order;

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
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

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
	 * Creates and returns a deep copy of the list, which contains the selection
	 * states, which correspond to the adapter's items.
	 * 
	 * @return A deep copy of the list, which contains the selection states,
	 *         which correspond to the adapter's items, as an instance of the
	 *         type {@link List}. The list may not be null
	 */
	protected final List<Boolean> cloneSelections() {
		List<Boolean> clonedSelections = new ArrayList<Boolean>();

		for (Boolean selection : getSelections()) {
			clonedSelections.add(Boolean.valueOf(selection));
		}

		return clonedSelections;
	}

	/**
	 * Returns the set, which contains the listeners, which should be notified
	 * when the selection of an item of the adapter has been changed.
	 * 
	 * @return The set, which contains the listeners, which should be notified
	 *         when the selection of an item of the adapter has been changed, as
	 *         an instance of the type {@link Set}. The set may not be null
	 */
	protected final Set<ListSelectionListener<ItemType>> getSelectionListeners() {
		return selectionListeners;
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
	 * 
	 * @param selections
	 *            A list, which contains the selection states, which correspond
	 *            to the adapter's items, as an instance of the type
	 *            {@link List}. The list may not be null
	 * @param selectionListeners
	 *            A set, which contains the listeners, which should be notified
	 *            when the selection of an item of the adapter has been changed,
	 *            as an instance of the type {@link Set} The set may not be null
	 */
	protected AbstractListSelection(final List<Boolean> selections,
			final Set<ListSelectionListener<ItemType>> selectionListeners) {
		ensureNotNull(selections, "The selections may not be null");
		ensureNotNull(selectionListeners,
				"The selection listeners may not be null");
		this.selections = selections;
		this.selectionListeners = selectionListeners;
	}

	/**
	 * Creates a new selection, which is able to manage the selection states of
	 * the items of a {@link ListAdapter}.
	 */
	public AbstractListSelection() {
		this(new ArrayList<Boolean>(),
				new LinkedHashSet<ListSelectionListener<ItemType>>());
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
	public final List<Boolean> getSelections() {
		return selections;
	}

	@Override
	public final boolean isSelected(final int index) {
		return selections.get(index);
	}

	@Override
	public final List<Integer> getSelectedIndices() {
		List<Integer> selectedIndices = new ArrayList<Integer>();

		for (int i = 0; i < selections.size(); i++) {
			if (selections.get(i)) {
				selectedIndices.add(i);
			}
		}

		return selectedIndices;
	}

	@Override
	public final List<Integer> getUnselectedIndices() {
		List<Integer> unselectedIndices = new ArrayList<Integer>();

		for (int i = 0; i < selections.size(); i++) {
			if (!selections.get(i)) {
				unselectedIndices.add(i);
			}
		}

		return unselectedIndices;
	}

	@Override
	public final void onSorted(final List<ItemType> sortedList,
			final List<Boolean> sortedSelections, final Order order) {
		selections = sortedSelections;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + selectionListeners.hashCode();
		result = prime * result + selections.hashCode();
		return result;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractListSelection<?> other = (AbstractListSelection<?>) obj;
		if (!selectionListeners.equals(other.selectionListeners))
			return false;
		if (!selections.equals(other.selections))
			return false;
		return true;
	}

	@Override
	public abstract AbstractListSelection<ItemType> clone()
			throws CloneNotSupportedException;

}