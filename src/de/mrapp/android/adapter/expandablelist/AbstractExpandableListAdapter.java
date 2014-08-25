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

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import de.mrapp.android.adapter.ExpandableListAdapter;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.datastructure.group.GroupIterator;
import de.mrapp.android.adapter.datastructure.group.GroupListIterator;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.selectable.MultipleChoiceListAdapterImplementation;

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
 * @param <DecoratorType>
 *            The type of the decorator, which allows to customize the
 *            appearance of the views, which are used to visualize the group and
 *            child items of the adapter
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractExpandableListAdapter<GroupType, ChildType, DecoratorType>
		extends BaseExpandableListAdapter implements
		ExpandableListAdapter<GroupType, ChildType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The context, the adapter belongs to.
	 */
	private final transient Context context;

	/**
	 * The inflater, which is used to inflate the views, which are used to
	 * visualize the adapter's group items.
	 */
	private final transient Inflater groupInflater;

	/**
	 * The inflater, which is used to inflate the views, which are used to
	 * visualize the adapter's child items.
	 */
	private final transient Inflater childInflater;

	/**
	 * The decorator, which allows to customize the appearance of the views,
	 * which are used to visualize the group and child items of the adapter.
	 */
	private final transient DecoratorType decorator;

	/**
	 * True, if duplicate group items are allowed, false otherwise.
	 */
	private boolean allowDuplicateGroups;

	/**
	 * True, if duplicate children, regardless from the group they belong to,
	 * are allowed, false otherwise.
	 */
	private boolean allowDuplicateChildren;

	/**
	 * A set, which contains the listeners, which should be notified, when the
	 * adapter's underlying data has been modified.
	 */
	private Set<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners;

	/**
	 * An adapter, which manages the adapter's group items.
	 */
	private MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter;

	private MultipleChoiceListAdapter<ChildType> createChildAdapter() {
		return new MultipleChoiceListAdapterImplementation<ChildType>(context,
				childInflater, getChildDecoratorFactory()
						.createChildDecorator());
	}

	protected abstract ChildDecoratorFactory<ChildType> getChildDecoratorFactory();

	protected AbstractExpandableListAdapter(
			final Context context,
			final Inflater groupInflater,
			final Inflater childInflater,
			final DecoratorType decorator,
			final boolean allowDuplicateGroups,
			final boolean allowDuplicateChildren,
			final Set<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners) {
		ensureNotNull(context, "The context may not be null");
		ensureNotNull(groupInflater, "The group inflater may not be null");
		ensureNotNull(childInflater, "The child inflater may not be null");
		ensureNotNull(decorator, "The decorator may not be null");
		ensureNotNull(adapterListeners, "The adapter listeners may not be null");
		this.context = context;
		this.groupInflater = groupInflater;
		this.childInflater = childInflater;
		this.decorator = decorator;
		this.adapterListeners = adapterListeners;
	}

	@Override
	public final void addAdapterListener(
			final ExpandableListAdapterListener<GroupType, ChildType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		adapterListeners.add(listener);
	}

	@Override
	public final void removeAdapterListener(
			final ExpandableListAdapterListener<GroupType, ChildType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		adapterListeners.remove(listener);
	}

	@Override
	public final boolean areDuplicateGroupsAllowed() {
		return allowDuplicateGroups;
	}

	@Override
	public final void allowDuplicateGroups(final boolean allowDuplicateGroups) {
		this.allowDuplicateGroups = allowDuplicateGroups;
	}

	@Override
	public final boolean addGroup(final GroupType group) {
		return groupAdapter.addItem(new Group<GroupType, ChildType>(group,
				createChildAdapter()));
	}

	@Override
	public final boolean addGroup(final int index, final GroupType group) {
		return groupAdapter.addItem(index, new Group<GroupType, ChildType>(
				group, createChildAdapter()));
	}

	@Override
	public final boolean addAllGroups(final Collection<GroupType> groups) {
		ensureNotNull(groups, "The collection may not be null");
		boolean result = true;

		for (GroupType group : groups) {
			result &= groupAdapter.addItem(new Group<GroupType, ChildType>(
					group, createChildAdapter()));
		}

		return result;
	}

	@Override
	public final boolean addAllGroups(final int index,
			final Collection<GroupType> groups) {
		ensureNotNull(groups, "The collection may not be null");
		boolean result = true;
		int currentIndex = index;

		for (GroupType group : groups) {
			result &= groupAdapter
					.addItem(currentIndex, new Group<GroupType, ChildType>(
							group, createChildAdapter()));
			currentIndex++;
		}

		return result;
	}

	@Override
	public final boolean addAllGroups(final GroupType... groups) {
		return addAllGroups(getNumberOfGroups(), groups);
	}

	@Override
	public final boolean addAllGroups(final int index,
			final GroupType... groups) {
		ensureNotNull(groups, "The array may not be null");
		return addAllGroups(index, Arrays.asList(groups));
	}

	@Override
	public final GroupType replaceGroup(final int index, final GroupType group) {
		return groupAdapter.replaceItem(index,
				new Group<GroupType, ChildType>(group, createChildAdapter()))
				.getData();
	}

	@Override
	public final GroupType removeGroup(final int index) {
		return groupAdapter.removeItem(index).getData();
	}

	@Override
	public final boolean removeGroup(final GroupType group) {
		int index = indexOfGroup(group);

		if (index != -1) {
			groupAdapter.removeItem(index);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public final boolean removeAllGroups(final Collection<GroupType> groups) {
		ensureNotNull(groups, "The collection may not be null");
		boolean result = true;

		for (GroupType group : groups) {
			result &= removeGroup(group);
		}

		return result;
	}

	@Override
	public final boolean removeAllGroups(final GroupType... groups) {
		ensureNotNull(groups, "The array may not be null");
		return removeAllGroups(Arrays.asList(groups));
	}

	@Override
	public final void retainAllGroups(final Collection<GroupType> groups) {
		ensureNotNull(groups, "The collection may not be null");

		for (int i = getNumberOfGroups() - 1; i >= 0; i--) {
			if (!containsGroup(getGroup(i))) {
				removeGroup(i);
			}
		}
	}

	@Override
	public final void retainAllGroups(final GroupType... groups) {
		ensureNotNull(groups, "The array may not be null");
		retainAllGroups(Arrays.asList(groups));
	}

	@Override
	public final void clearGroups() {
		groupAdapter.clearItems();
	}

	@Override
	public final Iterator<GroupType> groupIterator() {
		return new GroupIterator<GroupType, ChildType>(groupAdapter.iterator());
	}

	@Override
	public final ListIterator<GroupType> groupListIterator() {
		return new GroupListIterator<GroupType, ChildType>(
				groupAdapter.listIterator(), context, childInflater,
				getChildDecoratorFactory());
	}

	@Override
	public final ListIterator<GroupType> groupListIterator(final int index) {
		return new GroupListIterator<GroupType, ChildType>(
				groupAdapter.listIterator(index), context, childInflater,
				getChildDecoratorFactory());
	}

	@Override
	public final Collection<GroupType> subListGroups(final int start,
			final int end) {
		Collection<GroupType> subList = new ArrayList<GroupType>();

		for (int i = start; i < end; i++) {
			subList.add(getGroup(i));
		}

		return subList;
	}

	@Override
	public final Object[] groupsToArray() {
		return getAllGroups().toArray();
	}

	@Override
	public final <T> T[] groupsToArray(final T[] array) {
		return getAllGroups().toArray(array);
	}

	@Override
	public final GroupType getGroup(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getData();
	}

	@Override
	public final int indexOfGroup(final GroupType group) {
		ensureNotNull(group, "The group may not be null");
		return getAllGroups().indexOf(group);
	}

	@Override
	public final int lastIndexOfGroup(final GroupType group) {
		ensureNotNull(group, "The group may not be null");
		return getAllGroups().lastIndexOf(group);
	}

	@Override
	public final boolean containsGroup(final GroupType group) {
		ensureNotNull(group, "The group may not be null");
		return getAllGroups().contains(group);
	}

	@Override
	public final boolean containsAllGroups(final Collection<GroupType> groups) {
		ensureNotNull(groups, "The collection may not be null");
		return getAllGroups().containsAll(groups);
	}

	@Override
	public final boolean containsAllGroups(final GroupType... groups) {
		ensureNotNull(groups, "The array may not be null");
		return containsAllGroups(Arrays.asList(groups));
	}

	@Override
	public final int getNumberOfGroups() {
		return groupAdapter.getNumberOfItems();
	}

	@Override
	public final List<GroupType> getAllGroups() {
		List<GroupType> result = new ArrayList<GroupType>();

		for (Group<GroupType, ChildType> group : groupAdapter.getAllItems()) {
			result.add(group.getData());
		}

		return result;
	}

	@Override
	public final boolean isGroupEmpty(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter().isEmpty();
	}

	@Override
	public final boolean isGroupEmpty(final GroupType group) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return isGroupEmpty(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean areDuplicateChildrenAllowed() {
		return allowDuplicateChildren;
	}

	@Override
	public final void allowDuplicateChildren(
			final boolean allowDuplicateChildren) {
		this.allowDuplicateChildren = true;

		if (!allowDuplicateChildren) {
			for (Group<GroupType, ChildType> group : groupAdapter.getAllItems()) {
				group.getChildAdapter().allowDuplicates(false);
			}
		}
	}

	@Override
	public final boolean areDuplicateChildrenAllowed(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.areDuplicatesAllowed();
	}

	@Override
	public final void allowDuplicateChildren(final int groupIndex,
			final boolean allowDuplicateChildren) {
		groupAdapter.getItem(groupIndex).getChildAdapter()
				.allowDuplicates(allowDuplicateChildren);
	}

	@Override
	public final boolean areDuplicateChildrenAllowed(final GroupType group) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return areDuplicateChildrenAllowed(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final void allowDuplicateChildren(final GroupType group,
			final boolean allowDuplicateChildren) {
		int index = indexOfGroup(group);

		if (index != -1) {
			allowDuplicateChildren(index, allowDuplicateChildren);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean addChild(final int groupIndex, final ChildType child) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.addItem(child);
	}

	@Override
	public final boolean addChild(final GroupType group, final ChildType child) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return addChild(index, child);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean addChild(final int groupIndex, final int index,
			final ChildType child) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.addItem(index, child);
	}

	@Override
	public final boolean addChild(final GroupType group, final int index,
			final ChildType child) {
		int groupIndex = indexOfGroup(group);

		if (groupIndex != -1) {
			return addChild(groupIndex, index, child);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean addAllChildren(final int groupIndex,
			final Collection<ChildType> children) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.addAllItems(children);
	}

	@Override
	public final boolean addAllChildren(final GroupType group,
			final Collection<ChildType> children) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return addAllChildren(index, children);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean addAllChildren(final int groupIndex, final int index,
			final Collection<ChildType> children) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.addAllItems(index, children);
	}

	@Override
	public final boolean addAllChildren(final GroupType group, final int index,
			final Collection<ChildType> children) {
		int groupIndex = indexOfGroup(group);

		if (index != -1) {
			return addAllChildren(groupIndex, index, children);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean addAllChildren(final int groupIndex,
			final ChildType... children) {
		return addAllChildren(groupIndex, Arrays.asList(children));
	}

	@Override
	public final boolean addAllChildren(final GroupType group,
			final ChildType... children) {
		return addAllChildren(group, Arrays.asList(children));
	}

	@Override
	public final boolean addAllChildren(final int groupIndex, final int index,
			final ChildType... children) {
		return addAllChildren(groupIndex, index, Arrays.asList(children));
	}

	@Override
	public final boolean addAllChildren(final GroupType group, final int index,
			final ChildType... children) {
		return addAllChildren(group, index, Arrays.asList(children));
	}

	@Override
	public final ChildType replaceChild(final int groupIndex, final int index,
			final ChildType child) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.replaceItem(index, child);
	}

	@Override
	public final ChildType replaceChild(final GroupType group, final int index,
			final ChildType child) {
		int groupIndex = indexOfGroup(group);

		if (groupIndex != -1) {
			return replaceChild(groupIndex, index, child);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final ChildType removeChild(final int groupIndex, final int index) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.removeItem(index);
	}

	@Override
	public final ChildType removeChild(final GroupType group, final int index) {
		int groupIndex = indexOfGroup(group);

		if (index != -1) {
			return removeChild(groupIndex, index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean removeChild(final int groupIndex, final ChildType child) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.removeItem(child);
	}

	@Override
	public final boolean removeChild(final GroupType group,
			final ChildType child) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return removeChild(index, child);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean removeAllChildren(final Collection<ChildType> children) {
		boolean result = true;

		for (Group<GroupType, ChildType> group : groupAdapter.getAllItems()) {
			result &= group.getChildAdapter().removeAllItems(children);
		}

		return result;
	}

	@Override
	public final boolean removeAllChildren(final int groupIndex,
			final Collection<ChildType> children) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.removeAllItems(children);
	}

	@Override
	public final boolean removeAllChildren(final GroupType group,
			final Collection<ChildType> children) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return removeAllChildren(index, children);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean removeAllChildren(final ChildType... children) {
		return removeAllChildren(Arrays.asList(children));
	}

	@Override
	public final boolean removeAllChildren(final int groupIndex,
			final ChildType... children) {
		return removeAllChildren(groupIndex, Arrays.asList(children));
	}

	@Override
	public final boolean removeAllChildren(final GroupType group,
			final ChildType... children) {
		return removeAllChildren(group, Arrays.asList(children));
	}

	@Override
	public final void retainAllChildren(final Collection<ChildType> children) {
		for (Group<GroupType, ChildType> group : groupAdapter.getAllItems()) {
			group.getChildAdapter().retainAllItems(children);
		}
	}

	@Override
	public final void retainAllChildren(final int groupIndex,
			final Collection<ChildType> children) {
		groupAdapter.getItem(groupIndex).getChildAdapter()
				.retainAllItems(children);
	}

	@Override
	public final void retainAllChildren(final GroupType group,
			final Collection<ChildType> children) {
		int index = indexOfGroup(group);

		if (index != -1) {
			retainAllChildren(index, children);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final void retainAllChildren(final ChildType... children) {
		retainAllChildren(Arrays.asList(children));
	}

	@Override
	public final void retainAllChildren(final int groupIndex,
			final ChildType... children) {
		retainAllChildren(groupIndex, Arrays.asList(children));
	}

	@Override
	public final void retainAllChildren(final GroupType group,
			final ChildType... children) {
		retainAllChildren(group, Arrays.asList(children));
	}

	@Override
	public final void clearChildren() {
		for (Group<GroupType, ChildType> group : groupAdapter.getAllItems()) {
			group.getChildAdapter().clearItems();
		}
	}

	@Override
	public final void clearChildren(final int groupIndex) {
		groupAdapter.getItem(groupIndex).getChildAdapter().clearItems();
	}

	@Override
	public final void clearChildren(final GroupType group) {
		int index = indexOfGroup(group);

		if (index != -1) {
			clearChildren(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final Iterator<ChildType> childIterator() {
		return getAllChildren().iterator();
	}

	@Override
	public final Iterator<ChildType> childIterator(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter().iterator();
	}

	@Override
	public final Iterator<ChildType> childIterator(final GroupType group) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return childIterator(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final ListIterator<ChildType> childListIterator(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.listIterator();
	}

	@Override
	public final ListIterator<ChildType> childListIterator(final GroupType group) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return childListIterator(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final ListIterator<ChildType> childListIterator(
			final int groupIndex, final int index) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.listIterator(index);
	}

	@Override
	public final ListIterator<ChildType> childListIterator(
			final GroupType group, final int index) {
		int groupIndex = indexOfGroup(group);

		if (index != -1) {
			return childListIterator(groupIndex, index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final Collection<ChildType> subListChildren(final int groupIndex,
			final int start, final int end) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.subList(start, end);
	}

	@Override
	public final Collection<ChildType> subListChildren(final GroupType group,
			final int start, final int end) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return subListChildren(index, start, end);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final Object[] childrenToArray() {
		return getAllChildren().toArray();
	}

	@Override
	public final Object[] childrenToArray(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter().toArray();
	}

	@Override
	public final Object[] childrenToArray(final GroupType group) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return childrenToArray(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final <T> T[] childrenToArray(final T[] array) {
		return getAllChildren().toArray(array);
	}

	@Override
	public final <T> T[] childrenToArray(final int groupIndex, final T[] array) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.toArray(array);
	}

	@Override
	public final <T> T[] childrenToArray(final GroupType group, final T[] array) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return childrenToArray(index, array);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final ChildType getChild(final GroupType group, final int index) {
		int groupIndex = indexOfGroup(group);

		if (groupIndex != -1) {
			return getChild(groupIndex, index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final int indexOfChild(final ChildType child) {
		for (int i = 0; i < groupAdapter.getNumberOfItems(); i++) {
			if (groupAdapter.getItem(i).getChildAdapter().containsItem(child)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final int indexOfChild(final int groupIndex, final ChildType child) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.indexOf(child);
	}

	@Override
	public final int indexOfChild(final GroupType group, final ChildType child) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return indexOfChild(index, child);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final int lastIndexOfChild(final ChildType child) {
		for (int i = groupAdapter.getNumberOfItems() - 1; i >= 0; i--) {
			if (groupAdapter.getItem(i).getChildAdapter().containsItem(child)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final int lastIndexOfChild(final int groupIndex,
			final ChildType child) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.lastIndexOf(child);
	}

	@Override
	public final int lastIndexOfChild(final GroupType group,
			final ChildType child) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return lastIndexOfChild(index, child);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean containsChild(final ChildType child) {
		for (Group<GroupType, ChildType> group : groupAdapter.getAllItems()) {
			if (group.getChildAdapter().containsItem(child)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public final boolean containsChild(final int groupIndex,
			final ChildType child) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.containsItem(child);
	}

	@Override
	public final boolean containsChild(final GroupType group,
			final ChildType child) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return containsChild(index, child);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean containsAllChildren(
			final Collection<ChildType> children) {
		return getAllChildren().containsAll(children);
	}

	@Override
	public final boolean containsAllChildren(final int groupIndex,
			final Collection<ChildType> children) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.containsAllItems(children);
	}

	@Override
	public final boolean containsAllChildren(final GroupType group,
			final Collection<ChildType> children) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return containsAllChildren(index, children);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean containsAllChildren(final ChildType... children) {
		return containsAllChildren(Arrays.asList(children));
	}

	@Override
	public final int getNumberOfChildren() {
		return getAllChildren().size();
	}

	@Override
	public final int getNumberOfChildren(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.getNumberOfItems();
	}

	@Override
	public final int getNumberOfChildren(final GroupType group) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return getNumberOfChildren(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final List<ChildType> getAllChildren() {
		List<ChildType> result = new ArrayList<ChildType>();

		for (Group<GroupType, ChildType> group : groupAdapter.getAllItems()) {
			result.addAll(group.getChildAdapter().getAllItems());
		}

		return result;
	}

	@Override
	public final Collection<ChildType> getAllChildren(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter().getAllItems();
	}

	@Override
	public final Collection<ChildType> getAllChildren(final GroupType group) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return getAllChildren(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean containsAllChildren(final int groupIndex,
			final ChildType... children) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.containsAllItems(children);
	}

	@Override
	public final boolean containsAllChildren(final GroupType group,
			final ChildType... children) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return containsAllChildren(index, children);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final ChildType getChild(final int groupIndex, final int childIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.getItem(childIndex);
	}

	@Override
	public final long getChildId(final int groupIndex, final int childIndex) {
		int id = groupAdapter.getNumberOfItems() + childIndex;

		for (int i = 0; i < groupIndex; i++) {
			id += groupAdapter.getItem(i).getChildAdapter().getNumberOfItems();
		}

		return id;
	}

	@Override
	public final int getChildrenCount(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.getNumberOfItems();
	}

	@Override
	public final int getGroupCount() {
		return groupAdapter.getNumberOfItems();
	}

	@Override
	public final long getGroupId(final int groupIndex) {
		return groupIndex;
	}

	@Override
	public final boolean hasStableIds() {
		return true;
	}

	@Override
	public final View getGroupView(final int groupIndex,
			final boolean isExpanded, final View convertView,
			final ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final View getChildView(final int groupIndex, final int childIndex,
			final boolean isLastChild, final View convertView,
			final ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onSaveInstanceState(final Bundle outState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRestoreInstanceState(final Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

	@Override
	public abstract AbstractExpandableListAdapter<GroupType, ChildType, DecoratorType> clone()
			throws CloneNotSupportedException;

}