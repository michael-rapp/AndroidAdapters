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
package de.mrapp.android.adapter.expandablelist;

import java.util.LinkedHashSet;
import java.util.Set;

import android.content.Context;
import de.mrapp.android.adapter.ExpandableListDecorator;
import de.mrapp.android.adapter.inflater.Inflater;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a list of arbitrary group and child items. Such an adapter's purpose is to
 * provide the underlying data for visualization using a
 * {@link ExpandableListView} widget.
 * 
 * @param <GroupType>
 *            The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *            The type of the underlying data of the adapter's child items
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public class ExpandableListAdapterImplementation<GroupType, ChildType>
		extends
		AbstractExpandableListAdapter<GroupType, ChildType, ExpandableListDecorator<GroupType, ChildType>> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	protected ExpandableListAdapterImplementation(
			final Context context,
			final Inflater groupInflater,
			final Inflater childInflater,
			final ExpandableListDecorator<GroupType, ChildType> decorator,
			final boolean allowDuplicateGroups,
			final boolean allowDuplicateChildren,
			final Set<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners) {
		super(context, groupInflater, childInflater, decorator,
				allowDuplicateGroups, allowDuplicateChildren, adapterListeners);
	}

	public ExpandableListAdapterImplementation(final Context context,
			final Inflater groupInflater, final Inflater childInflater,
			final ExpandableListDecorator<GroupType, ChildType> decorator) {
		this(context, groupInflater, childInflater, decorator, false, false,
				new LinkedHashSet<ExpandableListAdapterListener<GroupType, ChildType>>());
	}

	@Override
	public final boolean isChildSelectable(final int groupPosition,
			final int childPosition) {
		return false;
	}

	@Override
	protected final ChildDecoratorFactory<ChildType> getChildDecoratorFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final AbstractExpandableListAdapter<GroupType, ChildType, ExpandableListDecorator<GroupType, ChildType>> clone()
			throws CloneNotSupportedException {
		return new ExpandableListAdapterImplementation<GroupType, ChildType>(
				getContext(), getGroupInflater(), getChildInflater(),
				getDecorator(), areDuplicateGroupsAllowed(),
				areDuplicateChildrenAllowed(), getAdapterListeners());
	}

}