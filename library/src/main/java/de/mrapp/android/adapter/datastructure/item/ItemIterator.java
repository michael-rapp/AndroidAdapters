/*
 * Copyright 2014 - 2016 Michael Rapp
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
import android.support.annotation.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import de.mrapp.android.adapter.list.ListAdapter;

import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * An iterator, which allows to iterate the data of items, which are contained by a list. When the
 * iterated list is modified, the underlying data of the adapter, the items belong to, is also
 * modified.
 *
 * @param <DataType>
 *         The type of the items' data
 * @author Michael Rapp
 * @since 0.1.0
 */
public class ItemIterator<DataType> implements Iterator<DataType> {

    /**
     * A list, which contains the items, whose data should be iterated.
     */
    private List<Item<DataType>> items;

    /**
     * The adapter, whose underlying data should be modified, when the list, which is iterated by
     * the iterator, is modified.
     */
    private ListAdapter<DataType> adapter;

    /**
     * The current index of the iterator.
     */
    private int currentIndex;

    /**
     * The item, whose data has been returned the last time when the next-method has been called.
     */
    private Item<DataType> lastReturnedItem;

    /**
     * Creates a new iterator, which allows to iterate the data of items, which are contained by a
     * list.
     *
     * @param items
     *         The list, which contains the items, whose data should be iterated, as an instance of
     *         the type {@link List}. The list may not be null
     * @param adapter
     *         The adapter, whose underlying data should be modified, when the list, which is
     *         iterated by the iterator, is modified, as an instance of the type {@link ListAdapter}
     *         or null, if no adapter's underlying data should be modified
     */
    public ItemIterator(@NonNull final List<Item<DataType>> items,
                        @Nullable final ListAdapter<DataType> adapter) {
        ensureNotNull(items, "The items may not be null");
        this.items = items;
        this.adapter = adapter;
        this.currentIndex = -1;
        this.lastReturnedItem = null;
    }

    @Override
    public final boolean hasNext() {
        return currentIndex < items.size() - 1;
    }

    @Override
    public final DataType next() {
        if (!hasNext()) {
            lastReturnedItem = null;
            throw new NoSuchElementException();
        } else {
            currentIndex++;
            lastReturnedItem = items.get(currentIndex);
            return lastReturnedItem.getData();
        }

    }

    @Override
    public final void remove() {
        if (adapter == null) {
            throw new UnsupportedOperationException();
        } else if (lastReturnedItem == null) {
            throw new IllegalStateException();
        } else {
            items.remove(currentIndex);
            adapter.removeItem(currentIndex);
            currentIndex--;
            lastReturnedItem = null;
        }
    }

}