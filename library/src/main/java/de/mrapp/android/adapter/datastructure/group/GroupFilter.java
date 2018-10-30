/*
 * Copyright 2014 - 2018 Michael Rapp
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
import de.mrapp.android.adapter.Filter;
import de.mrapp.util.Condition;

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
        Condition.INSTANCE.ensureNotNull(filter, "The filter may not be null");
        this.filter = filter;
    }

    @Override
    public final boolean match(@NonNull final Group<GroupType, ChildType> data,
                               @NonNull final String query, final int flags) {
        return filter.match(data.getData(), query, flags);
    }

}