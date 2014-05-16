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
package de.mrapp.android.adapter;
import de.mrapp.android.adapter.list.AbstractListAdapter;
import de.mrapp.android.adapter.util.Group;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a
 * two-dimensional list of items, which are categorized by groups, must
 * implement. Such an adapter is meant to provide the underlying data for
 * visualization using a {@link ExpandableListView} widget.
 * 
 * @param <GroupDataType>> The type of the group's underlying data
 * @param <ChildDataType>
 *            The type of the children's underlying data
 * @param <GroupAdapterType>
 *            The type of the adapter, which manages the groups
 * @param <ChildAdapterType>> The type of the adapter, which manages the
 *        children
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface ExpandableListAdapter<GroupDataType, ChildDataType, GroupAdapterType extends AbstractListAdapter<Group<GroupDataType, ChildDataType, ChildAdapterType>>, ChildAdapterType extends AbstractListAdapter<ChildDataType>>
		extends Adapter {

	@Override
	ExpandableListAdapter<GroupDataType, ChildDataType, GroupAdapterType, ChildAdapterType> clone()
			throws CloneNotSupportedException;

}