/*
 * Copyright 2014 - 2019 Michael Rapp
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package de.mrapp.android.adapter.datastructure.group;

import android.content.Context;

import java.util.ListIterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.expandablelist.NullObjectDecorator;
import de.mrapp.android.adapter.list.selectable.MultipleChoiceListAdapterImplementation;
import de.mrapp.android.util.logging.LogLevel;
import de.mrapp.util.Condition;

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
     * Creates a new adapter, which may be used to manage the child items of a newly created group.
     *
     * @return The adapter, which has been created, as an instance of the type {@link
     * MultipleChoiceListAdapter}. The adapter may not be null
     */
    private MultipleChoiceListAdapter<ChildType> createChildAdapter() {
        if (context == null) {
            throw new UnsupportedOperationException();
        }

        MultipleChoiceListAdapter<ChildType> childAdapter =
                new MultipleChoiceListAdapterImplementation<>(context,
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
     */
    public GroupListIterator(@NonNull final ListIterator<Group<GroupType, ChildType>> iterator,
                             @Nullable final Context context) {
        Condition.INSTANCE.ensureNotNull(iterator, "The list iterator may not be null");
        this.listIterator = iterator;
        this.context = context;
    }

    @Override
    public final void add(@NonNull final GroupType group) {
        listIterator.add(new Group<>(group, createChildAdapter()));
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
    public final void set(@NonNull final GroupType group) {
        listIterator.set(new Group<>(group, createChildAdapter()));
    }

}