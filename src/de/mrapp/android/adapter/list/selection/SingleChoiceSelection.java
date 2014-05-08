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
 * that only one item can be selected at once.
 * 
 * @param <ItemType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public class SingleChoiceSelection<ItemType> extends
		AbstractListSelection<ItemType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new selection, which is able to manage the selection states of
	 * the items of a {@link ListAdapter} in a way, that only one item can be
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
	protected SingleChoiceSelection(final List<Boolean> selections,
			final Set<ListSelectionListener<ItemType>> selectionListeners) {
		super(selections, selectionListeners);
	}

	/**
	 * Creates a new selection, which is able to manage the selection states of
	 * the items of a {@link ListAdapter} in a way, that only one iteme can be
	 * selected at once.
	 */
	public SingleChoiceSelection() {
		super();
	}

	@Override
	public final void triggerSelection(final int index) {
		for (int i = 0; i < getSelections().size(); i++) {
			if (i == index && !getSelections().get(i)) {
				getSelections().set(i, true);
				notifyOnItemSelected(index);
			} else if (i != index && getSelections().get(i)) {
				getSelections().set(i, false);
				notifyOnItemUnselected(index);
			}
		}
	}

	@Override
	public final void onItemAdded(final ItemType item, final int index) {
		if (getSelections().isEmpty()) {
			getSelections().add(index, true);
			notifyOnItemSelected(index);
		} else {
			getSelections().add(index, false);
		}
	}

	@Override
	public final void onItemRemoved(final ItemType item, final int index) {
		getSelections().remove(index);

		if (getSelections().size() >= index + 1) {
			triggerSelection(index);
			notifyOnItemSelected(index);
		} else if (getSelections().size() >= index) {
			triggerSelection(index - 1);
			notifyOnItemSelected(index - 1);
		}
	}

	@Override
	public final String toString() {
		return "SingleChoiceSelection [selections=" + getSelections()
				+ ", selectionListeners=" + getSelectionListeners() + "]";
	}

	@Override
	public final SingleChoiceSelection<ItemType> clone()
			throws CloneNotSupportedException {
		return new SingleChoiceSelection<ItemType>(cloneSelections(),
				getSelectionListeners());
	}

}