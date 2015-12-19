/*
 * AndroidAdapters Copyright 2014 - 2015 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package de.mrapp.android.adapter.datastructure.group;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.ListIterator;

import android.content.Context;

import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.expandablelist.NullObjectDecorator;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.selectable.MultipleChoiceListAdapterImplementation;
import de.mrapp.android.adapter.logging.LogLevel;

/**
 * A list iterator, which allows to iterate the data of groups, which can be iterated by using an
 * other list iterator.
 *
 * @param <GroupType>
 *         The type of the groups' data
 * @param <ChildType>
 *         The type of the underlying data of the groups' child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public class GroupListIterator<GroupType, ChildType> implements ListIterator<GroupType> {

    /**
     * The list iterator, which allows to iterate the groups.
     */
    private final ListIterator<Group<GroupType, ChildType>> listIterator;

    /**
     * The context, which should be used by the iterator.
     */
    private final Context context;

    /**
     * The inflater, which is used to inflate the views, which are used to visualize the groups'
     * child items.
     */
    private final Inflater childInflater;

    /**
     * Creates a new adapter, which may be used to manage the child items of a newly created group.
     *
     * @return The adapter, which has been created, as an instance of the type {@link
     * MultipleChoiceListAdapter}. The adapter may not be null
     */
    private MultipleChoiceListAdapter<ChildType> createChildAdapter() {
        MultipleChoiceListAdapter<ChildType> childAdapter =
                new MultipleChoiceListAdapterImplementation<ChildType>(context, childInflater,
                        new NullObjectDecorator<ChildType>());
        childAdapter.setLogLevel(LogLevel.OFF);
        return childAdapter;
    }

    /**
     * Creates a new list iterator, which allows to iterate the data of groups, which can be
     * iterated by using an other list iterator.
     *
     * @param iterator
     *         The list iterator, which allows to iterate the groups, as an instance of the type
     *         {@link ListIterator}. The list iterator may not be null
     * @param context
     *         The context, which should be used by the iterator, as an instance of the class {@link
     *         Context} or null, if no adapter's underlying data should be modified
     * @param childInflater
     *         The inflater, which should be used to inflate the views, which are used to visualize
     *         the groups' child items, as an instance of the type {@link Inflater} or null, if no
     *         adapter's underlying data should be modified
     */
    public GroupListIterator(final ListIterator<Group<GroupType, ChildType>> iterator,
                             final Context context, final Inflater childInflater) {
        ensureNotNull(iterator, "The list iterator may not be null");
        this.listIterator = iterator;
        this.context = context;
        this.childInflater = childInflater;
    }

    @Override
    public final void add(final GroupType group) {
        if (context == null || childInflater == null) {
            throw new UnsupportedOperationException();
        }

        listIterator.add(new Group<GroupType, ChildType>(group, createChildAdapter()));
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
        if (context == null || childInflater == null) {
            throw new UnsupportedOperationException();
        }

        listIterator.set(new Group<GroupType, ChildType>(group, createChildAdapter()));
    }

}