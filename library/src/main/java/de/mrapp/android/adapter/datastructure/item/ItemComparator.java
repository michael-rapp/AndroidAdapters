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
package de.mrapp.android.adapter.datastructure.item;

import android.support.annotation.NonNull;

import java.util.Comparator;

import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * A comparator, which allows to compare two items by comparing their data.
 *
 * @param <DataType>
 *         The type of the items' data
 * @author Michael Rapp
 * @since 0.1.0
 */
public class ItemComparator<DataType> implements Comparator<Item<DataType>> {

    /**
     * The comparator, which is used to compare the items' data.
     */
    private final Comparator<DataType> comparator;

    /**
     * Creates a new comparator, which allows to compare two items by comparing their data.
     *
     * @param comparator
     *         The comparator, which should be used to compare the items' data, as an instance of
     *         the type {@link Comparator}. The comparator may not be null
     */
    public ItemComparator(@NonNull final Comparator<DataType> comparator) {
        ensureNotNull(comparator, "The comparator may not be null");
        this.comparator = comparator;
    }

    @Override
    public final int compare(final Item<DataType> lhs, final Item<DataType> rhs) {
        return comparator.compare(lhs.getData(), rhs.getData());
    }

}