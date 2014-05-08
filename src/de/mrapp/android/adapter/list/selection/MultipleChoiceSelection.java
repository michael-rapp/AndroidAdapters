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
import java.util.Set;

/**
 * Manages the selection states of the items of a {@link ListAdapter} in a way,
 * that multiple items can be selected at once. Triggering the selection of an
 * already selected item causes it to become unselected.
 * 
 * @param <ItemType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public class MultipleChoiceSelection<ItemType> extends
		AbstractListSelection<ItemType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new selection, which is able to manage the selection states of
	 * the items of a {@link ListAdapter} in a way, that multiple items can be
	 * selected at once.
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
	protected MultipleChoiceSelection(final List<Boolean> selections,
			final Set<ListSelectionListener<ItemType>> selectionListeners) {
		super(selections, selectionListeners);
	}

	/**
	 * Creates a new selection, which is able to manage the selection states of
	 * the items of a {@link ListAdapter} in a way, that multiple items can be
	 * selected at once.
	 */
	public MultipleChoiceSelection() {
		super();
	}

	@Override
	public final void triggerSelection(final int index) {
		if (getSelections().get(index)) {
			getSelections().set(index, false);
			notifyOnItemUnselected(index);
		} else {
			getSelections().set(index, true);
			notifyOnItemSelected(index);
		}

	}

	@Override
	public final void onItemAdded(final ItemType item, final int index) {
		getSelections().add(index, false);
	}

	@Override
	public final void onItemRemoved(final ItemType item, final int index) {
		getSelections().remove(index);
	}

	@Override
	public final String toString() {
		return "MultipleChoiceSelection [selections=" + getSelections()
				+ ", selectionListeners=" + getSelectionListeners() + "]";
	}

	@Override
	public final MultipleChoiceSelection<ItemType> clone() {
		return new MultipleChoiceSelection<ItemType>(cloneSelections(),
				getSelectionListeners());
	}

}