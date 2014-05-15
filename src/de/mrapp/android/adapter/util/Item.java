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
package de.mrapp.android.adapter.util;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;
import de.mrapp.android.adapter.SortingNotSupportedException;

/**
 * Represents an item of an adapter. It contains the item's data, a selection
 * state and may be enabled or disabled.
 * 
 * @param <DataType>
 *            The type of the item's data
 * 
 * @author Michael Rapp
 */
public class Item<DataType> implements Entity, Comparable<Item<DataType>> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The item's data.
	 */
	private DataType data;

	/**
	 * The item's selection state.
	 */
	private int selectionState;

	/**
	 * True, if the item is enabled, false otherwise.
	 */
	private boolean enabled;

	/**
	 * Creates a new item.
	 * 
	 * @param data
	 *            The item's data, as an instance of the generic type DataType.
	 *            The data may not be null
	 */
	public Item(final DataType data) {
		setData(data);
		setEnabled(true);
		setSelectionState(0);
	}

	/**
	 * Returns the item's data.
	 * 
	 * @return The item's data, as an instance of the generic type DataType
	 */
	public final DataType getData() {
		return data;
	}

	/**
	 * Sets the item's data.
	 * 
	 * @param data
	 *            The data, which should be set, as an instance of the generic
	 *            type DataType. The data may not be null
	 */
	public final void setData(final DataType data) {
		ensureNotNull(data, "The data may not be null");
		this.data = data;
	}

	/**
	 * Returns the item's selection state.
	 * 
	 * @return The item's selection state as an {@link Integer} value
	 */
	public final int getSelectionState() {
		return selectionState;
	}

	/**
	 * Sets the item's selection state.
	 * 
	 * @param selectionState
	 *            The selection state, which should be set, as a {@link Integer}
	 *            value
	 */
	public final void setSelectionState(final int selectionState) {
		this.selectionState = selectionState;
	}

	/**
	 * Returns, whether the item is enabled, or not.
	 * 
	 * @return True, if the item is enabled false otherwise
	 */
	public final boolean isEnabled() {
		return enabled;
	}

	/**
	 * Sets, whether the item is be enabled, or not.
	 * 
	 * @param enabled
	 *            True, if the item should be enabled, false otherwise
	 */
	public final void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	@SuppressWarnings("unchecked")
	@Override
	public final int compareTo(final Item<DataType> another) {
		try {
			Comparable<DataType> comparable = (Comparable<DataType>) getData();
			return comparable.compareTo(another.getData());
		} catch (ClassCastException e) {
			throw new SortingNotSupportedException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public final Item<DataType> clone() throws CloneNotSupportedException {
		try {
			DataType clonedData = (DataType) data.getClass().getMethod("clone")
					.invoke(data);
			Item<DataType> clonedItem = new Item<DataType>(clonedData);
			clonedItem.setSelectionState(getSelectionState());
			clonedItem.setEnabled(isEnabled());
			return clonedItem;
		} catch (Exception e) {
			throw new CloneNotSupportedException();
		}
	}

	@Override
	public String toString() {
		return "Item [data=" + data + ", selectionState=" + selectionState
				+ ", enabled=" + enabled + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + data.hashCode();
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + selectionState;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item<?> other = (Item<?>) obj;
		if (!data.equals(other.data))
			return false;
		if (enabled != other.enabled)
			return false;
		if (selectionState != other.selectionState)
			return false;
		return true;
	}

}