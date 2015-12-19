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
    public GroupIterator(final Iterator<Group<GroupType, ChildType>> iterator) {
        ensureNotNull(iterator, "The iterator may not be null");
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