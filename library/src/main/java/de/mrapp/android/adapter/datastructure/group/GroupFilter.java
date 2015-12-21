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

import android.support.annotation.NonNull;

import de.mrapp.android.adapter.Filter;

import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * A filter, which allows to filter groups, depending on their data.
 *
 * @param <GroupType>
 *         The type of the group's data
 * @param <ChildType>
 *         The type of the group's children
 * @author Michael Rapp
 * @since 0.1.0
 */
public class GroupFilter<GroupType, ChildType> implements Filter<Group<GroupType, ChildType>> {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The filter, which is used to filter the groups, depending on their data.
     */
    private final Filter<GroupType> filter;

    /**
     * Creates a new filter, which allows to filter groups, depending on their data.
     *
     * @param filter
     *         The filter, which should be used to filter the groups, depending on their data, as an
     *         instance of the type {@link Filter}. The filter may not be null
     */
    public GroupFilter(@NonNull final Filter<GroupType> filter) {
        ensureNotNull(filter, "The filter may not be null");
        this.filter = filter;
    }

    @Override
    public final boolean match(@NonNull final Group<GroupType, ChildType> data,
                               @NonNull final String query, final int flags) {
        return filter.match(data.getData(), query, flags);
    }

}