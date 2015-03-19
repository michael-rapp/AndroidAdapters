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
package de.mrapp.android.adapter.datastructure.group;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.datastructure.item.Item;

/**
 * A data structure, which categorizes multiple items of an adapter. A group has
 * all properties of an item, but additionally it does contain an adapter, which
 * manages the group's child items.
 * 
 * @param <GroupType>
 *            The type of the groups's data
 * @param <ChildType>
 *            The type of the child items' data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public class Group<GroupType, ChildType> extends Item<GroupType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The adapter, which manages the group's child items.
	 */
	private transient MultipleChoiceListAdapter<ChildType> childAdapter;

	/**
	 * Creates a new data structure, which categorizes multiple items of an
	 * adapter.
	 * 
	 * @param data
	 *            The group's data, as an instance of the generic type
	 *            GroupType. The data may not be null
	 * @param childAdapter
	 *            The adapter, which manages the group's child items, as an
	 *            instance of the type {@link MultipleChoiceListAdapter}. The
	 *            adapter may not be null
	 */
	public Group(final GroupType data,
			final MultipleChoiceListAdapter<ChildType> childAdapter) {
		super(data);
		setChildAdapter(childAdapter);
	}

	/**
	 * Returns the adapter, which manages the group's child items.
	 * 
	 * @return The adapter, which manages the group's child items, as an
	 *         instance of the type {@link MultipleChoiceListAdapter}. The
	 *         adapter may not be null
	 */
	public final MultipleChoiceListAdapter<ChildType> getChildAdapter() {
		return childAdapter;
	}

	/**
	 * Sets the adapter, which manages the group's child items.
	 * 
	 * @param childAdapter
	 *            The adapter, which should be set, as an instance of the type
	 *            {@link MultipleChoiceListAdapter}. The adapter may not be null
	 */
	public final void setChildAdapter(
			final MultipleChoiceListAdapter<ChildType> childAdapter) {
		ensureNotNull(childAdapter, "The child adapter may not be null");
		this.childAdapter = childAdapter;
	}

	@Override
	@SuppressWarnings("unchecked")
	public final Group<GroupType, ChildType> clone()
			throws CloneNotSupportedException {
		try {
			GroupType clonedData = (GroupType) getData().getClass()
					.getMethod("clone").invoke(getData());
			MultipleChoiceListAdapter<ChildType> clonedChildAdapter = childAdapter
					.clone();
			Group<GroupType, ChildType> clonedGroup = new Group<GroupType, ChildType>(
					clonedData, clonedChildAdapter);
			clonedGroup.setSelected(isSelected());
			clonedGroup.setEnabled(isEnabled());
			clonedGroup.setState(getState());
			return clonedGroup;
		} catch (Exception e) {
			throw new CloneNotSupportedException();
		}
	}

	@Override
	public final String toString() {
		return "Group [data=" + getData() + ", selected=" + isSelected()
				+ ", enabled=" + isEnabled() + ", state=" + getState() + "]";
	}

}