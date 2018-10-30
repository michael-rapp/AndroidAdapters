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

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import de.mrapp.android.adapter.datastructure.AbstractUnmodifiableList;

/**
 * An implementation of the type {@link List}, which throws exceptions of the type {@link
 * UnsupportedOperationException} when attempted to be modified. Such a list encapsulates an other
 * list, which contains items of the type {@link Item}.
 *
 * @param <DataType>
 *         The type of the underlying data of the list's items
 * @author Michael Rapp
 * @since 0.1.0
 */
public class UnmodifiableItemList<DataType>
        extends AbstractUnmodifiableList<DataType, Item<DataType>> {

    /**
     * Creates a new implementation of the type {@link List}, which throws exceptions of the type
     * {@link UnsupportedOperationException} when attempted to be modified.
     *
     * @param encapsulatedList
     *         The list, which should be encapsulated by the list, as an instance of the type {@link
     *         List}. The list may not be null
     */
    public UnmodifiableItemList(@NonNull final List<Item<DataType>> encapsulatedList) {
        super(encapsulatedList);
    }

    @Override
    @NonNull
    public final Iterator<DataType> iterator() {
        return new ItemIterator<>(getEncapsulatedList(), null);
    }

    @Override
    public final ListIterator<DataType> listIterator() {
        return new ItemListIterator<>(getEncapsulatedList(), null);
    }

    @Override
    @NonNull
    public final ListIterator<DataType> listIterator(final int location) {
        return new ItemListIterator<>(getEncapsulatedList(), null, location);
    }

    @Override
    public final DataType get(final int location) {
        return getEncapsulatedList().get(location).getData();
    }

}