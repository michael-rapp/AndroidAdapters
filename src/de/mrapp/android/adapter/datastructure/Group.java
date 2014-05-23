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
package de.mrapp.android.adapter.datastructure;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;
import de.mrapp.android.adapter.list.AbstractListAdapter;

/**
 * Represents a group, which categorizes multiple items of an adapter. It
 * contains the groups's data, an adapter, which manages the child items, a
 * selection state and may be enabled or disabled and expanded or collapsed.
 * 
 * @param <DataType>
 *            The type of the groups's data
 * @param <ChildDataType>
 *            The type of the child item's data
 * @param <ChildAdapterType>
 *            The type of the adapter, which manages the group's child items
 * 
 * @author Michael Rapp
 */
public class Group<DataType, ChildDataType, ChildAdapterType extends AbstractListAdapter<ChildDataType>>
		extends Item<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * True, if the group is expanded, false otherwise.
	 */
	private boolean expanded;

	/**
	 * The adapter, which manages the group's child items.
	 */
	private ChildAdapterType childAdapter;

	/**
	 * Creates a new group, which categorizes multiple items of an adapter.
	 * 
	 * @param data
	 *            The item's data, as an instance of the generic type DataType.
	 *            The data may not be null
	 * @param childAdapter
	 *            The adapter, which manages the group's child items, as an
	 *            instance of the generic type ChildAdapterType. The adapter may
	 *            not be null
	 */
	public Group(final DataType data, final ChildAdapterType childAdapter) {
		super(data);
		setChildAdapter(childAdapter);
		setExpanded(false);
	}

	/**
	 * Returns, whether the group is expanded, or not.
	 * 
	 * @return True, if the group is expanded, false otherwise
	 */
	public final boolean isExpanded() {
		return expanded;
	}

	/**
	 * Sets, whether the group is expanded, or not.
	 * 
	 * @param expanded
	 *            True, if the group should be expanded, false otherwise
	 */
	public final void setExpanded(final boolean expanded) {
		this.expanded = expanded;
	}

	/**
	 * Returns the adapter, which manages the group's child items.
	 * 
	 * @return The adapter, which manages the group's child items, as an
	 *         instance of the generic type ChildAdapterType. The adapter may
	 *         not be null
	 */
	public final ChildAdapterType getChildAdapter() {
		return childAdapter;
	}

	/**
	 * Sets the adapter, which manages the group's child items.
	 * 
	 * @param childAdapter
	 *            The adapter, which should be set, as an instance of the
	 *            generic type ChildAdapterType. The adapter may not be null
	 */
	public final void setChildAdapter(final ChildAdapterType childAdapter) {
		ensureNotNull(childAdapter, "The adapter may not be null");
		this.childAdapter = childAdapter;
	}

	@Override
	public final String toString() {
		return "Group [expanded=" + expanded + ", childAdapter=" + childAdapter
				+ ", data=" + getData() + ", selected=" + isSelected()
				+ ", enabled=" + isEnabled() + "]";
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + childAdapter.hashCode();
		result = prime * result + (expanded ? 1231 : 1237);
		return result;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group<?, ?, ?> other = (Group<?, ?, ?>) obj;
		if (!childAdapter.equals(other.childAdapter))
			return false;
		if (expanded != other.expanded)
			return false;
		return true;
	}

}