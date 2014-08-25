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

import java.util.ListIterator;

import android.content.Context;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.expandablelist.ChildDecoratorFactory;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.selectable.MultipleChoiceListAdapterImplementation;

/**
 * A list iterator, which allows to iterate the data of groups, which can be
 * iterated by using an other list iterator.
 * 
 * @param <GroupType>
 *            The type of the groups' data
 * @param <ChildType>
 *            The type of the underlying data of the groups' child items
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public class GroupListIterator<GroupType, ChildType> implements
		ListIterator<GroupType> {

	/**
	 * The list iterator, which allows to iterate the groups.
	 */
	private final ListIterator<Group<GroupType, ChildType>> listIterator;

	/**
	 * The context, which should be used by the iterator.
	 */
	private final Context context;

	/**
	 * The inflater, which is used to inflate the views, which are used to
	 * visualize the groups' child items.
	 */
	private final Inflater childInflater;

	/**
	 * The factory, which is used to create the decorators, which are used to
	 * customize the appearance of the views, which are used to visualize the
	 * child items of the adapter.
	 */
	private final ChildDecoratorFactory<ChildType> childDecoratorFactory;

	/**
	 * Creates a new adapter, which may be used to manage the child items of a
	 * newly created group.
	 * 
	 * @return The adapter, which has been created, as an instance of the type
	 *         {@link MultipleChoiceListAdapter}. The adapter may not be null
	 */
	private MultipleChoiceListAdapter<ChildType> createChildAdapter() {
		return new MultipleChoiceListAdapterImplementation<ChildType>(context,
				childInflater, childDecoratorFactory.createChildDecorator());
	}

	/**
	 * Creates a new list iterator, which allows to iterate the data of groups,
	 * which can be iterated by using an other list iterator.
	 * 
	 * @param iterator
	 *            The list iterator, which allows to iterate the groups, as an
	 *            instance of the type {@link ListIterator}. The list iterator
	 *            may not be null
	 * @param context
	 *            The context, which should be used by the iterator, as an
	 *            instance of the class {@link Context}. The context may not be
	 *            null
	 * @param childInflater
	 *            The inflater, which should be used to inflate the views, which
	 *            are used to visualize the groups' child items, as an instance
	 *            of the type {@link Inflater}. The inflater may not be null
	 * @param childDecoratorFactory
	 *            The factory, which should be used to create the decorators,
	 *            which are used to customize the appearance of the views, which
	 *            are used to visualize the child items of the adapter, as an
	 *            instance of the type {@link ChildDecoratorFactory}. The
	 *            factory may not be null
	 */
	public GroupListIterator(
			final ListIterator<Group<GroupType, ChildType>> iterator,
			final Context context, final Inflater childInflater,
			final ChildDecoratorFactory<ChildType> childDecoratorFactory) {
		ensureNotNull(iterator, "The list iterator may not be null");
		ensureNotNull(context, "The context may not be null");
		ensureNotNull(childInflater, "The child inflater may not be null");
		ensureNotNull(childDecoratorFactory,
				"The child decorator factory may not be null");
		this.listIterator = iterator;
		this.context = context;
		this.childInflater = childInflater;
		this.childDecoratorFactory = childDecoratorFactory;
	}

	@Override
	public final void add(final GroupType group) {
		listIterator.add(new Group<GroupType, ChildType>(group,
				createChildAdapter()));
	}

	@Override
	public final boolean hasNext() {
		return listIterator.hasNext();
	}

	@Override
	public final boolean hasPrevious() {
		return listIterator.hasPrevious();
	}

	@Override
	public final GroupType next() {
		return listIterator.next().getData();
	}

	@Override
	public final int nextIndex() {
		return listIterator.nextIndex();
	}

	@Override
	public final GroupType previous() {
		return listIterator.previous().getData();
	}

	@Override
	public final int previousIndex() {
		return listIterator.previousIndex();
	}

	@Override
	public final void remove() {
		listIterator.remove();
	}

	@Override
	public final void set(final GroupType group) {
		listIterator.set(new Group<GroupType, ChildType>(group,
				createChildAdapter()));
	}

}