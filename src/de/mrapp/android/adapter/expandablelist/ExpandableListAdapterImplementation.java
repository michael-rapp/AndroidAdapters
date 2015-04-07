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
import android.view.View;
import de.mrapp.android.adapter.ExpandableListDecorator;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.selectable.MultipleChoiceListAdapterImplementation;
import de.mrapp.android.adapter.logging.LogLevel;

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
			final LogLevel logLevel,
			final MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter,
			final boolean allowDuplicateChildren,
			final boolean notifyOnChange,
			final boolean expandGroupOnClick,
			final Set<ExpandableListAdapterItemClickListener<GroupType, ChildType>> itemClickListeners,
			final Set<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners,
			final Set<ExpansionListener<GroupType, ChildType>> expansionListeners) {
		super(context, groupInflater, childInflater, decorator, logLevel,
				groupAdapter, allowDuplicateChildren, notifyOnChange,
				expandGroupOnClick, itemClickListeners, adapterListeners,
				expansionListeners);
	}

	public ExpandableListAdapterImplementation(final Context context,
			final Inflater groupInflater, final Inflater childInflater,
			final ExpandableListDecorator<GroupType, ChildType> decorator) {
		this(
				context,
				groupInflater,
				childInflater,
				decorator,
				LogLevel.ALL,
				new MultipleChoiceListAdapterImplementation<Group<GroupType, ChildType>>(
						context, groupInflater,
						new NullObjectDecorator<Group<GroupType, ChildType>>()),
				false,
				true,
				true,
				new LinkedHashSet<ExpandableListAdapterItemClickListener<GroupType, ChildType>>(),
				new LinkedHashSet<ExpandableListAdapterListener<GroupType, ChildType>>(),
				new LinkedHashSet<ExpansionListener<GroupType, ChildType>>());
	}

	@Override
	public final boolean isChildSelectable(final int groupPosition,
			final int childPosition) {
		return false;
	}

	@Override
	protected final void applyDecoratorOnGroup(final Context context,
			final View view, final int index) {
		GroupType group = getGroup(index);
		boolean expanded = isGroupExpanded(index);

		// TODO: Use actual values
		boolean enabled = true;
		int state = 0;
		boolean filtered = false;

		getDecorator().applyDecoratorOnGroup(context, this, view, group, index,
				expanded, enabled, state, filtered);
	}

	@Override
	protected final void applyDecoratorOnChild(final Context context,
			final View view, final int groupIndex, final int childIndex) {
		GroupType group = getGroup(groupIndex);
		ChildType child = getChild(groupIndex, childIndex);
		getDecorator().applyDecoratorOnChild(context, this, view, child,
				childIndex, group, groupIndex, true, 0, false);
	}

	@Override
	public final AbstractExpandableListAdapter<GroupType, ChildType, ExpandableListDecorator<GroupType, ChildType>> clone()
			throws CloneNotSupportedException {
		return new ExpandableListAdapterImplementation<GroupType, ChildType>(
				getContext(), getGroupInflater(), getChildInflater(),
				getDecorator(), getLogLevel(), cloneGroupAdapter(),
				areDuplicateChildrenAllowed(), isNotifiedOnChange(),
				isGroupExpandedOnClick(), getItemClickListeners(),
				getAdapterListeners(), getExpansionListeners());
	}

}