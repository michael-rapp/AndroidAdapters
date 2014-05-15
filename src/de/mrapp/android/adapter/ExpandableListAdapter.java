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

/**
 * Defines the interface, an adapter, whose underlying data is managed as a
 * two-dimensional list of items, which are categorized by groups, must
 * implement. Such an adapter is meant to provide the underlying data for
 * visualization using a {@link ExpandableListView} widget.
 * 
 * @param <GroupType>
 *            The type of the adapter, which manages the groups
 * @param <ChildType>
 *            The type of the adapter, which manages the children
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface ExpandableListAdapter<GroupType extends ListAdapter<ListAdapter<ChildType>>, ChildType>
		extends Adapter {

	@Override
	ExpandableListAdapter<GroupType, ChildType> clone()
			throws CloneNotSupportedException;

}