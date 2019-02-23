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

import androidx.annotation.NonNull;
import de.mrapp.util.Condition;

import java.util.Iterator;

/**
 * An iterator, which allows to iterate the data of groups, which can be iterated by using an other
 * iterator.
 *
 * @param <GroupType>
 *         The type of the groups' data
 * @param <ChildType>
 *         The type of the underlying data of the groups' child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public class GroupIterator<GroupType, ChildType> implements Iterator<GroupType> {

    /**
     * The iterator, which allows to iterate the groups.
     */
    private final Iterator<Group<GroupType, ChildType>> iterator;

    /**
     * Creates a new iterator, which allows to iterate the data of groups, which can be iterated by
     * using an other iterator.
     *
     * @param iterator
     *         The iterator, which allows to iterate the groups, as an instance of the type {@link
     *         Iterator}. The iterator may not be null
     */
    public GroupIterator(@NonNull final Iterator<Group<GroupType, ChildType>> iterator) {
        Condition.INSTANCE.ensureNotNull(iterator, "The iterator may not be null");
        this.iterator = iterator;
    }

    @Override
    public final boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public final GroupType next() {
        return iterator.next().getData();
    }

    @Override
    public final void remove() {
        iterator.remove();
    }

}