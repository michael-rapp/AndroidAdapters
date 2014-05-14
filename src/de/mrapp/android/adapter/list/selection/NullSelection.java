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
 * that no item can be selected.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
// TODO: Add toString-method
public class NullSelection<DataType> extends AbstractListSelection<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new selection, which is able to manage the selection states of
	 * the items of a {@link ListAdapter} in a way, that no item can be
	 * selected.
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
	protected NullSelection(final List<Boolean> selections,
			final Set<ListSelectionListener<DataType>> selectionListeners) {
		super(selections, selectionListeners);
	}

	/**
	 * Creates a new selection, which is able to manage the selection states of
	 * the items of a {@link ListAdapter} in a way, that no can be selected.
	 */
	public NullSelection() {
		super();
	}

	@Override
	public final void triggerSelection(final int index) {
		return;
	}

	@Override
	public final void onItemAdded(final DataType item, final int index) {
		getSelections().add(index, false);
	}

	@Override
	public final void onItemRemoved(final DataType item, final int index) {
		getSelections().remove(index);
	}

	@Override
	public final AbstractListSelection<DataType> clone()
			throws CloneNotSupportedException {
		return new NullSelection<DataType>(cloneSelections(),
				getSelectionListeners());
	}

}
