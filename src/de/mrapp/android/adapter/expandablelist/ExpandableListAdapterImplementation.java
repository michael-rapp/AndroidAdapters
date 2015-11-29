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
import de.mrapp.android.adapter.expandablelist.enablestate.ExpandableListEnableStateListener;
import de.mrapp.android.adapter.expandablelist.filterable.AbstractFilterableExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.filterable.ExpandableListFilterListener;
import de.mrapp.android.adapter.expandablelist.itemstate.ExpandableListItemStateListener;
import de.mrapp.android.adapter.expandablelist.sortable.ExpandableListSortingListener;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.selectable.MultipleChoiceListAdapterImplementation;
import de.mrapp.android.adapter.logging.LogLevel;

/**
 * An adapter, whose underlying data is managed as a list of arbitrary group and
 * child items. Such an adapter's purpose is to provide the underlying data for
 * visualization using a {@link ExpandableListView} widget.
 * 
 * @param <GroupType>
 *            The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *            The type of the underlying data of the adapter's child items
 * 
 * @author Michael Rapp
 * 
 * @since 0.1.0
 */
public class ExpandableListAdapterImplementation<GroupType, ChildType> extends
		AbstractFilterableExpandableListAdapter<GroupType, ChildType, ExpandableListDecorator<GroupType, ChildType>> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary group and child items.
	 * 
	 * @param context
	 *            The context, the adapter belongs to, as an instance of the
	 *            class {@link Context}. The context may not be null
	 * @param groupInflater
	 *            The inflater, which should be used to inflate the views, which
	 *            are used to visualize the adapter's group items, as an
	 *            instance of the type {@link Inflater}. The inflater may not be
	 *            null
	 * @param childInflater
	 *            The inflater, which should be used to inflate the views, which
	 *            are used to visualize the adapter's child items, as an
	 *            instance of the type {@link Inflater}. The inflater may not be
	 *            null
	 * @param decorator
	 *            The decorator, which should be used to customize the
	 *            appearance of the views, which are used to visualize the group
	 *            and child items of the adapter, as an instance of the generic
	 *            type DecoratorType. The decorator may not be null
	 * @param logLevel
	 *            The log level, which should be used for logging, as a value of
	 *            the enum {@link LogLevel}. The log level may not be null
	 * @param groupAdapter
	 *            The adapter, which should manage the adapter's group items, as
	 *            an instance of the type {@link MultipleChoiceListAdapter}. The
	 *            adapter may not be null
	 * @param allowDuplicateChildren
	 *            True, if duplicate child items, regardless from the group they
	 *            belong to, should be allowed, false otherwise
	 * @param notifyOnChange
	 *            True, if the method <code>notifyDataSetChanged():void</code>
	 *            should be automatically called when the adapter's underlying
	 *            data has been changed, false otherwise
	 * @param expandGroupOnClick
	 *            True, if a group should be expanded, when it is clicked by the
	 *            user, false otherwise
	 * @param itemClickListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when an item of the adapter has been clicked by the user, as
	 *            an instance of the type {@link Set}, or an empty set, if no
	 *            listeners should be notified
	 * @param adapterListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been modified, as an
	 *            instance of the type {@link Set}, or an empty set, if no
	 *            listeners should be notified
	 * @param expansionListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when a group item has been expanded or collapsed, as an
	 *            instance of the type {@link Set}, or an empty set, if no
	 *            listeners should be notified
	 * @param enableStateListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when an item has been disabled or enabled, as an instance of
	 *            the type {@link Set}, or an empty set, if no listeners should
	 *            be notified
	 * @param numberOfGroupStates
	 *            The number of states, the adapter's group items may have, as
	 *            an {@link Integer} value. The value must be at least 1
	 * @param numberOfChildStates
	 *            The number of states, the adapter's child items may have, as
	 *            an {@link Integer} value. The value must be at least 1
	 * @param triggerGroupStateOnClick
	 *            True, if the state of a group item should be triggered, when
	 *            it is clicked by the user, false otherwise
	 * @param triggerChildStateOnClick
	 *            True, if the state of a child item should be triggered, when
	 *            it is clicked by the user, false otherwise
	 * @param itemStateListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the state of an item has been changed, or an empty set,
	 *            if no listeners should be notified
	 * @param sortingListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been sorted, or an
	 *            empty set, if no listeners should be notified
	 * @param filterListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been filtered, or an
	 *            empty set, if no listeners should be notified
	 */
	protected ExpandableListAdapterImplementation(final Context context, final Inflater groupInflater,
			final Inflater childInflater, final ExpandableListDecorator<GroupType, ChildType> decorator,
			final LogLevel logLevel, final MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter,
			final boolean allowDuplicateChildren, final boolean notifyOnChange, final boolean expandGroupOnClick,
			final Set<ExpandableListAdapterItemClickListener<GroupType, ChildType>> itemClickListeners,
			final Set<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners,
			final Set<ExpansionListener<GroupType, ChildType>> expansionListeners,
			final Set<ExpandableListEnableStateListener<GroupType, ChildType>> enableStateListeners,
			final int numberOfGroupStates, final int numberOfChildStates, final boolean triggerGroupStateOnClick,
			final boolean triggerChildStateOnClick,
			final Set<ExpandableListItemStateListener<GroupType, ChildType>> itemStateListeners,
			final Set<ExpandableListSortingListener<GroupType, ChildType>> sortingListeners,
			final Set<ExpandableListFilterListener<GroupType, ChildType>> filterListeners) {
		super(context, groupInflater, childInflater, decorator, logLevel, groupAdapter, allowDuplicateChildren,
				notifyOnChange, expandGroupOnClick, itemClickListeners, adapterListeners, expansionListeners,
				enableStateListeners, numberOfGroupStates, numberOfChildStates, triggerGroupStateOnClick,
				triggerChildStateOnClick, itemStateListeners, sortingListeners, filterListeners);
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary group and child items.
	 * 
	 * @param context
	 *            The context, the adapter belongs to, as an instance of the
	 *            class {@link Context}. The context may not be null
	 * @param groupInflater
	 *            The inflater, which should be used to inflate the views, which
	 *            are used to visualize the adapter's group items, as an
	 *            instance of the type {@link Inflater}. The inflater may not be
	 *            null
	 * @param childInflater
	 *            The inflater, which should be used to inflate the views, which
	 *            are used to visualize the adapter's child items, as an
	 *            instance of the type {@link Inflater}. The inflater may not be
	 *            null
	 * @param decorator
	 *            The decorator, which should be used to customize the
	 *            appearance of the views, which are used to visualize the group
	 *            and child items of the adapter, as an instance of the generic
	 *            type DecoratorType. The decorator may not be null
	 */
	public ExpandableListAdapterImplementation(final Context context, final Inflater groupInflater,
			final Inflater childInflater, final ExpandableListDecorator<GroupType, ChildType> decorator) {
		this(context, groupInflater, childInflater, decorator, LogLevel.ALL,
				new MultipleChoiceListAdapterImplementation<Group<GroupType, ChildType>>(context, groupInflater,
						new NullObjectDecorator<Group<GroupType, ChildType>>()),
				false, true, true, new LinkedHashSet<ExpandableListAdapterItemClickListener<GroupType, ChildType>>(),
				new LinkedHashSet<ExpandableListAdapterListener<GroupType, ChildType>>(),
				new LinkedHashSet<ExpansionListener<GroupType, ChildType>>(),
				new LinkedHashSet<ExpandableListEnableStateListener<GroupType, ChildType>>(), 1, 1, false, false,
				new LinkedHashSet<ExpandableListItemStateListener<GroupType, ChildType>>(),
				new LinkedHashSet<ExpandableListSortingListener<GroupType, ChildType>>(),
				new LinkedHashSet<ExpandableListFilterListener<GroupType, ChildType>>());
	}

	@Override
	protected final void applyDecoratorOnGroup(final Context context, final View view, final int index) {
		GroupType group = getGroup(index);
		boolean expanded = isGroupExpanded(index);
		boolean enabled = isGroupEnabled(index);
		int state = getGroupState(index);
		boolean filtered = areGroupsFiltered();
		getDecorator().applyDecoratorOnGroup(context, this, view, group, index, expanded, enabled, state, filtered);
	}

	@Override
	protected final void applyDecoratorOnChild(final Context context, final View view, final int groupIndex,
			final int childIndex) {
		GroupType group = getGroup(groupIndex);
		ChildType child = getChild(groupIndex, childIndex);
		boolean enabled = isChildEnabled(groupIndex, childIndex);
		int state = getChildState(groupIndex, childIndex);
		boolean filtered = areChildrenFiltered();
		getDecorator().applyDecoratorOnChild(context, this, view, child, childIndex, group, groupIndex, enabled, state,
				filtered);
	}

	@Override
	public final boolean isChildSelectable(final int groupPosition, final int childPosition) {
		return false;
	}

	@Override
	public final int getChildTypeCount() {
		return getDecorator().getChildTypeCount();
	}

	@Override
	public final int getChildType(final int groupIndex, final int childIndex) {
		return getDecorator().getChildType(this, getChild(groupIndex, childIndex), childIndex, getGroup(groupIndex),
				groupIndex, isChildEnabled(groupIndex, childIndex), getChildState(groupIndex, childIndex),
				isFiltered());
	}

	@Override
	public final int getGroupTypeCount() {
		return getDecorator().getGroupTypeCount();
	}

	@Override
	public final int getGroupType(final int groupIndex) {
		return getDecorator().getGroupType(this, getGroup(groupIndex), groupIndex, isGroupEnabled(groupIndex),
				getGroupState(groupIndex), isFiltered());
	}

	@Override
	public final ExpandableListAdapterImplementation<GroupType, ChildType> clone() throws CloneNotSupportedException {
		return new ExpandableListAdapterImplementation<GroupType, ChildType>(getContext(), getGroupInflater(),
				getChildInflater(), getDecorator(), getLogLevel(), cloneGroupAdapter(), areDuplicateChildrenAllowed(),
				isNotifiedOnChange(), isGroupExpandedOnClick(), getItemClickListeners(), getAdapterListeners(),
				getExpansionListeners(), getEnableStateListeners(), getNumberOfGroupStates(), getNumberOfChildStates(),
				isGroupStateTriggeredOnClick(), isChildStateTriggeredOnClick(), getItemStateListeners(),
				getSortingListeners(), getFilterListeners());
	}

}