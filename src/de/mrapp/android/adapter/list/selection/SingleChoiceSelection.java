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

	@Override
	public final void triggerSelection(final int index) {
		for (int i = 0; i < getSelections().size(); i++) {
			if (i == index && !getSelections().get(i)) {
				getSelections().set(i, true);
				notifyOnItemSelected(index);
			} else if (getSelections().get(i)) {
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
		// TODO: Handle, if removed item was selected + listener notification
	}

}