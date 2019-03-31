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

import java.util.Comparator;

import androidx.annotation.Nullable;
import de.mrapp.util.Condition;

/**
 * A comparator, which allows to compare two groups by comparing their data.
 *
 * @param <GroupType>
 *         The type of the groups' data
 * @param <ChildType>
 *         The type of the groups' children
 * @author Michael Rapp
 * @since 0.1.0
 */
public class GroupComparator<GroupType, ChildType>
        implements Comparator<Group<GroupType, ChildType>> {

    /***
     * The comparator, which is used to compare the groups' data.
     */
    private final Comparator<GroupType> comparator;

    /**
     * Creates a new comparator, which should be used to compare two groups by comparing their
     * data.
     *
     * @param comparator
     *         The comparator, which should be used to compare the groups' data, as an instance of
     *         the type {@link Comparator} or null, if the natural order should be used
     */
    public GroupComparator(@Nullable final Comparator<GroupType> comparator) {
        this.comparator = comparator;
    }

    @Override
    public final int compare(final Group<GroupType, ChildType> lhs,
                             final Group<GroupType, ChildType> rhs) {
        if (comparator != null) {
            return comparator.compare(lhs.getData(), rhs.getData());
        } else {
            return lhs.compareTo(rhs);
        }
    }

}